package com.iptv_comm.services.impl;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.Message;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;
import com.iptv_comm.db.dao.dao.impl.mysql.JdbcMessageDao;
import com.iptv_comm.services.model.MessageService;
@Path("/MessageService")
public class MessageServiceImpl implements MessageService {

	JdbcMessageDao messagedao;
	
	public MessageServiceImpl(){
		messagedao = new JdbcMessageDao();
	}
	
	@PUT
	@Path("/message/{senderid},{receiverid},{subject},{body},{token}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Override
	public void sendMessage(@PathParam("senderid") long senderId, @PathParam("receiverid")  long receiverId, 
			                          @PathParam("subject") String subject, @PathParam("body") String body, @PathParam("token") String token) throws DBException {
		 messagedao.sendMessage(senderId, receiverId, subject, body,token);
		 
		
	}
	
	@GET
	@Path("/messagelist/{reciverId},{token}")
	@Produces({MediaType.APPLICATION_JSON})
	@Override
	public ArrayList<Message> getReceivedMessages(@PathParam("reciverId") long reciverId, @PathParam("token") String token)
			throws DBException {
		
		return messagedao.getReceivedMessages(reciverId,token);
	}
	
	@PUT
	@Path("/showmessage/{messageId},{token}")
	//@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@Override
	public String showMessageContent(@PathParam("messageId") long messageId,@PathParam("token") String token) throws DBException {
		return messagedao.showMessageContent(messageId,token);
		
	}
	
	@PUT
	@Path("/replymessage/{messageId},{body},{token}")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@Override
	public void replyToMessage(@PathParam("messageId") long messageId, @PathParam("body") String body,@PathParam("token") String token) throws DBException {
		messagedao.replyToMessage(messageId, body,token);
		
	}
	

	@PUT
	@Path("/forwardmessage/{messageId},{newreceiverid},{token}")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@Override
	public void forwardMessage(@PathParam("messageId") long messageId,  @PathParam("newreceiverid") long newreciverId,@PathParam("token") String token)
			throws DBException {
		messagedao.forwardMessage(messageId, newreciverId,token); 
		
	}
	

	@POST
	@Path("/suggestprogram/{body},{token}")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@Override
	public void suggestLiveProgram(Message message, @PathParam("body") String body,@PathParam("token") String token) throws DBException {
		messagedao.suggestLiveProgram(message, body,token);
		
	}
	
	
	@POST
	@Path("/commentprogram/{body},{token}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Override
	public void commentProgram(Message message, @PathParam("body") String body, @PathParam("token") String token) throws DBException {
		messagedao.commentProgram(message, body,token);
		
	}
	
	@POST
	@Path("/commentTocomment/{senderid},{parentid},{body},{token}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Override
	public void commentToComment(@PathParam("senderid")long senderId,@PathParam("parentid") long parentId, @PathParam("body")String body,@PathParam("token") String token)
			throws DBException {
		messagedao.commentToComment(senderId, parentId, body,token);
	}

}
