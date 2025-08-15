package org.teamviewer.orderenricher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.teamviewer.orderenricher.model.CustomerEntity;
import org.teamviewer.orderenricher.model.EnrichedOrderDTO;
import org.teamviewer.orderenricher.model.EnrichedOrderEntity;
import org.teamviewer.orderenricher.model.ProductDTO;
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
     * @return                   A ResponseEntity indicating the result of the operation.
     */
    public ResponseEntity<EnrichedOrderDTO> enrichOrder(final EnrichedOrderDTO enrichedOrderDTO) {
        CustomerEntity customerEntity = customerRepository.findCustomerByCustomerId(enrichedOrderDTO.getCustomerId());
        ArrayList<ProductEntity> productEntities = findCustomerProducts(enrichedOrderDTO);

        EnrichedOrderEntity enrichedOrderEntity;
        enrichedOrderEntity = enrichedOrderDtoToEntity(enrichedOrderDTO);
        enrichedOrderEntity.setCustomer(customerEntity);
        enrichedOrderEntity.setProducts(productEntities);

        // save to the enriched_order table
        enrichedOrderRepository.save(enrichedOrderEntity);

        EnrichedOrderDTO returnedEnrichedOrderDTO = enrichedOrderEntityToDto(enrichedOrderEntity);
        returnedEnrichedOrderDTO.setProductIds(enrichedOrderDTO.getProductIds());

        // saves order detail record to Redis with the orderId as the key
        redisTemplate.opsForValue().set(returnedEnrichedOrderDTO.getOrderId(), returnedEnrichedOrderDTO);

        return ResponseEntity.ok(returnedEnrichedOrderDTO);
    }

    /**
     * Service method that will retrieve the requested Enriched Order Detail by:
     *  1) Checking if it exists in the Redis Cache
     *  2) If it does not exist in Redis, it will check the database table enrich_order
     *      2.5) It will save the Enriched Order in Redis for future retrieval attempts
     *  3) If it does not exist in either place, it will return a NOT_FOUND code with the empty DTO containing a response message
     * @param orderId       OrderID associated with an order
     * @return              Status of the response
     */
    public ResponseEntity<EnrichedOrderDTO> retrieveOrderDetail(final String orderId) {
        EnrichedOrderEntity enrichedOrderEntity;

        // check to see if the record has been sent recently and is stored in Redis
        EnrichedOrderDTO enrichedOrderDTO = redisTemplate.opsForValue().get(orderId);

        // if it was not found in Redis cache, check database table
        if (enrichedOrderDTO == null) {
            enrichedOrderEntity = enrichedOrderRepository.findEnrichedOrderEntityByOrderId(orderId);

            if (enrichedOrderEntity == null) {
                // if we get here, it was neither in Redis nor in the enrich_order table
                enrichedOrderDTO = new EnrichedOrderDTO();
                enrichedOrderDTO.setResponseMessage("Order was not found with orderId: " + orderId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enrichedOrderDTO);
            } else {
                enrichedOrderDTO = enrichedOrderEntityToDto(enrichedOrderEntity);

                ArrayList<String> productIds = new ArrayList<>();
                for(ProductEntity productEntity : enrichedOrderEntity.getProducts() ) {
                    productIds.add(productEntity.getProductId());
                }
                enrichedOrderDTO.setProductIds(productIds);

                // save to Redis in case this order is asked for again
                redisTemplate.opsForValue().set(enrichedOrderDTO.getOrderId(), enrichedOrderDTO);
            }
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

    private EnrichedOrderEntity enrichedOrderDtoToEntity(EnrichedOrderDTO enrichedOrderDTO) {
        EnrichedOrderEntity enrichedOrderEntity = new EnrichedOrderEntity();
        enrichedOrderEntity.setOrderId(enrichedOrderDTO.getOrderId());
        enrichedOrderEntity.setTimestamp(enrichedOrderDTO.getTimestamp());

        // set customer information
        if (enrichedOrderEntity.getCustomer() != null) {
            enrichedOrderEntity.getCustomer().setCustomerId(enrichedOrderDTO.getCustomerId());
            enrichedOrderEntity.getCustomer().setName(enrichedOrderDTO.getName());
            enrichedOrderEntity.getCustomer().setStreet(enrichedOrderDTO.getStreet());
            enrichedOrderEntity.getCustomer().setZip(enrichedOrderDTO.getZip());
            enrichedOrderEntity.getCustomer().setCountry(enrichedOrderDTO.getCountry());
        }

        // set product information
        ArrayList<ProductEntity> productEntities = new ArrayList<>();
        if (enrichedOrderEntity.getProducts() != null) {
            for (ProductDTO productDTO : enrichedOrderDTO.getProductDTOS()) {
                productEntities.add(productDtoToEntity(productDTO));
            }
        }
        enrichedOrderEntity.setProducts(productEntities);

        return enrichedOrderEntity;
    }

    private EnrichedOrderDTO enrichedOrderEntityToDto(EnrichedOrderEntity enrichedOrderEntity) {
        EnrichedOrderDTO enrichedOrderDTO = new EnrichedOrderDTO();
        enrichedOrderDTO.setOrderId(enrichedOrderEntity.getOrderId());
        enrichedOrderDTO.setTimestamp(enrichedOrderEntity.getTimestamp());

        // set customer information in the DTO
        if (enrichedOrderEntity.getCustomer() != null) {
            enrichedOrderDTO.setCustomerId(enrichedOrderEntity.getCustomer().getCustomerId());
            enrichedOrderDTO.setName(enrichedOrderEntity.getCustomer().getName());
            enrichedOrderDTO.setStreet(enrichedOrderEntity.getCustomer().getStreet());
            enrichedOrderDTO.setZip(enrichedOrderEntity.getCustomer().getZip());
            enrichedOrderDTO.setCountry(enrichedOrderEntity.getCustomer().getCountry());
        }

        // set product information in the DTO
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();

        for (ProductEntity productEntity : enrichedOrderEntity.getProducts()) {
            productDTOS.add(productEntityToDto(productEntity));
        }

        enrichedOrderDTO.setProductDTOS(productDTOS);

        return enrichedOrderDTO;
    }

    private ProductDTO productEntityToDto(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(productEntity.getProductId());
        productDTO.setCategory(productEntity.getCategory());
        productDTO.setName(productEntity.getName());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setTags(productEntity.getTags());

        return productDTO;
    }

    private ProductEntity productDtoToEntity(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(productDTO.getProductId());
        productEntity.setCategory(productDTO.getCategory());
        productEntity.setName(productDTO.getName());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setTags(productDTO.getTags());

        return productEntity;
    }
}
