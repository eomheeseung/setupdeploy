package org.example.securityorder.model.service;

import org.example.securityorder.vo.dto.OrderRequestDto;
import org.example.securityorder.vo.dto.OrderResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    public List<OrderRequestDto>getOrderById(String userId);
    public OrderResponseDto createOrder(OrderRequestDto dto);
}
