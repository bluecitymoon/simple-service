package com.pure.service.service.impl;

import com.pure.service.service.OpenIdService;
import com.pure.service.service.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenIdServiceImpl implements OpenIdService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getTencentOpenId(String code) {

        //TODO move configuration to yml
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx7b87aa9a5ab66659&secret=86bba1f2cf1c7422b63fb843ca595903&grant_type=authorization_code&js_code=" + code;

        ResponseEntity<UserInfo> userInfoResponse = restTemplate.getForEntity(url, UserInfo.class);

        return userInfoResponse.getBody().getOpenid();
    }
}
