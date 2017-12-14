package com.zhekouxingqiu.main.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogUtil;
import com.bumptech.glide.Glide;


import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.ScreenUtils;
import so.bubu.lib.helper.StatusBarUtil;
import com.zhekouxingqiu.main.R;



/**
 * 加载loading
 * Created by wangwn on 2016/5/19.
 */
public class LoadingActivity extends AppCompatActivity {

    private ImageView mImgBackgroup;
    private int head_height;
    private AVUser mCurrentUser;
    private boolean mIsFirst;
    private SharedPreferences mSharedPreferences;
    private boolean mInvitationCode;
    private static final int ANIMATION_TIME = 1000;
    private static final float SCALE_END = 1.13F;
    private Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        head_height = StatusBarUtil.getStatusBarHeight(this);
        mImgBackgroup = (ImageView) findViewById(R.id.img_loading);

        initBackgroup();

        setStatusBar();

        LogUtil.log.e("packname",getPackageName());
//        startAnimator();

        getIsFirstAndLogin();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startToMainActivity();
            }
        }, 1000);

       // mInvitationCode = SharedPreferencesHelp.getInvitationCode(INVITE_CODE_KEY);
//        AVQueryUtil.avQueryByAvObject("Redeem", "isUsed");
    }

    private void startAnimator() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mImgBackgroup, "alpha", 0.1f, 1f);
        objectAnimator.setDuration(2000);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

//                getIsFirstAndLogin();

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startToMainActivity();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }


    private void startAnim()
    {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mImgBackgroup, "scaleX", 0f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mImgBackgroup, "scaleY", 0f, 1f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_TIME).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter()
        {


            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {


                startToMainActivity();

            }
        });
    }



    private void getIsFirstAndLogin() {

        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        mIsFirst = mSharedPreferences.getBoolean("isFirst", true);

    }

    private void initBackgroup() {


        Glide.with(this)
                .load(R.drawable.app_loading)
                .override(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this))
                .centerCrop()
                .into(mImgBackgroup);
    }

    private void setStatusBar() {

        StatusBarUtil.setTransparent(this);
    }

    private void startToMainActivity() {

        mCurrentUser = AVUser.getCurrentUser();

        NavigationHelper.fadeInActivity(LoadingActivity.this, MainActivity.class, null, true);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
