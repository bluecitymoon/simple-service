package com.pure.service.service.impl;

import com.pure.service.domain.CustomerCommunicationLog;
import com.pure.service.domain.CustomerCommunicationLogType;
import com.pure.service.domain.FreeClassRecord;
import com.pure.service.domain.NewOrderAssignHistory;
import com.pure.service.repository.CustomerCommunicationLogRepository;
import com.pure.service.repository.CustomerCommunicationLogTypeRepository;
import com.pure.service.repository.FreeClassRecordRepository;
import com.pure.service.repository.NewOrderAssignHistoryRepository;
import com.pure.service.service.FreeClassRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Service Implementation for managing FreeClassRecord.
 */
@Service
@Transactional
public class FreeClassRecordServiceImpl implements FreeClassRecordService{

    private final Logger log = LoggerFactory.getLogger(FreeClassRecordServiceImpl.class);

    private final FreeClassRecordRepository freeClassRecordRepository;
    private final NewOrderAssignHistoryRepository newOrderAssignHistoryRepository;
    private final CustomerCommunicationLogRepository customerCommunicationLogRepository;
    private final CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository;

    public FreeClassRecordServiceImpl(FreeClassRecordRepository freeClassRecordRepository,
                                      NewOrderAssignHistoryRepository newOrderAssignHistoryRepository,
                                      CustomerCommunicationLogRepository customerCommunicationLogRepository,
                                      CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository) {
        this.freeClassRecordRepository = freeClassRecordRepository;
        this.newOrderAssignHistoryRepository = newOrderAssignHistoryRepository;
        this.customerCommunicationLogRepository = customerCommunicationLogRepository;
        this.customerCommunicationLogTypeRepository = customerCommunicationLogTypeRepository;
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

        if (freeClassRecord.getId() == null){
            freeClassRecord.setStatus("新单");
        }

        boolean newOrder = false;
        if (freeClassRecord.getId() != null) {

            FreeClassRecord oldFreeClassRecord = freeClassRecordRepository.findOne(freeClassRecord.getId());

            String olderFollowerLogin = oldFreeClassRecord.getSalesFollower() == null? "" : oldFreeClassRecord.getSalesFollower().getLogin();
            String olderFollowerName = oldFreeClassRecord.getSalesFollower() == null? "" : oldFreeClassRecord.getSalesFollower().getFirstName();
            String newFollowerLogin = freeClassRecord.getSalesFollower() == null? "" : freeClassRecord.getSalesFollower().getLogin();
            String newFollowerName = freeClassRecord.getSalesFollower() == null? "": freeClassRecord.getSalesFollower().getFirstName();

            if (!olderFollowerLogin.equals(newFollowerLogin)) {

                NewOrderAssignHistory newOrderAssignHistory = new NewOrderAssignHistory();

                newOrderAssignHistory = newOrderAssignHistory.newFollowerName(newFollowerName)
                    .newFollowerLogin(newFollowerLogin)
                    .newFollowerName(newFollowerName)
                    .olderFollowerLogin(olderFollowerLogin)
                    .olderFollowerName(olderFollowerName)
                    .newOrder(freeClassRecord);

                newOrderAssignHistoryRepository.save(newOrderAssignHistory);
            }

        } else {

            newOrder = true;
        }

        log.debug("Operation on new order ? " + newOrder);

        FreeClassRecord saved = freeClassRecordRepository.saveAndFlush(freeClassRecord);

        //TODO fixing duplicate log issue
        log.debug("Saved new order {} " + saved );
        //Save log for new order created.
        if (newOrder) {

            CustomerCommunicationLogType newCreateOrderType = customerCommunicationLogTypeRepository.findByCode(CustomerCommunicationLogTypeEnum.new_order_created.name());
            CustomerCommunicationLog customerCommunicationLog = new CustomerCommunicationLog();
            customerCommunicationLog.comments("新单记录创建成功");

            customerCommunicationLog.setLogType(newCreateOrderType);
            customerCommunicationLog.freeClassRecord(saved);

            CustomerCommunicationLog savedLog = customerCommunicationLogRepository.save(customerCommunicationLog);

            log.debug("Saved customer log for the new order created {}", savedLog);

        }

        return saved;
    }

    /**
     *  Get all the freeClassRecords.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FreeClassRecord> findAll(Pageable pageable) {
        log.debug("Request to get all FreeClassRecords");
        return freeClassRecordRepository.findAll(pageable);
    }

    /**
     *  Get one freeClassRecord by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FreeClassRecord findOne(Long id) {
        log.debug("Request to get FreeClassRecord : {}", id);
        return freeClassRecordRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  freeClassRecord by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FreeClassRecord : {}", id);
        freeClassRecordRepository.delete(id);
    }

    @Override
    public List<FreeClassRecord> batchSave(List<FreeClassRecord> freeClassRecords) {

        List<FreeClassRecord> records = new ArrayList<>();

        freeClassRecords.forEach(newOrder -> records.add(save(newOrder)));

        return freeClassRecordRepository.save(freeClassRecords);
    }
}
