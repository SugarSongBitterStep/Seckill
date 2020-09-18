package cn.az.sec.service;

import cn.az.sec.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderService extends IService<OrderInfo> {

    /**
     *  添加订单
     * @param orderInfo 订单
     * @return 结果
     */
    long addOrder(OrderInfo orderInfo);

    /**
     * 获取订单详情
     * @param orderId id
     * @return 详情
     */
    OrderInfo getOrderInfo(long orderId);
}
