package cn.aolc.group.performance.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.aolc.group.performance.jpa.AttendEvent;
import cn.aolc.group.performance.jpa.LuckyTable;
import cn.aolc.group.performance.jpa.MonthlyCountryWealth;
import cn.aolc.group.performance.service.rest.CountryService;
import cn.aolc.group.performance.service.rest.UserWealthService;

@RestController
@RequestMapping("/rest/wealth")
@Transactional(propagation=Propagation.REQUIRED)
public class UserWealthRestController {
	
	private final static Logger logger=LoggerFactory.getLogger(UserWealthRestController.class);
	
	@Autowired
	private UserWealthService userWealthService;
	
	@Autowired
	private CountryService countryService;
	
	@RequestMapping("/attend/request")
	public AttendEvent attend(@RequestParam Boolean isfix) throws Exception{
		AttendEvent ae=userWealthService.attend(null, isfix);
		return ae;
	}
	
	@RequestMapping("/country/monthly")
	public MonthlyCountryWealth getMonthlyCountryWealth(@RequestParam Long countryId,@RequestParam(required=false) Integer yearNum,@RequestParam(required=false) Integer monthNum) throws Exception{
		return countryService.getMonthlyWealth(countryId, yearNum, monthNum);
	}
	
	@RequestMapping("/lottery/request")
	public Long lottery() throws Exception{
		LuckyTable lt=userWealthService.lottery(null);
		return lt.getCoinNum();
	}
	
	@RequestMapping("/country/savewealth")
	@ResponseStatus(value=HttpStatus.OK)
	public void saveMonthlyCountryWealth(@RequestParam Long wealthId,@RequestParam Double outcome,@RequestParam(required=false) String description) throws Exception{
		countryService.saveCountryWealth(wealthId, outcome, description);
	}
	
	
}
