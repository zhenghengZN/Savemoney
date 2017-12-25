package adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


import bean.TaobaoCoupons;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.ResourceHelper;
import utils.GlideHelper;

/**
 * 类型适配器
 * <p/>
 * Created by Administrator on 2016/3/23.
 */
public class TypeAdapter extends BaseAdapter {

    private ViewHolder holder;
    private TaobaoCoupons.WidgetsBean subcategroies = new TaobaoCoupons.WidgetsBean();
    private final int mWidth;
    private Context act;

    public TypeAdapter(Context act, TaobaoCoupons.WidgetsBean subcategroies) {
        this.subcategroies = subcategroies;
        this.act = act;
        WindowManager wm = (WindowManager) act.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getWidth();
        // 计算显示大小
        mWidth = wm.getDefaultDisplay().getWidth() / 4;
    }

    @Override
    public int getCount() {
        List<TaobaoCoupons.WidgetsBean.ObjectsBean> objectsX = subcategroies.getObjects();
        if (objectsX != null) {
            return objectsX.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (subcategroies.getStyle() != null) {
                if (subcategroies.getStyle().equals("IconAndText")) {
                    convertView = ResourceHelper.loadLayout(act, R.layout.subcatory_item);
                } else {
                    //其他布局
                }
            }
            holder = new ViewHolder();
            holder.subcategory_img = (ImageView) convertView.findViewById(R.id.subcategory_img);
            holder.subcategory_name = (TextView) convertView.findViewById(R.id.subcategory_name);
            holder.subcategory_ll = (LinearLayout) convertView.findViewById(R.id.subcategory_ll);
            ViewGroup.LayoutParams lp = holder.subcategory_ll.getLayoutParams();
            lp.height = mWidth;
            lp.width = mWidth;
            holder.subcategory_ll.setLayoutParams(lp);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TaobaoCoupons.WidgetsBean.ObjectsBean objectsBean = subcategroies.getObjects().get(position);
        holder.subcategory_name.setText(objectsBean.getName());
        GlideHelper.displayGridByResizeasBitmap(act, objectsBean.getPicUrl(), mWidth / 2, mWidth / 2, holder.subcategory_img);

        return convertView;
    }

    static class ViewHolder {
        LinearLayout subcategory_ll;
        ImageView subcategory_img;
        TextView subcategory_name;
    }

}
