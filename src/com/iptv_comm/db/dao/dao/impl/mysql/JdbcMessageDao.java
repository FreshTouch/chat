package com.iptv_comm.db.dao.dao.impl.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.Message;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;
import com.iptv_comm.db.dao.dao.MessageDao;
import com.iptv_comm.db.dao.factory.DaoFactory;

public class JdbcMessageDao extends JdbcDao implements MessageDao{
		
	public static final String insertmessagequery="insert into message_content values(null,1,?,?)";
	public static final String addmessage="insert into message values(null,?,?,?,null,null,null,?,null)";
	public static final String searchreceiverquery="select * from message where receiver=? and ISNULL(receive_dt)";
	public static final String showmessagequery="select user.first_name,user.last_name,message.send_dt,message_content.subject,message_content.body " +
			"from user,message,message_content where user.user_id= message.sender and" +
			" message.message_content_id= message_content.message_content_id and message.message_id=?";
	
	public static final String searchmessagebyId="Select * from message where message_id=?";
	
	public static final String searchMessage="select * from message_content where message_content_id=?";
	
	public static final String suggestquery="insert into message_content values(null,2,null,?)";
	

	@Override
	public void sendMessage(long senderId, long receiverId, String subject,
			String body) throws DBException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset;
		long messagecontentId=0;
		con=getConnection();
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
	}
	
	@Override
	public ArrayList<Message> getReceivedMessages(long reciverId)
			throws DBException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset;
		Message message;
		ArrayList<Message> receivedMessagesList=new ArrayList<Message>(); 
		con=getConnection();
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
		
		return receivedMessagesList;
	}
	
	@Override
	public String showMessageContent(long messageId) throws DBException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset;
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
			
			Date date = new Date(System.currentTimeMillis());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String data=dateFormat.format(date);
			
			
			String updatereceivedt="update message set receive_dt='"+data+"' where message_id=?";
			//pstmt.close();
			pstmt=con.prepareStatement(updatereceivedt,1);
			pstmt.setLong(1, messageId);
			pstmt.executeUpdate();
			
			showmessage = rset.getString(1) +"  "+rset.getString(2)+"  "+rset.getDate(3)+"   " +rset.getString(4)+"   "+rset.getString(5);
			
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
	public void replyToMessage(long messageId, String body)
			throws DBException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset;
		con=getConnection();
		try {
			pstmt=con.prepareStatement(searchmessagebyId,1);
			pstmt.setLong(1,messageId);
			rset=pstmt.executeQuery();
			rset.next();
			sendMessage(rset.getLong(3), rset.getLong(2), null, body);
			
		} catch (SQLException e) {
			throw new DBException();
		}
		
		finally{
			closeStatement(pstmt);
			closeConnection(con);
		}
	}
	
	
	
	@Override
	public void forwardMessage(long messageId, long newreciverId)
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
			long mess_cont_id=rset.getLong(4);
			//pstmt.close();
			pstmt=con.prepareStatement(searchMessage,1);
			pstmt.setLong(1, mess_cont_id);
			rset2=pstmt.executeQuery();
			rset2.next();
			sendMessage(rset.getLong(3),newreciverId,rset2.getString(3),rset2.getString(4));
			
		} catch (SQLException e) {
			throw new DBException();
		}
		
		finally{
			closeStatement(pstmt);
			closeConnection(con);
		}
	}
	@Override
	public void suggestLiveProgram( Message message, String body) throws DBException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset;
		con=getConnection();
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
		
	
	@Override
	public void commentProgram(Message message, String body) throws DBException {
		String commentquery="insert into message_content values(null,3,null,?)";
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		con=getConnection();
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
	
	@Override
	public void commentToComment(long senderId, long parentId, String body) throws DBException {
		String searchChannelProgram="select tv_channel_id,tv_program_id from message where message_id=?";
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String commentquery="insert into message_content values(null,3,null,?)";
		long messagecontentId=0;
		con=getConnection();
		try {
			pstmt=con.prepareStatement(searchChannelProgram,1);
			pstmt.setLong(1, parentId);
			rset=pstmt.executeQuery();
			rset.next();	
			int channelid=rset.getInt(1);
			long programid=rset.getLong(2);
			pstmt.close();
			
			pstmt=con.prepareStatement(commentquery,1);
			pstmt.setString(1, body);
			pstmt.executeUpdate();
			rset=pstmt.executeQuery("select LAST_INSERT_ID()");
			rset.next();
			messagecontentId=rset.getLong(1);
			pstmt.close();
			Date date = new Date(System.currentTimeMillis());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String data=dateFormat.format(date);
			
			String addcomment="insert into message values(null,?,null,?,?,?,?,'"+data+"',null)";
			
			pstmt=con.prepareStatement(addcomment,4);
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
	
	public static void main(String args[]){
		DaoFactory fact=DaoFactory.getDaoFactory(DaoFactory.DBType.MySQL);
        MessageDao messagedao=fact.getMessageDao();
        //Message message=new Message();
        try {
       	messagedao.sendMessage(5,3,"barev","fffff");
//        ArrayList<Message> list =  messagedao.getReceivedMessages(5);
//        for(Message mes:list){
//        	System.out.println(mes.getMessageId()+"   "+mes.getSender()+"   "+mes.getSendDt());
//        	}
        	//messagedao.showMessageContent(5);
        	//messagedao.replyToMessage(4, "hhh");
        	//messagedao.forwardMessage(2, 1);
        	//messagedao.suggestLiveProgram(1, 1, 2, 4, "dfjhtgjh");
        	//messagedao.commentProgram(1,1,1,"nayeq");
        	//messagedao.commentToComment(1,13,"nayum enq exbayr jan");
        } 
        
        catch (DBException e) {
            e.printStackTrace();
        }
	}

	

	

	

	

	

	
	

}
