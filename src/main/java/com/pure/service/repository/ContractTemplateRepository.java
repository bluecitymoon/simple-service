package com.pure.service.repository;

import com.pure.service.domain.ContractTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the ContractTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractTemplateRepository extends JpaRepository<ContractTemplate, Long>, JpaSpecificationExecutor<ContractTemplate> {

//    List<ContractTemplate> findByCustomerCardType_Id(Long id);
}
