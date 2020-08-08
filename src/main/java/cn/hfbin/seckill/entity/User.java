package cn.hfbin.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * @author az
 */
@Setter
@Getter
@ToString
@TableName("user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId
    private Integer id;
    private String userName;
    private String phone;
    private String password;
    private String salt;
    private String head;
    private Integer loginCount;
    private Date registerDate;
    private Date lastLoginDate;
}
