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
import com.avos.avoscloud.LogUtil;

import java.util.HashMap;
import java.util.Map;

import app.CommonData;
import so.bubu.Coupon.AliTrade.activity.MainActivity;
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

    public static UIHelper getInstance() {
        if (instance == null)
            instance = new UIHelper();
        return instance;
    }

    public HashMap url2Map(String url){
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
            openAlibc(context, url);
        } else if (url.contains("redirectAppPage?")) {
            HashMap<String, Object> parammap = url2Map(url);
            if(parammap == null) {
                return;
            }
            String pagevalue = (String) parammap.get("page");
            if("TaobaoCouponList".equalsIgnoreCase(pagevalue)) {
                Bundle db = new Bundle();
//                db.putString("url", url);
                LogUtil.log.e("openUrl", "" + url);
                db.putSerializable(CommonData.PARAMMAP,parammap);
                NavigationHelper.slideActivity((Activity) context, SearchResultActivity.class, db, false);

            }

            if("PageContainer".equalsIgnoreCase(pagevalue)) {
                Bundle db = new Bundle();
//                db.putString("url", url);
                db.putSerializable(CommonData.PARAMMAP, parammap);
                LogUtil.log.e("openUrl111", "" + url);
                NavigationHelper.alphaActivity((Activity) context, MainActivity.class, db, false);
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
