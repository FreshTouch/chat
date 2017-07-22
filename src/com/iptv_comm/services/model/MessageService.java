package com.iptv_comm.services.model;

import java.util.ArrayList;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.Message;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;

public interface MessageService {
	void sendMessage(long senderId, long receiverId, String subject,
			String body,String token) throws DBException;
	
	ArrayList<Message> getReceivedMessages(long reciverId,String token) throws DBException;
	
	String showMessageContent(long messageId,String token)throws DBException;
	
    void replyToMessage(long messageId,String body,String token)throws DBException;
	
	void forwardMessage(long messageId,long newreciverId,String token)throws DBException;
	
	void suggestLiveProgram(Message message, String body,String token) throws DBException;
	
	void commentProgram(Message message,String body,String token)throws DBException;
	
	void commentToComment(long senderId,long parentId,String body,String token)throws DBException;
}
