package utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import so.bubu.lib.helper.ResourceHelper;
import so.bubu.Coupon.AliTrade.R;

/**
 * Created by wangwn on 2016/4/22.
 */
public class GlideHelper {


    public static void display(Context context, int resId, ImageView tagetView) {

        Glide
                .with(context)
                .load(resId)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(tagetView);
    }

    /**
     * 加载圆角图片
     * @param context
     * @param resId
     * @param targetWidth
     * @param targetHeight
     * @param radis
     * @param targetView
     */
    public static void displayRoundedCornersImage(Context context, int resId, int targetWidth, int targetHeight,int radis,ImageView targetView) {

        Picasso
                .with(context)
                .load(resId)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .resize(targetWidth, targetHeight)
                .centerCrop()
                .transform(new so.bubu.lib.base.transformation.RoundedCornersTransformation(radis, 0))
                .into(targetView);

    }

    public static void displayRoundedCornersImageNoError(Context context, String url, int targetWidth, int targetHeight,int radis,ImageView targetView) {

        Glide
                .with(context)
                .load(url)
                .override(targetWidth, targetHeight)
                .centerCrop()
                .bitmapTransform(new RoundedCornersTransformation(context, radis, 0))
                .into(targetView);

    }

    public static void displayRoundedCornersImage(Context context, String url, int targetWidth, int targetHeight,int radis,ImageView targetView) {

        Picasso
                .with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .resize(targetWidth, targetHeight)
                .centerCrop()
                .transform(new so.bubu.lib.base.transformation.RoundedCornersTransformation(radis, 0))
                .into(targetView);

    }

    public static void displayRoundedCornersImageWithoutPlace(Context context, String url, int targetWidth, int targetHeight,int radis,ImageView targetView) {

        Picasso
                .with(context)
                .load(url)
                .resize(targetWidth, targetHeight)
                .centerCrop()
                .placeholder(R.drawable.loading)
                .transform(new so.bubu.lib.base.transformation.RoundedCornersTransformation(radis, 0))
                .into(targetView);

    }

    public static void displayRoundedCornersImage702(Context context, String url, int targetWidth, int targetHeight,int radis,ImageView targetView) {

        Picasso
                .with(context)
                .load(url)
                .placeholder(R.drawable.loading_702)
                .error(R.drawable.loading_702)
                .resize(targetWidth, targetHeight)
                .centerCrop()
                .transform(new so.bubu.lib.base.transformation.RoundedCornersTransformation(radis, 0))
                .into(targetView);

    }

    public static void displayRoundedCornersImage702(Context context, String url, int targetWidth, int targetHeight,int radis,ImageView targetView, so.bubu.lib.base.transformation.RoundedCornersTransformation.CornerType cornerType) {

        Picasso
                .with(context)
                .load(url)
                .placeholder(R.drawable.loading_702)
                .error(R.drawable.loading_702)
                .resize(targetWidth, targetHeight)
                .centerCrop()
                .transform(new so.bubu.lib.base.transformation.RoundedCornersTransformation(radis, 0, cornerType))
                .into(targetView);

    }

    public static void displayRoundedCornersImage(Context context, File url, int targetWidth, int targetHeight,int radis,ImageView targetView) {

        Picasso
                .with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .resize(targetWidth, targetHeight)
                .centerCrop()
                .transform(new so.bubu.lib.base.transformation.RoundedCornersTransformation(radis, 0))
                .into(targetView);

    }

    public static void display(Context context, File file, ImageView tagetView) {

        Glide
                .with(context)
                .load(file)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(tagetView);
    }

    public static void display(Context context, String url, ImageView tagetView) {

        Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(tagetView);
    }

    public static void display(Context context, Uri uri, ImageView tagetView) {

        Glide
                .with(context)
                .load(uri)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(tagetView);
    }

    /**
     * 加载图片带淡入淡出动画
     * 重载crossFade(int duration)等可以自定义动画效果
     *
     * @param context
     * @param resId
     * @param tagetView
     */
    public static void displayImageWithAnimate(Context context, int resId, ImageView tagetView) {

        Glide
                .with(context)
                .load(resId)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .crossFade()
                .into(tagetView);
    }

    public static void displayImageWithAnimate(Context context, String imgUrl, ImageView tagetView) {

        Glide
                .with(context)
                .load(imgUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .crossFade()
                .into(tagetView);
    }

    /**
     * 强制加载图片不带动画效果
     *
     * @param context
     * @param resId
     * @param tagetView
     */
    public static void displayImageWithOutAnimate(Context context, int resId, ImageView tagetView) {

        Glide
                .with(context)
                .load(resId)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .dontAnimate()
                .into(tagetView);
    }

    public static void displayImageWithOutAnimate(Context context, String imgUrl, ImageView tagetView) {

        Glide
                .with(context)
                .load(imgUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .dontAnimate()
                .into(tagetView);
    }


    /**
     * 加载gif动态图片
     *
     * @param context
     * @param gifUrl
     * @param tagetView
     */
    public static void loadGif(Context context, String gifUrl, ImageView tagetView) {

        Glide
                .with(context)
                .load(gifUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(tagetView);
    }

    /**
     * 加载gif动态图片
     *
     * @param context
     * @param gifId
     * @param tagetView
     */
    public static void loadGif(Context context, int gifId, ImageView tagetView) {

        Glide
                .with(context)
                .load(gifId)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(tagetView);
    }

    /**
     * gif转为 bitmap，只显示第一帧
     *
     * @param context
     * @param gifUrl
     * @param tagetView
     */
    public static void loadGifToBitmap(Context context, String gifUrl, ImageView tagetView) {

        Glide
                .with(context)
                .load(gifUrl)
                .asBitmap()
                .into(tagetView);
    }

    /**
     * 显示本地视频
     *
     * @param context
     * @param videoPath
     * @param tagetView
     */
    public static void loadLocalVideo(Context context, String videoPath, ImageView tagetView) {

        Glide
                .with(context)
                .load(Uri.fromFile(new File(videoPath)))
                .into(tagetView);
    }

    /**
     * 调整图片大小
     * CenterCrop()是一个裁剪技术，即缩放图像让它填充到 ImageView 界限内并且侧键额外的部分。ImageView 可能会完全填充，但图像可能不会完整显示。
     * fitCenter() 是裁剪技术，即缩放图像让图像都测量出来等于或小于 ImageView 的边界范围。该图像将会完全显示，但可能不会填满整个 ImageView。
     *
     * @param context
     * @param resId
     * @param targetWidth
     * @param targetHeight
     * @param targetView
     */
    public static void displayImageByResize(Context context, int resId, int targetWidth, int targetHeight, ImageView targetView) {

        Glide
                .with(context)
                .load(resId)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .override(targetWidth, targetHeight)
                .centerCrop()
                .bitmapTransform(new RoundedCornersTransformation(context, ResourceHelper.Dp2Px(4), 0))
                .into(targetView);

    }

    public static void displayImageByResize(Context context, String url, int targetWidth, int targetHeight, ImageView targetView) {

        Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .override(targetWidth, targetHeight)
                .centerCrop()
                .into(targetView);
    }


    public static void displayImageByResizeasBitmap(Context context, String url, int targetWidth, int targetHeight, ImageView targetView) {

        Glide
                .with(context)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .override(targetWidth, targetHeight)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(targetView);
    }

    /**
     * 跳过内存缓存
     *
     * @param context
     * @param resId
     * @param targetView
     */
    public static void displayImageWithoutMenmoryCache(Context context, int resId, ImageView targetView) {

        Glide
                .with(context)
                .load(resId)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .skipMemoryCache(true)
                .into(targetView);
    }

    public static void displayImageWithoutMenmoryCache(Context context, String url, ImageView targetView) {

        Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .skipMemoryCache(true)
                .into(targetView);
    }

    /**
     * 不使用缓存
     *
     * @param context
     * @param resId
     * @param targetView
     */
    public static void displayImageWithoutCache(Context context, int resId, ImageView targetView) {

        Glide
                .with(context)
                .load(resId)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(targetView);
    }

    public static void displayImageWithoutCache(Context context, String url, ImageView targetView) {

        Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(targetView);
    }

    /**
     * 加载图片优先级
     *
     * @param context
     * @param resId
     * @param targetView
     */
    public static void loadImageWithHighPriority(Context context, int resId, ImageView targetView) {

        Glide
                .with(context)
                .load(resId)
                .priority(Priority.HIGH)
                .into(targetView);
    }

    public static void loadImageWithHighPriority(Context context, String url, ImageView targetView) {

        Glide
                .with(context)
                .load(url)
                .priority(Priority.HIGH)
                .into(targetView);
    }

    public static void loadImagesWithLowPriority(Context context, int resId, ImageView targetView) {

        Glide
                .with(context)
                .load(resId)
                .priority(Priority.LOW)
                .into(targetView);

    }

    public static void loadImagesWithLowPriority(Context context, String url, ImageView targetView) {

        Glide
                .with(context)
                .load(url)
                .priority(Priority.LOW)
                .into(targetView);

    }

    /**
     * 加载缩略图
     *
     * @param context
     * @param resId
     * @param targetView
     */
    public static void loadImageWithThumbnail(Context context, int resId, ImageView targetView, float size) {

        Glide
                .with(context)
                .load(resId)
                .thumbnail(size)
                .into(targetView);

    }

    public static void loadImageWithThumbnail(Context context, String url, ImageView targetView, float size) {

        Glide
                .with(context)
                .load(url)
                .thumbnail(size)
                .into(targetView);
    }


    public static void loadImageThumbnailRequest(Context context, int resId, ImageView targetView) {

        // setup Glide request without the into() method
        DrawableRequestBuilder<Integer> thumbnailRequest = Glide
                .with(context)
                .load(resId);
        Glide
                .with(context)
                .load(resId)
                .thumbnail(thumbnailRequest)
                .into(targetView);

    }

    public static void loadImageThumbnailRequest(Context context, String url, ImageView targetView) {

        // setup Glide request without the into() method
        DrawableRequestBuilder<String> thumbnailRequest = Glide
                .with(context)
                .load(url);
        Glide
                .with(context)
                .load(url)
                .thumbnail(thumbnailRequest)
                .into(targetView);
    }

    /**
     * 高斯模糊
     * @param context
     * @param resId
     * @param targetView
     */
    public static void loadImageWithBlur(Context context,int resId,ImageView targetView){
        Glide
                .with(context)
                .load(resId)
                .bitmapTransform(new BlurTransformation(context, 18))
                .into(targetView);
    }

    /**
     * 高斯模糊
     * @param context
     * @param url
     * @param targetView
     */
    public static void loadImageWithBlur(Context context,String url,ImageView targetView){
        Glide
                .with(context)
                .load(url)
                .bitmapTransform(new BlurTransformation(context, 18))
                .into(targetView);
    }



}
