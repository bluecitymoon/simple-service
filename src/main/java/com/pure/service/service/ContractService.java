package com.pure.service.service;

import com.pure.service.domain.Contract;
import com.pure.service.service.dto.dto.CombinedConsultantReport;
import com.pure.service.service.dto.dto.PackageContractRequest;
import com.pure.service.service.dto.request.CustomerStatusRequest;
import com.pure.service.service.exception.CollectionNotPaidException;
import com.pure.service.service.exception.ContractsExceedLimitException;
import com.pure.service.service.exception.TemplateNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Contract.
 */
public interface ContractService {

    /**
     * Save a contract.
     *
     * @param contract the entity to save
     * @return the persisted entity
     */
    Contract save(Contract contract) throws CollectionNotPaidException;

    /**
     *  Get all the contracts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Contract> findAll(Pageable pageable);
    /**
     *  Get all the ContractDTO where Student is null.
     *
     *  @return the list of entities
     */
    List<Contract> findAllWhereStudentIsNull();

    /**
     *  Get the "id" contract.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Contract findOne(Long id);

    /**
     *  Delete the "id" contract.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    boolean contractAlreadyGenerated(String serialNumber);

    List<Contract> generatePackagedContract(PackageContractRequest packageContractRequest) throws TemplateNotFoundException, CollectionNotPaidException, ContractsExceedLimitException;

    String generateContractNumber();

    CombinedConsultantReport getCourseConsultantWorkReport(CustomerStatusRequest request);
}
