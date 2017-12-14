package common.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import iconicfont.IconicFontDrawable;
import com.zhekouxingqiu.main.R;


/**
 * Created by Administrator on 2016/3/26.
 */
public abstract class TitleAppCompatActivity extends AppBaseCompatActivity {

    /**
     * 设置后退按钮
     */
    public void setBackClickTwo() {
        findViewById(R.id.fl_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBack(-1, null);
            }
        });
    }

    /**
     * 设置后退按钮
     */
    public void setBackClick() {
        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBack(-1, null);
            }
        });
    }

    /**
     * 设置后退按钮
     */
    public void setBackBg(int backBg) {
        ((ImageView) findViewById(R.id.tv_back)).setImageResource(backBg);
    }

    /**
     * 设置后退按钮
     */
    public void setBackSearch() {
        findViewById(R.id.iv_back_poi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBack(-1, null);
            }
        });
    }

    /**
     * 设置控件状态
     *
     * @param id
     * @param visibility
     */
    public void setVisibility(int id, int visibility) {
        findViewById(id).setVisibility(visibility);
    }

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
     * 设置标题名
     *
     * @param titleID
     */
    public void setTitle(int titleID) {
        setTitle(getString(titleID));
    }

    /**
     * 设置标题名
     *
     * @param title
     */
    public void setTitle(String title) {
        ((TextView) findViewById(R.id.tv_title)).setText(title);
    }


    /**
     * 设置右边按钮名
     *
     * @param rightID
     */
    public void setRight(int rightID) {
        setRight(getString(rightID));
    }

    /**
     * 设置右边按钮名
     *
     * @param right
     */
    public void setRight(String right) {
        ((TextView) findViewById(R.id.tv_switching)).setText(right);
    }

    /**
     * 设置右边按钮图片
     *
     * @param right
     */
    public void setRightImg(IconicFontDrawable right) {
        setVisibility(R.id.img_click, View.VISIBLE);
        findViewById(R.id.img_click).setBackground(right);
    }

    /**
     * 设置右点击事件
     */
    public void setRightOnClick() {
        findViewById(R.id.tv_switching).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right();
            }
        });
    }

    /**
     * 设置右图片点击事件
     */
    public void setRightImgOnClick() {
        findViewById(R.id.img_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightImgClick();
            }
        });
    }

    /**
     * 隐藏右按钮
     */
    public void setHideRight() {
        findViewById(R.id.tv_switching).setVisibility(View.GONE);
    }

    /**
     * 隐藏中间按钮
     */
    public void setHideCenter() {
        findViewById(R.id.tv_title).setVisibility(View.GONE);
    }

    /**
     * 设置搜索
     */
    public void setSearch() {
        findViewById(R.id.iv_search_poi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    /**
     * 搜索
     */
    protected void search() {

    }

    /**
     * 右点击事件
     */
    protected void right() {

    }

    /**
     * 右图片点击事件
     */
    protected void rightImgClick() {

    }

    /**
     * 设置显示状态
     */
    public void setShowState(int showId, int showState) {
        findViewById(showId).setVisibility(showState);
    }

    /**
     * 修改控件显示状态
     */
    public void changeViewBg(int viewId, IconicFontDrawable iconicFontDrawable) {
        findViewById(viewId).setBackground(iconicFontDrawable);
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
