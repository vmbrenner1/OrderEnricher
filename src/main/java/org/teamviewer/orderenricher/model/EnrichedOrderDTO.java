package org.teamviewer.orderenricher.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Model representing order details.
 * This class contains all fields required pre- and post- order enrichment
 */
@Data
@NoArgsConstructor
public class EnrichedOrderDTO implements Serializable {
    private CustomerEntity customerEntity;
    private String customerId;
    private String orderId;
    private ArrayList<ProductEntity> productEntities;
    private ArrayList<String> productIds;
    private ZonedDateTime timestamp;
    private String responseMessage;
}
