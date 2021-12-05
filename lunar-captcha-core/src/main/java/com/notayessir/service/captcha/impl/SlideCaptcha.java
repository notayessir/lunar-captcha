package com.notayessir.service.captcha.impl;

import com.notayessir.bean.CaptchaConst;
import com.notayessir.bean.CaptchaData;
import com.notayessir.bean.Coordinate;
import com.notayessir.bean.bo.captcha.SlideCaptchaBO;
import com.notayessir.bean.bo.resource.SlideResourceBO;
import com.notayessir.bean.config.SlideCaptchaConfig;
import com.notayessir.bean.vo.SlideCaptchaVO;
import com.notayessir.service.captcha.Captcha;
import com.notayessir.service.loader.RandomResource;
import com.notayessir.util.ImageUtil;
import com.notayessir.util.UUIDUtil;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * implement of slide captcha
 */
public class SlideCaptcha extends Captcha<SlideCaptchaConfig, SlideCaptchaVO, SlideCaptchaBO> {

    /**
     * slide resources
     */
    private final RandomResource<SlideResourceBO> randomResource;


    public SlideCaptcha(RandomResource<SlideResourceBO> randomResource) {
        this.randomResource = randomResource;
    }

    @Override
    public CaptchaData<SlideCaptchaVO, SlideCaptchaBO> create(SlideCaptchaConfig captchaConfig) {
        SlideResourceBO slideResourceBO = randomResource.random();
        BufferedImage background = copyImage(slideResourceBO.getBackground());
        BufferedImage slideBlock = copyImage(slideResourceBO.getSlideBlock());

        int backgroundWidth = background.getWidth();
        int backgroundHeight = background.getHeight();
        int blockWidth = slideBlock.getWidth();
        int blockHeight = slideBlock.getHeight();

        Coordinate coordinate = genCoordinate(blockWidth, backgroundWidth - blockWidth, 0, backgroundHeight - blockHeight);
        exchangeRGBForEach(background, slideBlock, coordinate);
        BufferedImage slideBlockWithMargin = createSlideBlockWithMargin(blockWidth, backgroundHeight, slideBlock, coordinate);
        drawEdges(background, slideBlockWithMargin, coordinate);

        String mainImage = ImageUtil.imageToBase64(CaptchaConst.ImageFormat.JPG, background);
        String slideImage = ImageUtil.imageToBase64(CaptchaConst.ImageFormat.PNG, slideBlockWithMargin);
        String cliToken = UUIDUtil.UUID();
        SlideCaptchaBO slideCaptchaBO = new SlideCaptchaBO(coordinate, captchaConfig.getDeviation());
        SlideCaptchaVO slideCaptchaVO = new SlideCaptchaVO(mainImage, slideImage, slideBlockWithMargin.getWidth(), slideBlockWithMargin.getHeight(), cliToken);
        return new CaptchaData<>(slideCaptchaVO, slideCaptchaBO);
    }

    @Override
    public boolean check(SlideCaptchaBO backend, SlideCaptchaBO client) {
        int leftDeviation = backend.getCoordinate().getX() - backend.getDeviation();
        int rightDeviation = backend.getCoordinate().getX() + backend.getDeviation();
        return client.getCoordinate().getX() > leftDeviation && client.getCoordinate().getX() < rightDeviation;
    }

    /**
     * using white to draw the edge on background and slideBlock
     * @param background            background picture
     * @param slideBlockWithMargin  slideBlock
     * @param coordinate            slideBlock's coordinate on background
     */
    private void drawEdges(BufferedImage background, BufferedImage slideBlockWithMargin, Coordinate coordinate) {
        for (int i = 0; i < slideBlockWithMargin.getWidth(); i++) {
            for (int j = 0; j < slideBlockWithMargin.getHeight(); j++) {
                if (i == (slideBlockWithMargin.getWidth() - 1) || j == (slideBlockWithMargin.getHeight() - 1)) {
                    continue;
                }
                int rgb = slideBlockWithMargin.getRGB(i, j);
                int rightRgb = slideBlockWithMargin.getRGB(i + 1, j);
                int downRgb = slideBlockWithMargin.getRGB(i, j + 1);
                if ((rgb >= 0 && rightRgb < 0) || (rgb < 0 && rightRgb >= 0) || (rgb >= 0 && downRgb < 0) || (rgb < 0 && downRgb >= 0)) {
                    slideBlockWithMargin.setRGB(i, j, Color.WHITE.getRGB());
                    background.setRGB(coordinate.getX() + i, j, Color.WHITE.getRGB());
                }
            }
        }
    }

    /**
     * filling the margin on slideBlock picture in order to make slideBlock and background have same height
     * @param imageWidth    origin width
     * @param imageHeight   origin height
     * @param slideBlock    slideBlock
     * @param coordinate    slideBlock's coordinate on background picture
     * @return              slideBlock picture filled margin
     */
    private BufferedImage createSlideBlockWithMargin(int imageWidth, int imageHeight, BufferedImage slideBlock, Coordinate coordinate) {
        BufferedImage entireSlideBlock = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = entireSlideBlock.createGraphics();
        entireSlideBlock = graphics.getDeviceConfiguration().createCompatibleImage(imageWidth, imageHeight, Transparency.TRANSLUCENT);
        graphics.dispose();
        graphics = entireSlideBlock.createGraphics();
        graphics.drawImage(slideBlock, 0, coordinate.getY(), null);
        graphics.dispose();
        return entireSlideBlock;
    }

    /**
     * exchange two picture rgb value
     * @param background    background
     * @param slideBlock    slideBlock
     * @param coordinate    coordinate of background where start to exhcnage
     */
    private void exchangeRGBForEach(BufferedImage background, BufferedImage slideBlock, Coordinate coordinate) {
        for (int i = 0; i < slideBlock.getWidth(); i++) {
            for (int j = 0; j < slideBlock.getHeight(); j++) {
                int rgb = slideBlock.getRGB(i, j);
                if (rgb < 0) {
                    // 生成拼图
                    slideBlock.setRGB(i, j, background.getRGB(coordinate.getX() + i, coordinate.getY() + j));
                    // 用高斯模糊算法模糊原图中被抠图区域
                    int[][] coordinates = obtainCoordinateMatrix(background, coordinate.getX() + i, coordinate.getY() + j);
                    background.setRGB(coordinate.getX() + i, coordinate.getY() + j, avgMatrix(coordinates));
                }
            }
        }
    }


    /**
     * acquire the matrix of [x,y]
     * @param background            background image
     * @param centerX               x axis
     * @param centerY               y axis
     * @return                      matrix of [x,y]
     */
    private static int[][] obtainCoordinateMatrix(BufferedImage background, int centerX, int centerY) {
        int[][] matrix = new int[3][3];
        int startX = centerX - 1;
        int startY = centerY - 1;
        for (int cursorX = startX; cursorX < startX + 3; cursorX++) {
            for (int cursorY = startY; cursorY < startY + 3; cursorY++) {
                int tempX = cursorX;
                if (tempX < 0) {
                    tempX = -tempX;
                } else if (tempX >= background.getWidth()) {
                    tempX = centerX;
                }

                int tempY = cursorY;
                if (tempY < 0) {
                    tempY = -tempY;
                } else if (tempY >= background.getHeight()) {
                    tempY = centerY;
                }

                int rgb = background.getRGB(tempX, tempY);
                matrix[cursorX - startX][cursorY - startY] = rgb;
            }
        }
        return matrix;
    }

    /**
     * Gaussian Blur
     * @param rgbMatrix matrix
     * @return          value after calculate the matrix
     */
    private static int avgMatrix(int[][] rgbMatrix) {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int[] x : rgbMatrix) {
            for (int j = 0; j < x.length; j++) {
                if (j == 1) {
                    continue;
                }
                Color c = new Color(x[j]);
                r += c.getRed();
                g += c.getGreen();
                b += c.getBlue();
            }
        }
        int shadowDepth = 10;
        return new Color(r / shadowDepth, g / shadowDepth, b / shadowDepth).getRGB();
    }

}
