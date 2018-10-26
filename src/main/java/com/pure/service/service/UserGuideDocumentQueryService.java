package com.pure.service.service;


import com.pure.service.domain.UserGuideDocument;
import com.pure.service.domain.UserGuideDocument_;
import com.pure.service.repository.UserGuideDocumentRepository;
import com.pure.service.service.dto.UserGuideDocumentCriteria;
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
 * Service for executing complex queries for UserGuideDocument entities in the database.
 * The main input is a {@link UserGuideDocumentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link UserGuideDocument} or a {@link Page} of {%link UserGuideDocument} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class UserGuideDocumentQueryService extends QueryService<UserGuideDocument> {

    private final Logger log = LoggerFactory.getLogger(UserGuideDocumentQueryService.class);


    private final UserGuideDocumentRepository userGuideDocumentRepository;

    public UserGuideDocumentQueryService(UserGuideDocumentRepository userGuideDocumentRepository) {
        this.userGuideDocumentRepository = userGuideDocumentRepository;
    }

    /**
     * Return a {@link List} of {%link UserGuideDocument} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserGuideDocument> findByCriteria(UserGuideDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<UserGuideDocument> specification = createSpecification(criteria);
        return userGuideDocumentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link UserGuideDocument} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserGuideDocument> findByCriteria(UserGuideDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<UserGuideDocument> specification = createSpecification(criteria);
        return userGuideDocumentRepository.findAll(specification, page);
    }

    /**
     * Function to convert UserGuideDocumentCriteria to a {@link Specifications}
     */
    private Specifications<UserGuideDocument> createSpecification(UserGuideDocumentCriteria criteria) {
        Specifications<UserGuideDocument> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserGuideDocument_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), UserGuideDocument_.title));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), UserGuideDocument_.fileName));
            }
            if (criteria.getFullUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullUrl(), UserGuideDocument_.fullUrl));
            }
            if (criteria.getFullFolder() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullFolder(), UserGuideDocument_.fullFolder));
            }
            if (criteria.getBaseUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBaseUrl(), UserGuideDocument_.baseUrl));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), UserGuideDocument_.comments));
            }
//            if (criteria.getCreatedBy() != null) {
//                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), UserGuideDocument_.createdBy));
//            }
//            if (criteria.getCreatedDate() != null) {
//                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), UserGuideDocument_.createdDate));
//            }
//            if (criteria.getLastModifiedBy() != null) {
//                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), UserGuideDocument_.lastModifiedBy));
//            }
//            if (criteria.getLastModifiedDate() != null) {
//                specification = specification.and(buildStringSpecification(criteria.getLastModifiedDate(), UserGuideDocument_.lastModifiedDate));
//            }
        }
        return specification;
    }

}
