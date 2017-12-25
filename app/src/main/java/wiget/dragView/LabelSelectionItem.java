package wiget.dragView;

import java.io.Serializable;

import bean.MenuBean;


public class LabelSelectionItem implements Serializable {
    public static final int TYPE_LABEL_UNSELECTED = 1;
    public static final int TYPE_LABEL_SELECTED = 2;
    public static final int TYPE_LABEL_ALWAY_SELECTED = 5;
    public static final int TYPE_LABEL_SELECTED_TITLE = 3;
    public static final int TYPE_LABEL_UNSELECTED_TITLE = 4;


    public LabelSelectionItem(int itemType, String title) {
        this.itemType = itemType;
        this.title = title;
    }

    public LabelSelectionItem(int itemType, MenuBean.ObjectsBeanBean label) {
        this.itemType = itemType;
        this.label = label;
    }

    private int itemType;
    private String title;
    private MenuBean.ObjectsBeanBean label;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MenuBean.ObjectsBeanBean getLabel() {
        return label;
    }

    public void setLabel(MenuBean.ObjectsBeanBean label) {
        this.label = label;
    }

    public int getItemType() {
        return itemType;
    }
}
