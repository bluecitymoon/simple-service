package com.pure.service.service.impl;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.Student;
import com.pure.service.domain.StudentClassLog;
import com.pure.service.repository.ProductRepository;
import com.pure.service.repository.StudentClassLogRepository;
import com.pure.service.service.ClassArrangementService;
import com.pure.service.service.StudentClassLogQueryService;
import com.pure.service.service.StudentClassLogService;
import com.pure.service.service.dto.StudentClassLogCriteria;
import com.pure.service.service.dto.request.BatchSigninStudent;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;


/**
 * Service Implementation for managing StudentClassLog.
 */
@Service
@Transactional
public class StudentClassLogServiceImpl implements StudentClassLogService{

    private final Logger log = LoggerFactory.getLogger(StudentClassLogServiceImpl.class);

    private final StudentClassLogRepository studentClassLogRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StudentClassLogQueryService logQueryService;

    @Autowired
    private ClassArrangementService classArrangementService;

    public StudentClassLogServiceImpl(StudentClassLogRepository studentClassLogRepository) {
        this.studentClassLogRepository = studentClassLogRepository;
    }

    /**
     * Save a studentClassLog.
     *
     * @param studentClassLog the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentClassLog save(StudentClassLog studentClassLog) {
        log.debug("Request to save StudentClassLog : {}", studentClassLog);
        return studentClassLogRepository.save(studentClassLog);
    }

    /**
     *  Get all the studentClassLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentClassLog> findAll(Pageable pageable) {
        log.debug("Request to get all StudentClassLogs");
        return studentClassLogRepository.findAll(pageable);
    }

    /**
     *  Get one studentClassLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentClassLog findOne(Long id) {
        log.debug("Request to get StudentClassLog : {}", id);
        return studentClassLogRepository.findOne(id);
    }

    /**
     *  Delete the  studentClassLog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentClassLog : {}", id);
        studentClassLogRepository.delete(id);
    }

    @Override
    public void batchSignIn(BatchSigninStudent request) {

//        Product clazz = productRepository.findOne(request.getClassId());

        for (Student student : request.getStudents()) {

            signInForSingleStudent(student, request.getArrangementId());
        }
    }

    private void signInForSingleStudent(Student student, Long arrangementId) {

        LongFilter studentFilter = new LongFilter();
        studentFilter.setEquals(student.getId());

        LongFilter arrangementIdFilter = new LongFilter();
        arrangementIdFilter.setEquals(arrangementId);

        StudentClassLogCriteria criteria = new StudentClassLogCriteria();
        criteria.setStudentId(studentFilter);
        criteria.setArrangementId(arrangementIdFilter);

        List<StudentClassLog> logs = logQueryService.findByCriteria(criteria);

        if (!CollectionUtils.isEmpty(logs)) {
            return;
        }

        ClassArrangement classArrangement = classArrangementService.findOne(arrangementId);
        StudentClassLog studentClassLog = new StudentClassLog();
        studentClassLog.setActualTakenDate(Instant.now());
        studentClassLog.setStudent(student);
        studentClassLog.setArrangement(classArrangement);

        save(studentClassLog);
    }
}
