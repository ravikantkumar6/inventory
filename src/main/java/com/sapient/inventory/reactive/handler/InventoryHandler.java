package com.sapient.inventory.reactive.handler;

import com.sapient.inventory.model.Inventory;
import com.sapient.inventory.model.request.OrderRequest;
import com.sapient.inventory.reactive.service.InventoryReactiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryHandler {

    private final InventoryReactiveService inventoryReactiveService;

    public Mono<ServerResponse> getInventory(ServerRequest request) {
        log.info("Get Inventory");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inventoryReactiveService.getInventory());
    }

    public Mono<ServerResponse> saveInventory(ServerRequest serverRequest) {
        log.info("Save Inventory");
        return serverRequest
                .bodyToMono(Inventory.class)
                .flatMap(orderRequest -> Mono.fromCallable(() -> this.inventoryReactiveService.saveInventory(orderRequest)))
                .flatMap(inventory -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(inventory));
    }

    public Mono<ServerResponse> updateInventory(ServerRequest serverRequest) {
        log.info("Save Inventory");
        return serverRequest
                .bodyToMono(OrderRequest.class)
                .flatMap(orderRequest -> Mono.fromCallable(() -> this.inventoryReactiveService.updateInventory(orderRequest, false)))
                .flatMap(inventory -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(inventory));
    }
}
