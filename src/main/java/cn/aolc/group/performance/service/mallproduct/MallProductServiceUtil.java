package cn.aolc.group.performance.service.mallproduct;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MallProductServiceUtil  implements ApplicationContextAware, InitializingBean{
	
		
	private List<MallProductService> services=new ArrayList<MallProductService>();
	
	private ApplicationContext applicationContext;
	
	public List<MallProductService> getService(){		
		return services;
	}

	public void afterPropertiesSet() throws Exception {
		String [] beans=applicationContext.getBeanNamesForType(MallProductService.class);
		
		for(String bean:beans){
			services.add(applicationContext.getBean(bean, MallProductService.class));
		}
		
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}

}
