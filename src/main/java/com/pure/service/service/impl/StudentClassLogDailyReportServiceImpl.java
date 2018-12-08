package com.pure.service.service.impl;

import com.pure.service.domain.StudentClassLogDailyReport;
import com.pure.service.region.RegionUtils;
import com.pure.service.repository.ClassArrangementRepository;
import com.pure.service.repository.StudentClassLogDailyReportRepository;
import com.pure.service.service.StudentClassLogDailyReportService;
import com.pure.service.service.dto.dto.ClassSchedule;
import com.pure.service.service.dto.dto.StatusBasedStudent;
import com.pure.service.service.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;


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

        Long regionId = RegionUtils.getRegionIdForCurrentUser();
        Instant todayStart = DateUtil.getSimpleTodayInstantBegin();
        Instant todayEnd = DateUtil.getSimpleTodayInstantEnd();

        List<ClassSchedule> todaySchedules = classArrangementRepository.getAllSchedulesByRange(todayStart, todayEnd, regionId);

        return null;
    }
}
