package common.base;

import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;


import org.greenrobot.eventbus.EventBus;

import common.LeadCloudHelp;
import common.MobclickHelp;
import com.zhekouxingqiu.main.R;


/**
 * Created by Administrator on 2016/3/22.
 */
public abstract class AppBaseCompatActivity extends AppBasePermissionActivity {

    private boolean isHasNoData = false;

    private View contentFail;
    //	private ImageView ivContentFail;
    private TextView tvContentFail;

    protected void initFail() {
        contentFail = findViewById(R.id.ll_content_fail);
//		ivContentFail = findView(R.id.iv_content_fail);
        tvContentFail = findView(R.id.tv_content_fail);

        findViewById(R.id.ll_content_fail_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHasNoData) {
                    reloadData();
                }
            }
        });
    }

    protected void reloadData() {

    }

    protected void showFail(int showId) {
        isHasNoData = true;
        findViewById(showId).setVisibility(View.GONE);
        contentFail.setVisibility(View.VISIBLE);
        tvContentFail.setText(R.string.text_loading_fail);
    }

    protected void showContent(int showId) {
        isHasNoData = false;
        findViewById(showId).setVisibility(View.VISIBLE);
        contentFail.setVisibility(View.GONE);
    }

    protected void showNoData(int showId) {
        isHasNoData = false;
        findViewById(showId).setVisibility(View.GONE);
        contentFail.setVisibility(View.VISIBLE);
        tvContentFail.setText(R.string.text_loading_no_data);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (isOpenEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (isOpenEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isOpenMobclick()) {
            // 友盟
            MobclickHelp.onResume(getClass().getSimpleName(), this);
        }

        if (isOpenLeadCloud()) {
            // LeadCloud
            LeadCloudHelp.onResume(this);
        }

        if (isOpenLeadCloudClick()) {
            AVAnalytics.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isOpenMobclick()) {
            // 友盟
            MobclickHelp.onPause(getClass().getSimpleName(), this);
        }

        if (isOpenLeadCloud()) {
            // LeadCloud
            LeadCloudHelp.onPause(this);
        }

        if (isOpenLeadCloudClick()) {
            AVAnalytics.onPause(this);
        }
    }

    /**
     * 是否开启eventbus
     */
    protected boolean isOpenEventBus() {
        return false;
    }

    /**
     * 是否开启友盟统计
     *
     * @return
     */
    protected boolean isOpenMobclick() {
        return true;
    }

    /**
     * 是否开启leadcloud统计
     *
     * @return
     */
    protected boolean isOpenLeadCloud() {
        return true;
    }

    /**
     * 是否开启leadcloud统计
     *
     * @return
     */
    protected boolean isOpenLeadCloudClick() {
        return true;
    }

}
