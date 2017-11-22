package bean;

import java.util.List;

/**
 * Created by zhengheng on 17/11/17.
 */
public class TaobaoCatetory {


    private List<ObjectsBean> objects;

    public List<ObjectsBean> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectsBean> objects) {
        this.objects = objects;
    }

    public static class ObjectsBean {
        /**
         * category : 今日推荐
         * aid : 1
         */

        private String category;
        private int aid;
        private String collectionId;

        public String getCollectionId() {
            return collectionId;
        }

        public void setCollectionId(String collectionId) {
            this.collectionId = collectionId;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }
    }
}
