package cn.az.sec.service.ipml;

import cn.az.sec.dao.OrderInfoMapper;
import cn.az.sec.entity.OrderInfo;
import cn.az.sec.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderService {

    @Override
    public long addOrder(OrderInfo orderInfo) {
        return save(orderInfo) ? 1L : 0;
    }

    @Override
    public OrderInfo getOrderInfo(long orderId) {
        return getById(orderId);
    }
}
