package cn.aolc.group.performance.jpa.inter;

import java.io.Serializable;

public interface EntityWithId extends Serializable{
	
	public void setId(Long id);
	
	public Long getId();

}
