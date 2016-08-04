package com.iptv_comm.services.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvProvider;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;
import com.iptv_comm.db.dao.dao.impl.mysql.JdbcTvProviderDao;
import com.iptv_comm.services.model.TvProviderService;

/**
 * TV Provider service implementation
 */

@Path("/TvProvider")
public class TvProviderServiceImpl implements TvProviderService{
    private JdbcTvProviderDao tvProviderDao;
    
    public TvProviderServiceImpl() {
		tvProviderDao=new JdbcTvProviderDao();
	}

    @POST
    @Path("/tvprovider/{name}")
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void addTvProvider(@PathParam("name") String companyName) throws DBException{
        TvProvider tvProvider=new TvProvider();
        tvProvider.setCompanyName(companyName);
        tvProviderDao.addTvProvider(tvProvider);
    }
    
    @DELETE
    @Path("/remove/{name}")
    @Override
    public void removeTvProvider(@PathParam("name") String companyName) throws DBException{
    	long tvProviderId = tvProviderDao.getTvProviderId(companyName);
    	tvProviderDao.deleteTvProvider(tvProviderId);
    }
}
