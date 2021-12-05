package com.notayessir.bean.vo;

import com.notayessir.bean.CaptchaConst;

/**
 * view obj of slide captcha
 */
public class SlideCaptchaVO extends BaseCaptchaVO {

    /**
     * slide block
     */
    private String slideImage;

    /**
     * width of slide block
     */
    private int slideBlockWidth;

    /**
     * height of slide block
     */
    private int slideBlockHeight;



    public SlideCaptchaVO(String mainImage, String slideImage, int width, int height, String cliToken) {
        this.slideImage = slideImage;
        this.mainImage = mainImage;
        this.slideBlockHeight = height;
        this.slideBlockWidth = width;
        this.captchaType = CaptchaConst.CaptchaType.ROTATE_PICTURE;
        this.cliToken = cliToken;
    }

    public String getSlideImage() {
        return slideImage;
    }

    public void setSlideImage(String slideImage) {
        this.slideImage = slideImage;
    }

    public int getSlideBlockWidth() {
        return slideBlockWidth;
    }

    public void setSlideBlockWidth(int slideBlockWidth) {
        this.slideBlockWidth = slideBlockWidth;
    }

    public int getSlideBlockHeight() {
        return slideBlockHeight;
    }

    public void setSlideBlockHeight(int slideBlockHeight) {
        this.slideBlockHeight = slideBlockHeight;
    }

}
