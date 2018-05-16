package com.pure.service.repository;

import com.pure.service.domain.MarketingQrcode;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MarketingQrcode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketingQrcodeRepository extends JpaRepository<MarketingQrcode, Long>, JpaSpecificationExecutor<MarketingQrcode> {

}
