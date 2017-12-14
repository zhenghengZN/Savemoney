package iconicfont.icon;


import iconicfont.util.TypefaceManager;

/**
 * Created by wangwn on 2016/5/16.
 */
public enum CityGuideIcon implements Icon {

    ICON_SHARE_SINA(0xE74E),

    ICON_SHARE_WEIXING(0xE74B),

    ICON_SHARE_WEIXING_FRIEND(0xE74D),

    ICON_NAME(0xe69d),

    ICON_PWD(0xe686),

    ICON_CODE(0xe65c),

    ICON_DEFAULT_LOGO(0xe701),

    ICON_CLEAR_CACHE(0xe6ca),

    ICON_SETTING_PHONE(0xe6cf),

    ICON_SINA(0xe6a5),

    ICON_MAP(0xe6b4),

    ICON_QQ(0xe6da),

    ICON_WEB(0xE69F),

    ICON_VERSION(0xe6c5),

    ICON_SUPPORT(0xe6db),

    ICON_SHARE_FRIEND(0xe6f8),

    ICON_ORDER(0xE77C),

    ICON_RIGHT(0xe675),

    ICON_COLLECT(0xE76C),

    ICON_INFOR(0xE6EE),

    ICON_SUPPORT_FEEDBACK(0xE765),

    ICON_SETTING(0xE6DF),

    ICON_LETTER(0xe6de),

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

    ICON_USER_NAME(0xe6d4),

    ICON_NICKNAME(0xe728),

    ICON_USER_SEX(0xe72b),

    ICON_BIRTHDAY(0xe72a),

    ICON_USER(0xE687),

    ICON_BIND_TAOBAO(0xe67c),

    ICON_SHOP(0xe694),

    ICON_RECORD(0xe68d),

    ICON_SETTTING(0xe692),

    ICON_MINE_ORDER(0xe672);


    private int mIconUtfValue;

    private CityGuideIcon(int iconUtfValue) {
        mIconUtfValue = iconUtfValue;
    }



    public void setmIconUtfValue(int mIconUtfValue) {
        this.mIconUtfValue = mIconUtfValue;
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
