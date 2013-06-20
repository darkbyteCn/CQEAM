package com.sino.ams.oa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.QueryException;

public class SSOLoginDAO {

	private SSOLoginModel ssoLoginModel = new SSOLoginModel();
	private Connection conn = null;
	private SfUserDTO amsUser = null;

	public SSOLoginDAO(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 获取用户
	 * 
	 * @return boolean
	 * @throws QueryException
	 */
	public List<SfUserDTO> getUserDto(String oaName) throws QueryException {
		SQLModel sqlModel = ssoLoginModel.getUserLoginModel(oaName);
		SimpleQuery simp = new SimpleQuery(sqlModel, conn);
		simp.setDTOClassName(SfUserDTO.class.getName());
		simp.executeQuery();
		DTOSet dtoSet = null;
		if (simp.hasResult()) {
			dtoSet = simp.getDTOSet();
		}
		List<SfUserDTO> listUser = new ArrayList<SfUserDTO>();
		if (dtoSet != null) {
			if (dtoSet.getSize() > 0) {
				for (int i = 0; i < dtoSet.getSize(); i++) {
					SfUserDTO userDTo = (SfUserDTO) dtoSet.getDTO(i);
					listUser.add(userDTo);
				}
			}
		}
		return listUser;
	}

	public String getPendingCount(String oaName) {
		List<SfUserDTO> listUser = new ArrayList<SfUserDTO>();
		try {
			listUser = this.getUserDto(oaName);
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}int cot=0;
		for (SfUserDTO sfUserDTO : listUser) {
			SQLModel sqlModel=ssoLoginModel.getPendingCountModel(sfUserDTO.getLoginName());
			
			try {
				ResultSet result=conn.createStatement().executeQuery(sqlModel.getSqlStr());
				if(result.next())
				{
					cot+=result.getInt(1);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String count =String.valueOf(cot);
		return count;
	}
}
