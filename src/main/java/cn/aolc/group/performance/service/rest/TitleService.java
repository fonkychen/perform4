package cn.aolc.group.performance.service.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.dao.UserRepository;
import cn.aolc.group.performance.dao.tenant.TitleGroupRepository;
import cn.aolc.group.performance.dao.tenant.TitleRepository;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.tenant.Title;
import cn.aolc.group.performance.jpa.tenant.TitleGroup;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class TitleService extends BaseRestService{
	
	@Autowired
	private TitleRepository titleRepository;
	
	@Autowired
	private TitleGroupRepository titleGroupRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Title getNextTitle(User user) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		if(user==null || user.getTitle()==null || user.getTitle().getTitleGroup()==null){
			return null;
		}
		Title title=user.getTitle();
		TitleGroup tg=user.getTitle().getTitleGroup();
		List<Title> titles=titleRepository.findByTitleGroupAndRank(tg, title.getRank()+1);
		if(titles.size()>0){
			return titles.get(0);
		}
		else{
			return title;
		}
	}
	
	public List<TitleGroup> getTitleGroups() throws Exception{
		return titleGroupRepository.findAll();
	}
	
	//coin	
	public Title levelup(User user, Long titleId) throws Exception{
		if(user==null){
			user=getContextUser();
		}
		Title title=null;
		if(titleId!=null){
			title=titleRepository.findOne(titleId);
		}
		
		if(title==null){
			title=getNextTitle(user);
		}
		
		if(title.getBottom()>user.getUserScored()) throw new Exception("没有足够的功勋");
		user.setTitle(title);
		userRepository.save(user);
		return title;
	}

}
