package com.sapient.inventory.model.request;

import com.sapient.inventory.model.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderRequest {
    private String OrderDetail;
    private Address address;
    private String productId;
    private String orderId;
    private OrderStatus orderStatus;
}
