package com.pure.service.repository;

import com.pure.service.domain.CustomerScheduleFeedback;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerScheduleFeedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerScheduleFeedbackRepository extends JpaRepository<CustomerScheduleFeedback, Long>, JpaSpecificationExecutor<CustomerScheduleFeedback> {

}
