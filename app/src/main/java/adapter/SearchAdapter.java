package adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.LogUtil;

import java.util.List;


import app.CommonData;

import bean.TaobaoCoupons;
import common.CommonMethod;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.Coupon.AliTrade.R;
import utils.GlideHelper;
import utils.UIHelper;

/**
 * 搜索适配器
 *
 * @author linhuan on 16/4/12 上午11:51
 */
public class SearchAdapter<T> extends RecyclerView.Adapter {

    private int searchType;

    private Activity act;
    private List<T> dataList;
    private SearchInterface searchInterface;
    private int mWidth = ResourceHelper.Dp2Px(115);
    private int mHeight = ResourceHelper.Dp2Px(115);
    private String couponShareUrl;

    public SearchAdapter(Activity act, List<T> dataList, int searchType) {
        this.searchType = searchType;
        this.act = act;
        this.dataList = dataList;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setSearchInterface(SearchInterface searchInterface) {
        this.searchInterface = searchInterface;
    }

//    @Override
//    public int getItemViewType(int position) {
//        return CommonData.CITY_ACTIVITY == searchType ? CommonData.CITY_ACTIVITY : CommonMethod.getPlaceType(((PlaceRespBean) dataList.get(position)).getCategory());
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        v = ResourceHelper.loadLayout(act, R.layout.taobao_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        TaobaoCoupons.ObjectsBean taobaoContentBean = (TaobaoCoupons.ObjectsBean) dataList.get(position);
        showItem(viewHolder, taobaoContentBean);


    }

    private void showItem(ViewHolder viewHolder, TaobaoCoupons.ObjectsBean taobaoContentBean) {

        viewHolder.taobao_pro_desc.setText(taobaoContentBean.getTitle());

        viewHolder.taobao_finalPrice.setText(taobaoContentBean.getFinalPrice() + "");

        viewHolder.couponAmount.setText("立减 " + taobaoContentBean.getCouponAmount() + " 元");
        if (taobaoContentBean.getPlatform().equals("天猫")) {
            viewHolder.taobao_platform.setImageResource(R.drawable.tmall_logo_30);
            viewHolder.taobao_discountPrice.setText("天猫价 ¥" + taobaoContentBean.getDiscountPrice());
        } else {
            viewHolder.taobao_platform.setImageResource(R.drawable.taobao_logo_30);
            viewHolder.taobao_discountPrice.setText("淘宝价 ¥" + taobaoContentBean.getDiscountPrice());
        }
        GlideHelper.displayImageByResizeasBitmap(act, CommonMethod.getThumbUrl(taobaoContentBean.getPicUrl(), mWidth, mHeight), mWidth, mHeight, viewHolder.product_img);
        couponShareUrl = taobaoContentBean.getCouponShareUrl();
        viewHolder.rl_content.setOnClickListener(new SearchOnClick((T) taobaoContentBean));
    }

    class SearchOnClick implements View.OnClickListener {

        private T data;

        public SearchOnClick(T data) {
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            if (Helper.isNotNull(searchInterface)) {
                searchInterface.searchInterface();
            }


            if (CommonData.TAOBAO == searchType) {
                UIHelper.getInstance().openUrl(act, ((TaobaoCoupons.ObjectsBean) data).getCouponShareUrl());
            }

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View rl_content;
        private TextView taobao_pro_desc, taobao_discountPrice, taobao_finalPrice, couponAmount;
        private ImageView product_img, taobao_platform;

        private ViewHolder(View v) {
            super(v);

            product_img = (ImageView) v.findViewById(R.id.product_img);
            taobao_platform = (ImageView) v.findViewById(R.id.taobao_platform);
            taobao_pro_desc = (TextView) v.findViewById(R.id.taobao_pro_desc);
            taobao_discountPrice = (TextView) v.findViewById(R.id.taobao_discountPrice);
            taobao_finalPrice = (TextView) v.findViewById(R.id.taobao_finalPrice);
            couponAmount = (TextView) v.findViewById(R.id.couponAmount);
            rl_content = v.findViewById(R.id.rl_content);

        }

    }

    public interface SearchInterface {

        void searchInterface();

    }

}
