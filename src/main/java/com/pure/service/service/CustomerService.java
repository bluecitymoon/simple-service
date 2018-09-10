package com.pure.service.service;

import com.pure.service.domain.Customer;
import com.pure.service.domain.CustomerCommunicationSchedule;
import com.pure.service.service.dto.dto.CombinedReport;
import com.pure.service.service.dto.dto.Overview;
import com.pure.service.service.dto.request.CustomerStatusRequest;
import com.pure.service.service.dto.request.StatusReportElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Customer.
 */
public interface CustomerService {

    /**
     * Save a customer.
     *
     * @param customer the entity to save
     * @return the persisted entity
     */
    Customer save(Customer customer);

    /**
     *  Get all the customers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Customer> findAll(Pageable pageable);

    /**
     *  Get the "id" customer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Customer findOne(Long id);

    /**
     *  Delete the "id" customer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /*
     */
    Customer importCustomerFromNewOrder(Long newOrderId);

    void updateTrackTaskStatus(Customer customer, String lastComments);

    List<Customer> batchSave(List<Customer> customers);

    CombinedReport getStatusReport(CustomerStatusRequest customerStatusRequest);

    Overview getCurrentUserOverview();

    void updateCustomerStatusForNewSchedule(CustomerCommunicationSchedule schedule);

    List<StatusReportElement> getLocationStatusReport(CustomerStatusRequest customerStatusRequest);

    Customer preloadMergedCustomer(Long oid, Long tid);

    Customer mergeCustomer(Long originalId, Long targetId, Customer customer);

    void backupReport();
}
