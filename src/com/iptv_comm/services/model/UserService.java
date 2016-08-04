package com.iptv_comm.services.model;

import java.util.ArrayList;
import java.util.Date;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvChannel;
import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.User;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;

/**
 * Contains method declarations for User related functionality
 */
public interface UserService {
    /**
     * Register a user
     * @param username unique username
     * @param password password hash
     * @param email email address
     * @param firstName first name
     * @param lastName last name
     * @param dob date of birth
     */
    void registerUser(User user) throws DBException;

    /**
     * Remove a user
     * @param username unique username
     * 
     */
    ArrayList<User> userList()throws DBException;
    
    void removeUser(String username) throws DBException;
    
    String userLogin(String username,String password) throws DBException;
    
    void userLogout(String username)throws DBException;
    
    void updateUser( User user, String token) throws DBException;
    
    ArrayList<TvChannel> getTvChannelList(String userName, String token) throws DBException;
    
    void sendFriendRequest(long thisId,long receiverId, String token) throws DBException; 
    
    void acceptFriendRequest(long accId,long senderId, String token) throws DBException;
    
    void declineFriendRequest(long accId,long senderId, boolean isAnnoying, String token) throws DBException;
    
    ArrayList <User> getfriendList(long userId, String token) throws DBException;

}
