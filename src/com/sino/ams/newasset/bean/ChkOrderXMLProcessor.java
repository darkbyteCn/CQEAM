package com.sino.ams.newasset.bean;

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

import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
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
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ChkOrderXMLProcessor {
	private Connection conn = null;
	private Element root = null;
	private List children = null;
	private List headerIds = null;
	private Map chkOrderMap = null;
	private Map chkLinesMap = null;
	private List orderTypes = null;

	private static Map orderMap = OrderXMLAssistant.getChkOrderMap();
	private static Map itemMap = OrderXMLAssistant.getChkItemMap();
	private static String orderPrimaryKey = OrderXMLAssistant.
											getOrderPrimaryKey();
	private static String orderType = OrderXMLAssistant.getOrderType();


	public ChkOrderXMLProcessor(Connection conn) {
		super();
		this.conn = conn;
		headerIds = new ArrayList();
		chkOrderMap = new HashMap();
		chkLinesMap = new HashMap();
		this.orderTypes = OrderXMLAssistant.getOrderTypes();
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
				String transNo = "";
				String headerId = "";
				for (int i = 0; i < children.size(); i++) {
					ele = (Element) children.get(i);
					tmpType = ele.getAttributeValue(orderType);
					if (!orderTypes.contains(tmpType)) {
						continue;
					}
					transNo = ele.getAttributeValue(orderPrimaryKey);
					if(StrUtil.isEmpty(transNo)){
						continue;
					}
					headerId = produceHeaderId(transNo);
					parseAttr2ChkOrder(headerId, transNo, ele);
					parseChildren2ChkLine(headerId, ele);
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
	 * @param transNo String
	 * @throws QueryException
	 * @return headerId 返回工单头ID
	 */
	private String produceHeaderId(String transNo) throws QueryException {
		String headerId = "";
		try {
			SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT"
							+ " HEADER_ID"
							+ " FROM"
							+ " AMS_ASSETS_CHECK_HEADER AACH"
							+ " WHERE"
							+ " AACH.TRANS_NO = ?";
			sqlArgs.add(transNo);
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
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
	 * 功能：解析工单文件属性到AmsAssetsCheckLineDTO
	 * @param headerId String
	 * @param transNo String
	 * @param ele Element
	 * @throws ReflectException
	 */
	private void parseAttr2ChkOrder(String headerId, String transNo,
									Element ele) throws ReflectException {
		List attrs = ele.getAttributes();
		if (attrs != null && !attrs.isEmpty()) {
			Attribute attr = null;
			int attrCount = attrs.size();
			AmsAssetsCheckHeaderDTO chkOrder = new AmsAssetsCheckHeaderDTO();
			chkOrder.setHeaderId(headerId);
			chkOrder.setTransNo(transNo);
			String fieldName = "";
			String fieldValue = "";
			for (int i = 0; i < attrCount; i++) {
				attr = (Attribute) attrs.get(i);
				fieldName = attr.getName();
				fieldValue = attr.getValue();
				fieldName = (String) orderMap.get(fieldName);
				ReflectionUtil.setProperty(chkOrder, fieldName, fieldValue);
			}
			chkOrderMap.put(chkOrder.getHeaderId(), chkOrder);
		}
	}

	/**
	 * 功能：解析工单文件到AmsAssetsCheckLineDTO
	 * @param headerId String
	 * @param ele Element
	 * @throws ReflectException
	 */
	private void parseChildren2ChkLine(String headerId, Element ele) throws
			ReflectException {
		try {
			List lineChildren = ele.getChildren();
			if (!lineChildren.isEmpty()) {
				Element child = null;
				Attribute attr = null;
				int childCount = lineChildren.size();
				String fieldName = "";
				String fieldValue = "";
				DTOSet chkLines = new DTOSet();
				for (int i = 0; i < childCount; i++) {
					child = (Element) lineChildren.get(i);
					AmsAssetsCheckLineDTO chkLine = new AmsAssetsCheckLineDTO();
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
								if (fieldValue.equals(AssetsDictConstant.
										SACN_YES)) {
									fieldValue = AssetsDictConstant.STATUS_YES;
								} else if (fieldValue.equals(AssetsDictConstant.
										SACN_NO)) {
									fieldValue = AssetsDictConstant.STATUS_NO;
								}
							}
							ReflectionUtil.setProperty(chkLine, fieldName,
									fieldValue);
						}
						chkLine.setHeaderId(headerId);
						chkLines.addDTO(chkLine);
					}
				}
				chkLinesMap.put(headerId, chkLines);
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
	 * @param transNo String
	 * @return AmsAssetsCheckHeaderDTO
	 */
	public AmsAssetsCheckHeaderDTO getChkOrder(String transNo) {
		AmsAssetsCheckHeaderDTO chkOrder = new AmsAssetsCheckHeaderDTO();
		if (chkOrderMap.containsKey(transNo)) {
			chkOrder = (AmsAssetsCheckHeaderDTO) chkOrderMap.get(transNo);
		}
		return chkOrder;
	}

	/**
	 * 功能：根据工单头ID，获取扫描的资产信息
	 * @param transNo String
	 * @return DTOSet
	 */
	public DTOSet getChkLines(String transNo) {
		DTOSet chkLines = new DTOSet();
		if (chkOrderMap.containsKey(transNo)) {
			chkLines = (DTOSet) chkLinesMap.get(transNo);
		}
		return chkLines;
	}
}
