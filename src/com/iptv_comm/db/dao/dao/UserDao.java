package com.iptv_comm.db.dao.dao;

import java.util.ArrayList;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvChannel;
import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.User;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;

/**
 * DAO interface of TV Provider related functionality.
 */
public interface UserDao {

    /**
     * Add a new user
     * @param user
     * @return userId
     */
    long addUser(User user) throws DBException;

    /**
     * Removes a user
     * @param userId
     * @throws DBException
     */
    void removeUser(long userId) throws DBException;
    
    ArrayList<User> userList()throws DBException;
    
    long getUserId (String username) throws DBException;
    
    String login(String userName,String password) throws DBException;
    
     String getTokenById(long userId) throws DBException;
    
    void logout(String userName) throws DBException;
    
    void updateUser(User user,String token) throws DBException;
    
    ArrayList <TvChannel> getTvChannelList(String userName,String token) throws DBException;
    
    
    
    void sendFriendRequest(long thisId,long receiverId,String token) throws DBException; 
    
    void acceptFriendRequest(long accId,long senderId,String token) throws DBException;
    
    void declineFriendRequest(long accId,long senderId, boolean isAnnoying,String token) throws DBException;
    
    ArrayList <User> getfriendList(long userId,String token) throws DBException;

}
