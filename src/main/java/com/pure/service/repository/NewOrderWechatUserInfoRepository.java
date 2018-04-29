package com.pure.service.repository;

import com.pure.service.domain.NewOrderWechatUserInfo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NewOrderWechatUserInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewOrderWechatUserInfoRepository extends JpaRepository<NewOrderWechatUserInfo, Long>, JpaSpecificationExecutor<NewOrderWechatUserInfo> {

}
