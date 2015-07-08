package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.Host;
@Transactional
public interface HostRepository extends JpaRepository<Host, Long>{
	
	public List<Host> findByName(String name);

}
                                                                                                                                                                                           