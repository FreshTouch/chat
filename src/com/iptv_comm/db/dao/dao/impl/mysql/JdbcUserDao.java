package com.iptv_comm.db.dao.dao.impl.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public static final String insertQuery="insert into "+tableName+" (username, password, email, first_name, last_name, dob, avatar) "+
            "values(?, ?, ?, ?, ?, ?, ?);";
    public static final String deleteQuery="delete from "+tableName+" where  "+
            "user_id = ?";
    public static final String searchQuery = "select * from " + tableName + " where " +
    		"username= ?";
    
    public static final String updateQuery = "update  "+ tableName+"  set username =? , password=? , email=? , first_name=? , last_name=?," +
			   "dob=?, avatar=? where user_id=?";
    
    public static final String search="select * from "+ tableName+" where username=? and password=?";
    public static final String updateToken = "update  "+ tableName+"  set token =? where username=?";
    public static final String selectToken = "select token from "+ tableName+"  where username=?";
    public static final String  updateTokenToNull="update  "+ tableName+"  set token = null where username=?";
    public static final String updatelastactive = "update  "+ tableName+"  set last_active =? where username=?";

	public static final String tvchannelsearchquery="select * from tv_channel,tv_provider,user_tv_provider,user where user.user_id=user_tv_provider.user_id " +
			"and user_tv_provider.tv_provider_id = tv_provider.tv_provider_id and " +
			"tv_provider.tv_provider_id = tv_channel.provider_id and user.username=?";
	
	public static final String friendRequestQuery="insert into user_assoc value(null,?,?,1)";
	public static final String requestResponseQuery="update user_assoc set assoc_type_id=? where object_user_id=? and subject_user_id=?";
	public static final String  deleteAssocQuery="delete from user_assoc where object_user_id=? and subject_user_id=?";
	public static final String isExistQuery="select * from user_assoc where object_user_id=? and subject_user_id=?";
	public static final String isDublicateQuery="select * from user_assoc where object_user_id=? and subject_user_id=? and (assoc_type_id=1 or assoc_type_id=2)";
    public static final String getFriend1="select object_user_id from user_assoc where subject_user_id=? and assoc_type_id=2";
    public static final String getFriend2="select subject_user_id from user_assoc where object_user_id=? and assoc_type_id=2";
    public static final String searchfriendQuery = "select * from " + tableName + " where " +
    		"user_id = ?";
    
    @Override
	public long addUser(User user) throws DBException {
		long userId = 0;
		if(user != null && user.getUsername() != null && user.getPassword()!=null) {
			Connection con = null;
			PreparedStatement ps = null;
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
	public String login(String userName,String password) throws DBException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs;
		String usname="";
		
		char  xar []={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
				'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
				'!','@','#','$','%','^','&','*','(',')','{','}','[',']','.','>','<','/','?'};
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
					
			}
		}
		catch(Exception e){
			throw new DBException(e);
		}
		finally {
			closeStatement(ps);
			closeConnection(con);
		}
		return token;
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
			if(user.getUsername()!=null){
				pstmt.setString(1,user.getUsername());
				
			}
			else{
				pstmt.setString(1,rset.getString(2));
			}
		
			
			
			
			if(user.getPassword()!=null){
				pstmt.setString(2,user.getPassword());
			}
			else{
				pstmt.setString(2,rset.getString(3));
			}
			if(user.geteMail()!=null){
				pstmt.setString(3,user.geteMail());
			}
			else{
				pstmt.setString(3,rset.getString(4));
			}
			if(user.getFirstName()!=null){
				pstmt.setString(4,user.getFirstName());
			}
			else{
				pstmt.setString(4,rset.getString(5));
			}
			if(user.getLastName()!=null){
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
			
			
			
			if(user.getAvatar()!=null){
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
		ResultSet rset;
		TvChannel channel;
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
		ResultSet rset;
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
		ResultSet rset;
		ResultSet rset2;
		User user;
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
				user.setFirstName(rset2.getString(5));
				user.setLastName(rset2.getString(6));
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
	public static void main(String[] args){
        DaoFactory fact=DaoFactory.getDaoFactory(DaoFactory.DBType.MySQL);
        UserDao userDao=fact.getUserDao();
        User user=new User();
        user.setLastName("Serob");
        user.setPassword("2112");

        Date d=new Date(System.currentTimeMillis());
        user.setDob(d);
        user.setUserId(23);
        
        try {
        	
        	//System.out.println(userDao.login("Asya","22"));
            //userDao.addUser(user);
          //  userDao.updateUser(user,"AsyaO@jU&Y@Pyde$.EmYXR>d<uMx)Dg[(RehYPK[.rCtCT$R");
        	//userDao.logout("Serojh");
        	//userDao.sendFriendRequest(1, 3);
        	//userDao.acceptFriendRequest(3, 1);
        	//userDao.removeUser(24);
        	//System.out.println(userDao.getUserId("Asya"));
        	System.out.println(userDao.getTokenById(23));
        	
        	//userDao.declineFriendRequest(3,1,true);
        	//ArrayList<User> list=userDao.getfriendList(2);
        	//for(User us:list){
        		//System.out.println(us.getUserId());
        	//}
        } catch (DBException e) {
            e.printStackTrace();
        }
        
        
    

	
	}


	



	

	

	

}
