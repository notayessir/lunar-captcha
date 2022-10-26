[English](https://github.com/notayessir/lunar-captcha/blob/master/README_EN.md) | 中文

## 简介

该项目用于生成验证码图片，目前已实现常见的 4 种类型，仅为后端实现，前端部分需要额外的开发工作；该项目更多作为一个自行实现的参考，生产环境需要考虑其他因素。

## 样例

##### 字母

![image](https://github.com/notayessir/lunar-captcha/blob/master/docs/image/image.png)

##### 旋转

![rotate](https://github.com/notayessir/lunar-captcha/blob/master/docs/image/rotate.png)

##### 点选

![click](https://github.com/notayessir/lunar-captcha/blob/master/docs/image/click.png)

##### 滑动

![slide](https://github.com/notayessir/lunar-captcha/blob/master/docs/image/slide.png)

## 用法 & API

创建字母型验证码：

```java
ImageCaptchaConfig captchaConfig = new ImageCaptchaConfig(); // 字母配置
captchaConfig.setAdhesion(5);				// 字母间的贴合度
captchaConfig.setLeftRotation(10);	// 字母旋转范围，左旋角度
captchaConfig.setRightRotation(0);	// 字母旋转范围，右旋角度
captchaConfig.setImageWidth(125);		// 图片宽度
captchaConfig.setImageHeight(45);		// 图片高度
captchaConfig.setCodeCount(4);			// 字母个数
captchaConfig.setFontSize(45);			// 字体大小
captchaConfig.setImageFormat(CaptchaConst.ImageFormat.GIF);	// 图片格式，支持 gif 或 jpg
captchaConfig.setCodeColor("255,255,255,255");							// 图片背景 rgba
ImageCaptcha imageCaptcha = new ImageCaptcha();	
CaptchaData<ImageCaptchaVO, ImageCaptchaBO> imageCaptcha  = imageCaptcha.create(captchaConfig);
/**
xxBO，包含了该类型验证码的原始信息，对于该类型来说，即字母内容，用于校验，通常存在缓存中；
xxVO，包含图片信息 base64，用于前端渲染逻辑；
*/
```

校验：

```java
imageCaptcha.check(ImageCaptchaBO backend, ImageCaptchaBO client);
```

创建旋转型验证码：

```java
RotateResource randomResource = new RotateResource();	// 加载默认资源
RotateCaptcha rotateCaptcha = new RotateCaptcha(randomResource);	// 注入
RotateCaptchaConfig config = new RotateCaptchaConfig();						// 旋转型配置
config.setRotateDeviation(10);		// 允许的误差角度
CaptchaData<RotateCaptchaVO, RotateCaptchaBO> rotateCaptcha = rotateCaptcha.create(config);
/**
xxBO，对于该类型来说，包含后端旋转的角度；
xxVO，包含被旋转后的图片 base64，用于前端渲染逻辑；
*/
```

校验：

```java
rotateCaptcha.check(RotateCaptchaBO backend, RotateCaptchaBO client);
```

创建点选型验证码：

```java
ClickResource clickResource = new ClickResource();	// 加载默认资源
ClickCaptcha clickCaptcha = new ClickCaptcha(clickResource); // 注入
ClickCaptchaConfig captchaConfig = new ClickCaptchaConfig();	// 点选型配置
captchaConfig.setPatternNum(4);					// 展示图案数
captchaConfig.setPatternSelectedNum(2);	// 点选图案数
CaptchaData<ClickCaptchaVO, ClickCaptchaBO> captchaData = clickCaptcha.create(captchaConfig);
/**
xxBO，对于该类型来说，包含点选图案的坐标；
xxVO，包含渲染图案的图片 base64，用于前端渲染逻辑；
*/
```

校验：

```java
clickCaptcha.check(ClickCaptchaBO backend, ClickCaptchaBO client);
```

创建滑动型验证码：

```java
SlideResource slideResource = new SlideResource();	// 加载默认资源
SlideCaptcha slideCaptcha = new SlideCaptcha(slideResource); // 注入
SlideCaptchaConfig captchaConfig = new SlideCaptchaConfig();	// 滑动性配置
captchaConfig.setDeviation(10);		// x 左右误差
CaptchaData<SlideCaptchaVO, SlideCaptchaBO> captchaData = slideCaptcha.create(captchaConfig);
/**
xxBO，对于该类型来说，包含需要滑动的 x 坐标；
xxVO，包含被抠图后的图片 base64，用于前端渲染逻辑；
*/
```

校验：

```java
slideCaptcha.check(SlideCaptchaBO backend, SlideCaptchaBO client);
```

## 拓展

通常，为了提高验证码的防御能力，后端会定时更换图片资源，该项目提供了加载外部资源的方法，以下做个简单 demo：

```java
public class ExternalRotateResource extends RotateResource {

    @Override
    protected boolean isLoadDynamic() {
        return true;		// 设置使用外部图片资源
    }

    @Override
    protected void loadDynamic() {
        // 读取资源，加载到 RotateResource 中的 dynamicImages 列表中
    }
}
```

## 待做

- [ ] 滑动型 demo
- [ ] 旋转型 demo
- [ ] 点选型 demo

## 建议

1. 前后端参数进行加密，增加破解难度；
2. 若要达到更高的防御目标，应该要定期更新图片资源；
3. 为了达到更好的网络体验，可以降低资源的清晰度，当前实现的类型，占用大概 5 kb - 30 kb 资源和空间，因为 base64 比直接图片输出增大了传输的内容大小；
4. 对验证码接口进行一定的 ip 访问限制；
5. 对验证码的操作行为进行一定的记录，结合相关的模型/算法进行判断；
5. 图片混淆、图片静态化；

## 其他

市面上也有诸多商业化的验证码项目，可以提供稳定的服务，以下几个服务商贴出供参考：

- [腾讯 007](https://007.qq.com/)
- [网易易盾](https://dun.163.com/trial/sense)
- [极验](https://www.geetest.com/Sensebot)
- [Google reCaptcha](https://www.google.com/recaptcha/about/)
