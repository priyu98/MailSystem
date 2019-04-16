package com.mail.controller;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mail.pojo.User;
import com.mail.service.ServerConfigService;
import com.mail.service.UserService;
import com.mail.util.HttpUtil;
import com.mail.util.PassUtil;

@Controller
@RequestMapping("serverConfig")
public class ServerConfigController {
	@Autowired
	ServerConfigService serverConfig;
	
	private Gson gson = new GsonBuilder().serializeNulls().create();
	
	@RequestMapping("listConfig")
	@ResponseBody
	public String listConfig(HttpServletRequest request) {
		System.out.println(request.getSession().getAttribute("username"));
		Map<String,Object> map = serverConfig.getServerConfig();
		/*ModelAndView mv = new ModelAndView();
		mv.addObject("config",map);
		mv.setViewName("main");*/
		return gson.toJson(map);
	}
	
	@RequestMapping("updateConfig")
	@ResponseBody
	public void updateConfig() throws UnknownHostException {
		Map<String,Object> map = new HashMap<>();
		map.put("server_ip", HttpUtil.getLocalIp());
		map.put("server_name", HttpUtil.getLocalName());
		serverConfig.updateServerIP(map);
		/*ModelAndView mv = new ModelAndView("redirect:/serverConfig/listConfig");
		return mv;*/
	}
}
