package common.base;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;


import iconicfont.IconicFontDrawable;
import com.zhekouxingqiu.main.R;


/**
 * Created by Administrator on 2016/3/23.
 */
public class TitleFragment extends AppFragment {

    /**
     * 设置左按钮
     *
     * @param leftID
     */
    public void setLeft(int leftID) {
        setLeft(getString(leftID));
    }

    /**
     * 设置左按钮
     *
     * @param left
     */
    public void setLeft(String left) {
        ((TextView) findViewById(R.id.tv_find_sydney)).setText(left);
    }

    public void setLeft(View view, String left) {
        ((TextView) view.findViewById(R.id.tv_find_sydney)).setText(left);
    }

    public void setLeftDrawable(int leftId) {
        Drawable drawable = getResources().getDrawable(leftId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ((TextView) findViewById(R.id.tv_find_sydney)).setCompoundDrawables(drawable, null, null, null);
    }

    public void setLeftDrawable(View view, int leftId) {
        Drawable drawable = getResources().getDrawable(leftId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ((TextView) view.findViewById(R.id.tv_find_sydney)).setCompoundDrawables(drawable, null, null, null);
    }

    public void setLeftDrawable(View view, IconicFontDrawable iconicFontDrawable, int colorId) {
        iconicFontDrawable.setIconColor(getResources().getColor(colorId));
        view.findViewById(R.id.iv_find_sydney).setBackground(iconicFontDrawable);
    }

    public void setShowLeft() {
        findViewById(R.id.tv_find_sydney).setSelected(true);
        findViewById(R.id.tv_raiders).setSelected(false);
        findViewById(R.id.vw_find_sydney).setVisibility(View.VISIBLE);
        findViewById(R.id.vw_raiders).setVisibility(View.GONE);
    }

    public void setShowLeft(View view) {
        view.findViewById(R.id.tv_find_sydney).setSelected(true);
        view.findViewById(R.id.tv_raiders).setSelected(false);
        view.findViewById(R.id.vw_find_sydney).setVisibility(View.VISIBLE);
        view.findViewById(R.id.vw_raiders).setVisibility(View.GONE);
    }

//    public void setSpecificLeft(String left) {
//        ((TextView) findViewById(R.id.tv_specific_list)).setText(left);
//    }
//
//    public void setLeftSpecificDrawable(int leftId) {
//        Drawable drawable = getResources().getDrawable(leftId);
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        ((TextView) findViewById(R.id.tv_specific_list)).setCompoundDrawables(drawable, null, null, null);
//    }
//
//    public void setShowSpecificLeft() {
//        findViewById(R.id.fl_specific_list).setSelected(true);
//        findViewById(R.id.fl_specific_img).setSelected(false);
//        findViewById(R.id.vw_specific_list).setVisibility(View.VISIBLE);
//        findViewById(R.id.vw_specific_img).setVisibility(View.GONE);
//    }

    /**
     * 设置右按钮
     *
     * @param rightID
     */
    public void setRight(int rightID) {
        setRight(getString(rightID));
    }

    /**
     * 设置右按钮
     *
     * @param right
     */
    public void setRight(String right) {
        ((TextView) findViewById(R.id.tv_raiders)).setText(right);
    }

    public void setRight(View view, String right) {
        ((TextView) view.findViewById(R.id.tv_raiders)).setText(right);
    }

    public void setRightDrawable(int leftId) {
        Drawable drawable = getResources().getDrawable(leftId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ((TextView) findViewById(R.id.tv_raiders)).setCompoundDrawables(drawable, null, null, null);
    }

    public void setRightDrawable(View view, int leftId) {
        Drawable drawable = getResources().getDrawable(leftId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ((TextView) view.findViewById(R.id.tv_raiders)).setCompoundDrawables(drawable, null, null, null);
    }

    public void setRightDrawable(View view, IconicFontDrawable iconicFontDrawable, int colorId) {
        iconicFontDrawable.setIconColor(getResources().getColor(colorId));
        view.findViewById(R.id.iv_raiders).setBackground(iconicFontDrawable);
    }

    public void setShowRight() {
        findViewById(R.id.tv_find_sydney).setSelected(false);
        findViewById(R.id.tv_raiders).setSelected(true);
        findViewById(R.id.vw_find_sydney).setVisibility(View.GONE);
        findViewById(R.id.vw_raiders).setVisibility(View.VISIBLE);
    }

    public void setShowRight(View view) {
        view.findViewById(R.id.tv_find_sydney).setSelected(false);
        view.findViewById(R.id.tv_raiders).setSelected(true);
        view.findViewById(R.id.vw_find_sydney).setVisibility(View.GONE);
        view.findViewById(R.id.vw_raiders).setVisibility(View.VISIBLE);
    }

//    public void setSpecificRight(String right) {
//        ((TextView) findViewById(R.id.tv_specific_img)).setText(right);
//    }
//
//    public void setRightSpecificDrawable(int leftId) {
//        Drawable drawable = getResources().getDrawable(leftId);
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        ((TextView) findViewById(R.id.tv_specific_img)).setCompoundDrawables(drawable, null, null, null);
//    }
//
//    public void setShowSpecificRight() {
//        findViewById(R.id.fl_specific_list).setSelected(false);
//        findViewById(R.id.fl_specific_img).setSelected(true);
//        findViewById(R.id.vw_specific_list).setVisibility(View.GONE);
//        findViewById(R.id.vw_specific_img).setVisibility(View.VISIBLE);
//    }

    /**
     * 设置右按钮
     *
     * @param poiTitleID
     */
    public void setPoiTitle(int poiTitleID) {
        setPoiTitle(getString(poiTitleID));
    }

    /**
     * 设置右按钮
     *
     * @param poiTitle
     */
    public void setPoiTitle(String poiTitle) {
        ((TextView) findViewById(R.id.tv_title_poi)).setText(poiTitle);
    }

    /**
     * 设置左点击事件
     */
    public void setLiftOnClick() {
        findViewById(R.id.fl_find_sydney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left();
            }
        });
    }

    /**
     * 设置左点击事件
     */
    public void setLiftOnClick(View view) {
        view.findViewById(R.id.fl_find_sydney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left();
            }
        });
    }

    /**
     * 设置右点击事件
     */
    public void setRightOnClick() {
        findViewById(R.id.fl_raiders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right();
            }
        });
    }

    /**
     * 设置右点击事件
     */
    public void setRightOnClick(View view) {
        view.findViewById(R.id.fl_raiders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right();
            }
        });
    }

    /**
     * 设置左点击事件
     */
//    public void setSpecificLiftOnClick() {
//        findViewById(R.id.fl_specific_list).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                specificLeft();
//            }
//        });
//    }

    /**
     * 设置左点击事件
     */
    public void setSpecificLiftOnClick(View view) {
        view.findViewById(R.id.fl_find_sydney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specificLeft();
            }
        });
    }

    /**
     * 设置右点击事件
     */
//    public void setSpecificRightOnClick() {
//        findViewById(R.id.fl_specific_img).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                specificRight();
//            }
//        });
//    }

    /**
     * 设置右点击事件
     */
    public void setSpecificRightOnClick(View view) {
        view.findViewById(R.id.fl_raiders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specificRight();
            }
        });
    }

    /**
     * 设置POI点击事件
     */
    public void setSearchPoiOnClick() {
        findViewById(R.id.iv_search_poi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    /**
     * 设置POI点击事件
     */
    public void hideSearchPoiOnClick() {
        findViewById(R.id.iv_search_poi).setVisibility(View.GONE);
    }

    /**
     * 设置POI点击事件
     */
    public void showSearchPoiOnClick() {
        findViewById(R.id.iv_search_poi).setVisibility(View.VISIBLE);
    }

    /**
     * 后退界面
     */
    public void setPoiBack() {
        findViewById(R.id.iv_back_poi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    /**
     * 后退界面
     */
    public void setPoiBack(View view) {
        view.findViewById(R.id.iv_back_poi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    /**
     * 后退按钮
     */
    protected void back() {

    }

    /**
     * 左点击事件
     */
    protected void left() {

    }

    /**
     * 右点击事件
     */
    protected void right() {

    }

    /**
     * 左点击事件
     */
    protected void specificLeft() {

    }

    /**
     * 右点击事件
     */
    protected void specificRight() {

    }

    /**
     * 设置标题
     *
     * @param titleID
     */
    public void setTitle(int titleID) {
        setTitle(getString(titleID));
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        ((TextView) findViewById(R.id.tv_title)).setText(title);
    }

    /**
     * 设置搜索
     *
     * @param searchID
     */
//    public void setSearch(int searchID) {
//        setSearch(getString(searchID));
//    }
//
//    /**
//     * 设置标题
//     *
//     * @param search
//     */
//    public void setSearch(String search) {
//        ((TextView) findViewById(R.id.tv_search)).setText(search);
//    }

    /**
     * 设置搜索点击事件
     */
    public void setSearchOnClick() {
        findViewById(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    /**
     * 设置搜索点击事件
     */
    public void setSearchOnClick(View view) {
        view.findViewById(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    /**
     * 设置POI点击事件
     */
    public void hideSearchOnClick() {
        findViewById(R.id.tv_search).setVisibility(View.GONE);
    }

    /**
     * 设置POI点击事件
     */
    public void showSearchOnClick() {
        findViewById(R.id.tv_search).setVisibility(View.VISIBLE);
    }

    /**
     * 搜索
     */
    protected void search() {

    }

    /**
     * 修改控件显示状态
     */
    public void changeShowStatus(int viewId, int showStatus) {
        findViewById(viewId).setVisibility(showStatus);
    }

    /**
     * 修改控件显示状态
     */
    public void changeShowStatus(View view, int viewId, int showStatus) {
        view.findViewById(viewId).setVisibility(showStatus);
    }

    /**
     * 修改控件显示状态
     */
    public void changeViewBg(int viewId, IconicFontDrawable iconicFontDrawable) {
        findViewById(viewId).setBackground(iconicFontDrawable);
    }

    /**
     * 修改控件显示状态
     */
    public void changeViewBg(View view, int viewId, IconicFontDrawable iconicFontDrawable) {
        view.findViewById(viewId).setBackground(iconicFontDrawable);
    }

    public void clickActivity() {
        findViewById(R.id.iv_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.iv_activity).setVisibility(View.GONE);
                onClickActivity();
            }
        });
    }

    /**
     * 入口
     */
    protected void onClickActivity() {

    }

}
