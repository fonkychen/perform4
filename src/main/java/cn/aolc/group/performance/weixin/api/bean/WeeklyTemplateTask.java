package cn.aolc.group.performance.weixin.api.bean;


public class WeeklyTemplateTask extends TemplateTask{
	

	
	private Data data;
	
	public WeeklyTemplateTask(String touser){
		super(touser,"S0QBqik4n-iM3ZjIzWQWkgbSnWsM4sYol3nHD7M9g4Y");		
	
	}
	
	public void setData(Data data){
		this.data=data;
	}
	
	public Data getData(){
		return this.data;
	}
	
	public void setData(){
		
		data=new Data();
		
		DataItem difirst=new DataItem();
		difirst.setValue("您好！又到了周末点评的时候了");
		difirst.setColor("#173177");
		data.setFirst(difirst);
		
		DataItem dikeyword1=new DataItem();
		dikeyword1.setValue("这周员工表现怎么样？给个评价吧！");
		dikeyword1.setColor("#173177");
		data.setSchedule(dikeyword1);
		
		DataItem dikeyword2=new DataItem();
		dikeyword2.setValue("周日12点之前");
		dikeyword2.setColor("#173177");
		data.setTime(dikeyword2);
		
		DataItem diremark=new DataItem();
		diremark.setValue("点击这里马上周评");
		diremark.setColor("#173177");
		data.setRemark(diremark);
		
	}
	
	public static class Data{
		private DataItem first;
		
		private DataItem schedule;
		
		private DataItem time;
		
		private DataItem remark;

		public DataItem getFirst() {
			return first;
		}

		public void setFirst(DataItem first) {
			this.first = first;
		}

		
		public DataItem getRemark() {
			return remark;
		}

		public void setRemark(DataItem remark) {
			this.remark = remark;
		}

		public DataItem getSchedule() {
			return schedule;
		}

		public void setSchedule(DataItem schedule) {
			this.schedule = schedule;
		}

		public DataItem getTime() {
			return time;
		}

		public void setTime(DataItem time) {
			this.time = time;
		}
		
	}
	
	

}
