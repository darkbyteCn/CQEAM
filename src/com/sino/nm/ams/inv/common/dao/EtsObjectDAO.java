package com.sino.nm.ams.inv.common.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.nm.ams.inv.common.model.EtsObjectModel;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.specialty.model.OtLocsVindicateModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: EtsObjectDAO</p>
 * <p>Description:仓库地点设置</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author V-yushibo
 * @version 1.0
 */
public class EtsObjectDAO extends AMSBaseDAO {

	EtsObjectModel etsObjectModel = null;
	
	/**
     * 功能：资产地点表(AMS) ETS_OBJECT 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsObjectDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
	public EtsObjectDAO(SfUserDTO userAccount, EtsObjectDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		etsObjectModel = (EtsObjectModel) sqlProducer;
	}

	/**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		super.sqlProducer = new EtsObjectModel((SfUserDTO) userAccount, (EtsObjectDTO) dtoParameter);
	}

	/**
     * 功能：插入地点表(AMS)表“ AMS_OBJECT_ADDRESS”数据。
     */
    public void createData() throws DataHandleException {
        boolean hasError = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
        	
            super.createData();
            SQLModel sqlModel = etsObjectModel.getAddressCreateModel();
            DBOperator.updateRecord(sqlModel, conn);
            conn.commit();
            hasError = false;
            prodMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
            getMessage().addParameterValue("仓库");
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } finally {
            try {
                if (hasError) {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                throw new DataHandleException(ex1);
            }
        }
        getMessage().addParameterValue("AMS对象地点表");
    }
    
    /**
     * 功能：插入仓库表(AMS)表“ ETS_OBJECT”数据。
     */
    public void createEoData(String countyCodeMis) throws DataHandleException {
        boolean hasError = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            super.createData();
            SeqProducer seqProducer = new SeqProducer(conn);
//            String etsObjectS = seqProducer.getStrNextSeq("ETS_OBJECT_S");
            String etsObjectS = seqProducer.getGUID();
            EtsObjectDTO objDTO = (EtsObjectDTO) dtoParameter;
//        	String countyCodeMis = getCountyCodeMis();// 成本中心代码
            objDTO.setCountyName(getName());
			objDTO.setCost_code(countyCodeMis);
			objDTO.setCityName(getCityName());
			objDTO.setAreaName(getAreaName());
			String workorderObjectLocation = objDTO.getCountyName() + "."
					+ objDTO.getCityName() + objDTO.getAreaName() + "."
					+ objDTO.getWorkorderObjectName() + "." + "000";
			objDTO.setWorkorderObjectLocation(workorderObjectLocation);
			setDTOParameter(objDTO);
            SQLModel sqlModel = etsObjectModel.getDataCreateModel(etsObjectS, countyCodeMis);
            DBOperator.updateRecord(sqlModel, conn);
            SQLModel sqlModelAddressId = etsObjectModel.getDataCreateAddressModel(etsObjectS);
            DBOperator.updateRecord(sqlModelAddressId, conn);
            conn.commit();
            hasError = false;
            prodMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
            getMessage().addParameterValue("仓库");
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (QueryException e) {
			
			e.printStackTrace();
		} finally {
            try {
                if (hasError) {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                throw new DataHandleException(ex1);
            }
        }
        getMessage().addParameterValue("AMS对象地点表");
    }
    
    /**
     * 功能：更新资产地点表(AMS)表“ETS_OBJECT”数据
     * @param countyCodeMis
     * @throws DataHandleException
     * @throws QueryException 
     */
    public void updateData(String countyCodeMis) throws DataHandleException, QueryException {
		boolean operateResult = false;
		SQLModel sqlModel = null;
	     EtsObjectDTO objDTO = (EtsObjectDTO) dtoParameter;
	    objDTO.setCountyName(getName());
		objDTO.setCost_code(countyCodeMis);
		objDTO.setCityName(getCityName());
		objDTO.setAreaName(getAreaName());
		String workorderObjectLocation = objDTO.getCountyName() + "."
				+ objDTO.getCityName() + objDTO.getAreaName() + "."
				+ objDTO.getWorkorderObjectName() + "." + "000";
		objDTO.setWorkorderObjectLocation(workorderObjectLocation);
		setDTOParameter(objDTO);
		sqlModel = etsObjectModel.getDataUpdateModel(countyCodeMis);
		if (sqlModel != null && !sqlModel.isEmpty()) {
			DBOperator.updateRecord(sqlModel, conn);
			operateResult = true;
		}
	}

    /**
     * 功能：更新资产地点表(AMS)表“ETS_OBJECT”数据。
     */
    public void updateData() throws DataHandleException {
        EtsObjectDTO objDTO = (EtsObjectDTO) dtoParameter;
    	String countyCodeMis;
		try {
	   objDTO.setCountyName(getName());
	    countyCodeMis = getCountyCodeMis();
		objDTO.setCost_code(countyCodeMis);
		objDTO.setCityName(getCityName());
		objDTO.setAreaName(getAreaName());
		} catch (QueryException e) {
			
			e.printStackTrace();
		}
		String workorderObjectLocation = objDTO.getCountyName() + "."
				+ objDTO.getCityName() + objDTO.getAreaName() + "."
				+ objDTO.getWorkorderObjectName() + "." + "000";
		objDTO.setWorkorderObjectLocation(workorderObjectLocation);
		setDTOParameter(objDTO);
        super.updateData();
        getMessage().addParameterValue("资产仓库表");

    }

    private void disabledData(String[] workorderObjectCodes) throws DataHandleException { //执行批量失效操作
        SQLModel sqlModel = etsObjectModel.getDisabledModel(workorderObjectCodes);
        DBOperator.updateRecord(sqlModel, conn);
    }

    public void efficientData(String[] workorderObjectCodes) throws DataHandleException { //执行批量生效效操作
        SQLModel sqlModel = etsObjectModel.getEfficientModel(workorderObjectCodes);
        DBOperator.updateRecord(sqlModel, conn);
        message.setMessageValue("仓库生效成功！");
    }

    public void inureData() throws DataHandleException { //执行生效操作
        SQLModel sqlModel = etsObjectModel.getInureModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    public void deleteData() throws DataHandleException { //执行失效操作
        super.deleteData();
        getMessage().addParameterValue("资产地点");
    }

    public String checkCategory(String objCategory) throws QueryException, ContainerException { //检查同组织下是否有相同类型仓库
        SQLModel sqlModel = etsObjectModel.getTypeObjHasBeenModel(objCategory);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (!sq.hasResult()) {
            return "";
        } else {
            Row row = sq.getFirstRow();
            return row.getStrValue(0);
        }
    }

    public boolean checkCode(String objCode) throws QueryException { //检查同组织下是否有相同类型仓库
        SQLModel sqlModel = etsObjectModel.getCodeHasBeenModel(objCode);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.hasResult();
    }
    
    public boolean checkFields(String objectCategory, String businessCategory) throws QueryException { //在新增页面检查一个公司只允许有一个仪器仪表正常库
        SQLModel sqlModel = etsObjectModel.getFieldsHasBeenModel(objectCategory, businessCategory);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.hasResult();
    }
    
    public boolean checkFields(String workorderObjectCode, String objectCategory, String businessCategory) throws QueryException { //在修改页面检查一个公司只允许有一个仪器仪表正常库
        SQLModel sqlModel = etsObjectModel.getFieldsHasBeenModel(workorderObjectCode, objectCategory, businessCategory);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.hasResult();
    }

    /**
     * 失效时需判断该仓库是否有设备
     * @param workorderObjectCode 仓库代码
     * @return haveItems
     */
    private boolean isInvHaveItems(String workorderObjectCode) throws QueryException {
        SimpleQuery sq = new SimpleQuery(etsObjectModel.getItemsByObjectModel(workorderObjectCode), conn);
        sq.executeQuery();
        return sq.hasResult();
    }

    /**
     * 失效
     * @param workorderObjectCodes 选中的仓库代码
     * @throws DataHandleException
     * @throws QueryException
     */
    public void do_disable(String[] workorderObjectCodes) throws DataHandleException, QueryException {
        if (workorderObjectCodes != null) {
            boolean haveItems = false;
            String workorderObjectCode = "";
            for (int i = 0; i < workorderObjectCodes.length; i++) {
                workorderObjectCode = workorderObjectCodes[i];
                if (workorderObjectCode.indexOf("BJ") > -1) haveItems = isInvHaveItems(workorderObjectCode);
                if (haveItems) {
                    break;
                }
            }
            //判断该仓库是否有设备
            if (haveItems) {
                message.setMessageValue("您选择的项中仓库[" + workorderObjectCode + "]下还有设备，无法失效该仓库，请重新选择！");
                message.setIsError(true);
            } else {
                disabledData(workorderObjectCodes);
                message.setMessageValue("仓库失效成功！");
            }
        }
    }
    /**
	 * 功能：根据countyCode获取countyCodeMis的值
	 * 
	 * @return SQLModel
	 */
	private String getCountyCodeMis() throws QueryException {
		String countyCodeMis = "";
		EtsObjectDTO etsObjectDTO = (EtsObjectDTO) dtoParameter;
		EtsObjectModel etsObjectModel = new EtsObjectModel(userAccount,
				etsObjectDTO);
		String countyCode = etsObjectDTO.getCountyCode();
		SQLModel sqlModel = etsObjectModel.getCountyCodeMis(countyCode);
		SimpleQuery sqlQuery = new SimpleQuery(sqlModel, conn);
		sqlQuery.executeQuery();
		if (sqlQuery.hasResult()) {
			RowSet row = sqlQuery.getSearchResult();
			try {
				countyCodeMis = (String) row.getRow(0).getValue(
						"COUNTY_CODE_MIS");
			} catch (ContainerException e) {
				e.printStackTrace();
				throw new QueryException();
			}
		}
		return countyCodeMis;
	}
	
	/**
	 * 功能：获取city_name的值
	 * 
	 * @return SQLModel
	 */
	private String getCityName() throws QueryException {
		String countName = "";
		EtsObjectDTO objDTO = (EtsObjectDTO) dtoParameter;
		EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
		SQLModel sqlModel = etsObjectModel.getCityNameModel(objDTO.getCityId());
		SimpleQuery sqlQuery = new SimpleQuery(sqlModel, conn);
		sqlQuery.executeQuery();
		if (sqlQuery.hasResult()) {
			RowSet row = sqlQuery.getSearchResult();
			try {
				countName = (String) row.getRow(0).getValue("CITY_NAME");
			} catch (ContainerException e) {
				e.printStackTrace();
				throw new QueryException();
			}
		}
		return countName;
	}
	/**
	 * 功能：获取area_name的值
	 * 
	 * @return SQLModel
	 */

	private String getAreaName() throws QueryException {
		String countName = "";
		EtsObjectDTO objDTO = (EtsObjectDTO) dtoParameter;
		EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
		SQLModel sqlModel = etsObjectModel.getAreaNameModel(objDTO.getAreaId());
		SimpleQuery sqlQuery = new SimpleQuery(sqlModel, conn);
		sqlQuery.executeQuery();
		if (sqlQuery.hasResult()) {
			RowSet row = sqlQuery.getSearchResult();
			try {
				countName = (String) row.getRow(0).getValue("AREA_NAME");
			} catch (ContainerException e) {
				e.printStackTrace();
				throw new QueryException();
			}
		}
		return countName;
	}
	   private String getName()throws QueryException{
		      String countName="";
		         EtsObjectDTO objDTO = (EtsObjectDTO)dtoParameter;
		         EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
		            SQLModel sqlModel = etsObjectModel.getNameModel(objDTO.getCountyCode());
		              SimpleQuery sqlQuery = new SimpleQuery(sqlModel, conn);
		        sqlQuery.executeQuery();
		        if (sqlQuery.hasResult()) {
		            RowSet row = sqlQuery.getSearchResult();
		            try {
		            	countName = (String) row.getRow(0).getValue("COUNTY_NAME");
		            } catch (ContainerException e) {
		                e.printStackTrace();
		                throw new QueryException();
		            }
		        }
		       return countName;
		   }
}
