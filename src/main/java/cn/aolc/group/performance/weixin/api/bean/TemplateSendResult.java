package cn.aolc.group.performance.weixin.api.bean;

import weixin.popular.bean.BaseResult;

public class TemplateSendResult extends BaseResult{
	
	private String template_id;

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

}
