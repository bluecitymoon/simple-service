package com.pure.service.service.impl;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.StudentFrozen;
import com.pure.service.domain.StudentFrozenArrangement;
import com.pure.service.region.RegionUtils;
import com.pure.service.repository.StudentFrozenArrangementRepository;
import com.pure.service.repository.StudentFrozenRepository;
import com.pure.service.service.StudentFrozenArrangementQueryService;
import com.pure.service.service.StudentFrozenArrangementService;
import com.pure.service.service.StudentFrozenService;
import com.pure.service.service.dto.StudentFrozenArrangementCriteria;
import com.pure.service.service.dto.request.StudentFrozenRequest;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * Service Implementation for managing StudentFrozen.
 */
@Service
@Transactional
public class StudentFrozenServiceImpl implements StudentFrozenService{

    private final Logger log = LoggerFactory.getLogger(StudentFrozenServiceImpl.class);

    private final StudentFrozenRepository studentFrozenRepository;

    @Autowired
    private StudentFrozenArrangementQueryService studentFrozenArrangementQueryService;

    @Autowired
    private StudentFrozenArrangementService studentFrozenArrangementService;

    @Autowired
    private StudentFrozenArrangementRepository arrangementRepository;

    public StudentFrozenServiceImpl(StudentFrozenRepository studentFrozenRepository) {
        this.studentFrozenRepository = studentFrozenRepository;
    }

    /**
     * Save a studentFrozen.
     *
     * @param studentFrozen the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentFrozen save(StudentFrozen studentFrozen) {
        log.debug("Request to save StudentFrozen : {}", studentFrozen);
        return studentFrozenRepository.save(studentFrozen);
    }

    /**
     *  Get all the studentFrozens.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentFrozen> findAll(Pageable pageable) {
        log.debug("Request to get all StudentFrozens");
        return studentFrozenRepository.findAll(pageable);
    }

    /**
     *  Get one studentFrozen by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentFrozen findOne(Long id) {
        log.debug("Request to get StudentFrozen : {}", id);
        return studentFrozenRepository.findOne(id);
    }

    /**
     *  Delete the  studentFrozen by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentFrozen : {}", id);

        StudentFrozenArrangementCriteria criteria = new StudentFrozenArrangementCriteria();
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);

        criteria.setStudentFrozenId(longFilter);

        List<StudentFrozenArrangement> arrangements = studentFrozenArrangementQueryService.findByCriteria(criteria);

        arrangementRepository.delete(arrangements);

        studentFrozenRepository.delete(id);
    }

    @Override
    public StudentFrozen generateStudentFrozen(StudentFrozenRequest request) {

        Long regionId = RegionUtils.getRegionIdForCurrentUser();

        StudentFrozen studentFrozen = new StudentFrozen();
        studentFrozen.setStudent(request.getStudent());
        studentFrozen.setStartDate(request.getStartDate());
        studentFrozen.setEndDate(request.getEndDate());
        studentFrozen.setRegionId(regionId);

        StudentFrozen savedFrozen = save(studentFrozen);

        List<ClassArrangement> frozenArrangements = request.getClassArrangements();
        for (ClassArrangement classArrangement : frozenArrangements) {

            StudentFrozenArrangementCriteria criteria = new StudentFrozenArrangementCriteria();
            LongFilter longFilter = new LongFilter();
            longFilter.setEquals(classArrangement.getId());

            criteria.setClassArrangementId(longFilter);

            List<StudentFrozenArrangement> existedFrozenArrangement = studentFrozenArrangementQueryService.findByCriteria(criteria);
            if (!CollectionUtils.isEmpty(existedFrozenArrangement)) {
                continue;
            }

            StudentFrozenArrangement frozenArrangement = new StudentFrozenArrangement();
            frozenArrangement.setActive(true);
            frozenArrangement.setClassArrangement(classArrangement);
            frozenArrangement.setStudent(request.getStudent());
            frozenArrangement.setRegionId(regionId);
            frozenArrangement.setStudentFrozen(savedFrozen);

            studentFrozenArrangementService.save(frozenArrangement);

        }

        return savedFrozen;
    }
}
