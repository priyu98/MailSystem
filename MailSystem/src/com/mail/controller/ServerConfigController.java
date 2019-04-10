package com.mail.controller;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping("listConfig")
	public ModelAndView listConfig() {
		Map map = serverConfig.getServerConfig();
		ModelAndView mv = new ModelAndView();
		mv.addObject("config",map);
		mv.setViewName("listConfig");
		return mv;
	}
	
	@RequestMapping("updateConfig")
	public ModelAndView updateConfig() throws UnknownHostException {
		Map map = new HashMap();
		map.put("server_ip", HttpUtil.getLocalIp());
		map.put("server_name", HttpUtil.getLocalName());
		serverConfig.updateServerIP(map);
		ModelAndView mv = new ModelAndView("redirect:/serverConfig/listConfig");
		return mv;
	}
}
