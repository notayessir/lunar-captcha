package com.notayessir.bean.vo;

import com.notayessir.bean.CaptchaConst;

/**
 * view obj of click captcha
 */
public class ClickCaptchaVO extends BaseCaptchaVO{

    /**
     * pattern image after base64
     */
    private String patternImage;


    public ClickCaptchaVO(String mainImage, String patternImage, String cliToken) {
        this.mainImage = mainImage;
        this.patternImage = patternImage;
        this.captchaType = CaptchaConst.CaptchaType.CLICK_PATTERN;
        this.cliToken = cliToken;
    }

    public String getPatternImage() {
        return patternImage;
    }

    public void setPatternImage(String patternImage) {
        this.patternImage = patternImage;
    }


}
