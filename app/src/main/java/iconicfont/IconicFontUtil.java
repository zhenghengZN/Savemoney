package iconicfont;

import android.content.Context;
import android.view.View;


import so.bubu.lib.base.BaseApplication;
import so.bubu.lib.helper.Helper;
import iconicfont.icon.Icon;
import com.zhekouxingqiu.main.R;

/**
 * Created by wangwn on 2016/5/16.
 */
public class IconicFontUtil {



    private IconicFontUtil() {

    }

    public static IconicFontDrawable createIconicFontDrawable(Context context, Icon icon, int color){

        IconicFontDrawable iconicFontDrawable = new IconicFontDrawable(context, icon);
        iconicFontDrawable.setIconColor(color);

       return iconicFontDrawable;
    }

    public static IconicFontDrawable createIconicFont(Icon icon, int color){

        IconicFontDrawable iconicFontDrawable = new IconicFontDrawable(BaseApplication.getInstance(), icon);
        iconicFontDrawable.setIconColor(color);

        return iconicFontDrawable;
    }

    public static IconicFontDrawable createIconicFontDrawable(Icon icon){

        IconicFontDrawable iconicFontDrawable = new IconicFontDrawable(BaseApplication.getInstance(), icon);
        iconicFontDrawable.setIconColor(BaseApplication.getInstance().getResources().getColor(R.color.color_848484));

        return iconicFontDrawable;
    }

    public static IconicFontDrawable createIconicFont(Icon icon){

        IconicFontDrawable iconicFontDrawable = new IconicFontDrawable(BaseApplication.getInstance(), icon);

        return iconicFontDrawable;
    }

    public static IconicFontDrawable createIconicFontDrawablePre(Icon icon){

        IconicFontDrawable iconicFontDrawable = new IconicFontDrawable(BaseApplication.getInstance(), icon);
        iconicFontDrawable.setIconColor(BaseApplication.getInstance().getResources().getColor(R.color.color_82cd6b));

        return iconicFontDrawable;
    }

    public static void setIconicFontDrawable(Icon icon, int color, View target){

        IconicFontDrawable iconicFontDrawable = new IconicFontDrawable(BaseApplication.getInstance(), icon);
        iconicFontDrawable.setIconColor(color);

        if (Helper.isNotNull(target)) {
            target.setBackground(iconicFontDrawable);
        }

    }


}
