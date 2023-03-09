English | [中文](https://github.com/notayessir/lunar-captcha/blob/master/README.md)

## Introduction

Four common types of captcha are implemented which aimed at intercepting automatic robots\scripts.

Notice:

1. project now only has backend implementation, frontend implementation need to extra work.
2. project is more as a reference for self-realization, and the production environment needs to consider other factors.

## Demo

##### Letter

![image](https://github.com/notayessir/lunar-captcha/blob/master/docs/image/image.png)

##### Rotate

![rotate](https://github.com/notayessir/lunar-captcha/blob/master/docs/image/rotate.png)

##### Click

![click](https://github.com/notayessir/lunar-captcha/blob/master/docs/image/click.png)

##### Slide

![slide](https://github.com/notayessir/lunar-captcha/blob/master/docs/image/slide.png)

## Usage & API

create letter captcha :

```java
// image config
ImageCaptchaConfig captchaConfig = new ImageCaptchaConfig();
// adhesion of each letter
captchaConfig.setAdhesion(5);
// range of left rotation
captchaConfig.setLeftRotation(10);
// range of right rotation
captchaConfig.setRightRotation(0);
// image width
captchaConfig.setImageWidth(125);
// image height
captchaConfig.setImageHeight(45);
// count of letter
captchaConfig.setCodeCount(4);
// font of letter
captchaConfig.setFontSize(45);
// image form
captchaConfig.setImageFormat(CaptchaConst.ImageFormat.GIF);
// image rgba
captchaConfig.setCodeColor("255,255,255,255");
ImageCaptcha imageCaptcha = new ImageCaptcha();	
CaptchaData<ImageCaptchaVO, ImageCaptchaBO> captchaData  = imageCaptcha.create(captchaConfig);
/**
xxBO，include meta info, letter string for this kind of captcha, usually storage in cache for validate;
xxVO，image in base64，return to frontend;
*/
```

check :

```java
imageCaptcha.check(ImageCaptchaBO backend, ImageCaptchaBO client);
```

create rotate captcha :

```java
// load static resources
RotateResource randomResource = new RotateResource();
// inject to service
RotateCaptcha rotateCaptcha = new RotateCaptcha(randomResource);
// rotate config
RotateCaptchaConfig config = new RotateCaptchaConfig();
// deviation range
config.setRotateDeviation(10);
CaptchaData<RotateCaptchaVO, RotateCaptchaBO> rotateCaptcha = rotateCaptcha.create(config);
/**
xxBO，include correct rotate angle for this implement 
xxVO，image in base64，return to frontend;
*/
```

check ：

```java
rotateCaptcha.check(RotateCaptchaBO backend, RotateCaptchaBO client);
```

create click captcha :

```java
// load static resources
ClickResource clickResource = new ClickResource();
// inject to service
ClickCaptcha clickCaptcha = new ClickCaptcha(clickResource);
// click captcha
ClickCaptchaConfig captchaConfig = new ClickCaptchaConfig();
// patterns to show
captchaConfig.setPatternNum(4);
// patterns to click 
captchaConfig.setPatternSelectedNum(2);
CaptchaData<ClickCaptchaVO, ClickCaptchaBO> captchaData = clickCaptcha.create(captchaConfig);
/**
xxBO，include correct click coordinate for this implement 
xxVO，image in base64，return to frontend;
*/
```

check :

```java
clickCaptcha.check(ClickCaptchaBO backend, ClickCaptchaBO client);
```

create slide captcha :

```java
// load static resources
SlideResource slideResource = new SlideResource();
// inject to service
SlideCaptcha slideCaptcha = new SlideCaptcha(slideResource);
// slide config
SlideCaptchaConfig captchaConfig = new SlideCaptchaConfig();
// x axis deviation
captchaConfig.setDeviation(10);	
CaptchaData<SlideCaptchaVO, SlideCaptchaBO> captchaData = slideCaptcha.create(captchaConfig);
/**
xxBO，nclude correct click x axis for this implement
xxVO，image in base64，return to frontend;
*/
```

check :

```java
slideCaptcha.check(SlideCaptchaBO backend, SlideCaptchaBO client);
```

## Extend

Usually, in order to improve captcha defensive ability, captcha resources will be updated by backend in timing, for example :

```java
public class ExternalRotateResource extends RotateResource {    
  @Override    
  protected boolean isLoadDynamic() {        
    return true;		
  }    
  
  @Override    
  protected void loadDynamic() {        
    // load your resources    
  }
}
```

## Todo

- [ ] slide frontend demo
- [ ] rotate frontend demo
- [ ] click frontend demo

## Suggestion

1. encrypt frontend and backend param;
2. in order to improve captcha defensive ability, captcha resources should be updated by backend in timing;
3. in order to improve speed of network, reduce resouces clarity, or directly flush image to frontend in place base64, content of base64 is larger than origin image;  
4. interface limitation if needed;
5. record the behavior of user, combine some algo model to identify risks;
5. blur the image and make image static;

## Other 

there are several business project can supply stable service, services are below for reference :

- [腾讯 007](https://007.qq.com/)
- [网易易盾](https://dun.163.com/trial/sense)
- [极验](https://www.geetest.com/Sensebot)
- [Google reCaptcha](https://www.google.com/recaptcha/about/)







