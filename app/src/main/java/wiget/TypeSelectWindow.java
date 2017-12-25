package wiget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.LogUtil;
import com.zhekouxingqiu.main.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import adapter.CategroyMenuAdapter;
import app.CityGuideApplication;
import bean.MenuBean;
import so.bubu.lib.base.BaseApplication;
import so.bubu.lib.helper.ResourceHelper;
import utils.AnimationUtil;
import utils.MyJsonUtil;
import utils.dbUtils.DBHelper;
import wiget.dragView.ItemDragHelperCallBack;
import wiget.dragView.LabelSelectionAdapter;
import wiget.dragView.LabelSelectionItem;
import wiget.dragView.listener.OnEditFinishListener;
import wiget.dragView.listener.OnItemDragListener;

/**
 * Created by zhengheng on 17/12/20.
 */
public class TypeSelectWindow extends PopupWindow implements View.OnClickListener, OnItemDragListener {

    private Activity activity;
    View contentView;//弹出框的根布局，可以监听其点击事件，达到点击阴影消失弹框的效果
    private LinkedList<MenuBean.ObjectsBeanBean> gridlist = new LinkedList<>();
    private RecyclerView rcv;
    private static TypeSelectWindow instance;

    private TypeSelectWindow(Activity activity) {
        this.activity = activity;
//        initDatas();
        initView();
    }

    public static TypeSelectWindow getInstance(Activity activity) {
        LogUtil.log.e("zhengheng popwind1", "" + instance);
        if (instance == null) {
            instance = new TypeSelectWindow(activity);
        }
        return instance;
    }

    private LabelSelectionAdapter mLabelSelectionAdapter;
    private OnEditFinishListener mOnEditFinishListener;
    private ItemTouchHelper mHelper;

    private void initView() {
//        if (activity instanceof OnEditFinishListener) {
//            mOnEditFinishListener = (OnEditFinishListener) activity;
//        }
        instance = this;
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        int popHeight = wm.getDefaultDisplay().getHeight() - ResourceHelper.Dp2Px(44) - getStatusBarHeight();

        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        contentView = LayoutInflater.from(activity).inflate(R.layout.all_category, null);

        View cancel = contentView.findViewById(R.id.finish_all_category);
        cancel.setOnClickListener(this);
        rcv = (RecyclerView) contentView.findViewById(R.id.all_category_rcv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 4);
        rcv.setLayoutManager(gridLayoutManager);
        rcv.setHasFixedSize(true);
//        CategroyMenuAdapter adapter = new CategroyMenuAdapter(activity, gridlist);


//        ArrayList<MenuBean.ObjectsBeanBean> unselectedLabels = new ArrayList<>();
//        if (unselectedLabels != null && unselectedLabels.size() > 0) {
//
//            for (MenuBean.ObjectsBeanBean unselectedLabel : unselectedLabels) {
//                labelSelectionItems.add(new LabelSelectionItem(LabelSelectionItem.TYPE_LABEL_UNSELECTED, unselectedLabel));
//            }
//        }
        CityGuideApplication instance = (CityGuideApplication) CityGuideApplication.getInstance();
        mLabelSelectionAdapter = new LabelSelectionAdapter(instance.labelSelectionItems);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = mLabelSelectionAdapter.getItemViewType(position);
                return itemViewType == LabelSelectionItem.TYPE_LABEL_SELECTED || itemViewType == LabelSelectionItem.TYPE_LABEL_UNSELECTED || itemViewType == LabelSelectionItem.TYPE_LABEL_ALWAY_SELECTED ? 1 : 4;
            }
        });

        rcv.setAdapter(mLabelSelectionAdapter);
        ItemDragHelperCallBack callBack = new ItemDragHelperCallBack(this);
        mLabelSelectionAdapter.setOnChannelDragListener(this);
        mLabelSelectionAdapter.setOnEditFinishListener(mOnEditFinishListener);
        mHelper = new ItemTouchHelper(callBack);
        mHelper.attachToRecyclerView(rcv);


        this.setContentView(contentView);//设置布局
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        this.setAnimationStyle(R.style.SelectPopupWindow);

//        this.setOutsideTouchable(true);
//        this.setFocusable(true);

    }

    /**
     * 模拟数据
     */
//    List<String> listDatas = new ArrayList<>();
//    private void initDatas(){
//        listDatas.clear();
//        for (int i = 0;i<10;i++){
//            listDatas.add("menu"+i);
//        }
//    }
    private static final String JSON_FILE = "category.json";

    public void getAssetsJson() {
        gridlist.clear();
        String mineJson = MyJsonUtil.getJson(activity, JSON_FILE);
        List<MenuBean> menuBeans = JSON.parseArray(mineJson, MenuBean.class);
        LogUtil.log.e("zhengheng category", "" + menuBeans.toString());
        gridlist.addAll(menuBeans.get(0).getObjectsBean());
    }


    //显示
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, 0, 0);
            //执行进入动画，这里主要是执行列表下滑，背景变半透明在setAnimationStyle(R.style.SelectPopupWindow);中实现
            AnimationUtil.createAnimation(true, contentView, contentView, null);
//            setBg(true);

        }
    }

    //消失
    public void dismissPopup() {
        super.dismiss();// 调用super.dismiss(),如果直接dismiss()会一直会调用下面的dismiss()
    }

    @Override
    public void dismiss() {
        Log.e("zhengheng pop", "dismiss ");
        //执行推出动画，列表上滑退出，同时背景变透明
        AnimationUtil.createAnimation(false, contentView, contentView, new AnimationUtil.AnimInterface() {
            @Override
            public void animEnd() {
                dismissPopup();//动画执行完毕后消失
            }
        });
        ShowdowPopWindow instance = ShowdowPopWindow.getInstance(activity);
        if (instance != null && instance.isShowing()) {
            instance.dismiss();
        }
        cancelEdit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish_all_category:
                if (this.isShowing()) {
                    dismiss();

//                    ShowdowPopWindow instance = ShowdowPopWindow.getInstance();
//                    if(instance != null && instance.isShowing()){
//                        instance.dismiss();
//                    }
//                    setBg(false);
                }
        }
    }

    private int getStatusBarHeight() {
        int height = 0;
        //获取status_bar_height资源的ID
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            height = activity.getResources().getDimensionPixelSize(resourceId);
        }
        Log.e("zhengheng StatusBarHht", height + "");
        return height;
    }

    private void setBg(boolean on) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = on ? 0.3f : 1.0f;
        activity.getWindow().setAttributes(lp);
    }

    @Override
    public void onStarDrag(RecyclerView.ViewHolder viewHolder) {
        mHelper.startDrag(viewHolder);
    }

    @Override
    public void onItemMove(int starPos, int endPos) {
        List<LabelSelectionItem> data = mLabelSelectionAdapter.getData();
        LabelSelectionItem labelSelectionItem = data.get(starPos);
        //先删除之前的位置
        data.remove(starPos);
        //添加到现在的位置
        data.add(endPos, labelSelectionItem);
        mLabelSelectionAdapter.notifyItemMoved(starPos, endPos);
    }


    public boolean cancelEdit() {
        return mLabelSelectionAdapter.cancelEdit();
    }
}

