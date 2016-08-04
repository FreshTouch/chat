package com.iptv_comm.db.dao.dao;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvProvider;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;

/**
 * DAO interface of TV Provider related functionality
 */
public interface TvProviderDao {

    /**
     * Add a new TV Provider
     * @param tvProvider
     * @return tvProviderId
     * @throws com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException
     */
    int addTvProvider(TvProvider tvProvider) throws DBException;

    /**
     * Removes a TV provider
     * @param tvProviderId
     * @throws DBException
     */
    void deleteTvProvider(long tvProviderId) throws DBException;
    
    long getTvProviderId(String tvProviderName) throws DBException;

}
