package com.sino.ams.sampling.dao;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.sino.ams.sampling.bean.OrderXMLAssistant;
import com.sino.ams.sampling.constant.SamplingDicts;
import com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO;
import com.sino.ams.sampling.dto.AmsAssetsSamplingLineDTO;
import com.sino.ams.sampling.model.AmsAssetsSamplingHeaderModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ReflectException;
import com.sino.base.exception.XMLParseException;
import com.sino.base.log.Logger;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class SamplingOrderXMLProcessor {
	private Element root = null;
	private List children = null;
	private List headerIds = null;
	private Map orderHeaderMap = null;
	private Map orderLinesMap = null;

	private static Map orderMap = OrderXMLAssistant.getSamOrderMap();
	private static Map itemMap = OrderXMLAssistant.getUploadItemMap();
	private static String orderPrimaryKey = OrderXMLAssistant.getOrderPrimaryKey();
	private static String orderType = OrderXMLAssistant.getOrderType();
	private static String acceptOrder = SamplingDicts.ASS_SAM_PDA;

	private AmsAssetsSamplingHeaderDTO dto = null;
	private AmsAssetsSamplingHeaderModel sqlProducer = null;
	private SimpleQuery simp = null;

	public SamplingOrderXMLProcessor(Connection conn) {
		headerIds = new ArrayList();
		orderHeaderMap = new HashMap();
		orderLinesMap = new HashMap();

		dto = new AmsAssetsSamplingHeaderDTO();
		sqlProducer = new AmsAssetsSamplingHeaderModel(null, dto);
		simp = new SimpleQuery(null, conn);
	}

	/**
	 * 功能：解析上载的工单文件
	 * @param filePath String
	 * @throws XMLParseException
	 */
	public void parseXML(String filePath) throws XMLParseException {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(filePath);
			root = doc.getRootElement();
			children = root.getChildren();
			if (children != null) {
				Element ele = null;
				String tmpType = "";
				String orderNo = "";
				String headerId = "";
				for (int i = 0; i < children.size(); i++) {
					ele = (Element) children.get(i);
					tmpType = ele.getAttributeValue(orderType);
					if (!acceptOrder.equals(tmpType)) {
						continue;
					}
					orderNo = ele.getAttributeValue(orderPrimaryKey);
					if(StrUtil.isEmpty(orderNo)){
						continue;
					}
					headerId = produceHeaderId(orderNo);
					parseOrderProperty(headerId, orderNo, ele);
					parseOrderLine(headerId, ele);
				}
			}
		} catch (IOException ex) {
			Logger.logError(ex);
			throw new XMLParseException(ex);
		} catch (JDOMException ex) {
			Logger.logError(ex);
			throw new XMLParseException(ex);
		} catch (ReflectException ex) {
			ex.printLog();
			throw new XMLParseException(ex);
		} catch (QueryException ex) {
			ex.printLog();
			throw new XMLParseException(ex);
		}
	}

	/**
	 * 功能：根据TRANS_NO获取HEADER_ID
	 * @param orderNo String
	 * @return headerId 返回抽查工单ID
	 * @throws QueryException
	 */
	private String produceHeaderId(String orderNo) throws QueryException {
		String headerId = "";
		try {
			dto.setOrderNo(orderNo);
			sqlProducer.setDTOParameter(dto);
			SQLModel sqlModel = sqlProducer.getIdByOrderNoModel();
			simp.setSql(sqlModel);
			simp.executeQuery();
			if (simp.hasResult()) {
				headerId = simp.getFirstRow().getStrValue("HEADER_ID");
				headerIds.add(headerId);
			}
		} catch (ContainerException ex) {
			ex.printLog();
			throw new QueryException(ex);
		}
		return headerId;
	}

	/**
	 * 功能：解析工单文件属性到AmsAssetsSamplingHeaderDTO
	 * @param headerId String
	 * @param orderNo String
	 * @param ele Element
	 * @throws ReflectException
	 */
	private void parseOrderProperty(String headerId, String orderNo, Element ele) throws ReflectException {
		List attrs = ele.getAttributes();
		if (attrs != null && !attrs.isEmpty()) {
			Attribute attr = null;
			int attrCount = attrs.size();
			AmsAssetsSamplingHeaderDTO order = new AmsAssetsSamplingHeaderDTO();
			order.setHeaderId(headerId);
			order.setOrderNo(orderNo);
			String fieldName = "";
			String fieldValue = "";
			for (int i = 0; i < attrCount; i++) {
				attr = (Attribute) attrs.get(i);
				fieldName = attr.getName();
				fieldValue = attr.getValue();
				fieldName = (String) orderMap.get(fieldName);
				ReflectionUtil.setProperty(order, fieldName, fieldValue);
			}
			orderHeaderMap.put(order.getHeaderId(), order);
		}
	}

	/**
	 * 功能：解析工单文件到AmsAssetsSamplingLineDTO
	 * @param headerId String
	 * @param ele Element
	 * @throws ReflectException
	 */
	private void parseOrderLine(String headerId, Element ele) throws ReflectException {
		try {
			List lineChildren = ele.getChildren();
			if (!lineChildren.isEmpty()) {
				Element child = null;
				Attribute attr = null;
				int childCount = lineChildren.size();
				String fieldName = "";
				String fieldValue = "";
				DTOSet orderLines = new DTOSet();
				for (int i = 0; i < childCount; i++) {
					child = (Element) lineChildren.get(i);
					AmsAssetsSamplingLineDTO orderLine = new AmsAssetsSamplingLineDTO();
					List attrs = child.getAttributes();
					if (attrs != null && !attrs.isEmpty()) {
						for (int j = 0; j < attrs.size(); j++) {
							attr = (Attribute) attrs.get(j);
							fieldName = attr.getName();
							fieldName = (String) itemMap.get(fieldName);
							if (StrUtil.isEmpty(fieldName)) {
								continue;
							}
							fieldValue = attr.getValue();
							if (fieldName.equals("scanStatus")) {
								if (fieldValue.equals(SamplingDicts.SACN_YES)) {
									fieldValue = SamplingDicts.STATUS_YES;
								} else if (fieldValue.equals(SamplingDicts.SACN_NO)) {
									fieldValue = SamplingDicts.STATUS_NO;
								}
							}
							ReflectionUtil.setProperty(orderLine, fieldName, fieldValue);
						}
						orderLine.setHeaderId(headerId);
						orderLines.addDTO(orderLine);
					}
				}
				orderLinesMap.put(headerId, orderLines);
			}
		} catch (ReflectException ex) {
			ex.printLog();
		} catch (DTOException ex) {
			ex.printLog();
			throw new ReflectException(ex);
		}
	}

	/**
	 * 功能：获取工单头ID构成的列表
	 * @return List
	 */
	public List getHeaderIds() {
		return headerIds;
	}

	/**
	 * 功能：根据工单头ID，获取工单信息。
	 * @param headerId String
	 * @return AmsAssetsSamplingHeaderDTO
	 */
	public AmsAssetsSamplingHeaderDTO getSamplingOrder(String headerId) {
		AmsAssetsSamplingHeaderDTO order = new AmsAssetsSamplingHeaderDTO();
		if (orderHeaderMap.containsKey(headerId)) {
			order = (AmsAssetsSamplingHeaderDTO) orderHeaderMap.get(headerId);
		}
		return order;
	}

	/**
	 * 功能：根据工单头ID，获取扫描的资产信息
	 * @param headerId String
	 * @return DTOSet
	 */
	public DTOSet getOrderLines(String headerId) {
		DTOSet orderLines = new DTOSet();
		if (orderHeaderMap.containsKey(headerId)) {
			orderLines = (DTOSet) orderLinesMap.get(headerId);
		}
		return orderLines;
	}
}
