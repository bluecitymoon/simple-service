package com.pure.service.repository;

import com.pure.service.domain.CountNumber;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CountNumber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountNumberRepository extends JpaRepository<CountNumber, Long>, JpaSpecificationExecutor<CountNumber> {

}
