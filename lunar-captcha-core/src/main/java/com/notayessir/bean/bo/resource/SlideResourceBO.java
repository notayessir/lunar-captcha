package com.notayessir.bean.bo.resource;

import java.awt.image.BufferedImage;


/**
 * slide captcha resources
 */
public class SlideResourceBO {

    /**
     * background picture
     */
    private BufferedImage background;

    /**
     * slide block
     */
    private BufferedImage slideBlock;


    public SlideResourceBO(BufferedImage background, BufferedImage slideBlock) {
        this.background = background;
        this.slideBlock = slideBlock;
    }


    public BufferedImage getBackground() {
        return background;
    }

    public void setBackground(BufferedImage background) {
        this.background = background;
    }

    public BufferedImage getSlideBlock() {
        return slideBlock;
    }

    public void setSlideBlock(BufferedImage slideBlock) {
        this.slideBlock = slideBlock;
    }
}
