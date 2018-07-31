package com.pure.service.repository;

import com.pure.service.domain.CollectionStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CollectionStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectionStatusRepository extends JpaRepository<CollectionStatus, Long>, JpaSpecificationExecutor<CollectionStatus> {

}
