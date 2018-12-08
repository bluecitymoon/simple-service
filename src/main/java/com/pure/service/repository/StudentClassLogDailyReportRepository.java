package com.pure.service.repository;

import com.pure.service.domain.StudentClassLogDailyReport;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StudentClassLogDailyReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentClassLogDailyReportRepository extends JpaRepository<StudentClassLogDailyReport, Long>, JpaSpecificationExecutor<StudentClassLogDailyReport> {

}
