package cn.aolc.group.performance.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.aolc.group.performance.service.rest.MallService;

@RestController
@RequestMapping("/rest/mall")
public class MallRestController {
	
	@Autowired
	private MallService mallService;
	
	@RequestMapping("/order")
	@ResponseStatus(value=HttpStatus.OK)
	public void orderProduct(@RequestParam Long productId) throws Exception{
		mallService.orderProduct(productId);
	}
	
	@RequestMapping("/add")
	@ResponseStatus(value=HttpStatus.OK)
	public void addProduct(@RequestParam(required=false) Long id,@RequestParam(required=false) MultipartFile icon, @RequestParam String name, @RequestParam(required=false) String description,@RequestParam Long coinNum, @RequestParam Integer remainNum, @RequestParam Boolean isEntity,@RequestParam(required=false) String serviceName) throws Exception{
		mallService.addProduct(id,icon.getBytes(), name, description, coinNum, remainNum, isEntity, serviceName);
	}
	
	@RequestMapping("/edit")
	@ResponseStatus(value=HttpStatus.OK)
	public void editProduct(@RequestParam(required=false) Long id, @RequestParam String name, @RequestParam(required=false) String description,@RequestParam Long coinNum, @RequestParam Integer remainNum, @RequestParam Boolean isEntity,@RequestParam(required=false) String serviceName) throws Exception{
		mallService.addProduct(id,null, name, description, coinNum, remainNum, isEntity, serviceName);
	}
	
	@RequestMapping("/offproduct")
	@ResponseStatus(HttpStatus.OK)
	public void offproduct(@RequestParam Long id) throws Exception{
		mallService.offProduct(id);
	}
	
	@RequestMapping("/onproduct")
	@ResponseStatus(HttpStatus.OK)
	public void onproduct(@RequestParam Long id,@RequestParam Integer remainNum) throws Exception{
		mallService.onProduct(id, remainNum);
	}
	
	@RequestMapping("/order/check")
	@ResponseStatus(value=HttpStatus.OK)
	public void checkProduct(@RequestParam Long orderId) throws Exception{
		mallService.checkOrder(orderId);
	}

}
