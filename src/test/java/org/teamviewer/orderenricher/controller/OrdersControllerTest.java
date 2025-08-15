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
import org.teamviewer.orderenricher.model.OrderDetail;
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
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId("12345");
        orderDetail.setTimestamp(ZonedDateTime.now());
        ArrayList<String> productIds = new ArrayList<>();
        productIds.add("175");
        productIds.add("124");
        orderDetail.setProductIds(productIds);
        ResponseEntity<OrderDetail> expectedResponse = ResponseEntity.ok(orderDetail);

        Mockito.when(mockOrdersService.enrichOrder(orderDetail)).thenReturn(ResponseEntity.ok(orderDetail));

        ResponseEntity<OrderDetail> actualResponse = ordersController.orders(orderDetail);

        Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    @Tag("happy_path")
    @Tag("get_orders")
    void shouldReturnOkHttpResponseForGetOnSuccess() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId("12345");
        orderDetail.setTimestamp(ZonedDateTime.now());
        ArrayList<String> productIds = new ArrayList<>();
        productIds.add("175");
        productIds.add("124");
        orderDetail.setProductIds(productIds);
        ResponseEntity<OrderDetail> expectedResponse = ResponseEntity.ok(orderDetail);

        Mockito.when(mockOrdersService.retrieveOrderDetail(orderDetail.getOrderId())).thenReturn(ResponseEntity.ok(orderDetail));

        ResponseEntity<OrderDetail> actualResponse = ordersController.orders(orderDetail.getOrderId());

        Assertions.assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }
}
