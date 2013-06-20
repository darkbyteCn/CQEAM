package com.sino.ams.newasset.servlet;

import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.base.message.Message;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.Request2DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.web.*;
import com.sino.base.log.Logger;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.util.StrUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsItemCorrectLogDTO;
import com.sino.ams.newasset.constant.*;
import com.sino.ams.newasset.dao.AmsItemCorrectLogDAO;
import com.sino.ams.newasset.dao.ItemMaintainDAO3;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.model.ItemMaintainModel3;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-3-14
 * Time: 13:47:46
 * To change this template use File | Settings | File Templates.
 */
public class ItemMaintainServlet3 extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            user.setProvinceCode(getServletConfig(req).getProvinceCode());
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
            AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) req2DTO.getDTO(req);
            String deptName = dto.getDeptName();
            int index = deptName.indexOf("(");
            if (index > -1) {
                deptName = deptName.substring(0, index);
                dto.setDeptName(deptName);
            }
            String action = dto.getAct();
            conn = getDBConnection(req);
            ItemMaintainDAO3 maintainDAO = new ItemMaintainDAO3(user, dto, conn);
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            if (action.equals("")) {
                produceWebComponent(dto, optProducer, user, req);
                forwardURL = "/newasset/itemData3.jsp";
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
            	if( user.isProvAssetsManager() ){
            		dto = this.doSetOrgId(dto, conn);
            	}
                BaseSQLProducer sqlProducer = new ItemMaintainModel3(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setPageSize(20);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();

                produceWebComponent(dto, optProducer, user, req);
                forwardURL = "/newasset/itemData3.jsp";
            } else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
                maintainDAO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
                dto = (AmsAssetsAddressVDTO) maintainDAO.getDataByPrimaryKey();
                AmsItemCorrectLogDTO logDTO = new AmsItemCorrectLogDTO();
                logDTO.setBarcode(dto.getBarcode());
                AmsItemCorrectLogDAO logDAO = new AmsItemCorrectLogDAO(user, logDTO, conn);
                logDAO.setDTOClassName(AmsItemCorrectLogDTO.class.getName());
                DTOSet barcodeLogs = (DTOSet) logDAO.getDataByForeignKey("barcode");
                req.setAttribute(AssetsWebAttributes.BARCODE_LOGS, barcodeLogs);
                req.setAttribute(AssetsWebAttributes.ITEM_INFO_DTO, dto);
                forwardURL = AssetsURLList.ITEM_DETAIL_PAGE;
            } else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
            	String excelType = req.getParameter("excelType");
                File file = maintainDAO.getExportFile(excelType);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else {
                message = getMessage(AssetsMessageKeys.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!forwardURL.equals("")) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
        }
    }

    private void produceWebComponent(AmsAssetsAddressVDTO dto,
                                     AssetsOptProducer optProducer,
                                     SfUserDTO user,
                                     HttpServletRequest req) throws QueryException, ContainerException {
        String opt = optProducer.getItemCategoryOption(dto.getItemCategory());
        dto.setItemCategoryOpt(opt);
        opt = optProducer.getMainCompanyOption(dto.getMaintainCompany());
        dto.setMaintainCompanyOpt(opt);
        dto.setFinancePropOpt(optProducer.getFinancePropOption(dto.getFinanceProp() , true ));
        dto.setAreaTypeOpt(optProducer.getDictOption("ADDR_AREA_TYPE", dto.getAreaType()));
        dto.setCityOpt(optProducer.getCityOption(dto.getCity()));
        dto.setCountyOpt(optProducer.getOuCountyOptions(dto.getCity(), dto.getCounty()));

        String itemStatus = optProducer.getDictOption(AssetsDictConstant.ITEM_STATUS, dto.getItemStatus());
        req.setAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS, itemStatus);
        String deptOpt = "";
        if (user.getProvinceCode().equals(AssetsDictConstant.PROVINCE_CODE_SX)) {
            deptOpt = optProducer.getALLUserAsssetsDeptOption(dto.getResponsibilityDept(), user.getCompanyCode());
        } else {
            deptOpt = optProducer.getUserAsssetsDeptOption(dto.getResponsibilityDept());
        }
        req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);
        
        String shareOption = optProducer.getDictOption("SHARE_STATUS", dto.getShare());
		req.setAttribute("SHARE_OPTION", shareOption);
        String constructStatusOption = optProducer.getDictOption("CONSTRUCT_STATUS", dto.getConstructStatus());
		req.setAttribute("CONSTRUCT_OPTION", constructStatusOption);
		
        if (StrUtil.isEmpty(dto.getCompany())) {
            dto.setOrganizationId(user.getOrganizationId());
            dto.setCompany(user.getCompany());
        }
        String specialDepOpt = optProducer.getSpecialAsssetsDeptOption(dto.getSpecialityDept());
        req.setAttribute("DEPT_OPTIONS2", specialDepOpt);
        String allDeptOption = optProducer.getALLUserAsssetsDeptOption(dto.getMaintainDept(), user.getCompanyCode());
        req.setAttribute("DEPT_OPTIONS3", allDeptOption);
        req.setAttribute(QueryConstant.QUERY_DTO, dto);
    }
    
    private AmsAssetsAddressVDTO doSetOrgId( AmsAssetsAddressVDTO dto , Connection conn  ) throws QueryException, NumberFormatException, ContainerException{
    	
    	if( !StrUtil.isEmpty( dto.getCompany() ) ){
    		SQLModel sqlModel = new SQLModel();
    		List sqlArgs = new ArrayList();
    		String sqlStr = "SELECT EOCM.ORGANIZATION_ID," +
							" EOCM.MATCH_ORGANIZATION_ID, \n" +
							" EOCM.IS_TD \n" +
							"  FROM ETS_OU_CITY_MAP EOCM\n" +
							" WHERE EOCM.COMPANY = ? \n"  ;
            sqlArgs.add( dto.getCompany() );
    		sqlModel.setSqlStr(sqlStr);
    		sqlModel.setArgs(sqlArgs);
    		
    		SimpleQuery query = new SimpleQuery(sqlModel, conn);
    		query.executeQuery();
    		
    		if( query.hasResult() ){
    			Row row = query.getSearchResult().getRow( 0 );
    			String orgId = row.getStrValue( "ORGANIZATION_ID" );
    			if( !StrUtil.isEmpty( orgId ) ){
    				dto.setOrganizationId( Integer.parseInt( orgId ) );
    				String matchOrgId = row.getStrValue( "MATCH_ORGANIZATION_ID" );
    				if( !StrUtil.isEmpty( matchOrgId ) ){
    					dto.setMatchOrganizationId( Integer.parseInt( matchOrgId ) );
    				}else{
    					dto.setMatchOrganizationId( Integer.parseInt( orgId )  );
    				}
    				dto.setIsTD( row.getStrValue( "IS_TD" ) );
    			}
    		}
    	}
    	return dto;
    }
}

