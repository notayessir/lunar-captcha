package com.notayessir.manager;


import com.notayessir.bean.CaptchaConst;
import com.notayessir.bean.Session;
import com.notayessir.util.UUIDUtil;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CaptchaManager {

    private final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    private final Map<String, Integer> accessTokenMap = new ConcurrentHashMap<>();

    public void createCaptchaSession(String cliToken, CaptchaConst.CaptchaType captchaType, Object bo) {
        sessionMap.put(cliToken, new Session(bo, captchaType));
    }

    public void deleteCaptchaSession(String cliToken) {
        sessionMap.remove(cliToken);
    }

    public Session getCaptchaSession(String cliToken){
        try {
            return sessionMap.get(cliToken);
        }catch (NullPointerException npe){
            return null;
        }
    }



    public String createAccessToken(){
        String accessToken = UUIDUtil.UUID();
        accessTokenMap.put(accessToken, 1);
        return accessToken;
    }

    public boolean checkAndDelAccessTokenWhenSuccess(String accessToken){
        if (accessTokenMap.containsKey(accessToken)){
            accessTokenMap.remove(accessToken);
            return true;
        }
        return false;
    }

}
