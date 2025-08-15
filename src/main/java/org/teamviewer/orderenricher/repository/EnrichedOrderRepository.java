package org.teamviewer.orderenricher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teamviewer.orderenricher.model.EnrichedOrderEntity;

/**
 * Repository for managing enriched orders.
 * The function of the repository is to store/retrieve the data into the database.
 */
@Repository
public interface EnrichedOrderRepository extends JpaRepository<EnrichedOrderEntity, Long> {
    EnrichedOrderEntity findEnrichedOrderEntityByOrderId(String orderId);
}
