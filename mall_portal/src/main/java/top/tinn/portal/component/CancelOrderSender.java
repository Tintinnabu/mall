package top.tinn.portal.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.tinn.portal.domain.QueueEnum;

/**
 * @Description CancelOrderSender
 * @Author Tinn
 * @Date 2020/4/10 21:37
 */

@Component
public class CancelOrderSender {
    private static Logger LOGGER =
            LoggerFactory.getLogger(CancelOrderSender.class);
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Long orderId,final long delayTimes) {
        //给延迟队列发送消息
        amqpTemplate.convertAndSend(
                QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(),
                QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(),
                orderId, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message)
                            throws AmqpException {
                        message.getMessageProperties().setExpiration(
                                String.valueOf(delayTimes)
                        );
                        return message;
                    }
                });
        LOGGER.info("send orderId:{} on delay queue",orderId);
    }
}
