package com.pure.service.service.impl;

import com.pure.service.domain.Customer;
import com.pure.service.domain.CustomerCommunicationLog;
import com.pure.service.domain.CustomerCommunicationLogType;
import com.pure.service.domain.CustomerCommunicationSchedule;
import com.pure.service.domain.CustomerScheduleFeedback;
import com.pure.service.domain.FreeClassPlan;
import com.pure.service.domain.FreeClassRecord;
import com.pure.service.domain.NewOrderAssignHistory;
import com.pure.service.domain.User;
import com.pure.service.region.RegionUtils;
import com.pure.service.repository.CustomerCommunicationLogRepository;
import com.pure.service.repository.CustomerCommunicationLogTypeRepository;
import com.pure.service.repository.CustomerCommunicationScheduleRepository;
import com.pure.service.repository.CustomerRepository;
import com.pure.service.repository.CustomerScheduleFeedbackRepository;
import com.pure.service.repository.FreeClassPlanRepository;
import com.pure.service.repository.FreeClassRecordRepository;
import com.pure.service.repository.NewOrderAssignHistoryRepository;
import com.pure.service.repository.UserRepository;
import com.pure.service.service.CustomerCommunicationScheduleQueryService;
import com.pure.service.service.CustomerCommunicationScheduleService;
import com.pure.service.service.CustomerQueryService;
import com.pure.service.service.CustomerService;
import com.pure.service.service.FreeClassRecordQueryService;
import com.pure.service.service.FreeClassRecordService;
import com.pure.service.service.dto.BatchCustomers;
import com.pure.service.service.dto.BatchCustomersResponse;
import com.pure.service.service.dto.CustomerCommunicationScheduleCriteria;
import com.pure.service.service.dto.CustomerCriteria;
import com.pure.service.service.dto.dto.FreeClassPlanElement;
import com.pure.service.service.util.BatchCustomerUtil;
import com.pure.service.service.util.DateUtil;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Service Implementation for managing FreeClassRecord.
 */
@Service
@Transactional
public class FreeClassRecordServiceImpl implements FreeClassRecordService {

    private final Logger log = LoggerFactory.getLogger(FreeClassRecordServiceImpl.class);

    private final FreeClassRecordRepository freeClassRecordRepository;
    private final NewOrderAssignHistoryRepository newOrderAssignHistoryRepository;
    private final CustomerCommunicationLogRepository customerCommunicationLogRepository;
    private final CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository;
    private final UserRepository userRepository;
    private final CustomerCommunicationScheduleService scheduleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerQueryService customerQueryService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BatchCustomerUtil batchCustomerUtil;

    @Autowired
    private FreeClassPlanRepository freeClassPlanRepository;

    @Autowired
    private FreeClassRecordQueryService freeClassRecordQueryService;

    @Autowired
    private CustomerCommunicationScheduleRepository communicationScheduleRepository;

    @Autowired
    private CustomerCommunicationScheduleQueryService scheduleQueryService;

    @Autowired
    private CustomerScheduleFeedbackRepository feedbackRepository;


    public FreeClassRecordServiceImpl(FreeClassRecordRepository freeClassRecordRepository,
                                      NewOrderAssignHistoryRepository newOrderAssignHistoryRepository,
                                      CustomerCommunicationLogRepository customerCommunicationLogRepository,
                                      CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository,
                                      UserRepository userRepository,
                                      CustomerCommunicationScheduleService scheduleService) {
        this.freeClassRecordRepository = freeClassRecordRepository;
        this.newOrderAssignHistoryRepository = newOrderAssignHistoryRepository;
        this.customerCommunicationLogRepository = customerCommunicationLogRepository;
        this.customerCommunicationLogTypeRepository = customerCommunicationLogTypeRepository;
        this.userRepository = userRepository;
        this.scheduleService = scheduleService;
    }

    /**
     * Save a freeClassRecord.
     *
     * @param freeClassRecord the entity to save
     * @return the persisted entity
     */
    @Override
    public FreeClassRecord save(FreeClassRecord freeClassRecord) {
        log.debug("Request to save FreeClassRecord : {}", freeClassRecord);

        if (freeClassRecord.getId() == null) {
            freeClassRecord.setStatus("新单");
        }

        Long agentId = freeClassRecord.getAgentId();
        if (agentId != null) {
            User referer = userRepository.findOne(agentId);
            freeClassRecord.setReferer(referer);
        }

        boolean newOrder = false;
        if (freeClassRecord.getId() != null) {

            FreeClassRecord oldFreeClassRecord = freeClassRecordRepository.findOne(freeClassRecord.getId());

            String olderFollowerLogin = oldFreeClassRecord.getSalesFollower() == null ? "" : oldFreeClassRecord.getSalesFollower().getLogin();
            String newFollowerLogin = freeClassRecord.getSalesFollower() == null ? "" : freeClassRecord.getSalesFollower().getLogin();

            Customer associatedCustomer = customerRepository.findByNewOrder_Id(freeClassRecord.getId());

            if (!olderFollowerLogin.equals(newFollowerLogin)) {

                saveFreeClassAssignHistory(freeClassRecord, oldFreeClassRecord);

                if (associatedCustomer != null) {
                    associatedCustomer.setAssignDate(Instant.now());
                }

            }

            //sync data to customer if changes is made to new order
            if (associatedCustomer != null) {
                associatedCustomer.setOuterReferer(freeClassRecord.getOuterReferer());

                customerRepository.save(associatedCustomer);
            }

        } else {

            newOrder = true;
        }

        log.debug("Operation on new order ? " + newOrder);

        FreeClassRecord saved = freeClassRecordRepository.saveAndFlush(freeClassRecord);

        log.debug("Saved new order {} " + saved);
        //Save log for new order created.
        if (newOrder) {

            CustomerCommunicationLogType newCreateOrderType = customerCommunicationLogTypeRepository.findByCode(CustomerCommunicationLogTypeEnum.new_order_created.name());
            CustomerCommunicationLog customerCommunicationLog = new CustomerCommunicationLog();
            customerCommunicationLog.comments("回单记录创建成功");

            customerCommunicationLog.setLogType(newCreateOrderType);
            customerCommunicationLog.freeClassRecord(saved);
            customerCommunicationLog.setRegionId(saved.getRegionId());

            CustomerCommunicationLog savedLog = customerCommunicationLogRepository.save(customerCommunicationLog);

            log.debug("Saved customer log for the new order created {}", savedLog);

            //save the default customer for new order.
            Customer newCustomer = customerService.importCustomerFromNewOrder(saved.getId());

            log.debug("Default customer saved for the new order, customer is {} ", newCustomer);

        }


//        scheduleForCustomer(freeClassRecord);

        return saved;
    }
//
//    private void scheduleForCustomer(FreeClassRecord saved) {
//
//        String sourceType = saved.getSourceType();
//        if (StringUtils.isEmpty(sourceType) || !sourceType.equalsIgnoreCase("wechat")) {
//            return;
//        }
//
//        Instant scheduleDate = saved.getScheduleDate();
//        if (scheduleDate == null) {
//            return;
//        }
//
//        List<FreeClassPlan> plans = freeClassPlanRepository.findAll();
//        FreeClassPlan plan = null;
//        for (FreeClassPlan freeClassPlan : plans) {
//
//            if (freeClassPlan.getPlanDate() == null) {
//                continue;
//            }
//
//            if (DateUtil.isSameday(scheduleDate, freeClassPlan.getPlanDate())) {
//                plan = freeClassPlan;
//            }
//        }
//
//        if (plan == null) {
//
//            log.info("这一天没排活动计划，直接预约 {}", scheduleDate);
//
//            saveSchedule(scheduleDate, saved);
//
//        } else {
//
//            InstantFilter targetDateFilter = new InstantFilter();
//            targetDateFilter.setGreaterOrEqualThan(DateUtil.getBeginningOfInstant(plan.getPlanDate()));
//            targetDateFilter.setLessOrEqualThan(DateUtil.getEndingOfInstant(plan.getPlanDate()));
//
//            FreeClassRecordCriteria classRecordCriteria = new FreeClassRecordCriteria();
//            classRecordCriteria.setCreatedDate(targetDateFilter);
//
//            Integer orderedCount = freeClassRecordQueryService.findByCriteria(classRecordCriteria).size();
//
//            if (orderedCount >= plan.getLimitCount()) {
//
//                throw new RuntimeException("预约已满！");
//            }
//
//            saveSchedule(scheduleDate, );
//
//            //increase count
//            if (plan.getLimitCount() == null) {
//                plan.setLimitCount(1);
//            } else {
//                plan.setLimitCount(plan.getLimitCount() + 1);
//            }
//
//            freeClassPlanRepository.save(plan);
//        }
//
//    }

    private String saveSchedule(Instant scheduleDate, Customer customer) {


        CustomerCommunicationSchedule schedule = new CustomerCommunicationSchedule();

        schedule.setSceduleDate(scheduleDate);
        schedule.setCustomer(customer);
        schedule.setComments("客户从小程序提交的预约");
        schedule.setSourceType("WeChat");
        schedule.setCreatedBy("" + customer.getId());
        RegionUtils.setRegionAbstractAuditingRegionEntity(schedule);

        CustomerCommunicationSchedule savedSchedule = scheduleService.save(schedule);

        String giftCode = RandomStringUtils.randomNumeric(4);
        CustomerScheduleFeedback feedback = new CustomerScheduleFeedback();
        feedback.setCustomer(customer);
        feedback.setSchedule(savedSchedule);
        feedback.setGiftStatus("未领取");
        feedback.setGiftCode(giftCode);

        RegionUtils.setRegionAbstractAuditingRegionEntity(feedback);
        feedbackRepository.save(feedback);

        return giftCode;
    }

    @Override
    public String createScheduleForCustomer(Instant scheduleDate, Customer customer, String sourceType) {

        if (StringUtils.isEmpty(sourceType) || !sourceType.equalsIgnoreCase("wechat")) {
            return "";
        }

        if (scheduleDate == null) {
            return "";
        }

        checkCustomerAlreadyScheduled(customer);

        List<FreeClassPlan> plans = freeClassPlanRepository.findAll();
        FreeClassPlan plan = null;
        for (FreeClassPlan freeClassPlan : plans) {

            if (freeClassPlan.getPlanDate() == null) {
                continue;
            }

            if (DateUtil.isSameday(scheduleDate, freeClassPlan.getPlanDate())) {
                plan = freeClassPlan;
            }
        }

        //无计划的直接预约
        if (plan == null) {
            saveSchedule(scheduleDate, customer);

            return "";
        }

        InstantFilter targetDateFilter = new InstantFilter();
        targetDateFilter.setGreaterOrEqualThan(DateUtil.getBeginningOfInstant(plan.getPlanDate()));
        targetDateFilter.setLessOrEqualThan(DateUtil.getEndingOfInstant(plan.getPlanDate()));

        StringFilter stringFilter = new StringFilter();
        stringFilter.setEquals("WeChat");

        CustomerCommunicationScheduleCriteria criteria = new CustomerCommunicationScheduleCriteria();
        criteria.setSceduleDate(targetDateFilter);
        criteria.setSourceType(stringFilter);

        Integer orderedCount = scheduleQueryService.findByCriteria(criteria).size();

        if (orderedCount >= plan.getLimitCount()) {

            throw new RuntimeException("预约已满！");
        }

        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(customer.getId());

        criteria.setCustomerId(longFilter);

        Integer userOrderCount = scheduleQueryService.findByCriteria(criteria).size();
        if (userOrderCount > 0) {
            throw new RuntimeException("已预约，无法再次预约！");
        }

        String code = saveSchedule(scheduleDate, customer);

        //increase count
        if (plan.getActualCount() == null) {
            plan.setActualCount(1);
        } else {
            plan.setActualCount(plan.getActualCount() + 1);
        }

        freeClassPlanRepository.save(plan);

        return code;
    }

    private void checkCustomerAlreadyScheduled(Customer customer) {

        List<CustomerCommunicationSchedule> schedules = communicationScheduleRepository.findByCustomer_IdAndSourceType(customer.getId(), "WeChat");

        if (!CollectionUtils.isEmpty(schedules)) {

            throw new RuntimeException("已经预约过，无法再次预约！");
        }
    }

    @Override
    public List<FreeClassPlanElement> getSchedulePlanList() {

        List<FreeClassPlan> plans = freeClassPlanRepository.findAll();

        List<FreeClassPlanElement> elements = new ArrayList<>();

        int daysWant = 30;
        Instant now = Instant.now();

        for (int i = 0; i < daysWant; i++) {

            FreeClassPlanElement element = new FreeClassPlanElement();
            Instant scheduleDate = now.plus(i, ChronoUnit.DAYS);
            element.setScheduleDate(scheduleDate);
            element.setId(i + 1);

            FreeClassPlan freeClassPlan = findPlanInDay(plans, scheduleDate);

            String scheduleFormateString = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale( Locale.CHINA )
                .withZone( ZoneId.systemDefault())
                .format(scheduleDate);

            if (freeClassPlan == null) {
                element.setAvaliable(true);
                element.setLabel(scheduleFormateString + "  可预约");

                elements.add(element);

                continue;
            }

            int avaliableCount = (freeClassPlan.getLimitCount() == null? 0: freeClassPlan.getLimitCount()) - (freeClassPlan.getActualCount() == null? 0: freeClassPlan.getActualCount());

            if (avaliableCount < 1) {
                element.setAvaliable(false);
                element.setLabel(scheduleFormateString + "  已约满");
            } else {
                element.setAvaliable(false);
                element.setLabel(scheduleFormateString + "  剩余" + avaliableCount + "席位可约");
            }

            elements.add(element);
        }

        return elements;
    }

    @Override
    public void saveFreeClassAssignHistory(FreeClassRecord newFreeClassRecord, FreeClassRecord oldFreeClassRecord) {

        String olderFollowerLogin = "";
        String olderFollowerName = "";
        if (oldFreeClassRecord != null) {
            olderFollowerLogin = oldFreeClassRecord.getSalesFollower() == null ? "" : oldFreeClassRecord.getSalesFollower().getLogin();
            olderFollowerName = oldFreeClassRecord.getSalesFollower() == null ? "" : oldFreeClassRecord.getSalesFollower().getFirstName();
        }

        String newFollowerLogin = newFreeClassRecord.getSalesFollower() == null ? "" : newFreeClassRecord.getSalesFollower().getLogin();
        String newFollowerName = newFreeClassRecord.getSalesFollower() == null ? "" : newFreeClassRecord.getSalesFollower().getFirstName();

        if (!olderFollowerLogin.equals(newFollowerLogin)) {

            NewOrderAssignHistory newOrderAssignHistory = new NewOrderAssignHistory();

            newOrderAssignHistory = newOrderAssignHistory.newFollowerName(newFollowerName)
                .newFollowerLogin(newFollowerLogin)
                .newFollowerName(newFollowerName)
                .olderFollowerLogin(olderFollowerLogin)
                .olderFollowerName(olderFollowerName)
                .newOrder(newFreeClassRecord);
            newOrderAssignHistory.setRegionId(newFreeClassRecord.getRegionId());

            newOrderAssignHistoryRepository.save(newOrderAssignHistory);

        }

    }

    private FreeClassPlan findPlanInDay(List<FreeClassPlan> plans, Instant scheduleDate) {

        FreeClassPlan plan = null;
        for (FreeClassPlan freeClassPlan : plans) {

            if (freeClassPlan.getPlanDate() == null) {
                continue;
            }

            if (DateUtil.isSameday(scheduleDate, freeClassPlan.getPlanDate())) {
                plan = freeClassPlan;
            }
        }

        return plan;
    }

    /**
     * Get all the freeClassRecords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FreeClassRecord> findAll(Pageable pageable) {
        log.debug("Request to get all FreeClassRecords");
        return freeClassRecordRepository.findAll(pageable);
    }

    /**
     * Get one freeClassRecord by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FreeClassRecord findOne(Long id) {
        log.debug("Request to get FreeClassRecord : {}", id);
        return freeClassRecordRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the  freeClassRecord by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FreeClassRecord : {}", id);
        freeClassRecordRepository.delete(id);
    }

    @Override
    public BatchCustomersResponse upload(BatchCustomers customers) {
        String content = customers.getContent();

        List<FreeClassRecord> freeClassRecords = batchCustomerUtil.batchAnalysisCustomer(content);

        StringBuilder logBuilder = new StringBuilder();
        int importCount = 0, existedCount = 0;
        for (FreeClassRecord freeClassRecord : freeClassRecords) {

            FreeClassRecord existedNewOrder = freeClassRecordRepository.findByContactPhoneNumber(freeClassRecord.getContactPhoneNumber());
//            FreeClassRecord existedNewOrder = freeClassRecordRepository.findByPersonNameAndContactPhoneNumber(freeClassRecord.getPersonName(), freeClassRecord.getContactPhoneNumber());
            if (existedNewOrder == null) {
                save(freeClassRecord);

                importCount++;
            } else {
                existedCount++;
            }
        }

        logBuilder.append("成功导入数据" + importCount + "条，" + " 因为已存在而跳过数据" + existedCount + "条");

        BatchCustomersResponse response = new BatchCustomersResponse();
        response.setSuccess(true);
        response.setMessage(logBuilder.toString());

        return response;
    }

    @Override
    public BatchCustomersResponse upload(InputStream inputStream, String fileName) {
        Workbook workbook = null;

        try {
            if (fileName.endsWith(".xls")) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (fileName.endsWith(".xlsx")) {
//                workbook = new SXSSFWorkbook(inputStream);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<FreeClassRecord> batchSave(List<FreeClassRecord> freeClassRecords) {

        List<FreeClassRecord> records = new ArrayList<>();

        freeClassRecords.forEach(newOrder -> {
            FreeClassRecord freeClassRecord = save(newOrder);

            records.add(freeClassRecord);

            CustomerCriteria customerCriteria = new CustomerCriteria();
            LongFilter longFilter = new LongFilter();
            longFilter.setEquals(freeClassRecord.getId());
            customerCriteria.setNewOrderId(longFilter);

            List<Customer> existCustomers = customerQueryService.findByCriteria(customerCriteria);

            if (!CollectionUtils.isEmpty(existCustomers)) {

                for (Customer existCustomer : existCustomers) {
                    existCustomer.setSalesFollower(freeClassRecord.getSalesFollower());
                    existCustomer.setAssignDate(Instant.now());
                    customerService.save(existCustomer);
                }
            }
        });

        return freeClassRecords;
    }
}
