package bean;

/**
 * Created by zhengheng on 17/11/16.
 */
public class TitleBean {
    private String title;
    private boolean isSelect;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public TitleBean(String title, boolean isSelect) {
        this.title = title;
        this.isSelect = isSelect;
    }
}
