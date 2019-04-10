package com.mail.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.mail.pojo.User;
import com.mail.service.UserService;

import com.mail.pojo.User;
import com.mail.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	DaoSupport dao;
	
	@Override
	public List<User> list(Map map) {
		// TODO Auto-generated method stub
		return (List<User>) dao.findForList("UserMapper.list", map);
	}

	@Override
	public void add(Map map) {
		// TODO Auto-generated method stub
		//userMapper.add(user);
		dao.update("UserMapper.add", map);
	}

	@Override
	public void delete(String username) {
		// TODO Auto-generated method stub
		//userMapper.delete(username);
		dao.delete("UserMapper.delete", username);
	}

	@Override
	public void editPassword(User user) {
		// TODO Auto-generated method stub
		//userMapper.editPassword(user);
		dao.update("UserMapper.editPassword", user);
	}

	@Override
	public User getUserByUserid(String username) {
		// TODO Auto-generated method stub
		//return userMapper.getByUserid(username);
		return (User)dao.findForObject("UserMapper.getByUserid", username);
	}



}
