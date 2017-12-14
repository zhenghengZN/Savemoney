package utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SaveCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import app.AppConfig;
import event.RefreshEvent;
import push.PushHelper;
import share.SocialShareService;
import com.zhekouxingqiu.main.R;

import com.zhekouxingqiu.main.activity.UserInfoActivity;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.NetWorkHelper;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.ToastHelper;
import wiget.LoadingDialog;


/**
 * Created by wangwn on 2016/5/4.
 */
public class UserHelper {


    private UserHelper() {
    }

    private static UserHelper INSTANCE;

    public static UserHelper getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new UserHelper();
        }

        return INSTANCE;
    }

    public boolean isAlreadlyLoginIn() {

        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 获取当前账号信息
     *
     * @return
     */
    public AVUser getCurrentUser() {

        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
            // 允许用户使用应用
            return currentUser;
        } else {
            //缓存用户对象为空时， 可打开用户注册界面…
            return null;
        }
    }

    /**
     * 登录
     *
     * @param userName
     * @param pwd
     * @param listener
     */
    public void logInInBackground(String userName, String pwd, final UserActionListener listener) {

        AVUser.logInInBackground(userName, pwd, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {

                if (e == null) {
                    listener.actionSuccess(avUser);
                } else {
                    listener.actionFailure(e);
                }
            }
        });


    }

//    /**
//     * 普通注册
//     * @param userName
//     * @param pwd
//     * @param email
//     * @param listener
//     */
//    public void signUpInBackground(String userName, String pwd, String email, final UserActionListener listener){
//
//        AVUser user = new AVUser();
//        user.setUsername(userName);
//        user.setPassword(pwd);
//        user.setEmail(email);
//
//        // 其他属性可以像其他AVObject对象一样使用put方法添加
////        user.put("phone", "186-1234-0000");
//
//        user.signUpInBackground(new SignUpCallback() {
//            public void done(AVException e) {
//                if (e == null) {
//                    if (listener != null) {
//                        listener.actionSuccess(null);
//                    }
//                } else {
//
//                    if (listener != null) {
//                        listener.actionFailure(e);
//                    }
//                }
//            }
//        });
//    }
//
//    /**
//     * 登录
//     * @param userName
//     * @param pwd
//     * @param listener
//     */
//    public void logInInBackground(String userName, String pwd, final UserActionListener listener){
//
//        AVUser.logInInBackground(userName, pwd, new LogInCallback<AVUser>() {
//            @Override
//            public void done(AVUser avUser, AVException e) {
//
//                if (e == null) {
//                    listener.actionSuccess(avUser);
//                } else {
//                    listener.actionFailure(e);
//                }
//            }
//        });
//
//
//    }
//
//    /**
//     * 获取当前账号信息
//     * @return
//     */
//    public AVUser getCurrentUser(){
//
//        AVUser currentUser = AVUser.getCurrentUser();
//        if (currentUser != null) {
//            // 允许用户使用应用
//            return currentUser;
//        } else {
//            //缓存用户对象为空时， 可打开用户注册界面…
//            return null;
//        }
//    }
//
    /**
     * 注销登录
     */
    public void logOut(final Context context){

//        LoadingDialog.getInstance(context).showLoading();

        SocialShareService.logout(SocialShareService.platform,context);
        AVUser currentUser = getCurrentUser();
//        HashMap<String,Object>  userid = new HashMap<>();
//        userid.put("userId", currentUser.getObjectId());
//        LeanCloudService.callFunctionInBackground("youzanLogout", userid, new FunctionCallback() {
//            @Override
//            public void done(Object o, AVException e) {
//                if (e == null) {
//                    YouzanSDK.userLogout(context);
//                }
//            }
//        });

        AVUser.logOut();
        if (Helper.isNotEmpty(currentUser)) {
            currentUser = null;
        }

        PushHelper.removeInstallationId(SharedPreferencesHelp.getInstallationId(AppConfig.INSTALLATIION_KEY));
        ToastHelper.showToast(R.string.logout_success);
//        LoadingDialog.getInstance(context).hideLoading();

        EventBus.getDefault().post(new RefreshEvent(RefreshEvent.REFRESH_EVENT));
    }
//
//    /**
//     * 重置密码
//     * @param email
//     * @param listener
//     */
//    public void requestPasswordResetInBackground(String email, final UserActionListener listener){
//
//        AVUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {
//                    listener.actionSuccess(null);
//                } else {
//                    listener.actionFailure(e);
//                }
//            }
//        });
//
//    }
//
//    /**
//     * 修改密码
//     * @param userName
//     * @param old_pwd
//     * @param new_pwd
//     * @param listener
//     * @throws AVException
//     */
//    public void updatePasswordInBackground(String userName, String old_pwd, String new_pwd, final UserActionListener listener) throws AVException {
//
//        AVUser avUser = AVUser.logIn(userName, old_pwd);
//        avUser.updatePasswordInBackground(old_pwd, new_pwd, new UpdatePasswordCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {
//                    listener.actionSuccess(null);
//                } else {
//                    listener.actionFailure(e);
//                }
//            }
//        });
//    }
//
//    /**
//     * 发送验证邮件
//     * @param email
//     * @param listener
//     */
//    public void requestEmailVerfiyInBackground(String email, final UserActionListener listener){
//
//        AVUser.requestEmailVerfiyInBackground(email, new RequestEmailVerifyCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {
//                    listener.actionSuccess(null);
//                } else {
//                    listener.actionFailure(e);
//                }
//            }
//        });
//
//    }
//
//    /**
//     * 发送手机短信验证
//     * @param phoneName
//     * @param listener
//     */
//    public void requestMobilePhoneVerifyInBackground(String phoneName, final UserActionListener listener){
//
//
//        //如果你的账号需要重新发送短信请参考下面的代码
//        AVOSCloud.requestSMSCodeInBackground(phoneName, new RequestMobileCodeCallback() {
//
//            @Override
//            public void done(AVException e) {
//                //发送了验证码以后做点什么呢
//                if (e == null) {
//                    listener.actionSuccess(null);
//                } else {
//                    listener.actionFailure(e);
//                }
//            }
//        });
//    }
//

    /**
     * 手机号码注册
     *
     * @param phoneNum
     * @param smsCode
     * @param listener
     */
    public void signUpOrLoginByMobilePhoneInBackground(String phoneNum, String smsCode, final UserActionListener listener) {

        AVUser.signUpOrLoginByMobilePhoneInBackground(phoneNum, smsCode, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    listener.actionSuccess(avUser);
                } else {
                    listener.actionFailure(e);
                }
            }
        });

    }


    /**
     * 发送手机短信验证
     *
     * @param phoneName
     * @param listener
     */
    public void requestSmsCodeWithPhoneNumber(String phoneName, final UserActionListener listener) {


        //如果你的账号需要重新发送短信请参考下面的代码
        AVOSCloud.requestSMSCodeInBackground(phoneName, new RequestMobileCodeCallback() {

            @Override
            public void done(AVException e) {
                //发送了验证码以后做点什么呢
                if (e == null) {
                    listener.actionSuccess(null);
                } else {
                    listener.actionFailure(e);
                }
            }
        });
    }

    /**
     * 验证手机短信验证码
     *
     * @param smsCode
     * @param listener
     */
    public void verifyMobilePhoneInBackground(String smsCode, String phoneName, final UserActionListener listener) {

        AVOSCloud.verifySMSCodeInBackground(smsCode, phoneName, new AVMobilePhoneVerifyCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    listener.actionSuccess(null);
                } else {
                    listener.actionFailure(e);
                }
            }
        });

    }

//
//    /**
//     * 使用手机号码 + 密码登录方式
//     * @param userName
//     * @param pwd
//     * @param listener
//     */
//    public void loginByMobilePhoneNumber(String userName, String pwd, final UserActionListener listener){
//
//        AVUser.loginByMobilePhoneNumberInBackground(userName, pwd, new LogInCallback<AVUser>() {
//            @Override
//            public void done(AVUser avUser, AVException e) {
//                if (e == null) {
//                    listener.actionSuccess(avUser);
//                } else {
//                    listener.actionFailure(e);
//                }
//            }
//        });
//
//    }
//

    /**
     * 使用短信验证码 + 手机号码登录
     *
     * @param phoneNum
     * @param smsCode
     * @param listener
     */
    public void loginBySMSCodeInBackground(String phoneNum, String smsCode, final UserActionListener listener) {

        AVUser.loginBySMSCodeInBackground(phoneNum, smsCode, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    listener.actionSuccess(avUser);
                } else {
                    listener.actionFailure(e);
                }
            }
        });
    }
//
//    /**
//     * 手机号码短信重置密码
//     * @param phoneNum
//     * @param listener
//     */
//    public void requestPasswordResetBySmsCodeInBackground(String phoneNum, final UserActionListener listener){
//
//        AVUser.requestPasswordResetBySmsCodeInBackground(phoneNum, new RequestMobileCodeCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {
//                    listener.actionSuccess(null);
//                } else {
//                    listener.actionFailure(e);
//                }
//            }
//        });
//
//    }
//
//    public void resetPasswordBySmsCodeInBackground(String smsCode, String newPassword, final UserActionListener listener){
//
//        AVUser.resetPasswordBySmsCodeInBackground(smsCode, newPassword, new UpdatePasswordCallback() {
//            @Override
//            public void done(AVException e) {
//                if(e == null){
//                    //密码更改成功了！
//                    listener.actionSuccess(null);
//                }else{
//                    listener.actionFailure(e);
//                }
//            }
//        });
//    }
//
//
    /**
     * 上传头像
     * @param context
     * @param absPath
     */
    public void uploadProfile(final Context context, String absPath) {
        if (absPath != null) {
            String fileName = System.currentTimeMillis() + ".png";
            try {
                if (!NetWorkHelper.isNetworkAvailable()) {

                    ToastHelper.showToast(R.string.error_network_connecting);
                    return;
                }
                LoadingDialog.getInstance(context).showLoading(ResourceHelper.getString(R.string.icon_uploading));
                final AVFile avFile = AVFile.withAbsoluteLocalPath(fileName,absPath);
                avFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e != null) {
                            e.printStackTrace();
                            ToastHelper.showToast(e.getMessage());

                        } else {

                            //save to avuser
                            AVUser avUser = AVUser.getCurrentUser();
                            avUser.put(UserKey.USER_PROFILE_URL, avFile.getUrl());
                            avUser.put(UserKey.USER_ICON_FILE_KEY, avFile);
                            avUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException saveException) {
                                    if (saveException != null) {
                                        ToastHelper.showToast(R.string.error_upload_profile);
                                        saveException.printStackTrace();
                                    }
                                }
                            });
                            //update UI
                            UserInfoActivity userActivity = UserInfoActivity.getSharedInstance();
                            if (userActivity != null){
                                userActivity.setFace(avFile.getUrl());
                            }
                            EventBus.getDefault().post(new RefreshEvent(RefreshEvent.REFRESH_EVENT));
                        }
                        LoadingDialog.getInstance(context).hideLoading();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }



    public void TaoBaologin(final Activity context) {

        final AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.showLogin(context, new AlibcLoginCallback() {

            @Override
            public void onSuccess() {
                Toast.makeText(context, "登录成功 ",
                        Toast.LENGTH_LONG).show();
                //获取淘宝用户信息
                Log.i("zhengheng", "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());

            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(context, "登录失败 ",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * 退出登录
     */
    public void TaoBaologout(final Activity context) {

        AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.logout(context, new LogoutCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "退出登录成功",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(context, "退出登录失败 " + code + msg,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
//
//    public void sendFeedback(final Context context, String mail, String details) {
//        AVObject feebackObj = new AVObject("Feedback");
//        feebackObj.put("details", details);
//        feebackObj.put("device", Build.MODEL);
//        feebackObj.put("deviceOS", "Android");
//        feebackObj.put("deviceOSVersion", Build.VERSION.RELEASE);
//        try {
//            feebackObj.put("appVersion", CommonUtils.getInstance().getVersionName(context));
//        } catch (PackageManager.NameNotFoundException e) {
//            feebackObj.put("appVersion", "unknown");
//            e.printStackTrace();
//        }
//        feebackObj.put("contact", mail);
//        feebackObj.put("user", AVUser.getCurrentUser());
//
//        feebackObj.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                LoadingDialog.getInstance(context).hideLoading();
//                if (e == null) {
//                    ToastHelper.showToast(R.string.feedback_success);
//                    ((Activity) context).finish();
//                } else {
//                    ToastHelper.showToast(R.string.feedback_error);
//                    e.printStackTrace();
//                    Log.d("cai", "出错了 code = " + e.getCode());
//                }
//            }
//        });
//    }
//
//
//
//
//}
}
