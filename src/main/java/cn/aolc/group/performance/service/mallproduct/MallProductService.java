package cn.aolc.group.performance.service.mallproduct;

import cn.aolc.group.performance.jpa.User;

public interface MallProductService {
	

	public void doService(User user);
	
	public String getName();
	
	public String getServiceName();

}
