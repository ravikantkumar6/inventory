package com.sapient.inventory.reactive.handler;

import com.sapient.inventory.dto.Inventory;
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
                .body(inventoryReactiveService.getInventory(), Inventory.class);
    }

    public Mono<ServerResponse> saveInventory(ServerRequest request) {
        log.info("Save Inventory");
        return request.bodyToMono(Inventory.class)
                .doOnNext(inventory -> inventoryReactiveService.saveInventory(inventory))
                .then(ServerResponse.ok().build());
       }
}
