package com.iptv_comm.services.model;

import java.util.ArrayList;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvChannel;
import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvProgram;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;

public interface TvChannelService {
	
    void addTvChannel(TvChannel channel) throws DBException;
	
	
	void removeTvChannel(int tvChannelId) throws DBException;
	
	void updateTvChannel( TvChannel channel) throws DBException;
	
	ArrayList<TvProgram> getTvProgramList(int tvchannelid) throws DBException;
	
    String getDescription(int tvchannelid) throws DBException;
	
	String getLanguage(int tvchannelid) throws DBException;
}
