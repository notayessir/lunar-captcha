package com.notayessir.bean.bo.resource;

import java.awt.image.BufferedImage;

/**
 * rotate captcha resources
 */
public class RotateResourceBO {

    /**
     * rotated picture
     */
    private BufferedImage bufferedImage;


    public RotateResourceBO(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
