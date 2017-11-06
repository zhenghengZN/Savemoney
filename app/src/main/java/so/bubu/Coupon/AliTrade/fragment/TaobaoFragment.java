package so.bubu.Coupon.AliTrade.fragment;


import android.os.Bundle;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.LogUtil;
import com.flyco.tablayout.SlidingTabLayout;


import adapter.BaseCallFunctionBackListener;
import adapter.TaobaoCategoryAdpter;
import app.CommonData;
import common.base.TitleFragment;

import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import so.bubu.Coupon.AliTrade.activity.Taobao.SearchActivity;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.Coupon.AliTrade.R;
import utils.InformationHelper;
import wiget.FatherViewPager;


public class TaobaoFragment extends TitleFragment {
    private FatherViewPager fatherViewPager;
//    private TabLayout tab_layout;
    private String[] categoryList;
    private TaobaoCategoryAdpter taobaoCategoryAdpter;
    private SlidingTabLayout taobao_slidingTabLayout;
    public TaobaoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_taobao);
    }

    @Override
    protected void search() {
        NavigationHelper.alphaActivityAddsearchTypeData(act, SearchActivity.class, CommonData.TAOBAO, null, false);
    }

    @Override
    protected void initView() {
        super.initView();

        findViewById(R.id.tv_title).setVisibility(View.VISIBLE);
        setTitle(R.string.text_main_taobao);
        findViewById(R.id.tab_layout).setVisibility(View.GONE);
        changeViewBg(R.id.tv_search_imageview, IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SEARCH));

        taobao_slidingTabLayout = (SlidingTabLayout) findViewById(R.id.taobao_slidingTabLayout);
        fatherViewPager = (FatherViewPager) findViewById(R.id.view_pager_recommend);
        taobaoCategoryAdpter = new TaobaoCategoryAdpter(getChildFragmentManager(), categoryList);
        fatherViewPager.setAdapter(taobaoCategoryAdpter);
        setSearchOnClick();
    }

    @Override
    protected void initData() {
        super.initData();

        hasNetData();
    }

    private void hasNetData() {

        InformationHelper.getInstance().getTaobaoItemCategoriesBeta(new BaseCallFunctionBackListener() {
            @Override
            public void callFailure(int type, AVException e) {
                super.callFailure(type, e);
            }

            @Override
            public void callSuccess(boolean result, String jsonstr) {
                LogUtil.log.e("getTaobaoItemCategoriesBeta", jsonstr);
                if (result) {
                    categoryList = null;
                    categoryList = jsonstrFormatter(jsonstr);
                    TaobaoCategoryAdpter.categoryList = categoryList;
                    taobaoCategoryAdpter.notifyDataSetChanged();
                    taobao_slidingTabLayout.setViewPager(fatherViewPager);
                    fatherViewPager.setOffscreenPageLimit(categoryList.length);
                }
            }
        });
    }

    private String[] jsonstrFormatter(String jsonstr) {
        String[] categoryName =new String[0];
             if(!jsonstr.isEmpty()) {
                 jsonstr = jsonstr.replace("\"", "");
                 jsonstr = jsonstr.substring(1, jsonstr.length() - 1);
                 categoryName = jsonstr.split(",");
                 return categoryName;
             }
                return categoryName;
    }
}
