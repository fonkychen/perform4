package cn.aolc.group.performance.weixin.api.bean;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ReportTemplateTask extends TemplateTask{
	private Data data;
	
	private String title;		
	
	private String description;
	
	public ReportTemplateTask(String touser,String title,String description,String url){
		super(touser,"GF7lcq6BFV1R-Sl-AFBfgyty-z0UqZ-uJVXT4hRlE5w");		
		this.title=title;
		this.description=description;
		setUrl(url);
		setData();
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
		difirst.setValue(getTitle());
		difirst.setColor("#173177");
		data.setFirst(difirst);
		
		DataItem dikeyword1=new DataItem();
		
		dikeyword1.setValue(getDescription());
		dikeyword1.setColor("#173177");
		data.setKeyword1(dikeyword1);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年M月d日 HH:mm");
		DataItem dikeyword2=new DataItem();
		dikeyword2.setValue(sdf.format(new Date()));
		dikeyword2.setColor("#173177");
		data.setKeyword2(dikeyword2);
		
		DataItem diremark=new DataItem();
		diremark.setValue("点击了解详情");
		diremark.setColor("#173177");
		data.setRemark(diremark);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}