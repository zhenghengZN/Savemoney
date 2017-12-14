package com.zhekouxingqiu.main.activity.about;

import android.os.Bundle;
import android.view.KeyEvent;


import iconicfont.icon.CityGuideIcon;
import so.bubu.lib.helper.NavigationHelper;
import com.zhekouxingqiu.main.R;

public class AboutAuthorActivity extends CommonTopActitity {

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about_author);
    }

    @Override
    protected void initData() {
        super.initData();

        setLeftIcon(CityGuideIcon.ICON_BACK, getResources().getColor(R.color.color_ffffff));
        setCenterContent(R.string.title_author);
    }

    @Override
    protected void doBack(int keyCode, KeyEvent event) {
        NavigationHelper.finish(this, RESULT_OK, null);
    }
}
