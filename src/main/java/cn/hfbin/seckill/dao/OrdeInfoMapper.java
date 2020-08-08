package cn.hfbin.seckill.dao;

import cn.hfbin.seckill.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdeInfoMapper extends BaseMapper<OrderInfo> {

    int deleteByPrimaryKey(Long id);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
}