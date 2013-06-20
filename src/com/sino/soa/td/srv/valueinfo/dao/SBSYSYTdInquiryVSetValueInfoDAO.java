package com.sino.soa.td.srv.valueinfo.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.soa.td.srv.valueinfo.dto.SBSYSYTdInquiryVSetValueInfoDTO;
import com.sino.soa.td.srv.valueinfo.model.SBSYSYTdInquiryVSetValueInfoModel;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-14
 * Time: 14:32:53
 * To change this template use File | Settings | File Templates.
 */
public class SBSYSYTdInquiryVSetValueInfoDAO extends AMSBaseDAO {

	public SBSYSYTdInquiryVSetValueInfoDAO(SfUserDTO userAccount, SBSYSYTdInquiryVSetValueInfoDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SBSYSYTdInquiryVSetValueInfoDTO dtoPara = (SBSYSYTdInquiryVSetValueInfoDTO)dtoParameter;
		super.sqlProducer = new SBSYSYTdInquiryVSetValueInfoModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 *
	 * @param flexValue
	 * @return
	 * @throws com.sino.base.exception.QueryException
	 */
	public boolean isexistsSetValueModel(String flexValue, int flexValueSetId)throws QueryException {
		SBSYSYTdInquiryVSetValueInfoModel valueModel = (SBSYSYTdInquiryVSetValueInfoModel)sqlProducer;
        SQLModel sqlModel = valueModel.existsSetValueModel(flexValue, flexValueSetId);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        return sq.hasResult();
	}

	public List getAllFlexValues(String source) throws QueryException {
		SBSYSYTdInquiryVSetValueInfoModel valueModel = (SBSYSYTdInquiryVSetValueInfoModel)sqlProducer;
		List valueList = new ArrayList();
		SQLModel sqlModel = valueModel.getAllFlexValues(source);
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.executeQuery();
		if(simp.hasResult()){
			RowSet rs=simp.getSearchResult();
			for(int i=0;i<rs.getSize();i++){
				Row row=rs.getRow(i);
				try {
					valueList.add(row.getStrValue("FLEX_VALUE_SET_NAME"));
				} catch (ContainerException e) {
					e.printStackTrace();
				}
			}
		}
		return valueList;
	}
}