package com.notayessir.util;


import java.awt.*;
import java.awt.geom.QuadCurve2D;

/**
 * shape util, draw some commons pattern
 */
public class ShapeUtil {




    /**
     * draw line on image
     *
     * @param graphics     graphics obj
     * @param width        image width
     * @param height       image height
     */
    public static void drawRandomLine(Graphics2D graphics, int width, int height){
        int xs = RandomUtil.genNum(width);
        int ys = RandomUtil.genNum(height);
        int xe = xs + RandomUtil.genNum(width / 8);
        int ye = ys + RandomUtil.genNum(height / 8);
        graphics.setColor(RandomUtil.genColor());
        graphics.drawLine(xs, ys, xe, ye);
    }


    /**
     * draw oval on a random pos
     *
     * @param graphics graphics obj
     * @param width    image width
     * @param height   image height
     * @param color    color of oval
     * @param stroke   stroke thickness
     */
    public static void drawOval(Graphics2D graphics, int width, int height, Color color, BasicStroke stroke) {
        graphics.setPaint(color);
        graphics.setStroke(stroke);
        int diameter = (int) (height * 0.2);
        int letterAreaHeight = height / 3;
        graphics.drawOval(RandomUtil.genNum(width), RandomUtil.genNum(letterAreaHeight), diameter, diameter);
    }


    /**
     * draw quad curve2D
     *
     * @param graphics graphics obj
     * @param width    image width
     * @param height   image height
     * @param color    color of oval
     * @param stroke   stroke thickness
     */
    public static void drawQuadCurve2D(Graphics2D graphics, int width, int height, Color color, BasicStroke stroke) {
        graphics.setPaint(color);
        graphics.setStroke(stroke);
        int letterAreaHeight = height / 3;
        QuadCurve2D curve2D = new QuadCurve2D.Double(0, RandomUtil.genNum(letterAreaHeight, letterAreaHeight * 2), RandomUtil.genNum(width), RandomUtil.genNum(height), width, RandomUtil.genNum(letterAreaHeight, letterAreaHeight * 2));
        graphics.draw(curve2D);
    }

}
