package com.pure.service.service;

import com.pure.service.domain.FinanceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FinanceCategory.
 */
public interface FinanceCategoryService {

    /**
     * Save a financeCategory.
     *
     * @param financeCategory the entity to save
     * @return the persisted entity
     */
    FinanceCategory save(FinanceCategory financeCategory);

    /**
     *  Get all the financeCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FinanceCategory> findAll(Pageable pageable);

    /**
     *  Get the "id" financeCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FinanceCategory findOne(Long id);

    /**
     *  Delete the "id" financeCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
