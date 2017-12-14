package utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

/**
 * CommonUtils
 * Created by Auro on 15/9/22.
 */
public class CommonUtils {

    private static CommonUtils INSTANCE;

    public static CommonUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CommonUtils();
        return INSTANCE;
    }

    public static int SINGLE_CLICK_LENGTH = 666;

    private static long lastClick = 0;

    public static boolean isSingleClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick > CommonUtils.SINGLE_CLICK_LENGTH) {
            lastClick = now;
            return true;
        }
        return false;
    }

//    /**
//     * @param context ApplicationContext
//     */
//    public void getCurrentLocation(Context context) {
//
//        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        LocationListener locationListener = new StepLocationListener(context);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//    }

    //geo:latitude,longitude
    //geo:latitude,longitude?z=zoom，z表示zoom级别，值为数字1到23
    //geo:0,0?q=my+street+address
    //geo:0,0?q=business+near+city
//    public void openGeoUri(Context context, String location) {
//        if (!Helper.isNotEmpty(location))
//            return;
////        Uri mUri = Uri.parse("geo:39.940409,116.355257?q=西直门");
//        Uri mUri = Uri.parse("geo:0,0?q=" + location);
//        try {
//            Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
//            context.startActivity(mIntent);
//        } catch (Exception e) {
//            ToastHelper.showToast(R.string.error_no_map);
//        }
//    }
//
//    public void openPhoneCall(Context context, String phoneNum) {
//        if (!Helper.isNotEmpty(phoneNum))
//            return;
//        Uri mUri = Uri.parse("tel:" + phoneNum);
//        try {
//            Intent mIntent = new Intent(Intent.ACTION_DIAL, mUri);
//            context.startActivity(mIntent);
//        } catch (Exception e) {
//            ToastHelper.showToast(R.string.error_no_phone);
//        }
//    }

//    public void openUrl(Context context, String url) {
//        Intent intent = new Intent(context, WebViewActivity.class);
//        try {
//            intent.putExtra(WebViewActivity.URL, url);
//            context.startActivity(intent);
//        } catch (Exception e) {
//            ToastHelper.showToast(context, R.string.error_unknown_url);
//        }
//    }

    public String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packInfo.versionName;
    }

    public int getVersionCode(Context context) throws PackageManager.NameNotFoundException {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packInfo.versionCode;
    }


    public void openAppStore(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName); //传递包名，让市场接收
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 交给系统来调用对应类型的打开工具
     */
//    public void openFileBySystem(Context context, String filePath) {
//        try {
//            Intent myIntent = new Intent(Intent.ACTION_VIEW);
//            File file = new File(filePath);
//            String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
//            String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//            myIntent.setDataAndType(Uri.fromFile(file), mimetype);
//            context.startActivity(myIntent);
//        } catch (Exception e) {
//            e.printStackTrace();
//            ToastHelper.showToast(R.string.error_file_type);
//        }
//    }

    public String getApplicationMetaValue(Context context, String name) {
        String value = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void saveDeviceInfo(Context context) {
        AVObject deviceObj = new AVObject("Devices");
        deviceObj.put("deviceOSVersion", Build.VERSION.RELEASE);
        deviceObj.put("device", Build.MODEL);
        deviceObj.put("brand", Build.BRAND);
        deviceObj.put("deviceOS", "Android");
        deviceObj.put("user", AVUser.getCurrentUser());
        deviceObj.put("channel", getApplicationMetaValue(context, "leancloud"));
        try {
            deviceObj.put("appVersion", CommonUtils.getInstance().getVersionName(context));
        } catch (PackageManager.NameNotFoundException e) {
            deviceObj.put("appVersion", "unknown");
            e.printStackTrace();
        }
        deviceObj.saveInBackground();

    }

}
