package org.example.securityorder.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;


@Getter
public enum OrderStatus {
    CANCELED("canceled", 0),
    READY("ready", 1),
    COMPLETED("completed", 3),
    PROCESSING("processing", 4),
    FAILED("fail", 5);

    private final String status;
    private final Integer num;

    OrderStatus(String status, Integer num) {
        this.status = status;
        this.num = num;
    }

}

