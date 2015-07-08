package cn.aolc.group.performance.weixin.api.bean;

public class SelfMarkTemplateTask extends TemplateTask{
	private Data data;
	
	public SelfMarkTemplateTask(String touser){
		super(touser,"c3kxG4AMWmEdBCEyV86-mpjJh6gN6ue8nr5xwYmT36w");		
	}
	
	public void setDate(Data data){
		this.data=data;
	}
	
	public Data getData(){
		return this.data;
	}
	
	public static class Data{
		private DataItem first;
		
		private DataItem keyword1;
		
		private DataItem keyword2;
		
		private DataItem remark;

		public DataItem getFirst() {
			return first;
		}

		public void setFirst(DataItem first) {
			this.first = first;
		}

		public DataItem getKeyword1() {
			return keyword1;
		}

		public void setKeyword1(DataItem keyword1) {
			this.keyword1 = keyword1;
		}

		public DataItem getKeyword2() {
			return keyword2;
		}

		public void setKeyword2(DataItem keyword2) {
			this.keyword2 = keyword2;
		}

		public DataItem getRemark() {
			return remark;
		}

		public void setRemark(DataItem remark) {
			this.remark = remark;
		}
		
	}

	@Override
	public void setData() {
        data=new Data();
		
		DataItem difirst=new DataItem();
		difirst.setValue("您好！您有新的任务代办");
		difirst.setColor("#173177");
		data.setFirst(difirst);
		
		DataItem dikeyword1=new DataItem();
		dikeyword1.setValue("每日自评未填写");
		dikeyword1.setColor("#173177");
		data.setKeyword1(dikeyword1);
		
		DataItem dikeyword2=new DataItem();
		dikeyword2.setValue("待办");
		dikeyword2.setColor("#173177");
		data.setKeyword2(dikeyword2);
		
		DataItem diremark=new DataItem();
		diremark.setValue("请在今晚12点之前处理");
		diremark.setColor("#173177");
		data.setRemark(diremark);
	}

}
