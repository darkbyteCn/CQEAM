package com.sino.ams.system.place.dao;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.base.constant.WorldConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.*;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.system.place.dto.PlaceInfoDTO;
import com.sino.ams.system.place.model.PlaceInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class PlaceInfoDAO extends AMSBaseDAO {
	public PlaceInfoDAO(BaseUserDTO userAccount, PlaceInfoDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		commonObjectModel = new PlaceInfoModel((SfUserDTO) userAccount, dtoParameter);
	}
	

	private PlaceInfoModel commonObjectModel = null;
	SfUserDTO user=null;

	/**
	 * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
	 * @param userAccount BaseUserDTO
	 * @param dtoParameter DTO
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfUserDTO user = (SfUserDTO)userAccount;
		PlaceInfoDTO dto = (PlaceInfoDTO)dtoParameter;
		sqlProducer = new PlaceInfoModel(user, dto);
	}

	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.WebFileDownException
	 */
	public File getExportFile() throws WebFileDownException {
		File file = null;
		try {
			PlaceInfoModel modelProducer = (PlaceInfoModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "地点数据";
			String fileName = reportTitle + ".xls";
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);
			Map fieldMap = getFieldMap();
			rule.setFieldMap(fieldMap);
			CustomTransData custData = new CustomTransData();
			custData.setReportTitle(reportTitle);
			custData.setReportPerson(userAccount.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			rule.setCalPattern(LINE_PATTERN);
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
		} catch (DataTransException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		}
		return file;
	}

//	public void insertToData(String[] workorderObjectNos) { //执行同步至TDEAM
//        EtsObjectDTO dtoPara = (EtsObjectDTO) dtoParameter;
//        try {
//        	PlaceInfoModel etsObjectModel = (PlaceInfoModel) sqlProducer;
//            SQLModel sqlModel = etsObjectModel.getInsertToModel(workorderObjectNos);
//            DBOperator.updateRecord(sqlModel, conn);
//            if (userAccount.getIsTd().equals("Y")) {
//                syntoEAMLocus();     //TDEAM同步至EAM
//            } else {
//                syntoTDLocus();          //EAM同步至TDEAM
//            }
//
//
//            prodMessage(CustMessageKey.ENABLE_SUCCESS);
//            getMessage().addParameterValue(dtoPara.getCategoryName());
//        } catch (DataHandleException ex) {
//            prodMessage(CustMessageKey.ENABLE_FAILURE);
//            getMessage().addParameterValue(dtoPara.getCategoryName());
//            ex.printLog();
//        }
//    }
	
	public void syntoTDLocus() {
        CallableStatement cs = null;
        String callStr = "{CALL dbo.ESOP_SYN_LOCATION_TOTD ?}";
        try {
            cs = conn.prepareCall(callStr);
            cs.setInt(1, userAccount.getOrganizationId());
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBStatement(cs);
        }
    }

    public void syntoEAMLocus() {
        CallableStatement cs = null;
        String callStr = "{CALL dbo.ESOP_SYN_LOCATION_TOEAM ?}";
        try {
            cs = conn.prepareCall(callStr);
            cs.setInt(1, userAccount.getOrganizationId());
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBStatement(cs);
        }
    }
//    public String getNextWorkorderObjectCode(String provinceCode) throws QueryException {
//        String maxObjectCode = "";
//        try {
//        	PlaceInfoModel modelProducer = (PlaceInfoModel) sqlProducer;
//            SQLModel sqlModel = modelProducer.getNextObjectCodeModel(provinceCode);
//            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
//            simpleQuery.executeQuery();
//            if (simpleQuery.hasResult()) {
//                Row row = simpleQuery.getFirstRow();
//                maxObjectCode = row.getStrValue("OBJECT_CODE");
//            }
//        } catch (ContainerException ex) {
//            ex.printLog();
//            throw new QueryException(ex);
//        }
//        return maxObjectCode;
//    }
    
	private Map getFieldMap(){
		Map fieldMap = new HashMap();
		fieldMap.put("COMPANY", "公司名称");
		fieldMap.put("WORKORDER_OBJECT_CODE", "地点代码");
		fieldMap.put("WORKORDER_OBJECT_NAME", "地点名称");
		fieldMap.put("COUNTY_NAME", "所属区县");
		fieldMap.put("OBJECT_CATEGORY_NAME", "地点专业");
		fieldMap.put("IS_TD", "是否TD地点");
		fieldMap.put("CREATION_USER", "创建人");
		fieldMap.put("CREATION_DATE", "创建日期");
		fieldMap.put("DISABLE_DATE", "失效日期");
		fieldMap.put("UPDATED_USER", "上次更新人");
		fieldMap.put("VALUE", "行政区划");
		return fieldMap;
	}

	/**
	 * 功能：导出查询SQL资产数据
	 * @return String 返回导出Excel文件
	 * @throws com.sino.base.exception.WebFileDownException
	 */
	public File getExportFile2() throws WebFileDownException {
		File file = null;
		try {
			PlaceInfoModel modelProducer = (PlaceInfoModel)sqlProducer;
			SQLModel sqlModel = modelProducer.getPageQueryModel();
			String reportTitle = "地点签统计报表";
			String fileName = reportTitle + ".xls";
			TransRule rule = new TransRule();
			rule.setDataSource(sqlModel);
			rule.setSourceConn(conn);
			String filePath = WorldConstant.USER_HOME;
			filePath += WorldConstant.FILE_SEPARATOR;
			filePath += fileName;
			rule.setTarFile(filePath);
			DataRange range = new DataRange();
			rule.setDataRange(range);
			Map fieldMap= getFieldMap2();
			rule.setFieldMap(fieldMap);
			CustomTransData custData = new CustomTransData();
			custData.setReportTitle(reportTitle);
			custData.setReportPerson(userAccount.getUsername());
			custData.setNeedReportDate(true);
			rule.setCustData(custData);
			rule.setCalPattern(LINE_PATTERN);
			TransferFactory factory = new TransferFactory();
			DataTransfer transfer = factory.getTransfer(rule);
			transfer.transData();
			file = (File) transfer.getTransResult();
		} catch (DataTransException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		} catch (SQLModelException ex) {
			ex.printLog();
			throw new WebFileDownException(ex);
		}
		return file;
	}
	

	private Map getFieldMap2(){
		Map fieldMap = new HashMap();
		
		 fieldMap.put("WORKORDER_OBJECT_NO", "编号");
         fieldMap.put("WORKORDER_OBJECT_CODE", "地址代码");
         fieldMap.put("WORKORDER_OBJECT_NAME", "地点名称");
         fieldMap.put("B", "所属区县");
         fieldMap.put("C", "地点代码");
         fieldMap.put("D", "创建日期");
         fieldMap.put("E", "行政区划");
         fieldMap.put("F", "地点描述");
         fieldMap.put("COMPANY", "公司名称");
		return fieldMap;
	}
	/**
	 * 功能:检查地点是否存在
	 * @return boolean
	 */
//	public boolean existObject(){
//		boolean exist = false;
//		try {
//			PlaceInfoModel modelProducer = (PlaceInfoModel) sqlProducer;
//			SQLModel sqlModel = modelProducer.getObjectEsistModel();
//			SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
//			simQuery.executeQuery();
//			exist = simQuery.hasResult();
//		} catch (QueryException ex) {
//			ex.printLog();
//		}
//		return exist;
//	}
	
	/**
	 * 功能:检查基站或营业厅编号是否存在
	 * @return boolean
	 */
//	public boolean existBtsNo(){
//		boolean exist = false;
//		try {
//			PlaceInfoModel modelProducer = (PlaceInfoModel) sqlProducer;
//			SQLModel sqlModel = modelProducer.getBtsNoEsistModel();
//			SimpleQuery simQuery = new SimpleQuery(sqlModel, conn);
//			simQuery.executeQuery();
//			exist = simQuery.hasResult();
//		} catch (QueryException ex) {
//			ex.printLog();
//		}
//		return exist;
//	}
	/**
	 * 功能:保存地点
	 * @return boolean
	 */
	public boolean saveObject() {
		boolean operateResult = false;
		PlaceInfoDTO dto = (PlaceInfoDTO) dtoParameter;
		int objectNo = dto.getFlexValueId();
		try {
//			if (objectNo.equals("")) {
//				createObject();
//			} else {
				super.updateData();
//			}
			operateResult = true;
		} catch (DataHandleException ex) {
			ex.printLog();
		} finally{
			if(operateResult){
					prodMessage(CustMessageKey.OBJECT_UPDATE_SUCCESS);
			} else {
					prodMessage(CustMessageKey.OBJECT_UPDATE_FAILURE);
			}
		}
		return operateResult;
	}

	/**
	 * 功能：创建地点
	 * @throws DataHandleException
	 */
//	private void createObject() throws DataHandleException{
//		boolean operateResult = false;
//		boolean autoCommit = true;
//		EtsObjectDTO dto = (EtsObjectDTO) dtoParameter;
//		try {
//			autoCommit = conn.getAutoCommit();
//			conn.setAutoCommit(false);
//			SeqProducer seqProducer = new SeqProducer(conn);
//			String objectNo = seqProducer.getGUID();
//			dto.setRemark("地点统一维护程序创建");
//			dto.setWorkorderObjectNo(objectNo);
//			setDTOParameter(dto);
//			createData();
//			AmsObjectAddressDTO dtoAddress = new AmsObjectAddressDTO();
//			dtoAddress.setObjectNo(dto.getWorkorderObjectNo());
//			dtoAddress.setBoxNo("0000");
//			dtoAddress.setNetUnit("0000");
//			dtoAddress.setAddressNo(dto.getWorkorderObjectNo() + ".0000.0000");
//			dtoAddress.setRemark("地点统一维护程序创建");
//			AmsObjectAddressDAO objectDAO = new AmsObjectAddressDAO(userAccount, dtoAddress, conn);
//			objectDAO.createData();
//			operateResult = true;
//		} catch (SQLException ex) {
//			Logger.logError(ex);
//		} finally{
//			try {
//				if (operateResult) {
//					conn.commit();
//				} else {
//					conn.rollback();
//					dto.setWorkorderObjectNo("");
//				}
//				conn.setAutoCommit(autoCommit);
//			} catch (SQLException ex) {
//				Logger.logError(ex);
//				throw new DataHandleException(ex);
//			}
//		}
//	}

	/**
	 * 功能：失效地点
	 * @param objectNos String[]
	 * @return boolean
	 */
//	public boolean disableObjects(String[] objectNos){
//		boolean operateResult = false;
//		boolean autoCommit = true;
//		try {
//			if (objectNos != null && objectNos.length > 0) {
//				autoCommit = conn.getAutoCommit();
//				conn.setAutoCommit(false);
//				int objectCount = objectNos.length;
//				EtsObjectDTO dto = null;
//				CommonObjectModel modelProducer = (CommonObjectModel) sqlProducer;
//				SQLModel sqlModel = null;
//				for (int i = 0; i < objectCount; i++) {
//					dto = new EtsObjectDTO();
//					dto.setWorkorderObjectNo(objectNos[i]);
//					setDTOParameter(dto);
//					sqlModel = modelProducer.getDisableModel();
//					DBOperator.updateRecord(sqlModel, conn);
//				}
//				operateResult = true;
//			} else {
//				operateResult = true;
//			}
//		} catch (DataHandleException ex) {
//			ex.printLog();
//		} catch (SQLException ex) {
//			Logger.logError(ex);
//		} finally{
//			try {
//				if (operateResult) {
//					conn.commit();
//					prodMessage(CustMessageKey.OBJECT_DISABLE_SUCCESS);
//				} else {
//					conn.rollback();
//					prodMessage(CustMessageKey.OBJECT_DISABLE_FAILURE);
//				}
//				conn.setAutoCommit(autoCommit);
//			} catch (SQLException ex1) {
//				Logger.logError(ex1);
//			}
//		}
//		return operateResult;
//	}

//	public boolean enableObjects(String[] objectNos){
//		boolean operateResult = false;
//		boolean autoCommit = true;
//		try {
//			if (objectNos != null && objectNos.length > 0) {
//				autoCommit = conn.getAutoCommit();
//				conn.setAutoCommit(false);
//				int objectCount = objectNos.length;
//				EtsObjectDTO dto = null;
//				CommonObjectModel modelProducer = (CommonObjectModel) sqlProducer;
//				SQLModel sqlModel = null;
//				for (int i = 0; i < objectCount; i++) {
//					dto = new EtsObjectDTO();
//					dto.setWorkorderObjectNo(objectNos[i]);
//					setDTOParameter(dto);
//					sqlModel = modelProducer.getEnableModel();
//					DBOperator.updateRecord(sqlModel, conn);
//				}
//				operateResult = true;
//			} else {
//				operateResult = true;
//			}
//		} catch (DataHandleException ex) {
//			ex.printLog();
//		} catch (SQLException ex) {
//			Logger.logError(ex);
//		} finally{
//			try {
//				if (operateResult) {
//					conn.commit();
//					prodMessage(CustMessageKey.OBJECT_ENABLE_SUCCESS);
//				} else {
//					conn.rollback();
//					prodMessage(CustMessageKey.OBJECT_ENABLE_FAILURE);
//				}
//				conn.setAutoCommit(autoCommit);
//			} catch (SQLException ex1) {
//				Logger.logError(ex1);
//			}
//		}
//		return operateResult;
//	}
	
//	public String getAddress(String addressName) throws QueryException, ContainerException{
//		SQLModel sqlModel = commonObjectModel.getAddress(addressName);
//        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
//        simpleQuery.executeQuery();
//        RowSet rows = simpleQuery.getSearchResult();
//        Row row = null;
//        
//        StringBuffer result = new StringBuffer();
//        if (rows != null && rows.getSize() > 0) {
//        	result.append("<div id=\"search_suggest\"><font color = '#blue'>&nbsp;&nbsp;如下是已添加的地点名称，请确认是否新增该地点！</font>");
//        	for (int i = 0; i < rows.getSize(); i++) {
//        		row = rows.getRow(i);
//        		result.append("<div onmouseover=\"javascript:suggestOver(this);\" style = 'width:100%'");
//        		result.append(" onmouseout=\"javascript:suggestOut(this);\" ");
//        		result.append(" onclick=\"javascript:setSearch(this.innerHTML);\" ");
//        		result.append(" class=\"suggest_link\">" + row.getValue("WORKORDER_OBJECT_NAME") + "</div>");
//        	}
//        	result.append("<div align = 'right'><a href=''  onclick = 'do_Cancle();'><font color='blue' size='3'>关闭</font></a></div>");
//        	result.append("</div>");
//        }
//        return result.toString();
//	}
	
//	private String getIsTD() throws QueryException {
//        String isTd = "";
//        try {
//        	PlaceInfoModel modelProducer = (PlaceInfoModel) sqlProducer;
//            SQLModel sqlModel = modelProducer.geIsTDByOrgIdModel();
//            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
//            simpleQuery.executeQuery();
//            if (simpleQuery.hasResult()) {
//                Row row = simpleQuery.getFirstRow();
//                isTd = row.getStrValue("IS_TD");
//            }
//        } catch (ContainerException ex) {
//            ex.printLog();
//            throw new QueryException(ex);
//        }
//        return isTd;
//    }
	
//	public String getOrgChangeResponse() throws QueryException{
//        String resContent = "";
//        EtsObjectDTO dto = (EtsObjectDTO)dtoParameter;
//        AssetsOptProducer optProducer = new AssetsOptProducer(userAccount, conn);
//        resContent = optProducer.getAreaOptions(dto.getOrganizationId(), "");
//        String isTd = getIsTD();
//        if(isTd.equals("")){
//            isTd = "EMPTY_CONTENT";
//        }
//        resContent += "&";
//        resContent += isTd;
//        return resContent;
//    }
	
//	public String getMatchORG() throws QueryException, ContainerException {
//		PlaceInfoModel modelProducer = (PlaceInfoModel) sqlProducer;
//        SQLModel sqlModel = modelProducer.getMatchORG();
//        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
//        simpleQuery.executeQuery();
//        String matchORG = "";
//        RowSet rows = simpleQuery.getSearchResult();
//        if (rows != null && rows.getSize() > 0) {
//            matchORG = simpleQuery.getFirstRow().getStrValue("COMPANY_CODE");
//        }
//        return matchORG;
//    }
	
//	public String getLocation(String location) throws QueryException, ContainerException{
//		SQLModel sqlModel = commonObjectModel.getLocation(location);
//		SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
//		simpleQuery.executeQuery();
//		RowSet rows = simpleQuery.getSearchResult();
//		Row row = null;
//		
//		StringBuffer result = new StringBuffer();
//		if (rows != null && rows.getSize() > 0) {
//			result.append("<div id=\"search_suggest\"><font color = '#blue'>&nbsp;&nbsp;如下是已添加的描述，请确认是否新增该描述！</font>");
//			for (int i = 0; i < rows.getSize(); i++) {
//				row = rows.getRow(i);
//				result.append("<div onmouseover=\"javascript:suggestOver(this);\" style = 'width:100%'");
//				result.append(" onmouseout=\"javascript:suggestOut(this);\" ");
//				result.append(" onclick=\"javascript:setSearch(this.innerHTML);\" ");
//				result.append(" class=\"suggest_link\">" + row.getValue("LOCATION") + "</div>");
//			}
//			result.append("<div align = 'right'><a href=''  onclick = 'do_Cancle();'><font color='blue' size='3'>关闭</font></a></div>");
//			result.append("</div>");
//		}
//		return result.toString();
//	}
}
