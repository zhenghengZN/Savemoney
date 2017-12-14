package utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.avos.avoscloud.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import app.CommonData;
import event.RefreshEvent;
import share.SocialShareService;
import com.zhekouxingqiu.main.activity.BrowseRecordActivity;
import com.zhekouxingqiu.main.activity.MainActivity;
//import so.bubu.Coupon.AliTrade.activity.My.UserActivity;
import com.zhekouxingqiu.main.activity.SearchResultActivity;
import com.zhekouxingqiu.main.activity.UserInfoActivity;
import com.zhekouxingqiu.main.activity.about.BindingPhoneActivity;
import com.zhekouxingqiu.main.activity.about.Setting2Activity;
import com.zhekouxingqiu.main.activity.about.SettingActivity;
import com.zhekouxingqiu.main.activity.about.SupportActivity;
import com.zhekouxingqiu.main.activity.about.UserCallbackActivity;
import com.zhekouxingqiu.main.activity.login.ForgetActivity;
import com.zhekouxingqiu.main.activity.login.Login2Activity;
import com.zhekouxingqiu.main.activity.login.LoginActivity;
import com.zhekouxingqiu.main.activity.login.RegisterActivity;
import com.zhekouxingqiu.main.activity.login.StartActivity;
import com.zhekouxingqiu.main.fragment.MineFragment;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.ToastHelper;
import com.zhekouxingqiu.main.R;
import com.zhekouxingqiu.main.activity.WebViewActivity;
import com.zhekouxingqiu.main.activity.about.WeixinOpenActivity;

/**
 * Created by wangwn on 2016/4/14.
 */
public class UIHelper {

    private static UIHelper instance;
    public static final String REGISTER_TYPE = "register_type";
    public static final String REGISTER_PHONE = "register_phone";
    public static final String REGISTER_ACCOUNT = "register_account";

    public static UIHelper getInstance() {
        if (instance == null)
            instance = new UIHelper();
        return instance;
    }

    public HashMap url2Map(String url) {
        HashMap<String, Object> parammap = null;
        if (url != null && !(url.isEmpty())) {
            String[] urlstring = url.split("\\?");
            String[] parameters = urlstring[1].split("&");
            parammap = new HashMap<>();
            for (int i = 0; i < parameters.length; i++) {
                String[] keyAndvalue = parameters[i].split("=");
                parammap.put(keyAndvalue[0], keyAndvalue[1]);
            }
        }
        return parammap;
    }

    public void openUrl(Context context, String url) {
        LogUtil.log.e("测试专用", "" + url);
        if (url.contains("taobao")) {
            if (url.contains("https://h5.m.taobao.com/mlapp/")) {
                openAppPage((Activity) context, url);
            } else {
                openAlibc(context, url);
            }
        } else if (url.contains("redirectAppPage?")) {
            HashMap<String, Object> parammap = url2Map(url);
            if (parammap == null) {
                return;
            }
            String pagevalue = (String) parammap.get("page");
            if ("TaobaoCouponList".equalsIgnoreCase(pagevalue)) {
                Bundle db = new Bundle();
//                db.putString("url", url);
                LogUtil.log.e("openUrl", "" + url);
                db.putSerializable(CommonData.PARAMMAP, parammap);
                NavigationHelper.slideActivity((Activity) context, SearchResultActivity.class, db, false);

            }

            if ("PageContainer".equalsIgnoreCase(pagevalue)) {
                Bundle db = new Bundle();
//                db.putString("url", url);
                db.putSerializable(CommonData.PARAMMAP, parammap);
                LogUtil.log.e("openUrl111", "" + url);
                NavigationHelper.slideActivity((Activity) context, MainActivity.class, db, false);
            }

            if ("StaticList".equalsIgnoreCase(pagevalue)) {
                Bundle db = new Bundle();
//                db.putString("url", url);
                db.putSerializable(CommonData.PARAMMAP, parammap);
                LogUtil.log.e("openUrl222", "" + url);
                NavigationHelper.slideActivity((Activity) context, SettingActivity.class, db, false);
            }

            if("BrowseRecord".equalsIgnoreCase(pagevalue)) {
                Bundle db = new Bundle();
//                db.putString("url", url);
                db.putSerializable(CommonData.PARAMMAP, parammap);
                LogUtil.log.e("openUrl333", "" + url);
                NavigationHelper.slideActivity((Activity) context, BrowseRecordActivity.class, db, false);
            }

        } else if (url.contains("redirectAction?")) {
            HashMap<String, Object> parammap = url2Map(url);
            if (parammap == null) {
                return;
            }
            String action = (String) parammap.get("action");
            if ("ClearCache".equalsIgnoreCase(action)) {
                DataCleanManager.cleanInternalCache(context);
                DataCleanManager.cleanExternalCache(context);
                ToastHelper.showToast(ResourceHelper.getString(R.string.clear_cache_success));
            }

            if ("CopyText".equalsIgnoreCase(action)) {
                copy(SocialShareService.WEIXIN_LABEL, (String) parammap.get("text"), ResourceHelper.getString(R.string.weixin_group_copy), context);
            }

            if ("SignUpOrLoginWithMobilePhone".equalsIgnoreCase(action)) {
                Bundle db = new Bundle();
//                db.putString("url", url);
                db.putSerializable(CommonData.PARAMMAP, parammap);
                NavigationHelper.slideActivity((Activity) context, LoginActivity.class, db, false);
            }

            if ("TaobaoLogin".equalsIgnoreCase(action)) {
                Log.e("zhengheng", AlibcLogin.getInstance().isLogin() + " " + AlibcLogin.getInstance().getSession());
                if (AlibcLogin.getInstance().isLogin()) {
                    TaoBaologout((Activity) context);
                } else {
                    TaoBaologin((Activity) context);
                }
            }
        } else if (url.isEmpty()) {
            NavigationHelper.openBrowse(url, (Activity) context);
        } else {
            Intent intent = new Intent(context, WebViewActivity.class);
            try {
                intent.putExtra(WebViewActivity.URL, url);
                context.startActivity(intent);
            } catch (Exception e) {
                ToastHelper.showToast(R.string.error_unknown_url);
            }
        }
    }


    public void openAppPage(final Activity context, final String url) {
        if (!AlibcLogin.getInstance().isLogin()) {
            AlibcLogin alibcLogin = AlibcLogin.getInstance();
            alibcLogin.showLogin(context, new AlibcLoginCallback() {
                @Override
                public void onSuccess() {
                    openAlibc(context, url);
                    MineFragment.getInstance().mUserAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        } else {
            openAlibc(context, url);
        }
    }

    /**
     * 退出登录
     */
    public void TaoBaologout(final Activity context) {

        AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.logout(context, new LogoutCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "淘宝帐号已解除绑定",
                        Toast.LENGTH_SHORT).show();
                MineFragment.getInstance().mUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(context, "淘宝帐号解除绑定失败 " + code + msg,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void TaoBaologin(final Activity context) {

        AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.showLogin(context, new AlibcLoginCallback() {

            @Override
            public void onSuccess() {
                Toast.makeText(context, "淘宝帐号绑定成功 ",
                        Toast.LENGTH_LONG).show();
                MineFragment.getInstance().mUserAdapter.notifyDataSetChanged();
                //获取淘宝用户信息
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(context, "淘宝帐号绑定失败 ",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    public void copy(String label, String text, String toastText, Context context) {
        ClipboardManager clipboardManager = (ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboardManager.setPrimaryClip(clip);
        ToastHelper.showToast(toastText);
        if (SocialShareService.WEIXIN_LABEL.equals(label)) {
            SocialShareService.getInstance().openWeixinApp(context);
        }

    }

    public void openAlibc(Context context, String url) {
        AlibcBasePage alibcBasePage = new AlibcPage(url);
        AlibcShowParams alibcShowParams;
        if(url.equalsIgnoreCase("https://h5.m.taobao.com/mlapp/olist.html") || url.equalsIgnoreCase("https://h5.m.taobao.com/mlapp/cart.html"))
        {
            alibcShowParams = new AlibcShowParams(OpenType.H5, false);
        } else {
            alibcShowParams  = new AlibcShowParams(OpenType.Native, false);
        }
        alibcShowParams.setBackUrl("tbopen://alitradecoupon.bubu.so");
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams("mm_119950409_20916506_70766512", "", "mm_119950409_20916506_70766512");
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        AlibcTrade.show((Activity) context, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(TradeResult tradeResult) {
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });
    }

    public void openPublic(Activity act) {
        NavigationHelper.slideActivity(act, WeixinOpenActivity.class, null, false);
    }


    public void openApp(Activity act, String wechatApp, String wechatView) {
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName(wechatApp, wechatView);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        act.startActivityForResult(intent, 0);
    }

//    public void openUserPanel(Activity context) {
//        NavigationHelper.slideActivity(context, UserActivity.class, null, false);
//    }

    public void openUserInfoAcivity(Activity context) {
        NavigationHelper.slideActivity(context, UserInfoActivity.class, null, false);
    }

    /**
     * open loginActivity
     *
     * @param context context
     */
    public void openStartActivity(Activity context) {
        Intent intent = new Intent(context, StartActivity.class);
        context.startActivityForResult(intent, CommonData.LOGIN_INDEX);
    }


//    /**
//     * open FollowerActivity
//     *
//     * @param context
//     * @param userId
//     */
//    public void openFollowerActivity(Activity context, String userId, String type) {
//
//        if (!CommonUtils.isSingleClick()) {
//            return;
//        }
//        Bundle bundle = new Bundle();
//        bundle.putString(FollowerActivity.USER_ID, userId);
//        bundle.putString(FollowerActivity.USER_TYPE, type);
//
//        NavigationHelper.slideActivity(context, FollowerActivity.class, bundle, false);
//    }

    public void openSupport(Activity context) {
//        Intent intent = new Intent(context, SupportActivity.class);
//        context.startActivity(intent);

        NavigationHelper.slideActivity(context, SupportActivity.class, null, false);
    }

    public void openUserCallBack(Activity context) {
//        Intent intent = new Intent(context, UserCallbackActivity.class);
//        context.startActivity(intent);

        NavigationHelper.slideActivity(context, UserCallbackActivity.class, null, false);
    }

//    /**
//     * open myPoint activity
//     *
//     * @param context
//     */
//    public void openMyPointActivity(Activity context, int points, int talentLevel) {
//
//        if (CommonUtils.isSingleClick()) {
//            Bundle bundle = new Bundle();
//            bundle.putInt(MyPointActivity.POINT, points);
//            bundle.putInt(MyPointActivity.LEVEL, talentLevel);
//            NavigationHelper.slideActivity(context, MyPointActivity.class, bundle, false);
//        }
//
//
//    }

//    /**
//     * open myDynamic activity
//     *
//     * @param context
//     */
//    public void openMyDynamicActivity(Activity context) {
//
//        if (CommonUtils.isSingleClick()) {
//            NavigationHelper.slideActivity(context, MyDynamicActivity.class, null, false);
//        }
//
//
//    }

    /**
     * open favorite activity
     *
     * @param context
     */
    public void openFavoriteActivity(Activity context) {
//        Intent intent = new Intent(context, SettingIndependentActivity.class);
//        context.startActivity(intent);

        if (CommonUtils.isSingleClick()) {
//            NavigationHelper.slideActivity(context, FavoriteActivity.class, null, false);
        }

    }

    /**
     * open setting activity
     *
     * @param context
     */
    public void openSettingActivity(Activity context) {
//        Intent intent = new Intent(context, SettingIndependentActivity.class);
//        context.startActivity(intent);

        if (CommonUtils.isSingleClick()) {
            NavigationHelper.slideActivity(context, Setting2Activity.class, null, false);
        }

    }

    public void openWeixinOpen(Activity context) {
//        Intent intent = new Intent(context, WeixinOpenActivity.class);
//        context.startActivity(intent);
        NavigationHelper.slideActivity(context, WeixinOpenActivity.class, null, false);
    }

    /**
     * open BindingPhoneActivity
     *
     * @param context context
     */
    public void openBindingPhoneActivity(Activity context) {
        NavigationHelper.slideActivity(context, BindingPhoneActivity.class, null, false);
    }

    /**
     * open loginActivity
     *
     * @param context context
     */
    public void openLoginActivity(Context context) {
        Intent intent = new Intent(context, Login2Activity.class);
        context.startActivity(intent);
    }


    /**
     * open registerActivity
     *
     * @param context context
     */
    public void openRegisterActivity(Activity context, String register_type) {

        Intent intent = new Intent(context, RegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(REGISTER_TYPE, register_type);
        intent.putExtras(bundle);
        context.startActivity(intent);

//        NavigationHelper.slideActivity(context, RegisterActivity.class, bundle, false);
    }

    /**
     * open forget password
     *
     * @param context context
     */
    public void openForgetActivity(Activity context) {
        Intent intent = new Intent(context, ForgetActivity.class);
        context.startActivity(intent);
//        NavigationHelper.slideActivity(context, ForgetActivity.class, null, false);
    }

    /**
     * open change password
     *
     * @param context context
     */
    public void openMainActivity(Context context) {

        EventBus.getDefault().post(new RefreshEvent(RefreshEvent.REFRESH_EVENT));
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
