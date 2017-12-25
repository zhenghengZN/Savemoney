package bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhengheng on 17/11/1.
 */
public class TaobaoCoupons {

    /**
     * hasMore : true
     * objects : [{"category":"女装/女士精品","commision":2.7,"couponAmount":3,"couponEffectiveEndTime":"2017-12-31T15:59:59.000Z","couponRemain":89232,"couponShareUrl":"https://uland.taobao.com/coupon/edetail?e=yD4sdwtky8QN2BoQUE6FNzGPHTcqQ4ufIGZPSe9jdjR088L0dUrFHspPccAg7n0MpdNaR4WuSGicDadKY%2FDznrRpywujSvOp2nUIklpPPqYK4tl%2FBGQdTIlca75LhnUVze8N6wK7LDDioUGkcP0mO0vhb%2B5EG2EF1&pid=mm_119950409_20916506_70766512&af=1&tj1=1&tj2=1","couponTotal":99999,"discountPrice":49,"finalPrice":46,"id":"59f7ec8967f356006408179e","isFeatured":true,"picUrl":"http://img.alicdn.com/bao/uploaded/i4/2934924135/TB15quSljuhSKJjSspdXXc11XXa_!!0-item_pic.jpg_360x360","platform":"天猫","title":"加厚毛呢九分阔腿裤"},{"category":"服饰配件/皮带/帽子/围巾","commision":1.2,"couponAmount":30,"couponEffectiveEndTime":"2017-11-02T15:59:59.000Z","couponRemain":5000,"couponShareUrl":"https://uland.taobao.com/coupon/edetail?e=AXJVjXFdUiQN%2BoQUE6FNzGPHTcqQ4ufIGZPSe9jdjR088L0dUrFHspPccAg7n0Mpy7btAWKTsewjXUYC%2FVfP0hpywujSvOp2nUIklpPPqYJLckVDLRNFUdYi6G2BR2FerK0xctS2FQwnxBqfkr6k6wyAppCrdon5nhS&pid=mm_119950409_20916506_70766512&af=1&tj1=1&tj2=1","couponTotal":5000,"discountPrice":59.9,"finalPrice":29.9,"id":"59f7ec8917d0090062390f87","isFeatured":true,"picUrl":"http://img.alicdn.com/bao/uploaded/i2/1014983020/TB1EycCgBUSMeJjy1zkXXaWmpXa_!!0-item_pic.jpg_360x360","platform":"天猫","title":"冬季男士加厚针织围巾 礼盒装"}]
     */

    private boolean hasMore;
    private List<ObjectsBean> objects;
    private List<WidgetsBean> widgets;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<ObjectsBean> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectsBean> objects) {
        this.objects = objects;
    }

    public List<WidgetsBean> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<WidgetsBean> widgets) {
        this.widgets = widgets;
    }

    public static class ObjectsBean implements Serializable {
        public ObjectsBean(String category, int couponAmount, String biz30Day, String title, String platform, String couponShareUrl, double finalPrice, double discountPrice , String picUrl, String id) {
            this.category = category;
            this.couponAmount = couponAmount;
            this.biz30Day = biz30Day;
            this.title = title;
            this.platform = platform;
            this.couponShareUrl = couponShareUrl;
            this.finalPrice = finalPrice;
            this.discountPrice = discountPrice;
            this.picUrl = picUrl;
            this.id = id;
        }

        public ObjectsBean(){

        }

        /**
         * category : 女装/女士精品
         * commision : 2.7
         * couponAmount : 3
         * couponEffectiveEndTime : 2017-12-31T15:59:59.000Z
         * couponRemain : 89232
         * couponShareUrl : https://uland.taobao.com/coupon/edetail?e=yD4sdwtky8QN2BoQUE6FNzGPHTcqQ4ufIGZPSe9jdjR088L0dUrFHspPccAg7n0MpdNaR4WuSGicDadKY%2FDznrRpywujSvOp2nUIklpPPqYK4tl%2FBGQdTIlca75LhnUVze8N6wK7LDDioUGkcP0mO0vhb%2B5EG2EF1&pid=mm_119950409_20916506_70766512&af=1&tj1=1&tj2=1
         * couponTotal : 99999
         * discountPrice : 49
         * finalPrice : 46
         * id : 59f7ec8967f356006408179e
         * isFeatured : true
         * picUrl : http://img.alicdn.com/bao/uploaded/i4/2934924135/TB15quSljuhSKJjSspdXXc11XXa_!!0-item_pic.jpg_360x360
         * platform : 天猫
         * title : 加厚毛呢九分阔腿裤
         */

        private String category;
        private double commision;
        private int couponAmount;
        private String couponEffectiveEndTime;
        private int couponRemain;
        private String couponShareUrl;
        private int couponTotal;
        private double discountPrice;
        private double finalPrice;
        private String id;
        private boolean isFeatured;
        private String picUrl;
        private String platform;
        private String title;
        private String biz30Day;

        public String getBiz30Day() {
            return biz30Day;
        }

        public void setBiz30Day(String biz30Day) {
            this.biz30Day = biz30Day;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public double getCommision() {
            return commision;
        }

        public void setCommision(double commision) {
            this.commision = commision;
        }

        public int getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(int couponAmount) {
            this.couponAmount = couponAmount;
        }

        public String getCouponEffectiveEndTime() {
            return couponEffectiveEndTime;
        }

        public void setCouponEffectiveEndTime(String couponEffectiveEndTime) {
            this.couponEffectiveEndTime = couponEffectiveEndTime;
        }

        public int getCouponRemain() {
            return couponRemain;
        }

        public void setCouponRemain(int couponRemain) {
            this.couponRemain = couponRemain;
        }

        public String getCouponShareUrl() {
            return couponShareUrl;
        }

        public void setCouponShareUrl(String couponShareUrl) {
            this.couponShareUrl = couponShareUrl;
        }

        public int getCouponTotal() {
            return couponTotal;
        }

        public void setCouponTotal(int couponTotal) {
            this.couponTotal = couponTotal;
        }

        public Object getDiscountPrice() {
            if (discountPrice % (int) discountPrice == 0) {
                return (int) discountPrice;
            }
            return discountPrice;
        }

        public void setDiscountPrice(double discountPrice) {
            this.discountPrice = discountPrice;
        }

        public Object getFinalPrice() {
            if (finalPrice % (int) finalPrice == 0) {
                return (int) finalPrice;
            }
            return finalPrice;
        }

        public void setFinalPrice(double finalPrice) {
            this.finalPrice = finalPrice;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsFeatured() {
            return isFeatured;
        }

        public void setIsFeatured(boolean isFeatured) {
            this.isFeatured = isFeatured;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class WidgetsBean {
        /**
         * height : 344
         * objects : [{"picUrl":"https://gw.alicdn.com/tfs/TB1VDjOQFXXXXcHXpXXXXXXXXXX-350-350.jpg_120x120Q75s100.jpg","url":"https://s.click.taobao.com/2HPB2Zw"}]
         * type : Banner
         * width : 750
         * columnPerRow : 4
         * style : IconAndText
         */

        private int height;
        private String type;
        private int width;
        private int columnPerRow;
        private String backgroundImageUrl;

        public String getBackgroundImageUrl() {
            return backgroundImageUrl;
        }

        public void setBackgroundImageUrl(String backgroundImageUrl) {
            this.backgroundImageUrl = backgroundImageUrl;
        }

        private String style;
        //        @com.google.gson.annotations.SerializedName("objects")
        private List<ObjectsBean> objects;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getColumnPerRow() {
            return columnPerRow;
        }

        public void setColumnPerRow(int columnPerRow) {
            this.columnPerRow = columnPerRow;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public List<ObjectsBean> getObjects() {
            return objects;
        }

        public void setObjects(List<ObjectsBean> objects) {
            this.objects = objects;
        }

        public static class ObjectsBean {
            /**
             * picUrl : https://gw.alicdn.com/tfs/TB1VDjOQFXXXXcHXpXXXXXXXXXX-350-350.jpg_120x120Q75s100.jpg
             * url : https://s.click.taobao.com/2HPB2Zw
             */
            private String aid;
            private String picUrl;
            private String url;
            private String name;
            private String keywords;
            private float startXPercentage;
            private float startYPercentage;
            private float widthPercentage;

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public float getStartXPercentage() {
                return startXPercentage;
            }

            public void setStartXPercentage(float startXPercentage) {
                this.startXPercentage = startXPercentage;
            }

            public float getStartYPercentage() {
                return startYPercentage;
            }

            public void setStartYPercentage(float startYPercentage) {
                this.startYPercentage = startYPercentage;
            }

            public float getWidthPercentage() {
                return widthPercentage;
            }

            public void setWidthPercentage(float widthPercentage) {
                this.widthPercentage = widthPercentage;
            }

            public float getHeigthPercentage() {
                return heigthPercentage;
            }

            public void setHeigthPercentage(float heigthPercentage) {
                this.heigthPercentage = heigthPercentage;
            }

            private float heigthPercentage;

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

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }
        }
    }
}

//{
//      "hasMore": false,
//        "objects": [],
//        "widgets": [
//        {
//        "height": 344,
//        "objects": [{
//        "picUrl": "https://gw.alicdn.com/tfs/TB1VDjOQFXXXXcHXpXXXXXXXXXX-350-350.jpg_120x120Q75s100.jpg",
//        "url": "https://s.click.taobao.com/2HPB2Zw"
//        }],
//        "type": "Banner",
//        "width": 750
//        },
//        {
//        "columnPerRow": 4,
//        "objects": [
//        {
//        "keywords": "上衣,卫衣,T恤",
//        "name": "上装",
//        "picUrl": "https://gw.alicdn.com/tfs/TB1VDjOQFXXXXcHXpXXXXXXXXXX-350-350.jpg_120x120Q75s100.jpg",
//        "url": "/redirectAppPage?page=TaobaoItemList&category=女装&subcategory=上装"
//        },
//        {
//        "keywords": "牛仔裤,九分裤,七分裤,休闲裤,哈伦裤,阔腿裤,小脚裤",
//        "name": "裤装",
//        "picUrl": "https://gw.alicdn.com/tfs/TB17gLTQFXXXXbWXXXXXXXXXXXX-350-350.jpg_120x120Q75s100.jpg",
//        "url": "/redirectAppPage?page=TaobaoItemList&category=女装&subcategory=裤装"
//        },
//        {
//        "name": "衬衫",
//        "picUrl": "https://gw.alicdn.com/tfs/TB1e4vQQFXXXXc8XXXXXXXXXXXX-350-350.jpg_120x120Q75s100.jpg",
//        "url": "/redirectAppPage?page=TaobaoItemList&category=女装&subcategory=衬衫"
//        },
//        {
//        "name": "连衣裙",
//        "picUrl": "https://gw.alicdn.com/tps/TB1VzGkPFXXXXc8XVXXXXXXXXXX-350-350.jpg_120x120Q75s100.jpg",
//        "url": "/redirectAppPage?page=TaobaoItemList&category=女装&subcategory=连衣裙"
//        },
//        {
//        "keywords": "羊绒衫,针织衫",
//        "name": "毛衣",
//        "picUrl": "https://gw.alicdn.com/tps/TB1htufOVXXXXcyXXXXXXXXXXXX-350-350.jpg_120x120Q75s100.jpg",
//        "url": "/redirectAppPage?page=TaobaoItemList&category=女装&subcategory=毛衣"
//        },
//        {
//        "keywords": "A字裙,半裙,a字裙",
//        "name": "半身裙",
//        "picUrl": "https://gw.alicdn.com/tps/TB1UdcGPVXXXXajaXXXXXXXXXXX-375-375.jpg_120x120Q75s100.jpg",
//        "url": "/redirectAppPage?page=TaobaoItemList&category=女装&subcategory=半身裙"
//        },
//        {
//        "keywords": "棒球服,夹克,风衣,大衣,棉服,棉袄,羽绒服,斗篷",
//        "name": "外套",
//        "picUrl": "https://gw.alicdn.com/tps/TB1P.CQPXXXXXbiXpXXXXXXXXXX-350-350.jpg_120x120Q75s100.jpg",
//        "url": "/redirectAppPage?page=TaobaoItemList&category=女装&subcategory=外套"
//        },
//        {
//        "name": "套装",
//        "picUrl": "https://gw.alicdn.com/tps/TB18ZAqNXXXXXawXpXXXXXXXXXX-375-375.jpg_120x120Q75s100.jpg",
//        "url": "/redirectAppPage?page=TaobaoItemList&category=女装&subcategory=套装"
//        }
//        ],
//        "style": "IconAndText",
//        "type": "Grid"
//        }
//        ]
//        }
//        */