package cn.aolc.group.performance.controller.pc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.UserPopularAction;
import cn.aolc.group.performance.model.UserPopularScatter;
import cn.aolc.group.performance.service.rest.MallService;
import cn.aolc.group.performance.service.rest.UserPopularService;
import cn.aolc.group.performance.service.rest.UserRestService;

@Controller
@RequestMapping("/uspace")
public class UserSpaceController {
	
	@Autowired
	private UserRestService userRestService;
	
	@Autowired
	private UserPopularService userPopularService;
	
	@Autowired
	private MallService mallService;
	
	@RequestMapping("/{method}/{userId}")
	public void genericmethod(Model model,@PathVariable String method,@PathVariable Long userId) throws Exception{
		User cuser=userRestService.getUserById(userId);
		model.addAttribute("cuser", cuser);
		List<User> userlist=new ArrayList<User>();
		userlist.add(cuser);
		List<UserPopularScatter> pclist=userPopularService.getScatter(userlist, null);
		for(UserPopularScatter pc:pclist){
			if(pc.getPopularAction().equals(UserPopularAction.SendFlower))model.addAttribute("flower", pc.getCount());
			else if(pc.getPopularAction().equals(UserPopularAction.ThrowEgg)) model.addAttribute("egg", pc.getCount());
		}
					
		Method meth=getClass().getDeclaredMethod(method, Model.class,User.class);		
		meth.invoke(this, model,cuser);
	}
	
	@SuppressWarnings("unused")
	private void mallcheck(Model model,User user) throws Exception{
		model.addAttribute("mallorders", mallService.getOrders(user, 1, 20, "desc").getContent());
	}

}
