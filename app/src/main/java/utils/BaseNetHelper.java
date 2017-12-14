package utils;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;

import java.util.List;



import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.NetWorkHelper;
import so.bubu.lib.helper.ResourceHelper;
import so.bubu.lib.helper.ToastHelper;
import com.zhekouxingqiu.main.R;

/**
 * 网络请求公用方法
 *
 * @author linhuan on 16/5/24 上午10:00
 */
public abstract class BaseNetHelper {

    public static final int POI_COUNT = 20;                                                 // POI一页读取二十
    public static final int LIKE_USER_COUNT = 50;                                           // POI一页读取五十个

    public static final String ON_SECTION = "onSection";
    public static final String APP = "app";
    public static final String ON_PAGE = "onPage";
    public static final String TYPE = "type";
    public static final String SKIP = "skip";
    public static final String LIMIT = "limit";
    public static final String POST_ID = "postId";
    public static final String KEYWORDS = "keywords";
    public static final String ID = "id";
    public static final String REFERENCE_ID = "referenceId";
    public static final String CONTENT = "content";
    public static final String DESTINACTION_ID = "destinationId";
    public static final String REQUIRE_TOTAL_COUNT = "requireTotalCount";
    public static final String POST = "Post";
    public static final String COMMENT = "Comment";
    public static final String COMMENT_CLOUD_ID = "commentCloudId";
    public static final String TIMESTAMP = "timestamp";
    public static final String REPLY_TO_ID = "replyToId";
    public static final String CHANNEL_ID = "channelId";
    public static final String TARGET_USER_ID = "targetUserId";
    public static final String MINIMUN_MESSAGE_ID = "minimumMessageId";
    public static final String LOCATION_ID = "locationId";
    public static final String PRICE_RANGE_FROM = "priceRangeFrom";
    public static final String PRICE_RANGE_TO = "priceRangeTo";
    public static final String SUBCATEGORY_ARRAY= "subcategoryArray";

    public boolean isNetworkAvailable(){

        if (NetWorkHelper.isNetworkAvailable()) {
            return true;
        }else{
            ToastHelper.showToast(ResourceHelper.getString(R.string.network_error));
        }

        return false;
    }

    /**
     * 处理请求
     *
     * @param result
     * @param e
     * @param listener
     */
    public void handleAvCallResponse(boolean result, AVException e, CallFunctionBackListener listener) {

        if (Helper.isNotEmpty(result)) {
            if (result) {
                listener.callSuccess(result, "");
            } else {
                listener.callFailure(CallFailureType.OBJECT_IS_EMPTY, e);
            }
        } else {
            listener.callFailure(CallFailureType.OBJECT_JSON_NULL, e);
        }
    }

    /**
     * 处理请求
     *
     * @param result
     * @param e
     * @param listener
     */
    public void handleAvCallResponseToSingle(boolean result, AVException e, CallFunctionBackListener listener) {

        if (Helper.isNotEmpty(result)) {
            listener.callSuccess(result, "");
        } else {
            listener.callFailure(CallFailureType.OBJECT_JSON_NULL, e);
        }
    }

    /**
     * 处理请求
     *
     * @param o
     * @param e
     * @param listener
     */
    public void handleAvCallResponseWithJson(Object o, AVException e, CallFunctionBackListener listener) {

        if (Helper.isEmpty(o)) {
            listener.callFailure(CallFailureType.OBJECT_IS_EMPTY, e);
            return;
        }

        if (e != null) {

            listener.callFailure(CallFailureType.AVEXCEPTION, e);
        }

        String jsonString = JSON.toJSONString(o);
        if (Helper.isNotEmpty(jsonString)) {


            listener.callSuccess(true, jsonString);
        } else {
            listener.callFailure(CallFailureType.OBJECT_JSON_NULL, e);
        }
    }

    /**
     * 处理请求
     *
     * @param o
     * @param e
     * @param listener
     */
    public void handleAvCallResponseWithJsonToList(Object o, AVException e, CallFunctionBackListener listener) {

        if (Helper.isNotNull(e)) {
            listener.callFailure(CallFailureType.AVEXCEPTION, e);
            return;
        }

        if (Helper.isEmpty(o)) {
            listener.callSuccess(false, null);
            return;
        }

        String jsonString = JSON.toJSONString(o);
        if (Helper.isNotEmpty(jsonString)) {
            listener.callSuccess(true, jsonString);
        } else {
            listener.callSuccess(false, null);
        }
    }

    /**
     * 批量参数
     * @param objectIdList
     * @return
     */
    public String getLikeStatusArrayParams(List<String> objectIdList){


        if (Helper.isNotEmpty(objectIdList)) {
            StringBuilder stringBuilder = new StringBuilder();

            for (String objectId : objectIdList){

                if (objectId.equals(objectIdList.get(objectIdList.size() - 1))) {
                    stringBuilder.append(objectId);
                }else{
                    stringBuilder.append(objectId);
                    stringBuilder.append(",");
                }

            }
            return stringBuilder.toString();

        }else{
            return "";
        }

    }

}
