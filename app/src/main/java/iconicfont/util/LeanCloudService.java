package iconicfont.util;

import android.content.Context;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.FunctionCallback;

import java.util.HashMap;
import java.util.Map;


import app.CityGuideApplication;

/**
 * Created by zhengheng on 17/9/23.
 */
public  class LeanCloudService {

    private static Context context = ((CityGuideApplication) CityGuideApplication.getInstance()).getApplicationContext();

    public static <T>  void callFunctionInBackground(String name, Map<String, Object> param, FunctionCallback<T> callback)
    {
        if (param == null) {
            param = new HashMap<>();
        }

        CityGuideApplication applicationContext = (CityGuideApplication) context.getApplicationContext();
        Map<String, Object> appInfo = new HashMap<>();
        appInfo.put("ipAddress", AppInfoUtil.getIpaddress());
        appInfo.put("locationId", "00");
        appInfo.put("packageName", AppInfoUtil.getPackagename());
        appInfo.put("version", AppInfoUtil.getVersionName());

        param.put("appInfo", appInfo);
        AVCloud.callFunctionInBackground(name, param, callback);
    }
}
