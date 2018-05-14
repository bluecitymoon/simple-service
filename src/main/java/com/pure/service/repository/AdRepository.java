package com.pure.service.repository;

import com.pure.service.domain.Ad;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdRepository extends JpaRepository<Ad, Long>, JpaSpecificationExecutor<Ad> {

}
