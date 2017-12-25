package wiget;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;

import com.zhekouxingqiu.main.R;

/**
 * Created by zhengheng on 17/12/15.
 */
public class TimeCount extends CountDownTimer {
    private static final int TIME_TASCK = 1000;
    private Button button;
    private Context context;
    public TimeCount(Context context, long millisInFuture, Button view) {
        super(millisInFuture, TIME_TASCK);
        button = view;
        this.context = context;
    }

    @Override
    public void onFinish() {// 计时完毕
//        button.setTextColor(context.getResources().getColor(R.color.text_white));
        button.setBackgroundResource(R.drawable.btn_get_code_background_true);
        button.setText("获取验证码");
        button.setClickable(true);
    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程
//        button.setTextColor(context.getResources().getColor(R.color.home_item_count));
        button.setClickable(false);//防止重复点击
        button.setBackgroundResource(R.drawable.btn_get_code_background);
        button.setText("发送中("+millisUntilFinished / TIME_TASCK+")");
    }
}