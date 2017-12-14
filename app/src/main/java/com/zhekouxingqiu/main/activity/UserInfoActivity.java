package com.zhekouxingqiu.main.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.AppBarLayout;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RefreshCallback;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.umeng.socialize.sso.UMSsoHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import bean.UserRespBean;
import common.base.TitleAppCompatActivity;
import iconicfont.IconicFontDrawable;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import share.SocialShareService;
import com.zhekouxingqiu.main.R;
import com.zhekouxingqiu.main.activity.My.CropNewActivity;

import so.bubu.lib.base.recyclerview.recyclerView.AppBarStateChangeListener;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.InputMethodHelper;
import so.bubu.lib.helper.LogHelper;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.ScreenUtils;
import so.bubu.lib.helper.StatusBarUtil;
import utils.CallFunctionBackListener;
import utils.CommonUtils;
import utils.DataUtils;
import utils.UserHelper;
import utils.UserKey;
import utils.UserNetUtil;
import utils.photopicker.PhotoPickerActivity;
import utils.photopicker.utils.PhotoPickerIntent;
import utils.photopicker.utils.TimeUtil;
import utils.photopicker.widget.common.CommentChangeContentDialog;
import utils.photopicker.widget.common.SexDialog;
import wiget.CircleImageView;

public class UserInfoActivity extends TitleAppCompatActivity {

    private static UserInfoActivity SHARED_INSTANCE;

    public static UserInfoActivity getSharedInstance() {
        return SHARED_INSTANCE;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_info);
        SHARED_INSTANCE = this;
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        mTvUserDesc = findView(R.id.tv_user_desc);
        requestUserData();
    }

    @Override
    protected void doBack(int keyCode, KeyEvent event) {
        NavigationHelper.finish(this, RESULT_OK, null);
    }

    private SimpleTarget target = new SimpleTarget<Bitmap>(ResourceHelper.Dp2Px(65), ResourceHelper.Dp2Px(65)) {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            ivFace.setImageBitmap(bitmap);
        }
    };

    private TextView mTvUserDesc, mUserName, mNickName, tvSex, tvBirthday, tvName, mMPhoneNum, mClickPhone;
    private CircleImageView ivFace;
    private CircleImageView mUserIcon;
    private View llIconLayout;
    private Button btnLogout;

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        initHeadLayout();
        initFontIcon();

        ivFace = findView(R.id.iv_user_face);
        mUserIcon = findView(R.id.iv_user_face);
        btnLogout = findView(R.id.btn_logout);
        mUserName = findView(R.id.tv_user_name);
        mNickName = findView(R.id.tv_nickname);
        tvSex = findView(R.id.tv_sex);
        tvBirthday = findView(R.id.tv_birthday);
        tvName = findView(R.id.tv_name);
        mMPhoneNum = findView(R.id.tv_phone_num);
        mClickPhone = findView(R.id.v_click_phone);

        llIconLayout = findView(R.id.ll_container);
        initRightIcon();

        initEvent();
    }

    private CommentChangeContentDialog commentChangeContentDialog;

    private void initEvent() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserHelper.getInstance().logOut(UserInfoActivity.this);
                finish();
            }
        });

        llIconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isSingleClick()) {
                    selectPicture();
                }

            }
        });

        findViewById(R.id.ll_change_nickname).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.CHANGE_USER_NAME);
                if (Helper.isNull(commentChangeContentDialog)) {
                    commentChangeContentDialog = new CommentChangeContentDialog(UserInfoActivity.this);
                }
                commentChangeContentDialog.setCommentChangeInterface(new CommentChangeContentDialog.CommentChangeContentInterface() {
                    @Override
                    public void changeNo() {

                    }

                    @Override
                    public void changeYes(String content) {
//                        AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.CHANGE_USER_NAME_YES);
                        AVUser avUser = AVUser.getCurrentUser();
                        avUser.put(UserKey.USER_NICK_NAME, content);
                        avUser.saveInBackground();
                        mUserName.setText(content);
                        mNickName.setText(content);
//                        setTitle(content);
                    }
                });
                commentChangeContentDialog.setClose(true);
                commentChangeContentDialog.showContent(R.string.text_change_user_name, R.string.text_change_user_name_no_change, R.string.picker_yes).show();
            }
        });

        findViewById(R.id.ll_change_user_sex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Helper.isNull(mSexDialog)) {
                    mSexDialog = new SexDialog(UserInfoActivity.this);
                    mSexDialog.setSexDialogListener(new SexDialog.SexDialogListener() {
                        @Override
                        public void changeNo() {

                        }

                        @Override
                        public void changeYes(String content) {

                            AVUser avUser = AVUser.getCurrentUser();
                            avUser.put(UserKey.USER_SEX, content);
                            avUser.saveInBackground();
                            tvSex.setText(content);
                        }
                    });
                }
                mSexDialog.show();
            }
        });

        initBirthdayTimePicker();


        findViewById(R.id.ll_change_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodHelper.closeInputMethod(UserInfoActivity.this);
                mBirthdayTimePicker.show();

            }
        });


    }

    private SexDialog mSexDialog;
    private TimePickerView mBirthdayTimePicker;

    private void initBirthdayTimePicker() {
        //时间选择器
        mBirthdayTimePicker = new TimePickerView(UserInfoActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        mBirthdayTimePicker.setRange(calendar.get(Calendar.YEAR) - 50, calendar.get(Calendar.YEAR) + 20);//要在setTime 之前才有效果哦
        mBirthdayTimePicker.setTime(new Date());
        mBirthdayTimePicker.setCyclic(false);
        mBirthdayTimePicker.setCancelable(true);
        //时间选择后回调
        mBirthdayTimePicker.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {

                String time = TimeUtil.getTime(date);
                AVUser avUser = AVUser.getCurrentUser();
                avUser.put(UserKey.USER_BIRTHDAY, date);
                avUser.saveInBackground();
                tvBirthday.setText(time);
            }
        });
    }

    /**
     * 图片相关
     */
    public void selectPicture() {
        PhotoPickerIntent intent = new PhotoPickerIntent(UserInfoActivity.this);
        intent.setPhotoCount(1);
        intent.setShowCamera(true);
        startActivityForResult(intent, DataUtils.REQUEST_PICK);
    }

    private void initRightIcon() {
        IconicFontDrawable iconRight = new IconicFontDrawable(this, CityGuideIcon.ICON_RIGHT);
        iconRight.setIconColor(getResources().getColor(R.color.color_999999));

        findViewById(R.id.v_click_icon_nickname).setBackground(iconRight);
        findViewById(R.id.v_click_icon_sex).setBackground(iconRight);
        findViewById(R.id.v_click_icon_birthday).setBackground(iconRight);
    }

    private ImageView mBtnBack;

    private void initTitleBar() {

        mBtnBack = findView(R.id.tv_back);
        IconicFontDrawable iconicFont = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_BACK, getResources().getColor(R.color.color_000000));
        mBtnBack.setBackground(iconicFont);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationHelper.finish(UserInfoActivity.this, RESULT_OK, null);
            }
        });
    }

    private AppBarLayout mAppBarLayout;

    private void initHeadLayout() {
        mAppBarLayout = findView(R.id.app_bar);
        ViewGroup.LayoutParams layoutParams = mAppBarLayout.getLayoutParams();
        layoutParams.width = ScreenUtils.getScreenWidth(this);
//        layoutParams.height = ScreenUtils.getScreenWidth(this) * 3 / 5 - head_height;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mAppBarLayout.setLayoutParams(layoutParams);
//        initBackground();
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
//                if (state == State.EXPANDED) {
//                    objectAnmiator("#ff000000", "#ffffffff");
//                }else{
//                    objectAnmiator("#ffffffff", "#ff000000");
//                }
            }
        });
    }


    private View mIconUserName;
    private View mIconNickName;

    private void initFontIcon() {

        mIconUserName = findView(R.id.icon_username);
        mIconNickName = findView(R.id.icon_nickname);


        View mIconSex = findView(R.id.icon_sex);
        View mIconBirthday = findView(R.id.icon_birthday);


        IconicFontDrawable iconicFont = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_USER_NAME, getResources().getColor(R.color.color_e79369));
        IconicFontDrawable iconicFontNickname = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_NICKNAME, getResources().getColor(R.color.color_e688e9));
        IconicFontDrawable iconicFontSex = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_USER_SEX, getResources().getColor(R.color.color_69ade7));
        IconicFontDrawable iconicFontBirthday = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_BIRTHDAY, getResources().getColor(R.color.color_82cd6b));
        findViewById(R.id.icon_phone).setBackground(IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SETTING_PHONE, getResources().getColor(R.color.color_82cd6b)));

        mIconUserName.setBackground(iconicFont);
        mIconNickName.setBackground(iconicFontNickname);
        mIconSex.setBackground(iconicFontSex);
        mIconBirthday.setBackground(iconicFontBirthday);
    }

    @Override
    protected void initData() {
        super.initData();
    }


    private UserRespBean mUserRespBean;

    private void requestUserData() {
        UserNetUtil.getInstance().getCurrentUserByNet(new CallFunctionBackListener() {
            @Override
            public void callSuccess(boolean result, String jsonstr) {

                if (result) {
                    if (Helper.isNotEmpty(jsonstr)) {
                        mUserRespBean = JSON.parseObject(jsonstr, UserRespBean.class);
                        mTvUserDesc.setText(mUserRespBean.getUsername());
                    }

                }
            }

            @Override
            public void callFailure(int type, AVException e) {

                if (e != null) {
                    LogHelper.w(e.getLocalizedMessage());
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码  */
        UMSsoHandler ssoHandler = SocialShareService.getInstance().getController().getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case DataUtils.REQUEST_PICK:
                    ArrayList<String> selectedPicture =
                            data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                    //获取地址

                    if (selectedPicture.size() == 1) {
                        String path = "file://" + selectedPicture.get(0);
                        //调用裁剪

                        Intent intent = new Intent(this, CropNewActivity.class);
                        intent.putExtra("image", path);
                        startActivityForResult(intent, DataUtils.REQUEST_CROP);
                    }
                    break;
                case DataUtils.REQUEST_CROP:
                    //裁剪完毕
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            String absPath = bundle.getString("data");
                            LogHelper.e("data ====" + absPath);
                            if (Helper.isNotEmpty(absPath)) {
                                UserHelper.getInstance().uploadProfile(this, absPath);
                            } else {
                                LogHelper.e("裁剪地址absPath is null ==" + absPath);
                            }

                        }
                    }
                    break;
            }
        }

    }

    public void setFace(String path) {
        if (Helper.isNotEmpty(path)) {
            mUserIcon.setBackground(null);
            Glide.with(this.getApplicationContext())
                    .load(path)
                    .asBitmap()
                    .into(target);
        }
        AVUser currentUser = UserHelper.getInstance().getCurrentUser();
        if (Helper.isNotEmpty(currentUser)) {
            String nickName = (String) currentUser.get(UserKey.USER_NICK_NAME);
            if (Helper.isNotEmpty(nickName)) {
                mUserName.setText(nickName);
                setTitle(nickName);
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initUserLayout();
    }

    private void setBtnUnBindState(TextView textView) {
        textView.setText(R.string.setting_phone_unbind);
        textView.setBackgroundResource(R.drawable.btn_setting_unbind);
        textView.setTextColor(getResources().getColor(R.color.color_ffffff));
        textView.setVisibility(View.VISIBLE);
    }

    private void setBtnBindState(TextView textView) {
        textView.setText(R.string.setting_phone_bind);
        textView.setBackgroundResource(R.drawable.btn_setting_bind);
        textView.setTextColor(getResources().getColor(R.color.color_d2d2d2));
        textView.setVisibility(View.GONE);
    }

    public void initUserLayout() {
        AVUser.getCurrentUser().refreshInBackground(new RefreshCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (null != avObject) {
                    AVUser currentUser = (AVUser) avObject;
                    if (Helper.isNotEmpty(currentUser)) {

                        String nickName = (String) currentUser.get(UserKey.USER_NICK_NAME);
                        if (Helper.isNotEmpty(nickName)) {
                            mNickName.setText(nickName);
                        } else {
                            if (Helper.isNotEmpty(currentUser.getUsername())) {
                                mNickName.setText(currentUser.getUsername());
                            }
                        }

                        String sex = (String) currentUser.get(UserKey.USER_SEX);
                        if (Helper.isNotEmpty(sex)) {
                            tvSex.setText(sex);
                        } else {
                            tvSex.setText(ResourceHelper.getString(R.string.man));
                        }

                        Date birthday = (Date) currentUser.get(UserKey.USER_BIRTHDAY);
                        if (Helper.isNotEmpty(birthday)) {
                            tvBirthday.setText(TimeUtil.getTime(birthday));
                        } else {
                        }

                        if (Helper.isNotEmpty(currentUser.getUsername())) {
                            mUserName.setText(currentUser.getUsername());
                            tvName.setText(currentUser.getUsername());
                        }

                        String userDesc = (String) currentUser.get(UserKey.USER_PERSONAL_PROFILE);
                        if (Helper.isNotEmpty(userDesc)) {
                            mTvUserDesc.setText(userDesc);
                        }

                        AVFile avFile = (AVFile) currentUser.get(UserKey.USER_ICON_FILE_KEY);
                        mUserIcon.setImageResource(R.drawable.pho_user_head);
                        if (avFile != null && Helper.isNotEmpty(avFile.getUrl())) {
                            displayUserProfile(avFile.getUrl());
                        } else {
                            String profileUrl = (String) currentUser.get(UserKey.USER_PROFILE_URL);
                            if (Helper.isNotEmpty(profileUrl)) {
                                displayUserProfile(profileUrl);
                            }
                        }

                        if (currentUser.getBoolean(UserKey.USER_MOBILE_PHONE_VERIFIED)) {
                            mMPhoneNum.setText(currentUser.getMobilePhoneNumber());
                            setBtnBindState(mClickPhone);
                        } else {
                            mMPhoneNum.setText("");
                            setBtnUnBindState(mClickPhone);
                        }

                        // 第三方
                        Map<String, String> authData = (Map<String, String>) currentUser.get(UserKey.USER_AUTH_DATA);


                    } else {
                        mUserName.setText(ResourceHelper.getString(R.string.my_user_name));
                        mUserIcon.setImageResource(R.drawable.pho_user_head);
                        mMPhoneNum.setText("");
                        setBtnUnBindState(mClickPhone);
                    }
                }
            }
        });


    }

    private void displayUserProfile(String profile) {
        mUserIcon.setBackground(null);
        Glide.with(this.getApplicationContext())
                .load(profile)
                .asBitmap()
                .into(target);

        LogHelper.e(profile);
    }
}
