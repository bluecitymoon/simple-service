package com.pure.service.service.impl;

import com.pure.service.domain.Collection;
import com.pure.service.domain.CollectionStatus;
import com.pure.service.domain.CustomerCard;
import com.pure.service.domain.CustomerCollectionLog;
import com.pure.service.repository.CollectionRepository;
import com.pure.service.repository.CollectionStatusRepository;
import com.pure.service.repository.ContractRepository;
import com.pure.service.repository.ContractTemplateRepository;
import com.pure.service.repository.CustomerCardRepository;
import com.pure.service.service.CollectionService;
import com.pure.service.service.ContractService;
import com.pure.service.service.CustomerCollectionLogService;
import com.pure.service.service.dto.enumurations.CollectionStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * Service Implementation for managing Collection.
 */
@Service
@Transactional
public class CollectionServiceImpl implements CollectionService {

    private final Logger log = LoggerFactory.getLogger(CollectionServiceImpl.class);

    private final CollectionRepository collectionRepository;

    @Autowired
    private CollectionStatusRepository collectionStatusRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractTemplateRepository contractTemplateRepository;

    @Autowired
    private CustomerCardRepository customerCardRepository;

    @Autowired
    private CustomerCollectionLogService customerCollectionLogService;



    public CollectionServiceImpl(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    /**
     * Save a collection.
     *
     * @param collection the entity to save
     * @return the persisted entity
     */
    @Override
    public Collection save(Collection collection) {
        log.debug("Request to save Collection : {}", collection);

        CollectionStatus status = null;

        Float moneyAmount = collection.getMoneyShouldCollected() == null? 0.0f: collection.getMoneyShouldCollected();
        Float moneyCollection = collection.getMoneyCollected() == null? 0.0f: collection.getMoneyCollected();
        Float balance = moneyAmount - moneyCollection;

        if (moneyAmount == balance) {
            status = collectionStatusRepository.findByCode(CollectionStatusEnum.notCollected.name());
        } else if (balance > 0){
            status = collectionStatusRepository.findByCode(CollectionStatusEnum.partlyCollected.name());
        } else if (balance == 0) {
            status = collectionStatusRepository.findByCode(CollectionStatusEnum.collected.name());
        }

        collection.setStatus(status);

        return collectionRepository.save(collection);
    }

    /**
     * Get all the collections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Collection> findAll(Pageable pageable) {
        log.debug("Request to get all Collections");
        return collectionRepository.findAll(pageable);
    }

    /**
     * Get one collection by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Collection findOne(Long id) {
        log.debug("Request to get Collection : {}", id);
        return collectionRepository.findOne(id);
    }

    /**
     * Delete the  collection by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Collection : {}", id);
        collectionRepository.delete(id);
    }

    @Override
    public void confirmCustomerCollection(Collection collection) {

        CollectionStatus collectionStatus = collectionStatusRepository.findByCode(CollectionStatusEnum.collected.name());
        collection.setStatus(collectionStatus);

        Collection savedCollection = collectionRepository.save(collection);

        List<CustomerCard> cards = customerCardRepository.findBySerialNumber(collection.getSequenceNumber());

        CustomerCollectionLog log = new CustomerCollectionLog();
        log = log.collection(savedCollection)
            .balance(collection.getBalance())
            .serialNumber(collection.getSequenceNumber())
            .moneyCollected(collection.getMoneyCollected())
            .moneyShouldCollected(collection.getMoneyShouldCollected());

        if (!CollectionUtils.isEmpty(cards)) {
            log.setCustomer(cards.get(0).getCustomer());
        }

        customerCollectionLogService.save(log);
//
//        //确实收款后生成合同
//        String sequenceNumber = collection.getSequenceNumber();
//
//        if (contractService.contractAlreadyGenerated(sequenceNumber)) {
//
//            log.info("合同已生成，不会再次在确认收款的的时候生成新的合同，流水号 {}", sequenceNumber );
//            return;
//        }
//
//        if (CollectionUtils.isEmpty(cards)) {
//
//            log.info("该客户没有生成卡，确认收款时不自动生成合同，流水号 {}", sequenceNumber);
//            return;
//        }
//
//        CustomerCard latestCard = cards.get(cards.size() - 1) ;
//        List<ContractTemplate> contractTemplates = contractTemplateRepository.findByCustomerCardType_Id(latestCard.getId());
//
//        List<Contract> newContracts = new ArrayList<>();
//        for (ContractTemplate contractTemplate : contractTemplates) {
//            Contract contract = new Contract();
//
//            BeanUtils.copyProperties(contractTemplate, contract);
//
//            contract.setActive(true);
//        }
    }

    @Override
    public boolean customerCardPaid(String serialNumber) {

        Collection collection = collectionRepository.findBySequenceNumber(serialNumber);

        if (collection == null) {
            return false;
        }

        return collection.getStatus().getCode().equals(CollectionStatusEnum.collected.name());
    }
}
