package com.pure.service.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.pure.service.domain.ClassCategoryBase;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ClassCategoryBaseRepository;
import com.pure.service.service.dto.ClassCategoryBaseCriteria;


/**
 * Service for executing complex queries for ClassCategoryBase entities in the database.
 * The main input is a {@link ClassCategoryBaseCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ClassCategoryBase} or a {@link Page} of {%link ClassCategoryBase} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ClassCategoryBaseQueryService extends QueryService<ClassCategoryBase> {

    private final Logger log = LoggerFactory.getLogger(ClassCategoryBaseQueryService.class);


    private final ClassCategoryBaseRepository classCategoryBaseRepository;

    public ClassCategoryBaseQueryService(ClassCategoryBaseRepository classCategoryBaseRepository) {
        this.classCategoryBaseRepository = classCategoryBaseRepository;
    }

    /**
     * Return a {@link List} of {%link ClassCategoryBase} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassCategoryBase> findByCriteria(ClassCategoryBaseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ClassCategoryBase> specification = createSpecification(criteria);
        return classCategoryBaseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ClassCategoryBase} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassCategoryBase> findByCriteria(ClassCategoryBaseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ClassCategoryBase> specification = createSpecification(criteria);
        return classCategoryBaseRepository.findAll(specification, page);
    }

    /**
     * Function to convert ClassCategoryBaseCriteria to a {@link Specifications}
     */
    private Specifications<ClassCategoryBase> createSpecification(ClassCategoryBaseCriteria criteria) {
        Specifications<ClassCategoryBase> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClassCategoryBase_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ClassCategoryBase_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ClassCategoryBase_.code));
            }
        }
        return specification;
    }

}
