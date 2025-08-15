package org.teamviewer.orderenricher.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO representing enriched order details for transferring data between the frontend and backend.
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
