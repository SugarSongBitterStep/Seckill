package cn.az.sec.service.ipml;

import cn.az.sec.dao.GoodsMapper;
import cn.az.sec.entity.Goods;
import cn.az.sec.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author az
 * @since 09/19/20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
}
