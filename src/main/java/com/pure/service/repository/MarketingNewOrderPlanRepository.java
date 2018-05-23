package com.pure.service.repository;

import com.pure.service.domain.MarketingNewOrderPlan;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the MarketingNewOrderPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketingNewOrderPlanRepository extends JpaRepository<MarketingNewOrderPlan, Long>, JpaSpecificationExecutor<MarketingNewOrderPlan> {

    @Query("select marketing_new_order_plan from MarketingNewOrderPlan marketing_new_order_plan where marketing_new_order_plan.user.login = ?#{principal.username}")
    List<MarketingNewOrderPlan> findByUserIsCurrentUser();

}
