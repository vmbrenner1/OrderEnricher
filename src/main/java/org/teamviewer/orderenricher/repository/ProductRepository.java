package org.teamviewer.orderenricher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teamviewer.orderenricher.model.ProductEntity;

/**
 * Repository for managing product information.
 * The function of the repository is to store/retrieve the data into the database.
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    // finds details on product based on product id
    ProductEntity findProductByProductId(String productId);
}
