package com.ecomm.payment.enums;

import lombok.Getter;

@Getter
public enum PayType {
    JSAPI(1, "Web Page Payment (JS)"),
    MINI_APP(2, "Mini Program Payment"),
    APP(3, "App Payment"),
    NATIVE(4, "QR Code Payment"),
    BALANCE(5, "Balance Payment"),
    ;
    private final int value;
    private final String desc;

    PayType(int value, String desc) {
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
