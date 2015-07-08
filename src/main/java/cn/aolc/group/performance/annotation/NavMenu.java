package cn.aolc.group.performance.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.aolc.group.performance.jpa.enumeration.RoleType;
import cn.aolc.group.performance.site.menu.MenuCategory;

@Target(value=ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NavMenu {
	
	public MenuCategory category();
	
	public String value();
	
	public int rankWeight();
	
	public RoleType role(); 

}
