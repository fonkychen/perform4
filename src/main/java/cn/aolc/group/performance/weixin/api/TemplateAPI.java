package cn.aolc.group.performance.weixin.api;

import java.nio.charset.Charset;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;

import cn.aolc.group.performance.weixin.api.bean.TemplateSendResult;
import cn.aolc.group.performance.weixin.api.bean.TemplateTask;
import weixin.popular.api.BaseAPI;
import weixin.popular.client.JsonResponseHandler;

public class TemplateAPI extends BaseAPI{
	
		
	public TemplateSendResult templateMessageSend(String access_token,TemplateTask task){
		String json="";
		try{
			json=objectMapper.writeValueAsString(task);
		}catch(Exception e){
			return null;
		}
		
		
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/cgi-bin/message/template/send")
				.addParameter("access_token", access_token)
				.setEntity(new StringEntity(json,Charset.forName("utf-8")))
				.build();
		return localHttpClient.execute(httpUriRequest, JsonResponseHandler.createResponseHandler(TemplateSendResult.class));
	}

}
