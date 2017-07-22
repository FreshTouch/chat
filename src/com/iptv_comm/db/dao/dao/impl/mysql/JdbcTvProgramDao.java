package com.iptv_comm.db.dao.dao.impl.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvProgram;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;
import com.iptv_comm.db.dao.dao.TvProgramDao;
import com.iptv_comm.db.dao.factory.DaoFactory;


public class JdbcTvProgramDao extends JdbcDao implements TvProgramDao {

	 public static final String tableName="tv_program";
		
	
	@Override	
	public long addTvProgram(TvProgram program) throws DBException {
		long tvprogramid = 0;
		if(program != null  ) {
			Connection con = null;
			PreparedStatement ps = null;
			
			String insertQuery="insert into "+tableName+" (tv_channel_id,tv_program_name, " +
		    		"tv_program_descr,start_dt, duration_in_minutes) "+
		            "values(?, ?, ?, ?, ?)";
			try {
				con = getConnection();
				ps = con.prepareStatement(insertQuery,1);
				ps.setInt(1,program.getTvChannelId());
				ps.setLong(2, program.getTvProgramName());
				ps.setLong(3, program.getTvProgramDescr());
				ps.setDate(4,program.getStartDt());
				ps.setInt(5,program.getDurationInMinutes());
				
				if(ps.executeUpdate() == 1) {
					ResultSet rs = ps.getGeneratedKeys();
					rs.next(); 
					tvprogramid = rs.getInt(1);
					//System.out.println("TvProgram added, new id = " + tvprogramid);
					
				}
			}
			catch (Exception e){
				throw new DBException (e);
			}
			finally {
				closeStatement(ps);
				closeConnection(con);
			}
		}
		
		return tvprogramid;
	}
	
	@Override
	public void removeTvProgram(long tvProgramId) throws DBException {
		Connection con = null;
		PreparedStatement ps = null;
		String deleteQuery="delete from "+tableName+" where  "+
	            " tv_program_id = ?";
		try {
			con = getConnection();
			ps = con.prepareStatement(deleteQuery, 1);
			ps.setLong(1, tvProgramId);
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
	
	@Override
	public void updateTvProgram( TvProgram program) throws DBException {
		Connection con=null; 
		PreparedStatement pstmt = null;
		ResultSet rset;
		
		String searchQuery = "select * from " + tableName + " where " +
	    		" tv_program_id = ?";
		
		String updateQuery = "update  "+ tableName+"  set tv_channel_id=?, tv_program_name=?, " +
				   " tv_program_descr=?, start_dt=?, duration_in_minutes=? where tv_program_id=?";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(searchQuery, 1);
			pstmt.setLong(1, program.getTvProgramId());
			rset=pstmt.executeQuery();
			rset.next();
			
			pstmt=con.prepareStatement(updateQuery,1);
			if(program.getTvChannelId()!=0){
				pstmt.setInt(1,program.getTvChannelId());
			}
			else{
				pstmt.setInt(1,rset.getInt(2));
			}
			if(program.getTvProgramName()!=0){
				pstmt.setLong(2,program.getTvProgramName());
			}
			else{
				pstmt.setLong(2,rset.getLong(3));
			}
			if(program.getTvProgramDescr()!=0){
				pstmt.setLong(3,program.getTvProgramDescr());
			}
			else{
				pstmt.setLong(3,rset.getLong(4));
			}
			if(program.getStartDt()!=null){
				pstmt.setDate(4,program.getStartDt());
			}
			else{
				pstmt.setDate(4,rset.getDate(5));
			}
			if(program.getDurationInMinutes()!=0){
				pstmt.setInt(5,program.getDurationInMinutes());
			}
			else{
				pstmt.setInt(5,rset.getInt(6));
			}
			pstmt.setLong(6, program.getTvProgramId());
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
	public String getTvProgramDescription(long tvProgramId) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset=null;
		String descr="";
		
		String programdescrquery="select localized_description.description from tv_program, general_description, localized_description " +
				" where tv_program.tv_program_descr= general_description.description_id and general_description.description_id= localized_description.description_id" +
				" and tv_program.tv_program_id=?";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(programdescrquery, 1);
			pstmt.setLong(1, tvProgramId);
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

	
	
	public static void main(String [] args){
		 DaoFactory fact=DaoFactory.getDaoFactory(DaoFactory.DBType.MySQL);
	        TvProgramDao tvProgramDao=fact.getTvProgramDao();
	        TvProgram program=new TvProgram();
	        program.setTvChannelId(1);
	        program.setTvProgramName(1);
	        program.setTvProgramDescr(1);
	        Date d=new Date(System.currentTimeMillis());
	        program.setStartDt(d);
	        program.setDurationInMinutes(48);
	        try {
				tvProgramDao.addTvProgram(program);
			} catch (DBException e) {
				e.printStackTrace();
			}
	}
	
}
