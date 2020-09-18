package cn.az.sec.mq;

import cn.az.sec.bo.GoodsBo;
import cn.az.sec.entity.SeckillOrder;
import cn.az.sec.entity.User;
import cn.az.sec.redis.RedisService;
import cn.az.sec.service.OrderService;
import cn.az.sec.service.SeckillGoodsService;
import cn.az.sec.service.SeckillOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MqReceiver {

    private static final Logger log = LoggerFactory.getLogger(MqReceiver.class);

    @Resource
    private RedisService redisService;

    @Resource
    private SeckillGoodsService goodsService;

    @Resource
    private OrderService orderService;

    @Resource
    private SeckillOrderService seckillOrderService;

    @RabbitListener(queues = MqConfig.MIAOSHA_QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
        SeckillMessage mm = RedisService.stringToBean(message, SeckillMessage.class);
        User user = mm.getUser();
        long goodsId = mm.getGoodsId();

        GoodsBo goods = goodsService.getseckillGoodsBoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        SeckillOrder order = seckillOrderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        seckillOrderService.insert(user, goods);
    }
}
