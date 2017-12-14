package utils.dbUtils;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import app.CityGuideApplication;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.database.Database;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

import greendao.dao.AppDao;
import greendao.dao.DaoMaster;
import greendao.dao.DaoSession;
import greendao.dao.DraftBoxDao;
import greendao.dao.GuideDao;
import greendao.dao.HistoryDao;
import greendao.dao.RestaurantDao;
import so.bubu.lib.helper.Helper;

/**
 * 数据库管理类，采用单例模式
 * GreenDao并不保证线程安全，因此要加读写锁
 */
public class DbManager<T> {

    public static final String DB_NAME = "bubu_guide_guide.db";

    public static final String DB_ADELAIDE = "adelatde.db";
    public static final String DB_BRISBANE = "brisbane.db";
    public static final String DB_CAIRNS = "cairns.db";
    public static final String DB_GOLDCOAST = "goldcoast.db";
    public static final String DB_MELBOURNE = "melbourne.db";
    public static final String DB_NORTHERN = "northern.db";
    public static final String DB_SYDNEY = "sydney.db";
    public static final String DB_TASMANIA = "tasmainia.db";

    private static final String DB_PWD = "bubu_guide_pwd";
    private static DbManager INSTANCE = new DbManager();

    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private ReentrantReadWriteLock mReentrantLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock mReadLock = mReentrantLock.readLock();
    private ReentrantReadWriteLock.WriteLock mWriteLock = mReentrantLock.writeLock();
    private Thread mInitThread;
    private DaoMaster.EncryptedDevOpenHelper mEncryptedHelper;

    public static DbManager getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化工作，耗时操作，不能放在主线程内
     */
    public void initialize(final String dbName) {
//        if (Helper.isNull(mEncryptedHelper)) {
            mInitThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    initEncryptedDB(dbName);
//                    initUnencryptedDB(dbName);
                }
            });
            mInitThread.start();
//        }
    }

    /**
     * 初始化工作，耗时操作，不能放在主线程内
     */
    public void initialize() {
        if (Helper.isNull(mEncryptedHelper)) {
            mInitThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    initEncryptedDB(DB_NAME);
//                    initUnencryptedDB(DB_NAME);
                }
            });
            mInitThread.start();
        }
    }

    /**
     * 使用加密本地数据库
     */
    private DaoMaster initEncryptedDB(String dbName) {

        mEncryptedHelper = new DaoMaster.EncryptedDevOpenHelper(CityGuideApplication.getInstance(), dbName){
            @Override
            public void onUpgrade(Database db, int oldVersion, int newVersion) {
                MigrationHelper.migrate(db,
                        DraftBoxDao.class);

                DBController.updateDataBase(db, oldVersion, newVersion);

            }
        };

        Database readableDatabase = mEncryptedHelper.getReadableDatabase(DB_PWD);
        mDaoMaster = new DaoMaster(readableDatabase);
        return mDaoMaster;
    }

    /**
     * 使用不加密本地数据库
     */
    private DaoMaster initUnencryptedDB(String dbName) {
        mHelper = new DaoMaster.DevOpenHelper(CityGuideApplication.getInstance(), dbName);
        mDaoMaster = new DaoMaster(mHelper.getReadableDatabase());
        return mDaoMaster;
    }

    /**
     * 获取DaoMaster
     *
     * @return
     */
    public DaoMaster getDaoMaster() {
        if (Helper.isNull(mDaoMaster)) {
            try {
                mInitThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return mDaoMaster;
    }

    /**
     * 获取DaoSession
     *
     * @return
     */
    public DaoSession getSession() {
        return getDaoMaster().newSession();
    }

    // App数据
    public AppDao getAppDao() {
        return getSession().getAppDao();
    }

    public void updateData(T t, List<String> delKeys, List<String> updateKeys, List<T> updateDatas, Property property) {
        try {
            mWriteLock.lock();

            if (Helper.isNotEmpty(delKeys)) {
                ((AbstractDao) t).deleteByKeyInTx(delKeys);
            }

            // 获取已有数据
//            if (Helper.isNotEmpty(updateDatas)) {
//                List<T> updateList = new ArrayList<>(), addList = new ArrayList<>();
//                int size = updateKeys.size();
//                for (int i = 0; i < size; i++) {
//                    T updateMenu = updateDatas.get(i);
//                    T oldMenu = (T) ((AbstractDao) t).queryBuilder().where(property.eq(updateKeys.get(i))).unique();
//                    if (Helper.isEmpty(oldMenu)) {
//                        // 新数据
//                        addList.add(updateMenu);
//                    } else {
//                        // 更新数据
//                        updateList.add(updateMenu);
//                    }
//                }
//
//                if (0 < addList.size()) {
//                    ((AbstractDao) t).insertOrReplaceInTx(addList);
//                }
//                if (0 < updateList.size()) {
//                    ((AbstractDao) t).updateInTx(updateList);
//                }
//            }
            ((AbstractDao) t).insertOrReplaceInTx(updateDatas);

        } finally {
            mWriteLock.unlock();
        }
    }



    public void deleteAll(T t) {
        try {
            mWriteLock.lock();
            ((AbstractDao) t).deleteAll();
        } finally {
            mWriteLock.unlock();
        }
    }

    // Guide数据
    public GuideDao getGuideDao() {
        return getSession().getGuideDao();
    }

    /**
     * 读取数据
     */
    public List<T> getSelectData(T t) {
        return getSelectData(t, null);
    }

    /**
     * 读取数据
     */
    public List<T> getSelectData(T t, int limit) {
        return getSelectData(t, limit, null);
    }

    /**
     * 读取数据
     */
    public List<T> getSelectData(T t, Property descProperty) {
        return getSelectData(t, descProperty, null);
    }

    /**
     * 读取数据
     */
    public List<T> getSelectData(T t, WhereCondition whereCondition, WhereCondition... condMore) {
        return getSelectData(t, 0, null, whereCondition, condMore);
    }

    /**
     * 读取数据
     */
    public List<T> getSelectData(T t, int limit, WhereCondition whereCondition, WhereCondition... condMore) {
        return getSelectData(t, limit, null, whereCondition, condMore);
    }

    /**
     * 读取数据
     */
    public List<T> getSelectData(T t, Property descProperty, WhereCondition whereCondition, WhereCondition... condMore) {
        return getSelectData(t, 0, descProperty, whereCondition, condMore);
    }

    /**
     * 读取数据
     */
    public List<T> getSelectData(T t, Property descProperty, Property descPropertyTwo, WhereCondition whereCondition, WhereCondition... condMore) {
        QueryBuilder abstractDao = ((AbstractDao) t).queryBuilder();
        if (Helper.isNotNull(whereCondition)) {
            abstractDao.where(whereCondition, condMore);
        }
//        if (Helper.isNotNull(descProperty)) {
            abstractDao.orderDesc(descProperty, descPropertyTwo);
//        }
        return abstractDao.list();
    }

    /**
     * 读取数据
     */
    public List<T> getSelectData(T t, int limit, Property descProperty, WhereCondition whereCondition, WhereCondition... condMore) {
        QueryBuilder abstractDao = ((AbstractDao) t).queryBuilder();
        if (Helper.isNotNull(whereCondition)) {
            abstractDao.where(whereCondition, condMore);
        }
        if (0 != limit) {
            abstractDao.limit(limit);
        }
        if (Helper.isNotNull(descProperty)) {
            abstractDao.orderDesc(descProperty);
        }
        return abstractDao.list();
    }

    /**
     * 读取数据
     */
    public List<T> getSelectData(T t, int limit, Property descProperty, Property aesProperty, WhereCondition whereCondition, WhereCondition... condMore) {
        QueryBuilder abstractDao = ((AbstractDao) t).queryBuilder();
        if (Helper.isNotNull(whereCondition)) {
            abstractDao.where(whereCondition, condMore);
        }
        if (0 != limit) {
            abstractDao.limit(limit);
        }
        if (Helper.isNotNull(descProperty)) {
            abstractDao.orderDesc(descProperty);
        }
        if (Helper.isNotNull(aesProperty)) {
            abstractDao.orderAsc(aesProperty);
        }
        return abstractDao.list();
    }

    /**
     * 读取数据
     */
    public List<T> getSelectDataList(T t, Property descProperty, WhereCondition whereCondition, WhereCondition... condMore) {
        QueryBuilder abstractDao = ((AbstractDao) t).queryBuilder();
        abstractDao.where(whereCondition, condMore);
        abstractDao.orderAsc(descProperty);
        return abstractDao.list();
    }

    /**
     * 读取数据
     */
    public List<T> getSelectDataListDesc(T t, Property descProperty, WhereCondition whereCondition, WhereCondition... condMore) {
        QueryBuilder abstractDao = ((AbstractDao) t).queryBuilder();
        abstractDao.where(whereCondition, condMore);
        abstractDao.orderDesc(descProperty);
        return abstractDao.list();
    }

//    public static List<Restaurant> getRestayrantList() {
//        List<Place> placelist = DbManager.getInstance().getPlaceDao().queryBuilder().where(PlaceDao.Properties.Category.eq("Restaurant")).orderDesc(PlaceDao.Properties.Rating).limit(8).list();
//        List<Restaurant> restaurantList = new ArrayList<>();
//        for (Place place : placelist) {
//            DataAssociationBean dataAssociationBean = JSON.parseObject(place.getRestaurant(), DataAssociationBean.class);
//            restaurantList.add(DbManager.getInstance().getRestaurantDao().queryBuilder().where(RestaurantDao.Properties.ObjectId.eq(dataAssociationBean.getObjectId())).unique());
//        }
//        return restaurantList;
//    }

    private static final String CUISINES_DISTINCT = "SELECT DISTINCT " + RestaurantDao.Properties.Cuisines.name + " FROM " + RestaurantDao.TABLENAME;

    public List<String> getRestayrantList(String whereContent) {
        List<String> result = new ArrayList<>();
        Cursor c = DbManager.getInstance().getSession().getDatabase().rawQuery(CUISINES_DISTINCT + whereContent, null);
        try{
            if (c.moveToFirst()) {
                do {
                    result.add(c.getString(0));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return result;
    }

//    private static final String GET_PLACE_PHOTO = "SELECT "
//                                                    + PlaceDao.Properties.ObjectId.name + ", "
//                                                    + PlaceDao.Properties.Name.name + ", "
//                                                    + PlaceDao.Properties.NameCn.name + ", "
//                                                    + PlaceDao.Properties.Subcategory.name + ", "
//                                                    + PlaceDao.Properties.Rating.name + ", "
//                                                    + DocumentDao.Properties.Url.name
//                                                    + " FROM " + PlaceDao.TABLENAME + ", " + DocumentDao.TABLENAME
//                                                    + " WHERE " + PlaceDao.Properties.ObjectId.name + "=" + DocumentDao.Properties.PlaceCoverImageId.name + ", "
//                                                    + PlaceDao.Properties.Category.name + "='Hotel'";

//    private static final String SQL_DISTINCT_ENAME = "SELECT DISTINCT " + PlaceDao.Properties.Subcategory.name + " FROM " + PlaceDao.TABLENAME + " WHERE " + PlaceDao.Properties.Category.name + "='Hotel'";
//
//    public List<String> listEName() {
//        List<String> result = new ArrayList<>();
//        Cursor c = DbManager.getInstance().getSession().getDatabase().rawQuery(SQL_DISTINCT_ENAME, null);
//        try{
//            if (c.moveToFirst()) {
//                do {
//                    result.add(c.getString(0));
//                } while (c.moveToNext());
//            }
//        } finally {
//            c.close();
//        }
//        return result;
//    }





    // History数据
    public HistoryDao getHistoryDao() {
        return getSession().getHistoryDao();
    }




    public ReentrantReadWriteLock.ReadLock getReadLock() {
        return mReadLock;
    }

    public ReentrantReadWriteLock.WriteLock getWriteLock() {
        return mWriteLock;
    }


    public void insertOrReplaceInTx(T t, T bean) {
        try {
            mWriteLock.lock();
            ((AbstractDao) t).insertOrReplaceInTx(bean);
        } finally {
            mWriteLock.unlock();
        }
    }
}
