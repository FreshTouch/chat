package com.iptv_comm.db.dao.dao.impl.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvChannel;
import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.User;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;
import com.iptv_comm.db.dao.dao.UserDao;
import com.iptv_comm.db.dao.factory.DaoFactory;

/**
 * Created by user on 7/5/16.
 */
public class JdbcUserDao extends JdbcDao implements UserDao{
    public static final String tableName="user";
    
    @Override
	public long addUser(User user) throws DBException {
		long userId = 0;
		if(user != null && user.getUsername() != null && user.getPassword()!=null) {
			Connection con = null;
			PreparedStatement ps = null;
			String insertQuery="insert into "+tableName+" (username, password, email, first_name, last_name, dob, avatar) "+
		            "values(?, ?, ?, ?, ?, ?, ?);";
			try {
				con = getConnection();
				ps = con.prepareStatement(insertQuery,1);
				ps.setString(1, user.getUsername());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.geteMail());
				ps.setString(4, user.getFirstName());
				ps.setString(5, user.getLastName());
				ps.setDate(6, (Date) user.getDob());
				ps.setString(7, user.getAvatar());
				
				if(ps.executeUpdate() == 1) {
					ResultSet rs = ps.getGeneratedKeys();
					rs.next(); 
					userId = rs.getInt(1);
					System.out.println("User added, new id = " + userId);
					
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
		
		return userId;
	}
	
    @Override
	public ArrayList<User> userList() throws DBException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rset;
		ArrayList<User> userlist=new ArrayList<>();
		User user;
		try {
			con=getConnection();
			ps=con.prepareStatement("Select * from user");
			rset=ps.executeQuery();
			while(rset.next()){
				user=new User();
				user.setUserId(rset.getLong(1));
				user.setUsername(rset.getString(2));
				user.setPassword(rset.getString(3));
				user.seteMail(rset.getString(4));
				user.setFirstName(rset.getString(5));
				user.setLastName(rset.getString(6));
				user.setDob(rset.getDate(7));
				user.setAvatar(rset.getString(8));
				userlist.add(user);
				
			}
		}
			catch (Exception e){
				throw new DBException (e);
			}
			finally {
				closeStatement(ps);
				closeConnection(con);
			}
		return userlist;
	}
    
	public long getUserId (String username) throws DBException {
		long userId = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		String searchQuery = "select * from " + tableName + " where " +
	    		"username= ?";
		try {
			con = getConnection();
			ps = con.prepareStatement(searchQuery, 1);
			ps.setString(1, username);
				rs =ps.executeQuery();
				if(rs.next()){
					userId = rs.getLong(1);				
			}
		}
		catch (Exception e){
			throw new DBException (e);
		}
		finally {
			closeStatement(ps);
			closeConnection(con);
		}
		return userId;
		
	}
	
	
	
	
	@Override
	public User login(String userName,String password) throws DBException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		User user = null;
		String usname="";
		String search="select * from "+ tableName+" where username=? and password=?";
		String updateToken = "update  "+ tableName+"  set token =? where username=?";
		char  xar []={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
				'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
			
				
		String token=userName;
		while(token.length()<50){
			int r=(int)(Math.random()*xar.length);
			token+=xar[r];
		}
		try{
			con=getConnection();
			ps = con.prepareStatement(search,2);		
			ps.setString(1, userName);
			ps.setString(2, password);
			
				rs = ps.executeQuery();
				if(rs.next()){
					usname=rs.getString(2);
				}
					
			if(!usname.equals(userName)){
				throw new DBException();
			}
			else{
				
				Date date = new Date(System.currentTimeMillis());
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String data=dateFormat.format(date);
				final String updatelastactive = "update  "+ tableName+"  set last_active ='"+data+"' where username=?";
				
				ps.close();
				ps=con.prepareStatement(updatelastactive,1);
				
				
				ps.setString(1, userName);
				ps.executeUpdate();
				ps=con.prepareStatement(updateToken,2);
				ps.setString(1, token);
				ps.setString(2, userName);
				ps.executeUpdate();
				user = getInfo(token);
					
			}
		}
		catch(Exception e){
			throw new DBException(e);
		}
		finally {
			closeStatement(ps);
			closeConnection(con);
		}
		return user;
	} 
	
	public String getTokenById(long userId) throws DBException{
		String token="";
		 String selectToken = "select token from "+ tableName+"  where user_id=?";
		 Connection con=null;
		 PreparedStatement pstmt=null;
		 ResultSet rset=null;
		 try{
				    con=getConnection();
					pstmt=con.prepareStatement(selectToken,1);
					pstmt.setLong(1, userId);
					rset=pstmt.executeQuery();
					rset.next();
					token=rset.getString(1);
				
			}
			catch(Exception e){
				throw new DBException(e);
			}
			finally {
				closeStatement(pstmt);
				closeConnection(con);
			}
		return token;
	}
	
	
	@Override
	public void logout(String userName) throws DBException {
		Connection con = null;
		PreparedStatement ps = null;
		String  updateTokenToNull="update  "+ tableName+"  set token = null where username=?";
		String updatelastactive = "update  "+ tableName+"  set last_active =? where username=?";
		try{
			con=getConnection();
	
				ps=con.prepareStatement(updatelastactive,2);
				
				ps.setDate(1, null);
				ps.setString(2, userName);
				ps.executeUpdate();
				//provide access to account 
				ps=con.prepareStatement(updateTokenToNull,1);
				ps.setString(1,userName );
				ps.executeUpdate();
			
		}
		catch(Exception e){
			throw new DBException(e);
		}
		finally {
			closeStatement(ps);
			closeConnection(con);
		}
		
	}
	
	
	@Override
	public void removeUser(long userId) throws DBException {
		Connection con = null;
		PreparedStatement ps = null;
		String userassocdel="delete from user_assoc where object_user_id=?";
		String userassocdel2="delete from user_assoc where subject_user_id=?";
		String usertvproviderdel="delete from user_tv_provider where user_id=?";
		String usermessagedel="delete from message where sender=?";
		String usermessagedel2="delete from message where receiver=?";
		String deleteQuery="delete from "+tableName+" where  "+
	            "user_id = ?";
		try {
			con = getConnection();
			ps=con.prepareStatement(userassocdel,1);
			ps.setLong(1,userId);
			ps.executeUpdate();
			ps.close();
			
			ps=con.prepareStatement(userassocdel2,1);
			ps.setLong(1,userId);
			ps.executeUpdate();
			ps.close();
			
			ps=con.prepareStatement(usertvproviderdel,1);
			ps.setLong(1,userId);
			ps.executeUpdate();
			ps.close();
			
			ps=con.prepareStatement(usermessagedel,1);
			ps.setLong(1,userId);
			ps.executeUpdate();
			ps.close();
			
			ps=con.prepareStatement(usermessagedel2,1);
			ps.setLong(1,userId);
			ps.executeUpdate();
			ps.close();
			
			
			ps = con.prepareStatement(deleteQuery, 1);
			ps.setLong(1, userId);
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
	public void updateUser( User user,String token) throws DBException {
		Connection con=null; 
		PreparedStatement pstmt = null;
		ResultSet rset;
		String updateQuery = "update  "+ tableName+"  set username =? , password=? , email=? , first_name=? , last_name=?," +
				   "dob=?, avatar=? where user_id=?";
		String searchQuery1 = "select * from user where " +
		    		"user_id= ?";
	if(token.equals(getTokenById(user.getUserId()))){
		try {
			con = getConnection();
			pstmt = con.prepareStatement(searchQuery1, 1);
			pstmt.setLong(1, user.getUserId());
			rset=pstmt.executeQuery();
			//pstmt.close();
			rset.next();
			pstmt=con.prepareStatement(updateQuery,1);	
			if(user.getUsername().length()>4){
				pstmt.setString(1,user.getUsername());
				
			}
			else{
				pstmt.setString(1,rset.getString(2));
			}

			
			if(user.getPassword().length()>4){
				pstmt.setString(2,user.getPassword());
			}
			else{
				pstmt.setString(2,rset.getString(3));
			}
			if(user.geteMail().length()>6){
				pstmt.setString(3,user.geteMail());
			}
			else{
				pstmt.setString(3,rset.getString(4));
			}
			if(user.getFirstName().length()>2){
				pstmt.setString(4,user.getFirstName());
			}
			else{
				pstmt.setString(4,rset.getString(5));
			}
			if(user.getLastName().length()>4){
				pstmt.setString(5,user.getLastName());
			}
			else{
				pstmt.setString(5,rset.getString(6));
			}
		if(user.getDob()!=null){
				pstmt.setDate(6,(Date) user.getDob());
			}
			else{
				pstmt.setDate(6,rset.getDate(7));
			}  
			
			
			
			if(user.getAvatar().length()>9){
				pstmt.setString(7,user.getAvatar());
			}
			else{
				pstmt.setString(7,rset.getString(8));
			}
			
			
			pstmt.setLong(8, user.getUserId());
			
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
	else{
		throw new DBException ("token is not valid");
	}
	}
	
	
	@Override
	public ArrayList<TvChannel> getTvChannelList(String userName,String token)
			throws DBException {
		ArrayList<TvChannel> channellist=new ArrayList<TvChannel>();
		Connection con=null; 
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		TvChannel channel = null;
		 String tvchannelsearchquery="select * from tv_channel,tv_provider,user_tv_provider,user where user.user_id=user_tv_provider.user_id " +
					"and user_tv_provider.tv_provider_id = tv_provider.tv_provider_id and " +
					"tv_provider.tv_provider_id = tv_channel.provider_id and user.username=?";
		if(token.equals(getTokenById(getUserId(userName)))){
		try {
			con = getConnection();
			pstmt=con.prepareStatement(tvchannelsearchquery,1);
			pstmt.setString(1,userName);
			rset=pstmt.executeQuery();
			while(rset.next()){
				channel=new TvChannel();
				channel.setTvChannelId(rset.getInt(1));
				channel.setProviderId(rset.getInt(2));
				channel.setChannelNumber(rset.getInt(3));
				channel.setChannelName(rset.getLong(4));
				channel.setChannelDescr(rset.getLong(5));
				channel.setChannelUrl(rset.getString(6));
				channel.setChannelLogo(rset.getString(7));
				channellist.add(channel);
			}
			
		
		}
		catch (Exception e){
			throw new DBException (e);
		}
		finally {
			closeStatement(pstmt);
			closeConnection(con);
		}
		
		}
		else{
			throw new DBException ("token is not valid");
		}
		
		return channellist;
	}
	
	
	@Override
	public void sendFriendRequest(long thisId,long receiverId,String token) throws DBException {
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset = null;
		String friendRequestQuery="insert into user_assoc value(null,?,?,1)";
		String isExistQuery="select * from user_assoc where object_user_id=? and subject_user_id=?";
		String isDublicateQuery="select * from user_assoc where object_user_id=? and subject_user_id=? and (assoc_type_id=1 or assoc_type_id=2)";
		if(token.equals(getTokenById(thisId))){
		try {
			con=getConnection();
			pstmt=con.prepareStatement(isExistQuery,2);
			pstmt.setLong(1,thisId);
			pstmt.setLong(2, receiverId);
			rset=pstmt.executeQuery();
			int count=0;
			int countDublicate=0;
			while(rset.next()){
				count++;
			}
			pstmt=con.prepareStatement(isDublicateQuery,2);
			pstmt.setLong(1,receiverId);
			pstmt.setLong(2, thisId);
			rset=pstmt.executeQuery();
			while(rset.next()){
				countDublicate++;
			}
			
			if(count==0 && countDublicate==0){
				pstmt=con.prepareStatement(friendRequestQuery,2);
				pstmt.setLong(1,thisId);
				pstmt.setLong(2, receiverId);
				pstmt.executeUpdate();
			}
			else{
				//
				//
				System.out.println("chi kareli");
			}
			
			
		} catch(Exception e){
			throw new DBException(e);
		}
		finally {
			closeStatement(pstmt);
			closeConnection(con);
		}
		
		}
		else{
			throw new DBException ("token is not valid");
		}

		
	}
	
	
	    @Override
		public void acceptFriendRequest(long accId, long senderId,String token)
			throws DBException {
			Connection con=null;
			PreparedStatement pstmt=null;
			String requestResponseQuery="update user_assoc set assoc_type_id=? where object_user_id=? and subject_user_id=?";
			
			if(token.equals(getTokenById(accId))){
			try{
				con=getConnection();
				pstmt=con.prepareStatement(requestResponseQuery,2);
				pstmt.setInt(1, 2);
				pstmt.setLong(2,senderId);
				pstmt.setLong(3, accId);
				pstmt.executeUpdate();
			}
			catch(Exception e){
				throw new DBException(e);
			}
			finally {
				closeStatement(pstmt);
				closeConnection(con);
			}
			}
			else{
				throw new DBException ("token is not valid");
			}
	}
	@Override
	public void declineFriendRequest(long accId, long senderId,boolean isAnnoying,String token)
			throws DBException {
		Connection con=null;
		PreparedStatement pstmt=null;
		String requestResponseQuery="update user_assoc set assoc_type_id=? where object_user_id=? and subject_user_id=?";
		String  deleteAssocQuery="delete from user_assoc where object_user_id=? and subject_user_id=?";
		if(token.equals(getTokenById(accId))){
		try{
			con=getConnection();
			
			if(isAnnoying){
				pstmt=con.prepareStatement(requestResponseQuery,3);
				pstmt.setInt(1, 3);	
				pstmt.setLong(2,senderId);
				pstmt.setLong(3, accId);
				pstmt.executeUpdate();
			}
			else{
				pstmt=con.prepareStatement(deleteAssocQuery,2);
				pstmt.setLong(1, senderId);
				pstmt.setLong(2, accId);
				pstmt.executeUpdate();
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
		else{
			throw new DBException ("token is not valid");
		}
	}
	
	@Override
	public ArrayList<User> getfriendList(long userId,String token) throws DBException {
		ArrayList<User> friendlist=new ArrayList<User>();
		Connection con=null; 
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rset = null;
		ResultSet rset2 = null;
		User user = null;
		String getFriend1="select object_user_id from user_assoc where subject_user_id=? and assoc_type_id=2";
		String getFriend2="select subject_user_id from user_assoc where object_user_id=? and assoc_type_id=2";
		String searchfriendQuery = "select * from " + tableName + " where " +
	    		"user_id = ?";
		if(token.equals(getTokenById(userId))){
		try {
			con = getConnection();
			pstmt=con.prepareStatement(getFriend1,1);
			pstmt.setLong(1,userId);
			rset=pstmt.executeQuery();
			while(rset.next()){
				pstmt2=con.prepareStatement(searchfriendQuery,1);
				pstmt2.setLong(1, rset.getLong(1));
				user=new User();
				rset2=pstmt2.executeQuery();
				rset2.next();
				user.setUserId(rset2.getLong(1));
				user.setUsername(rset2.getString(2));
				user.setFirstName(rset2.getString(5));
				user.setLastName(rset2.getString(6));
				user.setDob(rset2.getDate(7));
				user.setAvatar(rset2.getString(8));
				friendlist.add(user);
			}
			pstmt=con.prepareStatement(getFriend2,1);
			pstmt.setLong(1,userId);
			rset=pstmt.executeQuery();
			while(rset.next()){
				pstmt2=con.prepareStatement(searchfriendQuery,1);
				pstmt2.setLong(1, rset.getLong(1));
				user=new User();
				rset2=pstmt2.executeQuery();
				rset2.next();
				user.setUserId(rset2.getLong(1));
				user.setFirstName(rset2.getString(5));
				user.setLastName(rset2.getString(6));
				user.setAvatar(rset2.getString(8));
				friendlist.add(user);
			}
			
		
		}
		catch (Exception e){
			throw new DBException (e);
		}
		finally {
			closeStatement(pstmt);
			closeConnection(con);
		}
		
		}
		else{
			throw new DBException ("token is not valid");
		}
		
		return friendlist;
	}
	
	
	
	
	public User getInfo(String token) throws DBException {
		String sql="Select * from user where token = ? ";
	    Connection con=null;
	    PreparedStatement prst=null;
	    ResultSet rset=null;
	    User user=null;
	    try {
			con = getConnection();
			prst = con.prepareStatement(sql, 1);
			prst.setString(1,token);
				rset =prst.executeQuery();
				if(rset.next()){
				user=new User();
				user.setUserId(rset.getLong(1));
				user.setUsername(rset.getString(2));
				user.seteMail(rset.getString(4));
				user.setFirstName(rset.getString(5));
				user.setLastName(rset.getString(6));
				user.setDob(rset.getDate(7));
				user.setAvatar(rset.getString(8));
				user.setToken(rset.getString(9));
			}
		}
		catch (Exception e){
			throw new DBException (e);
		}
		finally {
			closeStatement(prst);
			closeConnection(con);
		}
	    
	    
		return user;
	}
	
	
	@Override
	public ResultSet getTvLogoAndName(String userName, String token) throws Exception {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null; 
		String channellogoname="select tv_channel.channel_logo,general_name.internal_name from general_name,tv_channel,tv_provider,user_tv_provider,user"
				+ " where user.user_id=user_tv_provider.user_id and user_tv_provider.tv_provider_id = tv_provider.tv_provider_id"
				+ " and general_name.name_id = tv_channel.channel_name and tv_provider.tv_provider_id = tv_channel.provider_id and user.username=?";
		if(token.equals(getTokenById(getUserId(userName)))){
			
				con = getConnection();
				pstmt=con.prepareStatement(channellogoname,1);
				pstmt.setString(1,userName);
				rset=pstmt.executeQuery();
			
			
			}
			else{
				throw new DBException ("token is not valid");
			}
		return rset;
	}
	
	
	@Override
	public ArrayList <String> getTvLogo(String userName, String token) throws Exception {
		ResultSet rset=null; 
		ArrayList <String> list = new ArrayList<>();
		rset = getTvLogoAndName(userName, token);
		while(rset.next()){
			list.add(rset.getString(1));
		}
		
		return list;
	}

	
	@Override
	public ArrayList <String> getTvChanelName(String userName, String token) throws Exception {
		ResultSet rset=null; 
		ArrayList <String> list = new ArrayList<>();
		rset = getTvLogoAndName(userName, token);
		while(rset.next()){
			list.add(rset.getString(2));
		}
		
		return list;
	}
	
	
	
	@Override
	public void deletFriendById(long userId, long friendId, String token) throws DBException {
		Connection con=null;
		PreparedStatement pstmt=null;
		String deleteQuery = " delete  from user_assoc where assoc_type_id = 2 and object_user_id =? and subject_user_id =? ";
		if(token.equals(getTokenById(userId))){
		try {
			 con = getConnection();
				pstmt = con.prepareStatement(deleteQuery, 2);
				pstmt.setLong(1, friendId);
				pstmt.setLong(2, userId);
				pstmt.executeUpdate();
				
				pstmt = con.prepareStatement(deleteQuery, 2);
				pstmt.setLong(1, userId);
				pstmt.setLong(2, friendId);
				pstmt.executeUpdate();
						
			}
			catch (Exception e){
				throw new DBException (e);
			}
			finally {
				closeStatement(pstmt);
				closeConnection(con);
			}
		} else{
			throw new DBException("Token is not valid");
		}
		    
		
		
	}

	
	@Override
	public User searchUsers(String username) throws DBException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rset;
		User user;
		try {
			con=getConnection();
			ps=con.prepareStatement("Select * from user where username like ?");
			ps.setString(1,username);
			rset=ps.executeQuery();
			rset.next();
				user=new User();
				user.setUserId(rset.getLong(1));
				user.setUsername(rset.getString(2));
				user.seteMail(rset.getString(4));
				user.setFirstName(rset.getString(5));
				user.setLastName(rset.getString(6));
				user.setDob(rset.getDate(7));
				user.setAvatar(rset.getString(8));
				
		}
			catch (Exception e){
				throw new DBException (e);
			}
			finally {
				closeStatement(ps);
				closeConnection(con);
			}
		return user;
	}
	
	@Override
	public ArrayList<User> getfriendRecuestList(long userId) throws DBException {
		ArrayList<User> friendlist=new ArrayList<User>();
		Connection con=null; 
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		User user = null;
		String getFriend="select * from user, user_assoc  where user_assoc.assoc_type_id  = 1 and "
				+ "user_assoc.subject_user_id = ? and user.user_id = user_assoc.object_user_id";
		
		
		try {
			con = getConnection();
			pstmt=con.prepareStatement(getFriend,1);
			pstmt.setLong(1,userId);
			rset=pstmt.executeQuery();
			while(rset.next()){
				user=new User();
				user.setUserId(rset.getLong(1));
				user.setUsername(rset.getString(2));
				user.setFirstName(rset.getString(5));
				user.setLastName(rset.getString(6));
				user.setDob(rset.getDate(7));
				user.setAvatar(rset.getString(8));
				friendlist.add(user);
			}
		}
		catch (Exception e){
			throw new DBException (e);
		}
		finally {
			closeStatement(pstmt);
			closeConnection(con);
		}
	
		return friendlist;
	}
	
	
	public static void main(String[] args){
        DaoFactory fact=DaoFactory.getDaoFactory(DaoFactory.DBType.MySQL);
        UserDao userDao=fact.getUserDao();
        User user=new User();
        user.setLastName("Serob");
        //user.setPassword("2112");

        Date d=new Date(System.currentTimeMillis());
        user.setDob(d);
        user.setUserId(28);
        ResultSet rset;
        try {
        		//rset = new JdbcUserDao().getTvLogoAndName("2", "2mXgOaAJqEUjiAxfMGDCXQHuaGscGnvULuyeUDAoiJscuoGROP");
        		
            //userDao.deletFriendById(32, 34,"");
        	//System.out.println(userDao.login("Vagarshak","2112"));
            //userDao.addUser(user);
           // userDao.updateUser(user,"mishaYZQQZEyJIozVfTVhgUGOUMbNNAofffKjODztDIoagLFQn");
        	//userDao.logout("Serojh");
        	//userDao.sendFriendRequest(1, 3);  
        	//userDao.acceptFriendRequest(3, 1);
        	//userDao.removeUser(24);
        	//System.out.println(userDao.getUserId("Asya"));
        	//System.out.println(userDao.getTokenById(23));
        	//ArrayList <User> list = userDao.getfriendRecuestList(29);
        	//for(User us : list){
        	//	System.out.println(us.getUsername());
        	//}
        	
        	//userDao.declineFriendRequest(3,1,true);
        	//User list=userDao.searchUsers("misha");
        	//System.out.println(list.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    

	
	}

	

	

	

	


	



	

	

	

}
