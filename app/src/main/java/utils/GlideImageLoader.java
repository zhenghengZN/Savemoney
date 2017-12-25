package utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.youth.banner.loader.ImageLoader;
import com.zhekouxingqiu.main.R;

/**
 * Created by zhengheng on 17/12/18.
 */
public class GlideImageLoader extends ImageLoader {
    private  static GlideImageLoader instance;

    private GlideImageLoader(){}
    private static int targetWidth;
    private static int targetHeight;
    public static synchronized GlideImageLoader getInstance(int w,int h){
        if(instance == null) {
            instance = new GlideImageLoader();
            targetWidth = w;
            targetHeight = h;
        }
        return instance;
    }
    @Override
    public void displayImage(Context context, Object o, ImageView imageView) {

        Glide
                .with(context.getApplicationContext())
                .load(o)
//                .crossFade()
                .asBitmap()
                .placeholder(R.drawable.imagebackground)
                .error(R.drawable.imagebackground)
                .override(targetWidth, targetHeight)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
//                .animate(animationObject)
                .into(imageView);
    }


}
