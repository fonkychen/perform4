package cn.aolc.group.performance.tenant;


import cn.aolc.group.performance.jpa.inter.EntityWithId;

public interface TenantEntity extends EntityWithId{
	
	public String getTenantId();
	
	public void setTenantId(String tenentId);

}
