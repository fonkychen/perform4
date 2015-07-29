package cn.aolc.group.performance.weixin.api.bean;

public class UserCoinBonusTemplateTask extends TemplateTask{

	private Data data;
	private Long coinNum;
	public UserCoinBonusTemplateTask(String touser, Long coinNum) {
		
		super(touser, "YHgaCtM6T6piUH_1QqAGNPPXO8FvHnZH5p_cFo1hfxI");	
		this.coinNum=coinNum;
		setData();
	}
	public void setDate(Data data){
		this.data=data;
	}
	
	public Data getData(){
		return this.data;
	}

	public Long getCoinNum() {
		return coinNum;
	}
	public void setCoinNum(Long coinNum) {
		this.coinNum = coinNum;
	}
	@Override
	public void setData() {
		  data=new Data();
			
			DataItem difirst=new DataItem();
			difirst.setValue("KPI福利来了");
			difirst.setColor("#173177");
			data.setFirst(difirst);
			
			DataItem dikeyword1=new DataItem();
			dikeyword1.setValue("季度UB奖励");
			dikeyword1.setColor("#173177");
			data.setKeyword1(dikeyword1);
			
			DataItem dikeyword2=new DataItem();
			dikeyword2.setValue(coinNum+"UB");
			dikeyword2.setColor("#173177");
			data.setKeyword2(dikeyword2);
			
			DataItem diremark=new DataItem();
			diremark.setValue("继续加油!");
			diremark.setColor("#173177");
			data.setRemark(diremark);
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

}
