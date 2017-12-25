package com.zhekouxingqiu.main.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import app.CommonData;
import common.base.TitleAppCompatActivity;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import com.zhekouxingqiu.main.R;
import com.zhekouxingqiu.main.activity.Taobao.SearchActivity;
import com.zhekouxingqiu.main.fragment.TaobaoContentFragment;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.StatusBarUtil;

public class SearchResultActivity extends TitleAppCompatActivity implements View.OnClickListener {
    private static final String TAG = SearchResultActivity.class.getSimpleName();
    private static SearchResultActivity INSTANCE;
    public  static SearchResultActivity getInstance(){
       return INSTANCE;
    }
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        INSTANCE = this;
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        intent = getIntent();
        setContentView(R.layout.activity_search_result);
    }

    private TextView title;
    private FragmentManager fm;
    private TaobaoContentFragment taobaoContentFragment;
    private Intent intent;

    String keyword, subcategory;

    @Override
    protected void initView() {
        super.initView();
        fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        parammap = (HashMap<String, Object>) intent.getSerializableExtra(CommonData.PARAMMAP);

        title = (TextView) findViewById(R.id.tv_title);
        String pageTitle = (String) parammap.get("pageTitle");
        keyword = (String) parammap.get(CommonData.KEYWORD);
        subcategory = (String) parammap.get(CommonData.SUBCATEGORY);
        if (pageTitle != null) {
            title.setText(pageTitle);
        } else if (keyword != null) {
            title.setText(keyword);
        } else {
            title.setText(subcategory);
        }

        changeViewBg(R.id.tv_search_imageview, IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SEARCH));
        findViewById(R.id.iv_back_poi_imageview).setBackground(IconicFontUtil.createIconicFont(CityGuideIcon.ICON_BACK));
        findViewById(R.id.iv_back_poi).setOnClickListener(this);
        findViewById(R.id.tv_search).setOnClickListener(this);

        taobaoContentFragment = new TaobaoContentFragment();
        fragmentold = taobaoContentFragment;
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonData.PARAMMAP, parammap);
        taobaoContentFragment.setArguments(bundle);
        ft.replace(R.id.fragment, taobaoContentFragment);


        // addToBackStack添加到回退栈,addToBackStack与ft.add(R.id.fragment, new
        // MyFragment())效果相当
        // ft.addToBackStack("test");

        ft.commitAllowingStateLoss();
    }


    private HashMap<String, Object> parammap = new HashMap<>();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent " + intent + "," + this.intent);
        String title = null;
        String sub = null;
        if (intent !=null) {
            parammap = (HashMap<String, Object>) intent.getSerializableExtra(CommonData.PARAMMAP);
            if(parammap !=null) {
                title = (String) parammap.get(CommonData.KEYWORD);
                sub = (String) parammap.get(CommonData.SUBCATEGORY);

            }
        }


        if ((sub != null && !sub.equals(subcategory)) || (title != null && !title.equals(keyword))) {
            FragmentTransaction transaction = fm.beginTransaction();
            if (fragmentold != null) {
                transaction.remove(fragmentold);
            }
            transaction.commitAllowingStateLoss();
            this.intent = intent;
            initView();
        }
    }


    public void  changeContent(Intent intent){
        FragmentTransaction transaction = fm.beginTransaction();
        if (fragmentold != null) {
            transaction.remove(fragmentold);
        }
        transaction.commitAllowingStateLoss();
        this.intent = intent;
        initView();
    }

    @Override
    protected void doBack(int keyCode, KeyEvent event) {
        finish();
    }

    private Fragment fragmentold;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_poi:
                finish();
                break;
            case R.id.tv_search:
                NavigationHelper.alphaActivityAddsearchTypeData(this, SearchActivity.class, CommonData.TAOBAO, null, false);
                break;
        }
    }

}
