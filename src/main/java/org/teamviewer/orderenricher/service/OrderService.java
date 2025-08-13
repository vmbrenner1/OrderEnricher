package org.teamviewer.orderenricher.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.teamviewer.orderenricher.model.CustomerId;
import org.teamviewer.orderenricher.model.OrderDetails;
import org.teamviewer.orderenricher.model.ProductId;

/**
 * Service for managing order information.
 */
@Service
public class OrderService {
    /**
     * Repository for manipulating and managing customer information.
     *
     * @param orderDetails The details of the order including customer and product information, order indentifier, and timestamp of order placed.
     * @return             A ResponseEntity indicating the result of the operation.
     */
    // TODO: Paramerterize this endpoint to have the correct object returned
    public ResponseEntity receiveOrders(final OrderDetails orderDetails) {
        CustomerId customerId = orderDetails.getCustomerId();
        ProductId productId = orderDetails.getProductId();

        return ResponseEntity.ok("Customer information processed successfully for order ID: " + orderDetails.getOrderId());
    }
}
