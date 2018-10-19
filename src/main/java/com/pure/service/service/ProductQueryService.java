package com.pure.service.service;


import com.pure.service.domain.ClassAgeLevel_;
import com.pure.service.domain.ClassRoom_;
import com.pure.service.domain.Course_;
import com.pure.service.domain.Product;
import com.pure.service.domain.Product_;
import com.pure.service.domain.Teacher_;
import com.pure.service.repository.ProductRepository;
import com.pure.service.service.dto.ProductCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service for executing complex queries for Product entities in the database.
 * The main input is a {@link ProductCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Product} or a {@link Page} of {%link Product} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ProductQueryService extends QueryService<Product> {

    private final Logger log = LoggerFactory.getLogger(ProductQueryService.class);


    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Return a {@link List} of {%link Product} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Product> findByCriteria(ProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Product} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Product> findByCriteria(ProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification, page);
    }

    /**
     * Function to convert ProductCriteria to a {@link Specifications}
     */
    private Specifications<Product> createSpecification(ProductCriteria criteria) {
        Specifications<Product> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), Product_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Product_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Product_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Product_.code));
            }
            if (criteria.getMinutesPerClass() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinutesPerClass(), Product_.minutesPerClass));
            }
            if (criteria.getPlanedStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlanedStartDate(), Product_.planedStartDate));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), Product_.comments));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Product_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Product_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Product_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Product_.lastModifiedDate));
            }
            if (criteria.getClassAgeLevelId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClassAgeLevelId(), Product_.classAgeLevel, ClassAgeLevel_.id));
            }
            if (criteria.getTeacherId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTeacherId(), Product_.teacher, Teacher_.id));
            }
            if (criteria.getClassRoomId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClassRoomId(), Product_.classRoom, ClassRoom_.id));
            }
            if (criteria.getCourseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCourseId(), Product_.course, Course_.id));
            }
        }
        return specification;
    }

}
