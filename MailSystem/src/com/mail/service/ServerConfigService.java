package com.mail.service;

import java.util.Map;

public interface ServerConfigService {
	Map getServerConfig();
	void updateServerIP(Map map);
}
