package push;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FunctionCallback;

import java.util.HashMap;
import java.util.Map;


import app.AppConfig;
import iconicfont.util.LeanCloudService;
import so.bubu.lib.helper.DeviceHelper;
import so.bubu.lib.helper.LogHelper;

/**
 * 绑定installationId
 * Created by wneng on 16/7/8.
 */

public class PushHelper {

    private static final String SET_INSTALLATION_ID = "setInstallation";
    private static final String REMOVE_INSTALLATION_ID = "removeInstallation";

    public static void setInstallationId(String installationId){

        Map<String, Object> params = new HashMap<>();
        params.put("installationId", installationId);
        params.put(AppConfig.PACKAGE_KEY, DeviceHelper.getAppPackage());
        params.put(AppConfig.PACKAGE_KEY, DeviceHelper.getAppPackage());

        LeanCloudService.callFunctionInBackground(SET_INSTALLATION_ID, params, new FunctionCallback<Boolean>() {
            @Override
            public void done(Boolean aBoolean, AVException e) {

                if (e == null) {
                    if (aBoolean) {
                        LogHelper.e("setInstallationId = " + aBoolean);
                    }
                } else {
                    LogHelper.e(e.getLocalizedMessage());
                }
            }
        });

    }

    public static void removeInstallationId(String installationId){

        Map<String, Object> params = new HashMap<>();
        params.put("installationId", installationId);
        params.put(AppConfig.PACKAGE_KEY, DeviceHelper.getAppPackage());

        LeanCloudService.callFunctionInBackground(REMOVE_INSTALLATION_ID, params, new FunctionCallback<Boolean>() {
            @Override
            public void done(Boolean aBoolean, AVException e) {

                if (e == null) {
                    if (aBoolean) {
                        LogHelper.e("removeInstallation = " + aBoolean);
                    }
                } else {
                    LogHelper.e(e.getLocalizedMessage());
                }
            }
        });

    }


}
