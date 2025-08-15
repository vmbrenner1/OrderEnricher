package org.teamviewer.orderenricher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.teamviewer.orderenricher.model.CustomerEntity;
import org.teamviewer.orderenricher.model.OrderDetail;
import org.teamviewer.orderenricher.model.ProductEntity;
import org.teamviewer.orderenricher.repository.CustomerRepository;
import org.teamviewer.orderenricher.repository.ProductRepository;

import java.util.ArrayList;

/**
 * Service for managing order information.
 */
@Service
public class OrdersService {
    final private CustomerRepository customerRepository;
    final private ProductRepository productRepository;
    final private RedisTemplate<String, OrderDetail> redisTemplate;

    @Autowired
    public OrdersService(final CustomerRepository customerRepository,
                         final ProductRepository productRepository,
                         final RedisTemplate<String, OrderDetail> redisTemplate) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * Method that will process the order received by the controller, separating pieces as needed for storing in the database.
     *
     * @param orderDetail   The details of the order including customer and product information, order indentifier, and timestamp of order placed.
     * @return              A ResponseEntity indicating the result of the operation.
     */
    // TODO: Paramerterize this endpoint to have the correct object returned
    public ResponseEntity<OrderDetail> enrichOrder(final OrderDetail orderDetail) {
        CustomerEntity customerEntity = customerRepository.findCustomerByCustomerId(orderDetail.getCustomerId());
        ArrayList<ProductEntity> productEntities = findCustomerProducts(orderDetail);

        // while I could have made it all one line above, adding the final assignment here for more readability
        orderDetail.setCustomerEntity(customerEntity);
        orderDetail.setProductEntities(productEntities);

        // saves order detail record to Redis with the orderId as the key
        redisTemplate.opsForValue().set(orderDetail.getOrderId(), orderDetail);

        return ResponseEntity.ok(orderDetail);
    }

    public ResponseEntity<OrderDetail> retrieveOrderDetail(final String orderId) {
        OrderDetail orderDetail = redisTemplate.opsForValue().get(orderId);
        return ResponseEntity.ok(orderDetail);
    }

    // creates an ArrayList of Products with information from the database to match the product Ids provided
    private ArrayList<ProductEntity> findCustomerProducts(final OrderDetail orderDetail) {
        ArrayList<ProductEntity> productEntities = new ArrayList<>();

        for(String product : orderDetail.getProductIds()) {
            productEntities.add(productRepository.findProductByProductId(product));
        }

        return productEntities;
    }
}
