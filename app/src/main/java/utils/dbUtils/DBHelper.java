package utils.dbUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;

import bean.TaobaoCoupons;

/**
 * Created by zhengheng on 17/12/11.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "browserecord_db";
    public static final int VERSION = 1;
    public static String TABLE_NAME = "record";


    private static DBHelper dbhelper = null;

    public static DBHelper getInstance(Context context) {
        if (dbhelper == null) {
            dbhelper = new DBHelper(context);
        }
        return dbhelper;
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "( id integer primary key autoincrement , title varchar(20), category varchar(20), picUrl varchar(20), biz30Day varchar(20)," +
                "platform varchar(20),couponShareUrl  varchar(20), couponAmount int(5),discountPrice varchar(10), finalPrice varchar(10))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertDate(TaobaoCoupons.ObjectsBean bean) {
        //获取写数据库
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        //生成要修改或者插入的键值
        ContentValues cv = new ContentValues();

        cv.put("title", bean.getTitle());
        cv.put("category", bean.getCategory());
        cv.put("picUrl", bean.getPicUrl());
        cv.put("platform", bean.getPlatform());
        cv.put("couponShareUrl", bean.getCouponShareUrl());
        cv.put("couponAmount", bean.getCouponAmount());
        cv.put("discountPrice", bean.getDiscountPrice().toString());
        cv.put("finalPrice", bean.getFinalPrice().toString());
        cv.put("biz30Day", bean.getBiz30Day());
        // insert 操作
        db.insert(DBHelper.TABLE_NAME, null, cv);
        //关闭数据库
        db.close();
    }


    public LinkedList<TaobaoCoupons.ObjectsBean> getRecordList() {
        LinkedList<TaobaoCoupons.ObjectsBean> list = new LinkedList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                int couponAmount = cursor.getInt(cursor.getColumnIndex("couponAmount"));
                String biz30Day = cursor.getString(cursor.getColumnIndex("biz30Day"));
                String platform = cursor.getString(cursor.getColumnIndex("platform"));
                String picUrl = cursor.getString(cursor.getColumnIndex("picUrl"));
                String couponShareUrl = cursor.getString(cursor.getColumnIndex("couponShareUrl"));
                String finalPrice = cursor.getString(cursor.getColumnIndex("finalPrice"));
                String discountPrice = cursor.getString(cursor.getColumnIndex("discountPrice"));
                TaobaoCoupons.ObjectsBean bean = new TaobaoCoupons.ObjectsBean(category, couponAmount, biz30Day, title, platform, couponShareUrl, Double.valueOf(finalPrice), Double.valueOf(discountPrice), picUrl);
                list.add(0, bean);

            }
            Log.e("zhengheng  dbhelp", list.toString());
        } catch (SQLException e) {
            Log.e("zhengheng ", "queryDatas" + e.toString());
        } finally {
            if (cursor != null && db != null) {
                cursor.close();
                db.close();
            }
        }
        return list;
    }
}
