package com.pure.service.repository;

import com.pure.service.domain.FinanceCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FinanceCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinanceCategoryRepository extends JpaRepository<FinanceCategory, Long>, JpaSpecificationExecutor<FinanceCategory> {

    FinanceCategory findByName(String deal);
}
