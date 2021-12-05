package com.notayessir.service.captcha.impl;


import com.notayessir.bean.CaptchaConst;
import com.notayessir.bean.CaptchaData;
import com.notayessir.bean.Coordinate;
import com.notayessir.bean.bo.captcha.ClickCaptchaBO;
import com.notayessir.bean.bo.resource.ClickResourceBO;
import com.notayessir.bean.config.ClickCaptchaConfig;
import com.notayessir.bean.vo.ClickCaptchaVO;
import com.notayessir.service.captcha.Captcha;
import com.notayessir.service.loader.RandomResource;
import com.notayessir.util.ImageUtil;
import com.notayessir.util.RandomUtil;
import com.notayessir.util.UUIDUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

/**
 * implement of click captcha
 */
public class ClickCaptcha extends Captcha<ClickCaptchaConfig, ClickCaptchaVO, ClickCaptchaBO> {

    /**
     * resources of click captcha
     */
    private final RandomResource<ClickResourceBO> randomResource;

    public ClickCaptcha(RandomResource<ClickResourceBO> randomResource) {
        this.randomResource = randomResource;
    }

    @Override
    public CaptchaData<ClickCaptchaVO, ClickCaptchaBO> create(ClickCaptchaConfig captchaConfig) {
        ClickResourceBO clickResourceBO = randomResource.random();
        BufferedImage background = copyImage(clickResourceBO.getBackground());
        List<BufferedImage> patterns = clickResourceBO.getPatternTemplate();
        int[] indexArr = RandomUtil.genRandomIndex(patterns.size(), captchaConfig.getPatternNum());
        BufferedImage demoPattern = patterns.get(0);
        Coordinate[] coordinates = genCoordinates(CaptchaConst.CLICK_START_MARGIN, background.getWidth() - demoPattern.getWidth(),
                CaptchaConst.CLICK_START_MARGIN, background.getHeight() - demoPattern.getHeight(), captchaConfig.getPatternNum());

        drawPatternOnBackground(background, patterns, indexArr, coordinates);

        LinkedHashMap<Integer, Coordinate> selectedCoordinates = selectCoordinates(indexArr, coordinates, captchaConfig.getPatternSelectedNum());
        BufferedImage patternBlock = mergePatternImage(patterns, selectedCoordinates.keySet());
        String mainImage = ImageUtil.imageToBase64(CaptchaConst.ImageFormat.JPG, background);
        String patternImage = ImageUtil.imageToBase64(CaptchaConst.ImageFormat.PNG, patternBlock);
        ArrayList<Coordinate> bCoordinates = new ArrayList<>(selectedCoordinates.values());
        String cliToken = UUIDUtil.UUID();
        ClickCaptchaVO clickCaptchaVO = new ClickCaptchaVO(mainImage, patternImage, cliToken);
        ClickCaptchaBO clickCaptchaBO = new ClickCaptchaBO(bCoordinates, demoPattern.getWidth(), demoPattern.getHeight());
        return new CaptchaData<>(clickCaptchaVO, clickCaptchaBO);
    }

    @Override
    public boolean check(ClickCaptchaBO backend, ClickCaptchaBO client) {
        ArrayList<Coordinate> clientClickCoordinates = client.getClickCoordinates();
        ArrayList<Coordinate> backendClickCoordinates = backend.getClickCoordinates();
        if (clientClickCoordinates.size() != backendClickCoordinates.size()){
            return false;
        }
        for (int i = 0; i<clientClickCoordinates.size(); i++){
            Coordinate cliCoordinate = clientClickCoordinates.get(0);
            Coordinate backendCoordinate = backendClickCoordinates.get(0);
            int areaX = backendCoordinate.getX() + backend.getPatternWidth();
            int areaY = backendCoordinate.getY() + backend.getPatternHeight();
            if (cliCoordinate.getX() >= backendCoordinate.getX() && cliCoordinate.getX() <= areaX){
                if (cliCoordinate.getY() >= backendCoordinate.getY() && cliCoordinate.getY() <= areaY){
                    continue;
                }
                return false;
            }
            return false;
        }
        return true;
    }


    /**
     * randomly generate N coordinates in [x,y] area
     *
     * @param startX X start axis
     * @param endX   X end axis
     * @param startY Y start axis
     * @param endY   Y end axis
     * @param num    N coordinates
     * @return       N coordinates in [x,y] area
     */
    protected Coordinate[] genCoordinates(int startX, int endX, int startY, int endY, int num) {
        Coordinate[] coordinates = new Coordinate[num];
        for (int i = 0; i < num; i++) {
            coordinates[i] = genCoordinate(startX, endX, startY, endY);
        }
        return coordinates;
    }


    /**
     * composite several patterns to one picture by index
     *
     * @param patterns   patterns set
     * @param keySet     index
     * @return           composited pattern
     */
    private BufferedImage mergePatternImage(List<BufferedImage> patterns, Set<Integer> keySet) {
        BufferedImage demoPattern = patterns.get(0);
        BufferedImage patternImage = new BufferedImage(demoPattern.getWidth() * keySet.size(), demoPattern.getHeight(), demoPattern.getType());
        Graphics2D graphics = patternImage.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.dispose();
        int i = 0;
        for (int index : keySet) {
            BufferedImage pattern = patterns.get(index);
            Coordinate coordinate = new Coordinate(i * pattern.getWidth(), 0);
            cloneRGB(patternImage, pattern, coordinate);
            i++;
        }
        return patternImage;
    }


    /**
     * copy specified patterns to background by coordinates
     *
     * @param background        background picture
     * @param patterns          patterns
     * @param indexArr          index in patterns
     * @param coordinates       each pattern coordinate on background picture
     */
    private void drawPatternOnBackground(BufferedImage background, List<BufferedImage> patterns, int[] indexArr, Coordinate[] coordinates) {
        for (int cIndex = 0; cIndex < coordinates.length; cIndex++) {
            Coordinate coordinate = coordinates[cIndex];
            int pIndex = indexArr[cIndex];
            BufferedImage pattern = patterns.get(pIndex);
            cloneRGB(background, pattern, coordinate);
        }
    }


    /**
     * clone pattern RGB to background with specified coordinate
     * @param background    background picture
     * @param pattern       pattern
     * @param coordinate    pattern coordinate on background picture
     */
    private void cloneRGB(BufferedImage background, BufferedImage pattern, Coordinate coordinate){
        int height = pattern.getHeight();
        int width = pattern.getWidth();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = pattern.getRGB(i, j);
                if (rgb < 0) {
                    background.setRGB(coordinate.getX() + i , coordinate.getY() + j, rgb);
                }
            }
        }
    }

    /**
     * randomly select N coordinates in coordinates by shuffled indexArr
     * @param indexArr          indexArr
     * @param coordinates       coordinates
     * @param patternSelectNum  number of coordinate
     * @return                  N coordinates
     */
    private LinkedHashMap<Integer, Coordinate> selectCoordinates(int[] indexArr, Coordinate[] coordinates, int patternSelectNum) {
        ArrayList<Integer> arr = new ArrayList<>(indexArr.length);
        for (int i = 0; i < indexArr.length; i++) {
            arr.add(i);
        }
        Collections.shuffle(arr);
        LinkedHashMap<Integer, Coordinate> coordinateMap = new LinkedHashMap<>(patternSelectNum);
        for (int i = 0; i < patternSelectNum; i++) {
            int index = arr.get(i);
            coordinateMap.put(indexArr[index], coordinates[index]);
        }
        return coordinateMap;
    }



}
