package com.pure.service.service.impl;

import com.pure.service.domain.Contract;
import com.pure.service.domain.ContractStatus;
import com.pure.service.domain.ContractTemplate;
import com.pure.service.domain.Customer;
import com.pure.service.domain.CustomerCard;
import com.pure.service.domain.CustomerCommunicationLog;
import com.pure.service.domain.CustomerCommunicationLogType;
import com.pure.service.repository.ContractRepository;
import com.pure.service.repository.ContractStatusRepository;
import com.pure.service.repository.ContractTemplateRepository;
import com.pure.service.repository.CustomerCommunicationLogRepository;
import com.pure.service.repository.CustomerCommunicationLogTypeRepository;
import com.pure.service.service.ContractService;
import com.pure.service.service.CustomerCardService;
import com.pure.service.service.dto.dto.PackageContractRequest;
import com.pure.service.service.dto.enumurations.ContractStatusEnum;
import com.pure.service.service.dto.enumurations.CustomerCommunicationLogTypeEnum;
import com.pure.service.service.exception.TemplateNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Contract.
 */
@Service
@Transactional
public class ContractServiceImpl implements ContractService{

    private final Logger log = LoggerFactory.getLogger(ContractServiceImpl.class);

    private final ContractRepository contractRepository;

    @Autowired
    private ContractTemplateRepository contractTemplateRepository;

    @Autowired
    private CustomerCardService customerCardService;

    @Autowired
    private ContractStatusRepository contractStatusRepository;

    @Autowired
    private CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository;

    @Autowired
    private CustomerCommunicationLogRepository customerCommunicationLogRepository;

    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    /**
     * Save a contract.
     *
     * @param contract the entity to save
     * @return the persisted entity
     */
    @Override
    public Contract save(Contract contract) {
        log.debug("Request to save Contract : {}", contract);
        return contractRepository.save(contract);
    }

    /**
     *  Get all the contracts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Contract> findAll(Pageable pageable) {
        log.debug("Request to get all Contracts");
        return contractRepository.findAll(pageable);
    }


    /**
     *  get all the contracts where Student is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Contract> findAllWhereStudentIsNull() {
        log.debug("Request to get all contracts where Student is null");
        return StreamSupport
            .stream(contractRepository.findAll().spliterator(), false)
            .filter(contract -> contract.getStudent() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one contract by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Contract findOne(Long id) {
        log.debug("Request to get Contract : {}", id);
        return contractRepository.findOne(id);
    }

    /**
     *  Delete the  contract by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contract : {}", id);
        contractRepository.delete(id);
    }

    @Override
    public boolean contractAlreadyGenerated(String serialNumber) {

        List<Contract> existedContracts = contractRepository.findBySerialNumber(serialNumber);

        return !CollectionUtils.isEmpty(existedContracts);
    }

    @Override
    public List<Contract> generatePackagedContract(PackageContractRequest packageContractRequest) throws TemplateNotFoundException {

        List<ContractTemplate> templates = contractTemplateRepository.findByContractPackage_Id(packageContractRequest.getPackageId());

        if (CollectionUtils.isEmpty(templates)) throw new TemplateNotFoundException("该套餐没有套餐内容，需要添加合同模板");

        CustomerCard customerCard = customerCardService.findOne(packageContractRequest.getCustomerCardId());

        ContractStatus generated = contractStatusRepository.findByCode(ContractStatusEnum.generated.name());

        List<Contract> contracts = new ArrayList<>();
        for (ContractTemplate template : templates) {
            Contract contract = new Contract();

            contract.setSignDate(customerCard.getSignDate());
            contract.setCustomerCard(customerCard);
            contract.setCustomer(customerCard.getCustomer());
            contract.setTotalMoneyAmount(template.getTotalMoneyAmount());
            contract.setSerialNumber(customerCard.getSerialNumber());
            contract.setActive(false);
            contract.setCourse(customerCard.getCourse());
            contract.setContractNumber(generateContractNumber());
            contract.setTotalHours(template.getTotalHours());

            contract.setContractStatus(generated);

            contracts.add(contract);

        }

        saveContractLog(customerCard.getCustomer(), customerCard.getSerialNumber());

        return contractRepository.save(contracts);
    }

    private void saveContractLog(Customer customer, String serialNumber) {

        CustomerCommunicationLog log = new CustomerCommunicationLog();
        log.setCustomer(customer);
        log.setComments("客户生成套餐合同，流水号 " + serialNumber);

        CustomerCommunicationLogType type =customerCommunicationLogTypeRepository.findByCode(CustomerCommunicationLogTypeEnum.contract_generated.name());
        log.setLogType(type);

        customerCommunicationLogRepository.save(log);
    }

    @Override
    public String generateContractNumber() {

        Long currentInstant = Instant.now().getEpochSecond();

        String text = "" + currentInstant + RandomStringUtils.randomNumeric(6);

        return text;
    }
}
