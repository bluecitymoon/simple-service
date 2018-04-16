package com.pure.service.repository;

import com.pure.service.domain.MarketChannelCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MarketChannelCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketChannelCategoryRepository extends JpaRepository<MarketChannelCategory, Long> {

}
