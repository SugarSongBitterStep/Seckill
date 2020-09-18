package cn.az.sec.controller;

import cn.az.sec.bo.GoodsBo;
import cn.az.sec.entity.OrderInfo;
import cn.az.sec.entity.User;
import cn.az.sec.redis.RedisService;
import cn.az.sec.redis.UserKey;
import cn.az.sec.result.CodeMsg;
import cn.az.sec.result.Result;
import cn.az.sec.service.SeckillGoodsService;
import cn.az.sec.service.SeckillOrderService;
import cn.az.sec.util.CookieUtil;
import cn.az.sec.vo.OrderDetailVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/order")
public class SeckillOrderController {

    @Resource
    private RedisService redisService;
    @Resource
    private SeckillOrderService seckillOrderService;
    @Resource
    private SeckillGoodsService seckillGoodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(@RequestParam("orderId") long orderId, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        User user = redisService.get(UserKey.getByName, loginToken, User.class);
        if (user == null) {
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }
        OrderInfo order = seckillOrderService.getOrderInfo(orderId);
        if (order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsBo goods = seckillGoodsService.getseckillGoodsBoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.success(vo);
    }
}
