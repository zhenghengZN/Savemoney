package utils.photopicker.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wangwn on 2016/5/17.
 */
public class CropUtil {

    /**
     * 压缩图片并存入文件 使用该方法只会缩小文件SIZE 并不会缩小图片加载在内存中的SIZE
     *
     * @return 返回FILE 可以用get path来获得路径
     * @throws IOException
     */
    public static File compressAndSaveFile(Bitmap bitmap, String fileName, String path) throws IOException {
        final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";//保存到SD卡
        //产生目录 没有则新建目录
        String subForder = SAVE_PIC_PATH + path;
        File foder = new File(subForder);
        if (!foder.exists()) {
            foder.mkdirs();
        }
        //产生文件 没有则新建文件
        File myCaptureFile = new File(subForder, fileName);
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        //压缩图片
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;//个人喜欢从80开始,
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        int times = 0;
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            times++;
            if (times > 7)
                break;
        }
        //写入文件流
        try {
            FileOutputStream fos = new FileOutputStream(myCaptureFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        baos.flush();
        baos.close();
        return myCaptureFile;
    }
}
