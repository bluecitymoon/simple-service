package com.pure.service.repository;

import com.pure.service.domain.TimetableItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TimetableItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimetableItemRepository extends JpaRepository<TimetableItem, Long>, JpaSpecificationExecutor<TimetableItem> {

}
