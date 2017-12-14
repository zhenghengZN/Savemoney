package adapter;

import android.view.View;

import com.avos.avoscloud.AVException;

import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.ToastHelper;

import utils.CallFunctionBackListener;

/**
 * @author linhuan on 16/6/24 下午7:12
 */
public class BaseCallFunctionBackListener implements CallFunctionBackListener {

    private View view;

    public BaseCallFunctionBackListener() {

    }

    public BaseCallFunctionBackListener(View view) {
        this.view = view;
    }


    @Override
    public void callSuccess(boolean result, String jsonstr) {

    }

    @Override
    public void callFailure(int type, AVException e) {
        ToastHelper.showToast(R.string.text_net_no);
    }

}
