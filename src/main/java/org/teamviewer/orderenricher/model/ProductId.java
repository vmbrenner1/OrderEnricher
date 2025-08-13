package org.teamviewer.orderenricher.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Model representing a product's information.
 * This class contains fields for the product's name, price, category, and tags.
 */
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductId {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String price;

    @Getter
    @Setter
    private String category;

    @Getter
    @Setter
    private String tags;
}
