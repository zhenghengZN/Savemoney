package utils;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FunctionCallback;

import java.util.HashMap;
import java.util.Map;

import app.CityGuideApplication;
import app.CommonData;
import iconicfont.util.LeanCloudService;


/**
 * @author linhuan on 16/7/7 下午6:03
 */
public class InformationHelper extends BaseNetHelper {

    private static final String GETTAOBAOITEMS = "getTaobaoCoupons";
    private static final String GETTAOBAOITEMCATEGORIESBETA = "getTaobaoItemCategoriesBeta";

    private InformationHelper() {
    }

    private static InformationHelper INSTANCE;

    public static InformationHelper getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new InformationHelper();
        }

        return INSTANCE;
    }

    /**
     * 获取淘宝优惠卷
     */

    public void getTaobaoCoupons(int skip, int limit, String category, String keywords, String subcategory, final CallFunctionBackListener listener) {

        Map<String, Object> stringObjectMap = addRequestParams(skip, limit);
        stringObjectMap.put(CommonData.CATEGORY, category);
        stringObjectMap.put(CommonData.KEYWORD, keywords);
        stringObjectMap.put(CommonData.SUBCATEGORY, subcategory);
        LeanCloudService.callFunctionInBackground(GETTAOBAOITEMS, stringObjectMap, new FunctionCallback<Object>() {

            @Override
            public void done(Object o, AVException e) {
                handleAvCallResponseWithJsonToList(o, e, listener);

            }
        });;
    }

    /**
     * 获取淘宝分类
     */
    public void getTaobaoItemCategoriesBeta(final CallFunctionBackListener listener) {

        LeanCloudService.callFunctionInBackground(GETTAOBAOITEMCATEGORIESBETA, null, new FunctionCallback<Object>() {

            @Override
            public void done(Object o, AVException e) {
                handleAvCallResponseWithJsonToList(o, e, listener);

            }
        });;
    }

    private Map<String, Object> addRequestParams(int skip, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put(SKIP, skip);
        params.put(LIMIT, limit);

        return params;
    }

}
