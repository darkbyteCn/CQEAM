package com.sino.framework.security.bean;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sino.base.constant.web.WebConstant;
import com.sino.framework.security.dto.FilterConfigDTO;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public class FormDataProducer {

    private boolean dataCreated = false;
    private HttpServletRequest req = null;
    private String reDirectURL = "";
	private FilterConfigDTO filterConfig = null;

    public FormDataProducer(HttpServletRequest req, String reDirectURL) {
        super();
        this.req = req;
        this.reDirectURL = reDirectURL;
		filterConfig = SessionUtil.getFilterConfigDTO(req);
    }

    /**
     * 功能：记录会话过期时原来提交的各种数据。利于继续提交。
     */
    public void produceData(){
        if(!dataCreated){
            produceFrmData();
//			Message message = MessageManager.getMessage(MsgKeyConstant.SESSION_TIME_OUT);
//            req.setAttribute(MessageConstant.MESSAGE_DATA, message);
            dataCreated = true;
        }
    }

    /**
     * 功能：产生表单数据。
     */
    private void produceFrmData(){
        StringBuffer reqData = new StringBuffer();
        Map nameMap = req.getParameterMap();
        Iterator fieldNames = nameMap.keySet().iterator();
        String fieldName = "";
        String[] fieldValues = null;
        while(fieldNames.hasNext()){
            fieldName = String.valueOf(fieldNames.next());
            if(isSkipField(fieldName)){
                continue;
            }
            fieldValues = req.getParameterValues(fieldName);
            for(int i = 0; i < fieldValues.length; i++){
                reqData.append("<input type=\"hidden\" name=\"");
                reqData.append(fieldName);
                reqData.append("\" value=\"");
                reqData.append(fieldValues[i]);
                reqData.append("\">\n");
            }
        }
        reqData.append("<input type=\"hidden\" name=\"");
        reqData.append(WebConstant.REDIRECT_URL);
        reqData.append("\" value=\"");
        reqData.append(reDirectURL);
        reqData.append("\">\n");
        req.setAttribute(WebConstant.FORM_DATA, reqData.toString());
    }

	/**
	 * 功能：检查字段是否可以忽略不计，以免生成重复数据
	 * @param fieldName String
	 * @return boolean
	 */
	private boolean isSkipField(String fieldName){
        boolean isSkipField = false;
        if(fieldName.equals(filterConfig.getLoginName())){
            isSkipField = true;
        } else if(fieldName.equals(filterConfig.getLoginPwd())){
            isSkipField = true;
        } else if(fieldName.equals(WebConstant.REDIRECT_URL)){
            isSkipField = true;
        }
        return isSkipField;
    }
}
