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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.teamviewer.orderenricher.model.CustomerEntity;
import org.teamviewer.orderenricher.model.EnrichedOrderDTO;
import org.teamviewer.orderenricher.model.EnrichedOrderEntity;
import org.teamviewer.orderenricher.model.ProductEntity;
import org.teamviewer.orderenricher.repository.CustomerRepository;
import org.teamviewer.orderenricher.repository.EnrichedOrderRepository;
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
    private EnrichedOrderRepository mockEnrichedOrderRepository;
    @Mock
    private RedisTemplate<String, EnrichedOrderDTO> mockRedisTemplate;
    @Mock
    private ValueOperations<String, EnrichedOrderDTO> mockValueOperations;

    @InjectMocks
    private OrdersService ordersService;

    @Test
    @Tag("happy_path")
    @Tag("enrich_order")
    void shouldReturnOkHttpResponseForEnrichmentOnSuccess() {
        EnrichedOrderDTO mockEnrichedOrderDTO = new EnrichedOrderDTO();
        mockEnrichedOrderDTO.setCustomerId("1");
        mockEnrichedOrderDTO.setOrderId("12345");
        mockEnrichedOrderDTO.setTimestamp(ZonedDateTime.now());
        ArrayList<String> productIds = new ArrayList<>();
        productIds.add("175");
        productIds.add("124");
        mockEnrichedOrderDTO.setProductIds(productIds);

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

        ArrayList<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(mockProductEntity);

        EnrichedOrderEntity mockEnrichedOrderEntity = new EnrichedOrderEntity();
        mockEnrichedOrderEntity.setOrderId(mockEnrichedOrderDTO.getOrderId());
        mockEnrichedOrderEntity.setTimestamp(mockEnrichedOrderDTO.getTimestamp());
        mockEnrichedOrderEntity.setProducts(productEntities);
        mockEnrichedOrderEntity.setCustomer(mockCustomerEntity);

        Mockito.when(mockRedisTemplate.opsForValue()).thenReturn(mockValueOperations);
        Mockito.when(mockCustomerRepository.findCustomerByCustomerId(mockEnrichedOrderDTO.getCustomerId())).thenReturn(mockCustomerEntity);
        Mockito.when(mockProductRepository.findProductByProductId(mockEnrichedOrderDTO.getProductIds().get(0))).thenReturn(mockProductEntity);
        Mockito.when(mockProductRepository.findProductByProductId(mockEnrichedOrderDTO.getProductIds().get(1))).thenReturn(mockProductEntity);
        Mockito.when(mockEnrichedOrderRepository.save(Mockito.any(EnrichedOrderEntity.class))).thenReturn(mockEnrichedOrderEntity);
        Mockito.doNothing().when(mockValueOperations).set(Mockito.anyString(), Mockito.any(EnrichedOrderDTO.class));

        ResponseEntity<EnrichedOrderDTO> actualResponse = ordersService.enrichOrder(mockEnrichedOrderDTO);

        ResponseEntity<EnrichedOrderDTO> expectedResponse = ResponseEntity.ok(mockEnrichedOrderDTO);
        Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    @Tag("happy_path")
    @Tag("retrieve_order")
    void shouldReturnOkHttpResponseForRedisRetrievalOnSuccess() {
        EnrichedOrderDTO mockEnrichedOrderDTO = new EnrichedOrderDTO();
        mockEnrichedOrderDTO.setOrderId("12345");
        mockEnrichedOrderDTO.setTimestamp(ZonedDateTime.now());
        ArrayList<String> productIds = new ArrayList<>();
        productIds.add("175");
        productIds.add("124");
        mockEnrichedOrderDTO.setProductIds(productIds);

        Mockito.when(mockRedisTemplate.opsForValue()).thenReturn(mockValueOperations);
        Mockito.when(mockValueOperations.get(Mockito.anyString())).thenReturn(mockEnrichedOrderDTO);

        ResponseEntity<EnrichedOrderDTO> actualResponse = ordersService.retrieveOrderDetail(mockEnrichedOrderDTO.getOrderId());

        ResponseEntity<EnrichedOrderDTO> expectedResponse = ResponseEntity.ok(mockEnrichedOrderDTO);
        Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    @Tag("unhappy_path")
    @Tag("retrieve_order")
    void shouldReturnOkHttpResponseForDatabaseRetrievalOnSuccess() {
        EnrichedOrderDTO mockEnrichedOrderDTO = new EnrichedOrderDTO();
        mockEnrichedOrderDTO.setOrderId("12345");
        mockEnrichedOrderDTO.setTimestamp(ZonedDateTime.now());
        ArrayList<String> productIds = new ArrayList<>();
        productIds.add("175");
        productIds.add("124");
        mockEnrichedOrderDTO.setProductIds(productIds);

        EnrichedOrderEntity mockEnrichedOrderEntity = new EnrichedOrderEntity();
        mockEnrichedOrderEntity.setOrderId(mockEnrichedOrderDTO.getOrderId());
        mockEnrichedOrderEntity.setTimestamp(mockEnrichedOrderDTO.getTimestamp());
        mockEnrichedOrderEntity.setCustomer(new CustomerEntity());
        mockEnrichedOrderEntity.setProducts(new ArrayList<>());

        Mockito.when(mockRedisTemplate.opsForValue()).thenReturn(mockValueOperations);
        Mockito.when(mockValueOperations.get(Mockito.anyString())).thenReturn(null);
        Mockito.when(mockEnrichedOrderRepository.findEnrichedOrderEntityByOrderId(mockEnrichedOrderDTO.getOrderId())).thenReturn(mockEnrichedOrderEntity);

        ResponseEntity<EnrichedOrderDTO> actualResponse = ordersService.retrieveOrderDetail(mockEnrichedOrderDTO.getOrderId());

        ResponseEntity<EnrichedOrderDTO> expectedResponse = ResponseEntity.ok(mockEnrichedOrderDTO);
        Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    @Tag("unhappy_path")
    @Tag("retrieve_order")
    void shouldReturnNotFoundHttpResponseForRetrievalOnSuccess() {
        EnrichedOrderDTO mockEnrichedOrderDTO = new EnrichedOrderDTO();
        mockEnrichedOrderDTO.setOrderId("12345");
        mockEnrichedOrderDTO.setTimestamp(ZonedDateTime.now());
        ArrayList<String> productIds = new ArrayList<>();
        productIds.add("175");
        productIds.add("124");
        mockEnrichedOrderDTO.setProductIds(productIds);

        EnrichedOrderEntity mockEnrichedOrderEntity = new EnrichedOrderEntity();
        mockEnrichedOrderEntity.setOrderId(mockEnrichedOrderDTO.getOrderId());
        mockEnrichedOrderEntity.setTimestamp(mockEnrichedOrderDTO.getTimestamp());
        mockEnrichedOrderEntity.setCustomer(new CustomerEntity());
        mockEnrichedOrderEntity.setProducts(new ArrayList<>());

        Mockito.when(mockRedisTemplate.opsForValue()).thenReturn(mockValueOperations);
        Mockito.when(mockValueOperations.get(Mockito.anyString())).thenReturn(null);
        Mockito.when(mockEnrichedOrderRepository.findEnrichedOrderEntityByOrderId(Mockito.anyString())).thenReturn(null);

        ResponseEntity<EnrichedOrderDTO> actualResponse = ordersService.retrieveOrderDetail(mockEnrichedOrderDTO.getOrderId());

        ResponseEntity<EnrichedOrderDTO> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body(mockEnrichedOrderDTO);
        Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }
}
