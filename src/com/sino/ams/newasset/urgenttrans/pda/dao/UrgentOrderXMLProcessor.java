package com.sino.ams.newasset.urgenttrans.pda.dao;

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
import com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO;
import com.sino.ams.newasset.urgenttrans.dto.UrgentLineDTO;
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

public class UrgentOrderXMLProcessor {
	private Connection conn = null;
	private Element root = null;
	private List children = null;
	
	private List<String> headerIds = null;
	private Map<String,UrgentHeaderDTO> headerMap = null;  
	private Map<String,DTOSet> linesMap = null;
	private List orderTypes = null;

	private static Map<String,String> orderMap =  UrgentOrderXMLAssistant.getOrderMap(); 
	private static Map<String,String> itemMap =   UrgentOrderXMLAssistant.getItemMap(); 
	private static String orderPrimaryKey = UrgentOrderXMLAssistant.getOrderPrimaryKey();
	private static String orderType = UrgentOrderXMLAssistant.getOrderType();


	public UrgentOrderXMLProcessor(Connection conn) {
		super();
		this.conn = conn;
		headerIds = new ArrayList<String>();
		headerMap = new HashMap<String,UrgentHeaderDTO>();
		linesMap = new HashMap<String,DTOSet>();
		this.orderTypes = UrgentOrderXMLAssistant.getOrderTypes();
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
					parseAttr2Header(headerId, transNo, ele);
					parseChildren2Line(headerId, ele);
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
	@SuppressWarnings("unchecked")
	private String produceHeaderId(String transNo) throws QueryException {
		String headerId = "";
		try {
			SQLModel sqlModel = new SQLModel();
			List  sqlArgs = new ArrayList();
			String sqlStr = "SELECT"
							+ " TRANS_ID "
							+ " FROM"
							+ " AMS_ASSETS_TRANS_HEADER AATH"
							+ " WHERE"
							+ " AATH.TRANS_NO = ?";
			sqlArgs.add(transNo);
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
			simp.executeQuery();
			if (simp.hasResult()) {
				headerId = simp.getFirstRow().getStrValue("TRANS_ID");
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
	private void parseAttr2Header(String headerId, String transNo,
									Element ele) throws ReflectException {
		List<Attribute> attrs = ele.getAttributes();
		if (attrs != null && !attrs.isEmpty()) {
			Attribute attr = null;
			int attrCount = attrs.size();
			UrgentHeaderDTO chkOrder = new UrgentHeaderDTO();
			chkOrder.setTransId(headerId);
			chkOrder.setTransNo(transNo);
			String fieldName = "";
			String fieldValue = "";
			for (int i = 0; i < attrCount; i++) {
				attr = attrs.get(i);
				fieldName = attr.getName();
				fieldValue = attr.getValue();
				fieldName = (String) orderMap.get(fieldName);
				ReflectionUtil.setProperty(chkOrder, fieldName, fieldValue);
			}
			headerMap.put(chkOrder.getTransId(), chkOrder);
		}
	}

	/**
	 * 功能：解析工单文件到AmsAssetsCheckLineDTO
	 * @param headerId String
	 * @param ele Element
	 * @throws ReflectException
	 */
	private void parseChildren2Line(String headerId, Element ele) throws
			ReflectException {
		try {
			List<Element> lineChildren = ele.getChildren();
			if (!lineChildren.isEmpty()) {
				Element child = null;
				Attribute attr = null;
				int childCount = lineChildren.size();
				String fieldName = "";
				String fieldValue = "";
				DTOSet chkLines = new DTOSet();
				for (int i = 0; i < childCount; i++) {
					child = lineChildren.get(i);
					UrgentLineDTO line = new UrgentLineDTO();
					List<Attribute> attrs = child.getAttributes();
					if (attrs != null && !attrs.isEmpty()) {
						for (int j = 0; j < attrs.size(); j++) {
							attr = attrs.get(j);
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
							ReflectionUtil.setProperty(line, fieldName,
									fieldValue);
						}
						line.setTransId(headerId);
						chkLines.addDTO(line);
					}
				}
				linesMap.put(headerId, chkLines);
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
	public List<String> getHeaderIds() {
		return headerIds;
	}

	/**
	 * 功能：根据工单头ID，获取工单信息。
	 * @param transNo String
	 * @return AmsAssetsCheckHeaderDTO
	 */
	public UrgentHeaderDTO getHeader(String transNo) {
		UrgentHeaderDTO header = new UrgentHeaderDTO();
		if (headerMap.containsKey(transNo)) {
			header = (UrgentHeaderDTO) headerMap.get(transNo);
		}
		return header;
	}

	/**
	 * 功能：根据工单头ID，获取扫描的资产信息
	 * @param transNo String
	 * @return DTOSet
	 */
	public DTOSet getLines(String transNo) {
		DTOSet lines = new DTOSet();
		if (linesMap.containsKey(transNo)) {
			lines = (DTOSet) linesMap.get(transNo);
		}
		return lines;
	}
}
