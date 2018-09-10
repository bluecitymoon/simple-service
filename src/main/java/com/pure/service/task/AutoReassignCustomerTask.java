package com.pure.service.task;

import com.pure.service.domain.Customer;
import com.pure.service.domain.CustomerStatus;
import com.pure.service.domain.CustomerStatusReportDtl;
import com.pure.service.repository.CustomerRepository;
import com.pure.service.repository.CustomerStatusReportDtlRepository;
import com.pure.service.repository.CustomerStatusRepository;
import com.pure.service.service.CustomerQueryService;
import com.pure.service.service.CustomerService;
import com.pure.service.service.dto.CustomerCriteria;
import com.pure.service.service.dto.dto.CombinedReport;
import com.pure.service.service.dto.request.CustomerStatusRequest;
import com.pure.service.service.dto.request.ReportElement;
import com.pure.service.service.util.DateUtil;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class AutoReassignCustomerTask {

    private final Logger log = LoggerFactory.getLogger(AutoReassignCustomerTask.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerStatusReportDtlRepository reportDtlRepository;

    @Autowired
    private CustomerQueryService customerQueryService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerStatusRepository customerStatusRepository;
    /**
     * 每月底把上月未完成的新单重新分配到下月份继续完成
     * 并将上月的报告存档.
     * 每月的第一天的 00：01: 01启动这个任务
     */
    @Scheduled(cron = "01 01 00 1 * ?")
    public void reassign() {

        Instant now = Instant.now();
        log.info("开始启动自动重分配任务");

        Instant lastMonthBeginning = now.minus(15, ChronoUnit.DAYS);
        Instant startInstant = DateUtil.getFirstSecondOfMonth(LocalDateTime.ofInstant(lastMonthBeginning, ZoneId.systemDefault()));
        Instant endInstant = DateUtil.getLastSecondOfMonth(LocalDateTime.ofInstant(lastMonthBeginning, ZoneId.systemDefault()));

        log.info("Getting report between {} and {}", startInstant, endInstant);

        CustomerStatusRequest customerStatusRequest = new CustomerStatusRequest();
        customerStatusRequest.setStartDate(startInstant);
        customerStatusRequest.setEndDate(endInstant);

        CombinedReport report = customerService.getStatusReport(customerStatusRequest);

        List<CustomerStatusReportDtl> reportDtls = new ArrayList<>();

        List<ReportElement> reportElements = report.getData();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(lastMonthBeginning, ZoneId.systemDefault());
        for (ReportElement reportElement : reportElements) {

            CustomerStatusReportDtl dtl = new CustomerStatusReportDtl();
            BeanUtils.copyProperties(reportElement, dtl);

            dtl.setYear("" + localDateTime.getYear());
            dtl.setMonth("" + localDateTime.getMonthValue());
            reportDtls.add(dtl);
        }

        reportDtlRepository.save(reportDtls);

        //reassign
        reassignNewOrders(startInstant, endInstant, now);

    }

    private void reassignNewOrders(Instant startInstant, Instant endInstant, Instant newAssignDate) {

        CustomerCriteria customerCriteria = new CustomerCriteria();

        InstantFilter instantFilter = new InstantFilter();
        instantFilter.setGreaterOrEqualThan(startInstant);
        instantFilter.setLessOrEqualThan(endInstant);
        customerCriteria.setAssignDate(instantFilter);

        CustomerStatus newOrderStatus = customerStatusRepository.findByCode("new_created");

        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(newOrderStatus.getId());

        customerCriteria.setStatusId(longFilter);

        List<Customer> allCustomers = customerQueryService.findByCriteria(customerCriteria);
        for (Customer customer : allCustomers) {
            customer.setAssignDate(newAssignDate);
        }

        customerRepository.save(allCustomers);
    }

//    @Scheduled(fixedRate = 2000)
//    public void testLog() {
//
//        log.info("" + System.currentTimeMillis());
//    }
}
