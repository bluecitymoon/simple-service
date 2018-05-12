package com.pure.service.service.impl;

import com.pure.service.domain.FreeClassRecord;
import com.pure.service.domain.NewOrderWechatUserInfo;
import com.pure.service.repository.NewOrderWechatUserInfoRepository;
import com.pure.service.service.FreeClassRecordService;
import com.pure.service.service.NewOrderWechatUserInfoQueryService;
import com.pure.service.service.NewOrderWechatUserInfoService;
import com.pure.service.service.OpenIdService;
import com.pure.service.service.dto.NewOrderWechatUserInfoCriteria;
import io.github.jhipster.service.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;


/**
 * Service Implementation for managing NewOrderWechatUserInfo.
 */
@Service
@Transactional
public class NewOrderWechatUserInfoServiceImpl implements NewOrderWechatUserInfoService{

    private final Logger log = LoggerFactory.getLogger(NewOrderWechatUserInfoServiceImpl.class);

    private final NewOrderWechatUserInfoRepository newOrderWechatUserInfoRepository;
    private final NewOrderWechatUserInfoQueryService newOrderWechatUserInfoQueryService;
    private final OpenIdService openIdService;
    private final FreeClassRecordService freeClassRecordService;

    public NewOrderWechatUserInfoServiceImpl(NewOrderWechatUserInfoRepository newOrderWechatUserInfoRepository,
                                             NewOrderWechatUserInfoQueryService newOrderWechatUserInfoQueryService,
                                             OpenIdService openIdService,
                                             FreeClassRecordService freeClassRecordService) {
        this.newOrderWechatUserInfoRepository = newOrderWechatUserInfoRepository;
        this.newOrderWechatUserInfoQueryService = newOrderWechatUserInfoQueryService;
        this.openIdService = openIdService;
        this.freeClassRecordService = freeClassRecordService;

    }

    /**
     * Save a newOrderWechatUserInfo.
     *
     * @param newOrderWechatUserInfo the entity to save
     * @return the persisted entity
     */
    @Override
    public NewOrderWechatUserInfo save(NewOrderWechatUserInfo newOrderWechatUserInfo) {
        log.debug("Request to save NewOrderWechatUserInfo : {}", newOrderWechatUserInfo);
        return newOrderWechatUserInfoRepository.save(newOrderWechatUserInfo);
    }

    @Override
    public NewOrderWechatUserInfo migrate(NewOrderWechatUserInfo newOrderWechatUserInfo) {

        log.debug("Migrating wechat user {} ", newOrderWechatUserInfo);

        Set<FreeClassRecord> newOrdersAssigning = newOrderWechatUserInfo.getNewOrders();

        String openId = openIdService.getTencentOpenId(newOrderWechatUserInfo.getCode());

        log.debug("Getting user open id and find " + openId);
        NewOrderWechatUserInfo userInfoObject = null;

        if (!StringUtils.isEmpty(openId)) {

            newOrderWechatUserInfo.setOpenId(openId);

            NewOrderWechatUserInfoCriteria newOrderWechatUserInfoCriteria = new NewOrderWechatUserInfoCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setEquals(openId);
            newOrderWechatUserInfoCriteria.setOpenId(stringFilter);
            List<NewOrderWechatUserInfo> userInfos = newOrderWechatUserInfoQueryService.findByCriteria(newOrderWechatUserInfoCriteria);


            if (CollectionUtils.isEmpty(userInfos)) {

                userInfoObject = newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
            } else {

                userInfoObject = userInfos.get(0);

            }
        } else {
            log.error("Open id is empty for user {} ", newOrderWechatUserInfo );

        }


        FreeClassRecord newOrderCreated = freeClassRecordService.findOne(newOrdersAssigning.iterator().next().getId());
        newOrderCreated.setNewOrderWechatUserInfo(userInfoObject);

        freeClassRecordService.save(newOrderCreated);

        return userInfoObject;
    }

    /**
     *  Get all the newOrderWechatUserInfos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NewOrderWechatUserInfo> findAll(Pageable pageable) {
        log.debug("Request to get all NewOrderWechatUserInfos");
        return newOrderWechatUserInfoRepository.findAll(pageable);
    }

    /**
     *  Get one newOrderWechatUserInfo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NewOrderWechatUserInfo findOne(Long id) {
        log.debug("Request to get NewOrderWechatUserInfo : {}", id);
        return newOrderWechatUserInfoRepository.findOne(id);
    }

    /**
     *  Delete the  newOrderWechatUserInfo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NewOrderWechatUserInfo : {}", id);
        newOrderWechatUserInfoRepository.delete(id);
    }
}
