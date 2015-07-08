package cn.aolc.group.performance.service.rest;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.aolc.group.performance.annotation.MessageAnnotation;
import cn.aolc.group.performance.annotation.UserCoinAdded;
import cn.aolc.group.performance.dao.tenant.DrumEventRepository;
import cn.aolc.group.performance.jpa.Company;
import cn.aolc.group.performance.jpa.enumeration.CoinType;
import cn.aolc.group.performance.jpa.enumeration.DrumStatusType;
import cn.aolc.group.performance.jpa.enumeration.MessageType;
import cn.aolc.group.performance.jpa.tenant.DrumEvent;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class DrumEventService extends BaseRestService{
	
	@Autowired
	private DrumEventRepository drumEventRepository;
	
	@UserCoinAdded(coinType = CoinType.DrumEvent)
	@MessageAnnotation(type=MessageType.DrumEventType)
	public DrumEvent saveDrum(String content) throws Exception{
		DrumEvent de=new DrumEvent();
		Calendar cal=Calendar.getInstance();
		de.setStatusType(DrumStatusType.Waiting);
		de.setEvent(content);
		de.setUpdated(new Date());
		de.setUser(getContextUser());
		de.setYearNum(cal.get(Calendar.YEAR));
		return drumEventRepository.save(de);
	}
	
	public DrumEvent getDrum(Long id) throws Exception{
		return drumEventRepository.findOne(id);
	}
	
	public Page<DrumEvent> getDrum(Company company,Integer page,Integer size,String order) throws Exception{
		if(company==null){
			company=getContextUser().getCompany();
		}
		Calendar cal=Calendar.getInstance();
		Pageable pageable=new PageRequest(page-1, size, order.equals("asc")?Direction.ASC: Direction.DESC, "updated");
		return drumEventRepository.findByYearNumAndTenantId(cal.get(Calendar.YEAR), company.getCode(), pageable);
	}

}
