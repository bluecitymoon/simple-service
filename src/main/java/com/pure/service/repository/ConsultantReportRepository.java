package com.pure.service.repository;

import com.pure.service.domain.ConsultantReport;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ConsultantReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultantReportRepository extends JpaRepository<ConsultantReport, Long>, JpaSpecificationExecutor<ConsultantReport> {

}
