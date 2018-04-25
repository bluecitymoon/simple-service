package com.pure.service.repository;

import com.pure.service.domain.NewOrderAssignHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NewOrderAssignHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewOrderAssignHistoryRepository extends JpaRepository<NewOrderAssignHistory, Long>, JpaSpecificationExecutor<NewOrderAssignHistory> {

}
