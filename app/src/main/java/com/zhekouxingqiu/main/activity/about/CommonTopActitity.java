package com.zhekouxingqiu.main.activity.about;


import android.view.View;
import android.widget.TextView;

import common.base.AppBaseCompatActivity;
import iconicfont.IconicFontUtil;
import iconicfont.icon.*;
import com.zhekouxingqiu.main.R;


/**
 * @author linhuan on 2016/10/20 上午10:08
 */
public abstract class CommonTopActitity extends AppBaseCompatActivity {

    protected void setLeftIcon(Icon drawableId, int colorId) {
        findViewById(R.id.fl_left).setVisibility(View.VISIBLE);
//        findViewById(R.id.iv_left).setBackground(IconicFontUtil.createIconicFont(drawableId, colorId));
        findViewById(R.id.fl_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftEvent(v);
            }
        });
    }

    protected void leftEvent(View v) {
        doBack(-1, null);
    }

    protected void setRightIcon(Icon drawableId, int colorId) {
        findViewById(R.id.fl_right).setVisibility(View.GONE);
        findViewById(R.id.fl_right_icon).setVisibility(View.VISIBLE);
        findViewById(R.id.tv_right_icon).setBackground(IconicFontUtil.createIconicFont(drawableId, colorId));
        findViewById(R.id.fl_right_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightEvent(v);
            }
        });
    }

    protected void setRightText(int stringId) {
        findViewById(R.id.fl_right).setVisibility(View.VISIBLE);
        findViewById(R.id.fl_right_icon).setVisibility(View.GONE);
        setTextviewText(R.id.tv_right, stringId);
        findViewById(R.id.fl_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightEvent(v);
            }
        });
    }

    protected void rightEvent(View v) {}

    protected void setCenterContent(int stringId) {
        findViewById(R.id.tv_title).setVisibility(View.VISIBLE);
        setTextviewText(R.id.tv_title, stringId);
    }

    protected void setCenterContent(String string) {
        findViewById(R.id.tv_title).setVisibility(View.VISIBLE);
        setTextviewText(R.id.tv_title, string);
    }

    protected void setTextviewText(int viewId, String string) {
        ((TextView) findViewById(viewId)).setText(string);
    }

    protected void setTextviewText(int viewId, int stringId) {
        setTextviewText(viewId, getString(stringId));
    }

}
