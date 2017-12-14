package com.zhekouxingqiu.main.activity.about;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;


import app.AppConfig;
import iconicfont.icon.CityGuideIcon;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.ToastHelper;
import com.zhekouxingqiu.main.R;
import utils.ImageHelper;
import utils.PermissionHelper;
import utils.UIHelper;

/**
 * 微信公众号页面
 * Created by Auro on 15/12/11.
 */
public class WeixinOpenActivity extends CommonTopActitity {

    private ImageView ivIcon;

    private String[] item = new String[2];
    private PermissionHelper mPermissionHelper;

    private final static String WEIXIN_OPEN_ID = "bubu-zs";

    /**
     * onCreateView:初始化界面
     *
     * @param savedInstanceState
     * @author linhuan 2016/1/27 0027 11:27
     */
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_weixin_open);
    }

    @Override
    protected void initView() {
        setLeftIcon(CityGuideIcon.ICON_BACK, getResources().getColor(R.color.color_ffffff));
        setCenterContent(R.string.weixin_label);

        ivIcon = findView(R.id.iv_icon);

        initMenu();

        findView(R.id.tv_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });

//        Glide.with(this.getApplicationContext())
//                .load(R.drawable.weixin_qr)
//                .asBitmap()
//                .into(target);
    }

    @Override
    protected void initData() {
        super.initData();
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

    private void initMenu() {
        item[0] = getString(R.string.weixin_save_qr);
        item[1] = getString(R.string.weixin_copy_name);
    }

//    private SimpleTarget target = new SimpleTarget<Bitmap>(ResourceHelper.Dp2Px(200), ResourceHelper.Dp2Px(200)) {
//        @Override
//        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
//            // do something with the bitmap
//            // for demonstration purposes, let's just set it to an ImageView
//            ivIcon.setImageBitmap( bitmap );
//        }
//    };

    private void showMenu() {
        final Context context = this;

        new AlertDialog.Builder(context)
                .setTitle(ResourceHelper.getString(R.string.choose_weixin_follow))
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
//                    AVAnalytics.onEvent(context, AnalyticsActions.AN_OPEN_ABOUT, AnalyticsActions.AN_OPEN_ABOUT_WECHAT_QR_CODE);
                            saveFile();
                        } else if(which == 1) {
//                    AVAnalytics.onEvent(context, AnalyticsActions.AN_OPEN_ABOUT, AnalyticsActions.AN_OPEN_ABOUT_WECHAT_COPY_NAME);
                            copy();
                        }
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    // 复制公众号名称
    private void copy() {
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("weixin", WEIXIN_OPEN_ID);
        clipboardManager.setPrimaryClip(clip);
        ToastHelper.showToast(R.string.copy_already);
        openApp();
    }

    private void saveFile() {
        if (Helper.isNull(mPermissionHelper)) {
            mPermissionHelper = new PermissionHelper(this);
        }

        // 申请访问sd卡权限
        if (mPermissionHelper.isSDK_M() && mPermissionHelper.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            MPermissions.requestPermissions(this, AppConfig.REQUEST_SD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            requsetSdcardSucc();
        }
    }

    @PermissionGrant(AppConfig.REQUEST_SD)
    public void requsetSdcardSucc() {
        ImageHelper.SaveImageToSysAlbum(this, ivIcon);
        openApp();
    }

    @PermissionDenied(AppConfig.REQUEST_SD)
    public void requsetSdcardFail() {
        ToastHelper.showToast("保存失败");
    }

    private void openApp() {
        UIHelper.getInstance().openApp(this, "com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
    }

}
