package com.notayessir.service.loader.impl;

import com.notayessir.bean.bo.resource.SlideResourceBO;
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
 * slide resources loader
 */
public class SlideResource extends RandomResource<SlideResourceBO> {

    /**
     * internal static background resources
     */
    protected List<BufferedImage> internalBackgroundImages = new ArrayList<>();

    /**
     * internal static slide resource
     */
    protected BufferedImage internalSlideBlock;

    /**
     * external background resources
     */
    protected List<BufferedImage> externalBackgroundImages;

    /**
     * external slide resource
     */
    protected BufferedImage externalSlideBlock;


    public SlideResource() {
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
                String resPath = "/slide/background/";
                String fileName = resPath + i + ".jpg";
                inputStream = this.getClass().getResourceAsStream(fileName);
                assert inputStream != null;
                BufferedImage origin = ImageIO.read(inputStream);
                internalBackgroundImages.add(origin);
                inputStream.close();
            }
            String resPath = "/slide/block/1.png";
            inputStream = this.getClass().getResourceAsStream(resPath);
            assert inputStream != null;
            internalSlideBlock = ImageIO.read(inputStream);
            inputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public SlideResourceBO random() {
        if (Objects.isNull(externalBackgroundImages) || externalBackgroundImages.isEmpty()){
            return new SlideResourceBO(internalBackgroundImages.get(new Random().nextInt(internalBackgroundImages.size())),
                    internalSlideBlock);
        }
        return new SlideResourceBO(externalBackgroundImages.get(new Random().nextInt(externalBackgroundImages.size())),
                externalSlideBlock);
    }

}
