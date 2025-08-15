package org.teamviewer.orderenricher.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "country")
    private String country;

    @Column(name = "name")
    private String name;

    @Column(name = "street")
    private String street;

    @Column(name = "zip")
    private String zip;
}
