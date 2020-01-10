package com.pure.service.service;

import com.pure.service.service.dto.UserInfo;
import com.pure.service.service.dto.dto.DecodedPhoneNumberRequest;

public interface OpenIdService {

    UserInfo getTencentOpenId(String code);

    String getWechatDecodedPhoneNumber(DecodedPhoneNumberRequest request);
}
