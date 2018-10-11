package com.pure.service.repository;

import com.pure.service.domain.ContractPackageType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContractPackageType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractPackageTypeRepository extends JpaRepository<ContractPackageType, Long>, JpaSpecificationExecutor<ContractPackageType> {

}
