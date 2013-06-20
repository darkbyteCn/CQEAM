package com.sino.ams.newasset.report.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

public class DiffItemCatRptModel extends AMSSQLProducer {

	public DiffItemCatRptModel(SfUserDTO userAccount, AmsAssetsCheckLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException{
		AmsAssetsCheckLineDTO dto = (AmsAssetsCheckLineDTO) dtoParameter;
		ItemCatDetailRptModel modelProducer = new ItemCatDetailRptModel(userAccount, dto);
		return modelProducer.getDiffItemModel();
	}
}
