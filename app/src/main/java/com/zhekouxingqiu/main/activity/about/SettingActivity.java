package com.zhekouxingqiu.main.activity.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.LogUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import app.CommonData;
import bean.SettingBean;
import common.base.TitleAppCompatActivity;
import iconicfont.IconicFontDrawable;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.base.recyclerview.recyclerView.adapter.ComRecyclerViewAdapter;
import so.bubu.lib.base.recyclerview.recyclerView.adapter.RecyclerViewHolder;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.StatusBarUtil;
import utils.MyJsonUtil;
import utils.UIHelper;

import static com.tencent.bugly.crashreport.inner.InnerAPI.context;

public class SettingActivity extends TitleAppCompatActivity {

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        setContentView(R.layout.activity_mine_setting);
        Intent intent = getIntent();
        parammap = (HashMap<String, Object>) intent.getSerializableExtra(CommonData.PARAMMAP);
    }

    private List<SettingBean> settingBeans = new LinkedList<>();

    public void getAssetsJson() {
        String settingJson = MyJsonUtil.getJson(this, parammap.get("jsonFile") + ".json");
        LogUtil.log.e("zhengheng", settingJson);

        settingBeans.addAll(JSON.parseArray(settingJson, SettingBean.class));
        createLayout();
    }

    private List<SettingBean.ObjectsBean> cellotherList = new LinkedList<>();
    private ComRecyclerViewAdapter<SettingBean.ObjectsBean> cellOtherAdapter;

    private void createLayout() {

        for (int i = 0; i < settingBeans.size(); i++) {
            switch (settingBeans.get(i).getType()) {
                case "Cell":
                    List<SettingBean.ObjectsBean> CellList = settingBeans.get(i).getObjects();
                    for (int j = 0; j < CellList.size(); j++) {
                        View cell_layout = LayoutInflater.from(SettingActivity.this).inflate(R.layout.mine_cell, null, false);
                        View img_icon = cell_layout.findViewById(R.id.img_icon);
                        IconicFontDrawable iconicFont = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_CLEAR_CACHE, getResources().getColor(R.color.colorPrimary));
                        img_icon.setBackground(iconicFont);

                        TextView tv_name = (TextView) cell_layout.findViewById(R.id.tv_name);
                        tv_name.setText(CellList.get(j).getTitle());
                        TextView tv_detail = (TextView) cell_layout.findViewById(R.id.tv_detail);

                        if (CellList.get(j).getDetail() != null) {
                            tv_detail.setVisibility(View.VISIBLE);
                            tv_detail.setText(CellList.get(j).getDetail());

                        }

                        final String url = CellList.get(j).getUrl();
                        if (url != null && !url.isEmpty()) {
                            initRightClickIcon(cell_layout.findViewById(R.id.v_click));
                        }

                        cell_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UIHelper.getInstance().openUrl(SettingActivity.this, url);
                            }
                        });
                        ll_cell.addView(cell_layout);
                    }
                    break;

                case "CellOther":
                    cellotherList.addAll(settingBeans.get(i).getObjects());
                    cellOtherAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    protected void doBack(int keyCode, KeyEvent event) {
        NavigationHelper.finish(this, RESULT_OK, null);
    }

    private HashMap<String, Object> parammap;
    private RecyclerView setting_cell_recly;
    private LinearLayout ll_cell;

    @Override
    protected void initView() {
        super.initView();

        setBackClick();
        setTitle((String) parammap.get(CommonData.PAGETTITLE));
        setHideRight();

        ll_cell = (LinearLayout) findViewById(R.id.ll_cell);
        setting_cell_recly = (RecyclerView) findViewById(R.id.setting_cell_recly);
        setting_cell_recly.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cellOtherAdapter = new ComRecyclerViewAdapter<SettingBean.ObjectsBean>(this, cellotherList, R.layout.mine_cell) {
            @Override
            public void convert(RecyclerViewHolder viewHolder, SettingBean.ObjectsBean item, int position) {
                if (item.getDetail() != null) {

                    viewHolder.getView(R.id.tv_detail).setVisibility(View.VISIBLE);
                    viewHolder.setText(R.id.tv_detail, item.getDetail());
                }

                if (item.getUrl() != null && !item.getUrl().isEmpty()) {
                    View click = viewHolder.getView(R.id.v_click);
                    initRightClickIcon(click);
                }

                viewHolder.setText(R.id.tv_name, item.getTitle());


                ImageView img_icon = viewHolder.getView(R.id.img_icon);
                switch (position) {
                    case 0:
                        IconicFontDrawable iconicFontWeixinCommunion = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_WX, getResources().getColor(R.color.colorPrimary));
                        img_icon.setBackground(iconicFontWeixinCommunion);
                        break;
                    case 1:
                        IconicFontDrawable iconicFontSinaWeibo = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SINA, getResources().getColor(R.color.colorPrimary));
                        img_icon.setBackground(iconicFontSinaWeibo);
                        break;
                    case 2:
                        IconicFontDrawable iconicFontVersion = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_VERSION, getResources().getColor(R.color.colorPrimary));
                        img_icon.setBackground(iconicFontVersion);
                        break;
                }
            }
        };

        cellOtherAdapter.setOnItemClickLitener(new ComRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, Object item, int position) {
                if(cellotherList.get(position).getUrl() == null || cellotherList.get(position).getUrl().isEmpty()) {
                    return;
                }
                UIHelper.getInstance().openUrl(SettingActivity.this, cellotherList.get(position).getUrl());
            }

            @Override
            public void onItemLongClick(View view, Object item, int position) {

            }
        });
        setting_cell_recly.setAdapter(cellOtherAdapter);
        getAssetsJson();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private void initRightClickIcon(View view) {
        IconicFontDrawable iconRight = new IconicFontDrawable(context, CityGuideIcon.ICON_RIGHT);
        iconRight.setIconColor(context.getResources().getColor(R.color.color_999999));
        view.setBackground(iconRight);
    }
}
