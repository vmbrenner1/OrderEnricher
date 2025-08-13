package org.teamviewer.orderenricher.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamviewer.orderenricher.model.OrderDetails;
import org.teamviewer.orderenricher.service.OrderService;

/**
 * Controller for the Order Receiver
 */
@RestController
@RequestMapping("/api/v1/orders")
public class Orders {
    private final OrderService orderService;

    @Autowired
    public Orders(final OrderService orderService) {
        this.orderService = orderService;
    }

    // TODO: Paramerterize this endpoint to have the correct object returned
    // TODO: Edit this to have the correct object returned
    @Operation(summary = "Get Order Information", description = "Endpoint to get order information based on the provided order ID.")
    @GetMapping({"/{orderId}"})
    private ResponseEntity orders(@RequestParam String orderId) {
        // return data for the orderID given
        return null;
    }

    // TODO: Paramerterize this endpoint to have the correct object returned
    @Operation(summary = "Receive Order Information", description = "Endpoint to receive order information including order details (customer and product information), order id, and timestamp.")
    @PostMapping()
    private ResponseEntity orders(@RequestBody OrderDetails orderDetails) {
        return orderService.receiveOrders(orderDetails);
    }
}
