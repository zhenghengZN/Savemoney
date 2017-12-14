package bean;

import java.util.List;

/**
 * Created by zhengheng on 17/12/7.
 */
public class MineBean {

    /**
     * type : LoginCell
     * objects : [{"title":"快速登录","iconFontCode":"0xe77c;","picUrl":"http://ac-egDFgpKJ.clouddn.com/c7a93b81213187e02d9a.jpg","url":"/redirectAction?action=SignUpOrLoginWithMobilePhone"}]
     */

    private String type;
    private List<ObjectsBean> objects;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ObjectsBean> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectsBean> objects) {
        this.objects = objects;
    }

    public static class ObjectsBean {
        /**
         * title : 快速登录
         * iconFontCode : 0xe77c;
         * picUrl : http://ac-egDFgpKJ.clouddn.com/c7a93b81213187e02d9a.jpg
         * url : /redirectAction?action=SignUpOrLoginWithMobilePhone
         */

        private String title;
        private String iconFontCode;
        private String picUrl;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIconFontCode() {
            return iconFontCode;
        }

        public void setIconFontCode(String iconFontCode) {
            this.iconFontCode = iconFontCode;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
