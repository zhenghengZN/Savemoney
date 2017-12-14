package com.zhekouxingqiu.main.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import bean.UserBean;
import bean.UserRespBean;
import common.CommonMethod;
import common.base.TitleFragment;
import event.PoiEvent;
import iconicfont.IconicFontDrawable;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import com.zhekouxingqiu.main.R;
import com.zhekouxingqiu.main.activity.My.MessageActivity;
import so.bubu.lib.base.recyclerview.recyclerView.adapter.ComRecyclerViewAdapter;
import so.bubu.lib.base.recyclerview.recyclerView.adapter.RecyclerViewHolder;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.LogHelper;
import so.bubu.lib.helper.NavigationHelper;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.ScreenUtils;
import so.bubu.lib.helper.StatusBarUtil;
import so.bubu.lib.helper.ToastHelper;
import utils.AVAnalyticsHelper;
import utils.CallFunctionBackListener;
import utils.CommonUtils;
import utils.UIHelper;
import utils.UserHelper;
import utils.UserKey;
import utils.UserNetUtil;
import wiget.CircleImageView;

import static com.tencent.bugly.crashreport.inner.InnerAPI.context;

/**
 * 我
 * Created by wneng on 16/6/24.
 */

public class MyFragment extends TitleFragment {


    private Toolbar mToolbar;
    private AppBarLayout mAppBarLayout;
    private int head_height;
    private boolean hasNewMessage = false;
    private RecyclerView mMyRecyclerView;
    private List<UserBean> userList = new ArrayList<>();
    private TextView mTitle;
    private View mBtnBack;
    private View mImgLetter;
    private final int TYPE_INFORMATION = 0;
    private final int TYPE_COLLECTION = 1;
    private final int TYPE_SUPPORT = 2;
    private final int TYPE_SETTING = 3;
    private final int TYPE_ORDER = 4;
    private String[] titles = new String[]{

            ResourceHelper.getString(R.string.text_my_order),
            ResourceHelper.getString(R.string.my_collection),
            ResourceHelper.getString(R.string.text_my_information),
            ResourceHelper.getString(R.string.support),
            ResourceHelper.getString(R.string.setting)

    };
    private int[] typeList = new int[]{
            TYPE_ORDER,
            TYPE_COLLECTION,
            TYPE_INFORMATION,
            TYPE_SUPPORT,
            TYPE_SETTING
    };

    private IconicFontDrawable[] iconList = new IconicFontDrawable[]{
            IconicFontUtil.createIconicFont(CityGuideIcon.ICON_ORDER, context.getResources().getColor(R.color.colorPrimary)),
            IconicFontUtil.createIconicFont(CityGuideIcon.ICON_COLLECT, context.getResources().getColor(R.color.colorPrimary)),
            IconicFontUtil.createIconicFont(CityGuideIcon.ICON_INFOR, context.getResources().getColor(R.color.colorPrimary)),
            IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SUPPORT_FEEDBACK, context.getResources().getColor(R.color.colorPrimary)),
            IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SETTING, context.getResources().getColor(R.color.colorPrimary))

    };
    private ComRecyclerViewAdapter<UserBean> mUserAdapter;
    private CircleImageView mUserIcon;
    private TextView mUserName;
    private TextView mUserDesc;
    //    private View mUserTarento;
    private View llUserLayout;

    private SimpleTarget target = new SimpleTarget<Bitmap>(ResourceHelper.Dp2Px(65), ResourceHelper.Dp2Px(65)) {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            mUserIcon.setImageBitmap(bitmap);
        }
    };
    private AVUser mCurrentUser;
    private ImageView mTarentoLayout;
    private UserRespBean mUserRespBean;
    private TextView mTvPostCount;
    private TextView mTvPointCount;
    private TextView mTvFolloweeCount;
    private TextView mTvFollowerCount;
    private View mFolloweeLayout;
    private View mFollowerLayout;
//    private View mPostLayout;
//    private View mPointLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_center);
        head_height = StatusBarUtil.getStatusBarHeight(getActivity());
        initTitleBar();
        initUserData();

    }

    private void initUserData() {

        userList.clear();
        for (int i = 0; i < titles.length; i++) {

            UserBean userBean = new UserBean();
            userBean.setFontDrawable(iconList[i]);
            userBean.setName(titles[i]);
            userBean.setType(typeList[i]);
            userList.add(userBean);
        }

    }

    private void initTitleBar() {

        clickActivity();

        mTitle = findView(R.id.tv_title);
        mTitle.setText(ResourceHelper.getString(R.string.my_name_go));
        mBtnBack = findView(R.id.tv_back);
        mBtnBack.setVisibility(View.GONE);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showToast("back");
            }
        });
        mImgLetter = findView(R.id.img_click);
        IconicFontDrawable iconLetter = new IconicFontDrawable(context, CityGuideIcon.ICON_LETTER);
        iconLetter.setIconColor(context.getResources().getColor(R.color.color_000000));
        mImgLetter.setBackground(iconLetter);
        mImgLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    protected void onClickActivity() {

    }

    /**
     * 获取用户信息
     */
    private void requestUserData() {
        UserNetUtil.getInstance().getCurrentUserByNet(new CallFunctionBackListener() {
            @Override
            public void callSuccess(boolean result, String jsonstr) {

                if (result) {
                    if (Helper.isNotEmpty(jsonstr)) {
                        mUserRespBean = JSON.parseObject(jsonstr, UserRespBean.class);
                        CommonMethod.changeUserIcon(mTarentoLayout, mUserRespBean.getIsOfficial(), mUserRespBean.getTalentLevel());
                        mTvFolloweeCount.setText(mUserRespBean.getFolloweeCount() > 0 ? String.valueOf(mUserRespBean.getFolloweeCount()) : "0");
                        mTvFollowerCount.setText(mUserRespBean.getFollowerCount() > 0 ? String.valueOf(mUserRespBean.getFollowerCount()) : "0");
                        mTvPostCount.setText(mUserRespBean.getPostCount() > 0 ? String.valueOf(mUserRespBean.getPostCount()) : "0");
                        mTvPointCount.setText(mUserRespBean.getPoints() > 0 ? String.valueOf(mUserRespBean.getPoints()) : "0");
                        mUserDesc.setText(Helper.isNotEmpty(mUserRespBean.getPersonalSigniture()) ? mUserRespBean.getPersonalSigniture() : ResourceHelper.getString(R.string.user_desc));
                    }

                }
            }

            @Override
            public void callFailure(int type, AVException e) {

                if (e != null) {
//                    LogHelper.w(e.getLocalizedMessage());
                }
            }
        });
    }


    @Override
    protected void initView() {
        super.initView();

        mAppBarLayout = findView(R.id.app_bar);
        ViewGroup.LayoutParams layoutParams = mAppBarLayout.getLayoutParams();
        layoutParams.width = ScreenUtils.getScreenWidth(getActivity());
//        layoutParams.height = ResourceHelper.Dp2Px(148);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mAppBarLayout.setLayoutParams(layoutParams);


        mUserIcon = findView(R.id.iv_user_face);
        mUserName = findView(R.id.tv_user_nick_name);
        mUserDesc = findView(R.id.tv_user_desc);
        mTvPostCount = findView(R.id.tv_postcount);
        mTvPointCount = findView(R.id.tv_points);
        mTvFolloweeCount = findView(R.id.tv_followee_count);
        mTvFollowerCount = findView(R.id.tv_follower_count);
        mFolloweeLayout = findView(R.id.ll_followee_layout);
        mFollowerLayout = findView(R.id.ll_follower_layout);
//        mPostLayout = findView(R.id.ll_post_layout);
//        mPointLayout = findView(R.id.ll_point_layout);

        llUserLayout = findView(R.id.ll_user_layout);
        mTarentoLayout = findView(R.id.user_tarento_layout);

        llUserLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.PERSONAL_INFORMATION);

                if (UserHelper.getInstance().isAlreadlyLoginIn()) {
                    if (!CommonUtils.isSingleClick()) {
                        return;
                    }
//                    UIHelper.getInstance().openUserPanel(getActivity());
                } else {
                    if (!CommonUtils.isSingleClick()) {
                        return;
                    }
                    UIHelper.getInstance().openStartActivity(getActivity());
                }
            }
        });
//
//        mFolloweeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AVUser currentUser = UserHelper.getInstance().getCurrentUser();
//                if (Helper.isNotEmpty(currentUser)) {
//                    UIHelper.getInstance().openFollowerActivity(getActivity(), currentUser.getObjectId(), FollowerActivity.FOLLOWEE_TYPE);
//                } else {
//                    UIHelper.getInstance().openStartActivity(getActivity());
//                }
//
//
//            }
//        });

//        mFollowerLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AVUser currentUser = UserHelper.getInstance().getCurrentUser();
//                if (Helper.isNotEmpty(currentUser)) {
//                    UIHelper.getInstance().openFollowerActivity(getActivity(), currentUser.getObjectId(), FollowerActivity.FOLLOWER_TYPE);
//                } else {
//                    UIHelper.getInstance().openStartActivity(getActivity());
//                }
//            }
//        });

//        mPostLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openMyDynamic();
//            }
//        });
//
//        mPointLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openMyPoint();
//            }
//        });

        initUserLayout();

        initRightIcon();

        initRecycler();

        if (UserHelper.getInstance().isAlreadlyLoginIn()) {
            requestUserData();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        initUserLayout();
        requestUserData();

        mUserAdapter.notifyDataSetChanged();
    }


    public void initUserLayout() {

        mCurrentUser = AVUser.getCurrentUser();
        if (Helper.isNotEmpty(mCurrentUser)) {

            String nickName = (String) mCurrentUser.get(UserKey.USER_NICK_NAME);
            String personalProfile = (String) mCurrentUser.get(UserKey.USER_PERSONAL_PROFILE);
            if (Helper.isNotEmpty(nickName)) {
                mUserName.setText(nickName);
            } else {
                if (Helper.isNotEmpty(mCurrentUser.getUsername())) {
                    mUserName.setText(mCurrentUser.getUsername());
                }
            }

            mUserDesc.setVisibility(View.VISIBLE);
            if (Helper.isNotEmpty(personalProfile)) {
                mUserDesc.setText(personalProfile);
            } else {
                mUserDesc.setText(ResourceHelper.getString(R.string.user_desc));
            }

            mTarentoLayout.setVisibility(View.GONE);

            AVFile avFile = (AVFile) mCurrentUser.get(UserKey.USER_ICON_FILE_KEY);
            mUserIcon.setImageResource(R.drawable.pho_user_head);
            if (avFile != null && Helper.isNotEmpty(avFile.getUrl())) {
                displayUserProfile(avFile.getUrl());
            } else {
                String profileUrl = (String) mCurrentUser.get(UserKey.USER_PROFILE_URL);
                if (Helper.isNotEmpty(profileUrl)) {
                    displayUserProfile(profileUrl);
                }
            }

        } else {
            mUserName.setText(ResourceHelper.getString(R.string.my_user_name));
            mUserIcon.setImageResource(R.drawable.pho_user_head);
            mTarentoLayout.setVisibility(View.GONE);
            mUserDesc.setVisibility(View.GONE);
            mTvFolloweeCount.setText("0");
            mTvFollowerCount.setText("0");
            mTvPostCount.setText("0");
            mTvPointCount.setText("0");

        }

    }

    private void displayUserProfile(String profile) {
        mUserIcon.setBackground(null);
        Glide.with(getActivity().getApplicationContext())
                .load(profile)
                .asBitmap()
                .into(target);

        LogHelper.e(profile);
    }

    private void initRightIcon() {

//        mUserTarento = findView(R.id.iv_user_tarento);


//        IconicFontDrawable iconicFont = IconicFontUtil.createIconicFont(CityGuideIcon.ICON_MAN, context.getResources().getColor(R.color.color_ffffff));
//        mUserTarento.setBackground(iconicFont);

        View rightClick = findView(R.id.iv_right_click);

        initRightClickIcon(rightClick);

    }

    private void initRightClickIcon(View view) {
        IconicFontDrawable iconRight = new IconicFontDrawable(context, CityGuideIcon.ICON_RIGHT);
        iconRight.setIconColor(context.getResources().getColor(R.color.color_999999));
        view.setBackground(iconRight);
    }

    private void initRecycler() {

        mMyRecyclerView = findView(R.id.recycler_my_center);
        mMyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        mMyRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL_LIST));
//        mMyRecyclerView.addItemDecoration(new OnlyTopSpaceItemDecoration(ResourceHelper.Dp2Px(12)));
        mMyRecyclerView.setAdapter(mUserAdapter = new ComRecyclerViewAdapter<UserBean>(getActivity(), userList, R.layout.recycler_my_item) {

            @Override
            public void convert(RecyclerViewHolder viewHolder, UserBean item, int position) {

                View click = viewHolder.getView(R.id.v_click);
                initRightClickIcon(click);
//                setIconBg(viewHolder, position);

                View imgIcon = viewHolder.getView(R.id.img_icon);
                imgIcon.setBackground(item.getFontDrawable());

                viewHolder.setText(R.id.tv_name, item.getName());

                viewHolder.getView(R.id.tv_draft_num).setVisibility((1 == position && hasNewMessage) ? View.VISIBLE : View.GONE);
//                TextView draftNum = viewHolder.getView(R.id.tv_draft_num);
//                if(item.getType() == TYPE_BOX){
//
//
//                    if (UserHelper.getInstance().isAlreadlyLoginIn()) {
//                        int num = DraftBoxUtil.queryDraftNum(mCurrentUser.getObjectId());
//                        if (num  > 0) {
//                            draftNum.setVisibility(View.VISIBLE);
//                            draftNum.setText(num + "");
//                        }else{
//                            draftNum.setVisibility(View.GONE);
//                        }
//                    }else{
//                        draftNum.setVisibility(View.GONE);
//                    }
//
//                }else{
//                    draftNum.setVisibility(View.GONE);
//                }

            }
        });
        mUserAdapter.setOnItemClickLitener(new ComRecyclerViewAdapter.OnItemClickLitener<UserBean>() {
            @Override
            public void onItemClick(View view, UserBean item, int position) {

                switch (item.getType()) {
                    case TYPE_INFORMATION:
//                        AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.MY_DYNAMIC);
//                        openMyDynamic();
                        if (UserHelper.getInstance().isAlreadlyLoginIn()) {
                            NavigationHelper.slideActivity(getActivity(), MessageActivity.class, null, false);
                        } else {
                            UIHelper.getInstance().openStartActivity(getActivity());
                        }
                        hasNewMessage = false;
                        mUserAdapter.notifyDataSetChanged();
                        break;
                    case TYPE_COLLECTION:
                        AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.MY_COLLECT);
                        openFavorite();
                        break;
                    case TYPE_SUPPORT:
//                        AVAnalyticsHelper.addUseActions(AVAnalyticsHelper.MY_DRAFTS);
//                        openDraftBox();
                        AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.SUPPORT_FEEDBACK);
                        UIHelper.getInstance().openSupport(getActivity());
                        break;
                    case TYPE_SETTING:
                        AVAnalyticsHelper.addSettingActions(AVAnalyticsHelper.SETTING);
                        openSetting();
                        break;
                    case TYPE_ORDER:
                        openMyOrder();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, UserBean item, int position) {

            }
        });
    }

//    private void openMyPoint() {
//
//        if (UserHelper.getInstance().isAlreadlyLoginIn()) {
//
//            int points = mUserRespBean.getPoints();
//            int talentLevel = mUserRespBean.getTalentLevel();
//            UIHelper.getInstance().openMyPointActivity(getActivity(), points, talentLevel);
//        } else {
//            UIHelper.getInstance().openStartActivity(getActivity());
//        }
//    }


//    private void openMyDynamic() {
//
//        if (UserHelper.getInstance().isAlreadlyLoginIn()) {
//            UIHelper.getInstance().openMyDynamicActivity(getActivity());
//        } else {
//            UIHelper.getInstance().openStartActivity(getActivity());
//        }
//    }

    private void openSetting() {

        UIHelper.getInstance().openSettingActivity(getActivity());
    }

//    private void openDraftBox() {
//
//        if (UserHelper.getInstance().isAlreadlyLoginIn()) {
//            UIHelper.getInstance().openDraftBoxActivity(getActivity());
//        } else {
//            UIHelper.getInstance().openStartActivity(getActivity());
//        }
//    }

    private void openFavorite() {

        if (UserHelper.getInstance().isAlreadlyLoginIn()) {
            UIHelper.getInstance().openFavoriteActivity(getActivity());
        } else {
            UIHelper.getInstance().openStartActivity(getActivity());
        }
    }

    private void openMyOrder() {

        if (UserHelper.getInstance().isAlreadlyLoginIn()) {
            UIHelper.getInstance().openUrl(getActivity(), "https://h5.youzan.com/v2/usercenter/so2b53kz");
        } else {
            UIHelper.getInstance().openStartActivity(getActivity());
        }
    }

//    private void setIconBg(RecyclerViewHolder viewHolder, int position) {
//        View iconBg = viewHolder.getView(R.id.icon_bg);
//        switch (position) {
//            case 0:
//                iconBg.setBackgroundResource(R.drawable.my_feed_bg);
//                break;
//            case 1:
//                iconBg.setBackgroundResource(R.drawable.my_colloction_bg);
//                break;
//            case 2:
//                iconBg.setBackgroundResource(R.drawable.my_box_bg);
//                break;
//            case 3:
//                iconBg.setBackgroundResource(R.drawable.my_setting_bg);
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    protected boolean isOpenEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(PoiEvent event) {
        switch (event.getMsg()) {

            // 有未读消息：
            case PoiEvent.SHOW_HAS_INFOEMATION:
                hasNewMessage = true;
                mUserAdapter.notifyDataSetChanged();
                break;

        }
    }
}
