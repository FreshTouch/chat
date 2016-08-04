package com.iptv_comm.services.model;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvProgram;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;

public interface TvProgramService {
	void addTvProgram(TvProgram program) throws DBException;
	
	
	void removeTvProgram(long tvProgramId) throws DBException;
	
	void updateTvProgram( TvProgram program) throws DBException;
	
	String getTvProgramDescription(long tvProgramId) throws DBException;
}

