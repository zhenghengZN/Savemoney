package adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import greendao.bean.History;
import greendao.bean.Restaurant;
import iconicfont.IconicFontDrawable;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import so.bubu.lib.base.adapter.BasicsAdapter;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.ResourceHelper;
import com.zhekouxingqiu.main.R;

/**
 * @author linhuan on 16/4/29 下午4:51
 */
public class SearchHistoryAdapter<T> extends BasicsAdapter {

    public static final int HISTORY = 0;
    public static final int HOTEL = 1;
    public static final int CUISINE = 2;
    public static final int ENTER_CUISINE = 3;

    private int showType;
    private Map<Integer, Boolean> selectMap;
    private List<T> tempList;

    private IconicFontDrawable correctIconBg, showIconBg;
    private ViewHolder holder;

    public SearchHistoryAdapter(Context act, List<T> dataList) {
        super(act, dataList);
        showType = HISTORY;
        setShowDrawable();
    }

    public SearchHistoryAdapter(Context act, List<T> dataList, int showType) {
        super(act, dataList);
        this.showType = showType;
        setShowDrawable();
    }

    public List<String> getHotelType() {
        List<String> hotelType = new ArrayList<>();
        int size = selectMap.size();
        for (int i = 0; i < size; i++) {
            if (selectMap.get(i)) {
                hotelType.add((String) dataList.get(i));
            }
        }
        return hotelType;
    }

//    public String getCuisineType() {
//        if (1 == dataList.size()) {
//            return ((Restaurant) dataList.get(0)).getCuisines();
//        }
//        return "";
//    }

    public void setShowDrawable() {
        switch (showType) {

            case HISTORY:
                showIconBg = IconicFontUtil.createIconicFontDrawable(CityGuideIcon.ICON_TIME);
                break;

            case HOTEL:
                showIconBg = IconicFontUtil.createIconicFontDrawable(CityGuideIcon.ICON_HOTEL);
                break;

            case CUISINE:
                tempList = new ArrayList<>();
                tempList.addAll(dataList);
            case ENTER_CUISINE:
                showIconBg = IconicFontUtil.createIconicFontDrawable(CityGuideIcon.ICON_CUISINA);
                break;

        }

        initSelectMap();
    }

    private void initSelectMap() {
        selectMap = new HashMap<>();
        int size = dataList.size();
        for (int i = 0; i < size; i++) {
            selectMap.put(i, false);
        }
    }

    @Override
    protected View setViewHolder(View convertView) {
        holder = new ViewHolder();
        convertView = ResourceHelper.loadLayout(act, R.layout.item_search_history);
        holder.rlContent = (RelativeLayout) convertView.findViewById(R.id.rl_content);
        holder.ivPre = (ImageView) convertView.findViewById(R.id.iv_pre);
        holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
        holder.ivIcon.setBackground(showIconBg);
        holder.tvSearchContent = (TextView) convertView.findViewById(R.id.tv_search_content);
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    protected View getViewHolder(View convertView) {
        holder = (ViewHolder) convertView.getTag();
        return convertView;
    }

    @Override
    protected void setItemData(int position) {
        String showContent = "";
        switch (showType) {

            case HISTORY:
                showContent = ((History) dataList.get(position)).getSearchContent();
                break;

            case HOTEL:
                setPreData(position);
            case ENTER_CUISINE:
                showContent = (String) dataList.get(position);
                break;

            case CUISINE:
                setPreData(position);
                showContent = ((Restaurant) dataList.get(position)).getCuisines();
                break;

        }
        holder.tvSearchContent.setText(showContent);
    }

    private void setPreData(int position) {
        holder.rlContent.setOnClickListener(new ItemOnClick(position));
        if (selectMap.get(position)) {
            holder.ivPre.setVisibility(View.VISIBLE);
            if (Helper.isNull(correctIconBg)) {
                correctIconBg = IconicFontUtil.createIconicFontDrawablePre(CityGuideIcon.ICON_CORRECT);
            }
            holder.ivPre.setBackground(correctIconBg);
        } else {
            holder.ivPre.setVisibility(View.GONE);
        }
    }

    public void showSelectData(List<String> restaurantList) {
        initSelectMap();
        int size = dataList.size();
        for (String string : restaurantList) {
            for (int i = 0; i < size; i++) {
                if (dataList.get(i).equals(string)) {
                    selectMap.put(i, true);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void showSelectData(Restaurant restaurant) {
        dataList.clear();
        dataList.add(restaurant);
        selectMap.clear();
        selectMap.put(0, true);
    }

    class ItemOnClick implements View.OnClickListener {

        private int position;

        public ItemOnClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (showType) {

                case HOTEL:
                    selectMap.put(position, !selectMap.get(position));
                    break;

                case CUISINE:
                    if (selectMap.get(position)) {
                        dataList.clear();
                        dataList.addAll(tempList);
                        initSelectMap();
                    } else {
                        showSelectData((Restaurant) tempList.get(position));
                    }
                    break;

            }
            notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        TextView tvSearchContent;
        ImageView ivPre, ivIcon;
        RelativeLayout rlContent;
    }

}
