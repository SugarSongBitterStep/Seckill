package cn.az.sec.service.ipml;

import cn.az.sec.bo.GoodsBo;
import cn.az.sec.common.CommonConst;
import cn.az.sec.dao.SeckillOrderMapper;
import cn.az.sec.entity.OrderInfo;
import cn.az.sec.entity.SeckillOrder;
import cn.az.sec.entity.User;
import cn.az.sec.redis.RedisService;
import cn.az.sec.redis.SeckillKey;
import cn.az.sec.service.OrderService;
import cn.az.sec.service.SeckillGoodsService;
import cn.az.sec.service.SeckillOrderService;
import cn.az.sec.util.Md5Util;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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

    @Resource
    private SeckillGoodsService seckillGoodsService;
    @Resource
    private RedisService redisService;
    @Resource
    OrderService orderService;

    @Override
    public SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId) {
        return baseMapper.selectOne(Wrappers.<SeckillOrder>query()
                .eq("userId", userId)
                .eq("goods_id", goodsId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderInfo insert(User user, GoodsBo goods) {
        //秒杀商品库存减一
        int success = seckillGoodsService.reduceStock(goods.getId());
        if (success == 1) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setCreateTime(LocalDateTime.now());
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
            saveOrUpdate(seckillOrder);
            return orderInfo;
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    @Override
    public OrderInfo getOrderInfo(long orderId) {
        SeckillOrder seckillOrder = getById(orderId);
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
        String str = Md5Util.md5(UUID.randomUUID() + "123456");
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
