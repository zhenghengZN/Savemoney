package com.zhekouxingqiu.main.activity.login;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;

import app.AppConfig;
import iconicfont.IconicFontDrawable;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import push.PushHelper;

import com.zhekouxingqiu.main.R;

import so.bubu.lib.helper.DelayTaskHelper;
import so.bubu.lib.helper.InputMethodHelper;
import so.bubu.lib.helper.LogHelper;
import so.bubu.lib.helper.NetWorkHelper;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.StatusBarUtil;
import so.bubu.lib.helper.ToastHelper;
import so.bubu.lib.wiget.ClearEditText;
import utils.CommonUtils;
import utils.SharedPreferencesHelp;
import utils.StringUtils;
import utils.UserActionListener;
import utils.UserHelper;
import wiget.TimeCount;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        setContentView(R.layout.activity_mine_login);
//        StatusBarUtil.setTransparent(this);
        initView();
    }


    private ImageView iv_back;
    private View v_icon_user, v_icon_mail;
    private ClearEditText edt_name, edt_mail;
    private Button btn_login, btn_code;

    private void initView() {

        iv_back = (ImageView) findViewById(R.id.iv_back);
        v_icon_user = findViewById(R.id.v_icon_user);
        edt_name = (ClearEditText) findViewById(R.id.edt_name);

        v_icon_mail = findViewById(R.id.v_icon_mail);
        edt_mail = (ClearEditText) findViewById(R.id.edt_mail);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_code = (Button) findViewById(R.id.btn_code);
        btn_code.setEnabled(false);
        edt_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneNumber = edt_name.getText().toString().trim();
                if (phoneNumber.length() == 11 && StringUtils.checkPhone(phoneNumber)) {
                    btn_code.setEnabled(true);
                    btn_code.setBackgroundResource(R.drawable.btn_get_code_background_true);
                } else {
                    btn_code.setEnabled(false);
                    btn_code.setBackgroundResource(R.drawable.btn_get_code_background);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        IconicFontDrawable iconicFont = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_BACK, getResources().getColor(R.color.color_000000));
        iv_back.setBackground(iconicFont);

        IconicFontDrawable iconicCode = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_CODE, getResources().getColor(R.color.color_b2b2b2));
        IconicFontDrawable iconicUserName = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_NAME, getResources().getColor(R.color.color_b2b2b2));
        v_icon_user.setBackground(iconicUserName);
        v_icon_mail.setBackground(iconicCode);

        edt_mail.setHint(ResourceHelper.getString(R.string.input_code));
        edt_name.setHint(ResourceHelper.getString(R.string.mobilephone_num));
        edt_name.setFocusable(true);
        edt_name.setFocusableInTouchMode(true);
        edt_name.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        initEvent();
    }

    private void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isSingleClick())
                    return;
                if (checkMobilePhoneNumber()) {
                    onRequestMobilePhoneCode();
                    new TimeCount(LoginActivity.this,60000,btn_code).start();
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isSingleClick())
                    return;
                if (checkUserInfo()) {
//                    if (isPhone()) {
                    signUpOrLoginByMobilePhoneInBackground();
//                    } else {
//                        onSubmit();
//                    }

                }
            }
        });

    }

    private void signUpOrLoginByMobilePhoneInBackground() {

        InputMethodHelper.closeInputMethod(LoginActivity.this);
        if (!NetWorkHelper.isNetworkAvailable()) {
            ToastHelper.showToast(R.string.error_network_connecting);
            return;
        }

        UserHelper.getInstance().signUpOrLoginByMobilePhoneInBackground(name, mail, new UserActionListener() {
            @Override
            public void actionSuccess(AVUser avUser) {

//                avUser.setUsername(name);
//                avUser.setPassword("");
//                avUser.put(AppConfig.REGISTER_FROM_KEY, ResourceHelper.getString(R.string.app_name));
//                avUser.put(UserKey.USER_NICK_NAME, avUser.getUsername());
//                avUser.put(UserKey.USER_PERSONAL_PROFILE, ResourceHelper.getString(R.string.user_desc));
//                avUser.saveInBackground();
//                AVUser.logInInBackground(name, "", new LogInCallback<AVUser>() {
//                    @Override
//                    public void done(AVUser avUser, AVException e) {
//                        if (e == null) {
//                            LogHelper.d("login success");
//                        }
//                    }
//                });
                afterSignIn(avUser, null);

            }

            @Override
            public void actionFailure(AVException e) {


//                if (e.getCode() == ErrorUtils.ERROR_NAME_EXIST)
//                    ToastHelper.showToast(R.string.error_register_name_exist);
//                else if (e.getCode() == ErrorUtils.ERROR_MAIL_EXIST) {
//                    ToastHelper.showToast(R.string.error_register_mail_exist);
//                } else {
//                    ToastHelper.showToast(R.string.error_register);
//                }

                LogHelper.d(e.getCode() + "||" + e.getLocalizedMessage() + "||" + e.getMessage());
            }
        });


    }

    private void onRequestMobilePhoneCode() {

        UserHelper.getInstance().requestSmsCodeWithPhoneNumber(name, new UserActionListener() {
            @Override
            public void actionSuccess(AVUser avUser) {
                ToastHelper.showToast(R.string.send_phone_num);
            }

            @Override
            public void actionFailure(AVException e) {
                ToastHelper.showToast(R.string.send_phone_num_failure);
                LogHelper.d(e.getCode() + "||" + e.getLocalizedMessage() + "||" + e.getMessage());
            }
        });

    }

    private String name;
    private String mail;

    private boolean checkMobilePhoneNumber() {
        name = edt_name.getText().toString().trim();
        mail = edt_mail.getText().toString().trim();
        if (name.isEmpty() || !StringUtils.checkPhone(name)) {
            ToastHelper.showToast(R.string.error_phone_num);
            return false;
        }

        return true;
    }

    private boolean checkUserInfo() {
        mail = edt_mail.getText().toString().trim();
        name = edt_name.getText().toString().trim();

        if (mail.isEmpty() || mail.length() > 6) {
            ToastHelper.showToast(R.string.error_sms_code);
            return false;
        }

        if (name.isEmpty()) {
            ToastHelper.showToast(R.string.error_name_length);
            return false;
        }

        return true;
    }


    public void afterSignIn(AVUser avUser, AVException e) {


        if (avUser != null && e == null) {
            ToastHelper.showToast(R.string.register_success);
            PushHelper.setInstallationId(SharedPreferencesHelp.getInstallationId(AppConfig.INSTALLATIION_KEY));

            LoginActivity.this.finish();

        } else {
            ToastHelper.showToast(R.string.error_login);
        }
    }
}
