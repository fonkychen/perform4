package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.aolc.group.performance.jpa.ResetPassCode;

public interface ResetPassCodeRepository extends JpaRepository<ResetPassCode, Long>{
	
	public List<ResetPassCode> findByCode(String code);

}
