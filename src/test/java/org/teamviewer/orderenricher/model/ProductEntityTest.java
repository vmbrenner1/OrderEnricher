package org.teamviewer.orderenricher.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductEntityTest {
    @Test
    public void testGettersAndSetters() {
        ProductEntity mockProductEntity = new ProductEntity();
        mockProductEntity.setProductId("474748");
        mockProductEntity.setName("Ice Cream");
        mockProductEntity.setCategory("Food");
        mockProductEntity.setPrice("13.64");
        mockProductEntity.setTags("chocolate,rocky road,vanilla,strawberry");

        Assertions.assertEquals("474748", mockProductEntity.getProductId());
        Assertions.assertEquals("Ice Cream", mockProductEntity.getName());
        Assertions.assertEquals("Food", mockProductEntity.getCategory());
        Assertions.assertEquals("13.64", mockProductEntity.getPrice());
        Assertions.assertEquals("chocolate,rocky road,vanilla,strawberry", mockProductEntity.getTags());
    }

    @Test
    public void testHashCodeAndEquals() {
        ProductEntity mockProductEntity1 = new ProductEntity();
        mockProductEntity1.setProductId("474748");
        mockProductEntity1.setName("Ice Cream");
        mockProductEntity1.setCategory("Food");
        mockProductEntity1.setPrice("13.64");
        mockProductEntity1.setTags("chocolate,rocky road,vanilla,strawberry");

        ProductEntity mockProductEntity2 = new ProductEntity();
        mockProductEntity2.setProductId("474748");
        mockProductEntity2.setName("Ice Cream");
        mockProductEntity2.setCategory("Food");
        mockProductEntity2.setPrice("13.64");
        mockProductEntity2.setTags("chocolate,rocky road,vanilla,strawberry");

        ProductEntity mockProductEntity3 = new ProductEntity();
        mockProductEntity3.setProductId("68684");
        mockProductEntity3.setName("Popcorn");
        mockProductEntity3.setCategory("Food");
        mockProductEntity3.setPrice("4.32");
        mockProductEntity3.setTags("caramel,kettle,butter");

        Assertions.assertEquals(mockProductEntity1, mockProductEntity2);
        Assertions.assertEquals(mockProductEntity1.hashCode(), mockProductEntity2.hashCode());
        Assertions.assertNotEquals(mockProductEntity1, mockProductEntity3);
    }

    @Test
    public void testToString() {
        ProductEntity mockProductEntity1 = new ProductEntity();
        mockProductEntity1.setProductId("474748");
        mockProductEntity1.setName("Ice Cream");
        mockProductEntity1.setCategory("Food");
        mockProductEntity1.setPrice("13.64");
        mockProductEntity1.setTags("chocolate,rocky road,vanilla,strawberry");

        Assertions.assertEquals("ProductEntity(productId=474748, category=Food, name=Ice Cream, price=13.64, tags=chocolate,rocky road,vanilla,strawberry)",
                mockProductEntity1.toString());
    }
}
