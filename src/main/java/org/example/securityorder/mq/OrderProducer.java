package org.example.securityorder.mq;

import org.example.securityorder.vo.dto.OrderRequestDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * TODO 실행해서 메시지큐에 안들어가는 이유
 *  - 제이슨 컨버터 해결했는데 오류뜸
 *  - 컨슈머 문제
 *  - @value로 바인딩했는데 문제
 */
@Component
public class OrderProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;


    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrder(OrderRequestDto orderRequestDto) {
        rabbitTemplate.convertAndSend(exchange, routingKey, orderRequestDto);
        System.out.println("Sent Order: " + orderRequestDto);
    }
}
