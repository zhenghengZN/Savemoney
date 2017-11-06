package iconicfont.icon;


import iconicfont.util.TypefaceManager;

/**
 * Created by wangwn on 2016/5/16.
 */
public enum CityGuideIcon implements Icon {

    ICON_NEW_GOODS(0xE757),

    ICON_ADD_IMG(0xe6f0),

    ICON_CLOSE(0xe6dc),

    ICON_TIME(0xE698),

    ICON_GO(0xE675),

    ICON_HOTEL(0xE678),

    ICON_CUISINA(0xE665),

    ICON_CORRECT(0xE68B),

    ICON_BACK(0xE658),

    ICON_AUTHOR_NEW(0xE75B),

    ICON_SEARCH(0xE690),

    ICON_WX(0xe6a4),

    ICON_WEB_NEW(0xE75C),

    ICON_ZHIHU(0xE75D),

    ICON_USER(0xE687);


    private final int mIconUtfValue;

    private CityGuideIcon(int iconUtfValue) {
        mIconUtfValue = iconUtfValue;
    }


    @Override
    public TypefaceManager.IconicTypeface getIconicTypeface() {
        return TypefaceManager.IconicTypeface.CITYGUIDE;
    }


    @Override
    public int getIconUtfValue() {
        return mIconUtfValue;
    }
}
