package com.pure.service.service.impl;

import com.pure.service.service.FinanceCategoryService;
import com.pure.service.domain.FinanceCategory;
import com.pure.service.repository.FinanceCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing FinanceCategory.
 */
@Service
@Transactional
public class FinanceCategoryServiceImpl implements FinanceCategoryService{

    private final Logger log = LoggerFactory.getLogger(FinanceCategoryServiceImpl.class);

    private final FinanceCategoryRepository financeCategoryRepository;

    public FinanceCategoryServiceImpl(FinanceCategoryRepository financeCategoryRepository) {
        this.financeCategoryRepository = financeCategoryRepository;
    }

    /**
     * Save a financeCategory.
     *
     * @param financeCategory the entity to save
     * @return the persisted entity
     */
    @Override
    public FinanceCategory save(FinanceCategory financeCategory) {
        log.debug("Request to save FinanceCategory : {}", financeCategory);
        return financeCategoryRepository.save(financeCategory);
    }

    /**
     *  Get all the financeCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FinanceCategory> findAll(Pageable pageable) {
        log.debug("Request to get all FinanceCategories");
        return financeCategoryRepository.findAll(pageable);
    }

    /**
     *  Get one financeCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FinanceCategory findOne(Long id) {
        log.debug("Request to get FinanceCategory : {}", id);
        return financeCategoryRepository.findOne(id);
    }

    /**
     *  Delete the  financeCategory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FinanceCategory : {}", id);
        financeCategoryRepository.delete(id);
    }
}
