package so.bubu.Coupon.AliTrade.fragment;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.LogUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.impl.ScrollBoundaryDeciderAdapter;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.scwang.smartrefresh.layout.util.ScrollBoundaryUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import adapter.BaseCallFunctionBackListener;
import adapter.HorizontaltitleAdapter;
import adapter.TaobaoContentAdapter;
import adapter.TypeAdapter;
import app.CityGuideApplication;
import app.CommonData;
import bean.TaobaoCoupons;
import bean.TitleBean;
import common.CommonMethod;
import common.base.TitleFragment;

import so.bubu.Coupon.AliTrade.R;
import so.bubu.lib.base.BaseApplication;
import so.bubu.lib.helper.AppManager;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.ResourceHelper;
import utils.GlideHelper;
import utils.InformationHelper;
import utils.UIHelper;
import wiget.ItemOffsetDecoration;
import wiget.LoadingDialog;
import wiget.MyGridView;


public class TaobaoContentFragment extends TitleFragment {

    private int skip;
    private RecyclerView rcvinformation;
    private LinkedList<TaobaoCoupons.ObjectsBean> taobaoContentBeans = new LinkedList<>();
    private TaobaoContentAdapter taobaoContentAdapter;

    private int POI_COUNT;
    private LinkedList<TaobaoCoupons.WidgetsBean.ObjectsBean> gridlist = new LinkedList<>();
    private RefreshLayout refreshLayout;
    private LinearLayoutManager manager;
    private int contentDecoration = 8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_taobao_content);
        //  TODO  保存view对象
    }



    private OnRefreshLoadmoreListener refreshListener = new OnRefreshLoadmoreListener() {

        @Override
        public void onRefresh(RefreshLayout refreshlayout) {
            skip = skip_reset;
            hasAgainData = true;
            hasNetData();
            refreshlayout.finishRefresh();
        }

        @Override
        public void onLoadmore(RefreshLayout refreshlayout) {
            hasNetData();
        }
    };

    private RecyclerView content_recycler_title;
    View have_data, no_data;

    @Override
    protected void initView() {
        super.initView();

        have_data = findViewById(R.id.have_data);
        no_data = findViewById(R.id.no_data);
        no_data.setVisibility(View.VISIBLE);
        have_data.setVisibility(View.GONE);

        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        refreshLayout.setOnRefreshLoadmoreListener(refreshListener);
        rcvinformation = (RecyclerView) findViewById(R.id.rcv_information);

//        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        rcvinformation.setLayoutManager(manager);
        manager = new GridLayoutManager(getActivity(), 2);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(2, contentDecoration, false);
        rcvinformation.addItemDecoration(itemDecoration);
        rcvinformation.setLayoutManager(manager);

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

        content_recycler_title = (RecyclerView) findViewById(R.id.recycler_title);
        objects.clear();
        objects.add(new TitleBean("推荐", false));
        objects.add(new TitleBean("最新", false));
        objects.add(new TitleBean("销量", false));
        Adapter = new HorizontaltitleAdapter(act, objects);
        content_Adapter = new HorizontaltitleAdapter(act, objects);
        refreshLayout.setScrollBoundaryDecider(new ScrollBoundaryDeciderAdapter() {
            @Override
            public boolean canRefresh(View content) {
                return !ScrollBoundaryUtil.canScrollUp(rcvinformation);
            }
        });//设置滚动边界判断


        //获得屏幕的大小
        DisplayMetrics dm = BaseApplication.getInstance().getResources().getDisplayMetrics();
        width = dm.widthPixels;
    }


    @Override
    protected void initData() {
        super.initData();
        getCategory();
        hasNetData();
    }

    private boolean hasMore;
    private boolean hasAgainData;
    private HashMap<String, Object> Params = new HashMap<>();
    private Handler mHandler = new Handler();
    private Runnable runnable;

    private void hasNetData() {
        InformationHelper.getInstance().getTaobaoCoupons(skip, POI_COUNT, Params, new BaseCallFunctionBackListener() {
            @Override  //"category" -> "测试精选集"
            public void callSuccess(boolean result, String jsonstr) {
                have_data.setVisibility(View.VISIBLE);
                if (result) {
                    if (hasAgainData) {
                        hasAgainData = false;
                        taobaoContentBeans.clear();
                    }
                    LogUtil.log.e("Params", Params.toString());
                    TaobaoCoupons taobaoCoupons = JSON.parseObject(jsonstr, TaobaoCoupons.class);
//                    LogUtil.log.e("getTaobaoCoupons", "" + keyword + ":" + jsonstr);
                    hasMore = taobaoCoupons.isHasMore();
                    refreshLayout.setEnableLoadmore(hasMore);

                    taobaoContentBeans.addAll(taobaoCoupons.getObjects());
                    diffWiget(taobaoCoupons);

                    taobaoContentAdapter.notifyDataSetChanged();
                } else {
                    refreshLayout.setEnableLoadmore(false);
                }
                no_data.setVisibility(View.GONE);
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();
                //skip为起始位置,taobaoContentBeans记录了当前一共的个数;
                skip = taobaoContentBeans.size() + skip_reset;
            }

            @Override
            public void callFailure(int type, AVException e) {
                mHandler.removeCallbacks(runnable);
                runnable = new Runnable() {

                    @Override
                    public void run() {
                        hasNetData();
                    }

                };
                mHandler.post(runnable);

            }
        });
    }

    private LinearLayout gridview;

    private void diffWiget(TaobaoCoupons taobaoCoupons) {
        List<TaobaoCoupons.WidgetsBean> widgets = taobaoCoupons.getWidgets();
        if (widgets == null || widgets.size() == 0) {
            return;
        }
        gridview = (LinearLayout) ResourceHelper.loadLayout(act, R.layout.banner_grid);
        for (int i = 0; i < widgets.size(); i++) {
            TaobaoCoupons.WidgetsBean widgetsBean = widgets.get(i);
            String type = widgetsBean.getType();
            if ("Banner".equals(type)) {
                setBanner(widgetsBean);
            }

            if ("Grid".equals(type)) {
                setGrid(widgetsBean);
            }

            if ("CompositeBanner".equals(type)) {
                setCompositeBanner(widgetsBean);
            }
        }
        setRecyclertitle();
        taobaoContentAdapter.setHeaderView(gridview);
        //  TODO
        //当布局是网格布局是用于headview独占一行,内容为2列
        final GridLayoutManager gm = (GridLayoutManager) manager;
        gm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return taobaoContentAdapter.getHeaderView() != null && position == 0 ? gm.getSpanCount() : 1;
            }
        });

    }

    public void setWigetLine() {
        View wigetline = ResourceHelper.loadLayout(act, R.layout.wigetline);
        gridview.addView(wigetline, gridview.getChildCount());
    }

    public void setCompositeBanner(final TaobaoCoupons.WidgetsBean widgetsBean) {
        scale = width / widgetsBean.getWidth();
        height = scale * widgetsBean.getHeight();
        int linwidth = ResourceHelper.Dp2Px(1);
        final List<TaobaoCoupons.WidgetsBean.ObjectsBean> objects = widgetsBean.getObjects();
        //创建
        RelativeLayout CompositeBannerLayout = new RelativeLayout(act);
        RelativeLayout.LayoutParams CompositeBannerLayoutLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        CompositeBannerLayoutLayoutParams.width = (int) width;
        CompositeBannerLayoutLayoutParams.height = (int) height;
        CompositeBannerLayout.setLayoutParams(CompositeBannerLayoutLayoutParams);
        CompositeBannerLayout.setBackgroundColor(Color.parseColor("#99e2e2e2"));

        for (int i = 0; i < objects.size(); i++) {
            double StartXPercentage = objects.get(i).getStartXPercentage();
            double WidthPercentage = objects.get(i).getWidthPercentage();
            double StartYPercentage = objects.get(i).getStartYPercentage();
            double HeigthPercentage = objects.get(i).getHeigthPercentage();
            ImageView imageview = new ImageView(act);
            RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            int mHeight = (int) (HeigthPercentage * height);
            int mWidth = (int) (WidthPercentage * width);

            int marginx = 0;
            int marginy = 0;
            if (StartXPercentage + WidthPercentage < 1) {
                mWidth -= linwidth;
                marginx = linwidth;
            }

            if (StartYPercentage + HeigthPercentage < 1) {
                mHeight -= linwidth;
                marginy = linwidth;
            }

            Params.height = mHeight;
            Params.width = mWidth;

            Params.setMargins((int) (width * StartXPercentage), (int) (height * StartYPercentage), marginx, marginy);
            imageview.setLayoutParams(Params);

            GlideHelper.displayImageByResizeasBitmap(act, CommonMethod.getThumbUrl(objects.get(i).getPicUrl(), mWidth, mHeight), mWidth, mHeight, imageview);
            final String url = objects.get(i).getUrl();
            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.log.e("setCompositeBanner", "setCompositeBanner" + url);
                    UIHelper.getInstance().openUrl(act, url);
                }
            });
//            imageview.setVisibility(View.GONE);
            CompositeBannerLayout.addView(imageview);
//            众向的线
//            if (StartXPercentage + WidthPercentage < 1) {
//                View xview = new View(act);
//                RelativeLayout.LayoutParams Param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                Param.height = mHeight;
//                Param.width = linwidth;
//                Param.setMargins((int) (width * (WidthPercentage + StartXPercentage)), (int) (height * StartYPercentage), 0, 0);
//                xview.setLayoutParams(Param);
//                xview.setBackgroundColor(Color.parseColor("#ff0000"));//33e2e2e2
//                CompositeBannerLayout.addView(xview);
//            }
//            //横向的线
//            if (StartYPercentage + HeigthPercentage < 1) {
//                View yview = new View(act);
//                RelativeLayout.LayoutParams Param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                Param.height = linwidth;
//                Param.width = mWidth;
//                Param.setMargins((int) (width * StartXPercentage), (int) (height * (StartYPercentage + HeigthPercentage)), 0, 0);
//                yview.setLayoutParams(Param);
//                yview.setBackgroundColor(Color.parseColor("#ff0000"));//33e2e2e2
//                CompositeBannerLayout.addView(yview);
//            }
        }
        gridview.addView(CompositeBannerLayout, gridview.getChildCount());
        setWigetLine();
    }

    private double scale, width, height;
    private FrameLayout flActivity;
    private Banner banner;
    private View banner_layout;
    private LinkedList<TaobaoCoupons.WidgetsBean.ObjectsBean> bannerlist = new LinkedList<>();

    private void setBanner(final TaobaoCoupons.WidgetsBean widgetsBean) {
        banner_layout = ResourceHelper.loadLayout(act, R.layout.banner_layout);
        bannerlist.clear();
        bannerlist.addAll(widgetsBean.getObjects());
//        DisplayMetrics dm = BaseApplication.getInstance().getResources().getDisplayMetrics();
//        width = dm.widthPixels;
        scale = width / widgetsBean.getWidth();
        height = scale * widgetsBean.getHeight();
        banner = (Banner) banner_layout.findViewById(R.id.br_activity);
        flActivity = (FrameLayout) banner_layout.findViewById(R.id.fl_activity);
        List<String> pathList = new ArrayList();
        pathList.clear();
        for (TaobaoCoupons.WidgetsBean.ObjectsBean bannerRespBean : bannerlist) {
            pathList.add(bannerRespBean.getPicUrl());
        }
        flActivity.setVisibility(View.VISIBLE);
        // 界面显示
        banner.getLayoutParams().width = (int) width;
        banner.getLayoutParams().height = (int) height;

        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                if (Helper.isNotEmpty(bannerlist) && (position - 1) < bannerlist.size()) {
                    UIHelper.getInstance().openUrl(act, bannerlist.get((position - 1)).getUrl());
                }
            }
        });

        banner.setIndicatorGravity(BannerConfig.CENTER)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImages(pathList)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        if (Helper.isNotEmpty(path)) {
                            GlideHelper.displayImageByResizeasBitmap(context, CommonMethod.getThumbUrl(path.toString(), (int) width, (int) height), (int) width, (int) height, imageView);
                        }
                    }
                });
        banner.start();
        gridview.addView(banner_layout, gridview.getChildCount());
    }

    private int skip_reset;

    private void getCategory() {
        Bundle bundle = getArguments();
        Params = (HashMap<String, Object>) bundle.getSerializable(CommonData.PARAMMAP);
        //参数中若有SKIP和LIMIT则使用参数中的,没有则使用0和20
        skip_reset = Params.get(CommonData.SKIP) != null ? Integer.valueOf(Params.get(CommonData.SKIP).toString()) : 0;
        skip = skip_reset;
        POI_COUNT = Params.get(CommonData.LIMIT) != null ? Integer.valueOf(Params.get(CommonData.LIMIT).toString()) : 20;
    }

    private TypeAdapter typeAdapter;
    private View mygrid_layout;

    public void setGrid(final TaobaoCoupons.WidgetsBean widgetsBean) {
        mygrid_layout = ResourceHelper.loadLayout(act, R.layout.mygrid);
        gridlist.addAll(widgetsBean.getObjects());
        MyGridView noScrollGridview = ((MyGridView) mygrid_layout.findViewById(R.id.nsgd_type));
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
        gridview.addView(mygrid_layout, gridview.getChildCount());
        setWigetLine();
    }

    private LinkedList<TitleBean> objects = new LinkedList<>();
    private HorizontaltitleAdapter Adapter, content_Adapter;
    private int mSuspensionHeight = 0, mCurrentPosition = 0;
    private RecyclerView recycler_title;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager_content;
    private View recycler_title_layout;
//    private int

    public void setRecyclertitle() {
        recycler_title_layout = ResourceHelper.loadLayout(act, R.layout.recycler_title_layout);
        recycler_title = (RecyclerView) recycler_title_layout.findViewById(R.id.recycler_title);
        linearLayoutManager = new LinearLayoutManager(act, LinearLayout.HORIZONTAL, false);
        linearLayoutManager_content = new LinearLayoutManager(act, LinearLayout.HORIZONTAL, false);
        content_recycler_title.setLayoutManager(linearLayoutManager_content);
        recycler_title.setLayoutManager(linearLayoutManager);
//        Adapter = new HorizontaltitleAdapter(act, objects);
        recycler_title.setAdapter(Adapter);
        content_recycler_title.setAdapter(content_Adapter);
        //布局中的控件的adpter
        Adapter.setOnItemClickListener(new HorizontaltitleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (Adapter.getSelectedPosition() == position) {
                    return;
                }
                Adapter.notifyDataSetChanged();
                content_Adapter.setSelectedPosition(position);
                content_Adapter.notifyDataSetChanged();//更新在顶部的控件
//                taobaoContentAdapter.notifyDataSetChanged();//通过list获得参数,重新加载对应数据
            }
        });

        //固定在顶部控件的adpter
        content_Adapter.setOnItemClickListener(new HorizontaltitleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (content_Adapter.getSelectedPosition() == position) {
                    return;
                }
                content_Adapter.notifyDataSetChanged();

                Adapter.setSelectedPosition(position);
                Adapter.notifyDataSetChanged();//布局中的控件也更新
                manager.scrollToPositionWithOffset(1, ResourceHelper.Dp2Px(40));
                LogUtil.log.e("Adapter.setOnItemClickListener", "Adapter.setOnItemClickListener");
            }

        });

        rcvinformation.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight = ResourceHelper.Dp2Px(40);
                LogUtil.log.e("recycler_title.getTop()", recycler_title.getTop() + " ");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Helper.isNotEmpty(objects)) {
                    View view = manager.findViewByPosition(mCurrentPosition + 1);
                    if (view != null) {
                        if (view.getTop() - ResourceHelper.Dp2Px(contentDecoration / 2) <= mSuspensionHeight) {
                            content_recycler_title.setVisibility(View.VISIBLE);
                            content_recycler_title.setY(0);
                        } else {
                            content_recycler_title.setVisibility(View.GONE);
                        }
                        LogUtil.log.e("onScrollStateChanged  view.getTop()", view.getTop() + " ");
                        LogUtil.log.e("content_recycler_title.setY", "" + content_recycler_title.getY());
                    } else {
                        content_recycler_title.setY(0);
                    }
                }
            }

        });
        content_Adapter.notifyDataSetChanged();
        Adapter.notifyDataSetChanged();
        gridview.addView(recycler_title_layout, gridview.getChildCount());
    }
}


