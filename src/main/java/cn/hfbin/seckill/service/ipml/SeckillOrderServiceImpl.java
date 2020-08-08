package cn.hfbin.seckill.service.ipml;

import cn.hfbin.seckill.bo.GoodsBo;
import cn.hfbin.seckill.common.CommonConst;
import cn.hfbin.seckill.dao.SeckillOrderMapper;
import cn.hfbin.seckill.entity.OrderInfo;
import cn.hfbin.seckill.entity.SeckillOrder;
import cn.hfbin.seckill.entity.User;
import cn.hfbin.seckill.redis.RedisService;
import cn.hfbin.seckill.redis.SeckillKey;
import cn.hfbin.seckill.service.OrderService;
import cn.hfbin.seckill.service.SeckillGoodsService;
import cn.hfbin.seckill.service.SeckillOrderService;
import cn.hfbin.seckill.util.MD5Util;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * Created by: HuangFuBin
 * Date: 2018/7/16
 * Time: 16:47
 * Such description:
 */
@Slf4j
@Service("seckillOrderService")
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements SeckillOrderService {

    SeckillGoodsService seckillGoodsService;

    RedisService redisService;

    OrderService orderService;

    @Autowired
    public void setSeckillGoodsService(SeckillGoodsService seckillGoodsService) {
        this.seckillGoodsService = seckillGoodsService;
    }

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId) {
        return baseMapper.selectByUserIdAndGoodsId(userId, goodsId);
    }

    @Transactional
    @Override
    public OrderInfo insert(User user, GoodsBo goods) {
        //秒杀商品库存减一
        int success = seckillGoodsService.reduceStock(goods.getId());
        if (success == 1) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setCreateDate(new Date());
            orderInfo.setAddrId(0L);
            orderInfo.setGoodsCount(1);
            orderInfo.setGoodsId(goods.getId());
            orderInfo.setGoodsName(goods.getGoodsName());
            orderInfo.setGoodsPrice(goods.getSeckillPrice());
            orderInfo.setOrderChannel(1);
            orderInfo.setStatus(0);
            orderInfo.setUserId((long) user.getId());
            //添加信息进订单
            long orderId = orderService.addOrder(orderInfo);
            log.info("orderId -->" + orderId + "");
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setGoodsId(goods.getId());
            seckillOrder.setOrderId(orderInfo.getId());
            seckillOrder.setUserId((long) user.getId());
            //插入秒杀表
            baseMapper.insertSelective(seckillOrder);
            return orderInfo;
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    @Override
    public OrderInfo getOrderInfo(long orderId) {
        SeckillOrder seckillOrder = baseMapper.selectByPrimaryKey(orderId);
        if (seckillOrder == null) {
            return null;
        }
        return orderService.getOrderInfo(seckillOrder.getOrderId());
    }

    @Override
    public long getSeckillResult(Long userId, long goodsId) {
        SeckillOrder order = getSeckillOrderByUserIdGoodsId(userId, goodsId);
        // 秒杀成功
        if (order != null) {
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public boolean checkPath(User user, long goodsId, String path) {
        if (user == null || path == null) {
            return false;
        }
        String pathOld = redisService.get(SeckillKey.getSeckillPath, "" + user.getId() + "_" + goodsId, String.class);
        return path.equals(pathOld);
    }

    @Override
    public String createMiaoshaPath(User user, long goodsId) {
        if (user == null || goodsId <= 0) {
            return null;
        }
        String str = MD5Util.md5(UUID.randomUUID() + "123456");
        redisService.set(SeckillKey.getSeckillPath, "" + user.getId() + "_" + goodsId, str, CommonConst.RedisCacheExtime.GOODS_ID);
        return str;
    }

    /**
     * 秒杀商品结束标记
     */
    private void setGoodsOver(Long goodsId) {
        redisService.set(SeckillKey.isGoodsOver, "" + goodsId, true, CommonConst.RedisCacheExtime.GOODS_ID);
    }

    /**
     * 查看秒杀商品是否已经结束
     */
    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, "" + goodsId);
    }

}
