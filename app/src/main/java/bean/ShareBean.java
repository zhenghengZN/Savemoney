package bean;

import iconicfont.icon.Icon;

/**
 * @author linhuan on 2016/12/1 下午2:44
 */
public class ShareBean {

    private Icon shareIcon;
    private String shareTitle;
    private int shareColor;

    public ShareBean(Icon shareIcon, String shareTitle, int shareColor) {
        this.shareIcon = shareIcon;
        this.shareTitle = shareTitle;
        this.shareColor = shareColor;
    }

    public Icon getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(Icon shareIcon) {
        this.shareIcon = shareIcon;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public int getShareColor() {
        return shareColor;
    }

    public void setShareColor(int shareColor) {
        this.shareColor = shareColor;
    }
}
