package common.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import bean.TaobaoCoupons;
import common.CommonMethod;
import so.bubu.Coupon.AliTrade.R;
import so.bubu.lib.helper.DeviceHelper;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.ResourceHelper;
import utils.GlideHelper;
import utils.UIHelper;

/**
 * @author linhuan on 16/6/29 下午3:44
 */
public class BannerDialogAdapter extends BaseQuickAdapter<TaobaoCoupons.WidgetsBean.ObjectsBean> {

    private int width, height;
    private int bottom;
    private Activity activity;

    public BannerDialogAdapter(List<TaobaoCoupons.WidgetsBean.ObjectsBean> data, Activity activity) {
        super(R.layout.item_banner, data);

        this.activity = activity;
        bottom = ResourceHelper.Dp2Px(24);
        width = DeviceHelper.getScreenWidth() - ResourceHelper.Dp2Px(12 * 2);
        height = width * 226 / 610;
    }

    @Override
    protected void convert(BaseViewHolder helper, final TaobaoCoupons.WidgetsBean.ObjectsBean item) {
        if (Helper.isNotEmpty(item)) {
            ImageView imageView = helper.getView(R.id.iv_img);
            imageView.getLayoutParams().height = height;
            imageView.getLayoutParams().width = width;
            if (mData.size() == helper.getAdapterPosition() + 1) {
                imageView.setPadding(0, 0, 0, bottom);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    UIHelper.getInstance().openBanner(activity, item);
//                    AVAnalyticsHelper.addBanner(item.getTitle() + ":" + item.getId());
                }
            });
            GlideHelper.displayImageByResize(mContext, CommonMethod.getThumbUrl(item.getPicUrl(), width, height), width, height, imageView);
        }
    }
}
