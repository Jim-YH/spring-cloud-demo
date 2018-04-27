package jim.demo.spring.cloud.service.impl;

import com.hmily.tcc.annotation.Tcc;
import com.hmily.tcc.common.exception.TccRuntimeException;
import jim.demo.spring.cloud.client.AccountClient;
import jim.demo.spring.cloud.client.InventoryClient;
import jim.demo.spring.cloud.dto.AccountDTO;
import jim.demo.spring.cloud.dto.InventoryDTO;
import jim.demo.spring.cloud.entity.Order;
import jim.demo.spring.cloud.enums.OrderStatusEnum;
import jim.demo.spring.cloud.mapper.OrderMapper;
import jim.demo.spring.cloud.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author jim
 * @date 2018/4/24
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    AccountClient accountClient;

    @Autowired
    InventoryClient inventoryClient;

    /**
     *  makePayment 方法是tcc分布式事务的发起者，它里面有更新订单状态（本地），进行库存扣减（rpc扣减），资金扣减(rpc调用)
     *  confirmOrderStatus 为订单状态confirm方法，cancelOrderStatus 为订单状态cancel方法
     */
    @Override
    @Tcc(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public void makePayment(Order order) {
        order.setStatus(OrderStatusEnum.PAYING.getCode());
        orderMapper.update(order);
        //检查用户余额
        BigDecimal accountInfo = accountClient.findByUserId(order.getUserId());
        if (accountInfo.compareTo(order.getTotalAmount()) <= 0) {
            throw  new TccRuntimeException("余额不足！");
        }

        //检查商品库存
        final Integer inventoryInfo= inventoryClient.findByProductId(order.getProductId());
        if (inventoryInfo < order.getCount()) {
            throw  new TccRuntimeException("库存不足！");
        }

        //扣除用户余额
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAmount(order.getTotalAmount());
        accountDTO.setUserId(order.getUserId());

        LOG.debug("===========执行spring cloud扣减资金接口 account.payment ==========");
        accountClient.payment(accountDTO);

        //进入扣减库存操作
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setCount(order.getCount());
        inventoryDTO.setProductId(order.getProductId());

        LOG.debug("===========执行spring cloud扣减库存接口 inventory.decrease ==========");
        inventoryClient.decrease(inventoryDTO);
    }

    @Override
    public String mockPaymentInventoryWithTryException(Order order) {
        return null;
    }

    @Override
    public String mockPaymentInventoryWithTryTimeout(Order order) {
        return null;
    }

    public void confirmOrderStatus(Order order) {
        order.setStatus(OrderStatusEnum.PAY_SUCCESS.getCode());
        orderMapper.update(order);
        LOG.info("=========进行订单confirm操作完成================");
    }

    public void cancelOrderStatus(Order order) {
        order.setStatus(OrderStatusEnum.PAY_FAIL.getCode());
        orderMapper.update(order);
        LOG.info("=========进行订单cancel操作完成================");
    }

}
