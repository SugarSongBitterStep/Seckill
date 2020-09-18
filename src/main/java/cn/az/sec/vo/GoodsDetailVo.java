package cn.az.sec.vo;

import cn.az.sec.bo.GoodsBo;
import cn.az.sec.entity.User;
import lombok.Getter;
import lombok.Setter;

/**
 * @author azusachino
 */
@Getter
@Setter
public class GoodsDetailVo {

    private Integer miaoshaStatus = 0;
    private Integer remainSeconds = 0;
    private GoodsBo goods;
    private User user;
}
