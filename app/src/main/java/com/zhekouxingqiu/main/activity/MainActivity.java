package com.zhekouxingqiu.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.auth.third.ui.context.CallbackContext;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.PushService;

import java.util.HashMap;

import adapter.MainPagerAdapter;
import app.CommonData;
import common.base.StatusBarBaseCompatActivity;
import iconicfont.IconicFontDrawable;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;


import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.ToastHelper;
import com.zhekouxingqiu.main.R;
//import tools.fastlane.screengrab.Screengrab;
import utils.AVAnalyticsHelper;
import utils.SharedPreferencesHelp;
import wiget.NoScrollViewPager;

/**
 * 项目入口界面
 * Created by Administrator on 2016/3/24.
 */
public class MainActivity extends StatusBarBaseCompatActivity {

    public static final int ABOUT = 2;
    public static final int USER = 1;
    public static final int TAOBAO = 0;

    private int showType;

    private IconicFontDrawable aboutIconBg, taobaoIconBg, userIconBg;

    private NoScrollViewPager vpMain;
    private TextView aboutTv, taobaoTV, userTv;
    private ImageView aboutIcon, taobaoIcon, userIcon;

    private MainPagerAdapter mainPagerAdapter;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        // 订阅频道，当该频道消息到来的时候，打开对应的 Activity
        PushService.subscribe(this, "public", MainActivity.class);
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
    }

//    private String url = null;
    private HashMap<String, Object> parammap;
    @Override
    protected void initView() {
        super.initView();

        Intent intent = getIntent();
        parammap = (HashMap<String, Object>) intent.getSerializableExtra(CommonData.PARAMMAP);
        vpMain = findView(R.id.vp_main);

        View view = findViewById(R.id.rb_about);
        aboutTv = (TextView) view.findViewById(R.id.tv_icon_name);
        aboutTv.setText(R.string.text_city_find);
        aboutIcon = (ImageView) view.findViewById(R.id.iv_icon);
        view.setOnClickListener(onClickListener);
        view.setVisibility(View.GONE);

        // 淘宝
        view = findViewById(R.id.rb_taobao);
        taobaoTV = (TextView) view.findViewById(R.id.tv_icon_name);
        taobaoTV.setText(R.string.text_city_user);
        taobaoIcon = (ImageView) view.findViewById(R.id.iv_icon);
        view.setOnClickListener(onClickListener);

        // 我的
        view = findViewById(R.id.rb_user);
        userTv = (TextView) view.findViewById(R.id.tv_icon_name);
        userTv.setText(R.string.text_title_my);
        userIcon = (ImageView) view.findViewById(R.id.iv_icon);
        view.setOnClickListener(onClickListener);

        clickActivity();
    }

    @Override
    protected void onClickActivity() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        super.initData();
        showType = -1;

        aboutIconBg = IconicFontUtil.createIconicFontDrawable(CityGuideIcon.ICON_USER);

        //TODO
        taobaoIconBg = IconicFontUtil.createIconicFontDrawable(CityGuideIcon.ICON_NEW_GOODS);
        userIconBg = IconicFontUtil.createIconicFontDrawable(CityGuideIcon.ICON_USER);

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), parammap);
        vpMain.setAdapter(mainPagerAdapter);
        //vpMain.setOffscreenPageLimit(0);

        initDefaultItem();
    }

    private void initDefaultItem() {

        int firstPageItem = SharedPreferencesHelp.getFirstPageItem();
        LogUtil.log.e("firstPageItem","firstPageItem" + firstPageItem);

        switch (firstPageItem) {


//            case ABOUT:
//                setShowBtn(ABOUT);
//                break;

            case TAOBAO:
                setShowBtn(TAOBAO);
                break;

            case USER:
                setShowBtn(USER);
                break;

            default:
                setShowBtn(ABOUT);
                break;
        }

    }

//    @Override
//    protected boolean isOpenEventBus() {
//        return true;
//    }

    private void setShowBtn(int type) {
        if (showType == type) {
            return;
        }
        //TODO
        switch (type) {
//            case ABOUT:
//                setBtnShow(getResources().getColor(R.color.color_ff6f06), getResources().getColor(R.color.color_848484));
//                break;

            case USER:
                setBtnShow(getResources().getColor(R.color.color_848484), getResources().getColor(R.color.color_ff6f06));
                break;
            case TAOBAO:
                setBtnShow(getResources().getColor(R.color.color_ff6f06), getResources().getColor(R.color.color_848484));
                break;
        }
//        aboutIcon.setBackground(aboutIconBg);
//        aboutIcon.setPressed(ABOUT == type);

        taobaoIcon.setBackground(taobaoIconBg);
        taobaoIcon.setPressed(TAOBAO == type);

        userIcon.setBackground(userIconBg);
        userIcon.setPressed(USER == type);

            switch (type) {

//                case ABOUT:
//                    vpMain.setCurrentItem(ABOUT, false);
//                    break;

                case TAOBAO:
                    vpMain.setCurrentItem(TAOBAO, false);
                    break;

                case USER:
                    vpMain.setCurrentItem(USER, false);
                    break;
//                default:
//                    vpMain.setCurrentItem(showType, false);
//                    break;

            }

    }

    public void setBtnShow(int taobaoId, int userId) {
//        aboutIconBg.setIconColor(findId);
//        aboutTv.setTextColor(findId);
        taobaoIconBg.setIconColor(taobaoId);
        taobaoTV.setTextColor(taobaoId);

        userIconBg.setIconColor(userId);
        userTv.setTextColor(userId);
    }

    @Override
    protected boolean isSwipeback() {
        return false;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {


//                case R.id.rb_about:
//                    setShowBtn(ABOUT);
//                    break;

                // 淘宝优惠卷
                case R.id.rb_taobao:
                    setShowBtn(TAOBAO);
                    break;

                // 我的
                case R.id.rb_user:
                    AVAnalyticsHelper.addMainBodyActions(AVAnalyticsHelper.ME);
                    setShowBtn(USER);
                    break;
            }
        }
    };

    @Override
    protected void doBack(int keyCode, KeyEvent event) {
        if (doubleBackToExitPressedOnce) {
            int currentItem = vpMain.getCurrentItem();
            SharedPreferencesHelp.setFirstPageItem(currentItem);
//            AppManager.getAppManager().AppExit();
            finish();
            return;
        }
        doubleBackToExitPressedOnce = true;
        ToastHelper.showToast(ResourceHelper.getString(R.string.app_exit));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2 * 1000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
        LogUtil.log.i("zhengheng,CallbackContext");
    }
}
