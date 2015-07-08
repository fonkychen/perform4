package cn.aolc.group.performance.service.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.aolc.group.performance.dao.CompanyRepository;
import cn.aolc.group.performance.jpa.Company;

@Service
public class CompanyService extends BaseRestService{
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public Company getById(Long id){
		return companyRepository.findOne(id);
	}

}
