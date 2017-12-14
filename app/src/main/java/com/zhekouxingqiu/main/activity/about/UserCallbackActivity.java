package com.zhekouxingqiu.main.activity.about;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.avos.avoscloud.AVUser;

import common.base.TitleAppCompatActivity;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.ToastHelper;
import utils.AVAnalyticsHelper;
import utils.UserHelper;

/**
 * 用户反馈
 * Created by Auro on 15/10/23.
 */
public class UserCallbackActivity extends TitleAppCompatActivity {

    EditText edtMail;
    EditText edtCallback;

    /**
     * onCreateView:初始化界面
     *
     * @param savedInstanceState
     * @author linhuan 2016/1/27 0027 11:27
     */
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_callback);
    }

    @Override
    protected void initView() {

        setTitle(getString(R.string.user_callback));
        setBackClick();
        setHideRight();

        edtMail = findView(R.id.edt_mail);
        edtCallback = findView(R.id.edt_callback);
        AVUser currentUser = UserHelper.getInstance().getCurrentUser();
        if (currentUser != null)
            edtMail.setText(currentUser.getEmail());

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.COMPLETE_FEEDBACK);
                sendCallback();
            }
        });

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

    public void sendCallback() {
        String content = edtCallback.getText().toString().trim();
        if (content.isEmpty()) {
            ToastHelper.showToast(R.string.error_callback);
            return;
        }
        //send
        String mail = edtMail.getText().toString().trim();
//        LoadingDialog.getInstance(UserCallbackActivity.this).showLoading();
//        UserHelper.getInstance().sendFeedback(this, mail, content);
    }


}
