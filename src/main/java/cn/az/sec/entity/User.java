package cn.az.sec.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

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
    private String username;
    private String phone;
    private String password;
    private String salt;
    private String head;
    private Integer loginCount;
    private LocalDateTime registerTime;
    private LocalDateTime lastLoginTime;
    private Integer deleted;
}
