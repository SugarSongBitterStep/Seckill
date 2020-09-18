package cn.az.sec.mq;

import cn.az.sec.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MqSender {

    private static final Logger log = LoggerFactory.getLogger(MqSender.class);

    @Resource
    private AmqpTemplate amqpTemplate;

    public void sendSeckillMessage(SeckillMessage mm) {
        String msg = RedisService.beanToString(mm);
        log.info("send message:" + msg);
        amqpTemplate.convertAndSend(MqConfig.MIAOSHA_QUEUE, msg);
    }

}
