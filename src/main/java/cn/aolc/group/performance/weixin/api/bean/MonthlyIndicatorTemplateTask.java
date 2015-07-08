package cn.aolc.group.performance.weixin.api.bean;

public class MonthlyIndicatorTemplateTask extends TemplateTask{
	
	private Data data;

	public MonthlyIndicatorTemplateTask(String touser) {
		super(touser,"S0QBqik4n-iM3ZjIzWQWkgbSnWsM4sYol3nHD7M9g4Y");		
	}

	@Override
	public void setData() {
        data=new Data();
		
		DataItem difirst=new DataItem();
		difirst.setValue("您好！一个月又快结束了");
		difirst.setColor("#173177");
		data.setFirst(difirst);
		
		DataItem dikeyword1=new DataItem();
		dikeyword1.setValue("回顾一下本月员工的指标有没有达成吧！");
		dikeyword1.setColor("#173177");
		data.setSchedule(dikeyword1);
		
		DataItem dikeyword2=new DataItem();
		dikeyword2.setValue("月底之前");
		dikeyword2.setColor("#173177");
		data.setTime(dikeyword2);
		
		DataItem diremark=new DataItem();
		diremark.setValue("请及时处理");
		diremark.setColor("#173177");
		data.setRemark(diremark);
	}
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
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
