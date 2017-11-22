package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.avos.avoscloud.LogUtil;

import java.util.LinkedList;

import bean.TitleBean;
import so.bubu.Coupon.AliTrade.R;
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
//        if (position == 0) {
//            holder.title.setTextColor(0xFF6F06);
//        } else {
//        }
        LogUtil.log.e("HorizontaltitleAdapter", "onBindViewHolder");
        ViewGroup.LayoutParams layoutParams = holder.title.getLayoutParams();
        layoutParams.width = wightWidth;
        layoutParams.height = ResourceHelper.Dp2Px(160)/4;
        holder.title.setLayoutParams(layoutParams);
        TitleBean title = mTitles.get(position);
        holder.title.setText(title.getTitle());
        if(selectedPosition == position) {
            title.setIsSelect(true);
        } else {
            title.setIsSelect(false);
        }
        holder.title.setTextColor(title.isSelect()? mContext.getResources().getColor(R.color.color_ff6f06):mContext.getResources().getColor(R.color.color_000000));
//        holder.title.setTextColor(0xFFFFFF);
        holder.itemView.setTag(position);

    }

    private int selectedPosition = 0;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
            selectedPosition = (int) v.getTag();
        }
    }

    public int getSelectedPosition(){
        return selectedPosition;
    }

    public void  setSelectedPosition(int SelectedPosition) {
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

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
