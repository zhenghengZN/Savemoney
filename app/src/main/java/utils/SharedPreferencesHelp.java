package utils;

import java.util.Map;


import app.AppConfig;
import so.bubu.lib.helper.PreferencesHelper;

/**
 * Created by wangwn on 2016/6/3.
 */
public class SharedPreferencesHelp {

    public static void setCheckVersion(String name, String version) {
        PreferencesHelper.getInstance(AppConfig.SP_SHOW_NEW_GUIDE).putString(name, version);
    }

    public static String getCheckVersion(String name) {
        return PreferencesHelper.getInstance(AppConfig.SP_SHOW_NEW_GUIDE).getString(name, name);
    }

    public static void setInstallationId(String name, String installationId) {
        PreferencesHelper.getInstance(AppConfig.SP_INSTALLATION_ID).putString(name, installationId);
    }

    public static String getInstallationId(String name) {
        return PreferencesHelper.getInstance(AppConfig.SP_INSTALLATION_ID).getString(name, name);
    }

    public static void setInvitationCode(String key, boolean isInvite) {

        PreferencesHelper.getInstance(AppConfig.SP_INVITE_CODE).putBoolean(key, isInvite);
    }

    public static boolean getInvitationCode(String key) {
        return PreferencesHelper.getInstance(AppConfig.SP_INVITE_CODE).getBoolean(key, false);
    }

    public static Map<String, Integer> getBacteriaMap() {
        return (Map<String, Integer>) PreferencesHelper.getInstance(AppConfig.SP_INVITE_CODE).getObject(AppConfig.BACTERIA_ID);
    }

    public static void setBacteriaMap(Map<String, Integer> bacteriaMap) {
        PreferencesHelper.getInstance(AppConfig.SP_INVITE_CODE).putObject(AppConfig.BACTERIA_ID, bacteriaMap);
    }

    public static boolean getBacteriaContent() {
        return PreferencesHelper.getInstance(AppConfig.SP_INVITE_CODE).getBoolean(AppConfig.BACTERIA_CONTENT, false);
    }

    public static void setBacteriaContent() {
        PreferencesHelper.getInstance(AppConfig.SP_INVITE_CODE).putBoolean(AppConfig.BACTERIA_CONTENT, true);
    }

    public static void setDataVersion(String key, int dataVersion) {
        PreferencesHelper.getInstance(AppConfig.SP_LOCATION).putInt(key, dataVersion);
    }

    public static int getDataVersion(String key) {

        return PreferencesHelper.getInstance(AppConfig.SP_LOCATION).getInt(key, 1);
    }

    public static void setCityName(String key, String cityName) {

        PreferencesHelper.getInstance(AppConfig.SP_LOCATION).putString(key, cityName);

    }

    public static String getCityName(String key) {
        return PreferencesHelper.getInstance(AppConfig.SP_LOCATION).getString(key);
    }

//    public static void setLocationId(String locationKey, String locationId) {
//        PreferencesHelper.getInstance(AppConfig.SP_LOCATION).putString(locationKey, locationId);
//        CityGuideApplication.SYDNEY = locationId;
//    }
//
//    public static String getLocationId(String locationKey) {
//        return PreferencesHelper.getInstance(AppConfig.SP_LOCATION).getString(locationKey, CommonData.SEARCH_ID);
//    }

//    public static void setLocationLongitude(double longtitude){
//        PreferencesHelper.getInstance(AppConfig.SP_LOCATION).putString(AppConfig.LOCATION_LONGITUDE, longtitude + "");
//        CityGuideApplication.LONGITUDE = longtitude;
//    }
//
//    public static double getLocationLongitude() {
//        return Double.valueOf(PreferencesHelper.getInstance(AppConfig.SP_LOCATION).getString(AppConfig.LOCATION_LONGITUDE, CommonData.LONGITUDE));
//    }

//    public static void setLocationLatitude(double latitude) {
//        PreferencesHelper.getInstance(AppConfig.SP_LOCATION).putString(AppConfig.LOCATION_LATITUDE, latitude + "");
//        CityGuideApplication.LATITUDE = latitude;
//    }
//
//    public static double getLocationLatitude() {
//        return Double.valueOf(PreferencesHelper.getInstance(AppConfig.SP_LOCATION).getString(AppConfig.LOCATION_LATITUDE, CommonData.LATITUDE));
//    }
    public static void setCurrentDBName(String key, String dbName){
        PreferencesHelper.getInstance(AppConfig.SP_LOCATION).putString(key, dbName);
    }

    public static String getCurrentDBName(String key){
        return PreferencesHelper.getInstance(AppConfig.SP_LOCATION).getString(key);
    }

    public static int getSecondMessagePageItem(int defaultItem){
        return PreferencesHelper.getInstance(AppConfig.SP_PAGE_CACHE).getInt(AppConfig.SECOND_MESSAGE_PAGE, defaultItem);
    }

//    public static void setCityCenterLatitude(String key, float latitude){
//        PreferencesHelper.getInstance(AppConfig.SP_LOCATION).putFloat(key, latitude);
//        CityGuideApplication.LATITUDE = latitude;
//    }
//
//    public static float getCityCenterLatitude(String key){
//        return PreferencesHelper.getInstance(AppConfig.SP_LOCATION).getFloat(key, 0f);
//    }
//
//    public static void setCityCenterLongitude(String key, float longitude){
//        PreferencesHelper.getInstance(AppConfig.SP_LOCATION).putFloat(key, longitude);
//        CityGuideApplication.LONGITUDE = longitude;
//    }
//
//    public static float getCityCenterLongitude(String key){
//        return PreferencesHelper.getInstance(AppConfig.SP_LOCATION).getFloat(key, "");
//    }


    public static int getFirstPageItem(){
        return PreferencesHelper.getInstance(AppConfig.SP_PAGE_CACHE).getInt(AppConfig.FIRST_PAGE, 0);
    }

    public static void setFirstPageItem(int firstPageItem){
        PreferencesHelper.getInstance(AppConfig.SP_PAGE_CACHE).putInt(AppConfig.FIRST_PAGE, firstPageItem);
    }
}
