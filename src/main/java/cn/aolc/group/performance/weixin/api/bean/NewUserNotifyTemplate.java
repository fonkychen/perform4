package cn.aolc.group.performance.weixin.api.bean;

import cn.aolc.group.performance.jpa.User;

public class NewUserNotifyTemplate extends TemplateTask{
	private User user;
	private Data data;

	public NewUserNotifyTemplate(String touser,User user) {
		super(touser, "C3ed3WnBLovusR0QuzR5y2Qxmf1cbcmdnyTwemfRlAw");
		this.setUser(user);
		setData();
	}

	@Override
	public void setData() {
		if(getUser()==null)return;
		data=new Data();
		DataItem difirst=new DataItem();
		difirst.setValue("新成员加入通知");
		difirst.setColor("#173177");
		data.setFirst(difirst);
			
		DataItem dikeyword1=new DataItem();
		dikeyword1.setValue(getUser().getWorkerId());
		dikeyword1.setColor("#173177");
		data.setKeyword1(dikeyword1);
			
		DataItem dikeyword2=new DataItem();
		dikeyword2.setValue(getUser().getName());
		dikeyword2.setColor("#173177");
		data.setKeyword2(dikeyword2);
			
		DataItem diremark=new DataItem();
		diremark.setValue("欢迎"+getUser().getName()+"加入缔安市场部，职位："+getUser().getPosition().getName()+"，隶属"+(getUser().getCountry()!=null?getUser().getCountry().getName():"")+"。KPI365是充满乐趣的互动社区，将全程陪伴您在缔安的工作时光，预祝"+getUser().getName()+"使用愉快！");
		diremark.setColor("#173177");
		data.setRemark(diremark);
			
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
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
