package cn.aolc.group.performance.sync;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.aolc.group.performance.dao.OperationRecordRepository;
import cn.aolc.group.performance.jpa.OperationRecord;
import cn.aolc.group.performance.jpa.User;

@Service
public class UpdateOperationService {
	
	@Autowired
	private OperationRecordRepository operationRecordRepository;
	
	@Async
	public void updateOperation(User user,String methodName,String operationName,String description) throws Exception{
		Calendar cal=Calendar.getInstance();
		OperationRecord or=new OperationRecord();
		or.setUser(user);
		or.setYearNum(cal.get(Calendar.YEAR));
		or.setMonthNum(cal.get(Calendar.MONTH)+1);
		or.setDayNum(cal.get(Calendar.DATE));
		or.setMethodName(methodName);
		or.setOperationName(operationName);
		if(description.length()>255){
			description=description.substring(0, 255);
		}
		or.setDescription(description);
		or.setUpdated(new Date());
		
		operationRecordRepository.save(or);
	}

}
