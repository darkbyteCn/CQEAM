package com.sino.hn.todo.cxf.util;

import javax.xml.bind.JAXBElement;

import com.sino.hn.todo.cxf.beans.Close;
import com.sino.hn.todo.cxf.beans.ObjectFactory;
import com.sino.hn.todo.cxf.beans.Open;
import com.sino.sinoflow.todo.dto.OaTodoDTO;

/**
 * 
 * @系统名称: 
 * @功能描述: 类转换
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Dec 1, 2011
 */
public final class ConvertUtil {
	public static Open getOpenFromDTO(OaTodoDTO todoDTO) {
		ObjectFactory factory = new ObjectFactory();
		Open instance = factory.createOpen();
		JAXBElement<String> docId = factory.createOpenDocId(todoDTO.getDocId());
		instance.setDocId(docId);

		JAXBElement<String> sourceId = factory.createOpenSourceId(todoDTO
				.getSourceId());
		instance.setSourceId(sourceId);
		JAXBElement<String> userId = factory.createOpenUserId(todoDTO
				.getUserId());
		instance.setUserId(userId);
		JAXBElement<String> sysId = factory.createOpenSysId(todoDTO.getSysId());
		instance.setSysId(sysId);
		JAXBElement<String> startTime = factory.createOpenStartTime(todoDTO
				.getStartTime());
		instance.setStartTime(startTime);
		JAXBElement<String> workId = factory.createOpenWorkId(todoDTO
				.getWorkId());
		instance.setWorkId(workId);
		JAXBElement<String> title = factory.createOpenTitle(todoDTO.getTitle());
		instance.setTitle(title);
		JAXBElement<String> pri = factory.createOpenPri(todoDTO.getPri());
		instance.setPri(pri);
		JAXBElement<String> sender = factory.createOpenSender(todoDTO
				.getSender());
		instance.setSender(sender);

		JAXBElement<String> docType = factory.createOpenDocType(todoDTO
				.getDocType());
		instance.setDocType(docType);

		return instance;

	}

	public static Close getCloseFromDTO(OaTodoDTO todoDTO) {
		ObjectFactory factory = new ObjectFactory();
		Close instance = factory.createClose();

		JAXBElement<String> sourceId = factory.createCloseSourceId(todoDTO
				.getSourceId());
		instance.setSourceId(sourceId);
		JAXBElement<String> userId = factory.createCloseUserId(todoDTO
				.getUserId());
		instance.setUserId(userId);
		JAXBElement<String> sysId = factory
				.createCloseSysId(todoDTO.getSysId());
		instance.setSysId(sysId);
		JAXBElement<String> closeTime = factory.createCloseCloseTime(todoDTO
				.getCloseTime());
		instance.setCloseTime(closeTime);
		JAXBElement<String> docId = factory
				.createCloseDocId(todoDTO.getDocId());
		instance.setDocId(docId);
		JAXBElement<String> workId = factory.createCloseWorkId(todoDTO
				.getWorkId());
		instance.setWorkId(workId);

		return instance;

	}
}
