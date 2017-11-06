package common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.LinkedList;

import bean.TaobaoCoupons;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import so.bubu.Coupon.AliTrade.R;
import so.bubu.lib.base.BaseApplication;
import so.bubu.lib.helper.DeviceHelper;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.ResourceHelper;

/**
 * @author linhuan on 2017/1/20 下午4:37
 */
public class BannerDialog extends Dialog {

    private RecyclerView rcvContent;
    private BannerDialogAdapter bannerDialogAdapter;
    private LinkedList<TaobaoCoupons.WidgetsBean.ObjectsBean> bannerRespBeanList = new LinkedList<>();

    public BannerDialog(Context context) {
        super(context);
        init(context);
    }

    public BannerDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected BannerDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        setOwnerActivity((Activity) context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_banner);

        initWindows();

        findViewById(R.id.fl_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.iv_back).setBackground(IconicFontUtil.createIconicFont(CityGuideIcon.ICON_CLOSE, BaseApplication.getInstance().getResources().getColor(R.color.colorPrimary)));

        bannerDialogAdapter = new BannerDialogAdapter(bannerRespBeanList, getOwnerActivity());
        rcvContent = (RecyclerView) findViewById(R.id.rcv_content);
        rcvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvContent.setAdapter(bannerDialogAdapter);
    }

    public BannerDialog showContent(LinkedList<TaobaoCoupons.WidgetsBean.ObjectsBean> dataList) {
        this.bannerRespBeanList.clear();
        this.bannerRespBeanList.addAll(dataList);
        if (Helper.isNull(bannerDialogAdapter)) {
            if (Helper.isNotNull(rcvContent)) {
                bannerDialogAdapter = new BannerDialogAdapter(bannerRespBeanList, getOwnerActivity());
                rcvContent.setAdapter(bannerDialogAdapter);
            }
        } else {
            bannerDialogAdapter.notifyDataSetChanged();
        }
        return this;
    }

    public void initWindows() {
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = DeviceHelper.getScreenWidth() - ResourceHelper.Dp2Px(12 * 2);

        int height = (bannerRespBeanList.size() * lp.width * 226 / 610) + ResourceHelper.Dp2Px(2 * 52) + ResourceHelper.Dp2Px(12 * (bannerRespBeanList.size() + 1));
        lp.height = height > (DeviceHelper.getScreenHeight() / 3 * 2) ? DeviceHelper.getScreenHeight() / 3 * 2 : WindowManager.LayoutParams.WRAP_CONTENT;

        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);
    }
}
