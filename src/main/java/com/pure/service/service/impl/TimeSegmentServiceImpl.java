package com.pure.service.service.impl;

import com.pure.service.service.TimeSegmentService;
import com.pure.service.domain.TimeSegment;
import com.pure.service.repository.TimeSegmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TimeSegment.
 */
@Service
@Transactional
public class TimeSegmentServiceImpl implements TimeSegmentService{

    private final Logger log = LoggerFactory.getLogger(TimeSegmentServiceImpl.class);

    private final TimeSegmentRepository timeSegmentRepository;

    public TimeSegmentServiceImpl(TimeSegmentRepository timeSegmentRepository) {
        this.timeSegmentRepository = timeSegmentRepository;
    }

    /**
     * Save a timeSegment.
     *
     * @param timeSegment the entity to save
     * @return the persisted entity
     */
    @Override
    public TimeSegment save(TimeSegment timeSegment) {
        log.debug("Request to save TimeSegment : {}", timeSegment);
        return timeSegmentRepository.save(timeSegment);
    }

    /**
     *  Get all the timeSegments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TimeSegment> findAll(Pageable pageable) {
        log.debug("Request to get all TimeSegments");
        return timeSegmentRepository.findAll(pageable);
    }

    /**
     *  Get one timeSegment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TimeSegment findOne(Long id) {
        log.debug("Request to get TimeSegment : {}", id);
        return timeSegmentRepository.findOne(id);
    }

    /**
     *  Delete the  timeSegment by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimeSegment : {}", id);
        timeSegmentRepository.delete(id);
    }
}
