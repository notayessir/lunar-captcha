package com.notayessir.bean;

import java.io.Serializable;

/**
 * common coordinate
 */
public class Coordinate implements Serializable {

    private static final long serialVersionUID = 1069716363931218786L;

    /**
     * X axis
     */
    private int x;

    /**
     * Y axis
     */
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate() {
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
