//package wiget;
//
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.drawable.BitmapDrawable;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.view.animation.AlphaAnimation;
//import android.view.animation.TranslateAnimation;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//
//import com.alibaba.fastjson.JSON;
//import com.avos.avoscloud.LogUtil;
//import com.zhekouxingqiu.main.R;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import adapter.CategroyMenuAdapter;
//import bean.MenuBean;
//import bean.TaobaoCoupons;
//import so.bubu.lib.helper.ResourceHelper;
//import utils.MyJsonUtil;
//
///**
// * Created by zhengheng on 17/12/15.
// */
//public class CategoryPopwindow {
//    private Activity context;
//    private PopupWindow popWd;
//    private LinearLayout menu_category_layout;
//
//    public CategoryPopwindow(Activity context) {
//        this.context = context;
//    }
//
//    public void createPopWindow(View anchor, ViewGroup viewgroup) {
//        View view = context.getLayoutInflater().inflate(R.layout.all_category, viewgroup, false);
//        view.findViewById(R.id.finish_all_category).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popWd.dismiss();
//            }
//        });
//
//        RecyclerView rcv = (RecyclerView) view.findViewById(R.id.all_category_rcv);
//        menu_category_layout = (LinearLayout) view.findViewById(R.id.menu_category_layout);
//        rcv.setLayoutManager(new GridLayoutManager(context, 4));
//
//        CategroyMenuAdapter adapter = new CategroyMenuAdapter(context, gridlist);
//        adapter.setOnItemClickListener(new CategroyMenuAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                if (popWindowItemOnClick != null) {
//                    popWindowItemOnClick.ItemOnclick(view, position);
//                }
//            }
//        });
//        rcv.setAdapter(adapter);
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        int popHeight = wm.getDefaultDisplay().getHeight() - ResourceHelper.Dp2Px(44) - getStatusBarHeight();
//        Log.e("zhengheng popWidth", "" + popHeight);
//        popWd = new PopupWindow(context);
//        popWd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        popWd.setHeight(popHeight);
//        popWd.setContentView(view);
//        popWd.setBackgroundDrawable(new BitmapDrawable());
//        popWd.setOutsideTouchable(true);
//        popWd.setFocusable(true);
////        popWd.setAnimationStyle(R.style.anim_menu_bottombar);
//        getAssetsJson();
//        adapter.notifyDataSetChanged();
//
//        popWd.showAsDropDown(anchor, 0, 0);
//        setAnimset();
////        menu_category_layout.setAlpha(0.7f);
////        WindowManager.LayoutParams attributes = context.getWindow().getAttributes();
////        attributes.alpha = 0.7f;
////        context.getWindow().setAttributes(attributes);
//
//
//        popWd.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
////                WindowManager.LayoutParams attributes = context.getWindow().getAttributes();
////                attributes.alpha = 1f;
////                context.getWindow().setAttributes(attributes);
//////                menu_category_layout.setAlpha(1f);
////                AlphaAnimation alpha = new AlphaAnimation(0.7f , 1f);
////                alpha.setDuration(4000);
////                menu_category_layout.setAnimation(alpha);
//                setAnimset();
//            }
//        });
//    }
//
//    public void setAnimset(){
//        AnimatorSet set = new AnimatorSet();
//                set.setDuration(4000);
//                ObjectAnimator alpha = ObjectAnimator.ofFloat(menu_category_layout, "alpha", 0f, 1f);
//                ObjectAnimator translationY = ObjectAnimator.ofFloat(menu_category_layout, "TranslationY", 0, 1000);
//        set.play(alpha).with(translationY);
//                set.start();
//    }
//
//    private PopWindowItemOnClick popWindowItemOnClick;
//
//    public interface PopWindowItemOnClick {
//        void ItemOnclick(View view, int position);
//    }
//
//    public void setPopWindowItemOnClick(PopWindowItemOnClick popWindowItemOnClick) {
//        this.popWindowItemOnClick = popWindowItemOnClick;
//    }
//
//    private static final String JSON_FILE = "category.json";
//    private LinkedList<MenuBean.ObjectsBeanBean> gridlist = new LinkedList<>();
//
//    public void getAssetsJson() {
//        gridlist.clear();
//        String mineJson = MyJsonUtil.getJson(context, JSON_FILE);
//        List<MenuBean> menuBeans = JSON.parseArray(mineJson, MenuBean.class);
//        LogUtil.log.e("zhengheng category", "" + menuBeans.toString());
//        gridlist.addAll(menuBeans.get(0).getObjectsBean());
//    }
//
//
//    private int getStatusBarHeight() {
//        int height = 0;
//        //获取status_bar_height资源的ID
//        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            //根据资源ID获取响应的尺寸值
//            height = context.getResources().getDimensionPixelSize(resourceId);
//        }
//        Log.e("zhengheng StatusBarHht", height + "");
//        return height;
//    }
//}
