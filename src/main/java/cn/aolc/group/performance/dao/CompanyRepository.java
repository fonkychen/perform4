package cn.aolc.group.performance.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.Company;

@Transactional(propagation=Propagation.REQUIRED)
public interface CompanyRepository extends JpaRepository<Company, Long>{

}
