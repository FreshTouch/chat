package com.iptv_comm.services.impl;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.TvChannel;
import com.iptv_comm.db.dao.com.iptv_com.db.dao.dto.User;
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
	@Produces({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Override
	public ArrayList<User> userList() throws DBException {
		return userDao.userList();
	}

	@POST
	@Path("/users")
	@Consumes({ MediaType.APPLICATION_XML })
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
	@Path("login/{username},{password}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	@Override
	public String userLogin(@PathParam("username") String username, @PathParam("password") String password)
			throws DBException {
		String ustoken = userDao.login(username, password);
		return ustoken;
	}

	@PUT
	@Path("/logout/{username}")
	@Consumes(MediaType.APPLICATION_XML)
	@Override
	public void userLogout(@PathParam("username") String username) throws DBException {
		userDao.logout(username);

	}

	@PUT
	@Path("/update/{token}")
	@Consumes(MediaType.APPLICATION_XML)
	@Override
	public void updateUser(User user, @PathParam("token") String token) throws DBException {
		userDao.updateUser(user, token);
	}

	@GET
	@Path("/list/{userName},{token}")
	@Produces(MediaType.APPLICATION_XML)
	@Override
	public ArrayList<TvChannel> getTvChannelList(@PathParam("userName") String userName, @PathParam("token") String token) throws DBException {
		return userDao.getTvChannelList(userName, token);
	}

	@POST
	@Path("/users/{thisid},{receiverid},{token}")
	@Consumes(MediaType.APPLICATION_XML)
	@Override
	public void sendFriendRequest(@PathParam("thisid") long thisId, @PathParam("receiverid") long receiverId, @PathParam("token") String token)
			throws DBException {
		userDao.sendFriendRequest(thisId, receiverId, token);

	}

	@PUT
	@Path("/users/acc/{accid},{senderid},{token}")
	@Consumes(MediaType.APPLICATION_XML)
	@Override
	public void acceptFriendRequest(@PathParam("accid") long accId, @PathParam("senderid") long senderId, @PathParam("token") String token)
			throws DBException {
		userDao.acceptFriendRequest(accId, senderId, token);

	}

	@PUT
	@Path("/users/{accid},{senderid},{isAnn},{token}")
	@Consumes(MediaType.APPLICATION_XML)
	@Override
	public void declineFriendRequest(@PathParam("accid") long accId, @PathParam("senderid") long senderId,
			@PathParam("isAnn") boolean isAnnoying, @PathParam("token") String token) throws DBException {
		userDao.declineFriendRequest(accId, senderId, isAnnoying, token);

	}

	@GET
	@Path("/users/{userId},{token}")
	@Produces(MediaType.APPLICATION_XML)
	@Override
	public ArrayList<User> getfriendList(@PathParam("userId") long userId,  @PathParam("token") String token) throws DBException {
		return userDao.getfriendList(userId, token);
	}

}