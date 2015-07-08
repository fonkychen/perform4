package cn.aolc.group.performance.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.UserRandomComment;
import cn.aolc.group.performance.model.StaffDailyBoardItemModel;
import cn.aolc.group.performance.model.UserRandomModel;
import cn.aolc.group.performance.model.UserSelfMarkModel;
import cn.aolc.group.performance.service.rest.UserScoredService;

@RestController
@RequestMapping("/rest/scored")
public class UserScoredRestController {
	
	@Autowired
	private UserScoredService userScoredService;
	
	@RequestMapping("/comment/save")
	@ResponseStatus(value=HttpStatus.OK)
	public void savecomment(@RequestParam Long userId,
			@RequestParam Integer scored,@RequestParam String description)throws Exception{
		userScoredService.saveComment(userId, scored, description);
	}
	
	@RequestMapping(value="/selfmark/save",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void saveselfmark(@RequestBody(required=true) UserSelfMarkModel markModel) throws Exception{
		userScoredService.saveSelfMark(markModel);
	}
	
	@RequestMapping(value="/dailyboard/save",method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void savedailyboard(@RequestBody StaffDailyBoardItemModel model) throws Exception{
		userScoredService.saveDailyBoard(model);
	}
	
	@RequestMapping(value="/dailyboard/savegroup",method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void savegroupdailyboard(@RequestBody List<StaffDailyBoardItemModel> models) throws Exception{
		for(StaffDailyBoardItemModel model:models){
			userScoredService.saveDailyBoard(model);
		}
	}
 	
	@RequestMapping("/random/get")
    public Page<UserRandomComment> getrandom(@RequestParam(required=false) Integer page) throws Exception{
    	if(page==null){
    		page=1;
    	}    	
    	return userScoredService.getRandom(null, null, page, 10);
    }
	
	@RequestMapping("/random/save")
	@ResponseStatus(value=HttpStatus.OK)
	public void saverandom(@RequestBody UserRandomModel urmodel) throws Exception{
		userScoredService.saveRandom(urmodel);
	}

}
