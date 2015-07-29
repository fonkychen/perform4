package cn.aolc.group.performance.controller.pc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.aolc.group.performance.jpa.AchieveMilestone;
import cn.aolc.group.performance.jpa.AchieveRecord;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.AchieveType;
import cn.aolc.group.performance.model.AchieveModel;
import cn.aolc.group.performance.service.rest.AchieveRestService;
import cn.aolc.group.performance.service.rest.TitleService;
import cn.aolc.group.performance.service.rest.UserRestService;

@Controller
@RequestMapping("/service")
public class ServiceController {
	
	@Autowired
	private AchieveRestService achieveRestService;
	
	@Autowired
	private TitleService titleService;
	
	@Autowired
	private UserRestService userRestService;
	
	@RequestMapping("/mycenter/achieve")
	public void achieve(Model model,@RequestParam(required=false) String achieveType) throws Exception{
		List<AchieveMilestone> amlist=null;
		if(achieveType!=null){
			AchieveType at=AchieveType.valueOf(achieveType);
			amlist=achieveRestService.getAchieveMilestones(at);
			model.addAttribute("cachievetype", at);
		}
		else{
			amlist=achieveRestService.getAchieveMilestones(null);
		}
		List<AchieveRecord> ars=achieveRestService.getAchieveRecords(null, null, null);
		List<AchieveModel> amodels=new ArrayList<AchieveModel>();
		for(AchieveMilestone cam:amlist){
			AchieveModel amodel=new AchieveModel();
			
			amodel.setAchieveName(cam.getAchieveType().toString());
			amodel.setAchieveType(cam.getAchieveType().name());
			amodel.setCoinnum(cam.getCoinNum());
			amodel.setDescription(cam.getDescription());
			amodel.setName(cam.getName());
			amodel.setNum(cam.getNum());
			AchieveRecord car=null;
			for(AchieveRecord ar:ars){
				if(ar.getAchieveType().equals(cam.getAchieveType())){
					car=ar;
					break;
				}
			}
			
			if(car!=null ){
				amodel.setRecord(car.getStatis());			
			}
			else{
				amodel.setRecord(0);
			}
			
			if(amodel.getRecord()>=amodel.getNum()){
				amodel.setRecord(amodel.getNum());
				amodel.setIsOn(true);
			}
			else{
				amodel.setIsOn(false);
			}
			
			amodels.add(amodel);
			
		}
		
		
		model.addAttribute("models", amodels);
		
		model.addAttribute("achievetypes", AchieveType.values());
	}
	
	@RequestMapping("/mycenter/titles")
	public void titles(Model model) throws Exception{
		model.addAttribute("titleGroups", titleService.getTitleGroups());
	}
	
	@RequestMapping("/mycenter/organization")
	public void organization(Model model) throws Exception{
		User tuser=userRestService.getTopUser(null);
		if(tuser!=null)	model.addAttribute("usergroups", tuser.getOwnerGroups());
	}
	
	@RequestMapping("/mycenter/pinfo")
	public void personalinfo() throws Exception{
		
	}
	
	@RequestMapping("/mycenter/headicon")
	public void headicon(Model model) throws Exception{
		model.addAttribute("userIconGroups", userRestService.getUserIconGroups());
	}

}
