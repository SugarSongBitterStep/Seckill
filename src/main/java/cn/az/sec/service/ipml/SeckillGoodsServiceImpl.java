package cn.az.sec.service.ipml;

import cn.az.sec.bo.GoodsBo;
import cn.az.sec.dao.SeckillGoodsMapper;
import cn.az.sec.entity.Goods;
import cn.az.sec.entity.SeckillGoods;
import cn.az.sec.service.GoodsService;
import cn.az.sec.service.SeckillGoodsService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author azusachino
 */
@Service("seckillGoodsService")
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements SeckillGoodsService {

    @Resource
    private GoodsService goodsService;

    @Override
    public List<GoodsBo> getSeckillGoodsList() {
        List<SeckillGoods> seckillGoods = list();
        List<GoodsBo> goodsBos = new ArrayList<>();
        seckillGoods.forEach(s -> {
            Goods goods = goodsService.getById(s.getGoodsId());
            GoodsBo bo = new GoodsBo();
            BeanUtils.copyProperties(s, bo);
            BeanUtils.copyProperties(goods, bo);
            goodsBos.add(bo);
        });
        return goodsBos;
    }

    @Override
    public GoodsBo getseckillGoodsBoByGoodsId(long goodsId) {
        SeckillGoods seckillGoods = getOne(Wrappers.<SeckillGoods>lambdaQuery().eq(SeckillGoods::getGoodsId, goodsId));
        Goods goods = goodsService.getById(goodsId);
        GoodsBo bo = new GoodsBo();
        BeanUtils.copyProperties(seckillGoods, bo);
        BeanUtils.copyProperties(goods, bo);
        return bo;
    }

    @Override
    public int reduceStock(long goodsId) {
        SeckillGoods seckillGoods = getOne(Wrappers.<SeckillGoods>lambdaQuery().eq(SeckillGoods::getGoodsId, goodsId));
        int count = seckillGoods.getStockCount();
        return update(Wrappers.<SeckillGoods>lambdaUpdate()
                .eq(SeckillGoods::getGoodsId, goodsId)
                .set(SeckillGoods::getStockCount, count - 1)) ? 1 : 0;
    }
}
