package cn.hfbin.seckill.service;

import cn.hfbin.seckill.bo.GoodsBo;
import cn.hfbin.seckill.entity.OrderInfo;
import cn.hfbin.seckill.entity.SeckillOrder;
import cn.hfbin.seckill.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * My Blog : www.hfbin.cn
 * github: https://github.com/hfbin
 * Created by: HuangFuBin
 * Date: 2018/7/16
 * Time: 16:46
 * Such description:
 */
public interface SeckillOrderService extends IService<SeckillOrder> {

    SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId);

    OrderInfo insert(User user, GoodsBo goodsBo);

    OrderInfo getOrderInfo(long orderId);

    long getSeckillResult(Long userId, long goodsId);

    boolean checkPath(User user, long goodsId, String path);

    String createMiaoshaPath(User user, long goodsId);

}
