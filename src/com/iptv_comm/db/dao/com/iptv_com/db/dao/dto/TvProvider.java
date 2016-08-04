package com.iptv_comm.db.dao.com.iptv_com.db.dao.dto;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO class for TV Provider
 */
@XmlRootElement
public class TvProvider {
    private int tvProviderId;
    private String companyName;

    public int getTvProviderId() {
        return tvProviderId;
    }
    @XmlElement
    public void setTvProviderId(int tvProviderId) {
        this.tvProviderId = tvProviderId;
    }

    public String getCompanyName() {
        return companyName;
    }
    @XmlElement
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
