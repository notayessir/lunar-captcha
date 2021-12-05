package com.notayessir.service.gen.impl;



import com.notayessir.bean.config.ImageCaptchaConfig;
import com.notayessir.service.gen.ImageCaptchaGenerator;
import com.notayessir.util.AnimatedGifEncoder;
import com.notayessir.util.ImageUtil;
import com.notayessir.util.RandomUtil;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;

/**
 * generate gif frame
 */
public class GIFGenerator extends ImageCaptchaGenerator {

    /**
     * transparency for each letter
     */
    private ArrayDeque<Float> queue;

    /**
     * color for each letter
     */
    private Color[] colors;

    /**
     * gif tool
     */
    private AnimatedGifEncoder gifEncoder;



    public GIFGenerator(ImageCaptchaConfig captchaConfig, String letter) {
        super(captchaConfig, letter);
        init();
    }


    @Override
    protected void drawLetter(Graphics2D graphics, String captcha) {
        Font font = new Font("Arial", Font.BOLD, this.captchaConfig.getFontSize());
        graphics.setFont(font);
        // 抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // calculate x,y axis
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        Rectangle2D captchaBounds = font.getStringBounds(captcha, fontRenderContext);
        double x = (this.captchaConfig.getImageWidth() - captchaBounds.getWidth()) / 2;
        double y = (this.captchaConfig.getImageHeight() - captchaBounds.getHeight()) / 2 - captchaBounds.getY();

        // check if is need rotate
        int leftAngle = captchaConfig.getLeftRotation();
        int rightAngle = captchaConfig.getRightRotation();
        if (leftAngle == 0 && rightAngle == 0){
            for (int i = 0; i < captcha.length(); i++) {
                // setting transparency
                Float alpha = queue.removeFirst();
                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                graphics.setComposite(ac);
                // setting color
                graphics.setPaint(colors[i]);
                graphics.drawString(captcha.substring(i, i + 1), (int) x, (int) (y));
                // change x-axis for next
                Rectangle2D rectangle2D = font.getStringBounds(captcha.substring(i, i + 1), fontRenderContext);
                x += rectangle2D.getWidth() - this.captchaConfig.getAdhesion();
                // change transparency for next
                queue.addLast(alpha);
            }
        } else {
            AffineTransform affineTransform = new AffineTransform();
            for (int i = 0; i < captcha.length(); i++) {
                // setting transparency
                Float alpha = queue.removeFirst();
                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                graphics.setComposite(ac);

                // rotate random angle
                int randomAngle = RandomUtil.genNum(leftAngle, rightAngle);
                affineTransform.rotate(Math.toRadians(randomAngle), 0, 0);
                Font rotatedFont = font.deriveFont(affineTransform);
                graphics.setFont(rotatedFont);

                // setting color
                graphics.setPaint(colors[i]);
                graphics.drawString(captcha.substring(i, i + 1), (int) x, (int) (y));

                // change x-axis for next
                Rectangle2D rectangle2D = font.getStringBounds(captcha.substring(i, i + 1), fontRenderContext);
                x += rectangle2D.getWidth() - this.captchaConfig.getAdhesion();

                // resume angle for next
                affineTransform.rotate(Math.toRadians(-randomAngle), 0, 0);

                // change transparency for next
                queue.addLast(alpha);
            }
        }
        // change transparency for next frame
        queue.addLast(queue.removeFirst());
    }


    /**
     *  init transparency and color array
     */
    private void init() {
        initAlpha();
        initLetterColor();
    }

    /**
     * init letter transparency
     */
    private void initAlpha() {
        queue = new ArrayDeque<Float>(letter.length());
        float step = 1F / (letter.length() - 1);
        for (int i = 0; i < letter.length(); i++) {
            queue.addFirst(step * i);
        }
    }

    /**
     * randomly generate color for each letter
     */
    private void initLetterColor() {
        colors = new Color[letter.length()];
        for (int i = 0; i < letter.length(); i++) {
            colors[i] = RandomUtil.genColor();
        }
    }

    /**
     * gif config
     *
     * @param os OutputStream
     */
    private void initAnimatedGifEncoder(OutputStream os) {
        gifEncoder = new AnimatedGifEncoder();
        gifEncoder.start(os);
        // delay 100 ms for each frame
        gifEncoder.setDelay(150);
        // quality
        gifEncoder.setQuality(180);
        // repeat
        gifEncoder.setRepeat(0);
    }



    @Override
    public String gen() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        initAnimatedGifEncoder(outputStream);
        for (int i = 0; i < letter.length(); i++) {
            BufferedImage bufferedImage = new BufferedImage(captchaConfig.getImageWidth(), captchaConfig.getImageHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = bufferedImage.createGraphics();
            drawGraphics(graphics, letter);
            gifEncoder.addFrame(bufferedImage);
            graphics.dispose();
            bufferedImage.flush();
        }
        gifEncoder.finish();
        return ImageUtil.gifToBase64(outputStream.toByteArray());
    }

    /**
     * draw gif
     *
     * @param graphics graphics obj
     * @param captcha  captcha  letter
     */
    private void drawGraphics(Graphics2D graphics, String captcha) {
        fillBackGround(graphics);
        drawLetter(graphics, captcha);
        interfereLetter(graphics);
    }


}
