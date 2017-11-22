package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.avos.avoscloud.LogUtil;
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

    private int mWidth, mHeight, mGridWidth;//, picWidth;
    private View mHeaderView;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_GRID = 2;

    public TaobaoContentAdapter(Context mContext, LinkedList<TaobaoCoupons.ObjectsBean> mItemLists) {
        super(mContext, mItemLists);
        mWidth = ResourceHelper.Dp2Px(115);
        mHeight = ResourceHelper.Dp2Px(115);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        // 计算显示大小
        mGridWidth = wm.getDefaultDisplay().getWidth() / 2;
        LogUtil.log.e("mGridWidth","mGridWidth" + mGridWidth + "  ," + ResourceHelper.Dp2Px(280) + " ,"  );

//        picWidth = mGridWidth - ResourceHelper.Dp2Px(4);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }

//        if(viewType == TYPE_NORMAL) {
//            view = ResourceHelper.loadLayout(mContext, R.layout.taobao_item, parent, false);
//        }
        LogUtil.log.e("TaobaoContentAdapter", "1");
        if (viewType == TYPE_GRID) {
            view = ResourceHelper.loadLayout(mContext, R.layout.taobao_item2, parent, false);
        }
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LogUtil.log.e("haschage","haschage");
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        final int pos = getRealPosition(holder);
        if (getItemViewType(position) == TYPE_NORMAL) {
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

        if (getItemViewType(position) == TYPE_GRID) {
            TaobaoCoupons.ObjectsBean taobaoContentBean = mItemLists.get(pos);
            holder.taobao_pro_desc.setText(taobaoContentBean.getTitle());

            holder.taobao_finalPrice.setText(taobaoContentBean.getFinalPrice() + " ");

            ViewGroup.LayoutParams layoutParams = holder.rl_content.getLayoutParams();
            layoutParams.width = mGridWidth;
            layoutParams.height = mGridWidth + ResourceHelper.Dp2Px(110);
            holder.rl_content.setLayoutParams(layoutParams);
            holder.biz30Day.setText("月销:" + taobaoContentBean.getBiz30Day());
            holder.couponAmount.setText("立减 " + taobaoContentBean.getCouponAmount() + " 元");
            if (taobaoContentBean.getPlatform().equals("天猫")) {
                holder.taobao_platform.setImageResource(R.drawable.tmall_logo_30);
                holder.taobao_discountPrice.setText("天猫价 ¥" + taobaoContentBean.getDiscountPrice());
            } else {
                holder.taobao_platform.setImageResource(R.drawable.taobao_logo_30);
                holder.taobao_discountPrice.setText("淘宝价 ¥" + taobaoContentBean.getDiscountPrice());
            }
            GlideHelper.displayImageByResizeasBitmap(mContext, CommonMethod.getThumbUrl(taobaoContentBean.getPicUrl(), mGridWidth, mGridWidth), mGridWidth, mGridWidth, holder.product_img);
            holder.itemView.setTag(pos);
        }
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
//        if (mHeaderView == null) {
//            return TYPE_NORMAL;
//        }
        LogUtil.log.e("TaobaoContentAdapter", "2");
        if (getHeaderView() != null && position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_GRID;
        }


    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView product_img, taobao_platform;
        private TextView taobao_pro_desc, taobao_discountPrice, taobao_finalPrice, couponAmount, biz30Day;
        private RelativeLayout rl_content;

        public ViewHolder(View itemView) {
            super(itemView);

            product_img = (ImageView) itemView.findViewById(R.id.product_img);
            taobao_platform = (ImageView) itemView.findViewById(R.id.taobao_platform);
            biz30Day = (TextView) itemView.findViewById(R.id.biz30Day);
            taobao_pro_desc = (TextView) itemView.findViewById(R.id.taobao_pro_desc);
            taobao_discountPrice = (TextView) itemView.findViewById(R.id.taobao_discountPrice);
            taobao_finalPrice = (TextView) itemView.findViewById(R.id.taobao_finalPrice);
            couponAmount = (TextView) itemView.findViewById(R.id.couponAmount);
            rl_content = (RelativeLayout) itemView.findViewById(R.id.rl_content);
        }
    }
}
