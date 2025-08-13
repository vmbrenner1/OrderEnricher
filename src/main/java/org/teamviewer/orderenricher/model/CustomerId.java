package org.teamviewer.orderenricher.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model representing a customer's information.
 * This class contains fields for the customer's name, street, zip code, and country.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class CustomerId {
    @Getter
    @Setter
    @Id
    private String name;

    @Getter
    @Setter
    private String street;

    @Getter
    @Setter
    private String zip;

    @Getter
    @Setter
    private String country;
}
