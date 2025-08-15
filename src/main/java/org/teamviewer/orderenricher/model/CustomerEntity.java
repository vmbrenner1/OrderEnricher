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
 * Model representing a customer's information.
 * This class contains fields for the customer's name, street, zip code, and country.
 * Entity ID: customerId
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String customerId;
    private String country;
    private String name;
    private String street;
    private String zip;
}
