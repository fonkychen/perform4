package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.jpa.OperationRecord;
import cn.aolc.group.performance.jpa.User;

@Transactional(propagation=Propagation.REQUIRED)
public interface OperationRecordRepository extends JpaRepository<OperationRecord, Long>{
	
	@Query("select op.yearNum,op.monthNum,op.dayNum,op.operationName,count(distinct op.user) from OperationRecord op  where  CONCAT(op.yearNum,'-',CASE WHEN op.monthNum <10 THEN '0' ELSE '' END,op.monthNum,'-',CASE WHEN op.dayNum <10 THEN '0' ELSE '' END,op.dayNum) between ?1 and ?2 and op.user in ?3 group by op.yearNum,op.monthNum,op.dayNum,op.operationName order by op.yearNum asc,op.monthNum asc,op.dayNum asc")
	public List<Object[]> countDistinctUserByUpdatedBetweenAndUserIn(String startDate,String endDate,List<User> users);

}
