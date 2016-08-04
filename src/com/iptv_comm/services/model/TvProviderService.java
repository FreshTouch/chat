package com.iptv_comm.services.model;

import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;

/**
 * Contains method declarations for TV Provider related functionality
 */
public interface TvProviderService {
    /**
     * Add a TV Provider company
     * @param companyName unique comany name
     * @throws DBException
     */
    void addTvProvider(String companyName) throws DBException;

    /**
     * Remove a TV Provider company
     * @param companyName unique company name
     * @throws DBException
     */
    void removeTvProvider(String companyName) throws DBException;

}
