package com.notayessir.bean.bo.captcha;

import com.notayessir.bean.Coordinate;

import java.io.Serializable;

/**
 * meta info of rotate captcha
 */
public class RotateCaptchaBO implements Serializable {


    private static final long serialVersionUID = 7659371278127361074L;

    /**
     * correct rotate/slide coordinate
     */
    private Coordinate slideCoordinate;


    /**
     *  allow deviation
     */
    private int rotateDeviation;


    public RotateCaptchaBO(Coordinate coordinate, int rotateDeviation) {
        this.slideCoordinate = coordinate;
        this.rotateDeviation = rotateDeviation;
    }


    public int getRotateDeviation() {
        return rotateDeviation;
    }

    public void setRotateDeviation(int rotateDeviation) {
        this.rotateDeviation = rotateDeviation;
    }

    public Coordinate getSlideCoordinate() {
        return slideCoordinate;
    }

    public void setSlideCoordinate(Coordinate slideCoordinate) {
        this.slideCoordinate = slideCoordinate;
    }
}
