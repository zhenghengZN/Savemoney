package com.zhekouxingqiu.main.activity.about;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

import common.base.TitleAppCompatActivity;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.InputMethodHelper;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.NetWorkHelper;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.ToastHelper;
import so.bubu.lib.wiget.ClearEditText;
import utils.CommonUtils;
import utils.StringUtils;
import utils.UserHelper;

/**
 * Created by wangwn on 2016/6/6.
 */
public class BindingPhoneActivity extends TitleAppCompatActivity {

    private ClearEditText mEditPhoneNum;
    private ClearEditText mEditCode;
    private TextView mBtnGetCode;
    private Button mBtnSubmit;
    private String name;
    private int count = 60;

    /**
     * onCreateView:初始化界面
     *
     * @param savedInstanceState
     * @author linhuan 2016/1/27 0027 11:27
     */
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_binding_phone);
    }

    @Override
    protected void initView() {
        super.initView();

        setTitle(ResourceHelper.getString(R.string.binding_phone));
        setBackClick();
        setHideRight();

        mEditPhoneNum = findView(R.id.edt_phone_num);
        mEditCode = findView(R.id.edt_code);
        mBtnGetCode = findView(R.id.btn_code);
        mBtnSubmit = findView(R.id.btn_submit);

        mBtnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isSingleClick())
                    return;
                if (count != 60) {
                    return;
                }
                if (checkMobilePhoneNumber()) {

                    AVUser user = UserHelper.getInstance().getCurrentUser();

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                onRequestMobilePhoneCode();
                            }
                        }
                    });

                }
            }
        });

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

    }

    private void onSubmit() {
        InputMethodHelper.closeInputMethod(BindingPhoneActivity.this);
        if (!NetWorkHelper.isNetworkAvailable()) {
            ToastHelper.showToast(R.string.error_network_connecting);
            return;
        }
//
//        String trim = mEditCode.getText().toString().trim();
//        LoadingDialog.getInstance(BindingPhoneActivity.this).showLoading(R.string.msg_loading_photo);
//
//        BandingHelper.getInstance().verifyMobilePhone(trim, new CallFunctionBackListener() {
//            @Override
//            public void callSuccess(boolean result, String jsonstr) {
//                if (result) {
//                    AVUser user = UserHelper.getInstance().getCurrentUser();
//                    user.setMobilePhoneNumber(name);
//
//                    LoadingDialog.getInstance(BindingPhoneActivity.this).hideLoading();
//                    ToastHelper.showToast(R.string.bind_phone_num);
//                    doBack(-1, null);
//                }
//            }
//
//            @Override
//            public void callFailure(int type, AVException e) {
//                ToastHelper.showToast(R.string.bind_phone_num_code_fail);
//                LoadingDialog.getInstance(BindingPhoneActivity.this).showLoading(R.string.msg_loading_photo);
//            }
//        });
//
    }

    /**
     * 发送验证码
     */
    private void onRequestMobilePhoneCode() {

        if (!NetWorkHelper.isNetworkAvailable()) {
            ToastHelper.showToast(R.string.error_network_connecting);
            return;
        }

//        BandingHelper.getInstance().bindMobilePhone(name, new CallFunctionBackListener() {
//            @Override
//            public void callSuccess(boolean result, String jsonstr) {
//                ToastHelper.showToast(R.string.send_phone_num);
//
//                if (60 == count) {
//                    mBtnGetCode.setText(getString(R.string.title_countdown, count--));
//                    DelayTaskHelper.doDelayTask(1000, 60, new DelayTask.OnDelayExecuteListener() {
//                        @Override
//                        public void onProgressUpdate() {
//                            mBtnGetCode.setText(getString(R.string.title_countdown, count--));
//                        }
//
//                        @Override
//                        public void onPreExecute() {
//
//                        }
//
//                        @Override
//                        public void onPostExecute() {
//                            count = 60;
//                            mBtnGetCode.setText(R.string.get_sms_code);
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void callFailure(int type, AVException e) {
//                ToastHelper.showToast(R.string.send_phone_num_failure);
//            }
//        });

    }

    private boolean checkMobilePhoneNumber() {

        name = mEditPhoneNum.getText().toString().trim();
        if (name.isEmpty() || !StringUtils.checkPhone(name)) {
            ToastHelper.showToast(R.string.error_phone_num);
            return false;
        }

        return true;
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
}
