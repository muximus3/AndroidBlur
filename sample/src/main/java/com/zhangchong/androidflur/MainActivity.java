package com.zhangchong.androidflur;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zhangchong.mylibrary.BlurUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_online)
    Button btnOnline;
    @Bind(R.id.btn_local)
    Button btnLocal;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.iv_corner)
    ImageView ivCorner;
    private String url = "http://img2.mtime.cn/up/208/568208/A92B0631-1C5E-45E7-A0D9-62E66ABADE73_o.jpg";
    private long startTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Glide.with(this).load(url).centerCrop().into(iv);

        btnOnline.setOnClickListener(v -> {
            btnOnline.setEnabled(false);
            btnOnline.postDelayed(() -> btnOnline.setEnabled(true), 1000);
            startTime = System.currentTimeMillis();
            Glide.with(this).load(url).centerCrop().into(ivCorner);
            Glide.with(this).load(url).asBitmap().toBytes().centerCrop().into(new SimpleTarget<byte[]>() {
                @Override
                public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
                    BlurUtil.INSTANCE.blur(resource, 20, 8, bitmap -> {
                        tv.setText("Download and blur time: " + (System.currentTimeMillis() - startTime) + "ms");
                        iv.setImageBitmap(bitmap);
                    });
                }
            });
        });

        btnLocal.setOnClickListener(v -> {
            btnLocal.setEnabled(false);
            btnLocal.postDelayed(() -> btnLocal.setEnabled(true), 1000);
            startTime = System.currentTimeMillis();
            ivCorner.setImageResource(R.drawable.puppy);
            BlurUtil.INSTANCE.blur(BlurUtil.INSTANCE.bitmap2Bytes(
                    BitmapFactory.decodeResource(getResources(), R.drawable.puppy), 100), 20, 8,
                    bitmap -> {
                        tv.setText("Local image blur time: " + (System.currentTimeMillis() - startTime) + "ms");
                        iv.setImageBitmap(bitmap);
                    }
            );
        });

    }
}
