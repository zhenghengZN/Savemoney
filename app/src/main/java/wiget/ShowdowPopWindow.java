package wiget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.avos.avoscloud.LogUtil;
import com.zhekouxingqiu.main.R;

import so.bubu.lib.helper.ResourceHelper;

/**
 * Created by zhengheng on 17/12/20.
 */
public class ShowdowPopWindow extends PopupWindow implements View.OnClickListener{
    private Activity activity;
    View contentView;
    private static  ShowdowPopWindow instance;
    private ShowdowPopWindow(Activity activity){
        this.activity = activity;
        initView();
    }

    public static ShowdowPopWindow getInstance(Activity activity){
        LogUtil.log.e("zhengheng popwind2",""+ instance);
        if(instance == null) {
            instance = new ShowdowPopWindow(activity);
        }
            return instance;
    }


    private void initView(){
        instance = this;
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        int popHeight = wm.getDefaultDisplay().getHeight() - ResourceHelper.Dp2Px(44) - getStatusBarHeight();

        View contentView = LayoutInflater.from(activity).inflate(R.layout.showdow_pop, null);
        View layout = contentView.findViewById(R.id.showdow_pop_layout);
        layout.setOnClickListener(this);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(popHeight);

        this.setContentView(contentView);//设置布局
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        this.setAnimationStyle(R.style.SelectPopupWindow);

//        this.setOutsideTouchable(true);
//        this.setFocusable(true);

        contentView.setOnClickListener(this);
    }

    public void showPopupWindow(View parent){
        if(!this.isShowing()){
            this.showAsDropDown(parent, 0, 0);
//            setBg(true);
        }
    }

    public void dismissPopup(){
        super.dismiss();
    }

    @Override
    public void dismiss() {
        dismissPopup();
        TypeSelectWindow instance = TypeSelectWindow.getInstance(activity);
        if(instance != null && instance.isShowing()){
            instance.dismiss();
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

//    private void setBg(boolean on) {
//        WindowManager.LayoutParams lp=activity.getWindow().getAttributes();
//        lp.alpha=on?0.3f:1.0f;
//        activity.getWindow().setAttributes(lp);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showdow_pop_layout:
                if(this.isShowing()){
                    dismiss();
//                    setBg(false);
                }
        }
    }
}
