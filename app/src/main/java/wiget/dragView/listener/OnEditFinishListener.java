package wiget.dragView.listener;

import java.util.ArrayList;

import bean.MenuBean;

public interface OnEditFinishListener {
    void  onEditFinish(ArrayList<MenuBean.ObjectsBeanBean> selectedLabels, ArrayList<MenuBean.ObjectsBeanBean> unselectedLabel, ArrayList<MenuBean.ObjectsBeanBean> alwaySelectedLabels);
}
