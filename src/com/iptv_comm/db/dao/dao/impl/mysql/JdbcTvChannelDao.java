package com.iptv_comm.db.dao.dao.impl.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvChannel;
import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvProgram;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;
import com.iptv_comm.db.dao.dao.TvChannelDao;
import com.iptv_comm.db.dao.factory.DaoFactory;


public class JdbcTvChannelDao extends JdbcDao implements TvChannelDao{

	public static final String tableName="tv_channel";
	public static final String insertQuery="insert into "+tableName+" (provider_id, channel_number, channel_name, channel_descr,channel_url, channel_logo) "+
            "values(?, ?, ?, ?, ?, ?);";
	public static final String deleteQuery="delete from "+tableName+" where  "+
            "tv_channel_id = ?";
	
	public static final String updateQuery = "update  "+ tableName+"  set provider_id =?,channel_number=?,channel_name=?,channel_descr=? , channel_url=?," +
			   "channel_logo=? where tv_channel_id=?";
	public static final String searchQuery = "select * from " + tableName + " where " +
    		"tv_channel_id = ?";
	
	public static final String tvprogramsearchquery="select * from tv_program,tv_channel,general_name  " +
			"where tv_channel.channel_name= general_name.name_id and " +
			"general_name.name_id=tv_program.tv_program_name and tv_channel.tv_channel_id=?";
	
	public static final String descriptionQuery="select localized_description.description from localized_description,general_description,tv_channel" +
			" where localized_description.description_id= general_description.description_id and " +
			"general_description.description_id= tv_channel.channel_descr and tv_channel.tv_channel_id=?";
	
	public static final String languageQuery="select language.language_name from language, localized_name , general_name , tv_channel " +
			"where language.language_id= localized_name.language_id and localized_name.name_id= general_name.name_id " +
			"and general_name.name_id= tv_channel.channel_name and tv_channel.tv_channel_id=?";
	
	@Override	
	public int addTvChannel(TvChannel channel) throws DBException {
		int tvChannelId=0;
		 if(channel!=null && channel.getChannelUrl()!=null && channel.getChannelLogo()!=null){
	            Connection con = null;
	            PreparedStatement pstmt = null;
	            try {
	                con=getConnection();
	                pstmt=con.prepareStatement(insertQuery,1);
	                pstmt.setInt(1, channel.getProviderId());
					pstmt.setInt(2, channel.getChannelNumber());
					pstmt.setLong(3, channel.getChannelName());
					pstmt.setLong(4, channel.getChannelDescr());
					pstmt.setString(5, channel.getChannelUrl());
					pstmt.setString(6, channel.getChannelLogo());
	                if(pstmt.executeUpdate()==1) {

	                    ResultSet rs = pstmt.getGeneratedKeys();
	                    rs.next();
	                    tvChannelId=rs.getInt(1);
	                }
	                }
	                catch(Exception e){
	                	throw new DBException(e);
	                }
	            finally {
					closeStatement(pstmt);
					closeConnection(con);
				}
	            
		
		 }
		 return tvChannelId;
	}

	
	@Override
	public void removeTvChannel(int tvChannelId) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(deleteQuery, 1);
			pstmt.setInt(1, tvChannelId);
			pstmt.executeUpdate();
		}
		catch (Exception e){
			throw new DBException (e);
		}
		finally {
			closeStatement(pstmt);
			closeConnection(con);
		}
		
	}

	
	@Override
	public void updateTvChannel( TvChannel channel)throws DBException {
		
		Connection con=null; 
		PreparedStatement pstmt = null;
		ResultSet rset;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(searchQuery, 1);
			pstmt.setInt(1, channel.getTvChannelId());
			rset=pstmt.executeQuery();
			rset.next();
			//pstmt.close();
			pstmt=con.prepareStatement(updateQuery,1);
			if(channel.getProviderId()!=0){
				pstmt.setInt(1,channel.getProviderId());
			}
			else{
				pstmt.setInt(1,rset.getInt(2));
			}
			if(channel.getChannelNumber()!=0){
				pstmt.setInt(2,channel.getChannelNumber());
			}
			else{
				pstmt.setInt(2,rset.getInt(3));
			}
			if(channel.getChannelName()!=0){
				pstmt.setLong(3,channel.getChannelName());
			}
			else{
				pstmt.setLong(3,rset.getLong(4));
			}
			if(channel.getChannelDescr()!=0){
				pstmt.setLong(4,channel.getChannelDescr());
			}
			else{
				pstmt.setLong(4,rset.getLong(5));
			}
			if(channel.getChannelUrl()!=null){
				pstmt.setString(5,channel.getChannelUrl());
			}
			else{
				pstmt.setString(5,rset.getString(6));
			}
			if(channel.getChannelLogo()!=null){
				pstmt.setString(6,channel.getChannelLogo());
			}
			else{
				pstmt.setString(6,rset.getString(7));
			}
			pstmt.setInt(7, channel.getTvChannelId());
			pstmt.executeUpdate();
		}
		catch (Exception e){
			throw new DBException (e);
		}
		finally {
			closeStatement(pstmt);
			closeConnection(con);
		}
	}
	
	@Override
	public String getLanguage(int tvchannelid) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset;
		String lang;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(languageQuery, 1);
			pstmt.setInt(1, tvchannelid);
			rset = pstmt.executeQuery();
			rset.next();
			lang=rset.getString(1);
			
		}
		catch (Exception e){
			throw new DBException (e);
		}
		finally {
			closeStatement(pstmt);
			closeConnection(con);
		}
		return lang;
	}
	
	@Override
	public String getDescription(int tvchannelid) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset;
		String descr;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(descriptionQuery, 1);
			pstmt.setInt(1, tvchannelid);
			rset = pstmt.executeQuery();
			rset.next();
			descr=rset.getString(1);
			
		}
		catch (Exception e){
			throw new DBException (e);
		}
		finally {
			closeStatement(pstmt);
			closeConnection(con);
		}
		return descr;
	}
	
	@Override
	public ArrayList<TvProgram> getTvProgramList(int tvchannelid)
			throws DBException {
		ArrayList <TvProgram> programlist=new ArrayList<TvProgram>();
		TvProgram program;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(tvprogramsearchquery, 1);
			pstmt.setInt(1, tvchannelid);
			rset=pstmt.executeQuery();
			while(rset.next()){
				program=new TvProgram();
				program.setTvProgramId(rset.getLong(1));
				program.setTvChannelId(rset.getInt(2));
				program.setTvProgramName(rset.getLong(3));
				program.setTvProgramDescr(rset.getLong(4));
				program.setStartDt(rset.getDate(5));
				program.setDurationInMinutes(rset.getInt(6));
				programlist.add(program);
			}
			
		}
		catch (Exception e){
			throw new DBException (e);
		}
		finally {
			closeStatement(pstmt);
			closeConnection(con);
		}
		
	
		
		return programlist;
	}
	
	public static void main(String args[]){
		 DaoFactory fact=DaoFactory.getDaoFactory(DaoFactory.DBType.MySQL);
	        TvChannelDao tvChannelDao=fact.getTvChannelDao();
	        TvChannel channel =new TvChannel();
	        channel.setChannelName(2);
	        channel.setTvChannelId(3);
	       // channel.setChannelDescr(2);
	       // channel.setChannelLogo("Logik");
	       // channel.setChannelUrl("urlik");
	        try{
	        	tvChannelDao.removeTvChannel(12);
	        	
	        	//tvChannelDao.updateTvChannel( channel);
	        }
	        catch(DBException e){
	        	e.printStackTrace();
	        }
	}


	


	


	

}
