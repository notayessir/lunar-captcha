package com.notayessir.service;


import com.notayessir.bean.CaptchaConst;
import com.notayessir.bean.CaptchaData;
import com.notayessir.bean.Session;
import com.notayessir.bean.bo.captcha.ClickCaptchaBO;
import com.notayessir.bean.bo.captcha.ImageCaptchaBO;
import com.notayessir.bean.bo.captcha.RotateCaptchaBO;
import com.notayessir.bean.bo.captcha.SlideCaptchaBO;
import com.notayessir.bean.config.ClickCaptchaConfig;
import com.notayessir.bean.config.ImageCaptchaConfig;
import com.notayessir.bean.config.RotateCaptchaConfig;
import com.notayessir.bean.config.SlideCaptchaConfig;
import com.notayessir.bean.vo.*;
import com.notayessir.manager.CaptchaManager;
import com.notayessir.service.captcha.impl.ClickCaptcha;
import com.notayessir.service.captcha.impl.ImageCaptcha;
import com.notayessir.service.captcha.impl.RotateCaptcha;
import com.notayessir.service.captcha.impl.SlideCaptcha;
import com.notayessir.service.loader.impl.ClickResource;
import com.notayessir.service.loader.impl.RotateResource;
import com.notayessir.service.loader.impl.SlideResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CaptchaService {


    private final SlideCaptcha slideCaptcha;

    private final ClickCaptcha clickCaptcha;

    private final RotateCaptcha rotateCaptcha;

    private final ImageCaptcha imageCaptcha;


    @Autowired
    CaptchaManager captchaManager;


    public CaptchaService() {
        SlideResource slideResource = new SlideResource();
        slideCaptcha = new SlideCaptcha(slideResource);
        ClickResource clickResource = new ClickResource();
        clickCaptcha = new ClickCaptcha(clickResource);
        RotateResource randomResource = new RotateResource();
        rotateCaptcha = new RotateCaptcha(randomResource);
        imageCaptcha = new ImageCaptcha();
    }


    public Object createCaptcha(CaptchaConst.CaptchaType captchaType){
        switch (captchaType){
            case SLIDE_BLOCK:
                CaptchaData<SlideCaptchaVO, SlideCaptchaBO> slideCaptcha = createSlideCaptcha();
                captchaManager.createCaptchaSession(slideCaptcha.getVO().getCliToken(), slideCaptcha.getVO().getCaptchaType(), slideCaptcha.getBO());
                return slideCaptcha.getVO();
            case CLICK_PATTERN:
                CaptchaData<ClickCaptchaVO, ClickCaptchaBO> clickCaptcha = createClickCaptcha();
                captchaManager.createCaptchaSession(clickCaptcha.getVO().getCliToken(), clickCaptcha.getVO().getCaptchaType(), clickCaptcha.getBO());
                return clickCaptcha.getVO();
            case LETTER_IMAGE:
                CaptchaData<ImageCaptchaVO, ImageCaptchaBO> imageCaptcha = createImageCaptcha();
                captchaManager.createCaptchaSession(imageCaptcha.getVO().getCliToken(), imageCaptcha.getVO().getCaptchaType(), imageCaptcha.getBO());
                return imageCaptcha.getVO();
            case ROTATE_PICTURE:
                CaptchaData<RotateCaptchaVO, RotateCaptchaBO> rotateCaptcha = createRotateCaptcha();
                captchaManager.createCaptchaSession(rotateCaptcha.getVO().getCliToken(), rotateCaptcha.getVO().getCaptchaType(), rotateCaptcha.getBO());
                return rotateCaptcha.getVO();
            default:
                return null;
        }
    }

    private CaptchaData<ClickCaptchaVO, ClickCaptchaBO> createClickCaptcha(){
        ClickCaptchaConfig captchaConfig = new ClickCaptchaConfig();
        captchaConfig.setPatternNum(4);
        captchaConfig.setPatternSelectedNum(2);
        return clickCaptcha.create(captchaConfig);
    }

    private CaptchaData<RotateCaptchaVO, RotateCaptchaBO> createRotateCaptcha(){
        RotateCaptchaConfig config = new RotateCaptchaConfig();
        config.setRotateDeviation(10);
        return rotateCaptcha.create(config);
    }

    private CaptchaData<SlideCaptchaVO, SlideCaptchaBO> createSlideCaptcha(){
        SlideCaptchaConfig captchaConfig = new SlideCaptchaConfig();
        captchaConfig.setDeviation(10);
        return slideCaptcha.create(captchaConfig);
    }


    private CaptchaData<ImageCaptchaVO, ImageCaptchaBO> createImageCaptcha(){
        ImageCaptchaConfig captchaConfig = new ImageCaptchaConfig();
        captchaConfig.setAdhesion(5);
        captchaConfig.setLeftRotation(10);
        captchaConfig.setRightRotation(0);
        captchaConfig.setImageWidth(125);
        captchaConfig.setImageHeight(45);
        captchaConfig.setCodeCount(4);
        captchaConfig.setFontSize(45);
        captchaConfig.setImageFormat(CaptchaConst.ImageFormat.GIF);
        captchaConfig.setCodeColor("255,255,255,255");
        return imageCaptcha.create(captchaConfig);
    }



    public CheckResultVO checkCaptcha(String cliToken, String trackInfo){
        CheckResultVO resultVO = new CheckResultVO();
        resultVO.setSuccess(false);
        Session session = captchaManager.getCaptchaSession(cliToken);
        if (!Objects.isNull(session)){
            captchaManager.deleteCaptchaSession(cliToken);
            try {
                if (doCheck(session, trackInfo)){
                    resultVO.setSuccess(true);
                    resultVO.setAccessToken(captchaManager.createAccessToken());
                }
            } catch (Exception e) {
                return resultVO;
            }
            return resultVO;
        }
        return resultVO;
    }




    private boolean doCheck(Session session, String trackInfo) {
        switch (session.getCaptchaType()){
            case LETTER_IMAGE:
                ImageCaptchaBO imageCaptchaBO = (ImageCaptchaBO) session.getBo();
                if (imageCaptcha.check(imageCaptchaBO, new ImageCaptchaBO(trackInfo))){
                    return true;
                }
            case ROTATE_PICTURE:
            case CLICK_PATTERN:
            case SLIDE_BLOCK:
            default:
                return false;
        }
    }


}
