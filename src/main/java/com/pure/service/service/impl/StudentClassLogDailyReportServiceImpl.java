package com.pure.service.service.impl;

import com.pure.service.domain.Student;
import com.pure.service.domain.StudentAbsenceLog;
import com.pure.service.domain.StudentClassLog;
import com.pure.service.domain.StudentClassLogDailyReport;
import com.pure.service.domain.StudentFrozenArrangement;
import com.pure.service.domain.StudentLeave;
import com.pure.service.region.RegionUtils;
import com.pure.service.repository.ClassArrangementRepository;
import com.pure.service.repository.StudentClassLogDailyReportRepository;
import com.pure.service.service.StudentAbsenceLogQueryService;
import com.pure.service.service.StudentClassLogDailyReportQueryService;
import com.pure.service.service.StudentClassLogDailyReportService;
import com.pure.service.service.StudentClassLogQueryService;
import com.pure.service.service.StudentClassService;
import com.pure.service.service.StudentFrozenArrangementQueryService;
import com.pure.service.service.StudentLeaveQueryService;
import com.pure.service.service.dto.StudentAbsenceLogCriteria;
import com.pure.service.service.dto.StudentClassLogCriteria;
import com.pure.service.service.dto.StudentClassLogDailyReportCriteria;
import com.pure.service.service.dto.StudentFrozenArrangementCriteria;
import com.pure.service.service.dto.StudentLeaveCriteria;
import com.pure.service.service.dto.dto.ClassSchedule;
import com.pure.service.service.dto.dto.StatusBasedStudent;
import com.pure.service.service.dto.dto.StudentClassLogMonthlyReport;
import com.pure.service.service.dto.enumurations.StudentClassLogTypeEnum;
import com.pure.service.service.dto.request.CustomerStatusRequest;
import com.pure.service.service.util.DateUtil;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private StudentClassLogDailyReportQueryService studentClassLogDailyReportQueryService;

    @Autowired
    private StudentFrozenArrangementQueryService studentFrozenArrangementQueryService;

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
    public StatusBasedStudent getStudentClassLogDailyReportToday(Instant logDate) {

        StatusBasedStudent statusBasedStudent = new StatusBasedStudent();

        Long regionId = RegionUtils.getRegionIdForCurrentUser();
        Instant todayStart = DateUtil.getBeginningOfInstant(logDate);
        Instant todayEnd = DateUtil.getEndingOfInstant(logDate);

        List<ClassSchedule> todaySchedules = classArrangementRepository.getAllSchedulesByRange(todayStart, todayEnd, regionId);

        Set<Student> shouldTakenStudents = new HashSet<>();
        Set<Student> askedLeaveStudents = new HashSet<>();
        Set<Student> absentStudents = new HashSet<>();
        Set<Student> addedStudents = new HashSet<>();
        Set<Student> actualStudents = new HashSet<>();
        Set<Student> frozenStudents = new HashSet<>();

        StudentLeaveCriteria studentLeaveCriteria = new StudentLeaveCriteria();
        StudentAbsenceLogCriteria studentAbsenceLogCriteria = new StudentAbsenceLogCriteria();
        StudentClassLogCriteria studentClassLogCriteria = new StudentClassLogCriteria();
        StudentFrozenArrangementCriteria studentFrozenArrangementCriteria = new StudentFrozenArrangementCriteria();

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

            studentFrozenArrangementCriteria.setClassArrangementId(classArrangementId);
            List<Student> studentFrozenArrangements = studentFrozenArrangementQueryService.findByCriteria(studentFrozenArrangementCriteria).stream().map(StudentFrozenArrangement::getStudent).collect(Collectors.toList());

            frozenStudents.addAll(studentFrozenArrangements);
        }

        statusBasedStudent.setShouldTakenStudents(new ArrayList<>(shouldTakenStudents));
        statusBasedStudent.setAskedLeaveStudents(new ArrayList<>(askedLeaveStudents));
        statusBasedStudent.setAbsentStudents(new ArrayList<>(absentStudents));
        statusBasedStudent.setAddedStudents(new ArrayList<>(addedStudents));
        statusBasedStudent.setActualTakenStudents(new ArrayList<>(actualStudents));
        statusBasedStudent.setFrozenStudents(new ArrayList<>(frozenStudents));
        statusBasedStudent.setLogDate(logDate);


        return statusBasedStudent;
    }

    @Override
    public StudentClassLogDailyReport saveLogDailyReport(StudentClassLogDailyReport dailyReport) {

        Instant logDate = dailyReport.getLogDate();

        Instant logDateStart = DateUtil.getBeginningOfInstant(logDate);
        Instant logDateEnd = DateUtil.getEndingOfInstant(logDate);

        StudentClassLogDailyReportCriteria classLogDailyReportCriteria = new StudentClassLogDailyReportCriteria();
        InstantFilter instantFilter = new InstantFilter();
        instantFilter.setGreaterOrEqualThan(logDateStart);
        instantFilter.setLessOrEqualThan(logDateEnd);

        classLogDailyReportCriteria.setLogDate(instantFilter);

        List<StudentClassLogDailyReport> existedReports = studentClassLogDailyReportQueryService.findByCriteria(classLogDailyReportCriteria);

        if (CollectionUtils.isEmpty(existedReports)) {
            return save(dailyReport);
        }

        StudentClassLogDailyReport existedReport = existedReports.get(0);
        BeanUtils.copyProperties(dailyReport, existedReport, "id");


        return save(existedReport);
    }

    @Override
    public List<StudentClassLogMonthlyReport> getMonthlyReport(CustomerStatusRequest customerStatusRequest) {

        List<StudentClassLogMonthlyReport> result = new ArrayList<>();

        Map<String, List<StudentClassLogDailyReport>> mergedReportMap = new HashMap<>();
        StudentClassLogDailyReportCriteria reportCriteria = new StudentClassLogDailyReportCriteria();

        InstantFilter instantFilter = new InstantFilter();
        instantFilter.setGreaterThan(customerStatusRequest.getStartDate());
        instantFilter.setLessThan(customerStatusRequest.getEndDate());

        reportCriteria.setLogDate(instantFilter);

        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(RegionUtils.getRegionIdForCurrentUser());

        reportCriteria.setRegionId(longFilter);

        List<StudentClassLogDailyReport> studentClassLogDailyReports = studentClassLogDailyReportQueryService.findByCriteria(reportCriteria);

        if (CollectionUtils.isEmpty(studentClassLogDailyReports)) return result;

        for (StudentClassLogDailyReport studentClassLogDailyReport : studentClassLogDailyReports) {

            Instant logDate = studentClassLogDailyReport.getLogDate();

            LocalDateTime localDateTime = DateUtil.instantToLocalDateTime(logDate);
            Integer month = localDateTime.getMonthValue();
            Integer year = localDateTime.getYear();

            String monthYear = "" + year + "-" + month;
            List<StudentClassLogDailyReport> logs = mergedReportMap.get(monthYear);
            if (logs == null) {

                logs = new ArrayList<>();
                logs.add(studentClassLogDailyReport);

                mergedReportMap.put(monthYear, logs);

                continue;
            }

            logs.add(studentClassLogDailyReport);
        }

        for (Map.Entry<String, List<StudentClassLogDailyReport>> logListEntry : mergedReportMap.entrySet()) {

            StudentClassLogMonthlyReport monthlyReport = new StudentClassLogMonthlyReport();

            String monthYear = logListEntry.getKey();
            List<StudentClassLogDailyReport> reports = logListEntry.getValue();

            int shouldTaken = 0,leave = 0, absence = 0, added = 0, actualTaken = 0, frozen = 0;
            for (StudentClassLogDailyReport report : reports) {

                shouldTaken += report.getShouldTaken();
                leave += report.getLeave();
                absence += report.getAbsence();
                added += report.getAdded();
                actualTaken += report.getActualTaken();
                frozen += report.getFrozen();
            }

            Comparator<StudentClassLogDailyReport> logDailyReportComparator = (StudentClassLogDailyReport o1, StudentClassLogDailyReport o2) -> {
                if (o1.getLogDate().isBefore(o2.getLogDate())) {
                    return 1;
                } else {
                    return -1;
                }
            };

            Collections.sort(reports, logDailyReportComparator);

            monthlyReport.setYearMonth(monthYear);
            monthlyReport.setAbsence(absence);
            monthlyReport.setActualTaken(actualTaken);
            monthlyReport.setAdded(added);
            monthlyReport.setShouldTaken(shouldTaken);
            monthlyReport.setLeave(leave);
            monthlyReport.setDetails(reports);
            monthlyReport.setFrozen(frozen);

            result.add(monthlyReport);
        }


        return result;
    }
}
