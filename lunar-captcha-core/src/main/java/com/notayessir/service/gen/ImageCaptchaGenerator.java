package com.notayessir.service.gen;


import com.notayessir.bean.config.ImageCaptchaConfig;
import com.notayessir.util.RandomUtil;
import com.notayessir.util.ShapeUtil;

import java.awt.*;

/**
 * image captcha abstract generator
 */
public abstract class ImageCaptchaGenerator {

    /**
     * image captcha config
     */
    protected ImageCaptchaConfig captchaConfig;

    /**
     * image captcha content
     */
    protected String letter;


    protected ImageCaptchaGenerator(ImageCaptchaConfig captchaConfig, String letter) {
        this.captchaConfig = captchaConfig;
        this.letter = letter;
    }


    /**
     * generate picture in base64
     * @return  picture in base64
     */
    public abstract String gen();



    /**
     * draw background
     *
     * @param graphics graphics obj
     */
    protected void fillBackGround(Graphics2D graphics) {
        String[] rgba = captchaConfig.getCodeColor().split("[,;]");
        graphics.setPaint(new Color(Integer.parseInt(rgba[0]), Integer.parseInt(rgba[1]), Integer.parseInt(rgba[2]), Integer.parseInt(rgba[3])));
        graphics.fill(new Rectangle(0, 0, captchaConfig.getImageWidth(), captchaConfig.getImageHeight()));
    }


    /**
     * draw letter on background
     *
     * @param graphics graphics obj
     * @param captcha  letter
     */
    protected abstract void drawLetter(Graphics2D graphics, String captcha);

    /**
     * draw interference line on letter layer
     *
     * @param graphics graphics obj
     */
    protected void interfereLetter(Graphics2D graphics) {
        AlphaComposite ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1F * RandomUtil.genNum(6, 10));
        graphics.setComposite(ac3);
        BasicStroke stroke = new BasicStroke(2.5F);
        ShapeUtil.drawQuadCurve2D(graphics, captchaConfig.getImageWidth(), captchaConfig.getImageHeight(), RandomUtil.genColor(), stroke);
        ShapeUtil.drawOval(graphics, captchaConfig.getImageWidth(), captchaConfig.getImageHeight(), RandomUtil.genColor(), stroke);
    }


}
