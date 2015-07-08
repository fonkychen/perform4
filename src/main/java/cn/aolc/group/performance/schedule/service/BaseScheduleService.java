package cn.aolc.group.performance.schedule.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("baseSchedule")
public class BaseScheduleService {
	
	@Value("${server.ismaster}")
	private int isMaster;

	protected boolean isMaster() {
		return isMaster>0;
	}

	

}
