package com.pure.service.service.impl;

import com.pure.service.domain.CustomerCard;
import com.pure.service.repository.CustomerCardRepository;
import com.pure.service.service.CustomerCardService;
import com.pure.service.service.CustomerService;
import com.pure.service.service.dto.CardNumberRequest;
import com.pure.service.service.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerCard.
 */
@Service
@Transactional
public class CustomerCardServiceImpl implements CustomerCardService{

    private final Logger log = LoggerFactory.getLogger(CustomerCardServiceImpl.class);

    private final CustomerCardRepository customerCardRepository;

    @Autowired
    private CustomerService customerService;

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
        return customerCardRepository.save(customerCard);
    }

    /**
     *  Get all the customerCards.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerCard> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerCards");
        return customerCardRepository.findAll(pageable);
    }

    /**
     *  Get one customerCard by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerCard findOne(Long id) {
        log.debug("Request to get CustomerCard : {}", id);
        return customerCardRepository.findOne(id);
    }

    /**
     *  Delete the  customerCard by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerCard : {}", id);
        customerCardRepository.delete(id);
    }

    @Override
    public String generateCardNumber(CardNumberRequest cardNumberRequest) {

        return "hfbl" + cardNumberRequest.getCardCode() + DateUtil.getSimpleToday() + "";
    }
}
