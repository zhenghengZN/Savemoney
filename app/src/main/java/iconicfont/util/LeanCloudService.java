package iconicfont.util;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.FunctionCallback;

import java.util.HashMap;
import java.util.Map;


import app.CityGuideApplication;

/**
 * Created by zhengheng on 17/9/23.
 */
public  class LeanCloudService {

    public static <T>  void callFunctionInBackground(String name, Map<String, Object> param, FunctionCallback<T> callback)
    {
        if (param == null) {
            param = new HashMap<>();
        }

        Map<String, Object> appInfo = new HashMap<>();
        appInfo.put("ipAddress", AppInfoUtil.getPhoneIp());
        appInfo.put("locationId", "00");
        appInfo.put("packageName", AppInfoUtil.getPackagename());
        appInfo.put("version", AppInfoUtil.getVersionName());

        param.put("appInfo", appInfo);
        Log.e("LeanCloudService", "callFunctionInBackground" + AppInfoUtil.getPhoneIp()+AppInfoUtil.getPackagename());
        AVCloud.callFunctionInBackground(name, param, callback);
    }
}
