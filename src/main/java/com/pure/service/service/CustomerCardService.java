package com.pure.service.service;

import com.pure.service.domain.CustomerCard;
import com.pure.service.service.dto.CardNumberRequest;
import com.pure.service.service.dto.request.UpgradeCustomerCardRequest;
import com.pure.service.service.exception.CollectionNotPaidException;
import com.pure.service.service.exception.ContractsExceedLimitException;
import com.pure.service.service.exception.TemplateNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing CustomerCard.
 */
public interface CustomerCardService {

    /**
     * Save a customerCard.
     *
     * @param customerCard the entity to save
     * @return the persisted entity
     */
    CustomerCard save(CustomerCard customerCard);

    /**
     *  Get all the customerCards.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerCard> findAll(Pageable pageable);

    /**
     *  Get the "id" customerCard.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerCard findOne(Long id);

    /**
     *  Delete the "id" customerCard.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    String generateCardNumber(CardNumberRequest cardNumberRequest);

    List<CustomerCard> getCardsByCustomerId(Long id);

    CustomerCard upgradeCustomerCard(UpgradeCustomerCardRequest upgradeCustomerCardRequest) throws ContractsExceedLimitException, CollectionNotPaidException, TemplateNotFoundException;
}
