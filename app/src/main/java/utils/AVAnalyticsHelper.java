package utils;

import com.avos.avoscloud.AVAnalytics;

import so.bubu.lib.base.BaseApplication;

/**
 * 统计事件
 *
 * @author linhuan on 16/6/3 上午11:09
 */
public class AVAnalyticsHelper {

    /**
     * true 开启统计，false 关闭统计
     */
    public static boolean IS_OPEN_AVANALY = true;

    // poi分类信息数据
    private static final String FIND_ACTIONS = "发现";
    public static final String FIND_GUIDE = "指南";
    public static final String FIND_CHOICE = "精选";
    public static final String FIND_NREABY = "附近";

    public static void addFindActions(String actions) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), FIND_ACTIONS, actions);
        }
    }

    // poi分类信息数据
    private static final String POI_CATEGORY_ACTIONS = "精选";
    public static final String ATTRACTION = "景点";
    public static final String ATTRACTION_TOP = "景点头部";
    public static final String FOOD = "美食";
    public static final String FOOD_TOP = "美食头部";
    public static final String HOTEL = "住宿";
    public static final String HOTEL_TOP = "住宿头部";
    public static final String SHOPPING = "购物";
    public static final String SHOPPING_TOP = "购物头部";
    public static final String ACTIVITY = "活动";
    public static final String ACTIVITY_TOP = "活动头部";
    public static final String ITINERARY = "路线";
    public static final String ITINERARY_TOP = "路线头部";

    public static void addPOICategoryActions(int position, boolean flag) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), POI_CATEGORY_ACTIONS, getActionName(position, flag));
        }
    }

    // poi列表数据
    private static final String POI_LIST_ACTIONS = "精选列表";
    public static final String ATTRACTION_CHOSEN = "景点精选";
    public static final String ATTRACTION_POI = "景点poi";
    public static final String FOOD_CHOSEN = "美食精选";
    public static final String FOOD_POI = "美食poi";
    public static final String HOTEL_CHOSEN = "住宿精选";
    public static final String HOTEL_POI = "住宿poi";
    public static final String SHOPPING_CHOSEN = "购物精选";
    public static final String SHOPPING_POI = "购物poi";
    public static final String ACTIVITY_CHOSEN = "活动精选";
    public static final String ACTIVITY_POI = "活动poi";

    public static void addPOIListActions(int position, boolean isChosen) {
        if (IS_OPEN_AVANALY) {
            if (-1 != position && 2 != position) {
                AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), POI_LIST_ACTIONS, getListName(position, isChosen));
            }
        }
    }

    public static String getListName(int position, boolean isChosen) {
        String action = "";
        switch (position) {

            case 0:
                action = isChosen ? ATTRACTION_CHOSEN : ATTRACTION_POI;
                break;

            case 1:
                action = isChosen ? FOOD_CHOSEN : FOOD_POI;
                break;

            case 3:
                action = isChosen ? HOTEL_CHOSEN : HOTEL_POI;
                break;

            case 4:
                action = isChosen ? SHOPPING_CHOSEN : SHOPPING_POI;
                break;

            case 5:
                action = isChosen ? ACTIVITY_CHOSEN : ACTIVITY_POI;
                break;

        }
        return action;
    }

    public static String getActionName(int position, boolean isChosen) {
        String action = "";
        switch (position) {

            case 0:
                action = isChosen ? ATTRACTION_TOP : ATTRACTION;
                break;

            case 1:
                action = isChosen ? FOOD_TOP : FOOD;
                break;

            case 2:
                action = isChosen ? ITINERARY_TOP : ITINERARY;
                break;

            case 3:
                action = isChosen ? HOTEL_TOP : HOTEL;
                break;

            case 4:
                action = isChosen ? SHOPPING_TOP : SHOPPING;
                break;

            case 5:
                action = isChosen ? ACTIVITY_TOP : ACTIVITY;
                break;

        }
        return action;
    }

    // 附近
    private static final String NEARBY_ACTIONS = "附近";
    public static final String NEARBY_ATTRACTION = "景点";
    public static final String NEARBY_FOOD = "美食";
    public static final String NEARBY_HOTEL = "住宿";
    public static final String NEARBY_SHOPPING = "购物";
    public static final String NEARBY_ACTIVITY = "活动";
    public static final String NEARBY_ATTRACTION_POI = "景点poi";
    public static final String NEARBY_FOOD_POI = "美食poi";
    public static final String NEARBY_HOTEL_POI = "住宿poi";
    public static final String NEARBY_SHOPPING_POI = "购物poi";
    public static final String NEARBY_ACTIVITY_POI = "活动poi";
    public static final String NEARBY_LOCATE = "定位";
    public static final String NEARBY_NAVIGATION = "导航";
    public static final String NEARBY_POI_DETAIL = "poi详情";

    public static void addNearbyActions(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), NEARBY_ACTIONS, action);
        }
    }

    public static String getNearbyName(int position, boolean isChosen) {
        String action = "";
        switch (position) {

            case 0:
                action = isChosen ? NEARBY_ATTRACTION : NEARBY_ATTRACTION_POI;
                break;

            case 1:
                action = isChosen ? NEARBY_FOOD : NEARBY_FOOD_POI;
                break;

            case 2:
                action = isChosen ? NEARBY_HOTEL : NEARBY_HOTEL_POI;
                break;

            case 3:
                action = isChosen ? NEARBY_SHOPPING : NEARBY_SHOPPING_POI;
                break;

            case 4:
                action = isChosen ? NEARBY_ACTIVITY : NEARBY_ACTIVITY_POI;
                break;

        }
        return action;
    }

    // 指南
    private static final String CITY_OVERVIEW_OR_PRACTICAL_INFORMATION_ACTIONS = "指南";
    public static final String CITY_OVERVIEW_OR_PRACTICAL_INFORMATION_ACTIONS_NEXT = "下一页";
    public static final String CITY_OVERVIEW_OR_PRACTICAL_INFORMATION_ACTIONS_PREVIOUS = "上一页";
    public static final String CITY_OVERVIEW_OR_PRACTICAL_INFORMATION_ACTIONS_SHARE = "分享";
    public static final String CITY_OVERVIEW_OR_PRACTICAL_INFORMATION_ACTIONS_SHARE_ALL = "分享专题";
    public static final String CITY_OVERVIEW_OR_PRACTICAL_INFORMATION_ACTIONS_SHARE_POI = "分享poi";
    public static final String CITY_OVERVIEW_OR_PRACTICAL_INFORMATION_ACTIONS_PIC = "图片浏览";
    public static final String CITY_OVERVIEW_OR_PRACTICAL_INFORMATION_ACTIONS_NET = "网址";
    public static final String CITY_OVERVIEW_OR_PRACTICAL_INFORMATION_ACTIONS_PHONE = "电话";
    public static final String CITY_OVERVIEW_OR_PRACTICAL_INFORMATION_ACTIONS_APP = "下载app";
    public static final String CITY_OVERVIEW_OR_PRACTICAL_INFORMATION_SIDE_FRAME_DIRECTORY = "侧拉框目录";
    public static final String CITY_OVERVIEW_OR_PRACTICAL_INFORMATION_CLICK_THE_CATELOG = "点击目录";

    public static void addCityOverviewActions(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), CITY_OVERVIEW_OR_PRACTICAL_INFORMATION_ACTIONS, action);
        }
    }

    // 搜索数据
    private static final String SEARCH_ACTIONS = "搜索";
    public static final String SEARCH_ENTRANCE = "搜索入口";
    public static final String SEARCH_CLICK = "点击搜索";
    public static final String SEARCH_POI = "点击poi";
    public static final String SEARCH_HISTORY = "历史搜索";
    public static final String SEARCH_CLEAR_HISTORY = "清空历史";

    public static void addSearchActions(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), SEARCH_ACTIONS, action);
        }
    }

    // 推荐列表数据
    private static final String CHOSEN_DETAIL_ACTIONS = "精选详情页";
    public static final String HOT_DETAIL = "热门精选进入";
    public static final String ATTRACTION_DETAIL = "景点";
    public static final String FOOD_DETAIL = "美食";
    public static final String HOTEL_DETAIL = "住宿";
    public static final String SHOPPING_DETAIL = "购物";
    public static final String ACTIVITY_DETAIL = "活动";
    public static final String ITINERARY_DETAIL = "路线";
    public static final String COLLECT_DETAIL = "收藏";
    public static final String PRAISE_DETAIL = "点赞";
    public static final String COMMENT_DETAIL = "点评";
    public static final String RECOMMEND_DETAIL = "推荐详情";
    public static final String MAP_DETAIL = "地图";
    public static final String LOCATION_DETAIL = "定位";
    public static final String POI_DETAIL = "点击poi";
    public static final String POI_GO_DETAIL = "poi详情";
    public static final String NAVIGATION_DETAIL = "导航";

    public static void addChosenDetailActions(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), CHOSEN_DETAIL_ACTIONS, action);
        }
    }

    public static String getChosenDetailName(int position) {
        String action = "";
        switch (position) {

            case 0:
                action = ATTRACTION_DETAIL;
                break;

            case 1:
                action = FOOD_DETAIL;
                break;

            case 2:
                action = ITINERARY_DETAIL;
                break;

            case 3:
                action = HOTEL_DETAIL;
                break;

            case 4:
                action = SHOPPING_DETAIL;
                break;

            case 5:
                action = ACTIVITY_DETAIL;
                break;

        }
        return action;
    }

    // 具体详情页
    private static final String POI_DETAIL_ACTIONS = "具体详情页";
    public static final String ACTIVITY_RECOMMEND_DETAIL = "活动详情";
    public static final String FOOD_RECOMMEND_DETAIL = "美食介绍";
    public static final String SHOPPING_RECOMMEND_DETAIL = "伴手礼介绍";
    public static final String COLLECT_RECOMMEND_DETAIL = "收藏";
    public static final String PRAISE_RECOMMEND_DETAIL = "点赞";
    public static final String COMMEND_RECOMMEND_DETAIL_ADAPTER = "点评详情";
    public static final String COMMEND_RECOMMEND_DETAIL = "点评";
    public static final String PIC_LOOK_DETAIL = "图片浏览";
    public static final String MAP_RECOMMEND_DETAIL = "地图";
    public static final String NET_LOOK_DETAIL = "网址";
    public static final String PHONE_RECOMMEND_DETAIL = "电话";
    public static final String LOCATION_RECOMMEND_DETAIL = "定位";
    public static final String NAVIGATION_RECOMMEND_DETAIL = "导航";
    public static final String UBER_RECOMMEND_DETAIL = "uber";
    public static final String ASK_RECOMMEND_DETAIL = "问路模式";
    public static final String NEARBY_RECOMMEND_POI = "附近poi";
    public static final String AGODA_COM = "agoda.com";
    public static final String BOOKING_COM = "booking.com";

    public static void addPoiDetailActions(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), POI_DETAIL_ACTIONS, action);
        }
    }

    // 动态列表
    private static final String DYNAMIC_ACTIONS = "动态列表";
    public static final String SEND_PAGE_DYNAMIC = "发动态页";
    public static final String SEND_DYNAMIC = "发布";
    public static final String DETAIL_DYNAMIC = "动态详情页";
    public static final String DEL_DYNAMIC = "删除动态";
    public static final String REPORT_DYNAMIC = "举报动态";
    public static final String DEL_YES_DYNAMIC = "确认删除动态";
    public static final String REPORT_YES_DYNAMIC = "确认举报动态";
    public static final String DYNAMIC_ATTENTION = "关注";

    public static void addDynamicActions(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), DYNAMIC_ACTIONS, action);
        }
    }

    // 动态详情页
    private static final String DYNAMIC_DETAIL_ACTIONS = "动态详情";
    public static final String DEL_DYNAMIC_DETAIL = "删除动态";
    public static final String REPORT_DYNAMIC_DETAIL = "举报动态";
    public static final String DEL_YES_DYNAMIC_DETAIL = "确认删除动态";
    public static final String REPORT_YES_DYNAMIC_DETAIL = "确认举报动态";
    public static final String PIC_LOOK_DYNAMIC_DETAIL = "图片浏览";
    public static final String PRAISE_DYNAMIC_DETAIL = "赞";
    public static final String LOOK_COMMENT_DYNAMIC_DETAIL = "查看回复";
    public static final String REPORT_CONTENT_DYNAMIC_DETAIL = "回复动态";
    public static final String REPORT_COMMENT_CONTENT_DYNAMIC_DETAIL = "回复楼层";
    public static final String REPORT_COMMENT_YES_DYNAMIC_DETAIL = "确认回复";
    public static final String THUMBS_LIST_DYNAMIC_DETAIL = "点赞列表";
    public static final String OTHERS_GO_DYNAMIC_DETAIL = "他人主页进入";

    public static void addDynamicDetailActions(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), DYNAMIC_DETAIL_ACTIONS, action);
        }
    }

    // 主体数据
    private static final String MAIN_BODY_ACTIONS = "主页";
    public static final String DYNAMIC = "动态";
    public static final String FIND = "发现";
    public static final String NEWS = "消息";
    public static final String ME = "我";

    public static void addMainBodyActions(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), MAIN_BODY_ACTIONS, action);
        }
    }

    // 操作数据
    private static final String USE_ACTIONS = "我";
    public static final String LOGIN_WEIBO = "微博登陆";
    public static final String LOGIN_WEIXING = "微信登陆";
    public static final String LOGIN_ACCOUNT = "步步账号登陆";
    public static final String REGISTER_PHONE = "手机号码注册";
    public static final String REGISTER_OTHER = "其他号码注册";
    public static final String FORGET_PASSWORD = "忘记密码";
    public static final String RESET_PASSWORD = "重置密码";
    public static final String CREATE_ACCOUNT = "创建账号";
    public static final String CHANGE_PASSWORD = "修改密码";
    public static final String GO_LOGIN = "点击登陆";
    public static final String COMPLETE_CHANGE_PASSWORD = "完成修改密码";
    public static final String PERSONAL_INFORMATION = "个人信息";
    public static final String CHANGE_ICON = "修改头像";
    public static final String SIGN_OUT = "退出登陆";
    public static final String MY_DYNAMIC = "我的动态";
    public static final String MY_DYNAMIC_DETAIL = "我的动态进入详情";
    public static final String MY_DYNAMIC_DETAIL_DEL = "删除";
    public static final String MY_DYNAMIC_DETAIL_DEL_YES = "确认删除";
    public static final String MY_COLLECT = "收藏";
    public static final String MY_DRAFTS = "草稿箱";
    public static final String MY_DRAFTS_DEL = "草稿箱删除";
    public static final String MY_DRAFTS_RESEND = "草稿箱重发";
    public static final String MY_DRAFTS_DETAIL = "草稿箱详情";
    public static final String CONTENT_REPLY = "点评";
    public static final String CONTENT_REPLY_YES = "确认点评";
    public static final String CHANGE_USER_NAME = "修改用户名";
    public static final String CHANGE_USER_NAME_YES = "确认修改用户名";
    public static final String CHANGE_SIGNATURE = "修改个性签名";
    public static final String CHANGE_SIGNATURE_YES = "确认修改个性签名";
    public static final String REGISTER_WEIXING = "微信注册";
    public static final String REGISTER_WEIBO = "微博注册";
    public static final String REGISTER_PHONT = "手机注册";
    public static final String REGISTER_ACCOUNT = "账号注册";

    public static void addUseActions(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), USE_ACTIONS, action);
        }
    }

    // 收藏跳转数据
    private static final String SETTING_ACTIONS = "设置";
    public static final String SETTING = "设置";
    public static final String CLEAR_CACHE = "清理缓存";
    public static final String SUPPORT_FEEDBACK = "支持与反馈";
    public static final String WE_CHAT_NUMBER = "步步菌微信号";
    public static final String AUSTRALIA_GROUP = "澳洲群";
    public static final String STEP_BY_STEP_GUIDE_GROUP = "步步指南群";
    public static final String CLOUD_WEBSITE = "云端网站";
    public static final String SETTING_SINA = "微博";
    public static final String WEIXING_PUBLIC_NUM = "微信公众号";
    public static final String USER_CUSTOMER_FEEDBACK = "用户反馈";
    public static final String RECOMMENDED_APP = "推荐好友";
    public static final String COMPLETE_FEEDBACK = "反馈完成";
    public static final String APP_EVALUATION = "app评价";

    public static void addSettingActions(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), SETTING_ACTIONS, action);
        }
    }

    // 信息
    private static final String INFORMATION = "信息";
    public static final String NOTICE_LIST = "通知列表";
    public static final String COMMENT_LIST = "评论列表";
    public static final String PRAIST_LIST = "赞列表";
    public static final String NOTICE_DETAIL = "通知详情";
    public static final String PRAIST_DYNAMIC_DETAIL = "赞动态详情";
    public static final String COMMENT_DYNAMIC_DETAIL = "评论动态详情";
    public static final String REPLY_TO_COMMENT = "回复";
    public static final String REPLY_TO_COMMENT_YES = "确认回复";
    public static final String INFORMATION_BANNER = "banner_grid";
    public static final String INFORMATION_NET = "网站";
    public static final String INFORMATION_DYNAMIC = "动态";
    public static final String INFORMATION_GUIDE = "精选";

    public static void addInformationActions(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), INFORMATION, action);
        }
    }

    // 推送
    private static final String PUSH = "推送";
    public static final String PUSH_RECOMMEND_LIST = "精选列表";
    public static final String PUSH_COMMENT_LIST = "评论";
    public static final String PUSH_PRAIST_LIST = "赞";
    public static final String PUSH_POST_DETAIL = "动态";
    public static final String PUSH_NET_DETAIL = "网址";
    public static final String PUSH_APP_DETAIL = "打开app";

    public static void addPushActions(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), PUSH, action);
        }
    }

    // 他人主页
    private static final String OTHERS_HOME = "他人主页";
    public static final String DYNAMIC_GO_OTHERS = "动态进入";
    public static final String BACTERIA_GO_OTHERS = "菌落进入";
    public static final String MY_DYNAMIC_GO_OTHERS = "我的动态进入";
    public static final String THUMBS_LIST_GO_OTHERS = "点赞列表进入";
    public static final String WATCHLIST_GO_OTHERS = "关注";
    public static final String FANS_GO_OTHERS = "粉丝";
    public static final String WATCHLIST_GO_OTHERS_LIST = "关注列表";
    public static final String FANS_GO_OTHERS_LIST = "粉丝列表";
    public static final String ATTENTION = "关注用户";
    public static final String UN_ATTENTION = "取消关注用户";

    public static void othersHome(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), OTHERS_HOME, action);
        }
    }

    // 菌落
    private static final String BACTERIS_LIST = "菌落";
    public static final String DYNAMIC_TITLE = "动态";
    public static final String BACTERIS_TITLE = "菌落";
    public static final String BACTERIS_TITLE_LIST = "菌落列表";
    public static final String BACTERIS_TITLE_LIST_DETAILS = "菌落动态详情";
    public static final String BACTERIS_TITLE_LIST_DETAILS_SEND = "菌落动态发送界面";

    public static void bacteriaList(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), BACTERIS_LIST, action);
        }
    }

    // 切换城市
    private static final String CHANGE_CITY = "切换城市";
    public static final String SELECT_CHANGE = "切换";

    public static void changeCity(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), CHANGE_CITY, action);
        }
    }

    // banner统计
    private static final String BANNER = "banner_grid";

    public static void addBanner(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), BANNER, action);
        }
    }

    // banner统计
    private static final String SHOP = "商品";
    public static final String MAIN_SHOP = "商城_商品介绍";
    public static final String POI_SHOP = "poi_商品介绍";
    public static final String TAOKE = "跳转淘客";

    public static void addShop(String action) {
        if (IS_OPEN_AVANALY) {
            AVAnalytics.onEvent(BaseApplication.getInstance().getApplicationContext(), SHOP, action);
        }
    }

}
