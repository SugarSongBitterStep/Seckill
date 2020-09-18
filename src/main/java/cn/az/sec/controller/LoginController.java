package cn.az.sec.controller;

import cn.az.sec.common.CommonConst;
import cn.az.sec.entity.User;
import cn.az.sec.param.LoginParam;
import cn.az.sec.redis.RedisService;
import cn.az.sec.redis.UserKey;
import cn.az.sec.result.Result;
import cn.az.sec.service.UserService;
import cn.az.sec.util.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author azusachino
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @Resource
    private RedisService redisService;
    @Resource
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public Result<User> doLogin(HttpServletResponse response, HttpSession session, @Valid LoginParam loginParam) {
        Result<User> login = userService.login(loginParam);
        if (login.isSuccess()) {
            CookieUtil.writeLoginToken(response, session.getId());
            redisService.set(UserKey.getByName, session.getId(), login.getData(), CommonConst.RedisCacheExtime.REDIS_SESSION_EXPIRE_TIME);
        }
        return login;
    }

    @RequestMapping("/logout")
    public String doLogout(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtil.readLoginToken(request);
        CookieUtil.delLoginToken(request, response);
        redisService.del(UserKey.getByName, token);
        return "login";
    }
}
