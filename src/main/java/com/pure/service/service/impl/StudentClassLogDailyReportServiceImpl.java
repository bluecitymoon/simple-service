package com.pure.service.service.impl;

import com.pure.service.domain.Student;
import com.pure.service.domain.StudentAbsenceLog;
import com.pure.service.domain.StudentClassLog;
import com.pure.service.domain.StudentClassLogDailyReport;
import com.pure.service.domain.StudentLeave;
import com.pure.service.region.RegionUtils;
import com.pure.service.repository.ClassArrangementRepository;
import com.pure.service.repository.StudentClassLogDailyReportRepository;
import com.pure.service.service.StudentAbsenceLogQueryService;
import com.pure.service.service.StudentClassLogDailyReportService;
import com.pure.service.service.StudentClassLogQueryService;
import com.pure.service.service.StudentClassService;
import com.pure.service.service.StudentLeaveQueryService;
import com.pure.service.service.dto.StudentAbsenceLogCriteria;
import com.pure.service.service.dto.StudentClassLogCriteria;
import com.pure.service.service.dto.StudentLeaveCriteria;
import com.pure.service.service.dto.dto.ClassSchedule;
import com.pure.service.service.dto.dto.StatusBasedStudent;
import com.pure.service.service.dto.enumurations.StudentClassLogTypeEnum;
import com.pure.service.service.util.DateUtil;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing StudentClassLogDailyReport.
 */
@Service
@Transactional
public class StudentClassLogDailyReportServiceImpl implements StudentClassLogDailyReportService{

    private final Logger log = LoggerFactory.getLogger(StudentClassLogDailyReportServiceImpl.class);

    private final StudentClassLogDailyReportRepository studentClassLogDailyReportRepository;

    @Autowired
    private ClassArrangementRepository classArrangementRepository;

    @Autowired
    private StudentClassService studentClassService;

    @Autowired
    private StudentLeaveQueryService studentLeaveQueryService;

    @Autowired
    private StudentAbsenceLogQueryService studentAbsenceLogQueryService;

    @Autowired
    private StudentClassLogQueryService studentClassLogQueryService;

    public StudentClassLogDailyReportServiceImpl(StudentClassLogDailyReportRepository studentClassLogDailyReportRepository) {
        this.studentClassLogDailyReportRepository = studentClassLogDailyReportRepository;
    }

    /**
     * Save a studentClassLogDailyReport.
     *
     * @param studentClassLogDailyReport the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentClassLogDailyReport save(StudentClassLogDailyReport studentClassLogDailyReport) {
        log.debug("Request to save StudentClassLogDailyReport : {}", studentClassLogDailyReport);
        return studentClassLogDailyReportRepository.save(studentClassLogDailyReport);
    }

    /**
     *  Get all the studentClassLogDailyReports.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentClassLogDailyReport> findAll(Pageable pageable) {
        log.debug("Request to get all StudentClassLogDailyReports");
        return studentClassLogDailyReportRepository.findAll(pageable);
    }

    /**
     *  Get one studentClassLogDailyReport by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentClassLogDailyReport findOne(Long id) {
        log.debug("Request to get StudentClassLogDailyReport : {}", id);
        return studentClassLogDailyReportRepository.findOne(id);
    }

    /**
     *  Delete the  studentClassLogDailyReport by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentClassLogDailyReport : {}", id);
        studentClassLogDailyReportRepository.delete(id);
    }

    @Override
    public StatusBasedStudent getStudentClassLogDailyReportToday() {

        StatusBasedStudent statusBasedStudent = new StatusBasedStudent();

        Long regionId = RegionUtils.getRegionIdForCurrentUser();
        Instant todayStart = DateUtil.getSimpleTodayInstantBegin();
        Instant todayEnd = DateUtil.getSimpleTodayInstantEnd();

        List<ClassSchedule> todaySchedules = classArrangementRepository.getAllSchedulesByRange(todayStart, todayEnd, regionId);

        Set<Student> shouldTakenStudents = new HashSet<>();
        Set<Student> askedLeaveStudents = new HashSet<>();
        Set<Student> absentStudents = new HashSet<>();
        Set<Student> addedStudents = new HashSet<>();
        Set<Student> actualStudents = new HashSet<>();

        StudentLeaveCriteria studentLeaveCriteria = new StudentLeaveCriteria();
        StudentAbsenceLogCriteria studentAbsenceLogCriteria = new StudentAbsenceLogCriteria();

        StudentClassLogCriteria studentClassLogCriteria = new StudentClassLogCriteria();
        for (ClassSchedule todaySchedule : todaySchedules) {
            Long classId = todaySchedule.getClassId();

            List<Student> students = studentClassService.findStudentsInClass(classId);
            shouldTakenStudents.addAll(students);

            LongFilter classArrangementId = new LongFilter();
            classArrangementId.setEquals(todaySchedule.getArrangementId());

            studentLeaveCriteria.setClassArrangementId(classArrangementId);

            List<StudentLeave> studentLeaves = studentLeaveQueryService.findByCriteria(studentLeaveCriteria);

            List<Student> askedLeaveStudentsList = studentLeaves.stream().map(StudentLeave::getStudent).collect(Collectors.toList());
            askedLeaveStudents.addAll(askedLeaveStudentsList);

            studentAbsenceLogCriteria.setClassArrangementId(classArrangementId);
            List<StudentAbsenceLog> studentAbsenceLogs = studentAbsenceLogQueryService.findByCriteria(studentAbsenceLogCriteria);

            absentStudents.addAll(studentAbsenceLogs.stream().map(StudentAbsenceLog::getStudent).collect(Collectors.toList()));

            studentClassLogCriteria.setArrangementId(classArrangementId);
            List<StudentClassLog> studentClassLogs = studentClassLogQueryService.findByCriteria(studentClassLogCriteria);

            List<Student> addedStudentsList = studentClassLogs.stream().filter(studentClassLog -> studentClassLog.getType().getName().equals(StudentClassLogTypeEnum.AddedSign.name())).map(StudentClassLog::getStudent).collect(Collectors.toList());
            addedStudents.addAll(addedStudentsList);

            List<Student> actualStudentList = studentClassLogs.stream().map(StudentClassLog::getStudent).collect(Collectors.toList());
            actualStudents.addAll(actualStudentList);
        }

        statusBasedStudent.setShouldTakenStudents(new ArrayList<>(shouldTakenStudents));
        statusBasedStudent.setAskedLeaveStudents(new ArrayList<>(askedLeaveStudents));
        statusBasedStudent.setAbsentStudents(new ArrayList<>(absentStudents));
        statusBasedStudent.setAddedStudents(new ArrayList<>(addedStudents));
        statusBasedStudent.setActualTakenStudents(new ArrayList<>(actualStudents));
        statusBasedStudent.setLogDate(Instant.now());


        return statusBasedStudent;
    }
}
