package event;

/**
 * poi界面
 *
 * Created by Administrator on 2016/3/24.
 */
public class PoiEvent {

    public static final int SHOW_HAS_INFOEMATION = 9;                       // 有未读消息

    private int poiMsgType;


    public PoiEvent(int poiMsgType) {
        this.poiMsgType = poiMsgType;
    }


    public int getMsg() {
        return poiMsgType;
    }


}
