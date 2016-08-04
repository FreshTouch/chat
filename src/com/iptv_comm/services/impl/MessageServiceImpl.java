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
	@Path("/message/{senderid},{receiverid},{subject},{body}")
	@Consumes(MediaType.APPLICATION_XML)
	@Override
	public void sendMessage(@PathParam("senderid") long senderId, @PathParam("receiverid")  long receiverId, 
			                          @PathParam("subject") String subject, @PathParam("body") String body) throws DBException {
		 messagedao.sendMessage(senderId, receiverId, subject, body);
		
	}
	@GET
	@Path("/message/{reciverId}")
	@Produces({ MediaType.APPLICATION_XML })
	@Override
	public ArrayList<Message> getReceivedMessages( @PathParam("receiverid")long reciverId)
			throws DBException {
		return messagedao.getReceivedMessages(reciverId);
	}
	@PUT
	@Path("/showmessage/{messageId}")
	@Produces({ MediaType.APPLICATION_XML })
	@Override
	public String showMessageContent(@PathParam("messageId") long messageId) throws DBException {
		return messagedao.showMessageContent(messageId);
		
	}
	@PUT
	@Path("/replymessage/{messageId},{body}")
	@Consumes(MediaType.APPLICATION_XML)
	@Override
	public void replyToMessage(@PathParam("messageId") long messageId, @PathParam("body") String body) throws DBException {
		messagedao.replyToMessage(messageId, body);
		
	}
	@PUT
	@Path("/forwardmessage/{messageId},{newreceiverid}")
	@Consumes(MediaType.APPLICATION_XML)
	@Override
	public void forwardMessage(@PathParam("messageId")long messageId,  @PathParam("newreceiverid")long newreciverId)
			throws DBException {
		messagedao.forwardMessage(messageId, newreciverId); 
		
	}
	
	@POST
	@Path("/suggestprogram/{body}")
	@Consumes({ MediaType.APPLICATION_XML })
	@Override
	public void suggestLiveProgram(Message message, @PathParam("body") String body) throws DBException {
		messagedao.suggestLiveProgram(message, body);
		
	}
	@POST
	@Path("/commentprogram/{body}")
	@Consumes({ MediaType.APPLICATION_XML })
	@Override
	public void commentProgram(Message message, @PathParam("body") String body) throws DBException {
		messagedao.commentProgram(message, body);
		
	}
	@PUT
	@Path("/commentTocomment/{senderid},{parentid},{body}")
	@Consumes(MediaType.APPLICATION_XML)
	@Override
	public void commentToComment(@PathParam("senderid")long senderId,@PathParam("parentid") long parentId, @PathParam("body")String body)
			throws DBException {
		messagedao.commentToComment(senderId, parentId, body);
	}

}
