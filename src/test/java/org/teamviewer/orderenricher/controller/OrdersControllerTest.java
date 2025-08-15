package org.teamviewer.orderenricher.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.teamviewer.orderenricher.model.EnrichedOrderDTO;
import org.teamviewer.orderenricher.service.OrdersService;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class OrdersControllerTest {
    @Mock
    private OrdersService mockOrdersService;
    @InjectMocks
    private OrdersController ordersController;

    @Test
    @Tag("happy_path")
    @Tag("post_orders")
    void shouldReturnOkHttpResponseForPostOnSuccess() {
        EnrichedOrderDTO enrichedOrderDTO = new EnrichedOrderDTO();
        enrichedOrderDTO.setOrderId("12345");
        enrichedOrderDTO.setTimestamp(ZonedDateTime.now());
        ArrayList<String> productIds = new ArrayList<>();
        productIds.add("175");
        productIds.add("124");
        enrichedOrderDTO.setProductIds(productIds);
        ResponseEntity<EnrichedOrderDTO> expectedResponse = ResponseEntity.ok(enrichedOrderDTO);

        Mockito.when(mockOrdersService.enrichOrder(enrichedOrderDTO)).thenReturn(ResponseEntity.ok(enrichedOrderDTO));

        ResponseEntity<EnrichedOrderDTO> actualResponse = ordersController.orders(enrichedOrderDTO);

        Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    @Tag("happy_path")
    @Tag("get_orders")
    void shouldReturnOkHttpResponseForGetOnSuccess() {
        EnrichedOrderDTO enrichedOrderDTO = new EnrichedOrderDTO();
        enrichedOrderDTO.setOrderId("12345");
        enrichedOrderDTO.setTimestamp(ZonedDateTime.now());
        ArrayList<String> productIds = new ArrayList<>();
        productIds.add("175");
        productIds.add("124");
        enrichedOrderDTO.setProductIds(productIds);
        ResponseEntity<EnrichedOrderDTO> expectedResponse = ResponseEntity.ok(enrichedOrderDTO);

        Mockito.when(mockOrdersService.retrieveOrderDetail(enrichedOrderDTO.getOrderId())).thenReturn(ResponseEntity.ok(enrichedOrderDTO));

        ResponseEntity<EnrichedOrderDTO> actualResponse = ordersController.orders(enrichedOrderDTO.getOrderId());

        Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }
}
