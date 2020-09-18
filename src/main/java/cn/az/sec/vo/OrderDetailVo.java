package cn.az.sec.vo;

import cn.az.sec.bo.GoodsBo;
import cn.az.sec.entity.OrderInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author azusachino
 */
@Setter
@Getter
public class OrderDetailVo {

    private GoodsBo goods;
    private OrderInfo order;
}
