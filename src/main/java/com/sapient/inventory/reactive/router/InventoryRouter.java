package com.sapient.inventory.reactive.router;

import com.sapient.inventory.dto.Inventory;
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
    @RouterOperations({@RouterOperation(path = "/inventories", beanClass = Inventory.class, beanMethod = "getInventory"),
            @RouterOperation(path = "/inventory", beanClass = Inventory.class, beanMethod = "saveInventory",
                    operation = @Operation(
                            operationId = "saveInventory",
                            requestBody = @RequestBody(required = true, description = "Enter Request body as Json Object",
                                    content = @Content(
                                            schema = @Schema(implementation = Inventory.class)))))
    })
    public RouterFunction<ServerResponse> route(InventoryHandler inventoryHandler) {

        return RouterFunctions
                .route(GET("/inventories").and(accept(MediaType.APPLICATION_JSON)), inventoryHandler::getInventory)
                .andRoute(POST("/inventory").and(contentType(MediaType.APPLICATION_JSON)).and(accept(MediaType.APPLICATION_JSON)), inventoryHandler::saveInventory);
    }
}
