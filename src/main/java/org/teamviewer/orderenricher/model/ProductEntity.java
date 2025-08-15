package org.teamviewer.orderenricher.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model representing a product's information.
 * This class contains fields for the product's name, price, category, and tags.
 * Entity ID: productId
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String productId;
    private String category;
    private String name;
    private String price;
    private String tags;
}
