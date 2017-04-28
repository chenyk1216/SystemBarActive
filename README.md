# SystemBarActive
##**快速设置状态栏颜色，实现沉浸式状态栏的效果，代码采用构建者模式进行开发，简单快捷，一句搞定。**

###**特点**
关于半透明状态栏，在不用的Android版本之间也有着不同的效果。有些还受手机厂商定制的版本影响，其特点如下所示：
>1 . Android 4.4以下不支持半透明状态栏效果
>2 . Android 4.4以上5.0以下（不包括5.0）可使状态栏变色，效果为颜色渐变，可设置透明度
>3 . Android5.0以上可使状态栏变色，即可设置渐变效果，亦可让其显示纯色效果，同时支持透明度设置

**Android 4.4效果图：**

![Android 4.4效果](http://upload-images.jianshu.io/upload_images/2369466-dc1128024841778f?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**Android 5.0以上效果图**

![Android 5.0以上效果](http://upload-images.jianshu.io/upload_images/2369466-952d2656638f0006?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###**下载配置**
1、Android studio用户，可在项目中的 build.gradle 文件中添加如下引用

```
    compile 'android.dev.chenyk:systembarlib:1.1.0'
```
2、eclipse用户，将下载下来的jar包放入libs包中，[点我下载](https://github.com/chenykKits/SystemBarActive/edit/master/systembarlib-1.1.0.jar)

###**方法调用**
主要以构建者的模式进行，属性自由搭配，提供颜色、透明度、类型设置，简单易用，调用方法如下所示：

```
  new SystemBarManager.builder(thisActivity)
                .setTintType(SystemBarManager.TintType.GRADIENT)//状态栏类型，共纯色，渐变两种，默认纯色
                .setStatusBarColor(R.color.mainColor)//状态栏颜色
                .setAlpha(80)//状态栏的颜色透明度
                .build();
```
