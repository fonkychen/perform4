package cn.aolc.group.performance.weixin.api.bean;


public abstract class TemplateTask {
	

	private String touser;
	
	private String template_id;
	
	private String url;
	
	private String topcolor;
	
	
	public TemplateTask(String touser,String templateId){
		this.touser=touser;
		this.template_id=templateId;//"S0QBqik4n-iM3ZjIzWQWkgbSnWsM4sYol3nHD7M9g4Y";
		
		this.url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62d58ab131592fdc&redirect_uri=http%3A%2F%2Fwww.kpi365.com%2Fwechat%2Fselfmark-detail.html&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
		this.topcolor="#173177";
		setData();		
	}
	
	public abstract void setData();
	
	
	
	public static class DataItem{
		private String value;
		
		private String color;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
		
		
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

}
