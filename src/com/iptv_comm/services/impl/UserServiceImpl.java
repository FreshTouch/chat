package com.iptv_comm.services.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.User;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvChannel;
import com.iptv_comm.db.dao.com.iptv_com.db.exceptions.DBException;
import com.iptv_comm.db.dao.dao.impl.mysql.JdbcUserDao;
import com.iptv_comm.services.model.UserService;

@Path("/UserService")
public class UserServiceImpl implements UserService {

	JdbcUserDao userDao;

	public UserServiceImpl() {
		userDao = new JdbcUserDao();
	}

	@GET
	@Path("/users")
	@Produces({MediaType.APPLICATION_JSON })
	@Override
	public ArrayList<User> userList() throws DBException {
		return userDao.userList();
	}

	@POST
	@Path("/register")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Override
	public void registerUser(User user) throws DBException {
		userDao.addUser(user);

	}

	@DELETE
	@Path("/delete/{username}")
	@Override
	public void removeUser(@PathParam("username") String username) throws DBException {
		long userId = userDao.getUserId(username);
		userDao.removeUser(userId);

	}

	@GET
	@Path("/login/{username},{password}")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@Override
	public User userLogin(@PathParam("username") String username, @PathParam("password") String password)
			throws DBException {
		User user = userDao.login(username, password);
		return user;
		
	}

	@PUT
	@Path("/logout/{username}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Override
	public void userLogout(@PathParam("username") String username) throws DBException {
		userDao.logout(username);

	}

	@PUT
	@Path("/update/{token}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Override
	public void updateUser(User user, @PathParam("token") String token) throws DBException {
		userDao.updateUser(user, token);
	}

	@GET
	@Path("/list/{userName},{token}")
	@Produces({MediaType.APPLICATION_JSON})
	@Override
	public ArrayList<TvChannel> getTvChannelList(@PathParam("userName") String userName, @PathParam("token") String token) throws DBException {
		return userDao.getTvChannelList(userName, token);
	}

	@POST
	@Path("/sendfr/{thisid},{receiverid},{token}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Override
	public void sendFriendRequest(@PathParam("thisid") long thisId, @PathParam("receiverid") long receiverId, @PathParam("token") String token)
			throws DBException {
		userDao.sendFriendRequest(thisId, receiverId, token);

	}
	
	@PUT
	@Path("/accfr/{accid},{senderid},{token}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Override
	public void acceptFriendRequest(@PathParam("accid") long accId, @PathParam("senderid") long senderId, @PathParam("token") String token)
			throws DBException {
		userDao.acceptFriendRequest(accId, senderId, token);

	}
	
	@PUT
	@Path("/declinefriend/{accid},{senderid},{isAnn},{token}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Override
	public void declineFriendRequest(@PathParam("accid") long accId, @PathParam("senderid") long senderId,
			@PathParam("isAnn") boolean isAnnoying, @PathParam("token") String token) throws DBException {
		userDao.declineFriendRequest(accId, senderId, isAnnoying, token);

	}

	

	@DELETE
	@Path("/deleteFrd/{userId},{friendId},{token}")
	@Override
	public void deletFriendById(@PathParam("userId") long userId, @PathParam("friendId") long friendId, @PathParam("token") String token) throws DBException {
		userDao.deletFriendById(userId, friendId, token);
	}
	
	
	@GET
	@Path("/frlist/{userId},{token}")
	@Produces({MediaType.APPLICATION_JSON})
	@Override
	public ArrayList<User> getfriendList(@PathParam("userId") long userId,  @PathParam("token") String token) throws DBException {
		return userDao.getfriendList(userId, token);
	}
	//http://localhost:8080/IptvCommRest/rest/UserService/deleteFrd/34,32
	
	@GET
	@Path("/frreqlist/{userId}")
	@Produces({MediaType.APPLICATION_JSON})
	@Override
	public ArrayList<User> getfriendRecuestList(@PathParam("userId") long userId) throws DBException {
		return userDao.getfriendRecuestList(userId);
	}
	
	
	@GET
	@Path("/userinfo/{token}")
	@Produces({MediaType.APPLICATION_JSON})
	@Override
	public User getInfo(@PathParam("token") String token) throws DBException {
		
		return userDao.getInfo(token);
	}
	
	@GET
	@Path("/logoname/{username},{token}") 
	@Produces({MediaType.APPLICATION_JSON})
	@Override
	public ResultSet getTvLogoAndName(@PathParam("username") String userName,@PathParam("token") String token) throws Exception {
		return userDao.getTvLogoAndName(userName, token);
	}
	

	@GET
	@Path("/logo/{userName},{token}")
	@Produces({MediaType.APPLICATION_JSON})
	@Override
	public  ArrayList <String> getTvLogo(@PathParam("userName")String userName, @PathParam("token") String token) throws Exception {
		return userDao.getTvLogo(userName, token);
	}

	@GET
	@Path("/channelname/{userName},{token}")
	@Produces({MediaType.APPLICATION_JSON})
	@Override
	public  ArrayList <String> getTvChanelName(@PathParam("userName")String userName, @PathParam("token") String token) throws Exception {
		return userDao.getTvChanelName(userName, token);
	}
	@GET
	@Path("/searchuser/{userName}")
	@Produces({MediaType.APPLICATION_JSON})
	@Override
	public User searchUsers(@PathParam("userName") String username) throws DBException{
		return userDao.searchUsers(username);
	}

	

	
}