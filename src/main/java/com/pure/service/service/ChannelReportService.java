package com.pure.service.service;

import com.pure.service.domain.ChannelReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ChannelReport.
 */
public interface ChannelReportService {

    /**
     * Save a channelReport.
     *
     * @param channelReport the entity to save
     * @return the persisted entity
     */
    ChannelReport save(ChannelReport channelReport);

    /**
     *  Get all the channelReports.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ChannelReport> findAll(Pageable pageable);

    /**
     *  Get the "id" channelReport.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ChannelReport findOne(Long id);

    /**
     *  Delete the "id" channelReport.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
