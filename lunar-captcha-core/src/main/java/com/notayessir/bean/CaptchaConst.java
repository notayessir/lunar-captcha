package com.notayessir.bean;

/**
 * some const of captcha
 */
public interface CaptchaConst {


    /**
     * kind of captcha
     */
    enum CaptchaType{

        /**
         * click
         */
        CLICK_PATTERN,

        /**
         * slide
         */
        SLIDE_BLOCK,

        /**
         * image
         */
        LETTER_IMAGE,

        /**
         * rotate
         */
        ROTATE_PICTURE
    }

    /**
     * kind of image
     */
    enum ImageFormat {
        PNG("png", "data:image/png;base64,"),
        JPG("jpg", "data:image/jpg;base64,"),
        GIF("gif", "data:image/gif;base64,");

        private final String value;

        private final String base64Prefix;

        ImageFormat(String value, String base64Prefix) {
            this.value = value;
            this.base64Prefix = base64Prefix;
        }

        public String getBase64Prefix() {
            return base64Prefix;
        }

        public String getValue() {
            return value;
        }
    }


    /**
     * margin of click
     */
    int CLICK_START_MARGIN = 10;


    /**
     * rotate gap angle of rotate
     */
    int PICTURE_ROTATE_GAP_ANGLE = 5;



    /**
     * content of image captcha
     */
    enum StringType {


        /**
         * only letter
         */
        MIX_STRING,

        /**
         * mix letter and number
         */
        MIX_STRING_AND_NUMBER,

    }

}
