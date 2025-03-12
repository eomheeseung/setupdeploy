package org.example.securityorder.vo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.example.securityorder.vo.OrderStatus;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
public class Order {
    public Order() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId")
    private String userId;

    private String orderId;

    private String address;

    // 주문한 아이템 id
    private String productId;

    // 주문한 아이템 이름
    @Column(name = "product_name")
    private String productName;

    // 수량
    private Integer quantity;

    // 가격
    private Integer price;

    // 총가격 = 수량 * 가격
    private Integer totalPrice;

    // 주문 상태
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public void addInfo(Integer totalPrice, OrderStatus orderStatus) {
        this.orderId = UUID.randomUUID().toString();
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Builder
    public Order(String userId, String address, String productId, String productName, Integer quantity, Integer price) {
        this.userId = userId;
        this.address = address;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}
