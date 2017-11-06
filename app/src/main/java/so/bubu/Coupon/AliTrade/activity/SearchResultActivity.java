package so.bubu.Coupon.AliTrade.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.LogUtil;

import app.CommonData;
import common.base.TitleAppCompatActivity;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import so.bubu.Coupon.AliTrade.R;
import so.bubu.Coupon.AliTrade.activity.Taobao.SearchActivity;
import so.bubu.Coupon.AliTrade.fragment.TaobaoContentFragment;
import so.bubu.lib.helper.InputMethodHelper;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.StatusBarUtil;
import utils.InformationHelper;

public class SearchResultActivity extends TitleAppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        intent = getIntent();
        setContentView(R.layout.activity_search_result);
    }


    private TextView title;
    private FragmentManager fm;
    private TaobaoContentFragment taobaoContentFragment;
    private Intent intent;

    @Override
    protected void initView() {
        super.initView();
        fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        keyword = intent.getStringExtra(CommonData.KEYWORD);
        category = intent.getStringExtra(CommonData.CATEGORY);
        subcategory = intent.getStringExtra(CommonData.SUBCATEGORY);
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(keyword);
        String url = intent.getStringExtra("url");
        splitUrl(url);
        changeViewBg(R.id.tv_search_imageview, IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SEARCH));
        findViewById(R.id.iv_back_poi_imageview).setBackground(IconicFontUtil.createIconicFont(CityGuideIcon.ICON_BACK));
        findViewById(R.id.iv_back_poi).setOnClickListener(this);
        findViewById(R.id.tv_search).setOnClickListener(this);

        taobaoContentFragment = new TaobaoContentFragment();
        fragmentold = taobaoContentFragment;
        Bundle bundle = new Bundle();
        bundle.putString(CommonData.KEYWORD, keyword);
        bundle.putString(CommonData.CATEGORY, category);
        bundle.putString(CommonData.SUBCATEGORY, subcategory);
        taobaoContentFragment.setArguments(bundle);
        ft.replace(R.id.fragment, taobaoContentFragment);

        // addToBackStack添加到回退栈,addToBackStack与ft.add(R.id.fragment, new
        // MyFragment())效果相当
        // ft.addToBackStack("test");

        ft.commit();
    }

    private String category;
    private String subcategory;
    private String keyword;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.intent = intent;
    }

    private void splitUrl(String url) {
        if (url != null && !(url.isEmpty())) {
            String[] parameters = url.split("&");
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].contains("category=") && !parameters[i].contains("subcategory=")) {
                    category = parameters[i].substring(parameters[i].indexOf("=") + 1, parameters[i].length());
                } else if (parameters[i].contains("subcategory=")) {
                    subcategory = parameters[i].substring(parameters[i].indexOf("=") + 1, parameters[i].length());
                }
            }
            title.setText(subcategory);
            keyword = null;
        }
    }

    @Override
    protected void doBack(int keyCode, KeyEvent event) {
        finish();
    }

    private Fragment fragmentold;

    @Override
    protected void onResume() {
        super.onResume();

        FragmentTransaction transaction = fm.beginTransaction();
        if (fragmentold != null) {
            transaction.remove(fragmentold);
        }
        transaction.commit();
        initView();
    }

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
