package com.sino.ams.newasset.urgenttrans.pda.dao;

import java.sql.Connection;
import java.util.Iterator;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.ams.newasset.urgenttrans.constant.UrgentAppConstant;
import com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO;
import com.sino.ams.newasset.urgenttrans.dto.UrgentLineDTO;
import com.sino.ams.newasset.urgenttrans.pda.model.UrgentDownloadModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ReflectException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.pda.dao.OrderDownLoadInterface;

public class UrgentOrderDownloadDAO extends AMSBaseDAO  implements OrderDownLoadInterface{
	private StringBuffer orderXml = null;
	UrgentHeaderDTO headerDTO = null;
	UrgentLineDTO lineDTO = null;
	
	UrgentDownloadModel leaseModel = null;
	private static Map<String,String> orderMap = UrgentOrderXMLAssistant.getOrderMap();
    private static Map<String,String> itemMap = UrgentOrderXMLAssistant.getItemMap();
    
    private int type = 0 ; //鉴别调出单/调入单
//    private static String orderPrimaryKey = OrderXMLAssistant.getOrderPrimaryKey();
	
	public UrgentOrderDownloadDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn); 
		this.initSQLProducer(userAccount, dtoParameter); 
	}

	@Override
	protected void initSQLProducer(BaseUserDTO arg0, DTO arg1) {
		// TODO Auto-generated method stub
		headerDTO = (UrgentHeaderDTO) dtoParameter;
		leaseModel = new UrgentDownloadModel((SfUserDTO)userAccount, headerDTO );
		sqlProducer = leaseModel ;
	}
 
	public StringBuffer getOrderXml()  {
//		boolean autoCommit = true;
        boolean operateResult = false;
        orderXml = new StringBuffer(); 
        try {
//        	autoCommit = conn.getAutoCommit();
//			conn.setAutoCommit(false);
			SQLModel sqlModel = leaseModel.getHeadersModel( type );
			SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.setDTOClassName( UrgentHeaderDTO.class.getName());
            simp.setCalPattern(LINE_PATTERN);
            simp.executeQuery();
            DTOSet chkOrders = simp.getDTOSet();
            if (chkOrders != null && !chkOrders.isEmpty()) {
                int orderCount = chkOrders.getSize();
                UrgentHeaderDTO headerDTO = null;
                for (int i = 0; i < orderCount; i++) {
                    headerDTO = (UrgentHeaderDTO) chkOrders.getDTO(i);
                    headerDTO.setPeriod( "1" );
                    if( type == UrgentAppConstant.DOWNLOAD_TYPE_OUT ){
                    	headerDTO.setOrderType( UrgentAppConstant.OUT_ORDER_NAME );
//                    	headerDTO.setOrderTypeNmme( UrgentAppConstant.OUT_ORDER_NAME );
                    }else if( type == UrgentAppConstant.DOWNLOAD_TYPE_IN ){
                    	headerDTO.setOrderType( UrgentAppConstant.IN_ORDER_NAME );
//                    	headerDTO.setOrderTypeNmme( UrgentAppConstant.IN_ORDER_NAME );
                    } 
                    setDTOParameter(headerDTO);
//                    startRootXml();
                    startOrderXml();
                    if( type == UrgentAppConstant.DOWNLOAD_TYPE_IN  ){
	                    sqlModel = leaseModel.getLinesModel( headerDTO.getTransId() );
	                    simp.setSql(sqlModel);
	                    simp.setDTOClassName(UrgentLineDTO.class.getName());
	                    simp.executeQuery();
	                    DTOSet chkAssets = simp.getDTOSet();
	                    if (chkAssets != null && !chkAssets.isEmpty()) {
	                        int itemCount = chkAssets.getSize();
	                        for (int j = 0; j < itemCount; j++) {
	                            lineDTO = (UrgentLineDTO) chkAssets.getDTO(j);
	                            startItemXml((j == (itemCount - 1)));
	                        }
	                    }
                    }
                    endOrderXml();
                }
            } else {
                constructNoDataXml();
            }
//			endRootXml();//与网络运维工单合并下载，不需要再次提供根节点信息
            operateResult = true;
        } catch (QueryException ex) {
            ex.printLog();
        } catch (ReflectException ex) {
            Logger.logError(ex);
        } catch (SQLModelException ex) {
        	Logger.logError(ex);
		} finally {
//            try {
//                if (operateResult) {
//                    conn.commit();
//                } else {
//                    conn.rollback();
//                }
//                conn.setAutoCommit(autoCommit);
//            } catch (SQLException ex1) {
//                Logger.logError(ex1);
//            }
            return orderXml;
        } 
	}
	
	/**
     * 功能：构造xml起始根节点
     */
    private void startRootXml() {
        orderXml.append("<?xml version=\"1.0\" encoding=\"GB2312\" ?> ");
        orderXml.append(WorldConstant.ENTER_CHAR);
        orderXml.append(WorldConstant.TAB_CHAR); 
        orderXml.append("<");
        orderXml.append(UrgentOrderXMLAssistant.getRootName());
        orderXml.append(">");
        orderXml.append(WorldConstant.ENTER_CHAR);
        orderXml.append(WorldConstant.TAB_CHAR);
        orderXml.append(WorldConstant.TAB_CHAR);
    }
    
    private void startOrderXml() throws ReflectException {
    	UrgentHeaderDTO chkOrder = (UrgentHeaderDTO) dtoParameter;
//    	String field = null;
//    	String fieldValue = null;
    	
        orderXml.append("<");
        orderXml.append(UrgentOrderXMLAssistant.getOrderName()); 
        
        Iterator pdaKeys = orderMap.keySet().iterator();
        String pdaField = "";
        String serverField = "";
        String nodeValue = "";
        while (pdaKeys.hasNext()) {
            pdaField = (String) pdaKeys.next();
            serverField = (String) orderMap.get(pdaField);
            nodeValue = String.valueOf(ReflectionUtil.getProperty(chkOrder, serverField));
            orderXml.append(" ");
            orderXml.append(pdaField);
            orderXml.append("=\"");
            orderXml.append(StrUtil.xmlFormat(nodeValue));
            orderXml.append("\"");
        }
        orderXml.append(">"); 
    }
    
    private void startItemXml( boolean isLastAssets ) throws ReflectException{
    	orderXml.append("<");
        orderXml.append(UrgentOrderXMLAssistant.getItemName());
        Iterator pdaKeys = itemMap.keySet().iterator();
        String pdaField = "";
        String serverField = "";
        String nodeValue = "";
        while (pdaKeys.hasNext()) {
            pdaField = (String) pdaKeys.next();
            serverField = (String) itemMap.get(pdaField);
            nodeValue = String.valueOf(ReflectionUtil.getProperty(lineDTO, serverField));
            nodeValue = StrUtil.xmlFormat(nodeValue);
            if (pdaField.equals("status")) {
                nodeValue = AssetsDictConstant.SACN_NO;
            }
            orderXml.append(" ");
            orderXml.append(pdaField);
            orderXml.append("=\"");
            orderXml.append(nodeValue);
            orderXml.append("\"");
        }
        orderXml.append("/>");
        orderXml.append(WorldConstant.ENTER_CHAR);
        orderXml.append(WorldConstant.TAB_CHAR);
        orderXml.append(WorldConstant.TAB_CHAR);
        if (!isLastAssets) {
            orderXml.append(WorldConstant.TAB_CHAR);
        }
    }
    
    /**
     * 功能：构造盘点工单节点结束xml
     */
    private void endOrderXml() {
        orderXml.append("</");
        orderXml.append(UrgentOrderXMLAssistant.getOrderName());
        orderXml.append(">");
        orderXml.append(WorldConstant.ENTER_CHAR);
        orderXml.append(WorldConstant.TAB_CHAR);
        orderXml.append(WorldConstant.TAB_CHAR);
    }

    /**
     * 功能：结束根节点
     */
    private void endRootXml() {
        orderXml.append("</");
        orderXml.append(UrgentOrderXMLAssistant.getRootName());
        orderXml.append(">");
    }
    
    /**
     * 功能：构造无数据xml提示
     */
    private void constructNoDataXml() {
        orderXml.append(WorldConstant.ENTER_CHAR);
        orderXml.append(WorldConstant.TAB_CHAR);
    }

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
