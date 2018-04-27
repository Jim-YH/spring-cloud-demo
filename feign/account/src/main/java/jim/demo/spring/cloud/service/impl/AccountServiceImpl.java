package jim.demo.spring.cloud.service.impl;

import com.hmily.tcc.annotation.Tcc;
import com.hmily.tcc.common.exception.TccRuntimeException;
import jim.demo.spring.cloud.entity.AccountDO;
import jim.demo.spring.cloud.entity.AccountDTO;
import jim.demo.spring.cloud.mapper.AccountMapper;
import jim.demo.spring.cloud.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author jim
 * @date 2018/4/23
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    AccountMapper accountMapper;

    @Override
    @Tcc(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean payment(AccountDTO accountDTO) {
        LOG.debug("============spring cloud 执行try付款接口===============");
        final AccountDO accountDO = accountMapper.findByUserId(accountDTO.getUserId());
        accountDO.setBalance(accountDO.getBalance().subtract(accountDTO.getAmount()));
        accountDO.setFreezeAmount(accountDO.getFreezeAmount().add(accountDTO.getAmount()));
        accountDO.setUpdateTime(new Date());
        final int update = accountMapper.update(accountDO);
        if (update != 1) {
            throw new TccRuntimeException("资金不足！");
        }
        return true;
    }

    @Override
    public AccountDO findByUserId(String userId) {
        return accountMapper.findByUserId(userId);
    }

    public boolean confirm(AccountDTO accountDTO) {

        LOG.debug("============spring cloud tcc 执行确认付款接口===============");

        final AccountDO accountDO = accountMapper.findByUserId(accountDTO.getUserId());
        accountDO.setFreezeAmount(accountDO.getFreezeAmount().subtract(accountDTO.getAmount()));
        accountDO.setUpdateTime(new Date());

        final int rows = accountMapper.confirm(accountDO);
        if(rows != 1){
            throw  new TccRuntimeException("确认扣减账户异常！");
        }
        return true;
    }

    public boolean cancel(AccountDTO accountDTO) {

        LOG.debug("============spring cloud tcc 执行取消付款接口===============");

        final AccountDO accountDO = accountMapper.findByUserId(accountDTO.getUserId());
        accountDO.setBalance(accountDO.getBalance().add(accountDTO.getAmount()));
        accountDO.setFreezeAmount(accountDO.getFreezeAmount().subtract(accountDTO.getAmount()));
        accountDO.setUpdateTime(new Date());

        final int rows = accountMapper.cancel(accountDO);
        if(rows != 1){
            throw  new TccRuntimeException("取消扣减账户异常！");
        }
        return true;
    }

}
