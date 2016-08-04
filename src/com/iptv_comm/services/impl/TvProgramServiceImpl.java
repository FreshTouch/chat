package com.iptv_comm.services.impl;

import java.sql.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvProgram;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;
import com.iptv_comm.db.dao.dao.impl.mysql.JdbcTvProgramDao;
import com.iptv_comm.services.model.TvProgramService;


@Path("/TvProgram")
public class TvProgramServiceImpl implements TvProgramService{

	JdbcTvProgramDao tvProgramDao;
	
	
	public TvProgramServiceImpl(){
		tvProgramDao = new JdbcTvProgramDao();
	}
	
	

	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_XML })
	@Override
	public void addTvProgram(TvProgram program)	throws DBException {		
		tvProgramDao.addTvProgram(program);
	}

	
	@DELETE
	@Path("/remove/{tvProgramId}")
	@Override
	public void removeTvProgram(@PathParam("tvProgramId") long tvProgramId) throws DBException {
		tvProgramDao.removeTvProgram(tvProgramId);
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_XML)
	public void updateTvProgram( TvProgram program)
			throws DBException {
		tvProgramDao.updateTvProgram( program);
	}
	
	
	@GET
	@Path("/descr/{tvProgramId}")
	@Produces(MediaType.APPLICATION_XML)
	public String getTvProgramDescription(@PathParam("tvProgramId") long tvProgramId) throws DBException {
		
		return tvProgramDao.getTvProgramDescription(tvProgramId);
	}




}
