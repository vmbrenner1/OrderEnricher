package org.teamviewer.orderenricher.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

/**
 * Model representing order details.
 * This class contains fields for customer ID and product ID in order to recieve orders.
 */
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderDetails {
    @Getter
    @Setter
    private CustomerId customerId;

    @Getter
    @Setter
    private ProductId productId;

    @Getter
    @Setter
    private String orderId;

    @Getter
    @Setter
    private ZonedDateTime timestamp;
}
