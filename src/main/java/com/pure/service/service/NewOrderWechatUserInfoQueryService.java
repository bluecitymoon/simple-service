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

import com.pure.service.domain.NewOrderWechatUserInfo;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.NewOrderWechatUserInfoRepository;
import com.pure.service.service.dto.NewOrderWechatUserInfoCriteria;


/**
 * Service for executing complex queries for NewOrderWechatUserInfo entities in the database.
 * The main input is a {@link NewOrderWechatUserInfoCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link NewOrderWechatUserInfo} or a {@link Page} of {%link NewOrderWechatUserInfo} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class NewOrderWechatUserInfoQueryService extends QueryService<NewOrderWechatUserInfo> {

    private final Logger log = LoggerFactory.getLogger(NewOrderWechatUserInfoQueryService.class);


    private final NewOrderWechatUserInfoRepository newOrderWechatUserInfoRepository;

    public NewOrderWechatUserInfoQueryService(NewOrderWechatUserInfoRepository newOrderWechatUserInfoRepository) {
        this.newOrderWechatUserInfoRepository = newOrderWechatUserInfoRepository;
    }

    /**
     * Return a {@link List} of {%link NewOrderWechatUserInfo} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NewOrderWechatUserInfo> findByCriteria(NewOrderWechatUserInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<NewOrderWechatUserInfo> specification = createSpecification(criteria);
        return newOrderWechatUserInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link NewOrderWechatUserInfo} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NewOrderWechatUserInfo> findByCriteria(NewOrderWechatUserInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<NewOrderWechatUserInfo> specification = createSpecification(criteria);
        return newOrderWechatUserInfoRepository.findAll(specification, page);
    }

    /**
     * Function to convert NewOrderWechatUserInfoCriteria to a {@link Specifications}
     */
    private Specifications<NewOrderWechatUserInfo> createSpecification(NewOrderWechatUserInfoCriteria criteria) {
        Specifications<NewOrderWechatUserInfo> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NewOrderWechatUserInfo_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), NewOrderWechatUserInfo_.code));
            }
            if (criteria.getEncryptedData() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEncryptedData(), NewOrderWechatUserInfo_.encryptedData));
            }
            if (criteria.getIv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIv(), NewOrderWechatUserInfo_.iv));
            }
            if (criteria.getNickName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNickName(), NewOrderWechatUserInfo_.nickName));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGender(), NewOrderWechatUserInfo_.gender));
            }
            if (criteria.getLanguage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguage(), NewOrderWechatUserInfo_.language));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), NewOrderWechatUserInfo_.city));
            }
            if (criteria.getProvince() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProvince(), NewOrderWechatUserInfo_.province));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), NewOrderWechatUserInfo_.country));
            }
            if (criteria.getAvatarUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAvatarUrl(), NewOrderWechatUserInfo_.avatarUrl));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), NewOrderWechatUserInfo_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), NewOrderWechatUserInfo_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), NewOrderWechatUserInfo_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), NewOrderWechatUserInfo_.lastModifiedDate));
            }
            if (criteria.getOpenId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOpenId(), NewOrderWechatUserInfo_.openId));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), NewOrderWechatUserInfo_.comments));
            }
        }
        return specification;
    }

}
