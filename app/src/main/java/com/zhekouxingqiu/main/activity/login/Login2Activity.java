package com.zhekouxingqiu.main.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;


import app.AppConfig;
import iconicfont.IconicFontDrawable;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import push.PushHelper;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.InputMethodHelper;
import so.bubu.lib.helper.LogHelper;
import so.bubu.lib.helper.NetWorkHelper;
import so.bubu.lib.helper.StatusBarUtil;
import so.bubu.lib.helper.ToastHelper;
import utils.AVAnalyticsHelper;
import utils.CommonUtils;
import utils.SharedPreferencesHelp;
import utils.UIHelper;
import utils.UserActionListener;
import utils.UserHelper;

/**
 * 登陆页
 * Created by wangwn on 2016/4/14.
 */
public class Login2Activity extends AppCompatActivity {


    private TextView tvForget;
    private Button btnLogin;
    private EditText edtName;
    private EditText edtPassword;
    private String name;
    private String password;
    private ImageView ivBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBar();
        initView();
    }

    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }

    private void initView() {

        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvForget = (TextView) findViewById(R.id.tv_forget);
        btnLogin = (Button) findViewById(R.id.btn_login);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtPassword = (EditText) findViewById(R.id.edt_password);

        IconicFontDrawable iconicFont = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_BACK, getResources().getColor(R.color.color_000000));
        ivBack.setBackground(iconicFont);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        IconicFontDrawable iconicUserName = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_NAME, getResources().getColor(R.color.color_b2b2b2));
        IconicFontDrawable iconicPWD = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_PWD, getResources().getColor(R.color.color_b2b2b2));
        findViewById(R.id.v_icon_user).setBackground(iconicUserName);
        findViewById(R.id.v_icon_pwd).setBackground(iconicPWD);

        initEvent();
    }


    private void initEvent() {

        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.FORGET_PASSWORD);
                UIHelper.getInstance().openForgetActivity(Login2Activity.this);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isSingleClick())
                    return;
                if (checkLoginInfo()) {
                    onSubmit();
                }
            }
        });

    }

    private boolean checkLoginInfo() {
        name = edtName.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        if (name.isEmpty()) {
            ToastHelper.showToast(R.string.error_name_length);
            return false;
        } else if (password.isEmpty()) {
            ToastHelper.showToast(R.string.error_password_empty);
            return false;
        }
        return true;
    }

    private void onSubmit() {
        InputMethodHelper.closeInputMethod(Login2Activity.this);
        if (!NetWorkHelper.isNetworkAvailable()) {
            ToastHelper.showToast(R.string.error_network_connecting);
            return;
        }

        UserHelper.getInstance().logInInBackground(name, password, new UserActionListener() {
            @Override
            public void actionSuccess(AVUser avUser) {
                afterSubmit(avUser);
            }

            @Override
            public void actionFailure(AVException e) {

                ToastHelper.showToast(R.string.error_login);
                LogHelper.e(e.getCode() + "||" + e.getMessage());
            }
        });
//        if (mCurLoginType.equals("phone")) {
//
//            UserHelper.getInstance().loginByMobilePhoneNumber(name, password, new UserActionListener() {
//                @Override
//                public void actionSuccess(AVUser avUser) {
//                    afterSubmit(avUser);
//                }
//
//                @Override
//                public void actionFailure(AVException e) {
//                    LoadingDialog.getInstance(Login2Activity.this).hideLoading();
//                    ToastHelper.showToast(R.string.error_login);
//                    LogHelper.e(e.getCode() + "||" + e.getMessage());
//                }
//            });
//        }else{
//
//        }

    }

    private void afterSubmit(AVUser avUser) {
//        LoadingDialog.getInstance(Login2Activity.this).hideLoading();
        if (avUser != null) {
            ToastHelper.showToast(R.string.login_success);
            PushHelper.setInstallationId(SharedPreferencesHelp.getInstallationId(AppConfig.INSTALLATIION_KEY));
            Intent intent = new Intent();
//            intent.setAction(Constants.YZ_RECEIVER_FILTER);
            intent.putExtra("UserId", avUser.getObjectId());
            this.sendBroadcast(intent);
            Login2Activity.this.finish();
        } else {
            ToastHelper.showToast(R.string.error_login);
        }
    }
}
