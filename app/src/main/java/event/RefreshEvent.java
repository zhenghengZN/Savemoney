package event;

/**
 * Created by wangwn on 2016/4/27.
 */
public class RefreshEvent {

    public static final int REFRESH_EVENT = 11;

    public static final int CITY_GUIDE_EVENT = 12;

    private int event_type;
    private String dbName;


    public RefreshEvent(int event_type) {
        this.event_type = event_type;
    }

    public RefreshEvent(int event_type, String dbName) {
        this.event_type = event_type;
        this.dbName = dbName;
    }

    public int getEvent_type() {
        return event_type;
    }

    public void setEvent_type(int event_type) {
        this.event_type = event_type;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
