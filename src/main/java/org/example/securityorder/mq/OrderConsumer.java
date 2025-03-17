package org.example.securityorder.mq;

import lombok.extern.slf4j.Slf4j;
import org.example.securityorder.vo.dto.OrderRequestDto;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveOrder(OrderRequestDto order) {
        try {
            log.info("Received Order userId: {}", order.getUserId());
        } catch (Exception e) {
            log.error("Error processing order: {}", e.getMessage());
            throw new AmqpRejectAndDontRequeueException("Error processing order", e);  // 메시지를 큐로 재전송하지 않도록 예외 처리
        }
    }
}
