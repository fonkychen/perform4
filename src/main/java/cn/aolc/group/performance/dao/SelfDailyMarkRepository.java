package cn.aolc.group.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import cn.aolc.group.performance.jpa.SelfDailyMark;
import cn.aolc.group.performance.jpa.UserSelfMark;

public interface SelfDailyMarkRepository extends JpaRepository<SelfDailyMark, Long>{
	
	public List<SelfDailyMark> findByUserSelfMarkOrderByMarkCategoryAsc(UserSelfMark userSelfMark);

}
