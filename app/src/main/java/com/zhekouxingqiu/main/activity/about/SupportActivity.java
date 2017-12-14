package com.zhekouxingqiu.main.activity.about;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;


import common.SharePopMenu;
import common.base.TitleAppCompatActivity;
import iconicfont.IconicFontDrawable;
import iconicfont.icon.CityGuideIcon;
import share.SocialShareService;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.NavigationHelper;
import utils.AVAnalyticsHelper;
import utils.CommonUtils;
import utils.UIHelper;

/**
 * 支持与反馈
 * Created by Auro on 15/10/23.
 */
public class SupportActivity extends TitleAppCompatActivity {

    LinearLayout llEvaluation;
    LinearLayout llCallBack;
    LinearLayout llShare;
    View clickIcon1;
    View clickIcon2;
    View clickIcon3;

    private SharePopMenu sharePopMenu;

    /**
     * onCreateView:初始化界面
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_support);
    }

    @Override
    protected void initView() {

        setTitle(getString(R.string.support));
        setBackClick();
        setHideRight();

        llEvaluation = findView(R.id.ll_evaluation);
        llCallBack = findView(R.id.ll_callback);
        llShare = findView(R.id.ll_share);
        clickIcon1 = findView(R.id.v_click_icon1);
        clickIcon2 = findView(R.id.v_click_icon2);
        clickIcon3 = findView(R.id.v_click_icon3);

        final Activity currentContext = this;

        IconicFontDrawable iconRight = new IconicFontDrawable(currentContext, CityGuideIcon.ICON_RIGHT);
        iconRight.setIconColor(currentContext.getResources().getColor(R.color.color_999999));
        clickIcon1.setBackground(iconRight);
        clickIcon2.setBackground(iconRight);
        clickIcon3.setBackground(iconRight);

        llEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.APP_EVALUATION);
                CommonUtils.getInstance().openAppStore(currentContext, "com.bubu.steps");
            }
        });

        llCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.USER_CUSTOMER_FEEDBACK);
                UIHelper.getInstance().openUserCallBack(currentContext);
            }
        });

        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.RECOMMENDED_APP);

                if (Helper.isNull(sharePopMenu)) {
                    sharePopMenu = new SharePopMenu(SupportActivity.this, onRecyclerViewItemClickListener);
                }
                sharePopMenu.showPopupWindow(v);
            }
        });
    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            sharePopMenu.dismiss();
            String title = getString(R.string.share_app_title);
            String content = getString(R.string.share_app_content);

            switch (position) {

                case 0:
                    SocialShareService.getInstance().shareSina(SupportActivity.this, title, content, "http://www.bubu.so", 1);
                    break;

                case 1:
                    SocialShareService.getInstance().shareWx(SupportActivity.this, title, content, "http://www.bubu.so", 1);
                    break;

                case 2:
                    SocialShareService.getInstance().shareWxFriend(SupportActivity.this, title, content, "http://www.bubu.so", 1);
                    break;

            }
        }
    };

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
