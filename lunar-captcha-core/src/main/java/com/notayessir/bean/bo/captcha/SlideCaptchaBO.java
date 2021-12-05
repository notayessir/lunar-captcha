package com.notayessir.bean.bo.captcha;

import com.notayessir.bean.Coordinate;

import java.io.Serializable;


/**
 * meta info of slide captcha
 */
public class SlideCaptchaBO implements Serializable {


    private static final long serialVersionUID = -2927142704001522811L;

    /**
     * correct slide coordinate
     */
    private Coordinate coordinate;

    /**
     * x axis deviation
     */
    private int deviation;

    public SlideCaptchaBO(Coordinate coordinate, int deviation) {
        this.coordinate = coordinate;
        this.deviation = deviation;
    }


    public int getDeviation() {
        return deviation;
    }

    public void setDeviation(int deviation) {
        this.deviation = deviation;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

}
