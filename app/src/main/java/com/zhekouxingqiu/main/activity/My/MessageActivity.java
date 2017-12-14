package com.zhekouxingqiu.main.activity.My;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;


import adapter.MessageAdapter;
import common.base.TitleAppCompatActivity;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.NavigationHelper;
import utils.SharedPreferencesHelp;

/**
 * Created by wneng on 16/8/22.
 */
public class MessageActivity extends TitleAppCompatActivity {

    private ViewPager viewPagerMain;

    private MessageAdapter mMessageAdapter;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_message);
    }

    @Override
    protected void initView() {
        super.initView();

        viewPagerMain = findView(R.id.view_pager_main);
        setBackSearch();
        findViewById(R.id.iv_back_poi_imageview).setBackground(IconicFontUtil.createIconicFont(CityGuideIcon.ICON_BACK));
        setTitle(R.string.text_city_information);
        findViewById(R.id.tv_title).setVisibility(View.VISIBLE);
        findViewById(R.id.tab_layout).setVisibility(View.GONE);
        findViewById(R.id.tv_search).setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        super.initData();

        mMessageAdapter = new MessageAdapter(getSupportFragmentManager());
        viewPagerMain.setAdapter(mMessageAdapter);
        int firstPageItem = SharedPreferencesHelp.getFirstPageItem();
        if (firstPageItem == 2) {
            int secondPageItem = SharedPreferencesHelp.getSecondMessagePageItem(0);
            viewPagerMain.setCurrentItem(secondPageItem);
        }
    }

    @Override
    protected void doBack(int keyCode, KeyEvent event) {
        NavigationHelper.finish(this, RESULT_OK, null);
    }

}
