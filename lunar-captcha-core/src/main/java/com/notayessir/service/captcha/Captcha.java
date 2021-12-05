package com.notayessir.service.captcha;


import com.notayessir.bean.CaptchaData;
import com.notayessir.bean.Coordinate;
import com.notayessir.util.RandomUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * abstract captcha service, include some common method
 */
public abstract class Captcha<T, V, B> {


    /**
     * create captcha resources
     * @param captchaConfig config of captcha
     * @return              captcha resources
     */
    public abstract CaptchaData<V, B> create(T captchaConfig);

    /**
     * check frontend captcha param
     * @param backend   backend params
     * @param client    frontend params
     * @return          is correct
     */
    public abstract boolean check(B backend, B client);


    /**
     * randomly generate a coordinate in [x,y] area
     *
     * @param startX X start axis
     * @param endX   X end axis
     * @param startY Y start axis
     * @param endY   Y end axis
     * @return       a coordinate in [x,y] area
     */
    protected Coordinate genCoordinate(int startX, int endX, int startY, int endY) {
        int x = RandomUtil.genNum(startX, endX);
        int y = RandomUtil.genNum(startY, endY);
        return new Coordinate(x, y);
    }



    /**
     * composite two pictures vertical
     *
     * @param background picture 1
     * @param fragment   picture 2
     * @return           composited picture
     */
    protected BufferedImage mergeImagesVertical(BufferedImage background, BufferedImage fragment) {
        BufferedImage merge = new BufferedImage(background.getWidth(), background.getHeight() + fragment.getHeight(), background.getType());
        Graphics2D graphics = merge.createGraphics();
        graphics.drawImage(background, 0, 0, null);
        graphics.drawImage(fragment, 0, background.getHeight(), null);
        graphics.dispose();
        return merge;
    }

    /**
     * composite two pictures horizontal
     *
     * @param background picture 1
     * @param fragment   picture 2
     * @return           composited picture
     */
    protected BufferedImage mergeImagesHorizontal(BufferedImage background, BufferedImage fragment) {
        BufferedImage merge = new BufferedImage(background.getWidth() + fragment.getWidth(), background.getHeight(), background.getType());
        Graphics2D graphics = merge.createGraphics();
        graphics.drawImage(background, 0, 0, null);
        graphics.drawImage(fragment, background.getWidth(), 0, null);
        graphics.dispose();
        return merge;
    }

    /**
     * deep copy picture
     *
     * @param source    origin picture
     * @return          copied picture
     */
    protected BufferedImage copyImage(BufferedImage source){
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }






}
