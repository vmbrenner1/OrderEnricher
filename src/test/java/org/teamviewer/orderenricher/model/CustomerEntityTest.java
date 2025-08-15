package org.teamviewer.orderenricher.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerEntityTest {
    @Test
    public void testGettersAndSetters() {
        CustomerEntity mockCustomerEntity = new CustomerEntity();
        mockCustomerEntity.setName("Victoria");
        mockCustomerEntity.setZip("77777");
        mockCustomerEntity.setStreet("Neverland Street");
        mockCustomerEntity.setCountry("United States");
        mockCustomerEntity.setCustomerId("12345");

        Assertions.assertEquals("Victoria", mockCustomerEntity.getName());
        Assertions.assertEquals("77777", mockCustomerEntity.getZip());
        Assertions.assertEquals("Neverland Street", mockCustomerEntity.getStreet());
        Assertions.assertEquals("United States", mockCustomerEntity.getCountry());
        Assertions.assertEquals("12345", mockCustomerEntity.getCustomerId());
    }

    @Test
    public void testHashCodeAndEquals() {
        CustomerEntity mockCustomerEntity1 = new CustomerEntity();
        mockCustomerEntity1.setName("Victoria");
        mockCustomerEntity1.setZip("77777");
        mockCustomerEntity1.setStreet("Neverland Street");
        mockCustomerEntity1.setCountry("United States");
        mockCustomerEntity1.setCustomerId("12345");

        CustomerEntity mockCustomerEntity2 = new CustomerEntity();
        mockCustomerEntity2.setName("Victoria");
        mockCustomerEntity2.setZip("77777");
        mockCustomerEntity2.setStreet("Neverland Street");
        mockCustomerEntity2.setCountry("United States");
        mockCustomerEntity2.setCustomerId("12345");

        CustomerEntity mockCustomerEntity3 = new CustomerEntity();
        mockCustomerEntity3.setName("Nicholas");
        mockCustomerEntity3.setZip("56378");
        mockCustomerEntity3.setStreet("Rainbow Road");
        mockCustomerEntity3.setCountry("United States");
        mockCustomerEntity3.setCustomerId("54321");

        Assertions.assertEquals(mockCustomerEntity1, mockCustomerEntity2);
        Assertions.assertEquals(mockCustomerEntity1.hashCode(), mockCustomerEntity2.hashCode());
        Assertions.assertNotEquals(mockCustomerEntity1, mockCustomerEntity3);
    }

    @Test
    public void testToString() {
        CustomerEntity mockCustomerEntity1 = new CustomerEntity();
        mockCustomerEntity1.setName("Victoria");
        mockCustomerEntity1.setZip("77777");
        mockCustomerEntity1.setStreet("Neverland Street");
        mockCustomerEntity1.setCountry("United States");
        mockCustomerEntity1.setCustomerId("12345");

        Assertions.assertEquals("CustomerEntity(customerId=12345, country=United States, name=Victoria, street=Neverland Street, zip=77777)", mockCustomerEntity1.toString());
    }
}
