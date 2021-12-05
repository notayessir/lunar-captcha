package com.notayessir.controller;


import com.notayessir.bean.CaptchaConst;
import com.notayessir.bean.StandardReturn;
import com.notayessir.bean.params.CheckCaptchaParam;
import com.notayessir.bean.vo.CheckResultVO;
import com.notayessir.bean.vo.ResultBean;
import com.notayessir.manager.CaptchaManager;
import com.notayessir.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class Controller {


    @Autowired
    CaptchaService captchaService;


    @Autowired
    CaptchaManager captchaManager;

    @GetMapping("captcha")
    public ResultBean<?> jpg(CaptchaConst.CaptchaType captchaType){
        Object captchaVO = captchaService.createCaptcha(captchaType);
        return new ResultBean<>(StandardReturn.Code.SUCCESS, StandardReturn.Message.SUCCESS, captchaVO);
    }



    @PostMapping("captcha")
    public ResultBean<?> check(@RequestBody CheckCaptchaParam param){
        CheckResultVO checkResultVO = captchaService.checkCaptcha(param.getCliToken(), param.getTrackInfo());
        if (checkResultVO.isSuccess()){
            return new ResultBean<>(StandardReturn.Code.SUCCESS, StandardReturn.Message.SUCCESS, checkResultVO);
        }
        return new ResultBean<Void>(StandardReturn.Code.FAIL, StandardReturn.Message.FAIL);
    }


    @GetMapping("resource")
    public ResultBean<?> resource(String accessToken){
        if (captchaManager.checkAndDelAccessTokenWhenSuccess(accessToken)){
            return new ResultBean<Void>(StandardReturn.Code.SUCCESS, StandardReturn.Message.SUCCESS);
        }
        return new ResultBean<Void>(StandardReturn.Code.FAIL, StandardReturn.Message.FAIL);
    }


}
