package com.pure.service.service.impl;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.Contract;
import com.pure.service.domain.CustomerConsumerLog;
import com.pure.service.domain.Student;
import com.pure.service.domain.StudentClassLog;
import com.pure.service.repository.ContractRepository;
import com.pure.service.repository.CustomerConsumerLogRepository;
import com.pure.service.repository.ProductRepository;
import com.pure.service.repository.StudentClassLogRepository;
import com.pure.service.service.ClassArrangementService;
import com.pure.service.service.ContractQueryService;
import com.pure.service.service.StudentClassLogQueryService;
import com.pure.service.service.StudentClassLogService;
import com.pure.service.service.dto.ContractCriteria;
import com.pure.service.service.dto.StudentClassLogCriteria;
import com.pure.service.service.dto.request.BatchSigninStudent;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import org.apache.commons.lang3.RandomStringUtils;
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

    @Autowired
    private CustomerConsumerLogRepository customerConsumerLogRepository;

    @Autowired
    private ContractQueryService contractQueryService;

    @Autowired
    private ContractRepository contractRepository;

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

        Long currentInstant = Instant.now().getEpochSecond();

        String uniqueNumber = "" + currentInstant + RandomStringUtils.randomNumeric(6);

        StudentClassLog studentClassLog = new StudentClassLog();
        studentClassLog.setActualTakenDate(Instant.now());
        studentClassLog.setStudent(student);
        studentClassLog.setArrangement(classArrangement);
        //流水号
        studentClassLog.setUniqueNumber(uniqueNumber);

        saveLogWithUniqueNumber(studentClassLog, uniqueNumber);
    }

    private void saveLogWithUniqueNumber(StudentClassLog studentClassLog, String uniqueNumber) {

        ContractCriteria contractCriteria = new ContractCriteria();

        LongFilter studentIdFilter = new LongFilter();
        studentIdFilter.setEquals(studentClassLog.getStudent().getId());

        contractCriteria.setStudentId(studentIdFilter);

        Instant now = Instant.now();

        InstantFilter startDateFilter = new InstantFilter();
        startDateFilter.setLessOrEqualThan(now);

        InstantFilter endDateFilter = new InstantFilter();
        endDateFilter.setGreaterOrEqualThan(now);

        contractCriteria.setStartDate(startDateFilter);
        contractCriteria.setEndDate(endDateFilter);

        List<Contract> contracts = contractQueryService.findByCriteria(contractCriteria);

        if (CollectionUtils.isEmpty(contracts)) {
            throw new RuntimeException("耗课时没找到学员" + studentClassLog.getStudent().getName() + "的合同信息，无法签到耗课。");
        }

        //TODO 多合同处理？
        Contract targetContract = contracts.get(0);
        Integer classTakenCount = targetContract.getHoursTaken();
        if (classTakenCount == null) {
            classTakenCount = 0;
        }
        Integer classCount = studentClassLog.getArrangement().getConsumeClassCount();

        classTakenCount = classTakenCount + classCount;
        targetContract.setHoursTaken(classTakenCount);
        contractRepository.save(targetContract);

        //保存耗课记录
        CustomerConsumerLog customerConsumerLog = new CustomerConsumerLog();



        customerConsumerLog = customerConsumerLog.consumerName(studentClassLog.getStudent().getName())
            .count(new Float(classCount))
            .unit("课时")
            .student(studentClassLog.getStudent())
            .uniqueNumber(uniqueNumber);

        customerConsumerLogRepository.save(customerConsumerLog);

        //保存签到记录
        studentClassLog.setUniqueNumber(uniqueNumber);
        save(studentClassLog);
    }
}
