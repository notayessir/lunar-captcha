package com.notayessir.bean.bo.captcha;

import com.notayessir.bean.Coordinate;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * meta info of click captcha
 */
public class ClickCaptchaBO implements Serializable {


    private static final long serialVersionUID = -4750377247420420664L;

    /**
     * correct coordinate (include click pattern and click letter)
     */
    private ArrayList<Coordinate> clickCoordinates;

    /**
     * width of pattern, for calculate deviation
     */
    private Integer patternWidth;

    /**
     * height of pattern, for calculate deviation
     */
    private Integer patternHeight;


    public ClickCaptchaBO(ArrayList<Coordinate> clickCoordinates, Integer patternWidth, Integer patternHeight) {
        this.clickCoordinates = clickCoordinates;
        this.patternWidth = patternWidth;
        this.patternHeight = patternHeight;
    }

    public Integer getPatternWidth() {
        return patternWidth;
    }

    public void setPatternWidth(Integer patternWidth) {
        this.patternWidth = patternWidth;
    }

    public Integer getPatternHeight() {
        return patternHeight;
    }

    public void setPatternHeight(Integer patternHeight) {
        this.patternHeight = patternHeight;
    }

    public ArrayList<Coordinate> getClickCoordinates() {
        return clickCoordinates;
    }

    public void setClickCoordinates(ArrayList<Coordinate> clickCoordinates) {
        this.clickCoordinates = clickCoordinates;
    }

}
