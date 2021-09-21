package com.sapient.inventory.reactive.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.inventory.model.enums.OrderStatus;
import com.sapient.inventory.model.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceCommunication {

    public static final String SHIPPING_CREATE = "shipping.create";
    public static final String ORDER_UPDATE = "order.update";
    private final WebClientUtil webClientUtil;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void orderCancel(OrderRequest orderRequest, Boolean isEvent) {
        orderRequest.setOrderStatus(OrderStatus.CANCEL);
        if (isEvent) {
            sendEvent(orderRequest, ORDER_UPDATE);
        } else {
            String url = "http://localhost:9001/api/order/v1/update/order";
            webClientUtil.getResponseEntityMono(url, HttpMethod.PUT, new HttpEntity<>(orderRequest), String.class);
        }
    }

    public void saveShipping(OrderRequest orderRequest, Boolean isEvent) {
        if (orderRequest.getOrderStatus().compareTo(OrderStatus.CANCEL) != 0) {
            if (isEvent) {
                sendEvent(orderRequest, SHIPPING_CREATE);
            } else {
                String url = "http://localhost:9003/api/shipping/v1/shipping";
                webClientUtil.getResponseEntityMono(url, HttpMethod.POST, new HttpEntity<>(orderRequest), String.class);
            }
        }
    }

    private void sendEvent(OrderRequest orderRequest, String topic) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String message = mapper.writeValueAsString(orderRequest);
            kafkaTemplate.send(topic, message);
        } catch (JsonProcessingException e) {
            log.error("Exception occured in parsing the message: {}", orderRequest);
        }
    }
}
