package common.base;

import android.support.annotation.LayoutRes;
import android.view.View;


import so.bubu.lib.helper.StatusBarUtil;
import so.bubu.Coupon.AliTrade.R;

/**
 * Created by wangwn on 2016/4/13.
 */
public abstract class StatusBarBaseCompatActivity extends AppBaseCompatActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    protected void setStatusBar() {
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
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
