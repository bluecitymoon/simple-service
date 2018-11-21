package com.pure.service.service.impl;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.StudentFrozenArrangement;
import com.pure.service.repository.StudentFrozenArrangementRepository;
import com.pure.service.service.StudentFrozenArrangementQueryService;
import com.pure.service.service.StudentFrozenArrangementService;
import com.pure.service.service.dto.StudentFrozenArrangementCriteria;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing StudentFrozenArrangement.
 */
@Service
@Transactional
public class StudentFrozenArrangementServiceImpl implements StudentFrozenArrangementService{

    private final Logger log = LoggerFactory.getLogger(StudentFrozenArrangementServiceImpl.class);

    private final StudentFrozenArrangementRepository studentFrozenArrangementRepository;

    @Autowired
    private StudentFrozenArrangementQueryService arrangementQueryService;

    public StudentFrozenArrangementServiceImpl(StudentFrozenArrangementRepository studentFrozenArrangementRepository) {
        this.studentFrozenArrangementRepository = studentFrozenArrangementRepository;
    }

    /**
     * Save a studentFrozenArrangement.
     *
     * @param studentFrozenArrangement the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentFrozenArrangement save(StudentFrozenArrangement studentFrozenArrangement) {
        log.debug("Request to save StudentFrozenArrangement : {}", studentFrozenArrangement);
        return studentFrozenArrangementRepository.save(studentFrozenArrangement);
    }

    /**
     *  Get all the studentFrozenArrangements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentFrozenArrangement> findAll(Pageable pageable) {
        log.debug("Request to get all StudentFrozenArrangements");
        return studentFrozenArrangementRepository.findAll(pageable);
    }

    /**
     *  Get one studentFrozenArrangement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentFrozenArrangement findOne(Long id) {
        log.debug("Request to get StudentFrozenArrangement : {}", id);
        return studentFrozenArrangementRepository.findOne(id);
    }

    /**
     *  Delete the  studentFrozenArrangement by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentFrozenArrangement : {}", id);
        studentFrozenArrangementRepository.delete(id);
    }

    @Override
    public List<ClassArrangement> getStudentFrozenArrangements(Long frozenId) {

        StudentFrozenArrangementCriteria criteria = new StudentFrozenArrangementCriteria();
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(frozenId);
        criteria.setStudentFrozenId(longFilter);

        List<StudentFrozenArrangement> arrangements = arrangementQueryService.findByCriteria(criteria);
        if (CollectionUtils.isEmpty(arrangements)) return new ArrayList<>();

        return arrangements.stream().map(StudentFrozenArrangement::getClassArrangement).collect(Collectors.toList());
    }
}
