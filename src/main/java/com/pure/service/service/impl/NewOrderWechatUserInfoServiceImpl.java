package com.pure.service.service.impl;

import com.pure.service.domain.ClassCategory;
import com.pure.service.domain.FreeClassRecord;
import com.pure.service.domain.NewOrderWechatUserInfo;
import com.pure.service.domain.WechatToken;
import com.pure.service.repository.NewOrderWechatUserInfoRepository;
import com.pure.service.service.FreeClassRecordService;
import com.pure.service.service.MarketingQrcodeService;
import com.pure.service.service.NewOrderWechatUserInfoQueryService;
import com.pure.service.service.NewOrderWechatUserInfoService;
import com.pure.service.service.OpenIdService;
import com.pure.service.service.dto.NewOrderWechatUserInfoCriteria;
import com.pure.service.service.dto.UserInfo;
import com.pure.service.service.dto.dto.WechatFreeClassNotify;
import com.pure.service.service.dto.dto.WechatNotify;
import com.pure.service.service.dto.dto.WechatNotifyValue;
import io.github.jhipster.service.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * Service Implementation for managing NewOrderWechatUserInfo.
 */
@Service
@Transactional
public class NewOrderWechatUserInfoServiceImpl implements NewOrderWechatUserInfoService{
    @Qualifier("noopSslRestTemplate")
    @Autowired
    private RestTemplate noopSslRestTemplate;

    private final Logger log = LoggerFactory.getLogger(NewOrderWechatUserInfoServiceImpl.class);

    private final NewOrderWechatUserInfoRepository newOrderWechatUserInfoRepository;
    private final NewOrderWechatUserInfoQueryService newOrderWechatUserInfoQueryService;
    private final OpenIdService openIdService;
    private final FreeClassRecordService freeClassRecordService;
    private final MarketingQrcodeService marketingQrcodeService;

    public NewOrderWechatUserInfoServiceImpl(NewOrderWechatUserInfoRepository newOrderWechatUserInfoRepository,
                                             NewOrderWechatUserInfoQueryService newOrderWechatUserInfoQueryService,
                                             OpenIdService openIdService,
                                             FreeClassRecordService freeClassRecordService,
                                             MarketingQrcodeService marketingQrcodeService) {
        this.newOrderWechatUserInfoRepository = newOrderWechatUserInfoRepository;
        this.newOrderWechatUserInfoQueryService = newOrderWechatUserInfoQueryService;
        this.openIdService = openIdService;
        this.freeClassRecordService = freeClassRecordService;
        this.marketingQrcodeService = marketingQrcodeService;

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

        UserInfo userInfo = openIdService.getTencentOpenId(newOrderWechatUserInfo.getCode());

        String openId = userInfo.getOpenid();

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

            //Notify user
            WechatToken wechatToken = marketingQrcodeService.getWechatToken();
            String wechatNotifyUrl = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + wechatToken.getAccessToken();

            WechatNotify wechatNotify = new WechatNotify();
            wechatNotify.setAccess_token(wechatToken.getAccessToken());
            wechatNotify.setTemplate_id("Imn2TkIzUh1I4hvwsT1pi8FnfdENu9i7CjLPkflcne0");
            wechatNotify.setTouser(openId);

            WechatFreeClassNotify data = new WechatFreeClassNotify();
            Set<FreeClassRecord> freeClassRecords = newOrderWechatUserInfo.getNewOrders();
            for (FreeClassRecord freeClassRecord : freeClassRecords) {

                WechatNotifyValue name1 = new WechatNotifyValue();
                name1.setValue(freeClassRecord.getPersonName());

                data.setName1(name1);

                WechatNotifyValue phrase2 = new WechatNotifyValue();
                phrase2.setValue("已排课");

                data.setPhrase2(phrase2);

                WechatNotifyValue thing3 = new WechatNotifyValue();
                thing3.setValue("请各位家长带宝贝们提前5-10分钟到校，提前准备好，课前喝点水，上厕所，也有利于宝贝们能提前更好地融入课堂");

                data.setThing3(thing3);

                WechatNotifyValue thing4 = new WechatNotifyValue();
                StringBuilder className = new StringBuilder();
                for (ClassCategory classCategory : freeClassRecord.getClassCategories()) {
                    className.append(classCategory.getName());
                    className.append(" ");
                }
                thing4.setValue(className.toString());

                data.setThing4(thing4);

                DateTimeFormatter formatter =
                    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.CHINA )
                        .withZone( ZoneId.systemDefault() );
                WechatNotifyValue thing5 = new WechatNotifyValue();
                thing5.setValue(formatter.format(freeClassRecord.getScheduleDate()));

                data.setThing5(thing5);

            }

            wechatNotify.setData(data);

            log.info("Notify request {} ", wechatNotify);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity requestEntity = new HttpEntity(wechatNotify, headers);

            ResponseEntity<byte[]> responseEntity = noopSslRestTemplate.exchange(wechatNotifyUrl, HttpMethod.POST, requestEntity, byte[].class);

            log.info("Notify response is {}", requestEntity);

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
