package com.zhekouxingqiu.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.HashMap;
import java.util.LinkedList;

import adapter.BrowseRecordAdapter;
import app.CommonData;
import bean.TaobaoCoupons;
import common.base.TitleAppCompatActivity;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import com.zhekouxingqiu.main.R;
import utils.CommonUtils;
import utils.UIHelper;
import utils.dbUtils.DBHelper;

public class BrowseRecordActivity extends TitleAppCompatActivity {

    private HashMap<String, Object> parammap;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_browse_record);
        Intent intent = getIntent();
        parammap = (HashMap<String, Object>) intent.getSerializableExtra(CommonData.PARAMMAP);
    }

    private RefreshLayout smartRefreshLayout;
    private LinkedList<TaobaoCoupons.ObjectsBean> mItemLists = new LinkedList<>();
    private RecyclerView rcv_record;
    private BrowseRecordAdapter adapter;

    @Override
    protected void initView() {
        super.initView();
//        Log.e("zhengheng BrowseRecordActivity", "" + parammap.get(CommonData.PAGETTITLE));
        findView(R.id.tv_title).setVisibility(View.VISIBLE);
        setTitle((String) parammap.get(CommonData.PAGETTITLE));
        setBackSearch();
        findViewById(R.id.iv_back_poi_imageview).setBackground(IconicFontUtil.createIconicFont(CityGuideIcon.ICON_BACK));

        findView(R.id.tab_layout).setVisibility(View.GONE);
        findView(R.id.tv_search).setVisibility(View.GONE);

        smartRefreshLayout = findView(R.id.refreshLayout);
        smartRefreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        smartRefreshLayout.setOnRefreshLoadmoreListener(refreshListener);
        smartRefreshLayout.setEnableLoadmore(false);

        rcv_record = findView(R.id.rcv_record);
        rcv_record.setLayoutManager(new LinearLayoutManager(BrowseRecordActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new BrowseRecordAdapter(this, mItemLists);
        rcv_record.setAdapter(adapter);
        adapter.setOnItemClickListener(new BrowseRecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mItemLists.size() <= 0) {
                    return;
                }
                if (!CommonUtils.isSingleClick()) {
                    return;
                }
                UIHelper.getInstance().openUrl(BrowseRecordActivity.this, mItemLists.get(position).getCouponShareUrl());
            }
        });
    }

    private OnRefreshLoadmoreListener refreshListener = new OnRefreshLoadmoreListener() {

        @Override
        public void onRefresh(RefreshLayout refreshlayout) {
            initData();
            refreshlayout.finishRefresh();
        }

        @Override
        public void onLoadmore(RefreshLayout refreshlayout) {

        }
    };

    @Override
    protected void initData() {
        super.initData();
        mItemLists.clear();
        mItemLists.addAll(DBHelper.getInstance(this).getRecordList());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void doBack(int keyCode, KeyEvent event) {
        finish();
    }
}
