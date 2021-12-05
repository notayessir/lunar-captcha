package com.notayessir.service.loader.impl;

import com.notayessir.bean.bo.resource.ClickResourceBO;
import com.notayessir.service.loader.RandomResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


/**
 * click resources loader
 */
public class ClickResource extends RandomResource<ClickResourceBO> {

    /**
     * internal static background resources
     */
    protected List<BufferedImage> internalBackgroundImages = new ArrayList<>();

    /**
     * internal static pattern resources
     */
    protected List<BufferedImage> internalPatternImages = new ArrayList<>();

    /**
     * external background resources
     */
    protected List<BufferedImage> externalBackgroundImages;

    /**
     * external pattern resources
     */
    protected List<BufferedImage> externalPatternImages;

    public ClickResource() {
        loadInternal();
        if (isLoadExternal()){
            loadExternal();
        }
    }

    @Override
    public void loadInternal() {
        try {
            InputStream inputStream;
            int backgroundCount = 5;
            for (int i = 1; i <= backgroundCount; i++) {
                String resPath = "/click/background/";
                String fileName = resPath + i + ".jpg";
                inputStream = this.getClass().getResourceAsStream(fileName);
                assert inputStream != null;
                BufferedImage origin = ImageIO.read(inputStream);
                internalBackgroundImages.add(origin);
                inputStream.close();
            }
            int patternCount = 11;
            for (int i = 1; i <= patternCount; i++) {
                String resPath = "/click/pattern/";
                String fileName = resPath + i + ".png";
                inputStream = this.getClass().getResourceAsStream(fileName);
                assert inputStream != null;
                BufferedImage origin = ImageIO.read(inputStream);
                internalPatternImages.add(origin);
                inputStream.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public ClickResourceBO random() {
        if (Objects.isNull(externalBackgroundImages) || externalBackgroundImages.isEmpty()){
            return new ClickResourceBO(internalBackgroundImages.get(new Random().nextInt(internalBackgroundImages.size())),
                    internalPatternImages);
        }
        return new ClickResourceBO(externalBackgroundImages.get(new Random().nextInt(externalBackgroundImages.size())),
                externalPatternImages);
    }

}
