package org.teamviewer.orderenricher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teamviewer.orderenricher.model.CustomerEntity;

/**
 * Repository for managing customer information.
 * The function of the repository is to store the data into the database.
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    // finds details on customer based on customer id
    CustomerEntity findCustomerByCustomerId(String customerId);
}
