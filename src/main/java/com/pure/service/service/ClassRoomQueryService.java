package com.pure.service.service;


import com.pure.service.domain.ClassRoom;
import com.pure.service.domain.ClassRoom_;
import com.pure.service.repository.ClassRoomRepository;
import com.pure.service.service.dto.ClassRoomCriteria;
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
 * Service for executing complex queries for ClassRoom entities in the database.
 * The main input is a {@link ClassRoomCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ClassRoom} or a {@link Page} of {%link ClassRoom} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ClassRoomQueryService extends QueryService<ClassRoom> {

    private final Logger log = LoggerFactory.getLogger(ClassRoomQueryService.class);


    private final ClassRoomRepository classRoomRepository;

    public ClassRoomQueryService(ClassRoomRepository classRoomRepository) {
        this.classRoomRepository = classRoomRepository;
    }

    /**
     * Return a {@link List} of {%link ClassRoom} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassRoom> findByCriteria(ClassRoomCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ClassRoom> specification = createSpecification(criteria);
        return classRoomRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ClassRoom} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassRoom> findByCriteria(ClassRoomCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ClassRoom> specification = createSpecification(criteria);
        return classRoomRepository.findAll(specification, page);
    }

    /**
     * Function to convert ClassRoomCriteria to a {@link Specifications}
     */
    private Specifications<ClassRoom> createSpecification(ClassRoomCriteria criteria) {
        Specifications<ClassRoom> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), ClassRoom_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClassRoom_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ClassRoom_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ClassRoom_.code));
            }
            if (criteria.getRoomNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoomNumber(), ClassRoom_.roomNumber));
            }
            if (criteria.getAcreage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAcreage(), ClassRoom_.acreage));
            }
            if (criteria.getMaxStudentCapacity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxStudentCapacity(), ClassRoom_.maxStudentCapacity));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), ClassRoom_.comments));
            }
            if (criteria.getAvaliable() != null) {
                specification = specification.and(buildSpecification(criteria.getAvaliable(), ClassRoom_.avaliable));
            }
        }
        return specification;
    }

}
