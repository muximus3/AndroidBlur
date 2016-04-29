# AndroidBlur
An light repository simplify  blur any image in Android project. Depend on [RxJava](https://github.com/ReactiveX/RxJava),[RxAndroid](https://github.com/ReactiveX/RxAndroid),[Retrolambda](https://github.com/evant/gradle-retrolambda)

Usage
----
1.Add  dependency to your build.gradle
``` groovy
compile 'com.muximus3:AndroidBlur:1.0.0'
```
2.Blur any image you want
``` java
//blur local image, and apply to ImageView
 BlurUtil.INSTANCE.blur(BlurUtil.INSTANCE.bitmap2Bytes(
                    BitmapFactory.decodeResource(getResources(), R.drawable.puppy), 100), 20, 8,
                    bitmap -> {
                        ...
                        iv.setImageBitmap(bitmap);
                    }
            );
            
//download and blur image, as i use Glide, you may use any other libs like Picasso, etc.
Glide.with(this).load(url).asBitmap().toBytes().centerCrop().into(new SimpleTarget<byte[]>() {
                @Override
                public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
                    BlurUtil.INSTANCE.blur(resource, 20, 8, bitmap -> {
                        ...
                        iv.setImageBitmap(bitmap);
                    });
                }
            });
```
method  `blur(byte[] bkg, int radius, int sampleSize, Action1<Bitmap> onNext)` accept four parameters:
- `bkg`  the byte[] source of image, usually a downloading image or loacal iamge.
- `radius` the radius you want to blur, as i set it's 20
- `sampleSize` in case of outofmemorry error, source would be commpressed by 1/smpleSize, a bigger number makes blur faster.
- `onNext`     a callBack with bitmap data, it's what we want.
