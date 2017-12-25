package app;


import android.util.Log;
import android.util.Pair;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.tencent.bugly.crashreport.CrashReport;

import bean.MenuBean;
import common.ApiTokenManager;
import common.LeanCloudConfig;
import so.bubu.lib.base.BaseApplication;
import so.bubu.lib.helper.DeviceHelper;

import com.zhekouxingqiu.main.activity.MainActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import utils.MyJsonUtil;
import utils.SharedPreferencesHelp;
import utils.dbUtils.DBHelper;
import utils.dbUtils.DbManager;
import wiget.dragView.LabelSelectionItem;

import static app.AppConfig.INSTALLATIION_KEY;
import static app.AppConfig.PACKAGE_KEY;


/**
 * 应用程序入口
 */
public class CityGuideApplication extends BaseApplication {
    public static final boolean LAYOUT_CHANGE = true;
    @Override
    public void onCreate() {
        super.onCreate();
        initLeanCloud();
        Log.e("zhengheng", "onCreate " + getPackageName());
        DbManager.getInstance().initialize();

        CrashReport.initCrashReport(getApplicationContext(), "900033198", false);

        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {
                LogUtil.log.e("asyncInit", s);
            }
        });

        addAsynTask(0);
        initMenu();
    }



    @Override
    protected void startBackground(int type) {
//		PlaceListFragment.initPlaceMapData();
//		PlaceImgListFragment.initGuideMapData();
    }

    /**
     * 初始化leancloud
     */
    private void initLeanCloud() {

        if (LeanCloudConfig.IS_DEBUG) {
            Pair<String, String> devApiToken = ApiTokenManager.getInstance().getAvoCloudDebugApiToken();
            AVOSCloud.initialize(this.getApplicationContext(), devApiToken.first, devApiToken.second);
            AVOSCloud.setLastModifyEnabled(true);
            AVOSCloud.setDebugLogEnabled(true);
        } else {
            Pair<String, String> devApiToken = ApiTokenManager.getInstance().getAvoCloudReleaseApiToken();
            AVOSCloud.initialize(this.getApplicationContext(), devApiToken.first, devApiToken.second);

        }
        AVAnalytics.enableCrashReport(this, true);
//
//		AVOSCloud.setLastModifyEnabled(true);
//		AVOSCloud.setDebugLogEnabled(true);


        // 保存 installation 到服务器
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                AVInstallation.getCurrentInstallation().saveInBackground();
                String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                SharedPreferencesHelp.setInstallationId(INSTALLATIION_KEY, installationId);
                SharedPreferencesHelp.setInstallationId(PACKAGE_KEY, DeviceHelper.getAppPackage());
//				LogHelper.e(installationId);

            }
        });
        // 设置默认打开的 Activity
        PushService.setDefaultPushCallback(this, MainActivity.class);
        PushService.subscribe(this, "CommonPushMsg", MainActivity.class);


    }
    public List<MenuBean.ObjectsBeanBean> items = new ArrayList<>();
    public List<MenuBean.ObjectsBeanBean> otherItems = new ArrayList<>();
    private void initMenu2(){

        LinkedList<MenuBean.ObjectsBeanBean> menuLabel = DBHelper.getInstance(CityGuideApplication.getInstance().getApplicationContext()).getMenuLabel(true);
        if (menuLabel.size() > 0) {
            for (MenuBean.ObjectsBeanBean selectedLabel : menuLabel) {
                items.add(selectedLabel);
            }
            LogUtil.log.e("zhengheng", "read successful  select" + menuLabel.toString());
        } else {
            getAssetsJson();
            if (gridlist.size() > 0) {
                for (MenuBean.ObjectsBeanBean selectedLabel : gridlist) {
                    items.add(selectedLabel);
                }
            }
            LogUtil.log.e("zhengheng", "graidlist select" + gridlist);

        }
        LinkedList<MenuBean.ObjectsBeanBean> menuLabelunselect = DBHelper.getInstance(CityGuideApplication.getInstance().getApplicationContext()).getMenuLabel(false);

        if(menuLabelunselect.size() >0){
            for (MenuBean.ObjectsBeanBean unselectedLabel : menuLabelunselect) {
                otherItems.add(unselectedLabel);
            }
            LogUtil.log.e("zhengheng", "read successful  unselect" + menuLabelunselect.toString());
        }
    }

    private LinkedList<MenuBean.ObjectsBeanBean> gridlist = new LinkedList<>();
    private static final String JSON_FILE = "category.json";
    public ArrayList<LabelSelectionItem> labelSelectionItems = new ArrayList<>();
    private ArrayList<LabelSelectionItem> initMenu(){

        //编辑的标题布局
        labelSelectionItems.add(new LabelSelectionItem(LabelSelectionItem.TYPE_LABEL_SELECTED_TITLE, "切换栏目"));
        //总是不变化的label
//        labelSelectionItems.add(new LabelSelectionItem(LabelSelectionItem.TYPE_LABEL_ALWAY_SELECTED, gridlist.get(0)));
        //已经被选中的label
        LinkedList<MenuBean.ObjectsBeanBean> menuLabel = DBHelper.getInstance(CityGuideApplication.getInstance().getApplicationContext()).getMenuLabel(true);
        if (menuLabel.size() > 0) {
            for (MenuBean.ObjectsBeanBean selectedLabel : menuLabel) {
                labelSelectionItems.add(new LabelSelectionItem(LabelSelectionItem.TYPE_LABEL_SELECTED, selectedLabel));

            }
            LogUtil.log.e("zhengheng", "read successful  select" + menuLabel.toString());
        } else {
            getAssetsJson();
            if (gridlist.size() > 0) {
                for (MenuBean.ObjectsBeanBean selectedLabel : gridlist) {
                    labelSelectionItems.add(new LabelSelectionItem(LabelSelectionItem.TYPE_LABEL_SELECTED, selectedLabel));
                }
            }
            LogUtil.log.e("zhengheng", "graidlist select" + gridlist);

        }
        //未被选中的标题
        labelSelectionItems.add(new LabelSelectionItem(LabelSelectionItem.TYPE_LABEL_UNSELECTED_TITLE, "点击添加更多标签"));

        //未被选中的label
        LinkedList<MenuBean.ObjectsBeanBean> menuLabelunselect = DBHelper.getInstance(CityGuideApplication.getInstance().getApplicationContext()).getMenuLabel(false);

        if(menuLabelunselect.size() >0){
            for (MenuBean.ObjectsBeanBean unselectedLabel : menuLabelunselect) {
                labelSelectionItems.add(new LabelSelectionItem(LabelSelectionItem.TYPE_LABEL_UNSELECTED, unselectedLabel));
            }
            LogUtil.log.e("zhengheng", "read successful  unselect" + menuLabelunselect.toString());
        }
        return labelSelectionItems;
    }

    public void getAssetsJson() {
        gridlist.clear();
        String mineJson = MyJsonUtil.getJson(CityGuideApplication.getInstance().getApplicationContext(), JSON_FILE);
        List<MenuBean> menuBeans = JSON.parseArray(mineJson, MenuBean.class);
        LogUtil.log.e("zhengheng category", "" + menuBeans.toString());
        gridlist.addAll(menuBeans.get(0).getObjectsBean());
    }
}
