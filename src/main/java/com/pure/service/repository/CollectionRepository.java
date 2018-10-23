package com.pure.service.repository;

import com.pure.service.domain.Collection;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Collection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long>, JpaSpecificationExecutor<Collection> {

    List<Collection> findBySequenceNumber(String sequenceNumber);
}
