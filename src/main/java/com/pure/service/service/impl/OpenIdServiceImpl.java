package com.pure.service.service.impl;

import com.pure.service.security.SecurityUtils;
import com.pure.service.service.OpenIdService;
import com.pure.service.service.dto.UserInfo;
import com.pure.service.service.dto.dto.DecodedPhoneNumberRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenIdServiceImpl implements OpenIdService {

    @Autowired
    private RestTemplate restTemplate;

//    @Value("${simple.wechatmp.appid}")
    private String appid = "wx705a848318546f57";

//    @Value("#{simple.wechatmp.secret}")
    private String secret = "0bf25b973cafd1f277b81e8f5e812620";

    @Override
    public UserInfo getTencentOpenId(String code) {

        //TODO move configuration to yml
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&grant_type=authorization_code&js_code=" + code;

        ResponseEntity<UserInfo> userInfoResponse = restTemplate.getForEntity(url, UserInfo.class);

        return userInfoResponse.getBody();
    }

    @Override
    public String getWechatDecodedPhoneNumber(DecodedPhoneNumberRequest request) {
        return SecurityUtils.decryptData(request.getPhoneNumber(), request.getSessionKey(), request.getIv());
    }
}
