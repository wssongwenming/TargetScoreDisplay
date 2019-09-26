package com.huasun.display.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * author:songwenming
 * Date:2019/9/24
 * Description:
 */
public class DatabaseManager {
    private DaoSession mDaoSession=null;
    private UserProfileDao mDao=null;

    private DatabaseManager(){}

    public DatabaseManager init(Context context){
        initDao(context);
        return this;
    }
    private static final class Holder{
        private static final DatabaseManager INSTANCE=new DatabaseManager();
    }

    public static DatabaseManager getInstance(){
        return Holder.INSTANCE;
    }

    private void initDao(Context context){
        final ReleaseOpenHelper helper=new ReleaseOpenHelper(context,"bcsb.db");
        final Database db=helper.getWritableDb();
        mDaoSession=new DaoMaster(db).newSession();
        mDao=mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getDao(){
        return mDao;
    }

}
