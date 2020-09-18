package cn.az.sec.service;

import cn.az.sec.entity.User;
import cn.az.sec.param.LoginParam;
import cn.az.sec.result.Result;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {

    /**
     * login
     * @param loginParam param
     * @return result
     */
    Result<User> login(LoginParam loginParam);
}
