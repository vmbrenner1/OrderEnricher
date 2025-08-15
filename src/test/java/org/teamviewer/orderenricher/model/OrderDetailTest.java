package org.teamviewer.orderenricher.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class OrderDetailTest {
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

        OrderDetail mockOrderDetail = new OrderDetail();
        ArrayList<String> mockProductIds = new ArrayList<>();
        mockProductIds.add("474748");
        mockOrderDetail.setCustomerId("12345");
        mockOrderDetail.setCustomerEntity(mockCustomerEntity);
        mockOrderDetail.setProductEntities(mockProductEntities);
        mockOrderDetail.setOrderId("12345");
        mockOrderDetail.setTimestamp(zonedDateTime);
        mockOrderDetail.setProductIds(mockProductIds);

        Assertions.assertEquals("12345", mockOrderDetail.getCustomerEntity().getCustomerId());
        Assertions.assertEquals("474748", mockOrderDetail.getProductEntities().get(0).getProductId());
        Assertions.assertEquals("12345", mockOrderDetail.getCustomerId());
        Assertions.assertEquals("474748", mockOrderDetail.getProductIds().get(0));
        Assertions.assertEquals(zonedDateTime, mockOrderDetail.getTimestamp());
    }
}
