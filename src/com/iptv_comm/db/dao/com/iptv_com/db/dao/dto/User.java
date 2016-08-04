package com.iptv_comm.db.dao.com.iptv_com.db.dao.dto;


import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO class for user
 */
@XmlRootElement(name="user")
public class User {
    private long userId;
    private String username;
    private String password;
    private String eMail;
    private String firstName;
    private String lastName;
    private Date dob;
    private String avatar;
    private String token;
    private Date lastactive;

    public long getUserId() {
        return userId;
    }
    @XmlElement
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public User() {
		
	}
	public User(long userId, String username, String password, String eMail, String firstName, String lastName,
			Date dob, String avatar) {
	
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.eMail = eMail;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.avatar = avatar;
	}
	public String getUsername() {
        return username;
    }
    @XmlElement
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public String geteMail() {
        return eMail;
    }
    @XmlElement
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getFirstName() {
        return firstName;
    }
    @XmlElement
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    @XmlElement
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }
    @XmlElement
    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAvatar() {
        return avatar;
    }
    @XmlElement
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

	public String getToken() {
		return token;
	}
	@XmlElement
	public void setToken(String token) {
		this.token = token;
	}

	public Date getLastactive() {
		return lastactive;
	}
	@XmlElement
	public void setLastactive(Date lastactive) {
		this.lastactive = lastactive;
	}


    
}
