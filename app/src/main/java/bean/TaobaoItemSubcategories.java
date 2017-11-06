package bean;

/**
 * Created by zhengheng on 17/11/1.
 */
public class TaobaoItemSubcategories {

    /**
     * keywords : 上衣,卫衣,T恤
     * name : 上装
     * picUrl : http://gd1.alicdn.com/imgextra/i6/TB1U1gBXLNZWeJjSZFpYXFjBFXa_M2.SS2_120x120
     */

    private String keywords;
    private String name;
    private String picUrl;
    /**
     * matchTaobaoCategory : 居家日用/家庭/个人清洁工具
     */

    private String matchTaobaoCategory;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMatchTaobaoCategory() {
        return matchTaobaoCategory;
    }

    public void setMatchTaobaoCategory(String matchTaobaoCategory) {
        this.matchTaobaoCategory = matchTaobaoCategory;
    }
}
