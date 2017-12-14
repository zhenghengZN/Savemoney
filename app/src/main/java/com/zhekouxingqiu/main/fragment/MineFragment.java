package com.zhekouxingqiu.main.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.LinkedList;

import bean.MineBean;
import bean.UserRespBean;
import common.CommonMethod;
import common.base.TitleFragment;
import iconicfont.IconicFontDrawable;
import iconicfont.IconicFontUtil;
import iconicfont.icon.CityGuideIcon;
import com.zhekouxingqiu.main.R;
import so.bubu.lib.base.recyclerview.recyclerView.adapter.ComRecyclerViewAdapter;
import so.bubu.lib.base.recyclerview.recyclerView.adapter.RecyclerViewHolder;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.LogHelper;
import so.bubu.lib.helper.ResourceHelper;
import utils.CallFunctionBackListener;
import utils.CommonUtils;
import utils.GlideHelper;
import utils.MyJsonUtil;
import utils.UIHelper;
import utils.UserHelper;
import utils.UserKey;
import utils.UserNetUtil;
import wiget.CircleImageView;

import static com.tencent.bugly.crashreport.inner.InnerAPI.context;

/**
 * Created by zhengheng on 17/12/7.
 */
public class MineFragment extends TitleFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mine);
        initView();
    }

    private LinearLayout logincell_layout;
    private RecyclerView recycler_cell;
    private TextView mTitle;
    private IconicFontDrawable[] iconList = new IconicFontDrawable[]{
            IconicFontUtil.createIconicFont(CityGuideIcon.ICON_ORDER, context.getResources().getColor(R.color.colorPrimary)),
            IconicFontUtil.createIconicFont(CityGuideIcon.ICON_COLLECT, context.getResources().getColor(R.color.colorPrimary)),
            IconicFontUtil.createIconicFont(CityGuideIcon.ICON_INFOR, context.getResources().getColor(R.color.colorPrimary)),
            IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SUPPORT_FEEDBACK, context.getResources().getColor(R.color.colorPrimary)),
            IconicFontUtil.createIconicFont(CityGuideIcon.ICON_SETTING, context.getResources().getColor(R.color.colorPrimary))

    };

    private static MineFragment INSTANCE;
    public static MineFragment getInstance(){
        return INSTANCE;
    }
    @Override
    protected void initView() {
        super.initView();
        INSTANCE = this;
        mTitle = findView(R.id.tv_title);
        mTitle.setText(ResourceHelper.getString(R.string.my_name_go));
        logincell_layout = (LinearLayout) findViewById(R.id.logincell_layout);
        recycler_cell = (RecyclerView) findViewById(R.id.recycler_cell);
        recycler_cell.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mUserAdapter = new ComRecyclerViewAdapter<MineBean.ObjectsBean>(getActivity(), CellList, R.layout.mine_cell) {

            @Override
            public void convert(RecyclerViewHolder viewHolder, MineBean.ObjectsBean item, int position) {
                View click = viewHolder.getView(R.id.v_click);
                initRightClickIcon(click);

                viewHolder.setText(R.id.tv_name, CellList.get(position).getTitle());

                ImageView img_icon = viewHolder.getView(R.id.img_icon);
                // TODO 设置图片
                switch (position) {
                    case 0:
                        GlideHelper.displayRoundedCornersImageTaobao(getActivity(), DensityUtil.dp2px(20), DensityUtil.dp2px(20), DensityUtil.dp2px(4), img_icon);
                        Log.e("zhengheng MineFragment", AlibcLogin.getInstance().isLogin() + " " + AlibcLogin.getInstance().getSession());
                        if (AlibcLogin.getInstance().isLogin()) {
                            //
                            viewHolder.getView(R.id.tv_detail).setVisibility(View.VISIBLE);
                            viewHolder.setText(R.id.tv_detail, R.string.unbindTaobao);
                            viewHolder.setText(R.id.tv_name, R.string.alreadybindTaoBao);
                        } else {
                            viewHolder.getView(R.id.tv_detail).setVisibility(View.GONE);
                            viewHolder.setText(R.id.tv_name, CellList.get(position).getTitle());
                        }

                        break;
                    case 1:
                        IconicFontDrawable icon1 = new IconicFontDrawable(context, CityGuideIcon.ICON_MINE_ORDER);
                        icon1.setIconColor(context.getResources().getColor(R.color.colorPrimary));
                        img_icon.setBackground(icon1);
                        break;
                    case 2:
                        IconicFontDrawable icon2 = new IconicFontDrawable(context, CityGuideIcon.ICON_SHOP);
                        icon2.setIconColor(context.getResources().getColor(R.color.colorPrimary));
                        img_icon.setBackground(icon2);
                        break;
                    case 3:
                        IconicFontDrawable icon3 = new IconicFontDrawable(context, CityGuideIcon.ICON_RECORD);
                        icon3.setIconColor(context.getResources().getColor(R.color.colorPrimary));
                        img_icon.setBackground(icon3);
                        break;
                    case 4:
                        IconicFontDrawable icon4 = new IconicFontDrawable(context, CityGuideIcon.ICON_SETTTING);
                        icon4.setIconColor(context.getResources().getColor(R.color.colorPrimary));
                        img_icon.setBackground(icon4);
                        break;

                }

            }
        };
      //  com.zhekouxingqiu.main
        mUserAdapter.setOnItemClickLitener(new ComRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, Object item, int position) {
                UIHelper.getInstance().openUrl(getActivity(), CellList.get(position).getUrl());
            }

            @Override
            public void onItemLongClick(View view, Object item, int position) {

            }
        });
        recycler_cell.setAdapter(mUserAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        getAssetsJson();

    }

    private static final String MINE_JSON_FILENAME = "mine.json";
    private LinkedList<MineBean> mineBeans = new LinkedList<>();

    public void getAssetsJson() {
        String mineJson = MyJsonUtil.getJson(getActivity(), MINE_JSON_FILENAME);
        LogUtil.log.e("zhengheng", mineJson);

        mineBeans.addAll(JSON.parseArray(mineJson, MineBean.class));
        createMineLayout();
    }

    //    private View mine_logincell;
    private LinkedList<MineBean.ObjectsBean> CellList = new LinkedList<>();

    public void createMineLayout() {

        for (int i = 0; i < mineBeans.size(); i++) {
            switch (mineBeans.get(i).getType()) {
                case "LoginCell":
                    View mine_logincell = LayoutInflater.from(getActivity()).inflate(R.layout.mine_logincell, null, false);
                    View ll_user_layout = mine_logincell.findViewById(R.id.ll_user_layout);
                    mUserName = (TextView) mine_logincell.findViewById(R.id.tv_user_nick_name);
                    mUserDesc = (TextView) mine_logincell.findViewById(R.id.tv_user_desc);
                    mUserIcon = (ImageView) mine_logincell.findViewById(R.id.iv_user_face);
                    mTarentoLayout = (ImageView) mine_logincell.findViewById(R.id.user_tarento_layout);
                    final String url = mineBeans.get(i).getObjects().get(0).getUrl();
                    ll_user_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO
                            if (UserHelper.getInstance().isAlreadlyLoginIn()) {
                                if (!CommonUtils.isSingleClick()) {
                                    return;
                                }
                                UIHelper.getInstance().openUserInfoAcivity(getActivity());
                            } else {
                                if (!CommonUtils.isSingleClick()) {
                                    return;
                                }
                                UIHelper.getInstance().openUrl(getActivity(), url);
                            }

                        }
                    });
                    CircleImageView iv_user_face = (CircleImageView) mine_logincell.findViewById(R.id.iv_user_face);
                    ImageView iv_right_click = (ImageView) mine_logincell.findViewById(R.id.iv_right_click);
                    initRightClickIcon(iv_right_click);

                    logincell_layout.addView(mine_logincell);
                    break;
                case "Cell":
                    CellList.addAll(mineBeans.get(i).getObjects());
                    mUserAdapter.notifyDataSetChanged();
                    break;
            }
        }


    }

    public ComRecyclerViewAdapter<MineBean.ObjectsBean> mUserAdapter;

    @Override
    public void onResume() {
        super.onResume();

        initUserLayout();
        requestUserData();

        mUserAdapter.notifyDataSetChanged();
    }

    private AVUser mCurrentUser;
    private TextView mUserName, mUserDesc;
    private ImageView mTarentoLayout, mUserIcon;

    public void initUserLayout() {

        mCurrentUser = AVUser.getCurrentUser();
        if (Helper.isNotEmpty(mCurrentUser)) {

            String nickName = (String) mCurrentUser.get(UserKey.USER_NICK_NAME);
            String personalProfile = (String) mCurrentUser.get(UserKey.USER_PERSONAL_PROFILE);

            if (Helper.isNotEmpty(nickName)) {
                mUserName.setText(nickName);
                mUserName.setTextColor(Color.BLACK);
            } else {
                if (Helper.isNotEmpty(mCurrentUser.getUsername())) {
                    mUserName.setText(mCurrentUser.getUsername());
                    mUserName.setTextColor(Color.BLACK);
                }
            }

            mUserDesc.setVisibility(View.VISIBLE);
            if (Helper.isNotEmpty(personalProfile)) {
                mUserDesc.setText(ResourceHelper.getString(R.string.user_desc));
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
            mUserName.setTextColor(Color.parseColor("#ec2050"));
            mUserIcon.setImageResource(R.drawable.pho_user_head);
            mTarentoLayout.setVisibility(View.GONE);
            mUserDesc.setVisibility(View.GONE);
        }
    }

    private UserRespBean mUserRespBean;

    private void requestUserData() {
        UserNetUtil.getInstance().getCurrentUserByNet(new CallFunctionBackListener() {
            @Override
            public void callSuccess(boolean result, String jsonstr) {

                if (result) {
                    if (Helper.isNotEmpty(jsonstr)) {
                        mUserRespBean = JSON.parseObject(jsonstr, UserRespBean.class);
                        CommonMethod.changeUserIcon(mTarentoLayout, mUserRespBean.getIsOfficial(), mUserRespBean.getTalentLevel());
                        mUserDesc.setText(ResourceHelper.getString(R.string.user_desc));
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

    private void initRightClickIcon(View view) {
        IconicFontDrawable iconRight = new IconicFontDrawable(context, CityGuideIcon.ICON_RIGHT);
        iconRight.setIconColor(context.getResources().getColor(R.color.color_999999));
        view.setBackground(iconRight);
    }


    private SimpleTarget target = new SimpleTarget<Bitmap>(ResourceHelper.Dp2Px(65), ResourceHelper.Dp2Px(65)) {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            mUserIcon.setImageBitmap(bitmap);
        }
    };

    private void displayUserProfile(String profile) {
        mUserIcon.setBackground(null);
        Glide.with(getActivity().getApplicationContext())
                .load(profile)
                .asBitmap()
                .into(target);

        LogHelper.e(profile);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
        LogUtil.log.i("zhengheng,CallbackContext");

    }



}
