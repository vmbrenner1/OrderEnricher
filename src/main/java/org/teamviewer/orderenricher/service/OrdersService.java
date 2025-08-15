package org.teamviewer.orderenricher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.teamviewer.orderenricher.model.CustomerEntity;
import org.teamviewer.orderenricher.model.EnrichedOrderDTO;
import org.teamviewer.orderenricher.model.EnrichedOrderEntity;
import org.teamviewer.orderenricher.model.ProductEntity;
import org.teamviewer.orderenricher.repository.CustomerRepository;
import org.teamviewer.orderenricher.repository.EnrichedOrderRepository;
import org.teamviewer.orderenricher.repository.ProductRepository;

import java.util.ArrayList;

/**
 * Service for managing order information.
 */
@Service
public class OrdersService {
    final private CustomerRepository customerRepository;
    final private ProductRepository productRepository;
    final private EnrichedOrderRepository enrichedOrderRepository;
    final private RedisTemplate<String, EnrichedOrderDTO> redisTemplate;

    /**
     * Basic constructor for the Order Service
     * @param customerRepository    Customer Repository
     * @param productRepository     Product Repository
     * @param redisTemplate         Redis Template for Redis Caching
     */
    @Autowired
    public OrdersService(final CustomerRepository customerRepository,
                         final ProductRepository productRepository,
                         final EnrichedOrderRepository enrichedOrderRepository,
                         final RedisTemplate<String, EnrichedOrderDTO> redisTemplate) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.enrichedOrderRepository = enrichedOrderRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * Method that will process the order received by the controller, separating pieces as needed for storing in the database.
     *
     * @param enrichedOrderDTO   The details of the order including customer and product information, order indentifier, and timestamp of order placed.
     * @return              A ResponseEntity indicating the result of the operation.
     */
    // TODO: Paramerterize this endpoint to have the correct object returned
    public ResponseEntity<EnrichedOrderDTO> enrichOrder(final EnrichedOrderDTO enrichedOrderDTO) {
        CustomerEntity customerEntity = customerRepository.findCustomerByCustomerId(enrichedOrderDTO.getCustomerId());
        ArrayList<ProductEntity> productEntities = findCustomerProducts(enrichedOrderDTO);

        // while I could have made it all one line above, adding the final assignment here for more readability
        enrichedOrderDTO.setCustomerEntity(customerEntity);
        enrichedOrderDTO.setProductEntities(productEntities);

        // create the Entity version
        EnrichedOrderEntity enrichedOrderEntity = new EnrichedOrderEntity();
        enrichedOrderEntity.setCustomer(enrichedOrderDTO.getCustomerEntity());
        enrichedOrderEntity.setProducts(enrichedOrderDTO.getProductEntities());
        enrichedOrderEntity.setOrderId(enrichedOrderDTO.getOrderId());
        enrichedOrderEntity.setTimestamp(enrichedOrderDTO.getTimestamp());

        // saves order detail record to Redis with the orderId as the key
        redisTemplate.opsForValue().set(enrichedOrderDTO.getOrderId(), enrichedOrderDTO);
        // save to the enriched_order table
        enrichedOrderRepository.save(enrichedOrderEntity);

        return ResponseEntity.ok(enrichedOrderDTO);
    }

    public ResponseEntity<EnrichedOrderDTO> retrieveOrderDetail(final String orderId) {
        // check to see if the record has been sent recently and is stored in Redis
        EnrichedOrderDTO enrichedOrderDTO = redisTemplate.opsForValue().get(orderId);

        /*if (enrichedOrderDTO == null) {
            enrichedOrderDTO =
        }*/

        if (enrichedOrderDTO == null) {
            enrichedOrderDTO = new EnrichedOrderDTO();
            enrichedOrderDTO.setResponseMessage("Order was not found with orderId: " + orderId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enrichedOrderDTO);
        }
        return ResponseEntity.ok(enrichedOrderDTO);
    }

    // creates an ArrayList of Products with information from the database to match the product Ids provided
    private ArrayList<ProductEntity> findCustomerProducts(final EnrichedOrderDTO enrichedOrderDTO) {
        ArrayList<ProductEntity> productEntities = new ArrayList<>();

        for(String product : enrichedOrderDTO.getProductIds()) {
            productEntities.add(productRepository.findProductByProductId(product));
        }

        return productEntities;
    }
}
