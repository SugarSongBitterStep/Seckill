package cn.az.sec.common;

/**
 * Common Const
 *
 * @author az
 */
public class CommonConst {

    public interface RedisCacheExtime {
        int REDIS_SESSION_EXPIRE_TIME = 60 * 30;//30分钟
        int GOODS_LIST = 60 * 30 * 24;//1分钟
        int GOODS_ID = 60;//1分钟
        int GOODS_INFO = 60;//1分钟
    }
}
