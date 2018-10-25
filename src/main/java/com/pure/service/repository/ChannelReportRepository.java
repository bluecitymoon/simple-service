package com.pure.service.repository;

import com.pure.service.domain.ChannelReport;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ChannelReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChannelReportRepository extends JpaRepository<ChannelReport, Long>, JpaSpecificationExecutor<ChannelReport> {

}
