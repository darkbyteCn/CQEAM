package com.sino.ams.dzyh.bean;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sino.ams.dzyh.dto.EamDhCheckHeaderDTO;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.dto.SfUserRightDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.QueryException;
import com.sino.base.web.DatabaseForWeb;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class LvecOptProducer extends AssetsOptProducer {

	public LvecOptProducer(SfUserDTO userAccount, Connection conn) {
		super(userAccount, conn);
	}

	/**
	 * 功能：构造工单批状态下拉列表框
	 * @param selectedValue String
	 * @return String
	 * @throws QueryException
	 */
	public String getBatchStatusOpt(String selectedValue) throws QueryException {
		SQLModel sqlModel = LvecOptSQLProducer.getBatchStatusModel();
		DatabaseForWeb db2web = new DatabaseForWeb(sqlModel, conn);
		return db2web.getOptionHtml(selectedValue, true);
	}

	/**
	 * 功能：构造工单状态下拉列表框
	 * @param selectedValue String
	 * @return String
	 * @throws QueryException
	 */
	public String getOrderStatusOpt(String selectedValue) throws QueryException {
		SQLModel sqlModel = LvecOptSQLProducer.getOrderStatusModel();
		DatabaseForWeb db2web = new DatabaseForWeb(sqlModel, conn);
		return db2web.getOptionHtml(selectedValue, true);
	}


	/**
	 * 功能：构造工单执行部门下拉列表框
	 * @param dto EamDhCheckHeaderDTO
	 * @return String
	 * @throws QueryException
	 */
	public String getImpDeptOpt(EamDhCheckHeaderDTO dto) throws QueryException {
		SQLModel sqlModel = LvecOptSQLProducer.getImpDeptModel(userAccount, dto);
		DatabaseForWeb db2web = new DatabaseForWeb(sqlModel, conn);
		return db2web.getOptionHtml(dto.getImpDeptCode(), true);
	}

	/**
	 * 功能：构造工单执行部门下拉列表框
	 * @param selectedValue String
	 * @return String
	 * @throws QueryException
	 */
	public String getOrderTypeOpt(String selectedValue) throws QueryException {
		SQLModel sqlModel = LvecOptSQLProducer.getOrderTypeModel();
		DatabaseForWeb db2web = new DatabaseForWeb(sqlModel, conn);
		return db2web.getOptionHtml(selectedValue, true);
	}

	/**
	 * 功能：构造用户组别下拉框
	 * @param selectedValue String
	 * @return String
	 */
	public String getUserGroupOption(int selectedValue){
		StringBuffer groupOption = new StringBuffer();
		DTOSet userRights = userAccount.getUserRight();
		int dtoCount = userRights.getSize();
		SfUserRightDTO userRightDTO = null;
		Map groups = new HashMap();
		int groupId ;
		String groupName = "";
		groupOption.append("<option value=\"\">--请选择--</option>");
		String selectProp = "";
		for(int i = 0; i < dtoCount; i++){
			userRightDTO = (SfUserRightDTO)userRights.getDTO(i);
			groupId = userRightDTO.getGroupId();
			groupName = userRightDTO.getGroupName();
			if(!groups.containsKey(groupId)){
				if(groupId == selectedValue ){
					selectProp = " selected";
				} else {
					selectProp = "";
				}
				groups.put(groupId, groupName);
				groupOption.append("<option value=\"");
				groupOption.append(groupId);
				groupOption.append("\"");
				groupOption.append(selectProp);
				groupOption.append(">");
				groupOption.append(groupName);
				groupOption.append("</option>");
			}
		}
		return groupOption.toString();
	}
}
