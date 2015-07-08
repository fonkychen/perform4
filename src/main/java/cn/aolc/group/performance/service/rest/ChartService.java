package cn.aolc.group.performance.service.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.aolc.group.performance.dao.DailyBoardRepository;
import cn.aolc.group.performance.dao.IndicatorRepository;
import cn.aolc.group.performance.dao.MonthlyIndicatorRepository;
import cn.aolc.group.performance.dao.OperationRecordRepository;
import cn.aolc.group.performance.dao.ScoreHistoryRepository;
import cn.aolc.group.performance.dao.tenant.TaskTypeRepository;
import cn.aolc.group.performance.jpa.Indicator;
import cn.aolc.group.performance.jpa.MonthlyIndicator;
import cn.aolc.group.performance.jpa.User;
import cn.aolc.group.performance.jpa.enumeration.ScoreType;
import cn.aolc.group.performance.jpa.tenant.TaskType;
import cn.aolc.group.performance.model.ActivityChartItemModel;
import cn.aolc.group.performance.model.ActivityChartModel;
import cn.aolc.group.performance.model.BoardChartItemModel;
import cn.aolc.group.performance.model.BoardChartModel;
import cn.aolc.group.performance.model.IndicatorChartModel;
import cn.aolc.group.performance.model.ScoreChartItemModel;
import cn.aolc.group.performance.model.ScoreChartModel;

@Service
public class ChartService extends BaseRestService{
	@Autowired
	private ScoreHistoryRepository scoreHistoryRepository;
	
	@Autowired
	private DailyBoardRepository dailyBoardRepository;
	
	@Autowired
	private TaskTypeRepository taskTypeRepository;
	
	@Autowired
	private IndicatorRepository indicatorRepository;
	
	@Autowired
	private MonthlyIndicatorRepository monthlyIndicatorRepository;
	
	@Autowired
	private OperationRecordRepository operationRecordRepository;
	
	private static final String[] CHINESE_MONTH_CHARACTER={"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
	
	public static final String[] ACTIVITY_SHOW_LIST={"公事榜","每日自评","主管点评","有缘点评","签到","人气英雄","得胜鼓"};
	
	public ScoreChartModel calScoreByDay(List<User> users,Date startDate,Date endDate) throws Exception{
		if(startDate.after(endDate))throw new Exception("invalid date exception");
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");		
		List<Object[]> objslist=scoreHistoryRepository.findDailyGroupSumScoreByUserInAndScoreTypeAndDateBetween(users, sdf.format(startDate), sdf.format(endDate));;
		
		List<Object[]> objslist2=scoreHistoryRepository.findDailyGroupCountScoreByUserInAndScoreTypeAndDateBetween(users, sdf.format(startDate), sdf.format(endDate));		
		ScoreChartModel model=new ScoreChartModel();
		model.setStartDate(startDate);
		model.setEndDate(endDate);
		List<ScoreChartItemModel> itemlist=new ArrayList<ScoreChartItemModel>();		
		List<ScoreChartItemModel> pielist=new ArrayList<ScoreChartItemModel>();
		
		for(ScoreType st:ScoreType.values()){
			ScoreChartItemModel lineitem=new ScoreChartItemModel();
			ScoreChartItemModel pieitem=new ScoreChartItemModel();
			lineitem.setScoreType(st);
			pieitem.setScoreType(st);
			int showtype=0;
			if(st.equals(ScoreType.RANDOM_COMMENT_SCORE) || st.equals(ScoreType.RANDOM_PENALTY_SCORE) 
					|| st.equals(ScoreType.WEEKLY_COMMENT_SCORE) || st.equals(ScoreType.WEEKLY_PENALTY_SCORE)){
				showtype=1;
			}
			
			List<Object> linecategories=new ArrayList<Object>();
			List<Object> linevalues=new ArrayList<Object>();
			
			Calendar tmpcal1=Calendar.getInstance();
			tmpcal1.setTime(startDate);
			Calendar tmpcal2=Calendar.getInstance();
			tmpcal2.setTime(endDate);
			while(!tmpcal1.after(tmpcal2)){
				int yearm=tmpcal1.get(Calendar.YEAR);
				int monthm=tmpcal1.get(Calendar.MONTH)+1;
				int daym=tmpcal1.get(Calendar.DATE);
				
				int windex=tmpcal1.get(Calendar.DAY_OF_WEEK);
				
				String category=monthm+"-"+daym;
				int score=0;
				
				for(Object[] objs:objslist){
					
					if(((Number)objs[0]).intValue() == yearm
							&& ((Number)objs[1]).intValue()==monthm
							&& ((Number)objs[2]).intValue() == daym
							&& st.equals(objs[3])
							){
						if(objs[4]!=null)score=((Number)objs[4]).intValue();					
					    
					}
				}
				
				
				if(showtype==0){
					linecategories.add(category);
					linevalues.add(score);
				}
				else if(showtype==1 && windex==Calendar.FRIDAY){
					linecategories.add(category);
					linevalues.add(score);
				}				
				
				tmpcal1.add(Calendar.DATE, 1);
			}
			
		
			lineitem.setCategories(linecategories);
			lineitem.setValues(linevalues);
			itemlist.add(lineitem);
			
			List<Object> piecategories=new ArrayList<Object>();
			List<Object> pievalues=new ArrayList<Object>();
			for(Object[] objs:objslist2){//0:type 1:score 2:count
				if(!st.equals(objs[0])){
					continue;
				}
				piecategories.add(((Number)objs[1]).intValue());
				pievalues.add(((Number)objs[2]).intValue());
			}
			
			pieitem.setCategories(piecategories);
			pieitem.setValues(pievalues);
			pielist.add(pieitem);
		}
		
		
	
		
		model.setLineItems(itemlist);
		model.setPieItems(pielist);
	
		return model;
	}
	
	public BoardChartModel getBoardCount(List<User> users,Date startDate,Date endDate) throws Exception{
		BoardChartModel model=new BoardChartModel();
		model.setStartDate(startDate);
		model.setEndDate(endDate);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		List<Object[]> objslist=dailyBoardRepository.findGroupbyCountTaskTypeByUserInAndDateBetween(users, sdf.format(startDate), sdf.format(endDate));
		
		List<TaskType> taskTypes=taskTypeRepository.findAll();
		
		List<BoardChartItemModel> items=new ArrayList<BoardChartItemModel>();
		for(TaskType taskType:taskTypes){
			Calendar tmpcal1=Calendar.getInstance();
			tmpcal1.setTime(startDate);
			Calendar tmpcal2=Calendar.getInstance();
			tmpcal2.setTime(endDate);
			BoardChartItemModel item=new BoardChartItemModel();
			item.setTaskType(taskType);
			List<Object> categories=new ArrayList<Object>();
			List<Object> values=new ArrayList<Object>();
			int totalcount=0;
			while(!tmpcal1.after(tmpcal2)){
				int yearm=tmpcal1.get(Calendar.YEAR);
				int monthm=tmpcal1.get(Calendar.MONTH)+1;
				int daym=tmpcal1.get(Calendar.DATE);
				
				String category=monthm+"-"+daym;
				int count=0;
				
				for(Object[] objs:objslist){
					if(((Number)objs[0]).intValue() == yearm
							&& ((Number)objs[1]).intValue()==monthm
							&& ((Number)objs[2]).intValue() == daym
							&& taskType.equals(objs[3])
						){
							if(objs[4]!=null) count=((Number)objs[4]).intValue();
						}
				}
				
				
				categories.add(category);
				values.add(count);
				totalcount+=count;
				tmpcal1.add(Calendar.DATE, 1);
			}
			
			item.setCategories(categories);
			item.setValues(values);
			item.setTotalCount(totalcount);
			items.add(item);
		}
		
		model.setItems(items);
		
		return model;
		
	}
	
	public IndicatorChartModel getUserIndicator(User user) throws Exception{
		IndicatorChartModel model=new IndicatorChartModel();
		Calendar cal=Calendar.getInstance();
		List<Indicator> indicators=indicatorRepository.findByUserAndIsValidOrderByIdAsc(user, true);
		
		int totalweight=0;
		for(Indicator indi:indicators){
			totalweight+=indi.getWeight();
		}
		if(totalweight<=0)throw new Exception("invalid weight");
		int yearm=cal.get(Calendar.YEAR);
		//int monthm=cal.get(Calendar.MONTH)+1;
		List<MonthlyIndicator> milist=monthlyIndicatorRepository.findByYearNumAndIndicatorInOrderByMonthNumAsc(yearm, indicators);
		
		List<Object> categories=new ArrayList<Object>();
		
		List<Object> managerItems=new ArrayList<Object>();
		List<Object> selfItems=new ArrayList<Object>();
		List<Object> settledItems=new ArrayList<Object>();
		int i=1;
		while(i<=12){
		    String category=CHINESE_MONTH_CHARACTER[i-1];
			
			
			double managerValue=0d;
			double selfValue=0d;
			for(MonthlyIndicator mi:milist){
				if(!mi.getMonthNum().equals(i))continue;
				
				if(mi.getManagerProgress()!=null){
					managerValue+=mi.getManagerProgress()*mi.getIndicator().getWeight()/totalweight;
				}
				if(mi.getSelfProgress()!=null){
					selfValue+=mi.getSelfProgress()*mi.getIndicator().getWeight()/totalweight;
				}
				//break;				
			}
			categories.add(category);
			managerItems.add(Double.valueOf(managerValue).intValue());
			selfItems.add(Double.valueOf(selfValue).intValue());
			settledItems.add(Double.valueOf(managerValue*0.7+selfValue*0.3).intValue());
			i++;
		}
		model.setCategories(categories);
		model.setManagerItems(managerItems);
		model.setSelfItems(selfItems);
		model.setSettledItems(settledItems);
		return model;
	}
	
	public ActivityChartModel getUserActivity(List<User> users,Date startDate,Date endDate) throws Exception{
		ActivityChartModel model=new ActivityChartModel();
		model.setStartDate(startDate);
		model.setEndDate(endDate);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		List<Object[]> objslist=operationRecordRepository.countDistinctUserByUpdatedBetweenAndUserIn(sdf.format(startDate), sdf.format(endDate), users);
		double supposedpercount=users.size();
		
		List<ActivityChartItemModel> items=new ArrayList<ActivityChartItemModel>();
		for(String activity:ACTIVITY_SHOW_LIST){
			ActivityChartItemModel item=new ActivityChartItemModel();
			item.setName(activity);
			

			Calendar tmpcal1=Calendar.getInstance();
			tmpcal1.setTime(startDate);
			Calendar tmpcal2=Calendar.getInstance();
			tmpcal2.setTime(endDate);
			
			double actualcount=0d;
			double supposedcount=0d;
			List<Object> categories=new ArrayList<Object>();
			List<Object> values=new ArrayList<Object>();
			while(!tmpcal1.after(tmpcal2)){
				int yearm=tmpcal1.get(Calendar.YEAR);
				int monthm=tmpcal1.get(Calendar.MONTH)+1;
				int daym=tmpcal1.get(Calendar.DATE);
				
				int tcount=0;
				for(Object[] objs:objslist){
					if(objs[0].equals(yearm) && objs[1].equals(monthm) && objs[2].equals(daym) && objs[3].equals(activity)){
						tcount=((Number)objs[4]).intValue();
						break;
					}
				}
				categories.add(monthm+"-"+daym);
				values.add(tcount);
				actualcount=actualcount+tcount;
				supposedcount=supposedcount+supposedpercount;
				tmpcal1.add(Calendar.DATE, 1);
			}
			item.setActualCount(actualcount);
			item.setSupposedcount(supposedcount);
			item.setCategories(categories);
			item.setValues(values);
			if(supposedcount>0){
				item.setCountRate(actualcount/supposedcount*100);
			}
			
			items.add(item);
		}
		
		model.setItems(items);
		return model;
	}

}
