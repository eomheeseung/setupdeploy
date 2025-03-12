package org.example.securityorder.model.service;

import lombok.extern.slf4j.Slf4j;
import org.example.securityorder.model.exception.CustomRuntimeException;
import org.example.securityorder.model.repository.OrderRepository;
import org.example.securityorder.vo.OrderStatus;
import org.example.securityorder.vo.dto.OrderRequestDto;
import org.example.securityorder.vo.dto.OrderResponseDto;
import org.example.securityorder.vo.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper customModelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper customModelMapper) {
        this.orderRepository = orderRepository;
        this.customModelMapper = customModelMapper;
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        // 새로운 주문 객체 생성
        Order order = Order.builder()
                .userId(dto.getUserId())
                .price(dto.getPrice())
                .address(dto.getAddress())
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .quantity(dto.getQuantity())
                .build();

        order.addInfo(dto.getPrice() * dto.getQuantity(), OrderStatus.READY);

        log.info("order dto value:{}", dto.getPrice());

        // 주문 저장
        orderRepository.save(order);

        // 주문 상태를 'COMPLETED'로 변경
        order.updateOrderStatus(OrderStatus.COMPLETED);

        orderRepository.save(order);

        log.info("order userId value:{}", order.getUserId());

        // DB에 저장된 주문 정보 반환
        return findOrder(dto.getUserId());
    }


    public OrderResponseDto findOrder(String userId) {
        Optional<Order> optionalOrder = Optional.ofNullable(orderRepository.findByUserId(userId));

        return optionalOrder
                .map(order -> customModelMapper.map(order, OrderResponseDto.class))
                .orElseThrow(() -> new CustomRuntimeException("Order not found"));
    }


    @Override
    public List<OrderRequestDto> getOrderById(String userId) {
        return List.of();
    }
}
