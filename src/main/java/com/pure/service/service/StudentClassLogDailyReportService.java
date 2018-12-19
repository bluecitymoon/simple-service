package com.pure.service.service;

import com.pure.service.domain.StudentClassLogDailyReport;
import com.pure.service.service.dto.dto.StatusBasedStudent;
import com.pure.service.service.dto.dto.StudentClassLogMonthlyReport;
import com.pure.service.service.dto.request.CustomerStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

/**
 * Service Interface for managing StudentClassLogDailyReport.
 */
public interface StudentClassLogDailyReportService {

    /**
     * Save a studentClassLogDailyReport.
     *
     * @param studentClassLogDailyReport the entity to save
     * @return the persisted entity
     */
    StudentClassLogDailyReport save(StudentClassLogDailyReport studentClassLogDailyReport);

    /**
     *  Get all the studentClassLogDailyReports.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<StudentClassLogDailyReport> findAll(Pageable pageable);

    /**
     *  Get the "id" studentClassLogDailyReport.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StudentClassLogDailyReport findOne(Long id);

    /**
     *  Delete the "id" studentClassLogDailyReport.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    StatusBasedStudent getStudentClassLogDailyReportToday(Instant logDate);

    StudentClassLogDailyReport saveLogDailyReport(StudentClassLogDailyReport studentClassLogDailyReport);

    List<StudentClassLogMonthlyReport> getMonthlyReport(CustomerStatusRequest customerStatusRequest);
}
