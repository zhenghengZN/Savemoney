package utils.photopicker.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.avos.avoscloud.AVUser;

import java.io.File;
import utils.UserHelper;
import wiget.LoadingDialog;

/**
 * 由于替换了新裁剪控件而需要的新裁剪保存任务
 * Created by johnreese on 15/12/14.
 */
public class CropImageNewTask extends AsyncTask<String, Integer, String> {

    private Context mContext;

    private String path;

    private Bitmap bitmap;

    public CropImageNewTask(Context mContext) {
        this.mContext = mContext;
    }

    public void setParam(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    //onPreExecute方法用于在执行后台任务前做一些UI操作
    @Override
    protected void onPreExecute() {
    }

    //doInBackground方法内部执行后台任务,不可在此方法内修改UI
    @Override
    protected String doInBackground(String... params) {
        try {

            String fileName = System.currentTimeMillis() + ".jpg";
            String middlePath = "";
            AVUser user = UserHelper.getInstance().getCurrentUser();
            if (user != null) {
                middlePath = "/" + user.getSessionToken();
            }
            path = "/BUBU"+ middlePath +"/Crops/" + fileName;
            File file = CropUtil.compressAndSaveFile(bitmap, fileName, "/BUBU"+ middlePath +"/Crops");
            path = file.getPath();
            //recycle
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //onPreExecute方法用于在完成后台任务后做一些UI操作
    @Override
    protected void onPostExecute(String result) {
        LoadingDialog.getInstance(mContext).hideLoading();
        Intent intent = new Intent();
        intent.putExtra("data", path);
        Activity cropActivity = (Activity) mContext;
        cropActivity.setResult(Activity.RESULT_OK, intent);
        cropActivity.finish();
    }

}
