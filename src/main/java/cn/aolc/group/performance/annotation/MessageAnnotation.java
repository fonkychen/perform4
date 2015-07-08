package cn.aolc.group.performance.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.aolc.group.performance.jpa.enumeration.MessageType;

@Target(value=ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageAnnotation {	
	public MessageType[] type();
}
