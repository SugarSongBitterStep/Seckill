package cn.az.sec.service;

import cn.az.sec.bo.GoodsBo;
import cn.az.sec.entity.SeckillGoods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author az
 */
public interface SeckillGoodsService extends IService<SeckillGoods> {

    /**
     * @return 物品
     */
    List<GoodsBo> getSeckillGoodsList();

    /**
     * 通过id获取秒杀商品
     * @param goodsId id
     * @return 商品
     */
    GoodsBo getseckillGoodsBoByGoodsId(long goodsId);

    /**
     * 减库存
     * @param goodsId id
     * @return 结果
     */
    int reduceStock(long goodsId);
}
