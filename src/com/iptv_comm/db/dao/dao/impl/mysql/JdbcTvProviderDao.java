package com.iptv_comm.db.dao.dao.impl.mysql;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvProvider;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;
import com.iptv_comm.db.dao.dao.TvProviderDao;
import com.iptv_comm.db.dao.factory.DaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by user on 7/5/16.
 */
public class JdbcTvProviderDao extends JdbcDao implements TvProviderDao {
    
	public static final String tableName="tv_provider";
 
    @Override
    public int addTvProvider(TvProvider tvProvider) throws DBException {
        int tvProviderId=0;
        if(tvProvider!=null && tvProvider.getCompanyName()!=null){
            Connection con = null;
            PreparedStatement pstmt = null;
            String insertQuery="insert into "+tableName+" (company_name) "+
                    "values(?)";
            try {
                con=getConnection();
                pstmt=con.prepareStatement(insertQuery,1);
                pstmt.setString(1,tvProvider.getCompanyName());
                if(pstmt.executeUpdate()==1) {

                    ResultSet rs = pstmt.getGeneratedKeys();
                    rs.next();
                    tvProviderId=rs.getInt(1);
                }
            }
            catch(Exception e){
                throw new DBException(e);
            }
            finally{
                closeStatement(pstmt);
                closeConnection(con);
            }
        }
        return tvProviderId;
    }
    
    @Override
    public long getTvProviderId (String tvProviderName) throws DBException {
    	long tvProviderId=0;
    	Connection con = null;
    	PreparedStatement ps = null;
    	ResultSet rs=null;
    	String searchQuery = "select * from " + tableName + " where " +
        		"company_name = ?";
    	try {
    		con = getConnection();
    		ps = con.prepareStatement(searchQuery, 1);
    		ps.setString(1, tvProviderName);
    		    rs=ps.executeQuery();
                rs.next();
                tvProviderId=rs.getLong(1);
            
    	}
    	catch(Exception e) {
    		throw new DBException(e);
    	}
    	finally {
    		closeStatement(ps);
    		closeConnection(con);
    	}
       	return tvProviderId;
    }

    @Override
    public void deleteTvProvider(long tvProviderId) throws DBException {
    	Connection con = null;
    	PreparedStatement ps = null;
    	String deleteQuery="delete from "+tableName+" where  "+
                "tv_provider_id=?";
    	try {
    		con = getConnection();
    		ps = con.prepareStatement(deleteQuery, 1);
    		ps.setLong(1, tvProviderId);
    		ps.executeUpdate();
    	}
    	catch (Exception e){
    		throw new DBException (e);
    	}
    	finally {
    		closeStatement(ps);
    		closeConnection(con);
    	}
    }


    public static void main(String[] args){
        DaoFactory fact=DaoFactory.getDaoFactory(DaoFactory.DBType.MySQL);
        TvProviderDao tvProviderDao=fact.getTvProviderDao();
        TvProvider tvProvider=new TvProvider();
        tvProvider.setCompanyName("Rostelecom");
        try {
        	tvProviderDao.addTvProvider(tvProvider);
           // System.out.println(tvProviderDao.getTvProviderId("UCom"));
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
