package cn.az.sec.service.ipml;

import cn.az.sec.dao.UserMapper;
import cn.az.sec.entity.User;
import cn.az.sec.param.LoginParam;
import cn.az.sec.result.CodeMsg;
import cn.az.sec.result.Result;
import cn.az.sec.service.UserService;
import cn.az.sec.util.Md5Util;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result<User> login(LoginParam loginParam) {

        User user = baseMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, loginParam.getMobile()));
        if (user == null) {
            return Result.error(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPwd = user.getPassword();
        String salt = user.getSalt();
        String calcPass = Md5Util.formPassToDbPass(loginParam.getPassword(), salt);
        if (!StringUtils.equals(dbPwd, calcPass)) {
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        user.setPassword(StringUtils.EMPTY);
        return Result.success(user);
    }
}
