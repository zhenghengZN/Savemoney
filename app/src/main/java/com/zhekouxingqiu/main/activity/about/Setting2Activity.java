package com.zhekouxingqiu.main.activity.about;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;


import common.SharePopMenu;
import common.base.TitleAppCompatActivity;
import iconicfont.IconicFontDrawable;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import share.SocialShareService;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.LogHelper;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.ToastHelper;
import utils.AVAnalyticsHelper;
import utils.CommonUtils;
import utils.DataCleanManager;
import utils.UIHelper;
import utils.UserHelper;
import utils.UserKey;

/**
 * 设置
 * Created by Auro on 15/10/23.
 */
public class Setting2Activity extends TitleAppCompatActivity {

    LinearLayout llWebsite;
    LinearLayout llWeibo;
    LinearLayout llWeixin;
    TextView tvVersion;
    View clickIcon1;
    View clickIcon2;
    View clickIcon3;
    View clickIcon4;
    private View mIconLayout;
    private ImageView mUserIcon;
    private TextView mUserName;
    private View clickIconClearCache;
    private View iconClearCache;
    private View iconPhone;
    private View iconWeiBoNum;
    private View iconWeiXinNum;
    private View iconMap;
    private View iconWeiXinGroup;
    private View iconQQauro;
    private View iconQQcity;
    private View iconWebsite;
    private View iconSinaWeibo;
    private View iconWeixinOpen;
    private View iconVersion;
    private View iconSupport;
    private View clickIconSupport;
    private View llSupport;
    private View llWeixinGroup;
    private View llqqAuro;
    private View llqqCity;
    private View clickIconWeixinGroup;
    private View clickIconQQauro;
    private View clickIconQQcity;
    private View clickIconShare;
    private View iconWeiXinCommunion, iconWeiXinCooperation, clickIconWeixinCooperation, clickIconWeixinCommunion, llWeixinCooperation, llWeixinCommunion;


    private SharePopMenu sharePopMenu;

    private SimpleTarget target = new SimpleTarget<Bitmap>(ResourceHelper.Dp2Px(65), ResourceHelper.Dp2Px(65)) {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            // do something with the bitmap
            // for demonstration purposes, let's just set it to an ImageView
            mUserIcon.setImageBitmap(bitmap);
        }
    };
    private View llClearCache;
    private TextView mMPhoneNum;
    private TextView mClickPhone;
    private View llPhone;
    private View llPhoneLayout;
    private View llShare;
    private View iconShare;

    /**
     * onCreateView:初始化界面
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting_two);
    }

    @Override
    protected void initView() {

        setBackClick();
        setTitle(getString(R.string.setting));
        setHideRight();

        llWebsite = findView(R.id.ll_website);
        llWeibo = findView(R.id.ll_weibo);
        llWeixin = findView(R.id.ll_weixin);
        tvVersion = findView(R.id.tv_version);
        llSupport = findView(R.id.ll_support);
        llSupport.setVisibility(View.GONE);
        llShare = findView(R.id.ll_share);
        llWeixinGroup = findView(R.id.ll_weixin_group);
        llWeixinCooperation = findView(R.id.ll_weixin_cooperation);
        llWeixinCommunion = findView(R.id.ll_weixin_communion);
        llqqAuro = findView(R.id.ll_qq_auro);
        llqqCity = findView(R.id.ll_qq_city);
        llClearCache = findView(R.id.ll_clear_cache);
        llPhone = findView(R.id.ll_phone);
        llPhoneLayout = findView(R.id.ll_bind_layout);
        mIconLayout = findView(R.id.icon_layout);
        mUserIcon = findView(R.id.img_icon);
        mUserName = findView(R.id.tv_user_name);
        mMPhoneNum = findView(R.id.tv_phone_num);
        mClickPhone = findView(R.id.v_click_phone);

        final Context context = this;
        //向右箭头
        setRightClickIcon(context);

        setIcon();

        refreshUser();

        llWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.CLOUD_WEBSITE);
                UIHelper.getInstance().openUrl(context, "http://www.bubu.so");
            }
        });

        llWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.SETTING_SINA);
                UIHelper.getInstance().openUrl(context, "weibo.com/stepscloud");
            }
        });
        llWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.WEIXING_PUBLIC_NUM);
                UIHelper.getInstance().openWeixinOpen(Setting2Activity.this);

            }
        });

        llSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.SUPPORT_FEEDBACK);
                UIHelper.getInstance().openSupport(Setting2Activity.this);
            }
        });

        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.RECOMMENDED_APP);

                if (Helper.isNull(sharePopMenu)) {
                    sharePopMenu = new SharePopMenu(Setting2Activity.this, onRecyclerViewItemClickListener);
                }
                sharePopMenu.showPopupWindow(v);
            }
        });

        llWeixinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.WE_CHAT_NUMBER);
                copy(SocialShareService.WEIXIN_LABEL, SocialShareService.WEIXIN_OPEN_ID, ResourceHelper.getString(R.string.weixin_group_copy));
            }
        });

        llWeixinCooperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.WE_CHAT_NUMBER);
                copy(SocialShareService.WEIXIN_LABEL, SocialShareService.WEIXIN_Cooperation_ID, ResourceHelper.getString(R.string.weixin_group_copy));
            }
        });

        llWeixinCommunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.WE_CHAT_NUMBER);
                copy(SocialShareService.WEIXIN_LABEL, SocialShareService.WEIXIN_Communion_ID, ResourceHelper.getString(R.string.weixin_group_copy));
            }
        });

        llqqAuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.AUSTRALIA_GROUP);
                copy(SocialShareService.QQ_LABEL, SocialShareService.QQ_AURO_NUM, ResourceHelper.getString(R.string.qq_group_copy));
            }
        });

        llqqCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.STEP_BY_STEP_GUIDE_GROUP);
                copy(SocialShareService.QQ_LABEL, SocialShareService.QQ_CITY_NUM, ResourceHelper.getString(R.string.qq_group_copy));
            }
        });

        llClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.CLEAR_CACHE);
                DataCleanManager.cleanInternalCache(Setting2Activity.this);
                DataCleanManager.cleanExternalCache(Setting2Activity.this);
                ToastHelper.showToast(ResourceHelper.getString(R.string.clear_cache_success));
            }
        });

        mClickPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CommonUtils.isSingleClick()) {
                    UIHelper.getInstance().openBindingPhoneActivity(Setting2Activity.this);
                }

            }
        });

    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            sharePopMenu.dismiss();
            String title = getString(R.string.share_app_title);
            String content = getString(R.string.share_app_content);

            switch (position) {

                case 0:
                    SocialShareService.getInstance().shareSina(Setting2Activity.this, title, content, "http://www.bubu.so", 1);
                    break;

                case 1:
                    SocialShareService.getInstance().shareWx(Setting2Activity.this, title, content, "http://www.bubu.so", 1);
                    break;

                case 2:
                    SocialShareService.getInstance().shareWxFriend(Setting2Activity.this, title, content, "http://www.bubu.so", 1);
                    break;

            }
        }
    };

    private void setUserData() {
        AVUser currentUser = AVUser.getCurrentUser();

        if (Helper.isNotEmpty(currentUser)) {
            String nickName = (String) currentUser.get(UserKey.USER_NICK_NAME);

            if (Helper.isNotEmpty(nickName)) {
                mUserName.setText(nickName);
            } else {
                mUserName.setText(AVUser.getCurrentUser().getUsername());
            }

            AVFile avFile = currentUser.getAVFile(UserKey.USER_ICON_FILE_KEY);
            mUserIcon.setImageResource(R.drawable.pho_user_head);
            if (avFile != null && Helper.isNotEmpty(avFile.getUrl())) {
                displayUserProfile(avFile.getUrl());
            } else {
                String profileUrl = (String) currentUser.get(UserKey.USER_PROFILE_URL);
                if (Helper.isNotEmpty(profileUrl)) {
                    displayUserProfile(profileUrl);
                }
            }
            if (Helper.isNotEmpty(currentUser.getMobilePhoneNumber())) {

                mMPhoneNum.setText(currentUser.getMobilePhoneNumber());
                setBtnBindState();
            } else {
                mMPhoneNum.setText("");
                setBtnUnBindState();
            }
        } else {
            mUserName.setText(ResourceHelper.getString(R.string.my_user_name));
            mMPhoneNum.setText("");
            setBtnUnBindState();
        }
    }

    private void refreshUser() {
        setUserData();
        refreshUserData();
    }

    private void displayUserProfile(String profile) {
        mUserIcon.setBackground(null);
        Glide.with(this.getApplicationContext())
                .load(profile)
                .asBitmap()
                .into(target);

        LogHelper.e(profile);
    }


    private void setBtnUnBindState() {
        mClickPhone.setText(R.string.setting_phone_unbind);
        mClickPhone.setBackgroundResource(R.drawable.btn_setting_unbind);
        mClickPhone.setTextColor(getResources().getColor(R.color.color_ffffff));
    }

    private void setBtnBindState() {
        mClickPhone.setText(R.string.setting_phone_bind);
        mClickPhone.setBackgroundResource(R.drawable.btn_setting_bind);
        mClickPhone.setTextColor(getResources().getColor(R.color.color_d2d2d2));
    }

    public void copy(String label, String text, String toastText) {
        ClipboardManager clipboardManager = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboardManager.setPrimaryClip(clip);
        ToastHelper.showToast(toastText);
        if (SocialShareService.WEIXIN_LABEL.equals(label)) {
            SocialShareService.getInstance().openWeixinApp(this);
        }

    }

    private void setIcon() {
        iconPhone = findView(R.id.icon_phone);
        iconWeiBoNum = findView(R.id.icon_weibo);
        iconWeiXinNum = findView(R.id.icon_weixin);
        iconMap = findView(R.id.icon_map);
        iconClearCache = findView(R.id.icon_clear_cache);
        iconWeiXinCommunion = findView(R.id.icon_weixin_communion);
        iconWeiXinCooperation = findView(R.id.icon_weixin_cooperation);
        iconWeiXinGroup = findView(R.id.icon_weixin_group);
        iconQQauro = findView(R.id.icon_qq_auro);
        iconQQcity = findView(R.id.icon_qq_city);
        iconWebsite = findView(R.id.icon_website);
        iconSinaWeibo = findView(R.id.icon_sina_weibo);
        iconWeixinOpen = findView(R.id.icon_weixin_open);
        iconVersion = findView(R.id.icon_version);
        iconSupport = findView(R.id.icon_support);
        iconShare = findView(R.id.icon_share);

        IconicFontDrawable iconicFont = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_CLEAR_CACHE, getResources().getColor(R.color.color_82cd6b));
        iconClearCache.setBackground(iconicFont);

        IconicFontDrawable iconicFontphone = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SETTING_PHONE, getResources().getColor(R.color.color_82cd6b));
        iconPhone.setBackground(iconicFontphone);

        IconicFontDrawable iconicFontWeibo = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SINA, getResources().getColor(R.color.color_82cd6b));
        iconWeiBoNum.setBackground(iconicFontWeibo);

        IconicFontDrawable iconicFontWeixinNum = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_WX, getResources().getColor(R.color.color_82cd6b));
        iconWeiXinNum.setBackground(iconicFontWeixinNum);

        IconicFontDrawable iconicFontMap = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_MAP, getResources().getColor(R.color.color_82cd6b));
        iconMap.setBackground(iconicFontMap);

        IconicFontDrawable iconicFontWeixinGroup = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_WX, getResources().getColor(R.color.color_82cd6b));
        iconWeiXinGroup.setBackground(iconicFontWeixinGroup);

        IconicFontDrawable iconicFontWeixinCommunion = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_WX, getResources().getColor(R.color.color_82cd6b));
        iconWeiXinCommunion.setBackground(iconicFontWeixinCommunion);

        IconicFontDrawable iconicFontWeixinCooperation = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_WX, getResources().getColor(R.color.color_82cd6b));
        iconWeiXinCooperation.setBackground(iconicFontWeixinCooperation);

        IconicFontDrawable iconicFontQQauro = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_QQ, getResources().getColor(R.color.color_82cd6b));
        iconQQauro.setBackground(iconicFontQQauro);

        IconicFontDrawable iconicFontQQcity = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_QQ, getResources().getColor(R.color.color_82cd6b));
        iconQQcity.setBackground(iconicFontQQcity);

        IconicFontDrawable iconicFontWebsite = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_WEB, getResources().getColor(R.color.color_82cd6b));
        iconWebsite.setBackground(iconicFontWebsite);

        IconicFontDrawable iconicFontSinaWeibo = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SINA, getResources().getColor(R.color.color_82cd6b));
        iconSinaWeibo.setBackground(iconicFontSinaWeibo);

        IconicFontDrawable iconicFontWeixinOpen = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_WX, getResources().getColor(R.color.color_82cd6b));
        iconWeixinOpen.setBackground(iconicFontWeixinOpen);

        IconicFontDrawable iconicFontVersion = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_VERSION, getResources().getColor(R.color.color_82cd6b));
        iconVersion.setBackground(iconicFontVersion);

        IconicFontDrawable iconicFontSupport = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SUPPORT, getResources().getColor(R.color.color_82cd6b));
        iconSupport.setBackground(iconicFontSupport);

        IconicFontDrawable iconicFontShare = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SHARE_FRIEND, getResources().getColor(R.color.color_82cd6b));
        iconShare.setBackground(iconicFontShare);

    }

    private void setRightClickIcon(Context context) {
        clickIcon1 = findView(R.id.v_click_icon1);
        clickIcon2 = findView(R.id.v_click_icon2);
        clickIcon3 = findView(R.id.v_click_icon3);
        clickIcon4 = findView(R.id.v_click_icon4);
        clickIconClearCache = findView(R.id.v_click_clear_cache);
        clickIconSupport = findView(R.id.v_click_support);
        clickIconWeixinGroup = findView(R.id.v_click_weixin_group);
        clickIconWeixinCooperation = findView(R.id.v_click_weixin_cooperation);
        clickIconWeixinCommunion = findView(R.id.v_click_weixin_communion);
        clickIconQQauro = findView(R.id.v_click_qq_auro);
        clickIconQQcity = findView(R.id.v_click_qq_city);
        clickIconShare = findView(R.id.v_click_share);

        IconicFontDrawable iconRight = new IconicFontDrawable(context, CityGuideIcon.ICON_RIGHT);
        iconRight.setIconColor(context.getResources().getColor(R.color.color_999999));
        clickIcon1.setBackground(iconRight);
        clickIcon2.setBackground(iconRight);
        clickIcon3.setBackground(iconRight);
        clickIcon4.setBackground(iconRight);
        clickIconSupport.setBackground(iconRight);
        clickIconClearCache.setBackground(iconRight);
        clickIconWeixinGroup.setBackground(iconRight);
        clickIconWeixinCooperation.setBackground(iconRight);
        clickIconWeixinCommunion.setBackground(iconRight);
        clickIconQQauro.setBackground(iconRight);
        clickIconQQcity.setBackground(iconRight);
        clickIconShare.setBackground(iconRight);
    }

    private void refreshUserData() {
        if (UserHelper.getInstance().isAlreadlyLoginIn()) {
            if (Helper.isEmpty(UserHelper.getInstance().getCurrentUser().getMobilePhoneNumber())) {

                llPhone.setVisibility(View.GONE);
                llPhoneLayout.setVisibility(View.GONE);
//                mIconLayout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        UIHelper.getInstance().openUserPanel(Setting2Activity.this);
//                    }
//                });
            } else {
                llPhone.setVisibility(View.GONE);
                llPhoneLayout.setVisibility(View.GONE);
            }

        } else {
            mIconLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIHelper.getInstance().openStartActivity(Setting2Activity.this);
                }
            });
            llPhone.setVisibility(View.GONE);
            llPhoneLayout.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshUser();
    }

    @Override
    protected void initData() {
        try {
            tvVersion.setText(CommonUtils.getInstance().getVersionName(this));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * function: 后退处理
     *
     * @param keyCode
     * @param event
     * @author:linhuan 2014年8月5日 下午7:59:01
     */
    @Override
    protected void doBack(int keyCode, KeyEvent event) {

        NavigationHelper.finish(this, RESULT_OK, null);
    }


}
