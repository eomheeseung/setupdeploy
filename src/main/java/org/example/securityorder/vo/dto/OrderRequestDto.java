package org.example.securityorder.vo.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class OrderRequestDto {
    private String userId;
    private String address;

    // 주문한 아이템 이름
    private String productName;
    // 수량
    private Integer quantity;

    // 가격
    private Integer price;

    private String productId;

    public OrderRequestDto() {
    }
}
