package adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhekouxingqiu.main.R;

import java.util.LinkedList;
import java.util.List;

import bean.MenuBean;
import bean.TaobaoCoupons;
import so.bubu.lib.helper.ResourceHelper;
import utils.GlideHelper;

/**
 * 类型适配器
 * <p/>
 * Created by Administrator on 2016/3/23.
 */
public class CategroyMenuAdapter extends RecyclerView.Adapter<CategroyMenuAdapter.ViewHolder> implements View.OnClickListener {

    private ViewHolder holder;
    private List<MenuBean.ObjectsBeanBean> subcategroies = new LinkedList<>();
    private int mWidth;
    private Context act;

    public CategroyMenuAdapter(Context act, List<MenuBean.ObjectsBeanBean> subcategroies) {
        this.subcategroies = subcategroies;
        this.act = act;
        WindowManager wm = (WindowManager) act.getSystemService(Context.WINDOW_SERVICE);
//        wm.getDefaultDisplay().getWidth();
        // 计算显示大小
        mWidth = wm.getDefaultDisplay().getWidth() / 4;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = ResourceHelper.loadLayout(act, R.layout.subcatory_item);
        ViewHolder holder = new ViewHolder(convertView);
        convertView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewGroup.LayoutParams lp = holder.subcategory_ll.getLayoutParams();
        lp.height = mWidth;
        lp.width = mWidth;
        holder.subcategory_ll.setLayoutParams(lp);
        holder.subcategory_name.setText(subcategroies.get(position).getName());
        GlideHelper.displayImageByResizeasBitmap(act, subcategroies.get(position).getPicUrl(), mWidth / 2, mWidth / 2, holder.subcategory_img);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return subcategroies.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout subcategory_ll;
        private ImageView subcategory_img;
        private TextView subcategory_name;

        public ViewHolder(View itemView) {
            super(itemView);
            subcategory_img = (ImageView) itemView.findViewById(R.id.subcategory_img);
            subcategory_name = (TextView) itemView.findViewById(R.id.subcategory_name);
            subcategory_ll = (LinearLayout) itemView.findViewById(R.id.subcategory_ll);
        }
    }


}
