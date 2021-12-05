package com.notayessir.service.loader.impl;

import com.notayessir.bean.bo.resource.RotateResourceBO;
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
 * rotate resources loader
 */
public class RotateResource extends RandomResource<RotateResourceBO> {

    /**
     * internal static rotate resources
     */
    protected List<BufferedImage> internalImages = new ArrayList<>();

    /**
     * external rotate resources
     */
    protected List<BufferedImage> externalImages;


    public RotateResource() {
        loadInternal();
        if (isLoadExternal()){
            loadExternal();
        }
    }


    @Override
    protected void loadInternal() {
        try {
            InputStream inputStream;
            int resCount = 5;
            for (int i = 1; i <= resCount; i++) {
                String resPath = "/rotate/";
                String fileName = resPath + i + ".jpg";
                inputStream = this.getClass().getResourceAsStream(fileName);
                assert inputStream != null;
                BufferedImage origin = ImageIO.read(inputStream);
                internalImages.add(origin);
                inputStream.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }



    @Override
    public RotateResourceBO random() {
        if (Objects.isNull(externalImages) || externalImages.isEmpty()){
            return new RotateResourceBO(internalImages.get(new Random().nextInt(internalImages.size())));
        }
        return new RotateResourceBO(externalImages.get(new Random().nextInt(externalImages.size())));
    }

}
