package com.pure.service.service.impl;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.Contract;
import com.pure.service.domain.CustomerConsumerLog;
import com.pure.service.domain.Product;
import com.pure.service.domain.Student;
import com.pure.service.domain.StudentAbsenceLog;
import com.pure.service.domain.StudentClassLog;
import com.pure.service.domain.StudentClassLogType;
import com.pure.service.domain.StudentFrozenArrangement;
import com.pure.service.domain.StudentLeave;
import com.pure.service.region.RegionUtils;
import com.pure.service.repository.ContractRepository;
import com.pure.service.repository.CustomerConsumerLogRepository;
import com.pure.service.repository.ProductRepository;
import com.pure.service.repository.StudentAbsenceLogRepository;
import com.pure.service.repository.StudentClassLogRepository;
import com.pure.service.repository.StudentClassLogTypeRepository;
import com.pure.service.repository.StudentRepository;
import com.pure.service.service.ClassArrangementService;
import com.pure.service.service.ContractQueryService;
import com.pure.service.service.StudentAbsenceLogQueryService;
import com.pure.service.service.StudentClassLogQueryService;
import com.pure.service.service.StudentClassLogService;
import com.pure.service.service.StudentFrozenArrangementQueryService;
import com.pure.service.service.StudentLeaveQueryService;
import com.pure.service.service.dto.ContractCriteria;
import com.pure.service.service.dto.StudentAbsenceLogCriteria;
import com.pure.service.service.dto.StudentClassLogCriteria;
import com.pure.service.service.dto.StudentFrozenArrangementCriteria;
import com.pure.service.service.dto.StudentLeaveCriteria;
import com.pure.service.service.dto.enumurations.StudentClassLogTypeEnum;
import com.pure.service.service.dto.request.BatchSigninStudent;
import com.pure.service.service.dto.request.SingleArrangementRequest;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


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

    @Autowired
    private StudentLeaveQueryService studentLeaveQueryService;

    @Autowired
    private StudentFrozenArrangementQueryService studentFrozenArrangementQueryService;

    @Autowired
    private StudentClassLogTypeRepository studentClassLogTypeRepository;

    @Autowired
    private StudentAbsenceLogQueryService studentAbsenceLogQueryService;

    @Autowired
    private StudentAbsenceLogRepository studentAbsenceLogRepository;

    @Autowired
    private StudentRepository studentRepository;

//
//    @Autowired
//    private CustomerConsumerLogRepository customerConsumerLogRepository;

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

        List<Student> students = request.getStudents();

        StudentClassLogType regular = studentClassLogTypeRepository.findByCode(StudentClassLogTypeEnum.RegularSign.name());
        for (Student student : students) {
            signInForSingleStudent(student, request.getArrangementIds(), regular);
        }

        List<Student> addedStudents = request.getAddedStudents();

        StudentClassLogType added = studentClassLogTypeRepository.findByCode(StudentClassLogTypeEnum.AddedSign.name());
        for (Student student : addedStudents) {

            signInForSingleStudent(student, request.getArrangementIds(), added);
        }

        List<Student> absentStudents = request.getAbsentStudents();
        for (Student absentStudent : absentStudents) {

            saveAbsenceLog(request, absentStudent);
        }
    }

    @Override
    public void fixDuplicateSignIssue() {

        List<StudentClassLog> logs = studentClassLogRepository.findAll();
        Map<StudentArrangement, List<StudentClassLog>> studentClassLogMap = new HashMap<>();

        for (StudentClassLog studentClassLog : logs) {

            StudentArrangement studentArrangement = new StudentArrangement();
            studentArrangement.setArrangementId(studentClassLog.getArrangement().getId());
            studentArrangement.setStudentId(studentClassLog.getStudent().getId());

            List<StudentClassLog> studentClassLogs = studentClassLogMap.get(studentArrangement);
            if (studentClassLogs == null) {
                studentClassLogs = new ArrayList<>();

                studentClassLogMap.put(studentArrangement, studentClassLogs);
            }

            studentClassLogs.add(studentClassLog);
        }

        for (List<StudentClassLog> studentClassLogs : studentClassLogMap.values()) {

            if (studentClassLogs.size() < 2)  continue;

            for (int i = 0; i < studentClassLogs.size(); i++) {

                if (i == 0) continue;
                StudentClassLog toDeletedLog = studentClassLogs.get(i);

                rollbackStudentClassLog(toDeletedLog);
            }
        }
    }



    private void saveAbsenceLog(BatchSigninStudent request, Student absentStudent) {
        for (SingleArrangementRequest arrangementId : request.getArrangementIds()) {

            StudentAbsenceLogCriteria criteria = new StudentAbsenceLogCriteria();

            LongFilter studentFilter = new LongFilter();
            studentFilter.setEquals(absentStudent.getId());

            LongFilter arrangementIdFilter = new LongFilter();
            arrangementIdFilter.setEquals(arrangementId.getArrangementId());

            criteria.setStudentId(studentFilter);
            criteria.setClassArrangementId(arrangementIdFilter);

            List<StudentAbsenceLog> existedlogs = studentAbsenceLogQueryService.findByCriteria(criteria);
            if (CollectionUtils.isEmpty(existedlogs)) {

                ClassArrangement classArrangement = classArrangementService.findOne(arrangementId.getArrangementId());
                StudentAbsenceLog log = new StudentAbsenceLog();
                log.setClassArrangement(classArrangement);
                log.setStudent(absentStudent);
                log.setClassCount(classArrangement.getConsumeClassCount());

                RegionUtils.setRegionAbstractAuditingRegionEntity(log);

                studentAbsenceLogRepository.save(log);

            }
        }
    }

    private void signInForSingleStudent(Student student, List<SingleArrangementRequest> arrangementIds, StudentClassLogType type) {

        for (SingleArrangementRequest arrangementId : arrangementIds) {

            LongFilter studentFilter = new LongFilter();
            studentFilter.setEquals(student.getId());

            LongFilter arrangementIdFilter = new LongFilter();
            arrangementIdFilter.setEquals(arrangementId.getArrangementId());

            //检查这节课是不是签到过了
            StudentClassLogCriteria criteria = new StudentClassLogCriteria();
            criteria.setStudentId(studentFilter);
            criteria.setArrangementId(arrangementIdFilter);

            List<StudentClassLog> logs = logQueryService.findByCriteria(criteria);

            if (!CollectionUtils.isEmpty(logs)) {
                continue;
            }

            //检查是不是请假
            StudentLeaveCriteria studentLeaveCriteria = new StudentLeaveCriteria();
            studentLeaveCriteria.setStudentId(studentFilter);
            studentLeaveCriteria.setClassArrangementId(arrangementIdFilter);

            List<StudentLeave> studentLeaves = studentLeaveQueryService.findByCriteria(studentLeaveCriteria);
            //请过假的不能签到
            if (!CollectionUtils.isEmpty(studentLeaves)) {
                continue;
            }

            StudentFrozenArrangementCriteria studentFrozenArrangementCriteria = new StudentFrozenArrangementCriteria();
            studentFrozenArrangementCriteria.setClassArrangementId(arrangementIdFilter);
            studentFrozenArrangementCriteria.setStudentId(studentFilter);

            List<StudentFrozenArrangement> arrangements = studentFrozenArrangementQueryService.findByCriteria(studentFrozenArrangementCriteria);
            //冻结的不能签到
            if (!CollectionUtils.isEmpty(arrangements)) {
                continue;
            }

            ClassArrangement classArrangement = classArrangementService.findOne(arrangementId.getArrangementId());
            classArrangement.setActualTeacher(arrangementId.getActualTeacher());

            classArrangementService.save(classArrangement);

            Long currentInstant = Instant.now().getEpochSecond();

            String uniqueNumber = "" + currentInstant + RandomStringUtils.randomNumeric(6);

            StudentClassLog studentClassLog = new StudentClassLog();
            studentClassLog.setActualTakenDate(Instant.now());
            studentClassLog.setStudent(student);
            studentClassLog.setArrangement(classArrangement);
            studentClassLog.setType(type);
            //流水号
            studentClassLog.setUniqueNumber(uniqueNumber);

            RegionUtils.setRegionAbstractAuditingRegionEntity(studentClassLog);
            saveLogWithUniqueNumber(studentClassLog, uniqueNumber);
        }

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

        RegionUtils.setRegionAbstractAuditingRegionEntity(customerConsumerLog);

        customerConsumerLogRepository.save(customerConsumerLog);

        //保存签到记录
        studentClassLog.setUniqueNumber(uniqueNumber);
        save(studentClassLog);
    }

    @Override
    public void rollbackStudentClassLog(StudentClassLog studentClassLog) {

        String uniqueNumber = studentClassLog.getUniqueNumber();



        ContractCriteria contractCriteria = new ContractCriteria();

        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(studentClassLog.getStudent().getId());

        contractCriteria.setStudentId(longFilter);

        Contract contract = null;

        List<Contract> contracts = contractQueryService.findByCriteria(contractCriteria);

        if (CollectionUtils.isEmpty(contracts)) {

            return;
        }

        if (contracts.size() == 1) {
            contract = contracts.get(0);
        } else {

            for (Contract singleContract : contracts) {
                Product clazz = singleContract.getProduct();

                if (studentClassLog.getArrangement().getClazz().equals(clazz)) {
                    contract = singleContract;
                }
            }

            if (contract == null) {
                contract = contracts.get(0);
            }
        }

//        CustomerConsumerLog customerConsumerLog = customerConsumerLogRepository.findByUniqueNumber(uniqueNumber);
//        if (customerConsumerLog == null) {
//        Float count = customerConsumerLog.getCount();

        contract.setHoursTaken(contract.getHoursTaken() - studentClassLog.getArrangement().getConsumeClassCount());

        contractRepository.save(contract);

        //TODO
//        customerConsumerLogRepository.delete(customerConsumerLog);

        studentClassLogRepository.delete(studentClassLog);

    }

    private class StudentArrangement {
        private Long studentId;
        private Long arrangementId;

        public Long getStudentId() {
            return studentId;
        }

        public void setStudentId(Long studentId) {
            this.studentId = studentId;
        }

        public Long getArrangementId() {
            return arrangementId;
        }

        public void setArrangementId(Long arrangementId) {
            this.arrangementId = arrangementId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StudentArrangement)) return false;
            StudentArrangement that = (StudentArrangement) o;
            return Objects.equals(getStudentId(), that.getStudentId()) &&
                Objects.equals(getArrangementId(), that.getArrangementId());
        }

        @Override
        public int hashCode() {

            return Objects.hash(getStudentId(), getArrangementId());
        }
    }
}
