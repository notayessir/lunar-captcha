package com.notayessir.bean;


/**
 * keep the view obj and bis obj
 */
public class CaptchaData<V, B> {

    public CaptchaData(V VO, B BO) {
        this.VO = VO;
        this.BO = BO;
    }

    private V VO;

    private B BO;

    public B getBO() {
        return BO;
    }

    public void setBO(B BO) {
        this.BO = BO;
    }

    public V getVO() {
        return VO;
    }

    public void setVO(V VO) {
        this.VO = VO;
    }
}
