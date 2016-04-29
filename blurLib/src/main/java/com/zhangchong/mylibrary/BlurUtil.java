package com.zhangchong.mylibrary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Zhangchong on 2016/1/9.
 */
public enum BlurUtil {
    INSTANCE;



    public void blur(byte[] bkg, int radius, int sampleSize, Action1<Bitmap> onNext) {
        Observable.create((Subscriber<? super Bitmap> subscriber)  ->
                subscriber.onNext(FastBlur.doBlur(createImageThumbnail(bkg, sampleSize), radius, false)))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext);

    }

    public byte[] bitmap2Bytes(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        return baos.toByteArray();
    }

    private Bitmap createImageThumbnail(byte[] dataSource, int inSmpleSize) {
        Bitmap bitmap;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        opts.inSampleSize = inSmpleSize;
        opts.inJustDecodeBounds = false;
        opts.inInputShareable = true;
        opts.inPurgeable = true;
        bitmap = BitmapFactory.decodeByteArray(dataSource, 0, dataSource.length, opts);
        return bitmap;
    }
}
