package com.iptv_comm.services.impl;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvChannel;
import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvProgram;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;
import com.iptv_comm.db.dao.dao.impl.mysql.JdbcTvChannelDao;
import com.iptv_comm.services.model.TvChannelService;

@Path("/TvChannel")
public class TvChannelServiceImpl implements TvChannelService{

	JdbcTvChannelDao tvChannelDao;
	
	 public TvChannelServiceImpl() {
	 tvChannelDao=new JdbcTvChannelDao();
	}

	@POST
	@Path("/channel")
	@Consumes({ MediaType.APPLICATION_XML })
	@Override
	public void addTvChannel(TvChannel channel) throws DBException {		
		tvChannelDao.addTvChannel(channel);
		
	}
	@DELETE
	@Path("/remove/{tvchannelid}")
	@Override
	public void removeTvChannel(@PathParam("tvchannelid") int tvChannelId) throws DBException {
		tvChannelDao.removeTvChannel(tvChannelId);
	}
	
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_XML)
	@Override
	public void updateTvChannel( TvChannel channel) throws DBException {
		tvChannelDao.updateTvChannel(channel);
	}
	
	@GET
	@Path("/list/{tvchannelid}")
	@Produces({ MediaType.APPLICATION_XML })
	@Override
	public ArrayList<TvProgram> getTvProgramList(@PathParam("tvchannelid") int tvchannelid)
			throws DBException {
		return tvChannelDao.getTvProgramList(tvchannelid); 
	}
	
	@GET
	@Path("/descr/{tvchannelid}")
	@Produces({ MediaType.APPLICATION_XML })
	@Override
	public String getDescription(@PathParam("tvchannelid") int tvchannelid) throws DBException {
		return tvChannelDao.getDescription(tvchannelid);
	}
	
	@GET
	@Path("/language/{tvchannelid}")
	@Produces({ MediaType.APPLICATION_XML })
	@Override
	public String getLanguage(@PathParam("tvchannelid") int tvchannelid) throws DBException {
		return tvChannelDao.getLanguage(tvchannelid);
	}

}
