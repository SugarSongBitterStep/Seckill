package cn.az.sec.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("order_info")
public class OrderInfo {

    @TableId
    private Long id;

    private Long userId;

    private Long goodsId;

    private Long addrId;

    private String goodsName;

    private Integer goodsCount;

    private BigDecimal goodsPrice;

    private Integer orderChannel;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime payTime;

    private Integer deleted;

}