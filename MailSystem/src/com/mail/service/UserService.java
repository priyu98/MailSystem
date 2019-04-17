package com.mail.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mail.pojo.User;

public interface UserService {
	List<User> list(Map map);
	void add(Map map);
	void delete(String username);
	void editPassword(User user);
	User getUserByUserid(String username);
	Set<String> getSensitiveWordSet();
	List<User> searchByUsername(Map<String,Object> map);
	void SetAdmin(Map<String,Object> map);
}
