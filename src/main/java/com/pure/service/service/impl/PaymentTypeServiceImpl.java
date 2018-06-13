package com.pure.service.service.impl;

import com.pure.service.service.PaymentTypeService;
import com.pure.service.domain.PaymentType;
import com.pure.service.repository.PaymentTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PaymentType.
 */
@Service
@Transactional
public class PaymentTypeServiceImpl implements PaymentTypeService{

    private final Logger log = LoggerFactory.getLogger(PaymentTypeServiceImpl.class);

    private final PaymentTypeRepository paymentTypeRepository;

    public PaymentTypeServiceImpl(PaymentTypeRepository paymentTypeRepository) {
        this.paymentTypeRepository = paymentTypeRepository;
    }

    /**
     * Save a paymentType.
     *
     * @param paymentType the entity to save
     * @return the persisted entity
     */
    @Override
    public PaymentType save(PaymentType paymentType) {
        log.debug("Request to save PaymentType : {}", paymentType);
        return paymentTypeRepository.save(paymentType);
    }

    /**
     *  Get all the paymentTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentType> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentTypes");
        return paymentTypeRepository.findAll(pageable);
    }

    /**
     *  Get one paymentType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PaymentType findOne(Long id) {
        log.debug("Request to get PaymentType : {}", id);
        return paymentTypeRepository.findOne(id);
    }

    /**
     *  Delete the  paymentType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentType : {}", id);
        paymentTypeRepository.delete(id);
    }
}
