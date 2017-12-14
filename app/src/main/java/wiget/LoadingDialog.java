package wiget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.internal.ProgressDrawable;

import so.bubu.lib.helper.Helper;
import com.zhekouxingqiu.main.R;

/**
 * loading_default dialog
 * if the context has been finished may cause error :
 * [token android.os.BinderProxy@47a2d128 is not valid; is your activity running?]
 * Created by Auro on 8/20/15.
 */
public class LoadingDialog extends Dialog {

    private final String TAG = "LoadingDialog";

    ImageView loading;

    TextView tvMsg;

    private static LoadingDialog instance;

    private static Context mContext;

    private Animation anim;

    public static LoadingDialog getInstance(Context context) {
        try {
            if (mContext != null && ((Activity) mContext).isFinishing() ) {
                //if context has been finished
//            mContext = context;
                instance = new LoadingDialog(context);
            } else if (instance == null)
                instance = new LoadingDialog(context);
            else {
                if (!instance.isShowing()) {
                    //如果对话框已经隐藏了 并且不是同一个context 则不需要旧的context 而应该以新进来的context作为主体
                    Log.d("cai", "重新创建对话框");
                    instance = new LoadingDialog(context);
                }
            }
        } catch (Exception e) {
            instance = new LoadingDialog(context);
        }
        return instance;
    }

    public static LoadingDialog getInstance(Context context, boolean cancelalable) {
        try {
            if (mContext != null && ((Activity) mContext).isFinishing() ) {
                //if context has been finished
//            mContext = context;
                instance = new LoadingDialog(context, cancelalable);
            } else if (instance == null)
                instance = new LoadingDialog(context, cancelalable);
            else {
                if (!instance.isShowing()) {
                    //如果对话框已经隐藏了 并且不是同一个context 则不需要旧的context 而应该以新进来的context作为主体
                    Log.d("cai", "重新创建对话框");
                    instance = new LoadingDialog(context, cancelalable);
                }
            }
        } catch (Exception e) {
            instance = new LoadingDialog(context, cancelalable);
        }
        return instance;
    }

    public LoadingDialog(Context context) {
        super(context, R.style.Dialog);
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_loading, null);
        setContentView(layout);
        loading = (ImageView) layout.findViewById(R.id.rotateloading);
        mProgressDrawable = new ProgressDrawable();
        mProgressDrawable.setColor(0xff666666);
        loading.setImageDrawable(mProgressDrawable);
        tvMsg = (TextView) layout.findViewById(R.id.tv_msg);
        // 设置window属性

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.dimAmount = 0; // 去背景遮盖

        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);

        showAnim();

//        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }
    private ProgressDrawable mProgressDrawable;
    public LoadingDialog(Context context, boolean cancelalable) {
        super(context, R.style.Dialog);
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_loading, null);
        setContentView(layout);
        loading = (ImageView) layout.findViewById(R.id.rotateloading);
        mProgressDrawable = new ProgressDrawable();
        mProgressDrawable.setColor(0xff666666);
        loading.setImageDrawable(mProgressDrawable);
        tvMsg = (TextView) layout.findViewById(R.id.tv_msg);
        // 设置window属性

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.dimAmount = 0; // 去背景遮盖

        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);

        showAnim();

        this.setCancelable(cancelalable);
        this.setCanceledOnTouchOutside(cancelalable);
    }

    public void showLoading() {
        showLoading(null);
    }

    private void showAnim() {
        mProgressDrawable.start();
//        if (Helper.isNotNull(loading) && Helper.isNull(anim)) {
//            anim = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.9f, 0, ResourceHelper.Dp2Px(50));
//            anim.setDuration(200);
//            anim.setRepeatCount(-1);
//            anim.setRepeatMode(ValueAnimator.REVERSE);
//            loading.setAnimation(anim);
//        }
//        if (Helper.isNotNull(anim)) {
//            anim.start();
//        }
    }

    public void showLoading(String msg) {
        Log.d(TAG, "try to show loading_default");
        if (!instance.isShowing()) {
            Log.d(TAG, "show loading_default success");
            if (Helper.isNotEmpty(msg)) {
                tvMsg.setText(msg);
            } else {
                tvMsg.setText(R.string.loading);
            }
            if (!((Activity) mContext).isFinishing()) {
                instance.show();
            }
        }
    }

    public void showLoading(int msgId) {
        Log.d(TAG, "try to show loading_default");
        if (!instance.isShowing()) {
            Log.d(TAG, "show loading_default success");
            if (msgId > 0) {
                tvMsg.setText(msgId);
            } else {
                tvMsg.setText(R.string.loading);
            }
            instance.show();
        }
    }

    public void hideLoading() {
        Log.d(TAG, "try to hide loading_default");
        if (instance.isShowing()) {
            Log.d(TAG, "hide loading_default success");

            if (Helper.isNotNull(anim)) {
                anim.cancel();
            }
            if (Helper.isNotNull(loading)) {
                loading.clearAnimation();
            }
            mProgressDrawable.stop();
            instance.dismiss();
        }
    }

}