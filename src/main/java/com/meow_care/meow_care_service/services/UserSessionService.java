package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.entities.UserSession;

public interface UserSessionService {

    UserSession createSession(User user, String deviceId, String deviceName);

    //verify the token and return new token
    UserSession verifyAndRefreshToken(String accessToken, String refreshToken, String deviceId);

    //delete by device id
    void deleteByDeviceId(String deviceId);
}
