package com.pure.service.repository;

import com.pure.service.domain.CustomerStatusReportDtl;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerStatusReportDtl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerStatusReportDtlRepository extends JpaRepository<CustomerStatusReportDtl, Long>, JpaSpecificationExecutor<CustomerStatusReportDtl> {

}
