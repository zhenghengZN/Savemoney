package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.LogUtil;

import java.util.LinkedList;

import bean.TitleBean;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.ResourceHelper;

/**
 * Created by zhengheng on 17/11/14.
 */
public class HorizontaltitleAdapter extends RecyclerView.Adapter<HorizontaltitleAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private LinkedList<TitleBean> mTitles = new LinkedList();
    private int wightWidth;

    public HorizontaltitleAdapter() {

    }

    public HorizontaltitleAdapter(Context context, LinkedList<TitleBean> titles) {
        this.mContext = context;
        this.mTitles = titles;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        // 计算显示大小
        LogUtil.log.e("HorizontaltitleAdapter", " " + wm.getDefaultDisplay().getWidth());
        wightWidth = wm.getDefaultDisplay().getWidth() / titles.size();
    }

    @Override
    public HorizontaltitleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ResourceHelper.loadLayout(mContext, R.layout.horizontaltitle_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(HorizontaltitleAdapter.ViewHolder holder, int position) {
        LogUtil.log.e("HorizontaltitleAdapter", "onBindViewHolder");
        ViewGroup.LayoutParams layoutParams = holder.title_layout.getLayoutParams();
        layoutParams.width = wightWidth;
        layoutParams.height = ResourceHelper.Dp2Px(160) / 4;
        holder.title_layout.setLayoutParams(layoutParams);
        if (position == 3) {
            holder.title_image_layout.setVisibility(View.VISIBLE);
            changeImage(holder.itemView);
        } else {
//            isPriceZA = null;
            holder.title_image_layout.setVisibility(View.GONE);
        }
        TitleBean title = mTitles.get(position);
        holder.title.setText(title.getTitle());
        if (selectedPosition == position) {
            title.setIsSelect(true);
        } else {
            title.setIsSelect(false);

        }
        holder.title.setTextColor(title.isSelect() ? mContext.getResources().getColor(R.color.color_ff6f06) : mContext.getResources().getColor(R.color.color_000000));
//        holder.title.setTextColor(0xFFFFFF);
        holder.itemView.setTag(position);

    }

    private int selectedPosition = 0;

    @Override
    public void onClick(View v) {
        LogUtil.log.e(" v.getId()", "" + v.getId());
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position

            mOnItemClickListener.onItemClick(v, (int) v.getTag());
            selectedPosition = (int) v.getTag();

            if(selectedPosition == 3){
//                changeImage(v);
            } else {
                isPriceZA = null;
            }
        }
    }


    public int getSelectedPosition() {
        return selectedPosition;
    }

    private void changeImage(View  view) {
        final ImageView priceZA = (ImageView) view.findViewById(R.id.priceZA);
        final ImageView priceAZ = (ImageView) view.findViewById(R.id.priceAZ);
        if (isPriceZA == null) {
            priceAZ.setSelected(false);
            priceZA.setSelected(false);
        } else {
            if (isPriceZA) {
                priceZA.setSelected(true);
                priceAZ.setSelected(false);
//            isPriceZA = false;
            } else {
                priceZA.setSelected(false);
                priceAZ.setSelected(true);
//            isPriceZA = true;
            }
        }
    }

    private Boolean isPriceZA = null;
    // 让另一个布局的适配器能显示对应的价格图片颜色
    public void setPriceZA(Boolean isPriceZA) {
        this.isPriceZA = isPriceZA;
    }

    // 让另一个布局的适配器能显示对应的价格图片颜色
//    public void setPriceAZ(Boolean isPriceAZ) {
//        this.isPriceAZ = isPriceAZ;
//    }

    public void setSelectedPosition(int SelectedPosition) {
        this.selectedPosition = SelectedPosition;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private LinearLayout title_image_layout;
        private RelativeLayout title_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            title_layout = (RelativeLayout) itemView.findViewById(R.id.title_layout);
            title_image_layout = (LinearLayout) itemView.findViewById(R.id.title_image_layout);
        }
    }
}
