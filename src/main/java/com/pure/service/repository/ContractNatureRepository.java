package com.pure.service.repository;

import com.pure.service.domain.ContractNature;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContractNature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractNatureRepository extends JpaRepository<ContractNature, Long>, JpaSpecificationExecutor<ContractNature> {

    ContractNature findByCode(String code);
}
