package cn.aolc.group.performance.tenant;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.aolc.group.performance.security.UserPasswordDetails;

public class TenantSimpleRepository <T, ID extends Serializable>
extends SimpleJpaRepository<T, ID> {

	private EntityManager em;
	private Class<T> domainClass;
	private CrudMethodMetadata crudMethodMetadata;
	public TenantSimpleRepository(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.em=em;
		this.domainClass=domainClass;
	}
	
	@Transactional
	public <S extends T> S save(S entity) {
		if(entity instanceof TenantEntity){
			String tenantId=getTenantId();
			if(tenantId!=null){
				((TenantEntity)entity).setTenantId(tenantId);
			}
		
		}
		return super.save(entity);
	}
	
	protected TypedQuery<T> getQuery(Specification<T> spec, Sort sort) {

		//logger.info("TenantSimpleRepository getQuery");

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(getDomainClass());

		Root<T> root = applySpecificationToCriteria(spec, query);
		query.select(root);

		if (sort != null) {
			query.orderBy(toOrders(sort, root, builder));
		}

		TypedQuery<T> tq=applyRepositoryMethodMetadata(em.createQuery(query));
		
		return tq;
		
	}
	
	public void setRepositoryMethodMetadata(CrudMethodMetadata crudMethodMetadata) {
		this.crudMethodMetadata = crudMethodMetadata;
		super.setRepositoryMethodMetadata(crudMethodMetadata);
	}
	
	private <S> Root<T> applySpecificationToCriteria(Specification<T> spec, CriteriaQuery<S> query) {

		Assert.notNull(query);
		Root<T> root = query.from(getDomainClass());
		CriteriaBuilder builder = em.getCriteriaBuilder();
		Predicate tenantpre=(new TenantSpecification()).toPredicate(root, query, builder);
        if(tenantpre!=null){
        	//logger.info("add tenant predicate");
        	query=query.where(tenantpre);
        }
        

		if (spec == null) {
			return root;
		}

		
		Predicate predicate = spec.toPredicate(root, query, builder);
		
		
        
		if (predicate != null) {
			query=query.where(predicate);
		}

		return root;
	}

	private TypedQuery<T> applyRepositoryMethodMetadata(TypedQuery<T> query) {

		
		if (crudMethodMetadata == null) {
			return query;
		}

		LockModeType type = crudMethodMetadata.getLockModeType();
		TypedQuery<T> toReturn = type == null ? query : query.setLockMode(type);

		for (Entry<String, Object> hint : crudMethodMetadata.getQueryHints().entrySet()) {
			query.setHint(hint.getKey(), hint.getValue());
		}

		return toReturn;
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
