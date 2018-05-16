package com.pure.service.service.impl;

import com.google.common.collect.Lists;
import com.pure.service.domain.Asset;
import com.pure.service.domain.MarketingQrcode;
import com.pure.service.domain.QrCodeRequestBody;
import com.pure.service.domain.User;
import com.pure.service.domain.WechatToken;
import com.pure.service.repository.MarketingQrcodeRepository;
import com.pure.service.repository.UserRepository;
import com.pure.service.service.AssetService;
import com.pure.service.service.MarketingQrcodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


/**
 * Service Implementation for managing MarketingQrcode.
 */
@Service
@Transactional
public class MarketingQrcodeServiceImpl implements MarketingQrcodeService{

    private final Logger log = LoggerFactory.getLogger(MarketingQrcodeServiceImpl.class);

    private final MarketingQrcodeRepository marketingQrcodeRepository;

    private final RestTemplate restTemplate;

    private final AssetService assetService;
    private final UserRepository userRepository;

    public MarketingQrcodeServiceImpl(MarketingQrcodeRepository marketingQrcodeRepository,
                                      RestTemplate restTemplate,
                                      AssetService assetService,
                                      UserRepository userRepository) {
        this.marketingQrcodeRepository = marketingQrcodeRepository;
        this.restTemplate = restTemplate;
        this.assetService = assetService;
        this.userRepository = userRepository;
    }

    /**
     * Save a marketingQrcode.
     *
     * @param marketingQrcode the entity to save
     * @return the persisted entity
     */
    @Override
    public MarketingQrcode save(MarketingQrcode marketingQrcode) {
        log.debug("Request to save MarketingQrcode : {}", marketingQrcode);
        return marketingQrcodeRepository.save(marketingQrcode);
    }

    /**
     *  Get all the marketingQrcodes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MarketingQrcode> findAll(Pageable pageable) {
        log.debug("Request to get all MarketingQrcodes");
        return marketingQrcodeRepository.findAll(pageable);
    }

    /**
     *  Get one marketingQrcode by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MarketingQrcode findOne(Long id) {
        log.debug("Request to get MarketingQrcode : {}", id);
        return marketingQrcodeRepository.findOne(id);
    }

    /**
     *  Delete the  marketingQrcode by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarketingQrcode : {}", id);
        marketingQrcodeRepository.delete(id);
    }

    @Override
    public MarketingQrcode generate(Long id) throws IOException {

        //TODO dynamic configuration
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx705a848318546f57&secret=0bf25b973cafd1f277b81e8f5e812620";

        WechatToken token = restTemplate.getForObject(tokenUrl, WechatToken.class);
        log.debug("Got wechat token {} ", token);

        String qrCodeUrl = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=" + token.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Lists.newArrayList(MediaType.IMAGE_JPEG));

        QrCodeRequestBody qrCodeRequestBody = new QrCodeRequestBody();
        qrCodeRequestBody.setPath("pages/introduction/index?agentId=" + id);
        qrCodeRequestBody.setWidth(430);

        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(qrCodeUrl, qrCodeRequestBody, byte[].class);

        String resourceId = UUID.randomUUID().toString();
        String serverFileName = "/images/qrcode/" + resourceId + ".jpg";
        Path path = Paths.get(serverFileName);
        Files.write(path, responseEntity.getBody());

        String fullPath = "http://www.puzhenchina.com" + serverFileName;
        Asset asset = new Asset();
        asset.setResourceId(resourceId);
        asset.setFullPath(fullPath);
        asset.setType("jpg");
        asset.setComments("QR code image for agent " + id);

        assetService.save(asset);
        log.debug("Asset saved for image, got {}", asset);

        User agent = userRepository.findOne(id);
        MarketingQrcode marketingQrcode = new MarketingQrcode();
        marketingQrcode.agent(agent);
        marketingQrcode.setFileUrl(fullPath);

        log.debug("Saving qr code information {}", marketingQrcode);

        return save(marketingQrcode);
    }
}
