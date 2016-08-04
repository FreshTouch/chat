package com.iptv_comm.db.dao.factory;

import com.iptv_comm.db.dao.dao.MessageDao;
import com.iptv_comm.db.dao.dao.TvChannelDao;
import com.iptv_comm.db.dao.dao.TvProgramDao;
import com.iptv_comm.db.dao.dao.TvProviderDao;
import com.iptv_comm.db.dao.dao.UserDao;
import com.iptv_comm.db.dao.dao.impl.mysql.JdbcMessageDao;
import com.iptv_comm.db.dao.dao.impl.mysql.JdbcTvChannelDao;
import com.iptv_comm.db.dao.dao.impl.mysql.JdbcTvProgramDao;
import com.iptv_comm.db.dao.dao.impl.mysql.JdbcTvProviderDao;
import com.iptv_comm.db.dao.dao.impl.mysql.JdbcUserDao;

/**
 * MySql extension of DAO factory
 */
public class MySqlDaoFactory extends DaoFactory {
    @Override
    public TvProviderDao getTvProviderDao() {
        return new JdbcTvProviderDao();
    }

    @Override
    public UserDao getUserDao() {
    	//JdbcUserDao must implement UserDao
        return new JdbcUserDao();
    }

	@Override
	public TvChannelDao getTvChannelDao() {
		return new JdbcTvChannelDao();
	}

	@Override
	public TvProgramDao getTvProgramDao() {
		return new JdbcTvProgramDao();
	}

	@Override
	public MessageDao getMessageDao() {
		// TODO Auto-generated method stub
		return new JdbcMessageDao();
	}
}
