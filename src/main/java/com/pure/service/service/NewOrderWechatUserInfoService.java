package com.pure.service.service;

import com.pure.service.domain.NewOrderWechatUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing NewOrderWechatUserInfo.
 */
public interface NewOrderWechatUserInfoService {

    /**
     * Save a newOrderWechatUserInfo.
     *
     * @param newOrderWechatUserInfo the entity to save
     * @return the persisted entity
     */
    NewOrderWechatUserInfo save(NewOrderWechatUserInfo newOrderWechatUserInfo);

    /**
     *  Get all the newOrderWechatUserInfos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NewOrderWechatUserInfo> findAll(Pageable pageable);

    /**
     *  Get the "id" newOrderWechatUserInfo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NewOrderWechatUserInfo findOne(Long id);

    /**
     *  Delete the "id" newOrderWechatUserInfo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
