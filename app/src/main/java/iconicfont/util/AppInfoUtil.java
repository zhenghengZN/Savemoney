package iconicfont.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import app.CityGuideApplication;


/**
 * Created by zhengheng on 17/9/27.
 */
public class AppInfoUtil {

    private static Context context = ((CityGuideApplication)CityGuideApplication.getInstance()).getApplicationContext();

    public static String getVersionName() {

        String version="";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return version;
    }

    public static String getPackagename() {

//		String packageName = "";
//		PackageManager manager = this.getPackageManager();
//		try {
//			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
//			packageName = info.packageName;
//
//		} catch (PackageManager.NameNotFoundException e) {
//			e.printStackTrace();
//		}
//		return packageName;
        return context.getPackageName();
    }

    public static String getIpaddress() {

        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    private static String intToIp(int i) {

        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }


    public static String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }
}
