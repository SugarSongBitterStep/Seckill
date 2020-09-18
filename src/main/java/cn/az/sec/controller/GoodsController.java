package cn.az.sec.controller;

import cn.az.sec.bo.GoodsBo;
import cn.az.sec.common.CommonConst;
import cn.az.sec.entity.User;
import cn.az.sec.redis.GoodsKey;
import cn.az.sec.redis.RedisService;
import cn.az.sec.redis.UserKey;
import cn.az.sec.result.CodeMsg;
import cn.az.sec.result.Result;
import cn.az.sec.service.SeckillGoodsService;
import cn.az.sec.util.CookieUtil;
import cn.az.sec.vo.GoodsDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by: HuangFuBin
 * Date: 2018/7/11
 * Time: 20:52
 * Such description:
 */

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    RedisService redisService;
    @Autowired
    SeckillGoodsService seckillGoodsService;

    @RequestMapping("/list")
    public String list(Model model) {
        //修改前
       /* List<GoodsBo> goodsList = seckillGoodsService.getSeckillGoodsList();
         model.addAttribute("goodsList", goodsList);
    	 return "goods_list";*/
        //修改后
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        List<GoodsBo> goodsList = seckillGoodsService.getSeckillGoodsList();
        model.addAttribute("goodsList", goodsList);

        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html, CommonConst.RedisCacheExtime.GOODS_LIST);
        }
        return "goods_list";
    }

    @RequestMapping("/to_detail2/{goodsId}")
    public String detail2(Model model, @PathVariable("goodsId") long goodsId, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        User user = redisService.get(UserKey.getByName, loginToken, User.class);
        model.addAttribute("user", user);

        //取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        GoodsBo goods = seckillGoodsService.getseckillGoodsBoByGoodsId(goodsId);
        if (goods == null) {
            return "redirect:/goods/list";
        } else {
            model.addAttribute("goods", goods);
            LocalDateTime startAt = goods.getStartTime();
            LocalDateTime endAt = goods.getStartTime();
            LocalDateTime now = LocalDateTime.now();

            int miaoshaStatus = 0;
            int remainSeconds = 0;
            if (now.isBefore(startAt)) {
                //秒杀还没开始，倒计时
                remainSeconds = (now.compareTo(startAt)) / 1000;
            } else if (now.isAfter(endAt)) {
                //秒杀已经结束
                miaoshaStatus = 2;
                remainSeconds = -1;
            } else {
                //秒杀进行中
                miaoshaStatus = 1;
            }
            model.addAttribute("seckillStatus", miaoshaStatus);
            model.addAttribute("remainSeconds", remainSeconds);
            if (!StringUtils.isEmpty(html)) {
                redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html, CommonConst.RedisCacheExtime.GOODS_INFO);
            }
            return "goods_detail";
        }
    }

    @RequestMapping("/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(Model model,
                                        @PathVariable("goodsId") long goodsId, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        User user = redisService.get(UserKey.getByName, loginToken, User.class);

        GoodsBo goods = seckillGoodsService.getseckillGoodsBoByGoodsId(goodsId);
        if (goods == null) {
            return Result.error(CodeMsg.NO_GOODS);
        } else {
            model.addAttribute("goods", goods);
            LocalDateTime startAt = goods.getStartTime();
            LocalDateTime endAt = goods.getStartTime();
            LocalDateTime now = LocalDateTime.now();

            int miaoshaStatus = 0;
            int remainSeconds = 0;
            if (now.isBefore(startAt)) {
                //秒杀还没开始，倒计时
                remainSeconds = (now.compareTo(startAt)) / 1000;
            } else if (now.isAfter(endAt)) {
                //秒杀已经结束
                miaoshaStatus = 2;
                remainSeconds = -1;
            } else {
                //秒杀进行中
                miaoshaStatus = 1;
            }
            GoodsDetailVo vo = new GoodsDetailVo();
            vo.setGoods(goods);
            vo.setUser(user);
            vo.setRemainSeconds(remainSeconds);
            vo.setMiaoshaStatus(miaoshaStatus);
            return Result.success(vo);
        }
    }
}

