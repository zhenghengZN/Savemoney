package utils.photopicker.widget.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.NetWorkHelper;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.ToastHelper;

import static android.R.string.no;
import static android.R.string.yes;

/**
 * Created by wneng on 16/9/1.
 */

public class SexDialog extends Dialog {

    private SexDialogListener mSexDialogListener;
    private TextView tvChangeNo, tvChangeYes;
    private RadioGroup mRgSex;
    private RadioButton mRbMan;
    private RadioButton mRbwoMan;

    public SexDialog(Context context) {
        super(context);
    }

    public void setSexDialogListener(SexDialogListener sexDialogListener) {
        this.mSexDialogListener = sexDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_change_sex);

        getWindow().setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = ResourceHelper.Dp2Px(122 * 2 + 29 + 20 * 2);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);

        tvChangeNo = (TextView) findViewById(R.id.tv_change_no);
        tvChangeYes = (TextView) findViewById(R.id.tv_change_yes);
        mRgSex = (RadioGroup) findViewById(R.id.rg_sex);
        mRbMan = (RadioButton) findViewById(R.id.rb_sex_man);
        mRbwoMan = (RadioButton) findViewById(R.id.rb_sex_woman);

        tvChangeNo.setText(no);
        tvChangeYes.setText(yes);

        tvChangeNo.setOnClickListener(onClickListener);
        tvChangeYes.setOnClickListener(onClickListener);
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (NetWorkHelper.isNetworkAvailable()) {
                switch (v.getId()) {

                    case R.id.tv_change_no:
                        if (Helper.isNotNull(mSexDialogListener)) {
                            mSexDialogListener.changeNo();
                        }
                        dismiss();
                        break;

                    case R.id.tv_change_yes:
                        if (Helper.isNotNull(mSexDialogListener)) {

                            int checkedRadioButtonId = mRgSex.getCheckedRadioButtonId();

                            String sex ;
                            if (checkedRadioButtonId == R.id.rb_sex_man) {
                                sex = mRbMan.getText().toString().trim();
                            } else if (checkedRadioButtonId == R.id.rb_sex_woman) {

                                sex = mRbwoMan.getText().toString().trim();
                            }else{
                                sex = ResourceHelper.getString(R.string.man);
                            }
                            mSexDialogListener.changeYes(sex);

                        }
                        dismiss();
                        break;

                }
            } else {
                ToastHelper.showToast(R.string.text_net_no);
            }
        }
    };


    public interface SexDialogListener {

        void changeNo();

        void changeYes(String content);

    }


}
