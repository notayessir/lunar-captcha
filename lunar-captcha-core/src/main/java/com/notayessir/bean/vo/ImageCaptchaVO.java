package com.notayessir.bean.vo;

import com.notayessir.bean.CaptchaConst;


/**
 * view obj of image captcha
 */
public class ImageCaptchaVO extends BaseCaptchaVO{


    public ImageCaptchaVO(String mainImage, String cliToken) {
        this.mainImage = mainImage;
        this.captchaType = CaptchaConst.CaptchaType.LETTER_IMAGE;
        this.cliToken = cliToken;
    }

}
