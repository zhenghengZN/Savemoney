package wiget;

import android.content.Context;
import android.content.res.TypedArray;

import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import iconicfont.icon.*;
import iconicfont.IconicFontUtil;
import so.bubu.lib.base.BaseApplication;
import so.bubu.lib.helper.Helper;
import com.zhekouxingqiu.main.R;

/**
 * @author linhuan on 2016/10/24 下午2:16
 */
public class DownloadView extends RelativeLayout {

    private ImageView ivIcon;
    private TextView tvMaxTips, tvMinTips;
    private View vwLine;
    private Button btnDownload;

    public DownloadView(Context context) {
        this(context, null);
    }

    public DownloadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownloadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initData(context, attrs);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.download_view, this);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        tvMaxTips = (TextView) findViewById(R.id.tv_max_tips);
        tvMinTips = (TextView) findViewById(R.id.tv_min_tips);
        vwLine = findViewById(R.id.vw_line);
        btnDownload = (Button) findViewById(R.id.btn_download);
    }

    private void initData(Context context, AttributeSet attrs) {
        if (Helper.isNotNull(attrs)) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.download_type);

            // 设置内容
            String maxString = typedArray.getString(R.styleable.download_type_max_tips);
            if (Helper.isNotNull(tvMaxTips)) {
                tvMaxTips.setText(maxString);
            }

            String minString = typedArray.getString(R.styleable.download_type_min_tips);
            if (Helper.isNotNull(tvMinTips)) {
                tvMinTips.setText(minString);
            }

            boolean isHasBottom = typedArray.getBoolean(R.styleable.download_type_has_bottom, true);
            vwLine.setVisibility(isHasBottom ? View.VISIBLE : View.GONE);

        }
    }

    public void setTips(String maxTips, String minTips) {
        if (Helper.isNotNull(tvMaxTips)) {
            tvMaxTips.setText(maxTips);
        }

        if (Helper.isNotNull(tvMinTips)) {
            tvMinTips.setText(minTips);
        }
    }

    public void setBtnOnClick(OnClickListener btnOnClick) {
        if (Helper.isNotNull(btnDownload)) {
            btnDownload.setOnClickListener(btnOnClick);
        }
    }

    public void setIvIcon(Icon drawableId) {
        ivIcon.setBackground(IconicFontUtil.createIconicFontDrawable(BaseApplication.getInstance(), drawableId, BaseApplication.getInstance().getResources().getColor(R.color.colorPrimary)));
    }

    public void setIvIcon(int iconId) {
        ivIcon.setImageResource(iconId);
    }

}