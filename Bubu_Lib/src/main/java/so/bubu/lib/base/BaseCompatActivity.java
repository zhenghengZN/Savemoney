package so.bubu.lib.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;
import so.bubu.lib.R;
import so.bubu.lib.helper.AppManager;
import so.bubu.lib.helper.DelayTaskHelper;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.wiget.AsynTask;

/**
 * 项目基类
 *
 * Created by Administrator on 2016/3/22.
 */
public abstract class BaseCompatActivity extends AppCompatActivity {

    private static final String TAG = BaseCompatActivity.class.getSimpleName();

    private int backEnterAnim = 0;
    private int backExitAnim = 0;
    private String activityAnimType = "";

    // 手势后退layout
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);

        if (isSwipeback()) {
            mHelper = new SwipeBackActivityHelper(this);
            mHelper.onActivityCreate();

            // 添加手势返回View
//        getSwipeBackLayout().setEdgeSize(DeviceHelper.getScreenWidth());
            swipeBackActivityBase.getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
            swipeBackActivityBase.getSwipeBackLayout().setSwipeProcess(swipeProcess);
        }

        onCreateView(savedInstanceState);
        getAnimParams();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (isSwipeback()) {
            mHelper.onPostCreate();
        }
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (Helper.isNull(v) && Helper.isNotNull(mHelper))
            return mHelper.findViewById(id);
        return v;
    }

    private SwipeBackLayout.SwipeProcess swipeProcess = new SwipeBackLayout.SwipeProcess() {
        @Override
        public void beforeFinishActivity(Activity act) {
            setResultForSwipeBack(act);
        }
    };

    private SwipeBackActivityBase swipeBackActivityBase = new SwipeBackActivityBase() {

        @Override
        public SwipeBackLayout getSwipeBackLayout() {
            return mHelper.getSwipeBackLayout();
        }

        @Override
        public void setSwipeBackEnable(boolean enable) {
            getSwipeBackLayout().setEnableGesture(enable);
        }

        @Override
        public void scrollToFinishActivity() {
            Utils.convertActivityToTranslucent(BaseCompatActivity.this);
            getSwipeBackLayout().scrollToFinishActivity();
        }

    };

    /**
     * function: 获取动画参数
     *
     * @author linhuan 2014年7月18日 上午12:01:46
     */
    private void getAnimParams() {
        Intent intent = getIntent();
        if (Helper.isNotNull(intent)) {
            if (intent.hasExtra(BaseConstants.ActivityInfo.BUNDLEKEY_ACTIVITYANIMTYPE)) {
                activityAnimType  = intent.getStringExtra(BaseConstants.ActivityInfo.BUNDLEKEY_ACTIVITYANIMTYPE);
            }
            if (intent.hasExtra(BaseConstants.ActivityInfo.BUNDLEKEY_BACKENTERANIM)) {
                backEnterAnim = intent.getIntExtra(BaseConstants.ActivityInfo.BUNDLEKEY_BACKENTERANIM, 0);
            }
            if (intent.hasExtra(BaseConstants.ActivityInfo.BUNDLEKEY_BACKEXITANIM)) {
                backExitAnim = intent.getIntExtra(BaseConstants.ActivityInfo.BUNDLEKEY_BACKEXITANIM, 0);
            }
        }
    }

    /**
     * function: 设置后退动画(finish时调用)
     *
     * @author linhuan 2014年7月18日 上午1:24:17
     */
    public void setBackAnim() {
        if (BaseConstants.ActivityInfo.ACTIVITYANIMTYPE_SLIDE.equals(activityAnimType)) {
            overridePendingTransition(R.anim.pull_right_in, R.anim.pull_right_out);
        } else if (BaseConstants.ActivityInfo.ACTIVITYANIMTYPE_PUSHUP.equals(activityAnimType)) {
            overridePendingTransition(R.anim.pull_bottom_in, R.anim.pull_bottom_out);
        } else if (BaseConstants.ActivityInfo.ACTIVITYANIMTYPE_CENTER.equals(activityAnimType)) {
            overridePendingTransition(R.anim.pull_center_in, R.anim.pull_center_out);
        } else if (BaseConstants.ActivityInfo.ACTIVITYANIMTYPE_ALPHA.equals(activityAnimType)) {
            overridePendingTransition(R.anim.pull_alpha_in, R.anim.pull_alpha_out);
        } else {
            overridePendingTransition(backEnterAnim, backExitAnim);
        }
    }

    /**
     * onCreateView:初始化界面
     *
     * @author linhuan 2016/1/27 0027 11:27
     */
    protected abstract void onCreateView(Bundle savedInstanceState);

    /**
     * findView 读取布局
     *
     * @param id 控件ID
     * @return
     *
     * @author linhuan 2015年7月11日 上午9:20:38
     */
    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    @Override
    public void onBackPressed() {
        doBack(-1, null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode && 0 == event.getRepeatCount()) { // 按下的如果是BACK，同时没有重复
            doBack(keyCode, event);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 是否开启手势返回，默认true
     *
     * @return
     */
    protected boolean isSwipeback() {
        return true;
    }

    /**
     * function: 设置界面
     *
     * @author:linhuan 2015-1-14 上午9:05:01
     */
    protected void initView() {

    }

    /**
     * function: 设置数据
     *
     * @author:linhuan 2015-1-14 上午9:05:01
     */
    protected void initData() {

    }

    /**
     * function: 后退处理
     *
     * @param keyCode
     * @param event
     *
     * @author:linhuan 2014年8月5日 下午7:59:01
     */
    protected abstract void doBack(int keyCode, KeyEvent event);

    /**
     *
     * function: 后势滑动前设置结果(NavigationHelper.setResult)，与doBack不同的是不用自己调finish
     *
     *
     * @ author:linjunying 2014年8月13日 下午3:47:02
     */
    protected void setResultForSwipeBack(Activity act) {}

    /**
     * 添加异步请求
     *
     * @param type
     */
    protected final void addAsynTask(int type) {
        DelayTaskHelper.doAsynTask(new BaseOnAsynExecuteListener(type));
    }

    /**
     * 异步读取数据
     */
    class BaseOnAsynExecuteListener implements AsynTask.OnAsynExecuteListener {

        private int type;

        public BaseOnAsynExecuteListener(int type) {
            this.type = type;
        }

        @Override
        public void onStartBackground() {
            startBackground(type);
        }

        @Override
        public void onProgressUpdate() {
            progressUpdate(type);
        }

        @Override
        public void onPreExecute() {
            preExecute(type);
        }

        @Override
        public void onPostExecute() {
            postExecute(type);
        }
    }

    protected void startBackground(int type) {}

    protected void progressUpdate(int type) {}

    protected void preExecute(int type) {}

    protected void postExecute(int type) {}

}
