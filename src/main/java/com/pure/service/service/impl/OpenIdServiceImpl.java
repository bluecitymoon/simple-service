package com.pure.service.service.impl;

import com.pure.service.service.OpenIdService;
import com.pure.service.service.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenIdServiceImpl implements OpenIdService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${application.wechatmp.appid}")
    private String appid;

    @Value("${application.wechatmp.secret}")
    private String secret;

    @Override
    public String getTencentOpenId(String code) {

        //TODO move configuration to yml
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&grant_type=authorization_code&js_code=" + code;

        ResponseEntity<UserInfo> userInfoResponse = restTemplate.getForEntity(url, UserInfo.class);

        return userInfoResponse.getBody().getOpenid();
    }
}
