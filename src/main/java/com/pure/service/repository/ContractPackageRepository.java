package com.pure.service.repository;

import com.pure.service.domain.ContractPackage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContractPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractPackageRepository extends JpaRepository<ContractPackage, Long>, JpaSpecificationExecutor<ContractPackage> {

}
