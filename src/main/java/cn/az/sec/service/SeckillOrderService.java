package cn.az.sec.service;

import cn.az.sec.bo.GoodsBo;
import cn.az.sec.entity.OrderInfo;
import cn.az.sec.entity.SeckillOrder;
import cn.az.sec.entity.User;
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
