package org.teamviewer.orderenricher.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Model representing order details.
 * This class contains all fields required pre- and post- order enrichment
 */
@Data
@NoArgsConstructor
public class EnrichedOrderDTO implements Serializable {
    private String orderId;
    private ZonedDateTime timestamp;
    private ArrayList<String> productIds;
    private String responseMessage;

    private List<ProductDTO> productDTOS;

    private String customerId;
    private String name;
    private String street;
    private String zip;
    private String country;
}
