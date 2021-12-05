package com.notayessir.bean;

public class Session {


    private Object bo;

    private CaptchaConst.CaptchaType captchaType;

    public Session(Object bo, CaptchaConst.CaptchaType captchaType) {
        this.bo = bo;
        this.captchaType = captchaType;
    }

    public Object getBo() {
        return bo;
    }

    public void setBo(Object bo) {
        this.bo = bo;
    }

    public CaptchaConst.CaptchaType getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(CaptchaConst.CaptchaType captchaType) {
        this.captchaType = captchaType;
    }
}
