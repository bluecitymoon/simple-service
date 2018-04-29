package com.pure.service.service.impl;

import com.pure.service.service.NewOrderWechatUserInfoService;
import com.pure.service.domain.NewOrderWechatUserInfo;
import com.pure.service.repository.NewOrderWechatUserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing NewOrderWechatUserInfo.
 */
@Service
@Transactional
public class NewOrderWechatUserInfoServiceImpl implements NewOrderWechatUserInfoService{

    private final Logger log = LoggerFactory.getLogger(NewOrderWechatUserInfoServiceImpl.class);

    private final NewOrderWechatUserInfoRepository newOrderWechatUserInfoRepository;

    public NewOrderWechatUserInfoServiceImpl(NewOrderWechatUserInfoRepository newOrderWechatUserInfoRepository) {
        this.newOrderWechatUserInfoRepository = newOrderWechatUserInfoRepository;
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
