package com.pure.service.repository;

import com.pure.service.domain.Contract;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Contract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractRepository extends JpaRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {

    List<Contract> findBySerialNumber(String serialNumber);

    List<Contract> findByCustomerCard_Id(Long cardId);

    List<Contract> findByCustomer_Id(Long id);

    List<Contract> findByStudent_Id(Long id);
}
