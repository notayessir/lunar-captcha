package com.notayessir.service.gen.impl;


import com.notayessir.bean.config.ImageCaptchaConfig;
import com.notayessir.service.gen.ImageCaptchaGenerator;
import com.notayessir.util.ImageUtil;
import com.notayessir.util.RandomUtil;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * draw jpg captcha
 */
public class JPGGenerator extends ImageCaptchaGenerator {


    public JPGGenerator(ImageCaptchaConfig captchaConfig, String letter) {
        super(captchaConfig, letter);
    }

    @Override
    protected void drawLetter(Graphics2D graphics, String captcha) {
        Font font = new Font("Arial", Font.BOLD, this.captchaConfig.getFontSize());
        graphics.setFont(font);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        Rectangle2D captchaBounds = font.getStringBounds(captcha, fontRenderContext);
        double x = (this.captchaConfig.getImageWidth() - captchaBounds.getWidth()) / 2;
        double y = (this.captchaConfig.getImageHeight() - captchaBounds.getHeight()) / 2 - captchaBounds.getY();

        // check if is need rotate letter
        int leftAngle = captchaConfig.getLeftRotation();
        int rightAngle = captchaConfig.getRightRotation();
        if (leftAngle == 0 && rightAngle == 0){
            for (int i = 0; i < captcha.length(); i++) {
                graphics.setPaint(RandomUtil.genColor());
                Rectangle2D rectangle2D = font.getStringBounds(captcha.substring(i, i + 1), fontRenderContext);
                graphics.drawString(captcha.substring(i, i + 1), (int) x, (int) (y));
                x += rectangle2D.getWidth() - this.captchaConfig.getAdhesion();
            }
        }else {
            AffineTransform affineTransform = new AffineTransform();
            for (int i = 0; i < captcha.length(); i++) {

                Rectangle2D rectangle2D = font.getStringBounds(captcha.substring(i, i + 1), fontRenderContext);
                // rotate a random angle
                int randomAngle = RandomUtil.genNum(leftAngle, rightAngle);
                affineTransform.rotate(Math.toRadians(randomAngle), 0,0);
                Font rotatedFont = font.deriveFont(affineTransform);
                // draw letter
                graphics.setPaint(RandomUtil.genColor());
                graphics.setFont(rotatedFont);
                graphics.drawString(captcha.substring(i, i + 1), (int) x, (int) (y));

                x += rectangle2D.getWidth() - this.captchaConfig.getAdhesion();

                // resume angle
                affineTransform.rotate(Math.toRadians(-randomAngle), 0,0);
            }
        }
    }


    @Override
    public String gen() {
        BufferedImage bufferedImage = new BufferedImage(this.captchaConfig.getImageWidth(), this.captchaConfig.getImageHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        fillBackGround(graphics);
        drawLetter(graphics, this.letter);
        interfereLetter(graphics);
        String base64Str = ImageUtil.imageToBase64(captchaConfig.getImageFormat(), bufferedImage);
        graphics.dispose();
        return base64Str;
    }





}
