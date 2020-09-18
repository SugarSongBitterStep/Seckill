package cn.az.sec.bo;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoodsBo {

    private BigDecimal seckillPrice;

    private Integer stockCount;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long id;

    private String goodsName;

    private String goodsTitle;

    private String goodsImg;

    private BigDecimal goodsPrice;

    private Integer goodsStock;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String goodsDetail;

}
