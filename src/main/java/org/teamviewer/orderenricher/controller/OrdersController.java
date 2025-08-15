package org.teamviewer.orderenricher.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamviewer.orderenricher.model.OrderDetail;
import org.teamviewer.orderenricher.service.OrdersService;

/**
 * Controller for the Order Receiver
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {
    private final OrdersService ordersService;

    /**
     * Basic constructor for the controller in order to inject it with the service layer.
     * @param ordersService     The service layer being injected into the OrdersController
     */
    @Autowired
    public OrdersController(final OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    /**
     * Endpoint to get enriched order information based on the provided order ID.
     * @param orderId   The orderId associated with the order information.
     * @return          HTTP Response with the enriched OrderDetail
     */
    @Operation(summary = "Get Order Information", description = "Endpoint to get enriched order information based on the provided order ID.")
    @GetMapping({"/{orderId}"})
    private ResponseEntity<OrderDetail> orders(@PathVariable String orderId) {
        return ordersService.retrieveOrderDetail(orderId);
    }

    /**
     *
     * @param orderDetail
     * @return
     */
    @Operation(summary = "Receive Order", description = "Endpoint to send order information to be received.")
    @PostMapping()
    private ResponseEntity<OrderDetail> orders(@RequestBody OrderDetail orderDetail) {
        return ordersService.enrichOrder(orderDetail);
    }
}
