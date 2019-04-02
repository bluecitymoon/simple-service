package com.pure.service.service.impl;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.ClassCategoryBase;
import com.pure.service.domain.Contract;
import com.pure.service.domain.ContractStatus;
import com.pure.service.domain.ContractTemplate;
import com.pure.service.domain.Course;
import com.pure.service.domain.Customer;
import com.pure.service.domain.CustomerCard;
import com.pure.service.domain.CustomerCommunicationLog;
import com.pure.service.domain.CustomerCommunicationLogType;
import com.pure.service.domain.MarketChannelCategory;
import com.pure.service.domain.Student;
import com.pure.service.domain.StudentClassLog;
import com.pure.service.domain.User;
import com.pure.service.region.RegionIdStorage;
import com.pure.service.region.RegionUtils;
import com.pure.service.repository.CollectionRepository;
import com.pure.service.repository.ContractRepository;
import com.pure.service.repository.ContractStatusRepository;
import com.pure.service.repository.ContractTemplateRepository;
import com.pure.service.repository.CustomerCommunicationLogRepository;
import com.pure.service.repository.CustomerCommunicationLogTypeRepository;
import com.pure.service.repository.CustomerCommunicationScheduleRepository;
import com.pure.service.repository.StudentClassLogRepository;
import com.pure.service.service.CollectionService;
import com.pure.service.service.ContractQueryService;
import com.pure.service.service.ContractService;
import com.pure.service.service.CustomerCardService;
import com.pure.service.service.StudentClassLogQueryService;
import com.pure.service.service.dto.ContractCriteria;
import com.pure.service.service.dto.StudentClassLogCriteria;
import com.pure.service.service.dto.dto.CombinedConsultantReport;
import com.pure.service.service.dto.dto.ConsultantDealRate;
import com.pure.service.service.dto.dto.ConsultantDealRateReport;
import com.pure.service.service.dto.dto.ConsultantWork;
import com.pure.service.service.dto.dto.CourseCategoryBasedReport;
import com.pure.service.service.dto.dto.PackageContractRequest;
import com.pure.service.service.dto.dto.UserBasedConsultantReport;
import com.pure.service.service.dto.dto.WeekElement;
import com.pure.service.service.dto.enumurations.ContractStatusEnum;
import com.pure.service.service.dto.enumurations.CustomerCommunicationLogTypeEnum;
import com.pure.service.service.dto.request.CustomerStatusRequest;
import com.pure.service.service.exception.ContractsExceedLimitException;
import com.pure.service.service.exception.TemplateNotFoundException;
import com.pure.service.service.util.DateUtil;
import com.pure.service.service.util.MathUtil;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Contract.
 */
@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    private final Logger log = LoggerFactory.getLogger(ContractServiceImpl.class);

    private final ContractRepository contractRepository;

    @Autowired
    private ContractTemplateRepository contractTemplateRepository;

    @Autowired
    private CustomerCardService customerCardService;

    @Autowired
    private ContractStatusRepository contractStatusRepository;

    @Autowired
    private CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository;

    @Autowired
    private CustomerCommunicationLogRepository customerCommunicationLogRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private ContractQueryService contractQueryService;

    @Autowired
    private CustomerCommunicationScheduleRepository customerCommunicationScheduleRepository;

    @Autowired
    private StudentClassLogRepository studentClassLogRepository;

    @Autowired
    private StudentClassLogQueryService studentClassLogQueryService;

    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    /**
     * Save a contract.
     *
     * @param contract the entity to save
     * @return the persisted entity
     */
    @Override
    public Contract save(Contract contract) {
        log.debug("Request to save Contract : {}", contract);

//        if (contract.getId() == null) {
//
//            String serialNumber = contract.getSerialNumber();
//            if (!collectionService.customerCardPaid(serialNumber)) {
//
//                throw new CollectionNotPaidException("无法生成合同，应收款未付或未确认到款");
//            }
//        }
        return contractRepository.save(contract);
    }

    /**
     * Get all the contracts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Contract> findAll(Pageable pageable) {
        log.debug("Request to get all Contracts");
        return contractRepository.findAll(pageable);
    }


    /**
     * get all the contracts where Student is null.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Contract> findAllWhereStudentIsNull() {
        log.debug("Request to get all contracts where Student is null");
        return StreamSupport
            .stream(contractRepository.findAll().spliterator(), false)
            .filter(contract -> contract.getStudent() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one contract by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Contract findOne(Long id) {
        log.debug("Request to get Contract : {}", id);
        return contractRepository.findOne(id);
    }

    /**
     * Delete the  contract by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contract : {}", id);
        contractRepository.delete(id);
    }

    @Override
    public boolean contractAlreadyGenerated(String serialNumber) {

        List<Contract> existedContracts = contractRepository.findBySerialNumber(serialNumber);

        return !CollectionUtils.isEmpty(existedContracts);
    }

    @Override
    public List<Contract> generatePackagedContract(PackageContractRequest packageContractRequest) throws TemplateNotFoundException, ContractsExceedLimitException {

        List<ContractTemplate> templates = contractTemplateRepository.findByContractPackage_Id(packageContractRequest.getPackageId());

        if (CollectionUtils.isEmpty(templates)) throw new TemplateNotFoundException("该套餐没有套餐内容，需要添加合同模板");

        CustomerCard customerCard = customerCardService.findOne(packageContractRequest.getCustomerCardId());

//        String serialNumber = customerCard.getSerialNumber();
//        if (!collectionService.customerCardPaid(serialNumber)) {
//
//            throw new CollectionNotPaidException("无法生成合同，应收款未付或未确认到款");
//        }

        List<Contract> existedContracts = contractRepository.findByCustomerCard_Id(customerCard.getId());
        if (!CollectionUtils.isEmpty(existedContracts)) {

            if (existedContracts.size() >= templates.size()) {
                throw new ContractsExceedLimitException("已经存在" + existedContracts.size() + "份合同，无法生成更多合同，该合同套餐可以生成" + templates.size() + "份合同");
            }
        }

        ContractStatus generated = contractStatusRepository.findByCode(ContractStatusEnum.generated.name());

        Long regionId = Long.valueOf(RegionIdStorage.getRegionIdContext());
        List<Contract> contracts = new ArrayList<>();
        for (ContractTemplate template : templates) {
            Contract contract = new Contract();

            contract.setSignDate(customerCard.getSignDate());
            contract.setCustomerCard(customerCard);
            contract.setCustomer(customerCard.getCustomer());
            contract.setTotalMoneyAmount(template.getTotalMoneyAmount());
            contract.setSerialNumber(customerCard.getSerialNumber());
            contract.setActive(false);
//            contract.setCourse(customerCard.getCourse());
            contract.setContractNumber(generateContractNumber());
            contract.setTotalHours(template.getTotalHours());
            contract.setContractNature(template.getContractNature());
            contract.setRegionId(regionId);
            contract.setContractStatus(generated);

            Integer promotionAmount = template.getPromotionAmount() == null? 0: template.getPromotionAmount();
            Float totalMoneyAmount = template.getTotalMoneyAmount() == null? 0: template.getTotalMoneyAmount();
            Float moneyShouldCollected = totalMoneyAmount - promotionAmount;

            contract.setMoneyShouldCollected(moneyShouldCollected);

            contracts.add(contract);

            //如果这个客户只有一个学员，将这个合同分配到这个学员

        }

        saveContractLog(customerCard.getCustomer(), customerCard.getSerialNumber());

        return contractRepository.save(contracts);
    }

    private void saveContractLog(Customer customer, String serialNumber) {
        Long regionId = Long.valueOf(RegionIdStorage.getRegionIdContext());
        CustomerCommunicationLog log = new CustomerCommunicationLog();
        log.setCustomer(customer);
        log.setComments("客户生成套餐合同，流水号 " + serialNumber);
        log.setRegionId(regionId);
        CustomerCommunicationLogType type = customerCommunicationLogTypeRepository.findByCode(CustomerCommunicationLogTypeEnum.contract_generated.name());
        log.setLogType(type);

        customerCommunicationLogRepository.save(log);
    }

    @Override
    public String generateContractNumber() {

        Long currentInstant = Instant.now().getEpochSecond();

        String text = "" + currentInstant + RandomStringUtils.randomNumeric(6);

        return text;
    }

    @Override
    public CombinedConsultantReport getCourseConsultantWorkReport(CustomerStatusRequest request) {

        CombinedConsultantReport combinedConsultantReport = new CombinedConsultantReport();

        List<ConsultantWork> consultantWorks = new ArrayList<>();

        Map<MarketChannelCategory, List<Contract>> channelCategorySetMap = new HashMap<>();
        ConsultantDealRateReport consultantDealRateReport = new ConsultantDealRateReport();
        Map<User, List<Contract>> userListMap = new HashMap<>();
        Map<ClassCategoryBase,  List<Contract>> courseCategoryBaseListMap = new HashMap<>();

//        List<Contract> totalContracts = new ArrayList<>();

        List<WeekElement> weekElements = DateUtil.getWeekElementsBetween(request.getStartDate(), request.getEndDate());
        weekElements.forEach(weekElement -> {

            ContractCriteria contractCriteria = new ContractCriteria();

            InstantFilter signDate = new InstantFilter();
            signDate.setLessOrEqualThan(weekElement.getEnd());
            signDate.setGreaterOrEqualThan(weekElement.getStart());

            contractCriteria.setSignDate(signDate);

            LongFilter regionId = new LongFilter();
            regionId.setEquals(RegionUtils.getRegionIdForCurrentUser());

            contractCriteria.setRegionId(regionId);

            List<Contract> contracts = contractQueryService.findByCriteria(contractCriteria);

            Float totalMoneyShouldCollected = 0f;
            for (Contract contract : contracts) {

                //bad data
                if (contract.getTotalMoneyAmount() == null) {
                    continue;
                }

                Float moneyShouldCollected = contract.getMoneyShouldCollected();
                if (contract.getMoneyShouldCollected() == null) {

                    Float promotionAmount = contract.getPromotionAmount() == null ? 0 : contract.getPromotionAmount();
                    moneyShouldCollected = contract.getTotalMoneyAmount() - promotionAmount;
                }

                totalMoneyShouldCollected = totalMoneyShouldCollected + moneyShouldCollected;

                //group count with market channel
                MarketChannelCategory marketChannelCategory = contract.getCustomer().getChannel();

                if (marketChannelCategory == null) {
                    marketChannelCategory = new MarketChannelCategory(-1L, "未知渠道", "Unknown", "");
                }
                List<Contract> contractList = channelCategorySetMap.get(marketChannelCategory);
                if (contractList == null) {
                    contractList = new ArrayList<>();

                    channelCategorySetMap.put(marketChannelCategory, contractList);

                }

                contractList.add(contract);

                //group contracts with consultant
                List<Contract> consultantContracts = userListMap.get(contract.getCustomer().getCourseConsultant());
                if (consultantContracts == null) {
                    consultantContracts = new ArrayList<>();

                    userListMap.put(contract.getCustomer().getCourseConsultant(), consultantContracts);
                }

                consultantContracts.add(contract);

                //group contracts with course category
                Course course = contract.getCourse();
                ClassCategoryBase classCategoryBaseKey = new ClassCategoryBase(-1L, "未知课程", "Unknown");
                if (course != null) {
                    classCategoryBaseKey = course.getClassCategoryBase();
                }

                List<Contract> courseCategoryContracts = courseCategoryBaseListMap.get(classCategoryBaseKey);
                if (courseCategoryContracts == null) {
                    courseCategoryContracts = new ArrayList<>();

                    courseCategoryBaseListMap.put(classCategoryBaseKey, courseCategoryContracts);
                }
                courseCategoryContracts.add(contract);
            }

            Integer visitedCount = customerCommunicationScheduleRepository.getCustomerVisitedCountBetween(weekElement.getStart(), weekElement.getEnd(), RegionUtils.getRegionIdForCurrentUser());

            ConsultantWork consultantWork = new ConsultantWork();
            consultantWork.setContracts(contracts);
            consultantWork.setDealedMoneyAmount(totalMoneyShouldCollected);
            consultantWork.setWeekFromDate(weekElement.getStart());
            consultantWork.setWeekEndDate(weekElement.getEnd());
            consultantWork.setWeekName(weekElement.getWeekIndex());
            consultantWork.setContracts(contracts);
            consultantWork.setVisitedCount(visitedCount);

            consultantWorks.add(consultantWork);

        });


        calculateChannelBasedReport(channelCategorySetMap, consultantDealRateReport, request.getStartDate(), request.getEndDate());

        List<UserBasedConsultantReport> userBasedConsultantReports = calculateConsultantBasedReport(userListMap, request.getStartDate(), request.getEndDate());
        CourseCategoryBasedReport report = getCourseCategoryBasedReport(courseCategoryBaseListMap);

        combinedConsultantReport.setConsultantWorks(consultantWorks);
        combinedConsultantReport.setUserBasedConsultantReports(userBasedConsultantReports);
        combinedConsultantReport.setConsultantDealCount(consultantDealRateReport);
        combinedConsultantReport.setCourseCategoryBasedReport(report);

        return combinedConsultantReport;
    }

    @Override
    public void refreshContractBalance(Long studentId) {

        log.debug("Refreshing contract balance for student {}", studentId);

        List<Contract> contracts = contractRepository.findByStudent_Id(studentId);

        if (CollectionUtils.isEmpty(contracts)) return;

        List<StudentClassLog> studentClassLogs = studentClassLogRepository.findByStudent_Id(studentId);
        if (CollectionUtils.isEmpty(studentClassLogs)) return;

        for (StudentClassLog classLog : studentClassLogs) {

            ClassArrangement classArrangement = classLog.getArrangement();
            Student student = classLog.getStudent();
//
//            contracts.
//                stream()
//                .filter(contract -> contract.getProduct().equals(classArrangement.getClazz()))
//                .findFirst()

        }
    }

    @Override
    public void refreshContractBalance() {

        List<Contract> contracts = contractRepository.findAll();

        for (Contract contract : contracts) {

            if (contract.getTotalHours() == null || contract.getTotalHours() == 0) {
                continue;
            }

            Student student = contract.getStudent();

            if (student == null) continue;

            StudentClassLogCriteria criteria = new StudentClassLogCriteria();

            LongFilter studentId = new LongFilter();
            studentId.setEquals(student.getId());

            criteria.setStudentId(studentId);

            criteria.setArrangementStart(contract.getStartDate());
            criteria.setArrangementEnd(contract.getEndDate());

            List<StudentClassLog> studentClassLogs = studentClassLogQueryService.findByCriteria(criteria);

            if (CollectionUtils.isEmpty(studentClassLogs)) {
                contract.setHoursTaken(0);
            }

            //合同的班级匹配或者合同的课程匹配
            List<StudentClassLog> logs = studentClassLogs.stream().filter(log -> {

                if (contract.getProduct() != null ) {

                    return log.getArrangement().getClazz().equals(contract.getProduct());

                } else {

                    if (log.getArrangement().getClazz().getCourse() == null) {
                        return false;
                    }

                    return log.getArrangement().getClazz().getCourse().equals(contract.getCourse());
                }

            }).collect(Collectors.toList());

            int total = 0;
            if (CollectionUtils.isEmpty(logs)) {
                contract.setHoursTaken(0);

            }

            for (StudentClassLog studentClassLog : logs) {
                total += studentClassLog.getArrangement().getConsumeClassCount();
            }

            contract.setHoursTaken(total);

            contractRepository.save(contract);
        }

    }



    private CourseCategoryBasedReport getCourseCategoryBasedReport(Map<ClassCategoryBase, List<Contract>> courseCategoryBaseListMap) {
        CourseCategoryBasedReport report = new CourseCategoryBasedReport();

        for (Map.Entry<ClassCategoryBase, List<Contract>> entry : courseCategoryBaseListMap.entrySet()) {

            ClassCategoryBase key = entry.getKey();

            report.getCategories().add(key.getName());
            report.getContracts().add(entry.getValue());

            Set<Customer> customers = new HashSet<>();
            for (Contract contract : entry.getValue()) {

                if (contract.getCustomer() != null) {
                    customers.add(contract.getCustomer());
                }
            }

            report.getCounter().add(customers.size());

        }
        return report;
    }

    private List<UserBasedConsultantReport> calculateConsultantBasedReport(Map<User, List<Contract>> userListMap, Instant start, Instant end) {

        List<UserBasedConsultantReport> reports = new ArrayList<>();

        for (Map.Entry<User, List<Contract>> entry : userListMap.entrySet()) {
            User consultant = entry.getKey();

            UserBasedConsultantReport report = new UserBasedConsultantReport();

            report.setConsultantName(consultant.getFirstName());

            Float total = 0f;
            for (Contract contract : entry.getValue()) {

                report.getContracts().add(contract);
                report.getCards().add(contract.getCustomerCard());

                if (contract.getTotalMoneyAmount() == null) {
                    continue;
                }

                Float moneyShouldCollected = contract.getMoneyShouldCollected();
                if (contract.getMoneyShouldCollected() == null) {

                    Float promotionAmount = contract.getPromotionAmount() == null ? 0 : contract.getPromotionAmount();
                    moneyShouldCollected = contract.getTotalMoneyAmount() - promotionAmount;
                }

                total = total + moneyShouldCollected;
            }

            report.setTotalMoneyAmount(total);

            Integer visitedCount = customerCommunicationScheduleRepository.getCustomerVisitedCountBetween(start, end, RegionUtils.getRegionIdForCurrentUser(), consultant.getId());
            report.setCardCount(visitedCount);
            Integer dealCardCount = report.getCards().size();
            report.setCardCount(dealCardCount);

            Float cardRate = 0f;
            if (dealCardCount > 0 && visitedCount > 0) {
                cardRate = MathUtil.division(dealCardCount, visitedCount, 2);
            }

            report.setCardRate(cardRate);
            report.setShowCount(visitedCount);

            reports.add(report);

        }

        return reports;
    }

    private void calculateChannelBasedReport(Map<MarketChannelCategory, List<Contract>> channelCategorySetMap, ConsultantDealRateReport consultantDealRateReport, Instant start, Instant end) {

        for (Map.Entry<MarketChannelCategory, List<Contract>> entry : channelCategorySetMap.entrySet()) {

            String channelName = entry.getKey().getName();

            consultantDealRateReport.getChannelNames().add(channelName);

            ConsultantDealRate consultantDealRate = new ConsultantDealRate();

            Float total = 0f;
            Set<Customer> customers = new HashSet<>();
            for (Contract contract : entry.getValue()) {


                if (contract.getTotalMoneyAmount() == null) {
                    continue;
                }

                Float moneyShouldCollected = contract.getMoneyShouldCollected();
                if (contract.getMoneyShouldCollected() == null) {

                    Float promotionAmount = contract.getPromotionAmount() == null ? 0 : contract.getPromotionAmount();
                    moneyShouldCollected = contract.getTotalMoneyAmount() - promotionAmount;
                }

                total = total + moneyShouldCollected;
                customers.add(contract.getCustomer());
            }

            Integer visitedCount = customerCommunicationScheduleRepository.getCustomerVisitedCountBetweenWithMarketId(start, end, RegionUtils.getRegionIdForCurrentUser(), entry.getKey().getId());

            consultantDealRate.setTotalMoney(total);
            consultantDealRate.setDeal(customers.size());
            consultantDealRate.setVisit(visitedCount);

            Integer dealCardCount = customers.size();

            Float rate = 0f;
            if (dealCardCount > 0 && visitedCount > 0) {
                rate = MathUtil.division(dealCardCount, visitedCount, 2);
            }

            consultantDealRate.setRate(rate);
            consultantDealRateReport.getChannelCustomerCount().add(consultantDealRate);

        }
    }
}
