package com.pure.service.service;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.ClassArrangementRule;
import com.pure.service.service.dto.dto.ClassArrangementWeekElement;
import com.pure.service.service.dto.dto.ClassSchedule;
import com.pure.service.service.dto.request.CustomerStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing ClassArrangement.
 */
public interface ClassArrangementService {

    /**
     * Save a classArrangement.
     *
     * @param classArrangement the entity to save
     * @return the persisted entity
     */
    ClassArrangement save(ClassArrangement classArrangement);

    /**
     *  Get all the classArrangements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClassArrangement> findAll(Pageable pageable);

    /**
     *  Get the "id" classArrangement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClassArrangement findOne(Long id);

    /**
     *  Delete the "id" classArrangement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    void createClassArrangementsByRule(Long id);

    List<ClassSchedule> searchSchedulesInRange(CustomerStatusRequest customerStatusRequest);

    List<ClassArrangementWeekElement> getArrangementsInCurrentWeek();

    void createClassSchedule(ClassArrangementRule rule);

    void fixupClassArrangements();
}
