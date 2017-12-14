package utils.dbUtils;

import android.content.Context;

import app.CityGuideApplication;
import de.greenrobot.dao.database.Database;
import greendao.dao.AirlineDao;
import greendao.dao.AppDao;
import greendao.dao.ArticleDao;
import greendao.dao.ContactDao;
import greendao.dao.DaoMaster;
import greendao.dao.DaoSession;
import greendao.dao.DocumentDao;
import greendao.dao.GuideDao;
import greendao.dao.RouteDao;
import greendao.dao.VisaDao;
import so.bubu.lib.helper.Helper;
import so.bubu.lib.helper.LogHelper;

/**
 * 外部数据库控制类
 * Created by wneng on 16/8/12.
 */
public class DBController
{

    /**
     * 默认数据库名称:localdata
     */
    public static final String DATABASE_NAME = "bubu_guide_guide.db";
    
    private static final String DB_PWD = "bubu_guide_pwd";
    private static DaoMaster daoMaster;
    private static String lastDBName = "";
    private static DaoSession daoSession;
    private static Database sDatabase;

    private static DaoMaster obtainMaster(Context context, String dbName)
    {
        if (sDatabase == null) {
            sDatabase = new DaoMaster.EncryptedDevOpenHelper(context, dbName){
                @Override
                public void onUpgrade(Database db, int oldVersion, int newVersion) {
                    updateDataBase(db, oldVersion, newVersion);
                }
            }.getWritableDatabase(DB_PWD);
        }else{
            sDatabase.close();
            sDatabase = new DaoMaster.EncryptedDevOpenHelper(context, dbName){
                @Override
                public void onUpgrade(Database db, int oldVersion, int newVersion) {
                    updateDataBase(db, oldVersion, newVersion);
                }
            }.getWritableDatabase(DB_PWD);
        }

        return new DaoMaster(sDatabase);
    }

    public static void updateDataBase(Database db, int oldVersion, int newVersion) {
        if (Helper.isNotEmpty(db) && 3 == newVersion && 3 > oldVersion) {
            db.execSQL("ALTER TABLE article ADD tips VARCHAR(255)");
        }
    }
    
    private static DaoMaster getDaoMaster(Context context, String dbName)
    {


        LogHelper.e("currentDbName = " + lastDBName + "\n dbName = " + dbName);
        if (dbName == null){
            return null;
        }

        if (daoMaster == null) {
            daoMaster = obtainMaster(context, dbName);
            lastDBName = dbName;
        }else{

            if (!lastDBName.equals(dbName)) {
                daoMaster.getDatabase().close();
                daoMaster = obtainMaster(context, dbName);
                lastDBName = dbName;
            }

        }


        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @return
     */
    public static DaoSession getDaoSession(String dbName)
    {

        if (dbName == null) {
            return null;
        }
        if (daoSession == null) {

            daoSession =  getDaoMaster(CityGuideApplication.getInstance(), dbName).newSession();

        }else{

            if (!lastDBName.equals(dbName)) {
                daoSession.clear();
                daoSession.getDatabase().close();
                daoSession = getDaoMaster(CityGuideApplication.getInstance(), dbName).newSession();
            }

        }
        return daoSession;
    }


//    public static DaoSession getDaoSession(String dbName)
//    {
//
////        LogHelper.e("currentDbName = " + lastDBName + "\n dbName = " + dbName);
//        if (dbName == null) {
//            return null;
//        }
//
////        if (daoSession == null) {
////            daoSession =  getDaoMaster(CityGuideApplication.getInstance(), dbName).newSession();
////
////        }else{
////
////            if (!lastDBName.equals(dbName)) {
////                daoSession.clear();
////                daoSession.getDatabase().close();
////                daoSession = getDaoMaster(CityGuideApplication.getInstance(), dbName).newSession();
////            }
////
////        }
//        if (sDatabase == null) {
//             sDatabase = new DaoMaster.EncryptedDevOpenHelper(CityGuideApplication.getInstance(), dbName).getWritableDatabase(DB_PWD);
//        }else{
//            sDatabase.close();
//            sDatabase = new DaoMaster.EncryptedDevOpenHelper(CityGuideApplication.getInstance(), dbName).getWritableDatabase(DB_PWD);
//        }
//
//        DaoSession daoSession = new DaoMaster(sDatabase).newSession();
//        return daoSession;
//    }



    // App数据
    public static AppDao getAppDao(String dbName) {
        return getDaoSession(dbName).getAppDao();
    }

    // Article数据
    public ArticleDao getArticleDao(String dbName) {
        return getDaoSession(dbName).getArticleDao();
    }

    // Contact数据
    public ContactDao getContactDao(String dbName) {
        return getDaoSession(dbName).getContactDao();
    }

    // Guide数据
    public GuideDao getGuideDao(String dbName) {
        return getDaoSession(dbName).getGuideDao();
    }

    // Airline数据
    public AirlineDao getAirlineDao(String dbName) {
        return getDaoSession(dbName).getAirlineDao();
    }

    // Document数据
    public DocumentDao getDocumentDao(String dbName) {
        return getDaoSession(dbName).getDocumentDao();
    }

    // Route数据
    public RouteDao getRouteDao(String dbName) {
        return getDaoSession(dbName).getRouteDao();
    }

    // Visa数据
    public VisaDao getVisaDao(String dbName) {
        return getDaoSession(dbName).getVisaDao();
    }


}
