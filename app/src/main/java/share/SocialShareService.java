package share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.SaveCallback;
import com.tencent.mm.sdk.modelbiz.JumpToBizProfile;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.HandlerRequestCode;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.facebook.controller.UMFacebookHandler;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.EmailHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import app.AppConfig;
import iconicfont.util.LeanCloudService;
import push.PushHelper;
import com.zhekouxingqiu.main.R;
import com.zhekouxingqiu.main.activity.login.StartActivity;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.LogHelper;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.ToastHelper;
import utils.AVAnalyticsHelper;
import utils.DataUtils;
import utils.SharedPreferencesHelp;
import utils.UserKey;

/**
 * SocialShareService
 * Created by Auro on 15/10/22.
 */
public class SocialShareService {

    public static final String MARK_WEIXIN_USER = "markWeixinUserIsValid";

    final static UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    public final static String WEIXIN_LABEL = "weixin";
    public final static String QQ_LABEL = "qq_group";
    public final static String QQ_AURO_NUM = "473782719";
    public final static String QQ_CITY_NUM = "136336928";
    public final static String WEIXIN_OPEN_ID = "2986365250";
    public final static String WEIXIN_Communion_ID = "bubu-editor";
    public final static String WEIXIN_Cooperation_ID = "miakooo";

    private final String APP_WEIXIN_ID = "wx7401690314a5cf22";
    private final String APP_WEIXIN_SECRET = "bdee7bfc53aa4abf2973e092bb6a6892";

    private final String QQ_APP_ID = "100424468";
    private final String QQ_APP_KEY = "c7394704798a158208a74ab60104f0ba";

    private final String FACEBOOK_APP_ID = "1234567";
    private final String SINA_ID = "5103948932";

    private final String HOST_LINK = "http://www.bubu.so/share/";

    private static SocialShareService instance;
    // TODO
    private IWXAPI wxapi;

    private int type;

    private BandingInterface bandingInterface;

    public static SHARE_MEDIA platform;

//    public boolean isFromYz;

    public static SocialShareService getInstance() {
        if (instance == null)
            instance = new SocialShareService();
        return instance;
    }

    public UMSocialService getController() {
        return mController;
    }

    private UMImage getUmImage(Activity context, String title, String url) {
        UMImage umImage = new UMImage(context, url);
        umImage.setTargetUrl(url);
        umImage.setTitle(title);
        return umImage;
    }

    public void shareSina(Activity context, String title, String content, String url, final int type) {
        initSina();
        this.type = type;

        SinaShareContent sinaContent = new SinaShareContent();
        sinaContent.setShareContent(content);
        sinaContent.setTitle(title);
        sinaContent.setTargetUrl(url);
        sinaContent.setShareImage(getUmImage(context, title, url));

        mController.setShareMedia(sinaContent);

        mController.directShare(context, SHARE_MEDIA.SINA, snsPostListener);
    }

    public void shareWx(Activity context, String title, String content, String url, final int type) {
        initWx(context);
        this.type = type;

        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(content);
        weixinContent.setTitle(title);
        weixinContent.setTargetUrl(url);
        weixinContent.setShareImage(getUmImage(context, title, url));

        mController.setShareMedia(weixinContent);

        mController.directShare(context, SHARE_MEDIA.WEIXIN, snsPostListener);
    }

    public void shareWxFriend(Activity context, String title, String content, String url, final int type) {
        initWxFriend(context);
        this.type = type;

        CircleShareContent circleShareContent = new CircleShareContent();
        circleShareContent.setShareContent(content);
        circleShareContent.setTitle(title);
        circleShareContent.setTargetUrl(url);
        circleShareContent.setShareImage(getUmImage(context, title, url));

        mController.setShareMedia(circleShareContent);

        mController.directShare(context, SHARE_MEDIA.WEIXIN_CIRCLE, snsPostListener);
    }

    private SocializeListeners.SnsPostListener snsPostListener = new SocializeListeners.SnsPostListener() {
        @Override
        public void onStart() {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
            switch (type) {

                case 0:
                    AVAnalyticsHelper.addCityOverviewActions(share_media.toString());
                    break;

                case 1:
                    AVAnalyticsHelper.addUseActions(share_media.toString());
                    break;

            }
        }
    };

    public void share(Activity context, String title, String content, String url, SHARE_MEDIA shareMedia) {
//        setPlatforms();
        initWx(context);
////        initQq(context);
//        initSMS();
//        initEmail();
////        initFacebook(context);
////        initTwitter(context);
        initSina();
//
//        setShareContent(context, title, content, url);


        UMImage umImage = new UMImage(context, url);
        umImage.setTargetUrl(url);
        umImage.setTitle(title);

        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(content);
        weixinContent.setTitle(title);
        weixinContent.setTargetUrl(url);
        weixinContent.setShareImage(umImage);
        mController.setShareMedia(weixinContent);

//        SinaShareContent sinaContent = new SinaShareContent();
//        sinaContent.setShareContent(content);
//        sinaContent.setTitle(title);
//        sinaContent.setTargetUrl(url);
//
//        sinaContent.setShareImage(umImage);
//
//        mController.setShareMedia(sinaContent);

        mController.directShare(context, shareMedia, new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
                switch (type) {

                    case 0:
                        AVAnalyticsHelper.addCityOverviewActions(share_media.toString());
                        break;

                    case 1:
                        AVAnalyticsHelper.addUseActions(share_media.toString());
                        break;

                }
            }
        });
//        mController.openShare(context, new SocializeListeners.SnsPostListener() {
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
//
//                switch (type) {
//
//                    case 0:
//                        AVAnalyticsHelper.addCityOverviewActions(share_media.toString());
//                        break;
//
//                    case 1:
//                        AVAnalyticsHelper.addUseActions(share_media.toString());
//                        break;
//
//                }
//
//            }
//        });

    }

    private void setShareContent(Activity context, String title, String content, String url) {
        mController.setShareContent(content);
        mController.setAppWebSite(url);
//         设置分享图片, 参数2为图片的url地址
//        Bitmap bitmap = QRCodeUtils.genQRCode(url);
        Bitmap bitmap = null;
        mController.getConfig().setMailSubject(title);

        //朋友圈
        CircleShareContent circleShareContent = new CircleShareContent();
        circleShareContent.setShareContent(content);
        circleShareContent.setTitle(content);
        circleShareContent.setTargetUrl(url);
        circleShareContent.setAppWebSite(url);
        mController.setShareMedia(circleShareContent);

        if (bitmap != null) {
            UMImage umImage = new UMImage(context, bitmap);
            umImage.setTargetUrl(url);
            umImage.setTitle(title);
            circleShareContent.setShareImage(umImage);
            circleShareContent.setShareMedia(umImage);
            mController.setShareMedia(umImage);
        } else {
            UMImage umImage = new UMImage(context, url);
            umImage.setTargetUrl(url);
            umImage.setTitle(title);
            circleShareContent.setShareImage(umImage);
            circleShareContent.setShareMedia(umImage);
            mController.setShareMedia(umImage);
        }

    }

    private void setPlatforms() {
        mController.getConfig().setPlatforms(
//                SHARE_MEDIA.FACEBOOK,
                SHARE_MEDIA.EMAIL,
                SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.SMS,
                SHARE_MEDIA.SINA
        );
    }

    private void initWx(Activity context) {
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(context, APP_WEIXIN_ID, APP_WEIXIN_SECRET);
        wxHandler.addToSocialSDK();

    }

    private void initWxFriend(Activity context) {
        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context, APP_WEIXIN_ID, APP_WEIXIN_SECRET);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    private void initQq(Activity context) {
        //QQ 参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(context, QQ_APP_ID,
                QQ_APP_KEY);
        qqSsoHandler.addToSocialSDK();

        //QZONE
        //参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(context, QQ_APP_ID,
                QQ_APP_KEY);
        qZoneSsoHandler.addToSocialSDK();
    }

    private void initSMS() {
        // 添加短信
        SmsHandler smsHandler = new SmsHandler();
        smsHandler.addToSocialSDK();
    }

    private void initEmail() {
        // 添加email
        EmailHandler emailHandler = new EmailHandler();
        emailHandler.addToSocialSDK();
    }

    private void initFacebook(Activity context) {
        UMFacebookHandler mFacebookHandler = new UMFacebookHandler(context);
        mFacebookHandler.addToSocialSDK();
    }

    //    Twitter分享不支持URL图片分享，因此需要传递本地图片
    private void initTwitter(Activity context) {
        mController.getConfig().supportAppPlatform(context, SHARE_MEDIA.TWITTER,
                "com.umeng.share", true);
    }

    private void initSina() {
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
    }

    public void wxOAuthBanding(Activity mContext, BandingInterface bandingInterface) {
        this.bandingInterface = bandingInterface;
        initWx(mContext);
        initWxFriend(mContext);
        UMSsoHandler wxHandler = mController.getConfig().getSsoHandler(HandlerRequestCode.WX_REQUEST_CODE);
        if (wxHandler != null && !wxHandler.isClientInstalled()) {
            ToastHelper.showToast(R.string.error_weixin_not_found);
            return;
        }
        banding(mContext, SHARE_MEDIA.WEIXIN);
    }

    public void sinaOAuthBanding(Activity mContext, BandingInterface bandingInterface) {
        this.bandingInterface = bandingInterface;
        initSina();
        banding(mContext, SHARE_MEDIA.SINA);
    }

    public void wxOAuth(Activity mContext) {

        initWx(mContext);
        initWxFriend(mContext);
        UMSsoHandler wxHandler = mController.getConfig().getSsoHandler(HandlerRequestCode.WX_REQUEST_CODE);
        if (wxHandler != null && !wxHandler.isClientInstalled()) {
            ToastHelper.showToast(R.string.error_weixin_not_found);
            return;
        }
        oAuth(mContext, SHARE_MEDIA.WEIXIN);
    }

    public void sinaOAuth(Activity mContext) {

        initSina();
        oAuth(mContext, SHARE_MEDIA.SINA);
    }


    public void banding(final Activity mContext, final SHARE_MEDIA type) {
//        LoadingDialog.getInstance(mContext).showLoading(R.string.msg_banding);
        mController.doOauthVerify(mContext, type, new SocializeListeners.UMAuthListener() {
            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
//                LoadingDialog.getInstance(mContext).hideLoading();
                if (e != null) {
                    LogHelper.e(e.getLocalizedMessage());
                }
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
                if (value != null && !TextUtils.isEmpty(value.getString("uid"))) {
                    LogHelper.d("wneng", "授权完成");
//                    Toast.makeText(mContext, "授权完成", Toast.LENGTH_SHORT).show();
//                    Log.d("wneng", "Bundle = " + value.toString());
                    String token = value.getString("access_token");
                    LogHelper.d("wneng", "拿到的微信TOKEN是" + token);
                    getWXPlatformInfoBanding(mContext, type, token);
                } else {
//                    Toast.makeText(mContext, "授权失败", Toast.LENGTH_SHORT).show();
                    LogHelper.d("wneng", "授权失败");
                    ToastHelper.showToast(R.string.error_sso_banding);
//                    LoadingDialog.getInstance(mContext).hideLoading();
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                ToastHelper.showToast(R.string.error_sso_banding);
//                LoadingDialog.getInstance(mContext).hideLoading();
            }

            @Override
            public void onStart(SHARE_MEDIA platform) {
            }
        });

    }

    public void oAuth(final Activity mContext, final SHARE_MEDIA type) {
//        LoadingDialog.getInstance(mContext).showLoading(R.string.msg_logining);
        mController.doOauthVerify(mContext, type, new SocializeListeners.UMAuthListener() {
            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
                ToastHelper.showToast(R.string.error_sso_login);
//                LoadingDialog.getInstance(mContext).hideLoading();
                if (e != null) {
                    LogHelper.e(e.getLocalizedMessage());
                }
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
                if (value != null && !TextUtils.isEmpty(value.getString("uid"))) {
                    LogHelper.d("wneng", "授权完成");
//                    Toast.makeText(mContext, "授权完成", Toast.LENGTH_SHORT).show();
//                    Log.d("wneng", "Bundle = " + value.toString());
                    SocialShareService.platform = platform;
                    String token = value.getString("access_token");
                    LogUtil.log.e("SocialShareService  oAuth", "onComplete 授权完成");
                    LogHelper.d("wneng", "拿到的微信TOKEN是" + token);
                    getWXPlatformInfo(mContext, type, token);
                } else {
//                    Toast.makeText(mContext, "授权失败", Toast.LENGTH_SHORT).show();
                    LogHelper.d("wneng", "授权失败");
                    ToastHelper.showToast(R.string.error_sso_login);
//                    LoadingDialog.getInstance(mContext).hideLoading();
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                ToastHelper.showToast(R.string.error_sso_login);
//                LoadingDialog.getInstance(mContext).hideLoading();
            }

            @Override
            public void onStart(SHARE_MEDIA platform) {
                LogHelper.d("wneng", "授权开始");

            }
        });

    }

    private void getWXPlatformInfoBanding(final Activity mContext, final SHARE_MEDIA type, final String wxToken) {
        mController.getPlatformInfo(mContext, type, new SocializeListeners.UMDataListener() {
            @Override
            public void onStart() {
                LogHelper.d("wneng", "获取平台数据开始");
            }

            @Override
            public void onComplete(int status, Map<String, Object> info) {
                Log.d("wneng", "onComplete");
                if (status == 200 && info != null) {
                    StringBuilder sb = new StringBuilder();
                    Set<String> keys = info.keySet();
                    for (String key : keys) {
                        sb.append(key + "=" + info.get(key).toString() + "\r\n");
//                        Log.d("wneng", "data == key = " + key + "info =" + info.get(key));
                    }
                    if (type == SHARE_MEDIA.WEIXIN) {
                        String unionId = (String) info.get("unionid");
                        String openId = (String) info.get("openid");
                        final String usid = unionId; //TODO 这里使用的应该是unionId
                        final String token = wxToken; //TODO TOKEN之前误用了unionId，现在改用access_token
                        final String name = (String) info.get("nickname");
                        String headimgurl = (String) info.get("headimgurl");

                        //如果是微信登陆 需要额外做个操作让其去服务端检查用的是不是openid，正确应该使用unionId
                        LogHelper.d("wneng", "微信登陆执行额外检查 TOKEN = " + token + "openId =" + openId + "unionId = " + unionId);

                        if (!Helper.isNotEmpty(usid) || !Helper.isNotEmpty(token)) {
                            //检查信息是否足够

                            ToastHelper.showToast(R.string.error_sso_banding);
//                            LoadingDialog.getInstance(mContext).hideLoading();
                            return;
                        }
                        bandingAVOS(mContext, type, usid, token, name, headimgurl);
                    } else if (type == SHARE_MEDIA.SINA) {
                        String usid = "" + info.get("uid");
                        String token = (String) info.get("access_token");
                        String name = (String) info.get("screen_name");
                        String profileImageUrl = (String) info.get("profile_image_url");
                        if (!Helper.isNotEmpty(usid) || !Helper.isNotEmpty(token)) {
                            //检查信息是否足够

                            ToastHelper.showToast(R.string.error_sso_banding);
//                            LoadingDialog.getInstance(mContext).hideLoading();
                            return;
                        }
                        bandingAVOS(mContext, type, usid, token, name, profileImageUrl);
                    }
                } else {
                    ToastHelper.showToast(R.string.error_sso_banding);
//                    LoadingDialog.getInstance(mContext).hideLoading();
//                    Log.d("TestData", "发生错误：" + status);
                }
            }
        });
    }

    private void getWXPlatformInfo(final Activity mContext, final SHARE_MEDIA type, final String wxToken) {
        mController.getPlatformInfo(mContext, type, new SocializeListeners.UMDataListener() {
            @Override
            public void onStart() {
                LogHelper.d("wneng", "获取平台数据开始");
            }

            @Override
            public void onComplete(int status, Map<String, Object> info) {
                Log.d("wneng", "onComplete");
                if (status == 200 && info != null) {
                    StringBuilder sb = new StringBuilder();
                    Set<String> keys = info.keySet();
                    for (String key : keys) {
                        sb.append(key + "=" + info.get(key).toString() + "\r\n");
//                        Log.d("wneng", "data == key = " + key + "info =" + info.get(key));
                    }
                    if (type == SHARE_MEDIA.WEIXIN) {
                        String unionId = (String) info.get("unionid");
                        String openId = (String) info.get("openid");
                        final String usid = unionId; //TODO 这里使用的应该是unionId
                        final String token = wxToken; //TODO TOKEN之前误用了unionId，现在改用access_token
                        final String name = (String) info.get("nickname");
                        String headimgurl = (String) info.get("headimgurl");

                        //如果是微信登陆 需要额外做个操作让其去服务端检查用的是不是openid，正确应该使用unionId
                        LogHelper.d("wneng", "微信登陆执行额外检查 TOKEN = " + token + "openId =" + openId + "unionId = " + unionId);

                        if (!Helper.isNotEmpty(usid) || !Helper.isNotEmpty(token)) {
                            //检查信息是否足够

                            ToastHelper.showToast(R.string.error_sso_login);
//                            LoadingDialog.getInstance(mContext).hideLoading();
                            return;
                        }
                        loginAVOS(mContext, type, usid, token, name, headimgurl);
                    } else if (type == SHARE_MEDIA.SINA) {
                        String usid = "" + info.get("uid");
                        String token = (String) info.get("access_token");
                        String name = (String) info.get("screen_name");
                        String profileImageUrl = (String) info.get("profile_image_url");
                        if (!Helper.isNotEmpty(usid) || !Helper.isNotEmpty(token)) {
                            //检查信息是否足够

                            ToastHelper.showToast(R.string.error_sso_login);
//                            LoadingDialog.getInstance(mContext).hideLoading();
                            return;
                        }
                        loginAVOS(mContext, type, usid, token, name, profileImageUrl);
                    }
                } else {
                    ToastHelper.showToast(R.string.error_sso_login);
//                    LoadingDialog.getInstance(mContext).hideLoading();
//                    Log.d("TestData", "发生错误：" + status);
                }
            }
        });
    }

    private void bandingAVOS(final Context context, final SHARE_MEDIA type, final String usid, String token, final String name, final String headimgurl) {
        String snsType;
        Map<String, String> userData = new HashMap<>();
        if (type == SHARE_MEDIA.WEIXIN) {
            snsType = AVUser.AVThirdPartyUserAuth.SNS_TENCENT_WEIXIN;
            userData.put("openid", usid);
            userData.put("expires_at", "3600000");
            userData.put("access_token", token);
        } else {
            snsType = AVUser.AVThirdPartyUserAuth.SNS_SINA_WEIBO;
            userData.put("uid", usid);
            userData.put("expiration_in", "3600000");
            userData.put("access_token", token);
        }

        final AVUser currentUser = AVUser.getCurrentUser();
        Map<String, Object> authData = Helper.isEmpty(currentUser.get(UserKey.USER_AUTH_DATA)) ? new HashMap() : (Map) currentUser.get(UserKey.USER_AUTH_DATA);
        authData.put(snsType, userData);
        currentUser.put(UserKey.USER_AUTH_DATA, authData);
        currentUser.put("checklistTemplatesLoaded", true);

//        BandingHelper.getInstance().bindSocialAccount(snsType, userData, new CallFunctionBackListener() {
//            @Override
//            public void callSuccess(boolean result, String jsonstr) {
//                if (result) {
//                    afterBanding(context, currentUser);
//                }
//            }
//
//            @Override
//            public void callFailure(int type, AVException e) {
//                ToastHelper.showToast(R.string.error_sso_banding);
//                LoadingDialog.getInstance(context).hideLoading();
//            }
//        });

//        currentUser.signUpInBackground(new SignUpCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {
//                    //否则直接登陆
//                    afterBanding(context, currentUser);
//                } else {
//                    ToastHelper.showToast(R.string.error_sso_banding);
//                    LoadingDialog.getInstance(context).hideLoading();
//
//                }
//            }
//        });
    }

    private void loginAVOS(final Context context, final SHARE_MEDIA type, final String usid, String token, final String name, final String headimgurl) {
        String snsType;
        final String localUserType;
        if (type == SHARE_MEDIA.WEIXIN) {
            snsType = AVUser.AVThirdPartyUserAuth.SNS_TENCENT_WEIXIN;
            localUserType = DataUtils.USER_TYPE_WEIXIN;
        } else {
            snsType = AVUser.AVThirdPartyUserAuth.SNS_SINA_WEIBO;
            localUserType = DataUtils.USER_TYPE_WEIBO;
        }

        AVUser.AVThirdPartyUserAuth userAuth = new AVUser.AVThirdPartyUserAuth(token, "3600000", snsType, usid);
        AVUser.loginWithAuthData(AVUser.class, userAuth, new LogInCallback<AVUser>() {
            @Override
            public void done(final AVUser avUser, final AVException e) {
                if (e == null) {
                    Log.d("wneng", "绑定成功");
                    if (!avUser.getBoolean("checklistTemplatesLoaded")) {
                        //如果是首次登陆 设置名字
                        trySetUserName(context, avUser, name, localUserType, headimgurl);
                        if (type == SHARE_MEDIA.WEIXIN) {
                            AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.REGISTER_WEIXING);
                        } else {
                            AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.REGISTER_WEIBO);
                        }

                    } else {
                        //否则直接登陆
                        afterSubmit(context, avUser);
                    }
                } else {
//                    Log.d("wneng", "绑定失败");
                    ToastHelper.showToast(R.string.error_sso_login);
                    e.printStackTrace();
//                    LoadingDialog.getInstance(context).hideLoading();

                }
            }
        });
    }

    private void afterBanding(Context context, AVUser avUser) {
        ;
//        LoadingDialog.getInstance(context).hideLoading();
        if (avUser != null) {
            if (Helper.isNotEmpty(bandingInterface)) {
                bandingInterface.bangingSucc();
            }
            ToastHelper.showToast(R.string.login_banding);
            PushHelper.setInstallationId(SharedPreferencesHelp.getInstallationId(AppConfig.INSTALLATIION_KEY));
        } else {
            ToastHelper.showToast(R.string.error_login);
        }
    }

    private void afterSubmit(Context context, AVUser avUser) {
//        LoadingDialog.getInstance(context).hideLoading();
        if (avUser != null) {
            ToastHelper.showToast(R.string.login_success);
            PushHelper.setInstallationId(SharedPreferencesHelp.getInstallationId(AppConfig.INSTALLATIION_KEY));

            ((StartActivity) context).finish();

        } else {
            ToastHelper.showToast(R.string.error_login);
        }
    }


    private void doMarkWeixinUserIsValid(SHARE_MEDIA type, String unionId, FunctionCallback<Boolean> callBack) {
        if (type == SHARE_MEDIA.WEIXIN && unionId != null && callBack != null) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("unionid", unionId);
            LeanCloudService.callFunctionInBackground(MARK_WEIXIN_USER, params, callBack);
        }
    }

    private String getRandomName(String name) {
        Random random = new Random();
        return name + random.nextInt(500);
    }

    private void trySetUserName(final Context context, final AVUser avUser, String name, final String localUserType, final String headimgurl) {
        if (Helper.isNotEmpty(name)) {
            final String newName = getRandomName(name);
            avUser.setUsername(newName);
            avUser.put(UserKey.USER_NICK_NAME, name);
            avUser.put(AppConfig.REGISTER_FROM_KEY, ResourceHelper.getString(R.string.app_name));
            avUser.put(UserKey.USER_PROFILE_URL, headimgurl);
            avUser.put("checklistTemplatesLoaded", true);
            avUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        afterSubmit(context, avUser);
                        return;
                    }
                    if (e.getCode() == 202) {
                        trySetUserName(context, avUser, newName, localUserType, headimgurl);
                    } else {
                        //更改成功
//                        Log.d("wneng", "用户名更改成功");
                        afterSubmit(context, avUser);
                    }
                }
            });
        }
    }

    public void jumpToWeixinOpen(Context context) {
        JumpToBizProfile.Req req = new JumpToBizProfile.Req();
//        req.toUserName = "gh_9cdca725164d"; // 公众号原始ID
        req.profileType = JumpToBizProfile.JUMP_TO_NORMAL_BIZ_PROFILE;
        req.extMsg = "extMsg";
        wxapi = WXAPIFactory.createWXAPI(context, APP_WEIXIN_ID, true);
        wxapi.registerApp(APP_WEIXIN_ID);
        wxapi.sendReq(req);
    }

    public void openWeixinApp(Context context) {
        try {
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            context.startActivity(intent);
        } catch (Exception e) {
            ToastHelper.showToast(R.string.error_weixin_not_found);
        }
    }

    /**
     * 注销本次登陆
     *
     * @param platform
     */
    public static void logout(final SHARE_MEDIA platform, final Context context) {
        mController.deleteOauth(context, platform, new SocializeListeners.SocializeClientListener() {

            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(int status, SocializeEntity entity) {
                if (status != StatusCode.ST_CODE_SUCCESSED) {
                }
            }
        });
    }

    public String requireLinkForEvent(String cloudId) {
        return HOST_LINK + cloudId;
    }

//    public void shareEvent(Activity context, Event event) {
//        if (event == null) {
//            ToastHelper.showToast(R.string.error_event_lose);
//            return;
//        }
//        if (event.getCloudId() == null) {
//            if (StatusUtils.SAMPLE.equals(event.getRowStatus())) {
//                ToastHelper.showToast(R.string.error_sample_share);
//            } else {
//                ToastHelper.showToast(R.string.error_event_share);
//            }
//            return;
//        }
//
//        final String eventCloudId = event.getCloudId();
//        final String eventName = event.getTitle();
//        SocialShareService socialShareService = SocialShareService.getInstance();
//        String url = socialShareService.requireLinkForEvent(eventCloudId);
//        String title = context.getString(R.string.app_name);
//        String content = context.getString(R.string.share_event_part1) + eventName + context.getString(R.string.share_event_part2)
//                + url + context.getString(R.string.share_event_part3);
//        socialShareService.share(context, title, content, url);
//    }

    public interface BandingInterface {

        void bangingSucc();

        void bangingFail();

    }

}
