package so.bubu.Coupon.AliTrade.fragment;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.LogUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import adapter.BaseCallFunctionBackListener;
import adapter.TaobaoContentAdapter;
import adapter.TypeAdapter;
import app.CityGuideApplication;
import app.CommonData;
import bean.TaobaoCoupons;
import common.CommonMethod;
import common.base.TitleFragment;


import common.dialog.BannerDialog;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import so.bubu.Coupon.AliTrade.R;
import so.bubu.lib.base.BaseApplication;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.ResourceHelper;
import utils.GlideHelper;
import utils.InformationHelper;
import utils.UIHelper;
import wiget.MyGridView;


public class TaobaoContentFragment extends TitleFragment {

    private int skip = 0;
    private RecyclerView rcvinformation;
    private String category;
    private LinkedList<TaobaoCoupons.ObjectsBean> taobaoContentBeans = new LinkedList<>();
    private TaobaoContentAdapter taobaoContentAdapter;

    private static final int POI_COUNT = 20;
    private LinkedList<TaobaoCoupons.WidgetsBean.ObjectsBean> gridlist = new LinkedList<>();
    private RefreshLayout refreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_taobao_content);
    }

    private OnRefreshLoadmoreListener refreshListener = new OnRefreshLoadmoreListener() {

        @Override
        public void onRefresh(RefreshLayout refreshlayout) {
            skip = 0;
            taobaoContentBeans.clear();
            hasNetData();
            refreshlayout.finishRefresh();
        }

        @Override
        public void onLoadmore(RefreshLayout refreshlayout) {
            hasNetData();
        }
    };

    @Override
    protected void initView() {
        super.initView();

        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        refreshLayout.setOnRefreshLoadmoreListener(refreshListener);
        rcvinformation = (RecyclerView) findViewById(R.id.rcv_information);
        rcvinformation.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        taobaoContentAdapter = new TaobaoContentAdapter(act, taobaoContentBeans);

        taobaoContentAdapter.setOnItemClickListener(new TaobaoContentAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                if (taobaoContentBeans.size() >= 0) {
                    String couponShareUrl = taobaoContentBeans.get(position).getCouponShareUrl();
                    UIHelper.getInstance().openUrl(act, couponShareUrl);
                }
            }
        });
        rcvinformation.setAdapter(taobaoContentAdapter);

    }

    @Override
    protected void initData() {
        super.initData();
        getCategory();
        hasNetData();
    }

    private boolean hasMore;
    private String subcategory;

    private void hasNetData() {

        InformationHelper.getInstance().getTaobaoCoupons(skip, POI_COUNT, category, keyword, subcategory, new BaseCallFunctionBackListener() {
            @Override
            public void callSuccess(boolean result, String jsonstr) {
                if (result) {
                    TaobaoCoupons taobaoCoupons = JSON.parseObject(jsonstr, TaobaoCoupons.class);

                    hasMore = taobaoCoupons.isHasMore();
                    refreshLayout.setEnableLoadmore(hasMore);

                    taobaoContentBeans.addAll(taobaoCoupons.getObjects());
                    diffWiget(taobaoCoupons);
                    taobaoContentAdapter.notifyDataSetChanged();
                } else {
                    refreshLayout.setEnableLoadmore(false);
                }
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();
                skip = taobaoContentBeans.size();
            }

            @Override
            public void callFailure(int type, AVException e) {
                refreshLayout.autoRefresh();
            }
        });
    }

    private View gridview;

    private void diffWiget(TaobaoCoupons taobaoCoupons) {
        List<TaobaoCoupons.WidgetsBean> widgets = taobaoCoupons.getWidgets();
        if (widgets == null || widgets.size() == 0) {
            return;
        }
        gridview = ResourceHelper.loadLayout(act, R.layout.banner_grid);
        for (int i = 0; i < widgets.size(); i++) {
            TaobaoCoupons.WidgetsBean widgetsBean = widgets.get(i);
            String type = widgetsBean.getType();
            if ("Banner".equals(type)) {
                setBanner(widgetsBean);
            }

            if ("Grid".equals(type)) {
                setGrid(widgetsBean);
            }
        }
        taobaoContentAdapter.setHeaderView(gridview);
    }

    private double scale, width, height;
    private FrameLayout flActivity;
    private TextView tvNum, tvCount;
    private Banner banner;
    private BannerDialog bannerDialog;
    private LinkedList<TaobaoCoupons.WidgetsBean.ObjectsBean> bannerlist = new LinkedList<>();

    private void setBanner(final TaobaoCoupons.WidgetsBean widgetsBean) {
        bannerlist.clear();
        bannerlist.addAll(widgetsBean.getObjects());
        DisplayMetrics dm = BaseApplication.getInstance().getResources().getDisplayMetrics();
        width = dm.widthPixels;
        scale = width / widgetsBean.getWidth();
        height = scale * widgetsBean.getHeight();
        banner = (Banner) gridview.findViewById(R.id.br_activity);
        flActivity = (FrameLayout) gridview.findViewById(R.id.fl_activity);
        tvNum = (TextView) gridview.findViewById(R.id.tv_num);
        tvNum.setVisibility(View.GONE);
        tvCount = (TextView) gridview.findViewById(R.id.tv_count);
        tvCount.setVisibility(View.GONE);
        gridview.findViewById(R.id.iv_add).setBackground(IconicFontUtil.createIconicFont(CityGuideIcon.ICON_ADD_IMG, act.getResources().getColor(R.color.color_ffffff)));
        gridview.findViewById(R.id.ll_banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Helper.isNull(bannerDialog)) {
                    bannerDialog = new BannerDialog(act);
                }
                bannerDialog.showContent(bannerlist).show();
            }
        });
        List<String> pathList = new ArrayList();
        for (TaobaoCoupons.WidgetsBean.ObjectsBean bannerRespBean : bannerlist) {
            pathList.add(bannerRespBean.getPicUrl());
        }
        flActivity.setVisibility(View.VISIBLE);
        // 界面显示
        banner.getLayoutParams().width = (int) width;
        banner.getLayoutParams().height = (int) height;
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (Helper.isNotEmpty(bannerlist)) {
                    position = (position == (bannerlist.size() + 1)) ? 1 : position;
                    if ((position - 1) < bannerlist.size()) {
                        tvNum.setText(position + "");
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                if (Helper.isNotEmpty(bannerlist) && (position - 1) < bannerlist.size()) {
                    UIHelper.getInstance().openUrl(act, bannerlist.get((position - 1)).getUrl());
                }
            }
        });

        banner.setIndicatorGravity(BannerConfig.CENTER)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)
                .setImages(pathList)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        if (Helper.isNotEmpty(path)) {
                            GlideHelper.displayImageByResize(context, CommonMethod.getThumbUrl(path.toString(), (int) width, (int) height), (int) width, (int) height, imageView);
                        }
                    }
                })
                .start();

    }

//    private void setGrid(TaobaoCoupons.WidgetsBean widgetsBean) {
//        setListviewTop(widgetsBean);
//    }

    private String keyword;

    private void getCategory() {
        Bundle bundle = getArguments();
        category = bundle.getString(CommonData.CATEGORY);
        keyword = bundle.getString(CommonData.KEYWORD);
        subcategory = bundle.getString(CommonData.SUBCATEGORY);
    }

    private TypeAdapter typeAdapter;

    public void setGrid(final TaobaoCoupons.WidgetsBean widgetsBean) {
        gridlist.addAll(widgetsBean.getObjects());
        MyGridView noScrollGridview = ((MyGridView) gridview.findViewById(R.id.nsgd_type));
        View grid_layout = gridview.findViewById(R.id.grid_layout);
        if (gridlist.size() == 0) {
            grid_layout.setVisibility(View.GONE);
        } else {
            grid_layout.setVisibility(View.VISIBLE);
        }
        typeAdapter = new TypeAdapter(act, widgetsBean);
        noScrollGridview.setAdapter(typeAdapter);
        noScrollGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UIHelper.getInstance().openUrl(act, widgetsBean.getObjects().get(position).getUrl());
            }
        });
        noScrollGridview.setNumColumns(widgetsBean.getColumnPerRow());
        typeAdapter.notifyDataSetChanged();
    }

}


