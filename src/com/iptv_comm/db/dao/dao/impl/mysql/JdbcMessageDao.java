package com.iptv_comm.db.dao.dao.impl.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.aopalliance.reflect.Metadata;
import org.json.JSONArray;
import org.json.JSONObject;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.Message;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;
import com.iptv_comm.db.dao.dao.MessageDao;
import com.iptv_comm.db.dao.factory.DaoFactory;
import com.mysql.jdbc.ResultSetMetaData;

public class JdbcMessageDao extends JdbcDao implements MessageDao{
	JdbcUserDao userDao;
	
	public JdbcMessageDao(){
	userDao = new JdbcUserDao();}
		
	//String addmessage="insert into message values(null,?,?,?,null,null,null,?,null)";
	public static final String searchmessagebyId="Select * from message where message_id=?";	
	
	

	@Override
	public void sendMessage(long senderId, long receiverId, String subject,
			String body,String token) throws DBException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset;
		String insertmessagequery="insert into message_content values(null,1,?,?)";
		long messagecontentId=0;
		con=getConnection();
		if(token.equals(userDao.getTokenById(senderId))){
			try {
				pstmt=con.prepareStatement(insertmessagequery,2);
				pstmt.setString(1, subject);
				pstmt.setString(2, body);
				pstmt.executeUpdate();
				rset=pstmt.executeQuery("select LAST_INSERT_ID()");
				rset.next();
				messagecontentId=rset.getLong(1);
				//pstmt.close();
				Date date = new Date(System.currentTimeMillis());
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String data=dateFormat.format(date);
				
				String addmessage="insert into message values(null,?,?,?,null,null,null,'"+data+"',null)";
				pstmt.close();
				pstmt=con.prepareStatement(addmessage,3);
				pstmt.setLong(1,senderId);
				pstmt.setLong(2,receiverId);
				pstmt.setLong(3, messagecontentId);
				pstmt.executeUpdate();
				
				
				
			} catch (SQLException e) {
				throw new DBException(e);
			}
			finally{
				closeStatement(pstmt);
				closeConnection(con);
			}
		}else{
			throw new DBException ("token is not valid");
		}
	}
	
	@Override
	public ArrayList<Message> getReceivedMessages(long reciverId,String token)
			throws DBException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		Message message=null;
		String searchreceiverquery="select * from message where receiver=? and ISNULL(receive_dt)";
		ArrayList<Message> receivedMessagesList=new ArrayList<Message>(); 
		con=getConnection();
		
		if(token.equals(userDao.getTokenById(reciverId))){
		try {
			pstmt=con.prepareStatement(searchreceiverquery,1);
			pstmt.setLong(1,reciverId);
			rset=pstmt.executeQuery();
			while(rset.next()){
				message=new Message();
				message.setMessageId(rset.getLong(1));
				message.setSender(rset.getLong(2));
				message.setMessageContentId(rset.getLong(4));
				message.setSendDt(rset.getDate(8));
				receivedMessagesList.add(message);
			}
		} catch (SQLException e) {
			throw new DBException();
		}
		
		finally{
			closeStatement(pstmt);
			closeConnection(con);
		}
		
		
		}else{
			throw new DBException("token is not valid");
		}
		
		return receivedMessagesList;
	}
	
	@Override
	public String showMessageContent(long messageId,String token) throws DBException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String showmessagequery="select user.first_name,user.last_name,message.send_dt,message_content.subject,message_content.body,user.username " +
				"from user,message,message_content where user.user_id= message.sender and" +
				" message.message_content_id= message_content.message_content_id and message.message_id=?";
		String showmessage;
		
		try {
			con=getConnection();
			pstmt=con.prepareStatement(showmessagequery,1);
			pstmt.setLong(1,messageId);
			rset=pstmt.executeQuery();
			rset.next();
			//chjnjel!!!!
			//System.out.print(rset.getString(1) +"  "+rset.getString(2)+"  "+rset.getDate(3)+"   ");
			//System.out.println(rset.getString(4)+"   "+rset.getString(5));
			//if(token.equals(userDao.getTokenById(userDao.getUserId(rset.getString(6))))){
					
			Date date = new Date(System.currentTimeMillis());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String data=dateFormat.format(date);
			
			
			String updatereceivedt="update message set receive_dt='"+data+"' where message_id=?";
			//pstmt.close();
			pstmt=con.prepareStatement(updatereceivedt,1);
			pstmt.setLong(1, messageId);
			pstmt.executeUpdate();
			
			showmessage ="{\"firstName\": \""+rset.getString(1) +"\", \"lastName\": \"" +rset.getString(2)+"\","
					      + " \"subject\" : \"" +rset.getString(4)+"\",\"body\" :  \""+rset.getString(5)+"\"}";
			
			/*}
			else{
				throw new DBException("token is not valid");
			}*/
		} catch (SQLException e) {
			
			throw new DBException();
		}
		
		finally{
			closeStatement(pstmt);
			closeConnection(con);
		}
		return showmessage;
	}
	
	
	

	
	@Override
	public void replyToMessage(long messageId, String body,String token)
			throws DBException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		con=getConnection();
		try {
			pstmt=con.prepareStatement(searchmessagebyId,1);
			pstmt.setLong(1,messageId);
			rset=pstmt.executeQuery();
			rset.next();
			if(token.equals(userDao.getTokenById(rset.getLong(3)))){
			sendMessage(rset.getLong(3), rset.getLong(2), null, body,userDao.getTokenById( rset.getLong(3)));
			
			}
			else{
				
				throw new DBException("token is not valid");
			}
		} catch (SQLException e) {
			throw new DBException();
		}
		
		finally{
			closeStatement(pstmt);
			closeConnection(con);
		}
	}
	
	
	
	@Override
	public void forwardMessage(long messageId, long newreciverId,String token)
			throws DBException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset;
		ResultSet rset2;
		con=getConnection();
		try {
			pstmt=con.prepareStatement(searchmessagebyId,1);
			pstmt.setLong(1,messageId);
			rset=pstmt.executeQuery();
			rset.next();
			if(token.equals(userDao.getTokenById(rset.getLong(3)))){
			long mess_cont_id=rset.getLong(4);
			//pstmt.close();
			String searchMessage="select * from message_content where message_content_id=?";
			pstmt=con.prepareStatement(searchMessage,1);
			pstmt.setLong(1, mess_cont_id);
			rset2=pstmt.executeQuery();
			rset2.next();
			sendMessage(rset.getLong(3),newreciverId,rset2.getString(3),rset2.getString(4),userDao.getTokenById(rset.getLong(3)));
			
			}
			else{
				
				throw new DBException("token is not valid");
			}
			
		} catch (SQLException e) {
			throw new DBException();
		}
		
		finally{
			closeStatement(pstmt);
			closeConnection(con);
		}
	}
	@Override
	public void suggestLiveProgram( Message message, String body,String token) throws DBException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String suggestquery="insert into message_content values(null,2,null,?)";
		con=getConnection();
		if(token.equals(userDao.getTokenById(message.getSender()))){
		try {
			pstmt=con.prepareStatement(suggestquery,1);
			pstmt.setString(1, body);
			pstmt.executeUpdate();
			rset=pstmt.executeQuery("select LAST_INSERT_ID()");
			rset.next();
			message.setMessageContentId(rset.getLong(1));
			pstmt.close();
			Date date = new Date(System.currentTimeMillis());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String data=dateFormat.format(date);
			
			String addmessage="insert into message values(null,?,?,?,null,?,?,'"+data+"',null)";
			
			pstmt=con.prepareStatement(addmessage,5);
			pstmt.setLong(1,message.getSender());
			pstmt.setLong(2,message.getReceiver());
			pstmt.setLong(3, message.getMessageContentId());
			pstmt.setInt(4, message.getTvChannelId());
			pstmt.setLong(5, message.getTvProgramId());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
		finally{
			closeStatement(pstmt);
			closeConnection(con);
		}
		
		}
		
		else{
			throw new DBException("token is not valid");
		}
	}
		
	
	@Override
	public void commentProgram(Message message, String body,String token) throws DBException {
		String commentquery="insert into message_content values(null,3,null,?)";
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		con=getConnection();
		if(token.equals(userDao.getTokenById(message.getSender()))){
		try {
			pstmt=con.prepareStatement(commentquery,1);
			pstmt.setString(1, body);
			pstmt.executeUpdate();
			rset=pstmt.executeQuery("select LAST_INSERT_ID()");
			rset.next();
			message.setMessageContentId(rset.getLong(1));
			pstmt.close();
			Date date = new Date(System.currentTimeMillis());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String data=dateFormat.format(date);
			
			String addcomment="insert into message values(null,?,null,?,null,?,?,'"+data+"',null)";
			
			pstmt=con.prepareStatement(addcomment,4);
			pstmt.setLong(1,message.getSender());
			pstmt.setLong(2, message.getMessageContentId());
			pstmt.setInt(3,message.getTvChannelId());
			pstmt.setLong(4, message.getTvProgramId());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new DBException(e);
		}
		finally{
			closeStatement(pstmt);
			closeConnection(con);
		}
		}
		
		else{
			throw new DBException("token is not valid");
		}
	}
	
	@Override
	public void commentToComment(long senderId, long parentId, String body,String token) throws DBException {
		String searchChannelProgram="select tv_channel_id,tv_program_id from message where message_id=?";
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String commentquery="insert into message_content values(null,3,null,?)";
		long messagecontentId=0;
		con=getConnection();
		long programid;
		int channelid;
		if(token.equals(userDao.getTokenById(senderId))){
		try {
			pstmt=con.prepareStatement(searchChannelProgram,1);
			pstmt.setLong(1, parentId);
			rset=pstmt.executeQuery();
			rset.next();	
			 channelid=rset.getInt(1);
			programid=rset.getLong(2);
			//pstmt.close();
			
			pstmt=con.prepareStatement(commentquery,1);
			pstmt.setString(1, body);
			pstmt.executeUpdate();
			rset=pstmt.executeQuery("select LAST_INSERT_ID()");
			rset.next();
			messagecontentId=rset.getLong(1);
			//pstmt.close();
			Date date = new Date(System.currentTimeMillis());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String data=dateFormat.format(date);
			
			String addcomment="insert into message values(null,?,null,?,?,?,?,'"+data+"',null)";
			
			pstmt=con.prepareStatement(addcomment,5);
			pstmt.setLong(1,senderId);
			pstmt.setLong(2, messagecontentId);
			pstmt.setLong(3, parentId);
			pstmt.setInt(4,channelid);
			pstmt.setLong(5, programid);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new DBException(e);
		}
		finally{
			closeStatement(pstmt);
			closeConnection(con);
		}
		}
		
		else{
			
			throw new DBException("token is not valid");
		}
	}
	
	public static void main(String args[]){
		DaoFactory fact=DaoFactory.getDaoFactory(DaoFactory.DBType.MySQL);
        MessageDao messagedao=fact.getMessageDao();
        Message message=new Message();
        message.setSender(23);
        //message.setReceiver(23);
        message.setTvChannelId(1);
        message.setTvProgramId(1);
        try {
      	//messagedao.sendMessage(23,22,"barev","fbdfbnnbdfn","AsyaO@jU&Y@PyEmYXfpR>d<uMx)Dg[(RehYPK[.rCtCT$R");
      /*  ArrayList<Message> list =  messagedao.getReceivedMessages(5,"KarenJlMkg@$]R(V&[T*YiZLxwe<X[<Yl#wW&I]yLskGFb[%{o");
       for(Message mes:list){
       	System.out.println(mes.getMessageId()+"   "+mes.getSender()+"   "+mes.getSendDt());
      	}*/

        System.out.println(messagedao.showMessageContent(10,"degenikYxoYaMKgNBZCssfjTmzBCyqGjqsIRSysLygUxdxHIzn"));
        	//messagedao.replyToMessage(4,"Kakashka", "VagarshaknICPM(Q*WQIZc%b!j@b.k}<!^Ta}jiQejbQaulRI]");
        	//messagedao.forwardMessage(2, 23,"VagarshaknICPM(Q*WQIZc%b!j@b.k}<!^Ta}jiQejbQaulRI]");
        	//messagedao.suggestLiveProgram(message,"Hello","VagarshaknICPM(Q*WQIZc%b!j@b.k}<!^Ta}jiQejbQaulRI]");
        	//messagedao.commentProgram(message,"nayeq","AsyaO@jU&Y@Pyde$.EmYXfpR>d<uMx)Dg[(RehYPK[.rCtCT$R");
        	//messagedao.commentToComment(22,8,"nayum enq exbayr jan","VagarshaknICPM(Q*WQIZc%b!j@b.k}<!^Ta}jiQejbQaulRI]");
        } 
        
        catch (DBException e) {
            e.printStackTrace();
        }
	}

	
	

	

	

	

	

	
	

}
