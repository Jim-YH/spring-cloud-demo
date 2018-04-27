package jim.demo.spring.cloud.service;

import jim.demo.spring.cloud.entity.Order;

/**
 * @author jim
 * @date 2018/4/24
 */
public interface PaymentService {

    /**
     * 订单支付
     * @param order
     */
    void makePayment(Order order);

    /**
     * 模拟订单支付的时候库存异常
     * @param order 订单实体
     * @return String
     */
    String mockPaymentInventoryWithTryException(Order order);

    /**
     * 模拟订单支付的时候库存超时
     * @param order 订单实体
     * @return String
     */
    String mockPaymentInventoryWithTryTimeout(Order order);

}
