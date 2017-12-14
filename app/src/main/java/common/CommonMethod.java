package common;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhekouxingqiu.main.R;
import so.bubu.lib.base.BaseApplication;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.TimeHelper;

/**
 * 公用方法
 *
 * Created by Administrator on 2016/3/22.
 */
public class CommonMethod {

    public static final String BEIJING_GMT = "GMT+8:00";

    /**
     * 若已经是当前界面则不需要设置
     *
     * @param viewPager
     * @param type
     */
    public static void setViewPagerItem(ViewPager viewPager, int type) {
        if (type != viewPager.getCurrentItem()) {
            viewPager.setCurrentItem(type);
        }
    }


    /**
     * 获取滑动距离
     *
     * @param listView
     * @return
     */
    public static int getScrollY(ListView listView) {
        View c = listView.getChildAt(0);
        if (Helper.isNull(c)) {
            return 0;
        }
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int top = c.getTop();
        int calc = -top + firstVisiblePosition * c.getHeight();
        return 0 > calc ? 0 : calc;
    }

    public static Drawable setDrawable(int id) {
        Drawable drawables = BaseApplication.getInstance().getResources().getDrawable(id);
        drawables.setBounds(0, 0, drawables.getMinimumWidth(), drawables.getMinimumHeight());
        return drawables;
    }

    /**
     * 字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 截取数字
     *
      * @param content
     *  @return
     */
    public static String getStringToNumber(String content) {
        String regEx="[^0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(content);
        return matcher.replaceAll("").trim();
    }

    public static String getThumbUrl(String url, int width, int height) {
        return getThumbUrl(url, width, height, 90);
    }

    public static String getThumbUrl(String url, int width, int height, int quality) {
        if (Helper.isNotEmpty(url) && (url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".jpeg"))) {
            url += ("?imageView/1/w/" + width + "/h/" + height + "/q/" + quality + "/format/jpg");
        }
        return url;
    }



    public static String showDurationTime(String time) {
        int durationTime = Integer.valueOf(time);
        int m = 60;
        int h = m * 60;
        int day = h * 24;
        String showTime = "";
        boolean isDay = false;
        if (0 < (durationTime / day)) {
            showTime += ((durationTime / day) + "天");
            durationTime = durationTime - ((durationTime / day) * day);
            isDay = true;
        }
        if (0 < (durationTime / h)) {
            showTime += ((durationTime / h) + "小时");
            durationTime = durationTime - ((durationTime / h) * h);
            if (isDay) {
                return showTime;
            }
        }
        if (0 < (durationTime / m)) {
            showTime += ((durationTime / m) + "分钟");
        }
        return showTime;
    }





    public static String showLocalTime(Date date) {

        if (Helper.isEmpty(date)) {
            return "刚刚";
        }
        String showContent;
        long sendTime = date.getTime() / 1000;

        long currentTime = TimeHelper.getTimestamp();
        long thisYearTime = TimeHelper.getTimesThisYear();
        long todayTimestamp = TimeHelper.getTimesmorning();
        long yesterdayTimestamp = todayTimestamp - 60 * 60 * 24;
        long beforeYesterdayTimestamp = yesterdayTimestamp - 60 * 60 * 24;
        int timeDifference = (int) (currentTime - sendTime);
        if (sendTime < thisYearTime) {
            showContent = TimeHelper.timeToData(sendTime, TimeHelper.YYYYMMDD);
        } else if (sendTime < beforeYesterdayTimestamp) {
            showContent = TimeHelper.timeToData(sendTime, TimeHelper.MMDD);
        } else if (sendTime < yesterdayTimestamp) {
            showContent = "前天 " + TimeHelper.timeToData(sendTime, TimeHelper.HHMM);
        } else if (sendTime < todayTimestamp) {
            showContent = "昨天 " + TimeHelper.timeToData(sendTime, TimeHelper.HHMM);
        } else if (sendTime == currentTime) {
            showContent = "刚刚";
        } else if (60 > timeDifference) {
            showContent = (0 > timeDifference ? 0 : timeDifference) + "秒前";
        } else if (60 * 60 > timeDifference) {
            timeDifference = timeDifference / 60;
            showContent = (0 > timeDifference ? 0 : timeDifference) + "分钟前";
        } else {
            timeDifference = timeDifference / 60 / 60;
            showContent = (0 > timeDifference ? 0 : timeDifference) + "小时前";
        }

        return showContent;
    }






//    public static void changeCity(String locationId, double longtitude, double latitude) {
//        SharedPreferencesHelp.setLocationId(locationId);
//        SharedPreferencesHelp.setLocationLongitude(longtitude);
//        SharedPreferencesHelp.setLocationLatitude(latitude);
//        EventBus.getDefault().post(new ChangeCityEvent(ChangeCityEvent.CHANGE_CITY));
//    }


    public static String showTimeFormat(String formatTime, String oldFormat, String timeFormat) {
        if (Helper.isEmpty(formatTime)) {
            return "";
        }
        return TimeHelper.timeToData(TimeHelper.getTimeLong(formatTime, oldFormat), timeFormat);
    }

    public static String parseBestTime(String time) {
        Map<String, Integer> moneyMap = new HashMap<>();
        moneyMap.put("Jan", 1);
        moneyMap.put("Feb", 2);
        moneyMap.put("Mar", 3);
        moneyMap.put("Apr", 4);
        moneyMap.put("May", 5);
        moneyMap.put("June", 6);
        moneyMap.put("July", 7);
        moneyMap.put("Aug", 8);
        moneyMap.put("Sept", 9);
        moneyMap.put("Oct", 10);
        moneyMap.put("Nov", 11);
        moneyMap.put("Dec", 12);
        int start = 0;
        int end = 0;
        String showData = "";
        while (-1 != end) {
            end = time.indexOf(",", start);
            if (-1 != end) {
                showData += (moneyMap.get(time.substring(start, end)) + ",");
            } else {
                showData += (moneyMap.get(time.substring(start, time.length())) + "");
            }
            start = end + 1;
        }
        return showData;
    }

    public static void changeUserIcon(ImageView imageView, String isOfficial, int telentLevel) {
        if (Helper.isNotNull(isOfficial)) {
            if (isOfficial.equals("true")) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setBackgroundResource(R.drawable.icon_admin);
            } else {
                changeUserIcon(imageView, telentLevel);
            }
        } else {
            changeUserIcon(imageView, telentLevel);
        }
    }

    public static void changeUserIcon(ImageView imageView, int telentLevel) {
        if (Helper.isNotNull(telentLevel)) {
            if (0 < telentLevel) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setBackgroundResource(R.drawable.icon_master);
            } else {
                imageView.setVisibility(View.INVISIBLE);
                imageView.setBackground(null);
            }
        } else {
            imageView.setVisibility(View.INVISIBLE);
            imageView.setBackground(null);
        }
    }

    public static void changeUserIcon(ImageView imageView, boolean isOfficial, int telentLevel) {
        if (isOfficial) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setBackgroundResource(R.drawable.icon_admin);
        } else {
            imageView.setVisibility(View.INVISIBLE);
            if (0 < telentLevel) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setBackgroundResource(R.drawable.icon_master);
            } else {
                imageView.setVisibility(View.INVISIBLE);
                imageView.setBackground(null);
            }
        }
    }

}