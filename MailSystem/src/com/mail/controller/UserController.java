package com.mail.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
	
	private Gson gson = new GsonBuilder().serializeNulls().create();
	
	@ResponseBody
	@RequestMapping("listUser")
	public String listUser(HttpServletRequest request) {
		int page = Integer.valueOf(request.getParameter("page"));
		Map<String,Object> map = new HashMap<String,Object>();
		int limit_size=20;
		int limit_start=page*limit_size;
		map.put("limit_start", limit_start);
		map.put("limit_size", limit_size);
		List<User> userlist = userService.list(map);
		/*ModelAndView mv = new ModelAndView();	
		mv.addObject("userlist",userlist);
		mv.setViewName("listUser");*/
		map.put("userlist", userlist); //用户列表
		return gson.toJson(map);
	}
	
	@ResponseBody
	@RequestMapping(value = "addUser",produces = "text/plain;charset=utf-8")
	public String addUser(HttpServletRequest request) throws NoSuchAlgorithmException {
		String username=request.getParameter("username");
		String password = request.getParameter("pass");
		
		Map<String,Object> map = new HashMap<String,Object>();
		//Set<String> sensitiveWord = userService.getSensitiveWordSet();
		/*SensitiveWordUtil wordUtil = new SensitiveWordUtil();
		wordUtil.initSensitiveWordsMap(sensitiveWord);
		System.out.println(wordUtil.getSensitiveWords(username, MatchType.MAX_MATCH));*/
		map.put("username", username);
		map.put("pwdHash", PassUtil.digestString(password, "SHA"));
		map.put("password", password);
		userService.add(map);
		/*ModelAndView mv = new ModelAndView("redirect:/listUser?page=0");
		return mv;*/
		map.put("result", 1);
		return gson.toJson(map);
	}
	
	@RequestMapping("deleteUser")
	public String deleteUser(HttpServletRequest request) {
		String username=request.getParameter("username");
		userService.delete(username);
		/*ModelAndView mv = new ModelAndView("redirect:/listUser?page=0");
		return mv;*/
		Map<String,Object> map =new HashMap<>();
		map.put("result", 1);
		return gson.toJson(map);
	}
	
	@ResponseBody
	@RequestMapping("editUser")
	public String edituser(HttpServletRequest request) {
		String username = request.getParameter("username");
		User user=userService.getUserByUserid(username);
		/*ModelAndView mv = new ModelAndView();
		mv.addObject("user",user);
		mv.setViewName("editUser");
		return mv;*/
		Map<String,Object> map =new HashMap<>();
		map.put("user", user);
		return gson.toJson(map);
	}
	
	@ResponseBody
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
	@ResponseBody
	public String AdminLogin(HttpServletRequest request){
		String username=request.getParameter("username");
		String pass = request.getParameter("pass");
		request.getSession().setAttribute("username", username);
		System.out.print(username);
		User Auser = this.userService.getUserByUserid(username);
		String result ="";
		Map<String,Object> map = new HashMap<String,Object>();
		if(Auser==null)
			result = "00"; //没有该用户
		else {
			if(Auser.getPassword().equals(pass))
				result="1";  //登录
			else {
				result="01";  //密码错误
			}
		}
		map.put("result", result);
		System.out.print(map);
		return gson.toJson(map);
	}
	
}

