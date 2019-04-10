package com.mail.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mail.service.ServerConfigService;
@Service
public class ServerConfigServiceImpl implements ServerConfigService {
	@Autowired
	DaoSupport dao;
	
	@Override
	public Map getServerConfig() {
		// TODO Auto-generated method stub
		return (Map)dao.findForObject("ServerConfigMapper.getServerConfig", null);
	}

	@Override
	public void updateServerIP(Map map) {
		// TODO Auto-generated method stub
		dao.update("ServerConfigMapper.updateServerIP", map);
	}

}
