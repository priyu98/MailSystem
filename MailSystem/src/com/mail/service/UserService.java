package com.mail.service;

import java.util.List;
import java.util.Map;

import com.mail.pojo.User;

public interface UserService {
	List<User> list(Map map);
	void add(Map map);
	void delete(String username);
	void editPassword(User user);
	User getUserByUserid(String username);
}
