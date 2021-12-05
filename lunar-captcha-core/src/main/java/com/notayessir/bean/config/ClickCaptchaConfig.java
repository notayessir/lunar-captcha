package com.notayessir.bean.config;

/**
 * config of click captcha
 */
public class ClickCaptchaConfig {

    /**
     * how many patterns are show in the background
     */
    private int patternNum;

    /**
     * how many patterns show be selected
     */
    private Integer patternSelectedNum;


    public int getPatternNum() {
        return patternNum;
    }

    public void setPatternNum(int patternNum) {
        this.patternNum = patternNum;
    }

    public Integer getPatternSelectedNum() {
        return patternSelectedNum;
    }

    public void setPatternSelectedNum(Integer patternSelectedNum) {
        this.patternSelectedNum = patternSelectedNum;
    }
}
