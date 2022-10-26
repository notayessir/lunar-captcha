English | [中文](https://github.com/notayessir/lunar-captcha/blob/master/README.md)

## Introduction

Project are used in generate captcha, four common captcha are implemented for now and only backend implementation, frontend implementation need to extra implement.

Project is more as a reference for self-realization, and the production environment needs to consider other factors.

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
ImageCaptchaConfig captchaConfig = new ImageCaptchaConfig(); // image config
captchaConfig.setAdhesion(5);				// adhesion of each letter
captchaConfig.setLeftRotation(10);	// range of left rotation
captchaConfig.setRightRotation(0);	// range of right rotation
captchaConfig.setImageWidth(125);		// image width
captchaConfig.setImageHeight(45);		// image height
captchaConfig.setCodeCount(4);			// count of letter
captchaConfig.setFontSize(45);			// font of letter
captchaConfig.setImageFormat(CaptchaConst.ImageFormat.GIF);	// image form
captchaConfig.setCodeColor("255,255,255,255");							// image rgba
ImageCaptcha imageCaptcha = new ImageCaptcha();	
CaptchaData<ImageCaptchaVO, ImageCaptchaBO> imageCaptcha  = imageCaptcha.create(captchaConfig);
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
RotateResource randomResource = new RotateResource();	// load static resources
RotateCaptcha rotateCaptcha = new RotateCaptcha(randomResource);	// inject to service
RotateCaptchaConfig config = new RotateCaptchaConfig();						// rotate config
config.setRotateDeviation(10);		// deviation range
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
ClickResource clickResource = new ClickResource();	// load static resources
ClickCaptcha clickCaptcha = new ClickCaptcha(clickResource); // inject to service
ClickCaptchaConfig captchaConfig = new ClickCaptchaConfig();	// click captcha
captchaConfig.setPatternNum(4);					// patterns to show 
captchaConfig.setPatternSelectedNum(2);	// patterns to click 
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
SlideResource slideResource = new SlideResource();	// load static resources
SlideCaptcha slideCaptcha = new SlideCaptcha(slideResource); // inject to service
SlideCaptchaConfig captchaConfig = new SlideCaptchaConfig();	// slide config
captchaConfig.setDeviation(10);		// x axis deviation
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
    // your resources    
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







