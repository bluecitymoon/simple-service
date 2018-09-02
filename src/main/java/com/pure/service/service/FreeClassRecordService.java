package com.pure.service.service;

import com.pure.service.domain.Customer;
import com.pure.service.domain.FreeClassRecord;
import com.pure.service.service.dto.BatchCustomers;
import com.pure.service.service.dto.BatchCustomersResponse;
import com.pure.service.service.dto.dto.FreeClassPlanElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.InputStream;
import java.time.Instant;
import java.util.List;

/**
 * Service Interface for managing FreeClassRecord.
 */
public interface FreeClassRecordService {

    /**
     * Save a freeClassRecord.
     *
     * @param freeClassRecord the entity to save
     * @return the persisted entity
     */
    FreeClassRecord save(FreeClassRecord freeClassRecord);

    List<FreeClassRecord> batchSave(List<FreeClassRecord> freeClassRecords);

    /**
     *  Get all the freeClassRecords.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FreeClassRecord> findAll(Pageable pageable);

    /**
     *  Get the "id" freeClassRecord.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FreeClassRecord findOne(Long id);

    /**
     *  Delete the "id" freeClassRecord.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    BatchCustomersResponse upload(BatchCustomers customers);

    BatchCustomersResponse upload(InputStream inputStream, String name);

    void createScheduleForCustomer(Instant scheduleDate, Customer customer, String sourceType);

    List<FreeClassPlanElement> getSchedulePlanList();
}
