package wiget;

import android.content.Context;
import android.content.res.TypedArray;
import iconicfont.icon.*;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import so.bubu.lib.base.BaseApplication;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.ResourceHelper;
import com.zhekouxingqiu.main.R;


/**
 * @author linhuan on 2016/10/20 下午1:13
 */
public class EnterMessageView extends RelativeLayout {

    private ImageView ivIcon, ivback;
    private TextView tvHint, tvContent;
    private EditText etContent;
    private View vwLine;

    public EnterMessageView(Context context) {
        this(context, null);
    }

    public EnterMessageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EnterMessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initData(context, attrs);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.custom_enter_message, this);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        tvHint = (TextView) findViewById(R.id.tv_hint);
        tvContent = (TextView) findViewById(R.id.tv_content);
        etContent = (EditText) findViewById(R.id.et_content);
        vwLine = findViewById(R.id.vw_line);
        ivback = (ImageView) findViewById(R.id.iv_back);
    }

    private void initData(Context context, AttributeSet attrs) {
        if (Helper.isNotNull(attrs)) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.custom_enter_message);

            // 设置内容
            String hintStr = typedArray.getString(R.styleable.custom_enter_message_hint);
            if (Helper.isNotNull(hintStr)) {
                tvHint.setText(hintStr);
            }

            String contentStr = typedArray.getString(R.styleable.custom_enter_message_text);
            if (Helper.isNotNull(contentStr)) {
                tvContent.setText(contentStr);
            }

            boolean isShowContent = typedArray.getBoolean(R.styleable.custom_enter_message_is_show_content, true);
            if (isShowContent) {
                showContentVisibility(View.VISIBLE, View.GONE);
            } else {
                showContentVisibility(View.GONE, View.VISIBLE);
            }

            boolean isHasBottom = typedArray.getBoolean(R.styleable.custom_enter_message_is_has_bottom, true);
            vwLine.setVisibility(isHasBottom ? View.VISIBLE : View.GONE);

        }
    }

    public void setGoBack() {
        ivback.getLayoutParams().width = ResourceHelper.Dp2Px(14);
        ivback.getLayoutParams().height = ResourceHelper.Dp2Px(14);
        ((LayoutParams) ivback.getLayoutParams()).leftMargin = ResourceHelper.Dp2Px(7);
        ivback.setBackground(IconicFontUtil.createIconicFontDrawable(BaseApplication.getInstance().getApplicationContext(), CityGuideIcon.ICON_GO, R.color.color_add_appointment_list_no));
    }

    private void showContentVisibility(int tvVisibility, int etVisibility) {
        tvContent.setVisibility(tvVisibility);
        etContent.setVisibility(etVisibility);
    }

    public void setIvIcon(Icon drawableId) {
        setIvIcon(drawableId, R.color.color_add_appointment_list);
    }

    public void setIvIcon(Icon drawableId, int colorId) {
        ivIcon.setBackground(IconicFontUtil.createIconicFontDrawable(BaseApplication.getInstance(), drawableId, colorId));
    }

    public void setEtInputTypePhone() {
        etContent.setInputType(InputType.TYPE_CLASS_PHONE);
    }

    public void setShowContentStyle(final OnClickSelect onClickSelect, final int type) {
        tvContent.setTextColor(BaseApplication.getInstance().getResources().getColor(R.color.colorPrimary));
//        tvContent.setTextSize(14);
        if (Helper.isNotNull(onClickSelect)) {
            tvContent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickSelect.clickSelect(EnterMessageView.this, type);
                }
            });
        }
    }

    public void setOnClick(final OnClickSelect onClickSelect) {
        if (Helper.isNotNull(onClickSelect)) {
            tvContent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickSelect.clickSelect(EnterMessageView.this, 0);
                }
            });
        }
    }

    public void setLineVisibility(boolean isHasBottom) {
        vwLine.setVisibility(isHasBottom ? View.VISIBLE : View.GONE);
    }

    public void setContentVisibility(boolean isShowContent) {
        if (isShowContent) {
            showContentVisibility(View.VISIBLE, View.GONE);
        } else {
            showContentVisibility(View.GONE, View.VISIBLE);
        }
    }

    public void setHint(int hintStrId) {
        tvHint.setText(getContext().getString(hintStrId));
    }

    public void setHint(String hintStr) {
        tvHint.setText(hintStr);
    }

    public String getTvContent() {
        return tvContent.getText().toString();
    }

    public String getEditContent() {
        return etContent.getText().toString();
    }

    public void setEtContent(String contentStr) {
        etContent.setText(contentStr);
        etContent.setSelection(contentStr.length());
    }

    public void setTvContent(String contentStr) {
        tvContent.setText(contentStr);
    }

    public void setGameOver() {
        tvHint.setTextColor(BaseApplication.getInstance().getResources().getColor(R.color.color_line_bg));
        tvContent.setTextColor(BaseApplication.getInstance().getResources().getColor(R.color.color_line_bg));
    }

    public interface OnClickSelect {

        void clickSelect(View v, int type);

    }

}
