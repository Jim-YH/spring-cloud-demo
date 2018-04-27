package jim.demo.spring.cloud.service;

import com.hmily.tcc.annotation.Tcc;
import jim.demo.spring.cloud.entity.AccountDO;
import jim.demo.spring.cloud.entity.AccountDTO;

/**
 * @author jim
 * @date 2018/4/23
 */
public interface AccountService {

    /**
     * 扣款支付
     *
     * @param accountDTO 参数dto
     * @return true
     */
    @Tcc
    boolean payment(AccountDTO accountDTO);


    /**
     * 获取用户账户信息
     * @param userId 用户id
     * @return AccountDO
     */
    AccountDO findByUserId(String userId);

}
