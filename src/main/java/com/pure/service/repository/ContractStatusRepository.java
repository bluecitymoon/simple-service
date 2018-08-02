package com.pure.service.repository;

import com.pure.service.domain.ContractStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContractStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractStatusRepository extends JpaRepository<ContractStatus, Long>, JpaSpecificationExecutor<ContractStatus> {

    ContractStatus findByCode(String code);
}
