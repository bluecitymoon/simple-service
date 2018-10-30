package com.pure.service.service;


import com.pure.service.domain.AuthorityUserGuideDocument;
import com.pure.service.domain.AuthorityUserGuideDocument_;
import com.pure.service.domain.Authority_;
import com.pure.service.domain.UserGuideDocument_;
import com.pure.service.repository.AuthorityUserGuideDocumentRepository;
import com.pure.service.service.dto.AuthorityUserGuideDocumentCriteria;
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
 * Service for executing complex queries for AuthorityUserGuideDocument entities in the database.
 * The main input is a {@link AuthorityUserGuideDocumentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link AuthorityUserGuideDocument} or a {@link Page} of {%link AuthorityUserGuideDocument} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class AuthorityUserGuideDocumentQueryService extends QueryService<AuthorityUserGuideDocument> {

    private final Logger log = LoggerFactory.getLogger(AuthorityUserGuideDocumentQueryService.class);


    private final AuthorityUserGuideDocumentRepository authorityUserGuideDocumentRepository;

    public AuthorityUserGuideDocumentQueryService(AuthorityUserGuideDocumentRepository authorityUserGuideDocumentRepository) {
        this.authorityUserGuideDocumentRepository = authorityUserGuideDocumentRepository;
    }

    /**
     * Return a {@link List} of {%link AuthorityUserGuideDocument} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AuthorityUserGuideDocument> findByCriteria(AuthorityUserGuideDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<AuthorityUserGuideDocument> specification = createSpecification(criteria);
        return authorityUserGuideDocumentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link AuthorityUserGuideDocument} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AuthorityUserGuideDocument> findByCriteria(AuthorityUserGuideDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<AuthorityUserGuideDocument> specification = createSpecification(criteria);
        return authorityUserGuideDocumentRepository.findAll(specification, page);
    }

    /**
     * Function to convert AuthorityUserGuideDocumentCriteria to a {@link Specifications}
     */
    private Specifications<AuthorityUserGuideDocument> createSpecification(AuthorityUserGuideDocumentCriteria criteria) {
        Specifications<AuthorityUserGuideDocument> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AuthorityUserGuideDocument_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), AuthorityUserGuideDocument_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), AuthorityUserGuideDocument_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), AuthorityUserGuideDocument_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), AuthorityUserGuideDocument_.lastModifiedDate));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), AuthorityUserGuideDocument_.comments));
            }
            if (criteria.getAuthorityId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAuthorityId(), AuthorityUserGuideDocument_.authority, Authority_.id));
            }
            if (criteria.getUserGuideDocumentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserGuideDocumentId(), AuthorityUserGuideDocument_.userGuideDocument, UserGuideDocument_.id));
            }
        }
        return specification;
    }

}
