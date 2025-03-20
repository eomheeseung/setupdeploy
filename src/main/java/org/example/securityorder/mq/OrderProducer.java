package org.example.securityorder.mq;

import org.example.securityorder.vo.dto.OrderRequestDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



@Component
public class OrderProducer {
    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    /**
     * notice!
     * @value로 필드바인딩을 하지 않고 생성자 바인딩으로 하면 해결 가능함
     * @Value는 생성자 주입이 아닌 필드 주입 방식이므로, 주입 시점이 rabbitTemplate보다 늦을 수 있다!!
     *
     * @value로 바인딩을 할 때 필드를 만들어 놓고 생성자 바인딩(객체 생성 시점)을 하자!!!!
     *
     * 1. field를 final로 선언
     * 2. 생성자 파라미터에서 @value를 사용해서 바인딩
     * @param rabbitTemplate
     * @param exchange
     * @param routingKey
     */
    public OrderProducer(RabbitTemplate rabbitTemplate,
                         @Value("${rabbitmq.exchange.name}") String exchange,
                         @Value("${rabbitmq.routing.key}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void sendOrder(OrderRequestDto orderRequestDto) {
        System.out.println("Sending message to Exchange: " + exchange + " with Routing Key: " + routingKey);
        rabbitTemplate.convertAndSend(exchange, routingKey, orderRequestDto);
        System.out.println("Sent Order: " + orderRequestDto);
    }
}

