package org.example.securityorder.model.controller;

import com.netflix.discovery.converters.Auto;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.securityorder.model.service.OrderService;
import org.example.securityorder.model.service.OrderServiceImpl;
import org.example.securityorder.vo.dto.OrderRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private final OrderService orderService;

    @GetMapping("/view")
    public ResponseEntity<Object> viewOrders(HttpRequest request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addOrders(@RequestBody OrderRequestDto dto) {
        log.info("controller -> dto value:{}", dto.getUserId());
        log.info("controller -> dto getPrice:{}", dto.getPrice());
        log.info("controller -> dto getQuantity:{}", dto.getQuantity());
        return ResponseEntity.ok().body(orderService.createOrder(dto));
    }
}
