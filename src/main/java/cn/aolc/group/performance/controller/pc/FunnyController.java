package cn.aolc.group.performance.controller.pc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.aolc.group.performance.jpa.enumeration.BalanceType;
import cn.aolc.group.performance.service.mallproduct.MallProductServiceUtil;
import cn.aolc.group.performance.service.rest.CountryService;
import cn.aolc.group.performance.service.rest.MallService;
import cn.aolc.group.performance.service.rest.UserWealthService;

@Controller
@RequestMapping("/funny")
@Transactional(propagation=Propagation.REQUIRED)
public class FunnyController {
	
	@Autowired
	private MallService mallService;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private UserWealthService userWealthService;
	
	@Autowired
	private MallProductServiceUtil mallProductServiceUtil;
	
	@RequestMapping("/mall/index")
	public void mall(Model model) throws Exception{
		model.addAttribute("products", mallService.getValidProduct());
		model.addAttribute("mallServices", mallProductServiceUtil.getService());
	}
	
	@RequestMapping(value={"/mall/product/{id}"})
	public void productWithId(Model model,@PathVariable Long id,@RequestParam(required=false) Integer page,@RequestParam(required=false) String order) throws Exception{
		if(id!=null){
			model.addAttribute("product", mallService.getProduct(id));
		}
		
		if(page==null){
			page=1;
		}
		if(order==null){
			order="asc";
		}
		
		model.addAttribute("page", mallService.getProducts(page, 9,order));
		model.addAttribute("order", order);
		model.addAttribute("mallServices", mallProductServiceUtil.getService());
	}
	
	@RequestMapping("/mall/product")
	public void product(Model model,@RequestParam(required=false) Integer page,@RequestParam(required=false) String order) throws Exception{
		if(page==null){
			page=1;
		}
		if(order==null){
			order="asc";
		}
		
		model.addAttribute("page", mallService.getProducts(page, 9,order));
		model.addAttribute("order", order);
		model.addAttribute("mallServices", mallProductServiceUtil.getService());
	}
	
	@RequestMapping("/mall/orderlist")
	public void orderlist(Model model,@RequestParam(required=false) Integer page,@RequestParam(required=false) String order) throws Exception{
		if(page==null){
			page=1;
		}
		if(order==null){
			order="desc";
		}
		
		model.addAttribute("page", mallService.getOrders(page, 20, order));
		model.addAttribute("order", order);		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/mall/checkorder")
	public void checkorder(Model model,@RequestParam(required=false) Integer page,@RequestParam(required=false) String order) throws Exception{
		if(page==null){
			page=1;
		}
		if(order==null){
			order="desc";
		}
		model.addAttribute("page", mallService.getCheckOrders(page, 20,order));
		model.addAttribute("order", "desc");		
	}
	
	@RequestMapping("/money/countrywealth")
	public void countrywealth(Model model) throws Exception{
		model.addAttribute("countries", countryService.getCountries("wealth", "desc"));
	}
	
	@RequestMapping("/money/wealth")
	public void wealth(Model model,@RequestParam(required=false) Integer page,@RequestParam(required=false) String order) throws Exception{
		if(page==null){
			page=1;
		}
		if(order==null){
			order="desc";
		}
		model.addAttribute("order", order);
		model.addAttribute("page", userWealthService.getCoinHistory(page, 20, order));
		model.addAttribute("dailyincome", userWealthService.getDailyCoinSum(null, null, null, null, BalanceType.Income));
		model.addAttribute("dailyoutcome", userWealthService.getDailyCoinSum(null, null, null, null, BalanceType.Outcome));
		model.addAttribute("yearlycoins", userWealthService.getYearlyCoinSum(null, null));
	}
	
	@RequestMapping("/money/editcountrywealth")
	public void editcountrywealth(Model model) throws Exception{
		model.addAttribute("countries", countryService.getCountries("id", "asc"));
	}
}
