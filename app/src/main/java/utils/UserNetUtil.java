package utils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FunctionCallback;

import java.util.HashMap;
import java.util.Map;

import iconicfont.util.LeanCloudService;


/**
 * Created by wneng on 16/7/28.
 */

public class UserNetUtil extends BaseNetHelper {

    private static final String GET_CURRENT_USER_MSG = "getCurrentUser";
    private static final String GET_USER_VISITOR_BY_ID = "getUserForVisitorById";


    private UserNetUtil() {
    }

    private static UserNetUtil INSTANCE;

    public static UserNetUtil getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new UserNetUtil();
        }

        return INSTANCE;
    }

    /**
     * 获取当前用户信息
     *
     * @param listener
     */
    public void getCurrentUserByNet(final CallFunctionBackListener listener) {

        if (!isNetworkAvailable()) {
            return;
        }
        LeanCloudService.callFunctionInBackground(GET_CURRENT_USER_MSG, null, new FunctionCallback<Object>() {
            @Override
            public void done(Object o, AVException e) {
                handleAvCallResponseWithJson(o, e, listener);
            }
        });

    }


    /**
     * 他人主页信息
     * @param userId
     * @param listener
     */
    public void getUserForVisitorById(String userId, final CallFunctionBackListener listener){

        if (!isNetworkAvailable()) {
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("targetUserId", userId);

        LeanCloudService.callFunctionInBackground(GET_USER_VISITOR_BY_ID, params, new FunctionCallback<Object>() {
            @Override
            public void done(Object o, AVException e) {
                handleAvCallResponseWithJson(o, e, listener);
            }
        });
    }



}
