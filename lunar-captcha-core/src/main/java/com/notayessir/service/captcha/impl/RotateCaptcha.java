package com.notayessir.service.captcha.impl;


import com.notayessir.bean.CaptchaConst;
import com.notayessir.bean.CaptchaData;
import com.notayessir.bean.Coordinate;
import com.notayessir.bean.bo.captcha.RotateCaptchaBO;
import com.notayessir.bean.bo.resource.RotateResourceBO;
import com.notayessir.bean.config.RotateCaptchaConfig;
import com.notayessir.bean.vo.RotateCaptchaVO;
import com.notayessir.service.captcha.Captcha;
import com.notayessir.service.loader.RandomResource;
import com.notayessir.util.ImageUtil;
import com.notayessir.util.RandomUtil;
import com.notayessir.util.UUIDUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


/**
 * implement of rotate captcha
 */
public class RotateCaptcha extends Captcha<RotateCaptchaConfig, RotateCaptchaVO, RotateCaptchaBO> {

    /**
     * rotate captcha resources
     */
    private final RandomResource<RotateResourceBO> randomResource;

    public RotateCaptcha(RandomResource<RotateResourceBO> randomResource) {
        this.randomResource = randomResource;
    }

    @Override
    public CaptchaData<RotateCaptchaVO, RotateCaptchaBO> create(RotateCaptchaConfig captchaConfig) {
        BufferedImage originImage = copyImage(randomResource.random().getBufferedImage());
        int startAngle = CaptchaConst.PICTURE_ROTATE_GAP_ANGLE;
        int endAngle = 360 - startAngle;
        int rotateAngle = RandomUtil.genNum(startAngle,endAngle);
        BufferedImage rotatedImage = rotatePicture(originImage, rotateAngle);
        String mainImage = ImageUtil.imageToBase64(CaptchaConst.ImageFormat.JPG, rotatedImage);
        String cliToken = UUIDUtil.UUID();
        RotateCaptchaBO rotateCaptchaBO = new RotateCaptchaBO(new Coordinate(rotateAngle, 0), captchaConfig.getRotateDeviation());
        RotateCaptchaVO rotateCaptchaVO = new RotateCaptchaVO(mainImage, cliToken);
        return new CaptchaData<>(rotateCaptchaVO, rotateCaptchaBO);
    }

    @Override
    public boolean check(RotateCaptchaBO backend, RotateCaptchaBO client) {
        int leftDeviation = backend.getSlideCoordinate().getX() - backend.getRotateDeviation();
        int rightDeviation = backend.getSlideCoordinate().getX() + backend.getRotateDeviation();
        return client.getSlideCoordinate().getX() > leftDeviation && client.getSlideCoordinate().getX() < rightDeviation;
    }


    /**
     * rotate a picture without lose quality
     * @param background    origin picture
     * @param radians       rotate angle
     * @return              rotated picture
     */
    private BufferedImage rotatePicture(BufferedImage background, int radians) {
        double rads = Math.toRadians(radians);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = background.getWidth();
        int h = background.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);
        BufferedImage rotated = new BufferedImage(newWidth, newHeight, background.getType());
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);
        int x = w / 2;
        int y = h / 2;
        at.rotate(rads, x, y);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setPaint(Color.WHITE);
        g2d.fill(new Rectangle(0, 0, newWidth, newHeight));
        g2d.setTransform(at);
        g2d.drawImage(background, 0, 0, null);
        g2d.dispose();
        return rotated;
    }


}
