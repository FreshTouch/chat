package com.iptv_comm.db.dao.dao;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvProgram;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;

public interface TvProgramDao {

	long addTvProgram(TvProgram program) throws DBException;
	
	
	void removeTvProgram(long tvProgramId) throws DBException;
	
	String getTvProgramDescription(long tvProgramId) throws DBException;
	
	public void updateTvProgram( TvProgram program) throws DBException;
}