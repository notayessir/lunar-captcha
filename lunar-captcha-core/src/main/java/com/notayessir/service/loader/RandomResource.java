package com.notayessir.service.loader;


/**
 * captcha resources loader
 * @param <R>   type of captcha resources
 */
public abstract class RandomResource<R> {


    /**
     * randomly return a patch of resources
     * @return  captcha resource
     */
    public abstract R random();

    /**
     * load static resources
     */
    protected abstract void loadInternal();


    /**
     * is load external
     * @return  default return false
     */
    protected boolean isLoadExternal() {
        return false;
    }

    /**
     * load external resources
     */
    protected void loadExternal() {}


}
