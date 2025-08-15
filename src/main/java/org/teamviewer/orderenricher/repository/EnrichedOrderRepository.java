package org.teamviewer.orderenricher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teamviewer.orderenricher.model.EnrichedOrderEntity;

@Repository
public interface EnrichedOrderRepository extends JpaRepository<EnrichedOrderEntity, Long> {

}
