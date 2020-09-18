package cn.az.sec.service;

import cn.az.sec.bo.GoodsBo;
import cn.az.sec.entity.SeckillGoods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SeckillGoodsService extends IService<SeckillGoods> {

    List<GoodsBo> getSeckillGoodsList();

    GoodsBo getseckillGoodsBoByGoodsId(long goodsId);

    int reduceStock(long goodsId);
}
