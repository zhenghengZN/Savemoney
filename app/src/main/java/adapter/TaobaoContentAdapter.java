package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.kevin.wraprecyclerview.BaseRecyclerAdapter;

import java.util.LinkedList;

import bean.TaobaoCoupons;
import common.CommonMethod;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.Coupon.AliTrade.R;
import utils.GlideHelper;

/**
 * Created by zhengheng on 17/9/13.
 */
public class TaobaoContentAdapter extends BaseRecyclerAdapter<TaobaoCoupons.ObjectsBean, TaobaoContentAdapter.ViewHolder> implements View.OnClickListener {

    private int mWidth, mHeight;
    private View mHeaderView;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    public TaobaoContentAdapter(Context mContext, LinkedList<TaobaoCoupons.ObjectsBean> mItemLists) {
        super(mContext, mItemLists);
        mWidth = ResourceHelper.Dp2Px(115);
        mHeight = ResourceHelper.Dp2Px(115);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }
        View view = ResourceHelper.loadLayout(mContext, R.layout.taobao_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        final int pos = getRealPosition(holder);
        TaobaoCoupons.ObjectsBean taobaoContentBean = mItemLists.get(pos);
        holder.taobao_pro_desc.setText(taobaoContentBean.getTitle());

        holder.taobao_finalPrice.setText(taobaoContentBean.getFinalPrice() + " ");

        holder.biz30Day.setText("月销:" + taobaoContentBean.getBiz30Day());
        holder.couponAmount.setText("立减 " + taobaoContentBean.getCouponAmount() + " 元");
        if (taobaoContentBean.getPlatform().equals("天猫")) {
            holder.taobao_platform.setImageResource(R.drawable.tmall_logo_30);
            holder.taobao_discountPrice.setText("天猫价 ¥" + taobaoContentBean.getDiscountPrice());
        } else {
            holder.taobao_platform.setImageResource(R.drawable.taobao_logo_30);
            holder.taobao_discountPrice.setText("淘宝价 ¥" + taobaoContentBean.getDiscountPrice());
        }
        GlideHelper.displayImageByResizeasBitmap(mContext, CommonMethod.getThumbUrl(taobaoContentBean.getPicUrl(), mWidth, mHeight), mWidth, mHeight, holder.product_img);

        holder.itemView.setTag(pos);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    private OnItemClickListener mOnItemClickListener = null;

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mItemLists.size() : mItemLists.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;

        return TYPE_NORMAL;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView product_img, taobao_platform;
        private TextView taobao_pro_desc, taobao_discountPrice, taobao_finalPrice, couponAmount, biz30Day;

        public ViewHolder(View itemView) {
            super(itemView);

            product_img = (ImageView) itemView.findViewById(R.id.product_img);
            taobao_platform = (ImageView) itemView.findViewById(R.id.taobao_platform);
            biz30Day = (TextView) itemView.findViewById(R.id.biz30Day);
            taobao_pro_desc = (TextView) itemView.findViewById(R.id.taobao_pro_desc);
            taobao_discountPrice = (TextView) itemView.findViewById(R.id.taobao_discountPrice);
            taobao_finalPrice = (TextView) itemView.findViewById(R.id.taobao_finalPrice);
            couponAmount = (TextView) itemView.findViewById(R.id.couponAmount);
        }
    }
}
