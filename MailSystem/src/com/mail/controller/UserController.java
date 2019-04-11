package com.mail.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mail.pojo.User;
import com.mail.service.UserService;
import com.mail.util.PassUtil;
import com.mail.util.SensitiveWordUtil;
import com.mail.util.SensitiveWordUtil.MatchType;

@Controller
@RequestMapping("")
public class UserController {
	@Autowired
	UserService userService;
	
	@RequestMapping("listUser")
	public ModelAndView listUser(int page) {
		Map map =new HashMap();
		int limit_size=20;
		int limit_start=page*limit_size;
		map.put("limit_start", limit_start);
		map.put("limit_size", limit_size);
		ModelAndView mv = new ModelAndView();
		List<User> userlist = userService.list(map);
		mv.addObject("userlist",userlist);
		mv.setViewName("listUser");
		return mv;
	}
	
	@RequestMapping(value = "addUser",produces = "text/plain;charset=utf-8")
	public ModelAndView addUser(String username,String password) throws NoSuchAlgorithmException {
		Map map = new HashMap();
		Set<String> sensitiveWord = userService.getSensitiveWordSet();
		/*SensitiveWordUtil wordUtil = new SensitiveWordUtil();
		wordUtil.initSensitiveWordsMap(sensitiveWord);
		System.out.println(wordUtil.getSensitiveWords(username, MatchType.MAX_MATCH));*/
		map.put("username", username);
		map.put("pwdHash", PassUtil.digestString(password, "SHA"));
		map.put("password", password);
		userService.add(map);
		ModelAndView mv = new ModelAndView("redirect:/listUser?page=0");
		return mv;
	}
	
	@RequestMapping("deleteUser")
	public ModelAndView deleteUser(String username) {
		userService.delete(username);
		ModelAndView mv = new ModelAndView("redirect:/listUser?page=0");
		return mv;
	}
	
	@RequestMapping("editUser")
	public ModelAndView edituser(String username) {
		User user=userService.getUserByUserid(username);
		ModelAndView mv = new ModelAndView();
		mv.addObject("user",user);
		mv.setViewName("editUser");
		return mv;
	}
	
	@RequestMapping("editPassword")
	public ModelAndView editPassword(User user) throws NoSuchAlgorithmException{
		String pass = user.getPassword();
		user.setPwdHash(pass);
		userService.editPassword(user);
		ModelAndView mv = new ModelAndView("redirect:/listUser?page=0");
		return mv;
	}
	//后台登录
	@RequestMapping("AdminLogin")
	public String AdminLogin(String username, String pass){
		User Auser = this.userService.getUserByUserid(username);
		if(Auser==null)
			return "00"; //没有该用户
		else {
			if(Auser.getPassword().equals(pass))
				return "1";  //登录
			else {
				return "01";  //密码错误
			}
		}
	}
	
}
