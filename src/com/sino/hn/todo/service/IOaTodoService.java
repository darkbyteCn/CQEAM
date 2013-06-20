package com.sino.hn.todo.service;

import com.sino.hn.todo.dto.OaResponseDTO;
import com.sino.sinoflow.todo.dto.OaTodoDTO;

/**
 * 
 * 
 * @系统名称:  
 * @功能描述: 单个推送
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sangjun
 * @创建时间: Dec 3, 2011
 */
public interface IOaTodoService {
	public void clear();

	public boolean saveSender(OaTodoDTO todoDTO);
	
	public OaResponseDTO getResponseDTO();
}
