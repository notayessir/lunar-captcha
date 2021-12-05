package com.notayessir.util;


import com.notayessir.bean.CaptchaConst;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * util of image trans to base64
 */
public class ImageUtil {

    public static BufferedImage base64ToImage(String base64) {
        ByteArrayInputStream inputStream = null;
        try {
            byte[] bytes = Base64Util.decodeFromString(base64);
            inputStream = new ByteArrayInputStream(bytes);
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            return null;
        }finally {
            if (!Objects.isNull(inputStream)){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private static String imageToBase64(BufferedImage image, String format) {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            ImageIO.write(image, format, os);
            byte[] jigsawImages = os.toByteArray();
            return Base64Util.encodeToString(jigsawImages);
        } catch (IOException e) {
            return null;
        }finally {
            if (!Objects.isNull(os)){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String imageToBase64(CaptchaConst.ImageFormat format, BufferedImage image) {
        String base64Str = imageToBase64(image, format.getValue());
        if (StringUtils.isNotBlank(base64Str)){
            return format.getBase64Prefix() + base64Str;
        }
        return base64Str;
    }


    public static String gifToBase64(byte[] arr){
        return CaptchaConst.ImageFormat.GIF.getBase64Prefix() + Base64Util.encodeToString(arr);
    }

}
