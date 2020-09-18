package cn.az.sec.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("seckill_order")
public class SeckillOrder {

    @TableId
    private Long id;

    private Long userId;

    private Long orderId;

    private Long goodsId;

    private Integer deleted;

}