package com.zhekouxingqiu.main.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.sso.UMSsoHandler;

import iconicfont.IconicFontDrawable;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import share.SocialShareService;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.StatusBarUtil;
import utils.AVAnalyticsHelper;
import utils.CommonUtils;
import utils.UIHelper;

/**
 * 登录注册页
 * Created by wangwn on 2016/5/19.
 */
public class StartActivity extends AppCompatActivity {


    private Button btnLogin;
    private Button btnRegister;
    private View btnWeixin;
    private View btnSina;
    private ImageView mImgLogo;
    private TextView mBtnForget;
    private TextView mBtnUserRegister;
    private boolean isFromYz;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
//        isFromYz = getIntent().getBooleanExtra(Constants.YZ_TO_LOGIN, Boolean.FALSE);
        initView();
        setStatusbar();
    }

    private void setStatusbar() {

        StatusBarUtil.setTransparent(this);
    }

    private void initView() {

        mImgLogo = (ImageView) findViewById(R.id.start_logo);
        IconicFontDrawable iconicFont = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_DEFAULT_LOGO, getResources().getColor(R.color.color_82cd6b));
        mImgLogo.setBackground(iconicFont);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_phone_register);
        btnWeixin = findViewById(R.id.weixin_login);
        btnSina = findViewById(R.id.sina_login);
        mBtnForget = (TextView) findViewById(R.id.btn_forget_pwd);
        mBtnUserRegister = (TextView) findViewById(R.id.btn_account_register);

        initEvent();

//        if (AppConfig.IS_USER && !AppConfig.IS_DISNEY) {
//            findViewById(R.id.ll_three_login).setVisibility(View.INVISIBLE);
//        }

    }

    private void initEvent() {

        final UIHelper uiHelper = UIHelper.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isSingleClick())
                    return;
                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.LOGIN_ACCOUNT);
                uiHelper.openLoginActivity(StartActivity.this);
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isSingleClick())
                    return;
                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.REGISTER_PHONE);
                uiHelper.openRegisterActivity(StartActivity.this, UIHelper.REGISTER_PHONE);
                if (isFromYz) {
                    finish();
                }
            }
        });
        btnWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isSingleClick())
                    return;
                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.LOGIN_WEIXING);
                SocialShareService.getInstance().wxOAuth(StartActivity.this);
            }
        });
        btnSina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isSingleClick())
                    return;
                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.LOGIN_WEIBO);
                SocialShareService.getInstance().sinaOAuth(StartActivity.this);
            }
        });

        mBtnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.FORGET_PASSWORD);
                UIHelper.getInstance().openForgetActivity(StartActivity.this);
//                if (isFromYz) {
//                    finish();
//                }
            }
        });

        mBtnUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.REGISTER_OTHER);
                uiHelper.openRegisterActivity(StartActivity.this, UIHelper.REGISTER_ACCOUNT);
//                if (isFromYz) {
//                    finish();
//                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**使用SSO授权必须添加如下代码  */
        UMSsoHandler ssoHandler = SocialShareService.getInstance().getController().getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

}
