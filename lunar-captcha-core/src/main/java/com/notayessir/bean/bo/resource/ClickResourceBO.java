package com.notayessir.bean.bo.resource;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * click captcha resource obj
 */
public class ClickResourceBO {

    /**
     * background picture
     */
    private BufferedImage background;

    /**
     * clicked patterns
     */
    private List<BufferedImage> patterns;

    public ClickResourceBO(BufferedImage background, List<BufferedImage> patterns) {
        this.background = background;
        this.patterns = patterns;
    }

    public BufferedImage getBackground() {
        return background;
    }

    public void setBackground(BufferedImage background) {
        this.background = background;
    }

    public List<BufferedImage> getPatternTemplate() {
        return patterns;
    }

    public void setPatternTemplate(List<BufferedImage> patterns) {
        this.patterns = patterns;
    }
}
