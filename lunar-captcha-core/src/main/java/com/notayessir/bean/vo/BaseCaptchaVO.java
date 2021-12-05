package com.notayessir.bean.vo;


import com.notayessir.bean.CaptchaConst;

/**
 * base view obj
 */
public class BaseCaptchaVO {

    /**
     * background image after base64
     */
    protected String mainImage;

    /**
     * uuid
     */
    protected String cliToken;

    protected CaptchaConst.CaptchaType captchaType;


    public String getCliToken() {
        return cliToken;
    }

    public void setCliToken(String cliToken) {
        this.cliToken = cliToken;
    }


    public CaptchaConst.CaptchaType getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(CaptchaConst.CaptchaType captchaType) {
        this.captchaType = captchaType;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

}
