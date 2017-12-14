package com.zhekouxingqiu.main.activity.My;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.edmodo.cropper.CropImageView;

import common.base.TitleAppCompatActivity;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.StatusBarUtil;
import utils.photopicker.utils.CropImageNewTask;
import wiget.LoadingDialog;

/**
 * 新裁剪控件
 * Created by johnreese on 15/12/14.
 */
public class CropNewActivity extends TitleAppCompatActivity {

    public final static String IMAGE = "image";

    public final static String WIDTH = "width";

    public final static String HEIGHT = "height";

    CropImageView cropImageView;

    ImageView ivBack;

    Button btnCrop;

    /**
     * onCreateView:初始化界面
     *
     * @param savedInstanceState
     * @author linhuan 2016/1/27 0027 11:27
     */
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_crop);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);

    }

    @Override
    protected void initView() {
        super.initView();

        cropImageView = findView(R.id.CropImageView);
        ivBack = findView(R.id.btn_back);
        btnCrop = findView(R.id.btn_crop);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCrop();
            }
        });
    }

    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            // do something with the bitmap
            // for demonstration purposes, let's just set it to an ImageView
            cropImageView.setImageBitmap( bitmap );
        }
    };

    @Override
    protected void initData() {
        super.initData();
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        String path = intent.getStringExtra(IMAGE);
        int width = intent.getIntExtra(WIDTH, 1);
        int height = intent.getIntExtra(HEIGHT, 1);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setAspectRatio(width, height);

        Glide.with(this.getApplicationContext())
                .load(path)
                .asBitmap()
                .into(target);
    }



    /**
     * function: 后退处理
     *
     * @param keyCode
     * @param event
     * @author:linhuan 2014年8月5日 下午7:59:01
     */
    @Override
    protected void doBack(int keyCode, KeyEvent event) {
        NavigationHelper.finish(this, RESULT_OK, null);
    }

    @Override
    public void onDestroy() {
//        if (cropImageView != null)
//            cropImageView.recycle();
        cropImageView = null;
        super.onDestroy();
    }



    private void doCrop() {
        LoadingDialog.getInstance(this).showLoading();
        CropImageNewTask cropImageTask = new CropImageNewTask(this);
        cropImageTask.setParam(cropImageView.getCroppedImage());
        cropImageTask.execute();
    }

}
