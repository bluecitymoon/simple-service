package com.pure.service.service.impl;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.Product;
import com.pure.service.domain.Student;
import com.pure.service.region.RegionIdStorage;
import com.pure.service.service.StudentLeaveQueryService;
import com.pure.service.service.StudentLeaveService;
import com.pure.service.domain.StudentLeave;
import com.pure.service.repository.StudentLeaveRepository;
import com.pure.service.service.dto.StudentLeaveCriteria;
import com.pure.service.service.dto.dto.StudentLeaveRequest;
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


/**
 * Service Implementation for managing StudentLeave.
 */
@Service
@Transactional
public class StudentLeaveServiceImpl implements StudentLeaveService{

    private final Logger log = LoggerFactory.getLogger(StudentLeaveServiceImpl.class);

    private final StudentLeaveRepository studentLeaveRepository;

    @Autowired
    private StudentLeaveQueryService studentLeaveQueryService;

    public StudentLeaveServiceImpl(StudentLeaveRepository studentLeaveRepository) {
        this.studentLeaveRepository = studentLeaveRepository;
    }

    /**
     * Save a studentLeave.
     *
     * @param studentLeave the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentLeave save(StudentLeave studentLeave) {
        log.debug("Request to save StudentLeave : {}", studentLeave);

        Product leavedClass = studentLeave.getClassArrangement().getClazz();

        return studentLeaveRepository.save(studentLeave);
    }

    /**
     *  Get all the studentLeaves.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentLeave> findAll(Pageable pageable) {
        log.debug("Request to get all StudentLeaves");
        return studentLeaveRepository.findAll(pageable);
    }

    /**
     *  Get one studentLeave by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentLeave findOne(Long id) {
        log.debug("Request to get StudentLeave : {}", id);
        return studentLeaveRepository.findOne(id);
    }

    /**
     *  Delete the  studentLeave by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentLeave : {}", id);
        studentLeaveRepository.delete(id);
    }

    @Override
    public List<StudentLeave> createBatchStudentLeave(StudentLeaveRequest studentLeaveRequest) {

        List<StudentLeave> leaves = new ArrayList<>();

        Long regionId = Long.valueOf(RegionIdStorage.getRegionIdContext());

        for (Student student : studentLeaveRequest.getStudents()) {

            for (ClassArrangement classArrangement : studentLeaveRequest.getArrangements()) {

                StudentLeaveCriteria leaveCriteria = new StudentLeaveCriteria();
                LongFilter studentFilter = new LongFilter();
                studentFilter.setEquals(student.getId());

                LongFilter arrangementFilter = new LongFilter();
                arrangementFilter.setEquals(classArrangement.getId());

                leaveCriteria.setStudentId(studentFilter);
                leaveCriteria.setClassArrangementId(arrangementFilter);

                List<StudentLeave> leaveList = studentLeaveQueryService.findByCriteria(leaveCriteria);

                if (CollectionUtils.isEmpty(leaveList)) {

                    StudentLeave studentLeave = new StudentLeave();
                    studentLeave.setClassArrangement(classArrangement);
                    studentLeave.setStudent(student);
                    studentLeave.setRegionId(regionId);
                    //TODO add leave limit

                    leaves.add(studentLeave);
                }
            }
        }

        return studentLeaveRepository.save(leaves);
    }
}
