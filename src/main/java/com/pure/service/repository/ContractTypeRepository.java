package com.pure.service.repository;

import com.pure.service.domain.ContractType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContractType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractTypeRepository extends JpaRepository<ContractType, Long>, JpaSpecificationExecutor<ContractType> {

}
