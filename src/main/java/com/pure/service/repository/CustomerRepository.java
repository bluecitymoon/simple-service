package com.pure.service.repository;

import com.pure.service.domain.Customer;
import com.pure.service.service.dto.dto.ChannelReportElement;
import com.pure.service.service.dto.dto.LocationStatusReportEntity;
import com.pure.service.service.dto.dto.Overview;
import com.pure.service.service.dto.dto.ReportEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.Instant;
import java.util.List;


/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {


    @Query(nativeQuery = true)
    List<ReportEntity> searchCustomerStatusReport(Instant startDate, Instant endDate, Long regionId);

    @Query(nativeQuery = true)
    List<LocationStatusReportEntity> searchLocationCustomerStatusReport(Instant startDate, Instant endDate, Long regionId);

    @Query(nativeQuery = true)
    Overview searchCurrentUserOverview(Long userId, Instant endDate, Instant startDate, Long regionId);

    @Query(nativeQuery = true)
    List<ChannelReportElement> searchChannelReport(Instant startDate, Instant endDate, Long regionId);

    @Query(nativeQuery = true, value = "select count(0) as cardCount from customer_card cc left join customer c on cc.customer_id = c.id where c.channel_id = :1 and c.region_id = :2" +
        " and c.visit_date > :3 and c.visit_date < :4")
    Integer getChannelCustomerCardCount(Long channelId, Long regionId, Instant startDate, Instant endDate);

    @Query(nativeQuery = true, value = "select count(0) as contractCount from contract co left join customer c on co.customer_id = c.id where c.channel_id = :1 and c.region_id = :2 " +
        " and c.visit_date > :3 and c.visit_date < :4")
    Integer getChannelCustomerContractCount(Long channelId, Long regionId, Instant startDate, Instant endDate);

    Customer findByNewOrder_Id(Long newOrderId);

    Customer findByContactPhoneNumber(String contactPhoneNumber);

    List<Customer> findByAssignDateIsNullAndSalesFollowerIsNotNull();
}
