package com.iptv_comm.db.dao.com.iptv_com.db.dao.dto;

import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="message")
public class Message {

	private long messageId;
	private long sender;
	private long receiver;
	private long messageContentId;
	private long parentMessageId;
	private int tvChannelId;
	private long tvProgramId;
	private Date sendDt;
	private Date receiveDt;
	public long getMessageId() {
		return messageId;
	}
	
	 @XmlElement
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	 
	public long getSender() {
		return sender;
	}
	 @XmlElement
	public void setSender(long sender) {
		this.sender = sender;
	}
	 
	public long getReceiver() {
		return receiver;
	}
	 @XmlElement
	public void setReceiver(long receiver) {
		this.receiver = receiver;
	}
	
	public long getMessageContentId() {
		return messageContentId;
	}
	 @XmlElement
	public void setMessageContentId(long messageContentId) {
		this.messageContentId = messageContentId;
	}
	
	public long getParentMessageId() {
		return parentMessageId;
	}
	 @XmlElement
	public void setParentMessageId(long parentMessageId) {
		this.parentMessageId = parentMessageId;
	}
	
	public int getTvChannelId() {
		return tvChannelId;
	}
	 @XmlElement
	public void setTvChannelId(int tvChannelId) {
		this.tvChannelId = tvChannelId;
	}
	
	public long getTvProgramId() {
		return tvProgramId;
	}
	 @XmlElement
	public void setTvProgramId(long tvProgramId) {
		this.tvProgramId = tvProgramId;
	}
	
	public Date getSendDt() {
		return sendDt;
	}
	 @XmlElement
	public void setSendDt(Date sendDt) {
		this.sendDt = sendDt;
	}
	
	public Date getReceiveDt() {
		return receiveDt;
	}
	 @XmlElement
	public void setReceiveDt(Date receiveDt) {
		this.receiveDt = receiveDt;
	}
	
	

}
