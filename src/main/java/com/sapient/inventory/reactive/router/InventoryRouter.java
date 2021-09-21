package com.sapient.inventory.reactive.router;

import com.sapient.inventory.model.Inventory;
import com.sapient.inventory.model.request.OrderRequest;
import com.sapient.inventory.reactive.handler.InventoryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class InventoryRouter {

    @Bean
    @RouterOperations({@RouterOperation(path = "/inventories", beanClass = InventoryHandler.class, beanMethod = "getInventory"),
            @RouterOperation(path = "/save/inventory", beanClass = InventoryHandler.class, beanMethod = "saveInventory",
                    operation = @Operation(
                            operationId = "saveInventory",
                            requestBody = @RequestBody(required = true, description = "Enter Request body as Json Object",
                                    content = @Content(
                                            schema = @Schema(implementation = Inventory.class))))),
            @RouterOperation(path = "/update/inventory", beanClass = InventoryHandler.class, beanMethod = "updateInventory",
                    operation = @Operation(
                            operationId = "updateInventory",
                            requestBody = @RequestBody(required = true, description = "Enter Request body as Json Object",
                                    content = @Content(
                                            schema = @Schema(implementation = OrderRequest.class)))))
    })
    public RouterFunction<ServerResponse> route(InventoryHandler inventoryHandler) {

        return RouterFunctions
                .route(GET("/inventories").and(accept(MediaType.APPLICATION_JSON)), inventoryHandler::getInventory)
                .andRoute(POST("/save/inventory").and(contentType(MediaType.APPLICATION_JSON)).and(accept(MediaType.APPLICATION_JSON)), inventoryHandler::saveInventory)
                .andRoute(PUT("/update/inventory").and(contentType(MediaType.APPLICATION_JSON)).and(accept(MediaType.APPLICATION_JSON)), inventoryHandler::updateInventory);
    }
}
