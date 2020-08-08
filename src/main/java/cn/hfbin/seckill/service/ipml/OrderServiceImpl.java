package cn.hfbin.seckill.service.ipml;

import cn.hfbin.seckill.dao.OrdeInfoMapper;
import cn.hfbin.seckill.entity.OrderInfo;
import cn.hfbin.seckill.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created by: HuangFuBin
 * Date: 2018/7/17
 * Time: 10:50
 * Such description:
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrdeInfoMapper, OrderInfo> implements OrderService {

    @Override
    public long addOrder(OrderInfo orderInfo) {
        return baseMapper.insertSelective(orderInfo);
    }

    @Override
    public OrderInfo getOrderInfo(long orderId) {
        return baseMapper.selectByPrimaryKey(orderId);
    }
}
