package so.bubu.Coupon.AliTrade.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.PushService;

import adapter.BaseCallFunctionBackListener;
import adapter.MainPagerAdapter;
import bean.TaobaoCoupons;
import common.base.StatusBarBaseCompatActivity;
import iconicfont.IconicFontDrawable;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;



import so.bubu.lib.helper.AppManager;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.ToastHelper;
import so.bubu.Coupon.AliTrade.R;
//import tools.fastlane.screengrab.Screengrab;
import utils.InformationHelper;
import utils.SharedPreferencesHelp;
import wiget.NoScrollViewPager;

/**
 * 项目入口界面
 * Created by Administrator on 2016/3/24.
 */
public class MainActivity extends StatusBarBaseCompatActivity {

    public static final int ABOUT = 1;
    public static final int TAOBAO = 0;

    private int showType;

    private IconicFontDrawable aboutIconBg, taobaoIconBg;

    private NoScrollViewPager vpMain;
    private TextView aboutTv, taobaoTV;
    private ImageView aboutIcon, taobaoIcon;

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

    @Override
    protected void initView() {
        super.initView();

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


        InformationHelper.getInstance().getTaobaoCoupons(0, 20, null, "泡面", null, new BaseCallFunctionBackListener() {
            @Override
            public void callSuccess(boolean result, String jsonstr) {
                if (result) {
                    LogUtil.log.e("getTaobaoCouponscallSuccess",jsonstr);
                } else {

                }

            }

            @Override
            public void callFailure(int type, AVException e) {

            }
        });

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

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
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

            case TAOBAO:
                setBtnShow(getResources().getColor(R.color.color_848484), getResources().getColor(R.color.color_ff6f06));
                break;
        }
//        aboutIcon.setBackground(aboutIconBg);
//        aboutIcon.setPressed(ABOUT == type);

        taobaoIcon.setBackground(taobaoIconBg);
        taobaoIcon.setPressed(TAOBAO == type);

            switch (type) {

//                case ABOUT:
//                    vpMain.setCurrentItem(ABOUT, false);
//                    break;

                case TAOBAO:
                    vpMain.setCurrentItem(TAOBAO, false);
                    break;

//                default:
//                    vpMain.setCurrentItem(showType, false);
//                    break;

            }

    }

    public void setBtnShow(int findId, int taobaoId) {
        aboutIconBg.setIconColor(findId);
        aboutTv.setTextColor(findId);
        taobaoIconBg.setIconColor(taobaoId);
        taobaoTV.setTextColor(taobaoId);
    }

    @Override
    protected boolean isSwipeback() {
        return false;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                // poi
                case R.id.rb_about:
                    setShowBtn(ABOUT);
                    break;

                // 淘宝优惠卷
                case R.id.rb_taobao:
                    setShowBtn(TAOBAO);
                    break;

            }
        }
    };

    @Override
    protected void doBack(int keyCode, KeyEvent event) {
        if (doubleBackToExitPressedOnce) {
            int currentItem = vpMain.getCurrentItem();
            SharedPreferencesHelp.setFirstPageItem(currentItem);
            AppManager.getAppManager().AppExit();
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

}
