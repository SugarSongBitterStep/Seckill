package cn.hfbin.seckill.service;

import cn.hfbin.seckill.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Created by: HuangFuBin
 * Date: 2018/7/17
 * Time: 10:49
 * Such description:
 */
public interface OrderService extends IService<OrderInfo> {

    long addOrder(OrderInfo orderInfo);

    OrderInfo getOrderInfo(long rderId);
}
