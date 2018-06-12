package com.pure.service.repository;

import com.pure.service.domain.NewOrderResourceLocation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NewOrderResourceLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewOrderResourceLocationRepository extends JpaRepository<NewOrderResourceLocation, Long>, JpaSpecificationExecutor<NewOrderResourceLocation> {

}
