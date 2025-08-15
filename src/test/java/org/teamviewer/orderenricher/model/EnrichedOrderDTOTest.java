package org.teamviewer.orderenricher.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class EnrichedOrderDTOTest {
    @Test
    public void testGettersAndSetters() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        ProductDTO mockProductDTO = new ProductDTO();
        mockProductDTO.setProductId("474748");
        mockProductDTO.setName("Ice Cream");
        mockProductDTO.setCategory("Food");
        mockProductDTO.setPrice("13.64");
        mockProductDTO.setTags("chocolate,rocky road,vanilla,strawberry");
        ArrayList<ProductDTO> mockProductDTOs = new ArrayList<>();
        mockProductDTOs.add(mockProductDTO);

        EnrichedOrderDTO mockEnrichedOrderDTO = new EnrichedOrderDTO();
        ArrayList<String> mockProductIds = new ArrayList<>();
        mockProductIds.add("474748");
        mockEnrichedOrderDTO.setCustomerId("12345");
        mockEnrichedOrderDTO.setProductDTOS(mockProductDTOs);
        mockEnrichedOrderDTO.setOrderId("12345");
        mockEnrichedOrderDTO.setTimestamp(zonedDateTime);
        mockEnrichedOrderDTO.setProductIds(mockProductIds);

        mockEnrichedOrderDTO.setName("Victoria");
        mockEnrichedOrderDTO.setZip("77777");
        mockEnrichedOrderDTO.setStreet("Neverland Street");
        mockEnrichedOrderDTO.setCountry("United States");
        mockEnrichedOrderDTO.setCustomerId("12345");

        Assertions.assertEquals("12345", mockEnrichedOrderDTO.getCustomerId());
        Assertions.assertEquals("474748", mockEnrichedOrderDTO.getProductDTOS().get(0).getProductId());
        Assertions.assertEquals("474748", mockEnrichedOrderDTO.getProductIds().get(0));
        Assertions.assertEquals("Victoria", mockEnrichedOrderDTO.getName());
        Assertions.assertEquals("77777", mockEnrichedOrderDTO.getZip());
        Assertions.assertEquals("Neverland Street", mockEnrichedOrderDTO.getStreet());
        Assertions.assertEquals("United States", mockEnrichedOrderDTO.getCountry());
        Assertions.assertEquals("12345", mockEnrichedOrderDTO.getCustomerId());
        Assertions.assertEquals(zonedDateTime, mockEnrichedOrderDTO.getTimestamp());
    }
}
