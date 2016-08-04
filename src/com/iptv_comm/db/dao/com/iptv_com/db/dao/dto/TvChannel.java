package com.iptv_comm.db.dao.com.iptv_com.db.dao.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="channel")
public class TvChannel {

	private int tvChannelId;
	private int providerId;
	private int channelNumber;
	private long channelName;
	private long channelDescr;
	private String channelUrl;
	private String channelLogo;
	
	
	public TvChannel() {
		super();
	}
	public int getTvChannelId() {
		return tvChannelId;
	}
	@XmlElement
	public void setTvChannelId(int tvChannelId) {
		this.tvChannelId = tvChannelId;
	}
	public int getProviderId() {
		return providerId;
	}
	@XmlElement
	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}
	public int getChannelNumber() {
		return channelNumber;
	}
	@XmlElement
	public void setChannelNumber(int channelNumber) {
		this.channelNumber = channelNumber;
	}
	public long getChannelName() {
		return channelName;
	}
	@XmlElement
	public void setChannelName(long channelName) {
		this.channelName = channelName;
	}
	public long getChannelDescr() {
		return channelDescr;
	}
	@XmlElement
	public void setChannelDescr(long channelDescr) {
		this.channelDescr = channelDescr;
	}
	public String getChannelUrl() {
		return channelUrl;
	}
	@XmlElement
	public void setChannelUrl(String channelUrl) {
		this.channelUrl = channelUrl;
	}
	public String getChannelLogo() {
		return channelLogo;
	}
	@XmlElement
	public void setChannelLogo(String channelLogo) {
		this.channelLogo = channelLogo;
	}
	
	
}
