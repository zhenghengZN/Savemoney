package com.zhekouxingqiu.main.activity.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;

import app.AppConfig;
import iconicfont.IconicFontDrawable;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import push.PushHelper;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.InputMethodHelper;
import so.bubu.lib.helper.LogHelper;
import so.bubu.lib.helper.NetWorkHelper;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.StatusBarUtil;
import so.bubu.lib.helper.ToastHelper;
import utils.AVAnalyticsHelper;
import utils.CommonUtils;
import utils.ErrorUtils;
import utils.SharedPreferencesHelp;
import utils.StringUtils;
import utils.UIHelper;
import utils.UserKey;

/**
 * 注册页
 * Created by wangwn on 2016/4/14.
 */
public class RegisterActivity extends AppCompatActivity {

    private ImageView ivBack;

    private String name;
    private String mail;
    private String password;
    private EditText edtName;
    private EditText edtMail;
    private EditText edtPassword;
    private Button btnRegister;
    private String registerType = UIHelper.REGISTER_ACCOUNT;
    private Button mbtnGetCode;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedBundle) {
        super.onCreate(savedBundle);
        setContentView(R.layout.activity_register);
        Bundle extras = getIntent().getExtras();
        if (Helper.isNotEmpty(extras)) {
            registerType = extras.getString(UIHelper.REGISTER_TYPE);
        }

        setStatusBar();
        initView();
    }


    private void initView() {


        ivBack = (ImageView) findViewById(R.id.iv_back);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtMail = (EditText) findViewById(R.id.edt_mail);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
        mbtnGetCode = (Button) findViewById(R.id.btn_code);
        IconicFontDrawable iconicFont = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_BACK, getResources().getColor(R.color.color_000000));
        ivBack.setBackground(iconicFont);

        IconicFontDrawable iconicUserName = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_NAME, getResources().getColor(R.color.color_b2b2b2));
        IconicFontDrawable iconicPWD = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_PWD, getResources().getColor(R.color.color_b2b2b2));
        IconicFontDrawable iconicCode = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_CODE, getResources().getColor(R.color.color_b2b2b2));
        findViewById(R.id.v_icon_user).setBackground(iconicUserName);
        findViewById(R.id.v_icon_pwd).setBackground(iconicPWD);
        findViewById(R.id.v_icon_mail).setBackground(iconicCode);

        if (isPhone()) {
            edtMail.setHint(ResourceHelper.getString(R.string.input_code));
            edtName.setHint(ResourceHelper.getString(R.string.mobilephone_num));
            mbtnGetCode.setVisibility(View.VISIBLE);
        } else {
            edtMail.setHint(ResourceHelper.getString(R.string.mail));
            edtName.setHint(ResourceHelper.getString(R.string.input_username));
            mbtnGetCode.setVisibility(View.GONE);
        }


        initEvent();


    }

    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }

    private void initEvent() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isSingleClick())
                    return;
                if (checkUserInfo()) {
                    AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.CREATE_ACCOUNT);
                    if (isPhone()) {
                        signUpOrLoginByMobilePhoneInBackground();
                    } else {
                        onSubmit();
                    }

                }
            }
        });

        mbtnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isSingleClick())
                    return;
                if (checkMobilePhoneNumber()) {

                    onRequestMobilePhoneCode();
                }
            }
        });
    }

    /**
     * 验证短信验证码
     */
    private void signUpOrLoginByMobilePhoneInBackground() {

//        LoadingDialog.getInstance(RegisterActivity.this).showLoading(R.string.msg_registing);
        InputMethodHelper.closeInputMethod(RegisterActivity.this);
        if (!NetWorkHelper.isNetworkAvailable()) {
            ToastHelper.showToast(R.string.error_network_connecting);
            return;
        }

//        UserHelper.getInstance().signUpOrLoginByMobilePhoneInBackground(name, mail, new UserActionListener() {
//            @Override
//            public void actionSuccess(AVUser avUser) {
//
//                avUser.setUsername(name);
//                avUser.setPassword(password);
//                avUser.put(AppConfig.REGISTER_FROM_KEY, ResourceHelper.getString(R.string.app_name));
//                avUser.put(UserKey.USER_NICK_NAME, avUser.getUsername());
//                avUser.put(UserKey.USER_PERSONAL_PROFILE, ResourceHelper.getString(R.string.user_desc));
//                avUser.saveInBackground();
//                AVUser.logInInBackground(name, password, new LogInCallback<AVUser>() {
//                    @Override
//                    public void done(AVUser avUser, AVException e) {
//                        if (e == null) {
//                            LogHelper.d("login success");
//                        }
//                    }
//                });
//                afterSignIn(mContext, avUser, null);
//
//            }
//
//            @Override
//            public void actionFailure(AVException e) {
//
//
//                LoadingDialog.getInstance(RegisterActivity.this).hideLoading();
//                if (e.getCode() == ErrorUtils.ERROR_NAME_EXIST)
//                    ToastHelper.showToast(R.string.error_register_name_exist);
//                else if (e.getCode() == ErrorUtils.ERROR_MAIL_EXIST) {
//                    ToastHelper.showToast(R.string.error_register_mail_exist);
//                } else {
//                    ToastHelper.showToast(R.string.error_register);
//                }
//
//                LogHelper.d(e.getCode() + "||" + e.getLocalizedMessage() + "||" + e.getMessage());
//            }
//        });


    }

    /**
     * 发送验证码
     */
    private void onRequestMobilePhoneCode() {

//        UserHelper.getInstance().requestSmsCodeWithPhoneNumber(name, new UserActionListener() {
//            @Override
//            public void actionSuccess(AVUser avUser) {
//                ToastHelper.showToast(R.string.send_phone_num);
//            }
//
//            @Override
//            public void actionFailure(AVException e) {
//                ToastHelper.showToast(R.string.send_phone_num_failure);
//                LogHelper.d(e.getCode() + "||" + e.getLocalizedMessage() + "||" + e.getMessage());
//            }
//        });

    }


    private boolean checkMobilePhoneNumber() {
        name = edtName.getText().toString().trim();
        mail = edtMail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        if (name.isEmpty() || !StringUtils.checkPhone(name)) {
            ToastHelper.showToast(R.string.error_phone_num);
            return false;
        }

        return true;
    }

    private boolean isPhone() {
        if (UIHelper.REGISTER_PHONE.equals(registerType)) {
            return true;
        }
        return false;
    }

    private boolean checkUserInfo() {
        mail = edtMail.getText().toString().trim();
        name = edtName.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        if (!isPhone()) {
            if (mail.isEmpty() || !StringUtils.isEmail(mail)) {
                ToastHelper.showToast(R.string.error_mail);
                return false;
            }
        }else {

            if (mail.isEmpty() || mail.length() > 6) {
                ToastHelper.showToast(R.string.error_sms_code);
                return false;
            }
        }


        if (name.isEmpty()) {
            ToastHelper.showToast(R.string.error_name_length);
            return false;
        } else if (password.length() < 6 || password.length() > 16) {
            ToastHelper.showToast(R.string.error_password_length);
            return false;
        }

        return true;
    }

    private void onSubmit() {
        InputMethodHelper.closeInputMethod(RegisterActivity.this);
        if (!NetWorkHelper.isNetworkAvailable()) {
            ToastHelper.showToast(R.string.error_network_connecting);
            return;
        }
//        LoadingDialog.getInstance(RegisterActivity.this).showLoading(R.string.msg_registing);
        AVUser user = new AVUser();
        user.setUsername(name);
        user.setEmail(mail);
        user.setPassword(password);
        user.put(AppConfig.REGISTER_FROM_KEY, ResourceHelper.getString(R.string.app_name));
        user.put(UserKey.USER_NICK_NAME, user.getUsername());
        user.put(UserKey.USER_PERSONAL_PROFILE, ResourceHelper.getString(R.string.user_desc));
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                afterSubmit(e);
            }
        });
    }

    private void afterSubmit(AVException e) {
        mContext = RegisterActivity.this;
        if (e == null) {

            if (isPhone()) {
                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.REGISTER_PHONT);
            } else {
                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.REGISTER_ACCOUNT);
            }

            AVUser.logInInBackground(name, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    afterSignIn(mContext, avUser, e);
                }
            });

        } else {
//            LoadingDialog.getInstance(RegisterActivity.this).hideLoading();
            if (e.getCode() == ErrorUtils.ERROR_NAME_EXIST)
                ToastHelper.showToast(R.string.error_register_name_exist);
            else if (e.getCode() == ErrorUtils.ERROR_MAIL_EXIST) {
                ToastHelper.showToast(R.string.error_register_mail_exist);
            } else {
                ToastHelper.showToast(R.string.error_register);
                LogHelper.d("e.getCode()" + e.getCode());
            }
        }
    }


    public void afterSignIn(Context context, AVUser avUser, AVException e) {


        if (avUser != null && e == null) {
            ToastHelper.showToast(R.string.register_success);
//            LoadingDialog.getInstance(context).hideLoading();

//            绑定installationId
            PushHelper.setInstallationId(SharedPreferencesHelp.getInstallationId(AppConfig.INSTALLATIION_KEY));
            UIHelper.getInstance().openMainActivity(RegisterActivity.this);
            RegisterActivity.this.finish();

        } else {
            ToastHelper.showToast(R.string.error_login);
//            LoadingDialog.getInstance(context).hideLoading();
        }
    }


}
