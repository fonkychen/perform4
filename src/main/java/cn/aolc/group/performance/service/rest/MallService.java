package cn.aolc.group.performance.service.rest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.annotation.UserCoinAdded;
import cn.aolc.group.performance.dao.tenant.MallOrderRepository;
import cn.aolc.group.performance.dao.tenant.MallProductRepository;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.CoinType;
import cn.aolc.group.performance.jpa.enumeration.MallProductStatus;
import cn.aolc.group.performance.jpa.tenant.MallOrder;
import cn.aolc.group.performance.jpa.tenant.MallProduct;
import cn.aolc.group.performance.service.mallproduct.MallProductService;
import cn.aolc.group.performance.util.OperationDef;

@Service("mallService")
@Transactional(propagation=Propagation.REQUIRED)
public class MallService extends BaseRestService{
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private MallProductRepository mallProductRepository;
	
	@Autowired
	private MallOrderRepository mallOrderRepository;
	
	public MallProduct getProduct(Long productId) throws Exception{
		return mallProductRepository.findOne(productId);
	}
	
	@Secured({"ROLE_ADMIN"})
	@OperationDef("添加商品")
	public MallProduct addProduct(Long id,byte[] icon,String name,String description,Long coinNum,Integer remainNum,Boolean isEntity,String serviceName) throws Exception{
		MallProduct mp=null;
		if(id!=null){
			mp=mallProductRepository.findOne(id);
		}
		else{
			mp=new MallProduct();
		}
		
        if(icon!=null){
        	mp.setIcon(icon);
		}
        mp.setName(name);
        mp.setDescription(description);
        mp.setCoinNum(coinNum);
        mp.setRemainNum(remainNum);
        if(remainNum>0){
        	mp.setProductStatus(MallProductStatus.OnSale);
        }
        else{
        	mp.setProductStatus(MallProductStatus.OffSale);
        }
        
        mp.setUpdated(new Date());
        if(isEntity!=null && isEntity){
        	mp.setIsEntity(isEntity);
        }
        else{
        	mp.setIsEntity(false);
        }
        if(serviceName!=null)mp.setServiceName(serviceName);
        return mallProductRepository.save(mp);
        
       // updateUserNotifyService.updateUserNotify(NotifyType.NewMallProduct, Role.RoleEnum.ROLE_USER.getRole(), null, getPrincipal().getUser());
	}
	
	
	public List<MallProduct> getValidProduct() throws Exception{
		return mallProductRepository.findByProductStatusOrderByCoinNumDesc(MallProductStatus.OnSale);
	}
	
	public Page<MallProduct> getProducts(Integer page,Integer size,String order) throws Exception{
		Pageable pageable=new PageRequest(page-1,size,order.equals("asc")?Direction.ASC:Direction.DESC,"productStatus");
		return mallProductRepository.findAll(pageable);
	}
	
	@Secured({"ROLE_ADMIN"})
	public MallProduct offProduct(Long pid) throws Exception{
		MallProduct mp=mallProductRepository.findOne(pid);
		if(mp==null) throw new Exception("invalid product");
		mp.setProductStatus(MallProductStatus.OffSale);
		return mallProductRepository.save(mp);
	}
	
	@Secured({"ROLE_ADMIN"})
	public MallProduct onProduct(Long pid,Integer remainNum) throws Exception{
		MallProduct mp=mallProductRepository.findOne(pid);
		if(mp==null) throw new Exception("invalid product");
		if(remainNum<=0) throw new Exception("库存为0，请增加库存");
		mp.setProductStatus(MallProductStatus.OnSale);
		mp.setRemainNum(remainNum);
		return mallProductRepository.save(mp);
	}
	
	@Secured({"ROLE_ADMIN"})
	public MallProduct saveProduct(Long pid,Integer remainNum,Long coinNum) throws Exception{
		MallProduct mp=mallProductRepository.findOne(pid);
		if(mp==null)throw new Exception("invalid product");
		mp.setRemainNum(remainNum);
		mp.setCoinNum(coinNum);
		return mallProductRepository.save(mp);
	}
	
	@Secured({"ROLE_ADMIN"})
	public MallOrder checkOrder(Long orderId) throws Exception{
		MallOrder mo=mallOrderRepository.findOne(orderId);
		if(mo==null) throw new Exception("invalid order");
		mo.setChecked(true);
		mo.setCheckedTime(new Date());
		return mallOrderRepository.save(mo);
	}
	
	@OperationDef("兑换商品")
	@UserCoinAdded(coinType=CoinType.ShopMall)
	public synchronized MallOrder orderProduct(Long pid) throws Exception{
		MallProduct mp=mallProductRepository.findOne(pid);
		if(mp==null) throw new Exception("invalid product");
		if(mp.getRemainNum()<=0)throw new Exception("该产品已兑换完 ");
		User user=getContextUser();
		if(user.getUserCoins()<mp.getCoinNum()) throw new Exception("没有足够的UB");
		MallOrder mo=new MallOrder();
		mo.setChecked(false);
		mo.setCheckedTime(null);
		mo.setCoinNum(mp.getCoinNum());
		mo.setMallProduct(mp);
		mo.setOrderUser(user);
		mo.setUpdated(new Date());
		
		if(mp.getIsEntity()!=null && !mp.getIsEntity() && mp.getServiceName()!=null){
			MallProductService mps=null;
			try{
				mps=appContext.getBean(mp.getServiceName(), MallProductService.class);
			}catch(BeansException be){
				
			}
			if(mps!=null)mps.doService(user);
			mo.setChecked(true);
			mo.setCheckedTime(new Date());
		}
		
		mp.setRemainNum(mp.getRemainNum()-1);
		if(mp.getRemainNum()<=0){
			mp.setProductStatus(MallProductStatus.OffSale);
		}
		mallProductRepository.save(mp);
		
		return mallOrderRepository.save(mo);	
	
	}
	
	public Page<MallOrder> getOrders(Integer page,Integer size,String order) throws Exception{
		Pageable pageable=new PageRequest(page-1, size, order.equals("asc")?Direction.ASC:Direction.DESC, "updated");
		
		return mallOrderRepository.findAll(pageable);
	}
	
	public Page<MallOrder> getCheckOrders(Integer page,Integer size,String order) throws Exception{
		Sort sort1=new Sort(Direction.ASC, "checked");
		Sort sort2=new Sort(order.equals("asc")?Direction.ASC:Direction.DESC,"updated");
		Pageable pageable=new PageRequest(page-1, size, sort1.and(sort2));
		return mallOrderRepository.findAll(pageable);
	}

}
