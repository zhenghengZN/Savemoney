package com.zhekouxingqiu.main.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import android.widget.TextView;

import common.base.TitleFragment;
import iconicfont.icon.CityGuideIcon;
import so.bubu.lib.helper.NavigationHelper;
import com.zhekouxingqiu.main.R;
import com.zhekouxingqiu.main.activity.about.AboutAuthorActivity;
import utils.UIHelper;
import wiget.DownloadView;
import wiget.EnterMessageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingIndependentFragment extends TitleFragment {


    public SettingIndependentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }


    @Override
    protected void initView() {
        super.initView();

       // setLeftIcon(CityGuideIcon.ICON_BACK, getResources().getColor(R.color.color_ffffff));
        setCenterContent(R.string.title_activity_setting);

        ((DownloadView) findViewById(R.id.dlv_step)).setIvIcon(R.drawable.icon_step);
        ((DownloadView) findViewById(R.id.dlv_step)).setBtnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadStep();
            }
        });

        ((DownloadView) findViewById(R.id.dlv_bubu)).setIvIcon(R.drawable.icon_anzac);
        ((DownloadView) findViewById(R.id.dlv_bubu)).setBtnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadBubu();
            }
        });

        ((EnterMessageView) findViewById(R.id.emv_wechat)).setIvIcon(CityGuideIcon.ICON_WX, getResources().getColor(R.color.colorPrimary));
        ((EnterMessageView) findViewById(R.id.emv_wechat)).setGoBack();
        ((EnterMessageView) findViewById(R.id.emv_wechat)).setShowContentStyle(new EnterMessageView.OnClickSelect() {
            @Override
            public void clickSelect(View v, int type) {
                attentionWechat();
            }
        }, 0);

        ((EnterMessageView) findViewById(R.id.emv_web)).setIvIcon(CityGuideIcon.ICON_WEB_NEW, getResources().getColor(R.color.color_7faaf1));
        ((EnterMessageView) findViewById(R.id.emv_web)).setGoBack();
        ((EnterMessageView) findViewById(R.id.emv_web)).setShowContentStyle(new EnterMessageView.OnClickSelect() {
            @Override
            public void clickSelect(View v, int type) {
                webWechat();
            }
        }, 0);

        ((EnterMessageView) findViewById(R.id.emv_zhihu)).setIvIcon(CityGuideIcon.ICON_ZHIHU, getResources().getColor(R.color.color_4fb5ef));
        ((EnterMessageView) findViewById(R.id.emv_zhihu)).setGoBack();
        ((EnterMessageView) findViewById(R.id.emv_zhihu)).setShowContentStyle(new EnterMessageView.OnClickSelect() {
            @Override
            public void clickSelect(View v, int type) {
                attentionZhihu();
            }
        }, 0);


        ((EnterMessageView) findViewById(R.id.emv_author)).setIvIcon(CityGuideIcon.ICON_AUTHOR_NEW, getResources().getColor(R.color.color_ff8cc8));
        ((EnterMessageView) findViewById(R.id.emv_author)).setGoBack();
        findViewById(R.id.emv_author).setVisibility(View.VISIBLE);
        findViewById(R.id.emv_author).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationHelper.slideActivity(getActivity(), AboutAuthorActivity.class, null, false);
            }
        });

    }

    private void downloadStep() {
        NavigationHelper.openBrowse("", getActivity());
    }

    private void downloadBubu() {
        NavigationHelper.openBrowse("", getActivity());
    }

    private void attentionWechat() {
        UIHelper.getInstance().openPublic(getActivity());
    }

    private void webWechat() {
        UIHelper.getInstance().openUrl(getActivity(), getString(R.string.title_setting_web_url));
    }

    private void attentionSina() {
        UIHelper.getInstance().openUrl(getActivity(), getString(R.string.title_setting_web_sina_url));
    }

    private void attentionZhihu() {
        UIHelper.getInstance().openUrl(getActivity(), getString(R.string.title_setting_web_zhihu_url));
    }

//
//    private void doBack(int keyCode, KeyEvent event) {
//        NavigationHelper.finish(getActivity(), -1, null);
//    }

    private void setCenterContent(int stringId) {
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setVisibility(View.VISIBLE);
        title.setText(stringId);

    }
//
//    private void setLeftIcon(Icon drawableId, int colorId) {
//        findViewById(R.id.fl_left).setVisibility(View.VISIBLE);
////        findViewById(R.id.iv_left).setBackground(IconicFontUtil.createIconicFont(drawableId, colorId));
//        findViewById(R.id.fl_left).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                leftEvent(v);
//            }
//        });
//    }

//
//    private void leftEvent(View v) {
//        doBack(-1, null);
//    }


//    private void setTextviewText(int viewId, int stringId) {
//        setTextviewText(viewId, getString(stringId));
//    }

}
