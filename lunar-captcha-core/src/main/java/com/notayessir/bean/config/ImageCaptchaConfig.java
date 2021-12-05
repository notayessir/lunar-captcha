package com.notayessir.bean.config;

import com.notayessir.bean.CaptchaConst;

/**
 * config of image captcha
 */
public class ImageCaptchaConfig {

    private CaptchaConst.ImageFormat imageFormat;

    private CaptchaConst.StringType stringType;


    /**
     * color of letter
     */
    private String codeColor;

    /**
     * number of letter
     */
    private Integer codeCount;

    /**
     * width of image
     */
    private Integer imageWidth;

    /**
     * height of image
     */
    private Integer imageHeight;


    /**
     * font size of letter
     */
    private Integer fontSize;

    /**
     * adhesion of letter
     */
    private Integer adhesion;

    /**
     * angle of left rotation
     */
    private Integer leftRotation;

    /**
     * angle of right rotation
     */
    private Integer rightRotation;


    public CaptchaConst.ImageFormat getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(CaptchaConst.ImageFormat imageFormat) {
        this.imageFormat = imageFormat;
    }


    public CaptchaConst.StringType getStringType() {
        return stringType;
    }

    public void setStringType(CaptchaConst.StringType stringType) {
        this.stringType = stringType;
    }

    public String getCodeColor() {
        return codeColor;
    }

    public void setCodeColor(String codeColor) {
        this.codeColor = codeColor;
    }

    public Integer getCodeCount() {
        return codeCount;
    }

    public void setCodeCount(Integer codeCount) {
        this.codeCount = codeCount;
    }


    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Integer getAdhesion() {
        return adhesion;
    }

    public void setAdhesion(Integer adhesion) {
        this.adhesion = adhesion;
    }

    public Integer getLeftRotation() {
        return leftRotation;
    }

    public void setLeftRotation(Integer leftRotation) {
        this.leftRotation = leftRotation;
    }

    public Integer getRightRotation() {
        return rightRotation;
    }

    public void setRightRotation(Integer rightRotation) {
        this.rightRotation = rightRotation;
    }
}
