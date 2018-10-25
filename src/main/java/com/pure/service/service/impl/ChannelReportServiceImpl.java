package com.pure.service.service.impl;

import com.pure.service.service.ChannelReportService;
import com.pure.service.domain.ChannelReport;
import com.pure.service.repository.ChannelReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ChannelReport.
 */
@Service
@Transactional
public class ChannelReportServiceImpl implements ChannelReportService{

    private final Logger log = LoggerFactory.getLogger(ChannelReportServiceImpl.class);

    private final ChannelReportRepository channelReportRepository;

    public ChannelReportServiceImpl(ChannelReportRepository channelReportRepository) {
        this.channelReportRepository = channelReportRepository;
    }

    /**
     * Save a channelReport.
     *
     * @param channelReport the entity to save
     * @return the persisted entity
     */
    @Override
    public ChannelReport save(ChannelReport channelReport) {
        log.debug("Request to save ChannelReport : {}", channelReport);
        return channelReportRepository.save(channelReport);
    }

    /**
     *  Get all the channelReports.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ChannelReport> findAll(Pageable pageable) {
        log.debug("Request to get all ChannelReports");
        return channelReportRepository.findAll(pageable);
    }

    /**
     *  Get one channelReport by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ChannelReport findOne(Long id) {
        log.debug("Request to get ChannelReport : {}", id);
        return channelReportRepository.findOne(id);
    }

    /**
     *  Delete the  channelReport by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChannelReport : {}", id);
        channelReportRepository.delete(id);
    }
}
