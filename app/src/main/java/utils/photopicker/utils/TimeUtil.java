package utils.photopicker.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import so.bubu.lib.helper.Helper;

/**
 * 转换UTC时间为自己的格式
 * Created by wangwn on 2016/5/5.
 */
public class TimeUtil {


    public final static int DATE_FORMAT_WITH_WEEKDAY = 1;//2014年12月1日 星期一
    public final static int DATE_FORMAT_WITH_WEEKDAY2 = 2;//2014年12月1日 周一
    public final static int DATE_FORMAT_WITH_TIME = 3;//2014年12月1日 00:00
    public final static int DATE_FORMAT_ONLY_TIME = 4;//12:00
    public final static int DATE_FORMAT_ONLY_DATE = 5;//12月05日
    public final static int DATE_FORMAT_YEAR_DATE = 6;//2014年12月05日

    //local timezone
    private static TimeZone localTimeZone;

    //    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    /*根据服务器格式修改成对应的就好。*/

    /* 将Server传送的UTC时间转换为指定时区的时间 */
    @SuppressLint("SimpleDateFormat")
    public static String converTime(String srcTime, TimeZone timezone) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        SimpleDateFormat dspFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String convertTime;
        Date result_date;
        long result_time = 0;
        // 如果传入参数异常，使用本地时间
        if (null == srcTime)
            result_time = System.currentTimeMillis();
        else {
            try { // 将输入时间字串转换为UTC时间
                sdf.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
                result_date = sdf.parse(srcTime);
                result_time = result_date.getTime();
            } catch (Exception e) { // 出现异常时，使用本地时间
                result_time = System.currentTimeMillis();
                dspFmt.setTimeZone(TimeZone.getDefault());
                convertTime = dspFmt.format(result_time);
                return convertTime;
            }
        }
        // 设定时区
        dspFmt.setTimeZone(timezone);
        convertTime = dspFmt.format(result_time);
        return convertTime;
    }


    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate(Date date) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        String dateString = formatter.format(date);
        return dateString;
    }


    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String getMonthAndDayTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(date);
    }

    public static String getSpendTime(Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            int totalMinutes = getMinuteOfTwoDate(startDate, endDate);
            int h = totalMinutes / 60;
            int m = totalMinutes % 60;
            String spend = "";
            if (h > 0)
                spend += h + "小时";
            if (m > 0)
                spend += m + "分钟";
            return spend;
        }
        return "";
    }

    public static String getSpendTime(int totalMinutes) {
        if (Helper.isNotNull(totalMinutes)) {
            int h = totalMinutes / 60;
            int m = totalMinutes % 60;
            int day = totalMinutes / (60 * 60);
            String spend = "";
            if (day > 0)
                spend += day + "天";
            if (h > 0)
                spend += h + "小时";
            if (m > 0 && 0 == day)
                spend += m + "分钟";
            return spend;
        }
        return "";
    }

    public static int getMinuteOfTwoDate(Date start, Date end) {
        if (end != null && start != null && end.getTime() > start.getTime()) {
            return Math.round(end.getTime() - start.getTime()) / 60000;
        } else
            return 0;
    }

    public int getDaysOfTwoDate(Date dateSmall, String smallTimeZoneName, Date dateBig, String bigTimeZoneName) {
        if (dateSmall != null && dateBig != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
            try {
                Date dateSmallWithOutHour = simpleDateFormat.parse(formatDate(dateSmall, DATE_FORMAT_YEAR_DATE, smallTimeZoneName));
                Date dateBigWithOutHour = simpleDateFormat.parse(formatDate(dateBig, DATE_FORMAT_YEAR_DATE, bigTimeZoneName));
                return (int)((dateBigWithOutHour.getTime() - dateSmallWithOutHour.getTime()) / 86400000 );
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }

        }
        return 0;
    }


    public static String formatDate(Date date, int type, String timeZoneName) {
        return formatDate(date, type, timeZoneName, false);
    }

    public static String formatDate(Date date, int type, String timeZoneName, boolean removeAbbr) {
        TimeZone timeZone = getTimeZoneByName(timeZoneName);
        if (!removeAbbr) {
            return formatDate(date, timeZone, type) + getTimeZoneOffset(timeZoneName);
        } else {
            return formatDate(date, timeZone, type);
        }
    }

    public static String getTimeZoneOffset(String timeZoneName) {
        if (timeZoneName == null || timeZoneName.isEmpty())
            return "";
        String ret = "";
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneName);
        if (timeZone != null && !timeZone.hasSameRules(getLocalTimeZone())) {
            //TODO 优化以省略查询
            //mode 2 display name ss GMT+8
            ret = timeZone.getDisplayName(false, TimeZone.SHORT);
//            com.bubu.steps.model.local.TimeZone localZone = TimeZoneDAO.getInstance().findByTimeZoneName(timeZoneName);
//            if (localZone != null)
//                ret = localZone.getAbbr();
        }
        ret = ret.replace(":00", "");
        return " " + ret;
    }

    public static TimeZone getLocalTimeZone() {
        if (localTimeZone == null) {
            localTimeZone = TimeZone.getDefault();
        }
        return localTimeZone;
    }

    public static TimeZone getTimeZoneByName(String timeZoneName) {
        TimeZone timeZone;
        if (Helper.isNotEmpty(timeZoneName))
            timeZone = TimeZone.getTimeZone(timeZoneName);
        else {
            timeZone = TimeZone.getDefault();
        }
        return timeZone;
    }

    public static String formatDate(Date date, TimeZone timeZone, int type) {
        if (date == null)
            return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getFormatType(type), Locale.getDefault());
        if (timeZone != null) {
            simpleDateFormat.setTimeZone(timeZone);
        }
        return simpleDateFormat.format(date);
    }

    private static String getFormatType(int type) {
        switch (type) {
            case DATE_FORMAT_WITH_WEEKDAY:
                return "yyyy年MM月dd日 E";
            case DATE_FORMAT_WITH_WEEKDAY2:
                return "yyyy年MM月dd日 E";
            case DATE_FORMAT_WITH_TIME:
                return "yyyy年MM月dd日 HH:mm";
            case DATE_FORMAT_ONLY_TIME:
                return "HH:mm";
            case DATE_FORMAT_ONLY_DATE:
                return "MM月dd日";
            case DATE_FORMAT_YEAR_DATE:
                return "yyyy年MM月dd日";
            default:
                return "yyyy年MM月dd日";
        }
    }

    public static Date changeDateToTimeZone(Date date, String toTimeZoneName) {
        if (toTimeZoneName == null)
            return date;
        return changeDateFromTimeZoneToTimeZone(date, "UTC", toTimeZoneName);
    }

//    public Date changeDateFromLocalToTimeZone(Date date, String toTimeZoneName) {
//        return changeDateFromTimeZoneToTimeZone(date, getLocalTimeZoneName(), toTimeZoneName);
//    }

    public static Date changeDateFromTimeZoneToTimeZone(Date date, String fromTimeZoneName, String toTimeZoneName) {
        if (date == null)
            return null;
        if (isSameTimeZone(fromTimeZoneName, toTimeZoneName)) {
            return date;
        }
        TimeZone fromTimeZone = TimeZone.getTimeZone(fromTimeZoneName);
        TimeZone toTimeZone = TimeZone.getTimeZone(toTimeZoneName);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault());

        simpleDateFormat.setTimeZone(fromTimeZone);

        String stringOfDate = simpleDateFormat.format(date);

        Log.d("cai", "stringOfDate = " + stringOfDate);

        simpleDateFormat.setTimeZone(toTimeZone);

        try {
            return simpleDateFormat.parse(stringOfDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean isSameTimeZone(String timeZoneName1, String timeZoneName2) {
        TimeZone timeZone1 = TimeZone.getTimeZone(timeZoneName1);
        TimeZone timeZone2 = TimeZone.getTimeZone(timeZoneName2);
        return timeZone1.hasSameRules(timeZone2);
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
