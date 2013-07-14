package com.sino.ams.yearchecktaskmanager.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskBaseDateDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskHeaderDTO;
import com.sino.ams.yearchecktaskmanager.dto.AssetsYearCheckTaskLineDTO;
import com.sino.ams.yearchecktaskmanager.model.AssetsYearCheckTaskProvinceModel;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskConstant;
import com.sino.ams.yearchecktaskmanager.util.AssetsCheckTaskOrderGeneretor;
import com.sino.ams.yearchecktaskmanager.util.CommonUtil;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

public class AssetsYearCheckTaskProvinceDAO extends AMSBaseDAO{
	
	private AssetsYearCheckTaskProvinceModel myModel;
	public AssetsYearCheckTaskProvinceDAO(SfUserDTO userAccount, AssetsYearCheckTaskHeaderDTO dtoParameter,
			Connection conn) {
		super(userAccount, dtoParameter, conn);
		myModel = new  AssetsYearCheckTaskProvinceModel((SfUserDTO) userAccount,dtoParameter);
	}

	protected void initSQLProducer(BaseUserDTO arg0, DTO arg1) {
		AssetsYearCheckTaskHeaderDTO dtoPara = (AssetsYearCheckTaskHeaderDTO) dtoParameter;
		sqlProducer = new AssetsYearCheckTaskProvinceModel((SfUserDTO) userAccount,dtoPara);
	}
    
    //�����׼����Ϣ
	public void saveBaseDate(AssetsYearCheckTaskBaseDateDTO baseDateDto) throws DataHandleException {
		//AssetsYearCheckTaskProvinceModel model = (AssetsYearCheckTaskProvinceModel) sqlProducer;
        SQLModel sqlModel =myModel.getSaveBaseDateModel(baseDateDto);
        DBOperator.updateRecord(sqlModel, conn);
	}
	
	//��ѯ�����Ƿ���ڻ�׼����Ϣ
	public AssetsYearCheckTaskBaseDateDTO getBaseDatePeriod() throws QueryException, ContainerException {
		AssetsYearCheckTaskBaseDateDTO dto = null;
		RowSet rs = null;
		//AssetsYearCheckTaskProvinceModel model = (AssetsYearCheckTaskProvinceModel) sqlProducer;
        SQLModel sqlModel =myModel.getBaseDatePeriodModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            rs = sq.getSearchResult();
            Row row = rs.getRow(0);
            dto =new AssetsYearCheckTaskBaseDateDTO();
            dto.setBaseDateId(row.getStrValue("BASEDATE_ID"));
            dto.setBaseDateYear(row.getStrValue("BASEDATE_YEAR"));
            dto.setChkYearTaskOrderNumber(row.getStrValue("CHK_YEARTASK_ORDER_NUMBER"));
            dto.setBaseDateType(row.getStrValue("BASE_DATE_TYPE"));
            dto.setCheckBaseDateFrom(String.valueOf(row.getValue("CHECK_BASE_DATE_FROM")));
            dto.setCheckBaseDateEnd(String.valueOf(row.getValue("CHECK_BASE_DATE_END")));
            dto.setCheckTaskDateFrom(String.valueOf(row.getValue("CHECK_TASK_DATE_FROM")));
            dto.setCheckTaskDateEnd(row.getStrValue("CHECK_TASK_DATE_END"));
            dto.setEnabled(row.getStrValue("ENALBED"));
        }
		return dto;
	}
	
	public DTOSet getAllOU(AssetsYearCheckTaskHeaderDTO headerDto) throws QueryException, DTOException, ContainerException{
		 SQLModel sqlModel =myModel.getAllOUModel(headerDto);
		 DTOSet dtoSet = new DTOSet();
		 RowSet rs = null;
		 SimpleQuery sq = new SimpleQuery(sqlModel, conn);
		 sq.executeQuery();
	     if (sq.hasResult()) {
	        rs = sq.getSearchResult();
	     }
	     if(rs!=null && rs.getSize()>0){
	    	 for(int i=0;i<rs.getSize();i++){
	    		 Row row = rs.getRow(i);
	    		 AssetsYearCheckTaskLineDTO lineDto = new AssetsYearCheckTaskLineDTO();
	    		 //����ֵ
	    		 lineDto.setOrderNumber(row.getStrValue("ORDER_NUMBER"));
	    		 lineDto.setCompany(row.getStrValue("COMPANY"));
	    		 lineDto.setCompanyCode(String.valueOf(row.getValue("COMPANY_CODE")));
	    		 lineDto.setImplementOrganizationId(Integer.parseInt(String.valueOf(row.getValue("IMPLEMENT_ORGANIZATION_ID"))));
	    		 lineDto.setImplementDeptId(String.valueOf(row.getValue("IMPLEMENT_DEPT_ID")));
	    		 lineDto.setImplementDeptName(row.getStrValue("IMPLEMENT_DEPT_NAME"));
	    		 lineDto.setImplementName(row.getStrValue("IMPLEMENT_NAME"));
	    		 lineDto.setImplementBy(Integer.parseInt(String.valueOf(row.getValue("IMPLEMENT_BY"))));
	    		 dtoSet.addDTO(lineDto);
	    	 }
	     }
	     return dtoSet;
	}
	
	//��ȡ���л�׼��
	public AssetsYearCheckTaskBaseDateDTO   getBaseDateCity() throws QueryException, ContainerException {
		AssetsYearCheckTaskBaseDateDTO baseDateDto = null;
		RowSet rs = null;
		//AssetsYearCheckTaskProvinceModel model = (AssetsYearCheckTaskProvinceModel) sqlProducer;
        SQLModel sqlModel =myModel.getBaseDateCityModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            rs = sq.getSearchResult();
            Row row = rs.getRow(0);
            baseDateDto = new AssetsYearCheckTaskBaseDateDTO();
            baseDateDto.setCheckBaseDateCity(row.getStrValue("CHECK_BASE_DATE_CITY"));
            baseDateDto.setSoftWareMethod(row.getStrValue("NON_ADDRESS_ASSETS_SOFT"));
            if(baseDateDto.getSoftWareMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER)){
            	baseDateDto.setSoftWareMethodName("���й���Ա");
            }else if(baseDateDto.getSoftWareMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER)){
            	baseDateDto.setSoftWareMethodName("�����ʲ�����Ա");
            }else if(baseDateDto.getSoftWareMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON)){
            	baseDateDto.setSoftWareMethodName("�ʲ������˻��ض���Ա");
            }
            
            baseDateDto.setClientMethod(row.getStrValue("NON_ADDRESS_ASSETS_CLIENT"));
            if(baseDateDto.getClientMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER)){
            	baseDateDto.setClientMethodName("���й���Ա");
            }else if(baseDateDto.getClientMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER)){
            	baseDateDto.setClientMethodName("�����ʲ�����Ա");
            }else if(baseDateDto.getClientMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON)){
            	baseDateDto.setClientMethodName("�ʲ������˻��ض���Ա");
            }
            baseDateDto.setPipeLineMethod(row.getStrValue("NON_ADDRESS_ASSETS_PIPELINE"));
            if(baseDateDto.getPipeLineMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_CITY_MANAGER)){
            	baseDateDto.setPipeLineMethodName("���й���Ա");
            }else if(baseDateDto.getPipeLineMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_DEPT_MANAGER)){
            	baseDateDto.setPipeLineMethodName("�����ʲ�����Ա");
            }else if(baseDateDto.getPipeLineMethod().equals(AssetsCheckTaskConstant.NON_ADDRESS_METHOD_FOR_SOME_PERSON)){
            	baseDateDto.setPipeLineMethodName("�ʲ������˻��ض���Ա");
            }
        }
		return baseDateDto;
	}
	public boolean createYearline(AssetsYearCheckTaskLineDTO yearlineDTO)throws Exception{
		//AssetsYearCheckTaskProvinceModel model = (AssetsYearCheckTaskProvinceModel) sqlProducer;
		System.out.println("userAccount="+userAccount);
		SQLModel headerModel = myModel.getCreateTaksLineModel(yearlineDTO);
		return DBOperator.updateRecord(headerModel, conn);
	}
	
	public boolean createBaseDate(AssetsYearCheckTaskBaseDateDTO baseDateDTO)throws Exception{
		//AssetsYearCheckTaskProvinceModel model = (AssetsYearCheckTaskProvinceModel) sqlProducer;
		SQLModel dateBaseModel = myModel.getSaveBaseDateModel(baseDateDTO);
		return DBOperator.updateRecord(dateBaseModel, conn);
	}
	
	public boolean createHeader(AssetsYearCheckTaskHeaderDTO headerDto) throws DataHandleException{
		//AssetsYearCheckTaskProvinceModel model = (AssetsYearCheckTaskProvinceModel) sqlProducer;
		AssetsYearCheckTaskHeaderDTO headerDTO = (AssetsYearCheckTaskHeaderDTO) dtoParameter;
		headerDTO.setHeaderId(CommonUtil.getUUID());
		headerDTO.setParentOrderNumber(headerDto.getParentOrderNumber());
		setDTOParameter(headerDTO);
		SQLModel headerMdoel = myModel.getcreateTaskHeaderModel(headerDto);
		return DBOperator.updateRecord(headerMdoel, conn);
	}
	public boolean createOrderLines(DTOSet lineSet)throws Exception{
		boolean result = true;
		//AssetsYearCheckTaskProvinceModel model = (AssetsYearCheckTaskProvinceModel) sqlProducer;
		AssetsYearCheckTaskHeaderDTO headerDTO = (AssetsYearCheckTaskHeaderDTO) dtoParameter;
		AssetsCheckTaskOrderGeneretor orderGeneretor = new AssetsCheckTaskOrderGeneretor(conn,userAccount.getCompanyCode(),AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK);
		if(this.createHeader(headerDTO)){
			for (int i = 0; i < lineSet.getSize(); i++){
	         	AssetsYearCheckTaskLineDTO lineDto = (AssetsYearCheckTaskLineDTO) lineSet.getDTO(i);
	         	lineDto.setTaskOrderId(CommonUtil.getUUID());
	         	lineDto.setHeaderId(headerDTO.getHeaderId());
	         	lineDto.setOrderName(AssetsCheckTaskConstant.ORDER_NAME);
	         	orderGeneretor.setCompanyCode(lineDto.getCompanyCode());
	         	lineDto.setOrderNumber(orderGeneretor.getOrderNum());
	         	lineDto.setOrderType(AssetsCheckTaskConstant.ORDER_TYPE_ASS_CHK_TASK);
	         	lineDto.setOrderStatus(AssetsCheckTaskConstant.DO_CREATE);
	         	lineDto.setOrderLevel("2");
	         	lineDto.setIsLastLevel("N");
	         	lineDto.setImplementRoleName(AssetsCheckTaskConstant.ORDER_RECIVE_ROLE_TO_CITY);
	         	SQLModel headerModel = myModel.getCreateTaksLineModel(lineDto);
				boolean singleResult = DBOperator.updateRecord(headerModel, conn);
				if(singleResult){
					continue;
				}else{
					result = false;
					break;
				}
	         }
		}else{
			result = false;
		}
		return result;
	}
	
	public boolean createAll(AssetsYearCheckTaskLineDTO yearlineDTO,
			AssetsYearCheckTaskBaseDateDTO baseDateDto,
			DTOSet lineSet) throws Exception {
		 boolean operateResult = false;
	        boolean autoCommit = true;
	        try {
	            autoCommit = conn.getAutoCommit();
	            conn.setAutoCommit(false);
	            
	            createYearline(yearlineDTO); //�����������
	            createBaseDate(baseDateDto); //�����׼��
	            createOrderLines(lineSet); //�����·�����
	            
	            operateResult= true;
	        }catch (Exception ex) {
	            ex.printStackTrace();
	            operateResult = false;
	            throw new Exception(ex);
	        } finally {
	            try {
	                if (!operateResult) {
	                    conn.rollback();
	                } else {
	                    conn.commit();
	                }
	                conn.setAutoCommit(autoCommit);
	            } catch (SQLException ex) {
	            	ex.printStackTrace();
	                Logger.logError(ex);
	                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
	                throw new Exception(ex);
	            }
	        }
	        return operateResult;
	}

	public boolean createOrder(DTOSet lineSet) throws Exception {
		boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            
            createOrderLines(lineSet); //�����·�����
            
            operateResult= true;
        }catch (Exception ex) {
            ex.printStackTrace();
            operateResult = false;
            throw new Exception(ex);
        }  finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                } else {
                    conn.commit();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
                throw new Exception(ex);
            }
        }
        return operateResult;
	}
}
