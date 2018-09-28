package com.pure.service.repository;

import com.pure.service.domain.TimeSegment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TimeSegment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeSegmentRepository extends JpaRepository<TimeSegment, Long>, JpaSpecificationExecutor<TimeSegment> {

}
