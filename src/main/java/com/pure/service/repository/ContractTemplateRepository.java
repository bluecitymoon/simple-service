package com.pure.service.repository;

import com.pure.service.domain.ContractTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContractTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractTemplateRepository extends JpaRepository<ContractTemplate, Long>, JpaSpecificationExecutor<ContractTemplate> {

}
