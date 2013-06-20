package com.sino.ams.system.assetcatalog.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.assetcatalog.dto.AssetCatalogDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

public class AssetCatalogModel extends AMSSQLProducer {
	private SfUserDTO sfUser = null;

	public AssetCatalogModel(SfUserDTO userAccount, AssetCatalogDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	public SQLModel getPageQueryModel() throws SQLModelException { // 查询使用的sql
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AssetCatalogDTO assetCatalogDTO = (AssetCatalogDTO) dtoParameter;
		String sqlStr = "SELECT CONTENT_ID,\n" +
						"       CONTENT_CODE,\n" + 
						"       CONTENT_NAME,\n" + 
						"       CASE ENABLE WHEN 'Y' THEN '是' ELSE '否' END ENABLE,\n" + 
						"       CASE IMPORTANT_FLAG WHEN 'Y' THEN '是' ELSE '否' END IMPORTANT_FLAG,\n" + 
						"       ASSET_NAME\n" + 
						"  FROM AMS_CONTENT_DIC \n" +
						" WHERE (? = '' OR ASSET_NAME LIKE dbo.NVL(?,ASSET_NAME)) \n" +
						"   AND ENABLE = dbo.NVL(?,ENABLE) \n" +
						"   AND IMPORTANT_FLAG = dbo.NVL(?,IMPORTANT_FLAG) \n";
		
		sqlArgs.add(assetCatalogDTO.getAssetName());
		sqlArgs.add('%' + assetCatalogDTO.getAssetName() + '%');
		sqlArgs.add(assetCatalogDTO.getEnable());
		sqlArgs.add(assetCatalogDTO.getImportantFlag());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
