package bean;


import iconicfont.IconicFontDrawable;

/**
 * Created by wneng on 16/6/24.
 */

public class UserBean {

    private IconicFontDrawable mFontDrawable;

    private int color;

    private String name;

    private int type;

    private int num;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public IconicFontDrawable getFontDrawable() {
        return mFontDrawable;
    }

    public void setFontDrawable(IconicFontDrawable fontDrawable) {
        mFontDrawable = fontDrawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "color=" + color +
                ", mFontDrawable=" + mFontDrawable +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", type=" + type +
                '}';
    }
}
