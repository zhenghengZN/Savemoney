package utils.photopicker.widget.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.zhekouxingqiu.main.R;
import so.bubu.lib.base.BaseApplication;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.NetWorkHelper;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.ToastHelper;
import so.bubu.lib.wiget.ClearEditText;

/**
 * 修改内容弹出框
 *
 * @author linhuan on 16/7/1 下午5:50
 */
public class CommentChangeContentDialog extends Dialog {

    private boolean isClose = true;
    private String hint;
    private String content, no, yes;

    private ClearEditText tvShowContent;
    private TextView tvChangeNo, tvChangeYes;

    private CommentChangeContentInterface commentChangeInterface;

    public CommentChangeContentDialog(Context context) {
        super(context);
        init(context);
    }

    public CommentChangeContentDialog(Context context, String hint) {
        super(context);
        init(context);
        this.hint = hint;
    }

    public CommentChangeContentDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected CommentChangeContentDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {

    }

    public void setCommentChangeInterface(CommentChangeContentInterface commentChangeInterface) {
        this.commentChangeInterface = commentChangeInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_commen_change_content);

        getWindow().setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = ResourceHelper.Dp2Px(122 * 2 + 29 + 20 * 2);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);

        tvShowContent = (ClearEditText) findViewById(R.id.tv_show_content);
        if (Helper.isNotEmpty(hint)) {
            tvShowContent.setHint(hint);
        }
        tvChangeNo = (TextView) findViewById(R.id.tv_change_no);
        tvChangeYes = (TextView) findViewById(R.id.tv_change_yes);

        tvShowContent.setText(content);
        tvChangeNo.setText(no);
        tvChangeYes.setText(yes);

        tvChangeNo.setOnClickListener(onClickListener);
        tvChangeYes.setOnClickListener(onClickListener);
    }

    public CommentChangeContentDialog showContent(int noId, int yesId) {
        return showContent(BaseApplication.getInstance().getString(noId), BaseApplication.getInstance().getString(yesId));
    }

    public CommentChangeContentDialog showContent(String no, String yes) {
        this.no = no;
        this.yes = yes;

        if (Helper.isNotNull(tvChangeNo)) {
            tvChangeNo.setText(no);
        }
        if (Helper.isNotNull(tvChangeYes)) {
            tvChangeYes.setText(yes);
        }
        return this;
    }

    public CommentChangeContentDialog showContent(int hintId, int noId, int yesId) {
        return showContent(BaseApplication.getInstance().getString(hintId), BaseApplication.getInstance().getString(noId), BaseApplication.getInstance().getString(yesId));
    }

    public CommentChangeContentDialog showContent(String hint, String no, String yes) {
        this.no = no;
        this.yes = yes;
        this.hint = hint;

        if (Helper.isNotNull(tvChangeNo)) {
            tvChangeNo.setText(no);
        }
        if (Helper.isNotNull(tvChangeYes)) {
            tvChangeYes.setText(yes);
        }
        if (Helper.isNotNull(tvShowContent)) {
            tvShowContent.setHint(hint);
            tvShowContent.setText("");
        }
        return this;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (NetWorkHelper.isNetworkAvailable()) {
                switch (v.getId()) {

                    case R.id.tv_change_no:
                        if (Helper.isNotNull(commentChangeInterface)) {
                            commentChangeInterface.changeNo();
                        }
                        dismiss();
                        break;

                    case R.id.tv_change_yes:
                        if (Helper.isNotNull(commentChangeInterface)) {
                            String content = tvShowContent.getText().toString();
                            if (Helper.isEmpty(tvShowContent)) {
                                ToastHelper.showToast(R.string.text_change_user_name_no_empty);
                                return;
                            }
                            commentChangeInterface.changeYes(content);
                        }
                        if (isClose) {
                            dismiss();
                        }
                        break;

                }
            } else {
                ToastHelper.showToast(R.string.text_net_no);
            }
        }
    };

    public interface CommentChangeContentInterface {

        void changeNo();

        void changeYes(String content);

    }

}
