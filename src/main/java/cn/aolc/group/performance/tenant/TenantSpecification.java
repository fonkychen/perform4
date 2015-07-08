package cn.aolc.group.performance.tenant;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import cn.aolc.group.performance.security.UserPasswordDetails; 

public class TenantSpecification<T> implements Specification<T>{
	

	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
	
		String tenantId=getTenantId();
		if(tenantId!=null){			
			Path<String> tenant=root.get("tenantId");			
			return cb.equal(tenant, tenantId);
		}
		
		return null;
	}
	
	private String getTenantId(){
		if(SecurityContextHolder.getContext().getAuthentication()==null) return null;
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal!=null && (principal instanceof UserPasswordDetails)){
			return ((UserPasswordDetails)principal).getUser().getCompany().getCode();
		}
		return null;
	}

}
