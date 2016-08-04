package com.iptv_comm.db.dao.com.iptv_com.db.dao.dto;

import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="program")
public class TvProgram {

	private long tvProgramId;
	private int tvChannelId;
	private long tvProgramName;
	private long tvProgramDescr;
	private Date startDt;
	private int durationInMinutes;
	
	public long getTvProgramId() {
		return tvProgramId;
	}
	@XmlElement
	public void setTvProgramId(long tvProgramId) {
		this.tvProgramId = tvProgramId;
	}
	public int getTvChannelId() {
		return tvChannelId;
	}
	@XmlElement
	public void setTvChannelId(int tvChannelId) {
		this.tvChannelId = tvChannelId;
	}
	public long getTvProgramName() {
		return tvProgramName;
	}
	@XmlElement
	public void setTvProgramName(long tvProgramName) {
		this.tvProgramName = tvProgramName;
	}
	public long getTvProgramDescr() {
		return tvProgramDescr;
	}
	@XmlElement
	public void setTvProgramDescr(long tvProgramDescr) {
		this.tvProgramDescr = tvProgramDescr;
	}
	public Date getStartDt() {
		return startDt;
	}
	@XmlElement
	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}
	public int getDurationInMinutes() {
		return durationInMinutes;
	}
	@XmlElement
	public void setDurationInMinutes(int durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}
	
	
}
