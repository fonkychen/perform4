package cn.aolc.group.performance.controller.rest;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.WorkOvertime;
import cn.aolc.group.performance.jpa.enumeration.WorkOvertimeStatus;
import cn.aolc.group.performance.jpa.tenant.UserDailyRank;
import cn.aolc.group.performance.jpa.tenant.UserGroup;
import cn.aolc.group.performance.model.MessageModel;
import cn.aolc.group.performance.security.UserPasswordDetails;
import cn.aolc.group.performance.service.rest.TitleService;
import cn.aolc.group.performance.service.rest.UserPopularService;
import cn.aolc.group.performance.service.rest.UserRestService;
import cn.aolc.group.performance.service.rest.WorkStatusService;

@RestController
@RequestMapping("/rest/user")
public class UserRestController {
	
	@Autowired
	private UserRestService userRestService;
	
	@Autowired
	private WorkStatusService workStatusService;
	
	@Autowired
	private UserPopularService userPopularService;
	
	@Autowired
	private TitleService titleService;
	
	@RequestMapping("/rank")	
	public List<UserDailyRank> getHisRank() throws Exception{
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return userRestService.getDailyRank(null, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE), "score");
	}
	
	@RequestMapping("/levelup")
	@ResponseStatus(HttpStatus.OK)
	public void levelup(@RequestParam Long titleId) throws Exception{
		titleService.levelup(null, titleId);
	}
	
	@RequestMapping("/messages")
	public MessageModel messages() throws Exception{
		return userRestService.getMessages();
	}
	
	@RequestMapping("/ownusergroup")
	public List<UserGroup> getOwnUserGroup(@AuthenticationPrincipal UserPasswordDetails upd) throws Exception{
		User user=userRestService.getUserById(upd.getUser().getId());
		return user.getOwnerGroups();
	}
	
	@RequestMapping("/get")
	public User getUser(@AuthenticationPrincipal UserPasswordDetails upd) throws Exception{
		return userRestService.getUserById(upd.getUser().getId());
	}
	
	@RequestMapping("/hasGroup")
	public Boolean hasGroup(@AuthenticationPrincipal UserPasswordDetails upd,@RequestParam(required=false) Long userId) throws Exception{
		
		User user=null;
		if(userId!=null){
			user=userRestService.getUserById(userId);
		}
		else{
			user=userRestService.getUserById(upd.getUser().getId());
		}
		if(user!=null)return user.getUserGroup()!=null;
		return false;
	}
	
	@RequestMapping("/getWorkOvertime")
	public Page<WorkOvertime> getWorkOvertime(@RequestParam(required=false) Integer page) throws Exception{
		if(page==null){
			page=1;
		}
		return workStatusService.getOvertimeToConfirm(null, page);
	}
	
	@RequestMapping("/selectRandomUser")
	public User getRandomUser() throws Exception{
		return userRestService.selectRandomUser();
	}
	
	@RequestMapping("/list")
	public List<User> listUser() throws Exception{
		return userRestService.listUser(null);
	}
	
	@RequestMapping("/overtime/status")
	@ResponseStatus(value=HttpStatus.OK)
	public void setWorkOvertimeStatus(@RequestParam Long id,@RequestParam Integer enabled) throws Exception{
		WorkOvertimeStatus status=null;
		if(enabled.equals(1)){
			status=WorkOvertimeStatus.CONFIRMED;
		}
		else{
			status=WorkOvertimeStatus.REJECTED;
		}
		workStatusService.setOvertimeStatus(id, status);
	}
	
	@RequestMapping("/favor")
	@ResponseStatus(HttpStatus.OK)
	public void favor(@RequestParam Long userId,@RequestParam Integer type) throws Exception{
		userPopularService.favorUser(userId, type,null);		
	}
	
	@RequestMapping("/icon/{iconId}")
	@ResponseStatus(HttpStatus.OK)
	public void setIcon(@PathVariable Long iconId) throws Exception{
		userRestService.setIcon(null, iconId);
	}
	
	@RequestMapping("/nickname/set")
	@ResponseStatus(HttpStatus.OK)
	public void setNickName(@RequestParam String nickName) throws Exception{
		userRestService.setNickName(null, nickName);
	}
	
	@RequestMapping("/password/set")
	@ResponseStatus(HttpStatus.OK)
	public void setPassword(@RequestParam String oldpassword,@RequestParam String newpassword) throws Exception{
		userRestService.setPassword(null,oldpassword, newpassword);
	}
	
	@RequestMapping("/find")
	public List<User> find(@RequestParam String keyword) throws Exception{
		return userRestService.findUser(null, keyword);
	}

}
