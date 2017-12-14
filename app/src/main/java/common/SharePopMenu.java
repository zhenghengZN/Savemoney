package common;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import bean.ShareBean;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.base.BaseApplication;
import so.bubu.lib.helper.DeviceHelper;
import so.bubu.lib.helper.ResourceHelper;

/**
 * @author linhuan on 2016/12/1 下午1:50
 */
public class SharePopMenu extends PopupWindow {

    private View view;
    private LinearLayout llContent;

    public SharePopMenu(final Activity content, BaseQuickAdapter.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        List<ShareBean> shareBeanList = new ArrayList<>();
        shareBeanList.add(new ShareBean(CityGuideIcon.ICON_SHARE_SINA, ResourceHelper.getString(R.string.title_setting_sina), R.color.color_share_sina));
        shareBeanList.add(new ShareBean(CityGuideIcon.ICON_SHARE_WEIXING, ResourceHelper.getString(R.string.title_share_weixing), R.color.color_share_weixing));
        shareBeanList.add(new ShareBean(CityGuideIcon.ICON_SHARE_WEIXING_FRIEND, ResourceHelper.getString(R.string.title_share_weixing_friend), R.color.color_share_weixing_friend));

        view = ResourceHelper.loadLayout(content, R.layout.share_popup_menu, null);
        llContent = (LinearLayout) view.findViewById(R.id.ll_content);
        RecyclerView rcvContent = (RecyclerView) view.findViewById(R.id.rcv_content);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(content);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvContent.setLayoutManager(linearLayoutManager);

        view.findViewById(R.id.iv_image).setOnClickListener(null);

        BaseQuickAdapter<ShareBean> shareBeanBaseQuickAdapter = new BaseQuickAdapter<ShareBean>(R.layout.item_share_show, shareBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, ShareBean item) {
                ((LinearLayout.LayoutParams) helper.getView(R.id.ll_content).getLayoutParams()).width = DeviceHelper.getScreenWidth() / 4;
                helper.setText(R.id.tv_share, item.getShareTitle());
                helper.getView(R.id.iv_share).setBackground(IconicFontUtil.createIconicFont(item.getShareIcon(), content.getResources().getColor(item.getShareColor())));
            }
        };

        shareBeanBaseQuickAdapter.setOnRecyclerViewItemClickListener(onRecyclerViewItemClickListener);
        rcvContent.setAdapter(shareBeanBaseQuickAdapter);

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);

        setContentView(view);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow

            view.startAnimation(AnimationUtils.loadAnimation(BaseApplication.getInstance(), R.anim.fade_in));
            llContent.startAnimation(AnimationUtils.loadAnimation(BaseApplication.getInstance(), R.anim.push_bottom_in));

            this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            this.update();
        } else {
            this.dismiss();
        }
    }

}
