package com.codecool.shop.model.order;

import java.util.Arrays;
import java.util.List;

public enum PaymentStatus {
    NEW,
    PROCESSED,
    PAID;

    private static List<PaymentStatus> statuses = Arrays.asList(values());

    public PaymentStatus getNext() {
        return statuses.get((this.ordinal() + 1) % statuses.size());
    }
}
