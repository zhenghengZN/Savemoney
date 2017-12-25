package bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhengheng on 17/12/15.
 */
public class MenuBean implements Serializable {

    private List<ObjectsBeanBean> ObjectsBean;

    public List<ObjectsBeanBean> getObjectsBean() {
        return ObjectsBean;
    }

    public void setObjectsBean(List<ObjectsBeanBean> ObjectsBean) {
        this.ObjectsBean = ObjectsBean;
    }

    public static class ObjectsBeanBean implements Serializable{
        public ObjectsBeanBean(String picUrl, String name, boolean islike) {
            this.picUrl = picUrl;
            this.name = name;
            this.islike = islike;
        }

        public ObjectsBeanBean(){}
        /**
         * picUrl : http://gd3.alicdn.com/imgextra/i3/1633889075/TB2FsvPennI8KJjSszbXXb4KFXa_!!1633889075.png
         * name : 今日推荐
         */

        private String picUrl;
        private String name;
        private boolean islike = true;

        public boolean islike() {
            return islike;
        }

        public void setIslike(boolean islike) {
            this.islike = islike;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override
    public String toString() {
        return "MenuBean{" +
                "ObjectsBean=" + ObjectsBean +
                '}';
    }
}
