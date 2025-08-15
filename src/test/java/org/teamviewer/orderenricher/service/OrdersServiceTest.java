package org.teamviewer.orderenricher.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.teamviewer.orderenricher.model.CustomerEntity;
import org.teamviewer.orderenricher.model.OrderDetail;
import org.teamviewer.orderenricher.model.ProductEntity;
import org.teamviewer.orderenricher.repository.CustomerRepository;
import org.teamviewer.orderenricher.repository.ProductRepository;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class OrdersServiceTest {
    @Mock
    private CustomerRepository mockCustomerRepository;
    @Mock
    private ProductRepository mockProductRepository;
    @Mock
    private RedisTemplate<String, OrderDetail> mockRedisTemplate;
    @Mock
    private ValueOperations<String, OrderDetail> mockValueOperations;

    @InjectMocks
    private OrdersService ordersService;

    @Test
    @Tag("happy_path")
    @Tag("enrich_order")
    void shouldReturnOkHttpResponseForEnrichmentOnSuccess() {
        OrderDetail mockOrderDetail = new OrderDetail();
        mockOrderDetail.setOrderId("12345");
        mockOrderDetail.setTimestamp(ZonedDateTime.now());
        ArrayList<String> productIds = new ArrayList<>();
        productIds.add("175");
        productIds.add("124");
        mockOrderDetail.setProductIds(productIds);

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

        Mockito.when(mockRedisTemplate.opsForValue()).thenReturn(mockValueOperations);
        Mockito.when(mockCustomerRepository.findCustomerByCustomerId(mockOrderDetail.getCustomerId())).thenReturn(mockCustomerEntity);
        Mockito.when(mockProductRepository.findProductByProductId(mockOrderDetail.getProductIds().get(0))).thenReturn(mockProductEntity);
        Mockito.doNothing().when(mockValueOperations).set(Mockito.anyString(), Mockito.any(OrderDetail.class));

        ResponseEntity<OrderDetail> actualResponse = ordersService.enrichOrder(mockOrderDetail);

        ResponseEntity<OrderDetail> expectedResponse = ResponseEntity.ok(mockOrderDetail);
        Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    @Tag("happy_path")
    @Tag("retrieve_order")
    void shouldReturnOkHttpResponseForRetrievalOnSuccess() {
        OrderDetail mockOrderDetail = new OrderDetail();
        mockOrderDetail.setOrderId("12345");
        mockOrderDetail.setTimestamp(ZonedDateTime.now());
        ArrayList<String> productIds = new ArrayList<>();
        productIds.add("175");
        productIds.add("124");
        mockOrderDetail.setProductIds(productIds);

        Mockito.when(mockRedisTemplate.opsForValue()).thenReturn(mockValueOperations);
        Mockito.when(mockValueOperations.get(Mockito.anyString())).thenReturn(mockOrderDetail);

        ResponseEntity<OrderDetail> actualResponse = ordersService.retrieveOrderDetail(mockOrderDetail.getOrderId());

        ResponseEntity<OrderDetail> expectedResponse = ResponseEntity.ok(mockOrderDetail);
        Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }
}
