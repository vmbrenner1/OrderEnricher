package org.teamviewer.orderenricher.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class EnrichedOrderDTOTest {
    @Test
    public void testGettersAndSetters() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        CustomerEntity mockCustomerEntity = new CustomerEntity();
        mockCustomerEntity.setName("Victoria");
        mockCustomerEntity.setZip("77777");
        mockCustomerEntity.setStreet("Neverland Street");
        mockCustomerEntity.setCountry("United States");
        mockCustomerEntity.setCustomerId("12345");

        ProductEntity mockProductEntity = new ProductEntity();
        mockProductEntity.setProductId("474748");
        mockProductEntity.setName("Ice Cream");
        mockProductEntity.setCategory("Food");
        mockProductEntity.setPrice("13.64");
        mockProductEntity.setTags("chocolate,rocky road,vanilla,strawberry");
        ArrayList<ProductEntity> mockProductEntities = new ArrayList<>();
        mockProductEntities.add(mockProductEntity);

        EnrichedOrderDTO mockEnrichedOrderDTO = new EnrichedOrderDTO();
        ArrayList<String> mockProductIds = new ArrayList<>();
        mockProductIds.add("474748");
        mockEnrichedOrderDTO.setCustomerId("12345");
        mockEnrichedOrderDTO.setCustomerEntity(mockCustomerEntity);
        mockEnrichedOrderDTO.setProductEntities(mockProductEntities);
        mockEnrichedOrderDTO.setOrderId("12345");
        mockEnrichedOrderDTO.setTimestamp(zonedDateTime);
        mockEnrichedOrderDTO.setProductIds(mockProductIds);

        Assertions.assertEquals("12345", mockEnrichedOrderDTO.getCustomerEntity().getCustomerId());
        Assertions.assertEquals("474748", mockEnrichedOrderDTO.getProductEntities().get(0).getProductId());
        Assertions.assertEquals("12345", mockEnrichedOrderDTO.getCustomerId());
        Assertions.assertEquals("474748", mockEnrichedOrderDTO.getProductIds().get(0));
        Assertions.assertEquals(zonedDateTime, mockEnrichedOrderDTO.getTimestamp());
    }
}
