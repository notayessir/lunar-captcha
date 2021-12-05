package com.notayessir.service.captcha.impl;

import com.notayessir.bean.CaptchaConst;
import com.notayessir.bean.CaptchaData;
import com.notayessir.bean.bo.captcha.ImageCaptchaBO;
import com.notayessir.bean.config.ImageCaptchaConfig;
import com.notayessir.bean.vo.ImageCaptchaVO;
import com.notayessir.service.captcha.Captcha;
import com.notayessir.service.gen.impl.GIFGenerator;
import com.notayessir.service.gen.impl.JPGGenerator;
import com.notayessir.util.RandomUtil;
import com.notayessir.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;


/**
 * implement of image captcha
 */
public class ImageCaptcha extends Captcha<ImageCaptchaConfig, ImageCaptchaVO, ImageCaptchaBO> {


    @Override
    public CaptchaData<ImageCaptchaVO, ImageCaptchaBO> create(ImageCaptchaConfig captchaConfig) {
        String string = generateString(captchaConfig);
        String mainImage = buildImage(captchaConfig, string);
        String cliToken = UUIDUtil.UUID();
        ImageCaptchaBO imageCaptchaBO = new ImageCaptchaBO(string);
        ImageCaptchaVO imageCaptchaVO = new ImageCaptchaVO(mainImage, cliToken);
        return new CaptchaData<>(imageCaptchaVO, imageCaptchaBO);
    }

    @Override
    public boolean check(ImageCaptchaBO backend, ImageCaptchaBO client) {
        return StringUtils.equalsIgnoreCase(backend.getString(), client.getString());
    }

    /**
     * create a captcha image in base64
     * @param captchaConfig     captcha config
     * @param string            specified letter
     * @return                  image in form of base64
     */
    private String buildImage(ImageCaptchaConfig captchaConfig, String string) {
        if (captchaConfig.getImageFormat() == CaptchaConst.ImageFormat.GIF){
            return new GIFGenerator(captchaConfig, string).gen();
        }
        return new JPGGenerator(captchaConfig, string).gen();
    }


    /**
     * randomly generate a string of letter by config
     * @param captchaConfig   captcha config
     * @return                a string of letter
     */
    private String generateString(ImageCaptchaConfig captchaConfig) {
        int codeCount = captchaConfig.getCodeCount();
        if (captchaConfig.getStringType() == CaptchaConst.StringType.MIX_STRING) {
            return RandomUtil.genLetterStr(codeCount);
        }
        return RandomUtil.genLetterAndNumberStr(codeCount);
    }


}
