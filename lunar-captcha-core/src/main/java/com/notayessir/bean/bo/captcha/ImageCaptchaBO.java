package com.notayessir.bean.bo.captcha;

import java.io.Serializable;


/**
 * meta info of image captcha
 */
public class ImageCaptchaBO implements Serializable {


    private static final long serialVersionUID = 3972112001150445996L;

    /**
     * correct letter and number
     */
    private String string;


    public ImageCaptchaBO(String string) {
        this.string = string;
    }


    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
