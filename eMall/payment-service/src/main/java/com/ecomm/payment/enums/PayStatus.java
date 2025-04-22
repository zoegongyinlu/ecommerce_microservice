package com.ecomm.payment.enums;

import lombok.Getter;

@Getter
public enum PayStatus {
    NOT_COMMIT(0, "Not Submitted"),
    WAIT_BUYER_PAY(1, "Pending Payment"),
    TRADE_CLOSED(2, "Closed"),
    TRADE_SUCCESS(3, "Payment Successful"),
    TRADE_FINISHED(3, "Payment Successful"),
    ;
    private final int value;
    private final String desc;

    PayStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public boolean equalsValue(Integer value){
        if (value == null) {
            return false;
        }
        return getValue() == value;
    }
}