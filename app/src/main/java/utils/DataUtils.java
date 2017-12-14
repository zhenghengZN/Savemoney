package utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import so.bubu.lib.helper.PreferencesHelper;

/**
 * dataUtils
 * Created by Auro on 8/21/15.
 */
public class DataUtils {

    private final static int TIMEZONE_LENGTH = 424;

    private final static int CURRENCY_LENGTH = 19;

    /**
     * 用户类型
     */
    public static final String USER_TYPE_WEIBO = "weibo";

    public static final String USER_TYPE_WEIXIN = "weixin";

    public static final String USER_TYPE_BUBU = "bubu";

    /**
     * 图片选择
     */
    public static final int REQUEST_PICK = 0;
    /**
     * 请求裁剪
     */
    public static final int REQUEST_CROP = 2;

    private static DataUtils INSTANCE;

    public static DataUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new DataUtils();
        return INSTANCE;
    }

    private final String USER_SETTING = "USER_SETTING";

    private final String CHECK_LIST_STATE = "CHECK_LIST_STATE";

    private final String CHECK_LIST_SHOW = "SHOW";

    private final String CHECK_LIST_HIDE = "HIDE";

    private final String TODO_ALARM_ID = "TODO_ALARM_ID";

    private final String CURRENT_CURRENCY = "CURRENT_CURRENCY";

    private final String CURRENT_PICK_TIME = "CURRENT_PICK_TIME";

    private final String LAST_TIMEZONE = "LAST_TIME_ZONE";

    private final String HOTEST_EVENT_CACHE = "HOT_EVENT_CACHE";

    private final String LASTEST_EVENT_CACHE = "LASTEST_EVENT_CACHE";

    private final String EVENT_LAST_REFRESH_TIME = "EVENT_LAST_REFRESH_TIME";

    private final String USER_TYPE = "USER_TYPE";

    private final String TO_CHECK_IF_VALID_USER = "TO_CHECK_IF_VALID_USER";

    private final String BANNER_DATA = "BANNER_DATA";

    private final String GUIDE_PAGE_SHOWED = "GUIDE_PAGE_SHOWED";

//    public void setCheckListState(Context context, boolean state) {
//        if (state)
//            BasicUtils.putSharedPreferences(context, USER_SETTING, CHECK_LIST_STATE, CHECK_LIST_SHOW);
//        else
//            BasicUtils.putSharedPreferences(context, USER_SETTING, CHECK_LIST_STATE, CHECK_LIST_HIDE);
//    }
//
//    public boolean getCheckListState(Context context) {
//        String data = BasicUtils.getSharedPreferences(context, USER_SETTING, CHECK_LIST_STATE);
//        if (BasicUtils.judgeNotNull(data) && CHECK_LIST_HIDE.equals(data)) {
//            return false;
//        } else
//            return true;
//    }
//
//    public void setCurrentCurrency(Context context, Currency currency) {
//        if (BasicUtils.judgeNotNull(currency)) {
//            BasicUtils.putSharedPreferences(context, USER_SETTING, CURRENT_CURRENCY, JSON.toJSONString(currency));
//        }
//    }
//
//    public Currency getCurrentCurrency(Context context) {
//        String data = BasicUtils.getSharedPreferences(context, USER_SETTING, CURRENT_CURRENCY);
//        if (BasicUtils.judgeNotNull(data)) {
//            return JSON.parseObject(data, Currency.class);
//        }
//        return CurrencyDAO.getInstance().findByCode("CNY");
//    }

//    public void setLastTimeZoneName(Context context, String timeZoneName) {
//        if (BasicUtils.judgeNotNull(timeZoneName)) {
//            BasicUtils.putSharedPreferences(context, USER_SETTING, LAST_TIMEZONE, timeZoneName);
//        }
//    }
//
//    public String getLastTimeZoneName(Context context) {
//        String timeZoneName = BasicUtils.getSharedPreferences(context, USER_SETTING, LAST_TIMEZONE);
//        if (BasicUtils.judgeNotNull(timeZoneName)) {
//            return timeZoneName;
//        }
//        return "Asia/Shanghai";
//    }
//
//    public void setCurrentPickTime(Context context, Date date) {
//        if (BasicUtils.judgeNotNull(date)) {
//            BasicUtils.putSharedPreferences(context, USER_SETTING, CURRENT_PICK_TIME, JSON.toJSONString(date));
//        }
//    }
//
//    public Date getCurrentPickTime(Context context) {
//        String data = BasicUtils.getSharedPreferences(context, USER_SETTING, CURRENT_PICK_TIME);
//        if (BasicUtils.judgeNotNull(data)) {
//            return JSON.parseObject(data, Date.class);
//        }
//        return new Date();
//    }

//    public Calendar getCurrentPickCalendar(Context context) {
//        Date date = getCurrentPickTime(context);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        return calendar;
//    }

//    public void initCurrencyList(Context context) throws Exception {
//        if (isCurrencyInited()) {
//            Logs.d("BUBU", "currency list already exist");
//            return;
//        }
//        Logs.d("BUBU", "load currency list");
//        String fileName = "currency.json";
//        String jsonString = readFile(context, fileName);
//        // convert
//        List<Currency> currencies = JSON.parseObject(jsonString, new TypeToken<List<Currency>>() {
//        }.getType());
//        if (BasicUtils.judgeNotNull(currencies)) {
//            //save to db
//            for (Currency currency : currencies) {
//                currency.save();
//            }
//        }
//
//    }
//
//    private boolean isCurrencyInited() {
//        return CurrencyDAO.getInstance().countAll() == CURRENCY_LENGTH;
//    }

//    public void initCheckList(Context context) throws Exception {
//        Log.d("BUBU", "load checklist");
//        String fileName = "checkList.json";
//        String jsonString = readFile(context, fileName);
//        // convert
//        List<Checklist> checklists = JSON.parseObject(jsonString, new TypeToken<List<Checklist>>() {
//        }.getType());
//        if (BasicUtils.judgeNotNull(checklists)) {
//            //save to db
//            for (int i = 0; i < checklists.size(); i++) {
//                checklists.get(i).setOrderId(i);
//                checklists.get(i).setUser(UserService.getCurrentUser(context));
//                checklists.get(i).saveWithTime();
//            }
//        }
//    }

    private String readFile(Context context, String fileName) throws Exception {
        String jsonString;
        //read file

        StringBuilder buf = new StringBuilder();
        InputStream json = context.getAssets().open(fileName);
        BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
        String str;

        while ((str = in.readLine()) != null) {
            buf.append(str);
        }

        in.close();

        jsonString = buf.toString();
        return jsonString;
    }

//    public void initTimeZoneList(Context context) throws Exception {
//        if (isTimeZoneInited()) {
//            Log.d("BUBU", "time zone list already exist");
//            return;
//        }
//        Log.d("BUBU", "load time zone list");
//        String fileName = "timeZone.json";
//        String jsonString = readFile(context, fileName);
//        // convert
//        List<TimeZone> timeZones = JSON.parseObject(jsonString, new TypeToken<List<TimeZone>>() {
//        }.getType());
//        if (timeZones != null && timeZones.size() > 0) {
//            //save to db
//            for (TimeZone timeZone : timeZones) {
//                timeZone.save();
//            }
//        }
//    }
//
//    private boolean isTimeZoneInited() {
//        return TimeZoneDAO.getInstance().countAll() == TIMEZONE_LENGTH;
//    }

    /**
     * 存取闹钟ID
     * TODO_ALARM_ID
     */
//    public void saveAlarm(Context context, List<Integer> alarmId) {
//        if (context != null && alarmId != null) {
//            BasicUtils.putSharedPreferences(context, USER_SETTING, TODO_ALARM_ID, JSON.toJSONString(alarmId));
//        }
//    }
//
//    public List<Integer> getAlarm(Context context) {
//        if (context != null) {
//            String data = BasicUtils.getSharedPreferences(context, USER_SETTING, TODO_ALARM_ID);
//            if (BasicUtils.judgeNotNull(data)) {
//                return JSON.parseObject(data, new TypeToken<List<Integer>>() {
//                }.getType());
//            }
//        }
//        return null;
//    }

    /**
     * 存取当前用户的最新最热列表的JSON
     */
//    public void saveTempEvent(Context context, List<Event> eventList, boolean isHotest) {
//        //get object id string s
//        List<String> objectIdList = new ArrayList<>();
//        String jsonToSave = "";
//        if (BasicUtils.judgeNotNull(eventList)) {
//            Logs.d("cai", "存储了" + eventList.size());
//            for (Event event : eventList) {
//                objectIdList.add(event.getCloudId());
//            }
//            jsonToSave = JSON.toJSONString(objectIdList);
//        }
//        //get current user id
//        User currentUser = UserService.getCurrentUser(context);
//        String cloudId = "";
//        if (currentUser != null)
//            cloudId = currentUser.getCloudId();
//        if (isHotest) {
//            BasicUtils.putSharedPreferences(context, USER_SETTING, HOTEST_EVENT_CACHE + cloudId, jsonToSave);
//        } else {
//            BasicUtils.putSharedPreferences(context, USER_SETTING, LASTEST_EVENT_CACHE + cloudId, jsonToSave);
//        }
//    }
//
//    public List<Event> getTempEvent(Context context, boolean isHotest) {
//        String data = "";
//
//        //get user id
//        User currentUser = UserService.getCurrentUser(context);
//        String cloudId = "";
//        if (currentUser != null)
//            cloudId = currentUser.getCloudId();
//        //get event 's  obejct id list
//        if (isHotest) {
//            data = BasicUtils.getSharedPreferences(context, USER_SETTING, HOTEST_EVENT_CACHE + cloudId);
//        } else {
//            data = BasicUtils.getSharedPreferences(context, USER_SETTING, LASTEST_EVENT_CACHE + cloudId);
//        }
//        List<Event> eventList = new ArrayList<>();
//        //get event
//        if (BasicUtils.judgeNotNull(data)) {
//            List<String> objectIdList = JSON.parseObject(data, new TypeToken<List<String>>() {
//            }.getType());
//            if (BasicUtils.judgeNotNull(objectIdList)) {
//                for (String objectId : objectIdList) {
//                    Event event = EventService.getInstance().getTempEventFromDB(objectId);
//                    if (event != null)
//                        eventList.add(event);
//                    else
//                        Logs.d("cai", "找不到了。。。");
//                }
//            }
//        }
//        return eventList;
//
//    }

//    public void saveLastEventRefreshTime(Context context) {
//        Date now = new Date();
//        BasicUtils.putSharedPreferences(context, USER_SETTING, EVENT_LAST_REFRESH_TIME, "" + now.getTime());
//    }
//
//    public boolean isSameDayRefresh(Context context) {
//        String stringOfDate = BasicUtils.getSharedPreferences(context, USER_SETTING, EVENT_LAST_REFRESH_TIME);
//        if (BasicUtils.judgeNotNull(stringOfDate)) {
//            Date lastDay = new Date(Long.parseLong(stringOfDate));
//            Logs.d("cai", "转换得到" + lastDay.toString());
//            return DateUtils.getInstance().checkSameDay(new Date(), lastDay);
//        } else {
//            return false;
//        }
//    }

    public void setUserType(Context context, String userType) {
        PreferencesHelper.getInstance().putString(USER_TYPE, userType);
    }

    public String getUserType(Context context) {

        return PreferencesHelper.getInstance().getString(USER_TYPE);
    }

    /**
     * 检查是否需要重新请求 重新登陆
     */
//    //TODO 2015-12-25日加入该代码 可以在稳定之后删掉该段代码节省无谓检查
//    public void setHasChecked(Context context) {
//        Log.d("cai", "setHasChecked 保存为已经检查过");
//        BasicUtils.putSharedPreferences(context, USER_SETTING, TO_CHECK_IF_VALID_USER, "yes");
//    }
//
//    public boolean getHasChecked(Context context) {
//        String result = BasicUtils.getSharedPreferences(context, USER_SETTING, TO_CHECK_IF_VALID_USER);
//        if (BasicUtils.judgeNotNull(result) && result.equals("yes")) {
//            //如果已经检查过
//            Log.d("cai", "getHasChecked 已经检查过");
//            return true;
//        }
//        Log.d("cai", "getHasChecked 没有检查过");
//        return false;
//    }

    /**
     * 存取本地banner
     */
//    public void saveBannerData(Context context, List<BannerData> bannerDataList) {
//        String data = null;
//        if (bannerDataList != null) {
//            data = JSON.toJSONString(bannerDataList);
//        }
//        BasicUtils.putSharedPreferences(context, USER_SETTING, BANNER_DATA, data);
//    }
//
//    public List<BannerData> getBannerData(Context context) {
//        String result = BasicUtils.getSharedPreferences(context, USER_SETTING, BANNER_DATA);
//        if (BasicUtils.judgeNotNull(result)) {
//            return JSON.parseObject(result, new TypeToken<List<BannerData>>() {
//            }.getType());
//        }
//        return null;
//    }
//
//    /**
//     * 记录是否开过引导页
//     */
//    public boolean isGuidePageShowed(Context context) {
//        String result = BasicUtils.getSharedPreferences(context, USER_SETTING, GUIDE_PAGE_SHOWED);
//        if (BasicUtils.judgeNotNull(result)) {
//            if ("yes".equals(result)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void setIsGuidPageShowed(Context context) {
//        BasicUtils.putSharedPreferences(context, USER_SETTING, GUIDE_PAGE_SHOWED, "yes");
//    }

}
