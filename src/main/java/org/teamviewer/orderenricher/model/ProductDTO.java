package org.teamviewer.orderenricher.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for transferring Product information from the backend to the frontend.
 */
@Data
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private String productId;
    private String category;
    private String name;
    private String price;
    private String tags;
}
