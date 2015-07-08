package cn.aolc.group.performance.util.log;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("logAnnotationHandler")
public class LogAnnotationHandler implements ApplicationContextAware,InitializingBean {
	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}

	public void afterPropertiesSet() throws Exception {
		//applicationContext.
	}

}
