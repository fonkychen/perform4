package cn.aolc.group.performance.site;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.aolc.group.performance.annotation.NavMenu;
import cn.aolc.group.performance.jpa.enumeration.RoleType;
import cn.aolc.group.performance.site.menu.MenuCategory;
import cn.aolc.group.performance.util.LogUtil;

@Component("siteMapHandler")
public class SiteMapHandler implements ApplicationContextAware, InitializingBean,BeanFactoryAware {
	@Autowired
	private LogUtil logUtil;
	private ApplicationContext applicationContext;
	private BeanFactory factory;

	public void afterPropertiesSet() throws Exception {
//		 String[] beans=applicationContext.getBeanNamesForAnnotation(RequestMapping.class);
//		
//		for(String bean:beans){
//			
//			RequestMapping rm1=applicationContext.findAnnotationOnBean(bean, RequestMapping.class);
//			String classname=factory.getType(bean).getName();
//			int index$=classname.indexOf("$");
//			if(index$>0){
//				classname=classname.substring(0, index$);
//			}
//			Class cclass=Class.forName(classname);
//			RequestMapping mapping1=(RequestMapping) cclass.getAnnotation(RequestMapping.class);
//			String upart1=mapping1.value()[0];
//			Method[] methods=cclass.getDeclaredMethods();
//			for(Method method:methods){
//				if(method.isAnnotationPresent(RequestMapping.class) && method.isAnnotationPresent(NavMenu.class)){
//					
//					RequestMapping rm2=method.getAnnotation(RequestMapping.class);
//					if(rm2.value()==null) continue;
//					NavMenu menu=method.getAnnotation(NavMenu.class);
//					String upart2=rm2.value()[0];
//					
//					MenuCategory cate=menu.category();
//					int rankweight=menu.rankWeight();
//					RoleType role=menu.role();
//					String value=menu.value();
//					
//					logUtil.debug("url:"+upart1+upart2+".html" +" cate"+cate+" value:"+value);
//				}
//				
//				
//			}
//		}
		
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}

	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		this.factory=arg0;
	} 
}
