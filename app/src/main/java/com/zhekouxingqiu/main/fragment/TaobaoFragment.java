package com.zhekouxingqiu.main.fragment;


import android.os.Bundle;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.LogUtil;
import com.flyco.tablayout.SlidingTabLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import adapter.BaseCallFunctionBackListener;
import adapter.TaobaoCategoryAdpter;
import app.CommonData;
import common.base.TitleFragment;

import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import com.zhekouxingqiu.main.activity.Taobao.SearchActivity;
import so.bubu.lib.helper.NavigationHelper;
import com.zhekouxingqiu.main.R;
import utils.InformationHelper;
import wiget.FatherViewPager;


public class TaobaoFragment extends TitleFragment {
    private FatherViewPager fatherViewPager;
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


    private HashMap<String, Object> parammap;

    @Override
    protected void initView() {
        super.initView();

        parammap = (HashMap<String, Object>) getArguments().getSerializable(CommonData.PARAMMAP);
        findViewById(R.id.tv_title).setVisibility(View.VISIBLE);
        setTitle(R.string.text_main_taobao);
        findViewById(R.id.tab_layout).setVisibility(View.GONE);
        changeViewBg(R.id.tv_search_imageview, IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SEARCH));

        taobao_slidingTabLayout = (SlidingTabLayout) findViewById(R.id.taobao_slidingTabLayout);
        fatherViewPager = (FatherViewPager) findViewById(R.id.view_pager_recommend);
        taobaoCategoryAdpter = new TaobaoCategoryAdpter(getChildFragmentManager(), Params);
        fatherViewPager.setAdapter(taobaoCategoryAdpter);
        setSearchOnClick();
    }

    private String function = InformationHelper.GETTAOBAOITEMCATEGORIESBETA;

    @Override
    protected void initData() {
        super.initData();
        if (parammap != null) {
            function = (String) parammap.get("function");
        }
        hasNetData(function);

    }

    private ArrayList<HashMap<String, Object>> Params = new ArrayList<>();

    private void hasNetData(String function) {

        InformationHelper.getInstance().getTaobaoCategories(function, new BaseCallFunctionBackListener() {
            @Override
            public void callFailure(int type, AVException e) {
                super.callFailure(type, e);
            }

            @Override
            public void callSuccess(boolean result, String jsonstr) {
                LogUtil.log.e("getTaobaoItemCategoriesBeta", jsonstr);
                if (result) {
                    Params.clear();
                    try {
                        JSONObject json = new JSONObject(jsonstr);
                        Iterator<String> keys = json.keys();
                        String key = "";
                        String jsonObject = "";
                        while (keys.hasNext()) {
                            jsonObject = keys.next().toString();
                            Object o = json.get(jsonObject);

                            //判断 o s是否为jsonArray
                            if (o.toString().startsWith("[") && o.toString().endsWith("]")) {
                                JSONArray jsonArray = new JSONArray(o.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    Iterator<String> objectkey = object.keys();
                                    HashMap<String, Object> categorymap = new HashMap<>();

                                    while (objectkey.hasNext()) {
                                        //得到jsonarray中的key和value
                                        key = objectkey.next().toString();
                                        String vaule = object.getString(key);

                                        categorymap.put(key, vaule);
                                        LogUtil.log.e("jsonArray_object", "" + key + "," + vaule);
                                    }

                                    //将一组完整的jsonobject春到hashmap中在放入arraylist中
                                    Params.add(categorymap);
                                    LogUtil.log.e("jsonObject", jsonArray.toString());
                                }
                            }
                        }
                        LogUtil.log.e("getTaobaoCategories", "" + Params.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    taobaoCategoryAdpter.notifyDataSetChanged();
                    taobao_slidingTabLayout.setViewPager(fatherViewPager);
//                    fatherViewPager.setOffscreenPageLimit(TaobaoCatetorylist.size());
                }
            }
        });
    }


}
