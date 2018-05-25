
## 该库用于实现 Animate Drawable

实现动画Drawable 需要继承 Drawable 并且实现 Animatable 接口

### BiliBiliLoadingDrawable

创建Drawable

```
BiliBiliLoadingDrawable biliLoadingDrawable = new BiliBiliLoadingDrawable(300);
biliLoadingDrawable.setStrokeWidth(10);
biliLoadingDrawable.setRepeat(3);
biliLoadingDrawable.setColor(getResources().getColor(R.color.colorAccent));
```

设置给ImageView

```
mAnimate.setImageDrawable(biliLoadingDrawable);
```

开始

```
biliLoadingDrawable.start();
```

![](img/pic01.gif)

### CircleLoadingDrawable

创建

```
CircleLoadingDrawable circleLoadingDrawable = new CircleLoadingDrawable(300);
mImageView.setImageDrawable(circleLoadingDrawable);
```

开始

```
circleLoadingDrawable.start();
```

结束

```
circleLoadingDrawable.stop();
```

![](img/pic02.gif)

### AlphaProgressDrawable 

通过控制图片的 alpha 实现,两张图片之间的变换

![](img/pic03.gif)

```
// 创建drawable

Bitmap bitmapNormal = BitmapFactory.decodeResource(
        getResources(),
        R.drawable.home_normal
);
Bitmap bitmapSelected = BitmapFactory.decodeResource(
        getResources(),
        R.drawable.home_selected
);
AlphaProgressDrawable drawable = new AlphaProgressDrawable(
        bitmapNormal,
        bitmapSelected);
```

```
// 设置给imageView

imageView.setImageDrawable(drawable);
```

```
// 更新进度,实现变换

drawable.setProgress(progress)
```

#### ProgressColorTextView

>该view是一个特殊的TextView,具有根据进度设置文字颜色的功能

效果即为上图效果

```
// 创建textView

int colorNormal = getResources().getColor(R.color.textColorNormal);
int colorSelect = getResources().getColor(R.color.textColorSelected);

ProgressColorTextView textView = new ProgressColorTextView(WechatBottomActivity.this);

// 设置起始结束颜色
textView.setTextColor(colorNormal, colorSelect);
```

```
// 设置进度,变更颜色
textView.setProgress(progress);
```