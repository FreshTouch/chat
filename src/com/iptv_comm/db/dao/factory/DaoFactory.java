package com.iptv_comm.db.dao.factory;

import com.iptv_comm.db.dao.dao.MessageDao;
import com.iptv_comm.db.dao.dao.TvChannelDao;
import com.iptv_comm.db.dao.dao.TvProgramDao;
import com.iptv_comm.db.dao.dao.TvProviderDao;
import com.iptv_comm.db.dao.dao.UserDao;

/**
 * The  DAO factory abstract superclass
 */
public abstract class DaoFactory {
    //supported and future db types
    public static enum DBType{
        MySQL, PostgreSQL,Oracle};

    public abstract TvProviderDao getTvProviderDao();
    public abstract UserDao getUserDao();
    public abstract TvChannelDao getTvChannelDao();
    public abstract TvProgramDao getTvProgramDao();
    public abstract MessageDao getMessageDao();
    //... add your DAO getters


    /**
     * Returns concrete DAO factory class for specified DB type
     * @param dbType the Database type
     * @return a concrete DAO factory
     */
    public static DaoFactory getDaoFactory(
            DBType dbType
    ){
        switch(dbType){
            case MySQL:
                return new MySqlDaoFactory();
            default:
                return null;
        }
    }

}
