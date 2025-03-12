package org.example.securityorder.vo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.securityorder.vo.OrderStatus;

@Getter
@Setter
public class OrderResponseDto {
    private String userId;

    private String orderId;

    private String address;

    // 주문한 아이템 id
    private String productId;

    private String productName;

    // 수량
    private Integer quantity;

    // 가격
    private Integer price;

    // 총가격 = 수량 * 가격
    private Integer totalPrice;

    // 주문 상태
    private OrderStatus orderStatus;
}
