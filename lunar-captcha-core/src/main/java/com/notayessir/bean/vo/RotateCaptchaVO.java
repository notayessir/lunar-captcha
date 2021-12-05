package com.notayessir.bean.vo;

import com.notayessir.bean.CaptchaConst;


/**
 * view obj of rotate captcha
 */
public class RotateCaptchaVO extends BaseCaptchaVO{


    public RotateCaptchaVO(String mainImage, String cliToken) {
        this.mainImage = mainImage;
        this.captchaType = CaptchaConst.CaptchaType.ROTATE_PICTURE;
        this.cliToken = cliToken;
    }

}
