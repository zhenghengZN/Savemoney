package com.zhekouxingqiu.main.activity.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestPasswordResetCallback;

import iconicfont.IconicFontDrawable;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.InputMethodHelper;
import so.bubu.lib.helper.StatusBarUtil;
import so.bubu.lib.helper.ToastHelper;
import utils.AVAnalyticsHelper;
import utils.StringUtils;

/**
 * forget password page
 * Created by wangwn on 2016/5/20.
 */
public class ForgetActivity extends AppCompatActivity {

    private EditText edtMail;
    private Button btnForget;
    private ImageView ivBack;
    private LinearLayout llBackground;

    private String mail;

    @Override
    protected void onCreate(Bundle savedBundle) {
        super.onCreate(savedBundle);
        setContentView(R.layout.activity_forget);
        setStatusBar();
        initView();
        initEvent();
    }

    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }

    private void initView() {

        edtMail = (EditText) findViewById(R.id.edt_mail);
        btnForget = (Button) findViewById(R.id.btn_forget);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        IconicFontDrawable iconicFont = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_BACK, getResources().getColor(R.color.color_000000));
        ivBack.setBackground(iconicFont);
        llBackground = (LinearLayout) findViewById(R.id.ll_background);

    }

    private void initEvent(){

        llBackground.setBackgroundResource(R.drawable.start_background);
        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmail()){
                    AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.RESET_PASSWORD);
                    onSubmit();
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean checkEmail() {
        mail = edtMail.getText().toString().trim();
        if(mail.isEmpty() || !StringUtils.isEmail(mail)){
            ToastHelper.showToast(R.string.error_mail);
            return false;
        }
        return true;
    }

    private void onSubmit() {

        InputMethodHelper.closeInputMethod(this);
//        LoadingDialog.getInstance(this).showLoading();
        AVUser.requestPasswordResetInBackground(mail, new RequestPasswordResetCallback() {
            @Override
            public void done(AVException e) {
                afterSubmit(e);
            }
        });

    }

    private void afterSubmit(AVException e) {
//        LoadingDialog.getInstance(this).hideLoading();
        if(e == null){
            ToastHelper.showToast(R.string.reset_mail_send);
        }else{
            ToastHelper.showToast(R.string.error_reset_mail_send);
        }
    }
}
