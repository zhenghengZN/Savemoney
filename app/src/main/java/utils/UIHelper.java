package utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;

import java.util.HashMap;
import java.util.Map;

import so.bubu.Coupon.AliTrade.activity.SearchResultActivity;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.ToastHelper;
import so.bubu.Coupon.AliTrade.R;
import so.bubu.Coupon.AliTrade.activity.WebViewActivity;
import so.bubu.Coupon.AliTrade.activity.about.WeixinOpenActivity;

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

    public void openUrl(Context context, String url) {
        if (url.contains("taobao")) {
            openAlibc(context, url);
        } else if(url.contains("redirectAppPage?")) {
            String[] parameters = url.split("&");
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].contains("page=")) {
                    String page = parameters[i].substring(parameters[i].indexOf("=") + 1, parameters[i].length());
                    if (page.equalsIgnoreCase("TaobaoCouponList")) {
                        Bundle db = new Bundle();
//                        db.putInt("search_type", CommonData.TAOBAO);
                        db.putString("url",url);
                        NavigationHelper.slideActivity((Activity)context, SearchResultActivity.class, db, false);
                        break;
                    }
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

    public void openAlibc(Context context, String url) {
        AlibcBasePage alibcBasePage = new AlibcPage(url);
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Native, false);
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
}
