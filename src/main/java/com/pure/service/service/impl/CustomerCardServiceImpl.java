package com.pure.service.service.impl;

import com.pure.service.domain.Collection;
import com.pure.service.domain.Contract;
import com.pure.service.domain.Customer;
import com.pure.service.domain.CustomerCard;
import com.pure.service.domain.CustomerCardUpgradeLog;
import com.pure.service.domain.CustomerCommunicationLog;
import com.pure.service.domain.CustomerCommunicationLogType;
import com.pure.service.domain.CustomerStatus;
import com.pure.service.domain.FinanceCategory;
import com.pure.service.region.RegionUtils;
import com.pure.service.repository.CustomerCardRepository;
import com.pure.service.repository.CustomerCommunicationLogRepository;
import com.pure.service.repository.CustomerCommunicationLogTypeRepository;
import com.pure.service.repository.CustomerStatusRepository;
import com.pure.service.repository.FinanceCategoryRepository;
import com.pure.service.service.CollectionService;
import com.pure.service.service.ContractService;
import com.pure.service.service.CustomerCardQueryService;
import com.pure.service.service.CustomerCardService;
import com.pure.service.service.CustomerCardUpgradeLogService;
import com.pure.service.service.CustomerService;
import com.pure.service.service.dto.CardNumberRequest;
import com.pure.service.service.dto.CustomerCardCriteria;
import com.pure.service.service.dto.dto.PackageContractRequest;
import com.pure.service.service.dto.request.UpgradeCustomerCardRequest;
import com.pure.service.service.exception.CollectionNotPaidException;
import com.pure.service.service.exception.ContractsExceedLimitException;
import com.pure.service.service.exception.TemplateNotFoundException;
import com.pure.service.service.util.DateUtil;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * Service Implementation for managing CustomerCard.
 */
@Service
@Transactional
public class CustomerCardServiceImpl implements CustomerCardService {

    private final Logger log = LoggerFactory.getLogger(CustomerCardServiceImpl.class);

    private final CustomerCardRepository customerCardRepository;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private FinanceCategoryRepository financeCategoryRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerStatusRepository customerStatusRepository;

    @Autowired
    private CustomerCardQueryService customerCardQueryService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private CustomerCardUpgradeLogService logService;

    @Autowired
    private CustomerCommunicationLogTypeRepository logTypeRepository;

    @Autowired
    private CustomerCommunicationLogRepository logRepository;

    public CustomerCardServiceImpl(CustomerCardRepository customerCardRepository) {
        this.customerCardRepository = customerCardRepository;
    }

    /**
     * Save a customerCard.
     *
     * @param customerCard the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerCard save(CustomerCard customerCard) {
        log.debug("Request to save CustomerCard : {}", customerCard);

        //generate finance payment for new card
        if (customerCard.getId() == null) {

            CustomerCardCriteria customerCardCriteria = new CustomerCardCriteria();

            LongFilter longFilter = new LongFilter();
            longFilter.setEquals(customerCard.getCustomer().getId());
            customerCardCriteria.setCustomerId(longFilter);

            LongFilter customerCardTypeId = new LongFilter();
            customerCardTypeId.setEquals(customerCard.getCustomerCardType().getId());
            customerCardCriteria.setCustomerCardTypeId(customerCardTypeId);
            List<CustomerCard> cards = customerCardQueryService.findByCriteria(customerCardCriteria);

            if (!CollectionUtils.isEmpty(cards)) {
                throw new RuntimeException("该客户已经拥有" + customerCard.getCustomerCardType().getName() + "卡，无法再次生成！");
            }

            FinanceCategory dealCategory = financeCategoryRepository.findByCode("deal");
            Collection collection = new Collection();
            collection.setFinanceCategory(dealCategory);
            collection.setSequenceNumber(customerCard.getSerialNumber());
            collection.setMoneyShouldCollected(customerCard.getMoneyShouldCollected());
            collection.setMoneyCollected(customerCard.getMoneyCollected());

            if (customerCard.getMoneyShouldCollected() != null && customerCard.getMoneyCollected() != null) {
                Float balance = customerCard.getMoneyShouldCollected() - customerCard.getMoneyCollected();
                collection.setBalance(balance);
            }
            collection.setPayerName(customerCard.getCustomer().getName());

            collectionService.save(collection);

            CustomerStatus dealed = customerStatusRepository.findByCode("deal");

            Customer customer = customerCard.getCustomer();
            customer.setStatus(dealed);

            customerService.save(customer);

        }

        return customerCardRepository.save(customerCard);
    }

    /**
     * Get all the customerCards.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerCard> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerCards");
        return customerCardRepository.findAll(pageable);
    }

    /**
     * Get one customerCard by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerCard findOne(Long id) {
        log.debug("Request to get CustomerCard : {}", id);
        return customerCardRepository.findOne(id);
    }

    /**
     * Delete the  customerCard by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerCard : {}", id);
        customerCardRepository.delete(id);
    }

    @Override
    public String generateCardNumber(CardNumberRequest cardNumberRequest) {

        Customer customer = customerService.findOne(cardNumberRequest.getCustomerId());

        String freshNumber = "hfbl" + cardNumberRequest.getCardCode() + DateUtil.getSimpleToday() + customer.getId();

        Integer lastSequenceNumber = 1;
        Integer cardCount = customerCardRepository.getCustomerCardCount(cardNumberRequest.getCustomerId().intValue());

        if (cardCount != null) {

            lastSequenceNumber = cardCount + 1;
        }

        return freshNumber + lastSequenceNumber;

    }

    @Override
    public List<CustomerCard> getCardsByCustomerId(Long id) {

        CustomerCardCriteria customerCardCriteria = new CustomerCardCriteria();

        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);
        customerCardCriteria.setCustomerId(longFilter);

        RegionUtils.setRegionIdFilter(customerCardCriteria);

        return customerCardQueryService.findByCriteria(customerCardCriteria);
    }

    @Override
    public CustomerCard upgradeCustomerCard(UpgradeCustomerCardRequest upgradeCustomerCardRequest) throws ContractsExceedLimitException, CollectionNotPaidException, TemplateNotFoundException {

        CustomerCard newCustomerCard = new CustomerCard();
        BeanUtils.copyProperties(upgradeCustomerCardRequest, newCustomerCard);

        newCustomerCard.setCustomerCardType(upgradeCustomerCardRequest.getNewCustomerCardType());

        RegionUtils.setRegionAbstractAuditingRegionEntity(newCustomerCard);
        CustomerCard savedCustomerCard = save(newCustomerCard);

        PackageContractRequest contractRequest = new PackageContractRequest();
        contractRequest.setCustomerCardId(savedCustomerCard.getId());
        contractRequest.setCustomerId(upgradeCustomerCardRequest.getCustomer().getId());
        contractRequest.setPackageId(upgradeCustomerCardRequest.getUpgradePackage().getId());

        List<Contract> contracts = contractService.generatePackagedContract(contractRequest);

        CustomerCardUpgradeLog upgradeLog = new CustomerCardUpgradeLog();
        upgradeLog.setOriginalCardType(upgradeCustomerCardRequest.getCustomerCardType());
        upgradeLog.setNewCardType(upgradeCustomerCardRequest.getNewCustomerCardType());
        upgradeLog.setCustomerId(upgradeCustomerCardRequest.getCustomer().getId());
        upgradeLog.setCustomerName(upgradeCustomerCardRequest.getCustomer().getName());
        upgradeLog.setSerialNumber(newCustomerCard.getSerialNumber());

        RegionUtils.setRegionAbstractRegionEntity(upgradeLog);

        logService.save(upgradeLog);

        saveUpgradeLog(upgradeCustomerCardRequest.getCustomer(), "卡升级成功，生成了" + contracts.size() +"份合同，流水号 " + newCustomerCard.getSerialNumber());

        return savedCustomerCard;
    }

    private void saveUpgradeLog(Customer customer, String comments) {

        CustomerCommunicationLog log = new CustomerCommunicationLog();
        log.setComments(comments);
        log.setCustomer(customer);
        log.setFreeClassRecord(customer.getNewOrder());

        CustomerCommunicationLogType logType =logTypeRepository.findByCode("card_upgrade");
        log.setLogType(logType);
        RegionUtils.setRegionAbstractAuditingRegionEntity(log);

        logRepository.save(log);

    }
}
