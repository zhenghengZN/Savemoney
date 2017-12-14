//package so.bubu.Coupon.AliTrade.activity.My;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.design.widget.AppBarLayout;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.alibaba.fastjson.JSON;
//import com.avos.avoscloud.AVException;
//import com.avos.avoscloud.AVFile;
//import com.avos.avoscloud.AVObject;
//import com.avos.avoscloud.AVUser;
//import com.avos.avoscloud.RefreshCallback;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.animation.GlideAnimation;
//import com.bumptech.glide.request.target.SimpleTarget;
//
//import com.squareup.picasso.Picasso;
//import com.umeng.socialize.sso.UMSsoHandler;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Map;
//
//import bean.UserRespBean;
//import common.base.TitleAppCompatActivity;
//import iconicfont.IconicFontDrawable;
//import iconicfont.IconicFontUtil;
//import iconicfont.icon.CityGuideIcon;
//import so.bubu.Coupon.AliTrade.R;
//import so.bubu.lib.base.recyclerview.recyclerView.AppBarStateChangeListener;
//import so.bubu.lib.helper.Helper;
//import so.bubu.lib.helper.InputMethodHelper;
//import so.bubu.lib.helper.LogHelper;
//import so.bubu.lib.helper.NavigationHelper;
//import so.bubu.lib.helper.ResourceHelper;
//import so.bubu.lib.helper.ScreenUtils;
//import so.bubu.lib.helper.StatusBarUtil;
//import so.bubu.lib.helper.ToastHelper;
//import utils.UserHelper;
//import wiget.CircleImageView;
//
//import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
//import static com.squareup.picasso.MemoryPolicy.NO_STORE;
//import static so.bubu.Coupon.AliTrade.R.id.ll_change_password;
//
///**
// * 用户设置个人信息页面
// * Created by wneng on 16/7/12.
// */
//public class UserActivity extends TitleAppCompatActivity {
//
//    private AppBarLayout mAppBarLayout;
//    private int head_height;
//    private ImageView mImgBackgroup;
//
//    private TextView mUserName;
//    private CircleImageView mUserIcon;
//
//    private TextView mTitle;
//    private ImageView mBtnBack;
//    private Button btnLogout;
//
//    private TextView tvName;
//
//    private TextView tvMail;
//
//    private View clickIcon3;
//
//    private CircleImageView ivFace;
//
//    private LinearLayout llChangePassword;
//
//    private LinearLayout llMail;
//
//    private static UserActivity SHARED_INSTANCE;
//    private View mUserLayout;
//    private View mIconUserName;
//    private View mIconEmail;
//    private View mIconChangePwd;
//    private View mIconNickName;
//
//    private CommentChangeContentDialog commentChangeContentDialog;
//    private View mIconUserEdit;
//    private UserRespBean mUserRespBean;
//    private TextView mTvFolloweeCount;
//    private TextView mTvFollowerCount;
//    private View mBtnEditPerson;
//    private TextView mTvUserDesc;
//    private View llIconLayout;
//    private CommentChangeContentDialog mEditProfileDialog;
//    private View mFolloweeLayout;
//    private View mFollowerLayout;
//    private TextView mNickName;
//    private TextView tvSex;
//    private TextView tvBirthday;
//    private TextView tvLocation;
//    private SexDialog mSexDialog;
//    private TimePickerView mBirthdayTimePicker;
//
//    private TextView mMPhoneNum;
//    private TextView mClickPhone, vWeixing, vSina, vClickMail;
//
//
//    public static UserActivity getSharedInstance(){
//        return SHARED_INSTANCE;
//    }
//
//    private SimpleTarget target = new SimpleTarget<Bitmap>(ResourceHelper.Dp2Px(65), ResourceHelper.Dp2Px(65)) {
//        @Override
//        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
//            ivFace.setImageBitmap( bitmap );
//        }
//    };
//
//    /**
//     * onCreateView:初始化界面
//     */
//    @Override
//    protected void onCreateView(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_user_info);
//
//        mTvFolloweeCount = findView(R.id.user_followee_count);
//        mTvFollowerCount = findView(R.id.user_follower_count);
//        mTvUserDesc = findView(R.id.tv_user_desc);
//        requestUserData();
//        head_height = StatusBarUtil.getStatusBarHeight(this);
//        SHARED_INSTANCE = this;
//    }
//
//    /**
//     *  获取用户信息
//     */
//    private void requestUserData() {
//        UserNetUtil.getInstance().getCurrentUserByNet(new CallFunctionBackListener() {
//            @Override
//            public void callSuccess(boolean result, String jsonstr) {
//
//                if (result) {
//                    if (Helper.isNotEmpty(jsonstr)) {
//                        mUserRespBean = JSON.parseObject(jsonstr, UserRespBean.class);
////                        CommonMethod.changeUserIcon((ImageView) findViewById(R.id.iv_level), mUserRespBean.getIsOfficial(), mUserRespBean.getTalentLevel());
//                        mTvFolloweeCount.setText(mUserRespBean.getFolloweeCount() > 0 ? String.valueOf(mUserRespBean.getFolloweeCount()) : "0");
//                        mTvFollowerCount.setText(mUserRespBean.getFollowerCount() > 0 ? String.valueOf(mUserRespBean.getFollowerCount()) : "0");
//                        mTvUserDesc.setText(Helper.isNotEmpty(mUserRespBean.getPersonalSigniture()) ? mUserRespBean.getPersonalSigniture() : ResourceHelper.getString(R.string.user_desc));
//                    }
//
//                }
//            }
//
//            @Override
//            public void callFailure(int type, AVException e) {
//
//                if (e != null) {
//                    LogHelper.w(e.getLocalizedMessage());
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void initView() {
//        super.initView();
//
//        initTitleBar();
//        initHeadLayout();
//
//        initFontIcon();
//
//        mUserIcon = findView(R.id.iv_user_face);
//        mUserName = findView(R.id.tv_user_name);
//        mNickName = findView(R.id.tv_nickname);
//        tvSex= findView(R.id.tv_sex);
//        tvBirthday = findView(R.id.tv_birthday);
//        tvLocation = findView(R.id.tv_location);
//
//        btnLogout = findView(R.id.btn_logout);
//        tvName = findView(R.id.tv_name);
//        tvMail = findView(R.id.tv_mail);
//        clickIcon3 = findView(R.id.v_click_icon3);
//        ivFace = findView(R.id.iv_user_face);
//        llChangePassword = findView(ll_change_password);
//        llMail = findView(R.id.ll_mail);
//        mUserLayout = findView(R.id.frame_layout);
//        llIconLayout = findView(R.id.ll_container);
//        initRightIcon();
//
//        mBtnEditPerson = findView(R.id.btn_edit_person);
//        mFolloweeLayout = findView(R.id.ll_followee_layout);
//        mFollowerLayout = findView(R.id.ll_follower_layout);
//
//        mMPhoneNum = findView(R.id.tv_phone_num);
//        mClickPhone = findView(R.id.v_click_phone);
//
//        vWeixing = findView(R.id.v_weixing);
//        vSina = findView(R.id.v_sina);
//        vClickMail = findView(R.id.v_click_mail);
//
//        initEvent();
////        initUserLayout();
//
////        if (AppConfig.IS_DISNEY) {
////            findViewById(R.id.vw_setting).setVisibility(View.VISIBLE);
////            findViewById(R.id.ll_setting).setVisibility(View.VISIBLE);
////            findViewById(R.id.ll_setting).setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.SETTING);
////                    NavigationHelper.slideActivity(UserActivity.this, Setting2Activity.class, null, false);
////                }
////            });
////        } else {
//            findViewById(R.id.vw_setting).setVisibility(View.GONE);
//            findViewById(R.id.ll_setting).setVisibility(View.GONE);
////        }
//
//        vClickMail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (CommonUtils.isSingleClick()) {
//                    AVUser currentUser = AVUser.getCurrentUser();
//                    if (Helper.isNotEmpty(currentUser)) {
//                        if (currentUser.getBoolean(UserKey.USER_EMAIL_VERIFIED)) {
//
//                        } else {
//                            if (Helper.isNull(commentChangeContentDialog)) {
//                                commentChangeContentDialog = new CommentChangeContentDialog(UserActivity.this);
//                            }
//                            commentChangeContentDialog.setCommentChangeInterface(new CommentChangeContentDialog.CommentChangeContentInterface() {
//                                @Override
//                                public void changeNo() {
//
//                                }
//
//                                @Override
//                                public void changeYes(String content) {
//                                    if (content.isEmpty() || !StringUtils.isEmail(content)) {
//                                        ToastHelper.showToast(R.string.error_mail);
//                                        return;
//                                    }
//                                    BandingHelper.getInstance().bindEmail(content, new CallFunctionBackListener() {
//                                        @Override
//                                        public void callSuccess(boolean result, String jsonstr) {
//                                            if (result) {
//                                                ToastHelper.showToast(R.string.title_send_email_yes);
//                                            }
//                                            commentChangeContentDialog.dismiss();
//                                        }
//
//                                        @Override
//                                        public void callFailure(int type, AVException e) {
//                                            if (Helper.isNotEmpty(e) && Helper.isNotEmpty(e.getMessage())) {
//                                                ErrorBean errorBean = JSON.parseObject(e.getMessage(), ErrorBean.class);
//                                                if (Helper.isNotEmpty(errorBean) && Helper.isNotEmpty(errorBean.getError())) {
//                                                    ToastHelper.showToast(errorBean.getError());
//                                                } else {
//                                                    ToastHelper.showToast(R.string.title_send_email);
//                                                }
//                                            } else {
//                                                ToastHelper.showToast(R.string.title_send_email);
//                                            }
//                                        }
//                                    });
//                                }
//                            });
//                            commentChangeContentDialog.setClose(false);
//                            commentChangeContentDialog.showContent(R.string.text_change_email, R.string.text_change_user_name_no_banding, R.string.picker_yes).show();
//                        }
//                    }
//                }
//            }
//        });
//
//        findViewById(R.id.icon_sina).setBackground(IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SINA, getResources().getColor(R.color.color_e79369)));
//        vSina.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (CommonUtils.isSingleClick()) {
//                    AVUser currentUser = AVUser.getCurrentUser();
//                    Map<String, String> authData = (Map<String, String>) currentUser.get(UserKey.USER_AUTH_DATA);
//                    if (Helper.isNotEmpty(authData)) {
//                        if (authData.containsKey(AVUser.AVThirdPartyUserAuth.SNS_SINA_WEIBO)) {
//
//                        } else {
//                            SocialShareService.getInstance().sinaOAuthBanding(UserActivity.this, bandingInterface);
//                        }
//                    }
//                }
//            }
//        });
//
//        findViewById(R.id.icon_weixing).setBackground(IconicFontUtil.createIconicFont(CityGuideIcon.ICON_WX, getResources().getColor(R.color.color_82cd6b)));
//        vWeixing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (CommonUtils.isSingleClick()) {
//                    AVUser currentUser = AVUser.getCurrentUser();
//                    Map<String, String> authData = (Map<String, String>) currentUser.get(UserKey.USER_AUTH_DATA);
//                    if (Helper.isNotEmpty(authData)) {
//                        if (authData.containsKey(AVUser.AVThirdPartyUserAuth.SNS_TENCENT_WEIXIN)) {
//
//                        } else {
//                            SocialShareService.getInstance().wxOAuthBanding(UserActivity.this, bandingInterface);
//                        }
//                    }
//                }
//            }
//        });
//
//        findViewById(R.id.icon_phone).setBackground(IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SETTING_PHONE, getResources().getColor(R.color.color_82cd6b)));
//        mClickPhone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (CommonUtils.isSingleClick()) {
//                    AVUser currentUser = AVUser.getCurrentUser();
//                    if (Helper.isNotEmpty(currentUser)) {
//                        if (currentUser.getBoolean(UserKey.USER_MOBILE_PHONE_VERIFIED)) {
//
//                        } else {
//                            UIHelper.getInstance().openBindingPhoneActivity(UserActivity.this);
//                        }
//                    }
//                }
//            }
//        });
//
//        if (AppConfig.IS_USER) {
//            findViewById(R.id.ll_weixing).setVisibility(View.GONE);
//            findViewById(R.id.vw_weixing).setVisibility(View.GONE);
//            findViewById(R.id.ll_sina).setVisibility(View.GONE);
//            findViewById(R.id.vw_sina).setVisibility(View.GONE);
//        }
//
//    }
//
//    private SocialShareService.BandingInterface bandingInterface = new SocialShareService.BandingInterface() {
//        @Override
//        public void bangingSucc() {
//            initUserLayout();
//        }
//
//        @Override
//        public void bangingFail() {}
//    };
//
//    private void initRightIcon() {
//        IconicFontDrawable iconRight = new IconicFontDrawable(this, CityGuideIcon.ICON_RIGHT);
//        iconRight.setIconColor(getResources().getColor(R.color.color_999999));
//        clickIcon3.setBackground(iconRight);
//        findViewById(R.id.v_click_icon_setting).setBackground(iconRight);
//        findViewById(R.id.v_click_icon1).setBackground(iconRight);
//        findViewById(R.id.v_click_icon_nickname).setBackground(iconRight);
//        findViewById(R.id.v_click_icon_sex).setBackground(iconRight);
//        findViewById(R.id.v_click_icon_birthday).setBackground(iconRight);
//        findViewById(R.id.v_click_icon_location).setBackground(iconRight);
//    }
//
//    private void initFontIcon() {
//
//        mIconUserName = findView(R.id.icon_username);
//        mIconNickName = findView(R.id.icon_nickname);
//        mIconEmail = findView(R.id.icon_email);
//        mIconChangePwd = findView(R.id.icon_change_pwd);
//        mIconUserEdit = findView(R.id.icon_user_edit);
//        View mIconSex = findView(R.id.icon_sex);
//        View mIconBirthday = findView(R.id.icon_birthday);
//        View mIconLocation = findView(R.id.icon_location);
//
//        IconicFontDrawable iconicFont = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_USER_NAME, getResources().getColor(R.color.color_e79369));
//        IconicFontDrawable iconicFont1 = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SUPPORT, getResources().getColor(R.color.color_e8be4e));
//        IconicFontDrawable iconicFont2 = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_CHANGE_PWD, getResources().getColor(R.color.color_ffbcc8));
//        IconicFontDrawable iconicFont3 = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_PUBLISH_POST, getResources().getColor(R.color.color_ffffff));
//        IconicFontDrawable iconicFontNickname = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_NICKNAME, getResources().getColor(R.color.color_e688e9));
//        IconicFontDrawable iconicFontLocation = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_USER_LOCATION, getResources().getColor(R.color.color_ffffff));
//        IconicFontDrawable iconicFontSex = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_USER_SEX, getResources().getColor(R.color.color_69ade7));
//        IconicFontDrawable iconicFontBirthday = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_BIRTHDAY, getResources().getColor(R.color.color_82cd6b));
//        mIconUserName.setBackground(iconicFont);
//        mIconEmail.setBackground(iconicFont1);
//        mIconChangePwd.setBackground(iconicFont2);
//        mIconUserEdit.setBackground(iconicFont3);
//        mIconNickName.setBackground(iconicFontNickname);
//        mIconSex.setBackground(iconicFontSex);
//        mIconBirthday.setBackground(iconicFontBirthday);
//        mIconLocation.setBackground(iconicFontLocation);
//    }
//
//    private void initTitleBar() {
//        mTitle = findView(R.id.tv_title);
//        AVUser currentUser = UserHelper.getInstance().getCurrentUser();
//        String nickName = (String) currentUser.get(UserKey.USER_NICK_NAME);
//        if (Helper.isNotEmpty(nickName)) {
//            mTitle.setText(nickName);
//        }else{
//            mTitle.setText(ResourceHelper.getString(R.string.title_personal_info));
//        }
//
//
//        mBtnBack = findView(R.id.tv_back);
//        IconicFontDrawable iconicFont = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_BACK, getResources().getColor(R.color.color_000000));
//        mBtnBack.setBackground(iconicFont);
//        mBtnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavigationHelper.finish(UserActivity.this, RESULT_OK, null);
//            }
//        });
//
//    }
//
//    private void setBtnUnBindState(TextView textView) {
//        textView.setText(R.string.setting_phone_unbind);
//        textView.setBackgroundResource(R.drawable.btn_setting_unbind);
//        textView.setTextColor(getResources().getColor(R.color.color_ffffff));
//        textView.setVisibility(View.VISIBLE);
//    }
//
//    private void setBtnBindState(TextView textView) {
//        textView.setText(R.string.setting_phone_bind);
//        textView.setBackgroundResource(R.drawable.btn_setting_bind);
//        textView.setTextColor(getResources().getColor(R.color.color_d2d2d2));
//        textView.setVisibility(View.GONE);
//    }
//
//    public void initUserLayout() {
//        AVUser.getCurrentUser().refreshInBackground(new RefreshCallback<AVObject>() {
//            @Override
//            public void done(AVObject avObject, AVException e) {
//                if (null != avObject) {
//                    AVUser currentUser = (AVUser) avObject;
//                    if (Helper.isNotEmpty(currentUser)) {
//
//                        String nickName = (String) currentUser.get(UserKey.USER_NICK_NAME);
//                        if (Helper.isNotEmpty(nickName)) {
//                            mNickName.setText(nickName);
//                        } else {
//                            if (Helper.isNotEmpty(currentUser.getUsername())) {
//                                mNickName.setText(currentUser.getUsername());
//                            }
//                        }
//
//                        String sex = (String) currentUser.get(UserKey.USER_SEX);
//                        if (Helper.isNotEmpty(sex)) {
//                            tvSex.setText(sex);
//                        }else{
//                            tvSex.setText(ResourceHelper.getString(R.string.man));
//                        }
//
//                        Date birthday = (Date) currentUser.get(UserKey.USER_BIRTHDAY);
//                        if (Helper.isNotEmpty(birthday)) {
//                            tvBirthday.setText(TimeUtil.getTime(birthday));
//                        }else{
//                        }
//
//                        if (Helper.isNotEmpty(currentUser.getUsername())) {
//                            mUserName.setText(currentUser.getUsername());
//                            tvName.setText(currentUser.getUsername());
//                        }
//
//                        String userDesc = (String) currentUser.get(UserKey.USER_PERSONAL_PROFILE);
//                        if (Helper.isNotEmpty(userDesc)) {
//                            mTvUserDesc.setText(userDesc);
//                        }
//
//                        AVFile avFile = (AVFile) currentUser.get(UserKey.USER_ICON_FILE_KEY);
//                        mUserIcon.setImageResource(R.drawable.pho_user_head);
//                        if (avFile != null && Helper.isNotEmpty(avFile.getUrl())) {
//                            displayUserProfile(avFile.getUrl());
//                        }else{
//                            String profileUrl = (String) currentUser.get(UserKey.USER_PROFILE_URL);
//                            if (Helper.isNotEmpty(profileUrl)) {
//                                displayUserProfile(profileUrl);
//                            }
//                        }
//
//                        if (currentUser.getBoolean(UserKey.USER_MOBILE_PHONE_VERIFIED)) {
//                            mMPhoneNum.setText(currentUser.getMobilePhoneNumber());
//                            setBtnBindState(mClickPhone);
//                        }else{
//                            mMPhoneNum.setText("");
//                            setBtnUnBindState(mClickPhone);
//                        }
//
//                        if (currentUser.getBoolean(UserKey.USER_EMAIL_VERIFIED)) {
//                            tvMail.setText(currentUser.getEmail());
//                            setBtnBindState(vClickMail);
//                        } else {
//                            setBtnUnBindState(vClickMail);
//                        }
//
//                        if (!currentUser.getBoolean(UserKey.USER_MOBILE_PHONE_VERIFIED) && !currentUser.getBoolean(UserKey.USER_EMAIL_VERIFIED)) {
//                            llChangePassword.setVisibility(View.GONE);
//                            findViewById(R.id.vw_change_password).setVisibility(View.GONE);
//                        }
//
//                        // 第三方
//                        Map<String, String> authData = (Map<String, String>) currentUser.get(UserKey.USER_AUTH_DATA);
//
//                        if (Helper.isNotEmpty(authData)) {
//                            if (authData.containsKey(AVUser.AVThirdPartyUserAuth.SNS_TENCENT_WEIXIN)) {
//                                setBtnBindState(vWeixing);
//                            } else {
//                                setBtnUnBindState(vWeixing);
//                            }
//                            if (authData.containsKey(AVUser.AVThirdPartyUserAuth.SNS_SINA_WEIBO)) {
//                                setBtnBindState(vSina);
//                            } else {
//                                setBtnUnBindState(vSina);
//                            }
//                        } else {
//                            setBtnUnBindState(vWeixing);
//                            setBtnUnBindState(vSina);
//                        }
//
//                    } else {
//                        mUserName.setText(ResourceHelper.getString(R.string.my_user_name));
//                        mUserIcon.setImageResource(R.drawable.pho_user_head);
//                        mMPhoneNum.setText("");
//                        setBtnUnBindState(mClickPhone);
//                        setBtnUnBindState(vWeixing);
//                        setBtnUnBindState(vSina);
//                        setBtnUnBindState(vClickMail);
//                    }
//                }
//            }
//        });
//    }
//
//    private void displayUserProfile(String profile) {
//        mUserIcon.setBackground(null);
//        Glide.with(this.getApplicationContext())
//                .load(profile)
//                .asBitmap()
//                .into(target);
//
//        LogHelper.e(profile);
//    }
//
//    private void initHeadLayout() {
//        mAppBarLayout = findView(R.id.app_bar);
//        ViewGroup.LayoutParams layoutParams = mAppBarLayout.getLayoutParams();
//        layoutParams.width = ScreenUtils.getScreenWidth(this);
////        layoutParams.height = ScreenUtils.getScreenWidth(this) * 3 / 5 - head_height;
//        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        mAppBarLayout.setLayoutParams(layoutParams);
////        initBackground();
//        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
//            @Override
//            public void onStateChanged(AppBarLayout appBarLayout, State state) {
////                if (state == State.EXPANDED) {
////                    objectAnmiator("#ff000000", "#ffffffff");
////                }else{
////                    objectAnmiator("#ffffffff", "#ff000000");
////                }
//            }
//        });
//    }
//
//    public void objectAnmiator(final String color, String end){
//        ValueAnimator titleAnmiator = ValueAnimator.ofObject(new ColorEvaluator(), color, end);
//        titleAnmiator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                String currentColor = (String) animation.getAnimatedValue();
//                mTitle.setTextColor(Color.parseColor(currentColor));
//            }
//        });
//
//        ValueAnimator settingAnmiator = ValueAnimator.ofObject(new ColorEvaluator(), color, end);
//        settingAnmiator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                String currentColor = (String) animation.getAnimatedValue();
//                setBtnSettingColor(Color.parseColor(currentColor));
//            }
//        });
//
//        AnimatorSet anmitorSet = new AnimatorSet();
//        anmitorSet.play(titleAnmiator).with(settingAnmiator);
//        anmitorSet.setDuration(1000);
//        anmitorSet.start();
//    }
//
//    private void setBtnSettingColor(int color) {
//        IconicFontDrawable iconicFontDrawable = IconicFontUtil.createIconicFontDrawable(this, CityGuideIcon.ICON_BACK, color);
//        mBtnBack.setBackground(iconicFontDrawable);
//    }
//
//    private void initBackground() {
//        mImgBackgroup = findView(R.id.iv_background);
//
//        Picasso.with(this)
//                .load(R.drawable.pho_user_background)
//                .noFade()
//                .memoryPolicy(NO_CACHE, NO_STORE)
//                .config(Bitmap.Config.ARGB_8888)
//                .resize(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenWidth(this) * 3 / 5 - head_height)
//                .into(mImgBackgroup);
//    }
//
//    @Override
//    protected void initData() {
//        super.initData();
//    }
//
//    public void setFace(String path){
//        if(Helper.isNotEmpty(path)){
//            mUserIcon.setBackground(null);
//            Glide.with(this.getApplicationContext())
//                    .load(path)
//                    .asBitmap()
//                    .into(target);
//        }
//        AVUser currentUser = UserHelper.getInstance().getCurrentUser();
//        if (Helper.isNotEmpty(currentUser)) {
//            String nickName = (String) currentUser.get(UserKey.USER_NICK_NAME);
//            if (Helper.isNotEmpty(nickName)) {
//                mUserName.setText(nickName);
//                setTitle(nickName);
//            }
//
//        }
//
//    }
//
//
//    /**
//     * function: 后退处理
//     */
//    @Override
//    protected void doBack(int keyCode, KeyEvent event) {
//
//        NavigationHelper.finish(this, RESULT_OK, null);
//    }
//
//    private void initEvent() {
//        final Context currentContext = this;
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.SIGN_OUT);
//                UserHelper.getInstance().logOut(currentContext);
//                finish();
//            }
//        });
//        llChangePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.CHANGE_PASSWORD);
//                UIHelper.getInstance().openChangePasswordActivity(UserActivity.this);
//            }
//        });
//        llIconLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.CHANGE_ICON);
//                if (CommonUtils.isSingleClick()) {
//                    selectPicture();
//                }
//
//            }
//        });
//
//        findViewById(R.id.ll_change_nickname).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.CHANGE_USER_NAME);
//                if (Helper.isNull(commentChangeContentDialog)) {
//                    commentChangeContentDialog = new CommentChangeContentDialog(UserActivity.this);
//                }
//                commentChangeContentDialog.setCommentChangeInterface(new CommentChangeContentDialog.CommentChangeContentInterface() {
//                    @Override
//                    public void changeNo() {
//
//                    }
//
//                    @Override
//                    public void changeYes(String content) {
//                        AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.CHANGE_USER_NAME_YES);
//                        AVUser avUser = AVUser.getCurrentUser();
//                        avUser.put(UserKey.USER_NICK_NAME, content);
//                        avUser.saveInBackground();
//                        mUserName.setText(content);
//                        mNickName.setText(content);
//                        setTitle(content);
//                    }
//                });
//                commentChangeContentDialog.setClose(true);
//                commentChangeContentDialog.showContent(R.string.text_change_user_name, R.string.text_change_user_name_no_change, R.string.picker_yes).show();
//            }
//        });
//
//        mBtnEditPerson.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                showEditPersonProfileDialog();
//            }
//        });
//
//        mFolloweeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AVUser currentUser = UserHelper.getInstance().getCurrentUser();
//                if (Helper.isNotEmpty(currentUser)) {
//                    UIHelper.getInstance().openFollowerActivity(UserActivity.this, currentUser.getObjectId(), FollowerActivity.FOLLOWEE_TYPE);
//                }else{
//                    UIHelper.getInstance().openStartActivity(UserActivity.this);
//                }
//
//
//            }
//        });
//
//        mFollowerLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AVUser currentUser = UserHelper.getInstance().getCurrentUser();
//                if (Helper.isNotEmpty(currentUser)) {
//                    UIHelper.getInstance().openFollowerActivity(UserActivity.this, currentUser.getObjectId(), FollowerActivity.FOLLOWER_TYPE);
//                }else{
//                    UIHelper.getInstance().openStartActivity(UserActivity.this);
//                }
//            }
//        });
//
//        findViewById(R.id.ll_change_user_sex).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (Helper.isNull(mSexDialog)) {
//                    mSexDialog = new SexDialog(UserActivity.this);
//                    mSexDialog.setSexDialogListener(new SexDialog.SexDialogListener() {
//                        @Override
//                        public void changeNo() {
//
//                        }
//
//                        @Override
//                        public void changeYes(String content) {
//
//                            AVUser avUser = AVUser.getCurrentUser();
//                            avUser.put(UserKey.USER_SEX, content);
//                            avUser.saveInBackground();
//                            tvSex.setText(content);
//                        }
//                    });
//                }
//               mSexDialog.show();
//            }
//        });
//
//
//        initBirthdayTimePicker();
//
//
//        findViewById(R.id.ll_change_birthday).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                InputMethodHelper.closeInputMethod(UserActivity.this);
//                mBirthdayTimePicker.show();
//
//            }
//        });
//
//        findViewById(R.id.ll_change_user_location).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Bundle bundle = new Bundle();
//
//                bundle.putInt(SearchActivity.SEARCH_TYPE, CommonData.CITY_LOCATION);
//
//                NavigationHelper.alphaActivity(UserActivity.this, SearchActivity.class, bundle, false);
//            }
//        });
//
//    }
//
//    private void initBirthdayTimePicker() {
//        //时间选择器
//        mBirthdayTimePicker = new TimePickerView(UserActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
//        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        mBirthdayTimePicker.setRange(calendar.get(Calendar.YEAR) - 50, calendar.get(Calendar.YEAR) + 20);//要在setTime 之前才有效果哦
//        mBirthdayTimePicker.setTime(new Date());
//        mBirthdayTimePicker.setCyclic(false);
//        mBirthdayTimePicker.setCancelable(true);
//        //时间选择后回调
//        mBirthdayTimePicker.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
//
//            @Override
//            public void onTimeSelect(Date date) {
//
//                String time = TimeUtil.getTime(date);
//                AVUser avUser = AVUser.getCurrentUser();
//                avUser.put(UserKey.USER_BIRTHDAY, date);
//                avUser.saveInBackground();
//                tvBirthday.setText(time);
//            }
//        });
//    }
//
//    private void showEditPersonProfileDialog() {
//        AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.CHANGE_SIGNATURE);
//        if (Helper.isNull(mEditProfileDialog)) {
//            mEditProfileDialog = new CommentChangeContentDialog(UserActivity.this, ResourceHelper.getString(R.string.text_change_user_desc));
//            UserActivity.this.mEditProfileDialog.setCommentChangeInterface(new CommentChangeContentDialog.CommentChangeContentInterface() {
//                @Override
//                public void changeNo() {
//
//                }
//
//                @Override
//                public void changeYes(String content) {
//                    if (Helper.isNotEmpty(content)) {
//                        AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.CHANGE_SIGNATURE_YES);
//                        AVUser avUser = AVUser.getCurrentUser();
//                        avUser.put(UserKey.USER_PERSONAL_PROFILE, content);
//                        avUser.saveInBackground();
//                        mTvUserDesc.setText(content);
//                    } else {
//                        ToastHelper.showToast(R.string.text_introduction_no_empty);
//                    }
//                }
//            });
//        }
//        mEditProfileDialog.showContent(R.string.text_change_user_name_no_change, R.string.picker_yes).show();
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        initUserLayout();
//    }
//
//
//    /**
//     * 图片相关
//     */
//    public void selectPicture() {
//        PhotoPickerIntent intent = new PhotoPickerIntent(UserActivity.this);
//        intent.setPhotoCount(1);
//        intent.setShowCamera(true);
//        startActivityForResult(intent, DataUtils.REQUEST_PICK);
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        /**使用SSO授权必须添加如下代码  */
//        UMSsoHandler ssoHandler = SocialShareService.getInstance().getController().getConfig().getSsoHandler(requestCode) ;
//        if(ssoHandler != null){
//            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }
//
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case DataUtils.REQUEST_PICK:
//                    ArrayList<String> selectedPicture =
//                            data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
//                    //获取地址
//
//                    if (selectedPicture.size() == 1) {
//                        String path = "file://" + selectedPicture.get(0);
//                        //调用裁剪
//
//                        Intent intent = new Intent(this, CropNewActivity.class);
//                        intent.putExtra("image", path);
//                        startActivityForResult(intent, DataUtils.REQUEST_CROP);
//                    }
//                    break;
//                case DataUtils.REQUEST_CROP:
//                    //裁剪完毕
//                    if (data != null) {
//                        Bundle bundle = data.getExtras();
//                        if (bundle != null) {
//                            String absPath = bundle.getString("data");
//                            LogHelper.e("data ====" + absPath);
//                            if (Helper.isNotEmpty(absPath)) {
//                                UserHelper.getInstance().uploadProfile(this, absPath);
//                            }else{
//                                LogHelper.e("裁剪地址absPath is null ==" + absPath);
//                            }
//
//                        }
//                    }
//                    break;
//            }
//        }
//    }
//
//}
