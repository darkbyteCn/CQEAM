package com.sino.ams.system.basepoint.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.basepoint.dto.EtsObjectAttributeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsObjectAttributeModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsObjectAttributeModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsObjectAttributeModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：资产地点属性扩展弹性域 ETS_OBJECT_ATTRIBUTE 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsObjectAttributeDTO 本次操作的数据
	 */
	public EtsObjectAttributeModel(SfUserDTO userAccount, EtsObjectAttributeDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	/**
	 * 功能：框架自动生成资产地点属性扩展弹性域 ETS_OBJECT_ATTRIBUTE数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectAttributeDTO etsObjectAttribute = (EtsObjectAttributeDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " ETS_OBJECT_ATTRIBUTE("
			+ " OBJECT_NO,"
			+ " ATTRIBUTE1,"
			+ " ATTRIBUTE2,"
			+ " ATTRIBUTE3,"
			+ " ATTRIBUTE4,"
			+ " ATTRIBUTE5,"
			+ " ATTRIBUTE6,"
			+ " ATTRIBUTE7,"
			+ " ATTRIBUTE8,"
			+ " ATTRIBUTE9,"
			+ " ATTRIBUTE10,"
			+ " ATTRIBUTE11,"
			+ " ATTRIBUTE12,"
			+ " ATTRIBUTE13,"
			+ " ATTRIBUTE14,"
			+ " ATTRIBUTE15,"
			+ " ATTRIBUTE16,"
			+ " ATTRIBUTE17,"
			+ " ATTRIBUTE18,"
			+ " ATTRIBUTE19,"
			+ " ATTRIBUTE20,"
			+ " ATTRIBUTE21,"
			+ " ATTRIBUTE22,"
			+ " ATTRIBUTE23,"
			+ " ATTRIBUTE24,"
			+ " ATTRIBUTE25,"
			+ " ATTRIBUTE26,"
			+ " ATTRIBUTE27,"
			+ " ATTRIBUTE28,"
			+ " ATTRIBUTE29,"
			+ " ATTRIBUTE30,"
			+ " ATTRIBUTE31,"
			+ " ATTRIBUTE32,"
			+ " ATTRIBUTE33,"
			+ " ATTRIBUTE34,"
			+ " ATTRIBUTE35,"
			+ " ATTRIBUTE36,"
			+ " ATTRIBUTE37,"
			+ " ATTRIBUTE38,"
			+ " ATTRIBUTE39,"
			+ " ATTRIBUTE40,"
			+ " ATTRIBUTE41,"
			+ " ATTRIBUTE42,"
			+ " ATTRIBUTE43,"
			+ " ATTRIBUTE44,"
			+ " ATTRIBUTE45,"
			+ " ATTRIBUTE46,"
			+ " ATTRIBUTE47,"
			+ " ATTRIBUTE48,"
			+ " ATTRIBUTE49,"
			+ " ATTRIBUTE50,"
			+ " ATTRIBUTE51,"
			+ " ATTRIBUTE52,"
			+ " ATTRIBUTE53,"
			+ " ATTRIBUTE54,"
			+ " ATTRIBUTE55,"
			+ " ATTRIBUTE56,"
			+ " ATTRIBUTE57,"
			+ " ATTRIBUTE58,"
			+ " ATTRIBUTE59,"
			+ " ATTRIBUTE60,"
			+ " ATTRIBUTE61,"
			+ " ATTRIBUTE62,"
			+ " ATTRIBUTE63,"
			+ " ATTRIBUTE64,"
			+ " ATTRIBUTE65"
			+ ") VALUES ("
			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		sqlArgs.add(etsObjectAttribute.getObjectNo());
        sqlArgs.add(etsObjectAttribute.getAttribute1());
		sqlArgs.add(etsObjectAttribute.getAttribute2());
		sqlArgs.add(etsObjectAttribute.getAttribute3());
		sqlArgs.add(etsObjectAttribute.getAttribute4());
		sqlArgs.add(etsObjectAttribute.getAttribute5());
		sqlArgs.add(etsObjectAttribute.getAttribute6());
		sqlArgs.add(etsObjectAttribute.getAttribute7());
		sqlArgs.add(etsObjectAttribute.getAttribute8());
		sqlArgs.add(etsObjectAttribute.getAttribute9());
		sqlArgs.add(etsObjectAttribute.getAttribute10());
		sqlArgs.add(etsObjectAttribute.getAttribute11());
		sqlArgs.add(etsObjectAttribute.getAttribute12());
		sqlArgs.add(etsObjectAttribute.getAttribute13());
		sqlArgs.add(etsObjectAttribute.getAttribute14());
		sqlArgs.add(etsObjectAttribute.getAttribute15());
		sqlArgs.add(etsObjectAttribute.getAttribute16());
		sqlArgs.add(etsObjectAttribute.getAttribute17());
		sqlArgs.add(etsObjectAttribute.getAttribute18());
		sqlArgs.add(etsObjectAttribute.getAttribute19());
		sqlArgs.add(etsObjectAttribute.getAttribute20());
		sqlArgs.add(etsObjectAttribute.getAttribute21());
		sqlArgs.add(etsObjectAttribute.getAttribute22());
		sqlArgs.add(etsObjectAttribute.getAttribute23());
		sqlArgs.add(etsObjectAttribute.getAttribute24());
		sqlArgs.add(etsObjectAttribute.getAttribute25());
		sqlArgs.add(etsObjectAttribute.getAttribute26());
		sqlArgs.add(etsObjectAttribute.getAttribute27());
		sqlArgs.add(etsObjectAttribute.getAttribute28());
		sqlArgs.add(etsObjectAttribute.getAttribute29());
		sqlArgs.add(etsObjectAttribute.getAttribute30());
		sqlArgs.add(etsObjectAttribute.getAttribute31());
		sqlArgs.add(etsObjectAttribute.getAttribute32());
		sqlArgs.add(etsObjectAttribute.getAttribute33());
		sqlArgs.add(etsObjectAttribute.getAttribute34());
		sqlArgs.add(etsObjectAttribute.getAttribute35());
		sqlArgs.add(etsObjectAttribute.getAttribute36());
		sqlArgs.add(etsObjectAttribute.getAttribute37());
		sqlArgs.add(etsObjectAttribute.getAttribute38());
		sqlArgs.add(etsObjectAttribute.getAttribute39());
		sqlArgs.add(etsObjectAttribute.getAttribute40());
		sqlArgs.add(etsObjectAttribute.getAttribute41());
		sqlArgs.add(etsObjectAttribute.getAttribute42());
		sqlArgs.add(etsObjectAttribute.getAttribute43());
		sqlArgs.add(etsObjectAttribute.getAttribute44());
		sqlArgs.add(etsObjectAttribute.getAttribute45());
		sqlArgs.add(etsObjectAttribute.getAttribute46());
		sqlArgs.add(etsObjectAttribute.getAttribute47());
		sqlArgs.add(etsObjectAttribute.getAttribute48());
		sqlArgs.add(etsObjectAttribute.getAttribute49());
		sqlArgs.add(etsObjectAttribute.getAttribute50());
		sqlArgs.add(etsObjectAttribute.getAttribute51());
		sqlArgs.add(etsObjectAttribute.getAttribute52());
		sqlArgs.add(etsObjectAttribute.getAttribute53());
		sqlArgs.add(etsObjectAttribute.getAttribute54());
		sqlArgs.add(etsObjectAttribute.getAttribute55());
		sqlArgs.add(etsObjectAttribute.getAttribute56());
		sqlArgs.add(etsObjectAttribute.getAttribute57());
		sqlArgs.add(etsObjectAttribute.getAttribute58());
		sqlArgs.add(etsObjectAttribute.getAttribute59());
		sqlArgs.add(etsObjectAttribute.getAttribute60());
		sqlArgs.add(etsObjectAttribute.getAttribute61());
		sqlArgs.add(etsObjectAttribute.getAttribute62());
		sqlArgs.add(etsObjectAttribute.getAttribute63());
		sqlArgs.add(etsObjectAttribute.getAttribute64());
		sqlArgs.add(etsObjectAttribute.getAttribute65());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产地点属性扩展弹性域 ETS_OBJECT_ATTRIBUTE数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectAttributeDTO etsObjectAttribute = (EtsObjectAttributeDTO)dtoParameter;
		String sqlStr = "UPDATE ETS_OBJECT_ATTRIBUTE"
			+ " SET"
			+ " ATTRIBUTE1 = ?,"
			+ " ATTRIBUTE2 = ?,"
			+ " ATTRIBUTE3 = ?,"
			+ " ATTRIBUTE4 = ?,"
			+ " ATTRIBUTE5 = ?,"
			+ " ATTRIBUTE6 = ?,"
			+ " ATTRIBUTE7 = ?,"
			+ " ATTRIBUTE8 = ?,"
			+ " ATTRIBUTE9 = ?,"
			+ " ATTRIBUTE10 = ?,"
			+ " ATTRIBUTE11 = ?,"
			+ " ATTRIBUTE12 = ?,"
			+ " ATTRIBUTE13 = ?,"
			+ " ATTRIBUTE14 = ?,"
			+ " ATTRIBUTE15 = ?,"
			+ " ATTRIBUTE16 = ?,"
			+ " ATTRIBUTE17 = ?,"
			+ " ATTRIBUTE18 = ?,"
			+ " ATTRIBUTE19 = ?,"
			+ " ATTRIBUTE20 = ?,"
			+ " ATTRIBUTE21 = ?,"
			+ " ATTRIBUTE22 = ?,"
			+ " ATTRIBUTE23 = ?,"
			+ " ATTRIBUTE24 = ?,"
			+ " ATTRIBUTE25 = ?,"
			+ " ATTRIBUTE26 = ?,"
			+ " ATTRIBUTE27 = ?,"
			+ " ATTRIBUTE28 = ?,"
			+ " ATTRIBUTE29 = ?,"
			+ " ATTRIBUTE30 = ?,"
			+ " ATTRIBUTE31 = ?,"
			+ " ATTRIBUTE32 = ?,"
			+ " ATTRIBUTE33 = ?,"
			+ " ATTRIBUTE34 = ?,"
			+ " ATTRIBUTE35 = ?,"
			+ " ATTRIBUTE36 = ?,"
			+ " ATTRIBUTE37 = ?,"
			+ " ATTRIBUTE38 = ?,"
			+ " ATTRIBUTE39 = ?,"
			+ " ATTRIBUTE40 = ?,"
			+ " ATTRIBUTE41 = ?,"
			+ " ATTRIBUTE42 = ?,"
			+ " ATTRIBUTE43 = ?,"
			+ " ATTRIBUTE44 = ?,"
			+ " ATTRIBUTE45 = ?,"
			+ " ATTRIBUTE46 = ?,"
			+ " ATTRIBUTE47 = ?,"
			+ " ATTRIBUTE48 = ?,"
			+ " ATTRIBUTE49 = ?,"
			+ " ATTRIBUTE50 = ?,"
			+ " ATTRIBUTE51 = ?,"
			+ " ATTRIBUTE52 = ?,"
			+ " ATTRIBUTE53 = ?,"
			+ " ATTRIBUTE54 = ?,"
			+ " ATTRIBUTE55 = ?,"
			+ " ATTRIBUTE56 = ?,"
			+ " ATTRIBUTE57 = ?,"
			+ " ATTRIBUTE58 = ?,"
			+ " ATTRIBUTE59 = ?,"
			+ " ATTRIBUTE60 = ?,"
			+ " ATTRIBUTE61 = ?,"
			+ " ATTRIBUTE62 = ?,"
			+ " ATTRIBUTE63 = ?,"
			+ " ATTRIBUTE64 = ?,"
			+ " ATTRIBUTE65 = ?"
			+ " WHERE"
			+ " OBJECT_NO = ?";
		
		sqlArgs.add(etsObjectAttribute.getAttribute1());
		sqlArgs.add(etsObjectAttribute.getAttribute2());
		sqlArgs.add(etsObjectAttribute.getAttribute3());
		sqlArgs.add(etsObjectAttribute.getAttribute4());
		sqlArgs.add(etsObjectAttribute.getAttribute5());
		sqlArgs.add(etsObjectAttribute.getAttribute6());
		sqlArgs.add(etsObjectAttribute.getAttribute7());
		sqlArgs.add(etsObjectAttribute.getAttribute8());
		sqlArgs.add(etsObjectAttribute.getAttribute9());
		sqlArgs.add(etsObjectAttribute.getAttribute10());
		sqlArgs.add(etsObjectAttribute.getAttribute11());
		sqlArgs.add(etsObjectAttribute.getAttribute12());
		sqlArgs.add(etsObjectAttribute.getAttribute13());
		sqlArgs.add(etsObjectAttribute.getAttribute14());
		sqlArgs.add(etsObjectAttribute.getAttribute15());
		sqlArgs.add(etsObjectAttribute.getAttribute16());
		sqlArgs.add(etsObjectAttribute.getAttribute17());
		sqlArgs.add(etsObjectAttribute.getAttribute18());
		sqlArgs.add(etsObjectAttribute.getAttribute19());
		sqlArgs.add(etsObjectAttribute.getAttribute20());
		sqlArgs.add(etsObjectAttribute.getAttribute21());
		sqlArgs.add(etsObjectAttribute.getAttribute22());
		sqlArgs.add(etsObjectAttribute.getAttribute23());
		sqlArgs.add(etsObjectAttribute.getAttribute24());
		sqlArgs.add(etsObjectAttribute.getAttribute25());
		sqlArgs.add(etsObjectAttribute.getAttribute26());
		sqlArgs.add(etsObjectAttribute.getAttribute27());
		sqlArgs.add(etsObjectAttribute.getAttribute28());
		sqlArgs.add(etsObjectAttribute.getAttribute29());
		sqlArgs.add(etsObjectAttribute.getAttribute30());
		sqlArgs.add(etsObjectAttribute.getAttribute31());
		sqlArgs.add(etsObjectAttribute.getAttribute32());
		sqlArgs.add(etsObjectAttribute.getAttribute33());
		sqlArgs.add(etsObjectAttribute.getAttribute34());
		sqlArgs.add(etsObjectAttribute.getAttribute35());
		sqlArgs.add(etsObjectAttribute.getAttribute36());
		sqlArgs.add(etsObjectAttribute.getAttribute37());
		sqlArgs.add(etsObjectAttribute.getAttribute38());
		sqlArgs.add(etsObjectAttribute.getAttribute39());
		sqlArgs.add(etsObjectAttribute.getAttribute40());
		sqlArgs.add(etsObjectAttribute.getAttribute41());
		sqlArgs.add(etsObjectAttribute.getAttribute42());
		sqlArgs.add(etsObjectAttribute.getAttribute43());
		sqlArgs.add(etsObjectAttribute.getAttribute44());
		sqlArgs.add(etsObjectAttribute.getAttribute45());
		sqlArgs.add(etsObjectAttribute.getAttribute46());
		sqlArgs.add(etsObjectAttribute.getAttribute47());
		sqlArgs.add(etsObjectAttribute.getAttribute48());
		sqlArgs.add(etsObjectAttribute.getAttribute49());
		sqlArgs.add(etsObjectAttribute.getAttribute50());
		sqlArgs.add(etsObjectAttribute.getAttribute51());
		sqlArgs.add(etsObjectAttribute.getAttribute52());
		sqlArgs.add(etsObjectAttribute.getAttribute53());
		sqlArgs.add(etsObjectAttribute.getAttribute54());
		sqlArgs.add(etsObjectAttribute.getAttribute55());
		sqlArgs.add(etsObjectAttribute.getAttribute56());
		sqlArgs.add(etsObjectAttribute.getAttribute57());
		sqlArgs.add(etsObjectAttribute.getAttribute58());
		sqlArgs.add(etsObjectAttribute.getAttribute59());
		sqlArgs.add(etsObjectAttribute.getAttribute60());
		sqlArgs.add(etsObjectAttribute.getAttribute61());
		sqlArgs.add(etsObjectAttribute.getAttribute62());
		sqlArgs.add(etsObjectAttribute.getAttribute63());
		sqlArgs.add(etsObjectAttribute.getAttribute64());
		sqlArgs.add(etsObjectAttribute.getAttribute65());
		sqlArgs.add(etsObjectAttribute.getObjectNo());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产地点属性扩展弹性域 ETS_OBJECT_ATTRIBUTE数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectAttributeDTO etsObjectAttribute = (EtsObjectAttributeDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
			+ " ETS_OBJECT_ATTRIBUTE"
			+ " WHERE"
			+ " OBJECT_NO = ?";
		sqlArgs.add(etsObjectAttribute.getObjectNo());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产地点属性扩展弹性域 ETS_OBJECT_ATTRIBUTE数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
        EtsObjectAttributeDTO etsObjectAttribute = (EtsObjectAttributeDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " OBJECT_NO,"
			+ " ATTRIBUTE1,"
			+ " ATTRIBUTE2,"
			+ " ATTRIBUTE3,"
			+ " ATTRIBUTE4,"
			+ " ATTRIBUTE5,"
			+ " ATTRIBUTE6,"
			+ " ATTRIBUTE7,"
			+ " ATTRIBUTE8,"
			+ " ATTRIBUTE9,"
			+ " ATTRIBUTE10,"
			+ " ATTRIBUTE11,"
			+ " ATTRIBUTE12,"
			+ " ATTRIBUTE13,"
			+ " ATTRIBUTE14,"
			+ " ATTRIBUTE15,"
			+ " ATTRIBUTE16,"
			+ " ATTRIBUTE17,"
			+ " ATTRIBUTE18,"
			+ " ATTRIBUTE19,"
			+ " ATTRIBUTE20,"
			+ " ATTRIBUTE21,"
			+ " ATTRIBUTE22,"
			+ " ATTRIBUTE23,"
			+ " ATTRIBUTE24,"
			+ " ATTRIBUTE25,"
			+ " ATTRIBUTE26,"
			+ " ATTRIBUTE27,"
			+ " ATTRIBUTE28,"
			+ " ATTRIBUTE29,"
			+ " ATTRIBUTE30,"
			+ " ATTRIBUTE31,"
			+ " ATTRIBUTE32,"
			+ " ATTRIBUTE33,"
			+ " ATTRIBUTE34,"
			+ " ATTRIBUTE35,"
			+ " ATTRIBUTE36,"
			+ " ATTRIBUTE37,"
			+ " ATTRIBUTE38,"
			+ " ATTRIBUTE39,"
			+ " ATTRIBUTE40,"
			+ " ATTRIBUTE41,"
			+ " ATTRIBUTE42,"
			+ " ATTRIBUTE43,"
			+ " ATTRIBUTE44,"
			+ " ATTRIBUTE45,"
			+ " ATTRIBUTE46,"
			+ " ATTRIBUTE47,"
			+ " ATTRIBUTE48,"
			+ " ATTRIBUTE49,"
			+ " ATTRIBUTE50,"
			+ " ATTRIBUTE51,"
			+ " ATTRIBUTE52,"
			+ " ATTRIBUTE53,"
			+ " ATTRIBUTE54,"
			+ " ATTRIBUTE55,"
			+ " ATTRIBUTE56,"
			+ " ATTRIBUTE57,"
			+ " ATTRIBUTE58,"
			+ " ATTRIBUTE59,"
			+ " ATTRIBUTE60,"
			+ " ATTRIBUTE61,"
			+ " ATTRIBUTE62,"
			+ " ATTRIBUTE63,"
			+ " ATTRIBUTE64,"
			+ " ATTRIBUTE65"
			+ " FROM"
			+ " ETS_OBJECT_ATTRIBUTE"
			+ " WHERE"
			+ " OBJECT_NO = ?";
		sqlArgs.add(etsObjectAttribute.getObjectNo());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产地点属性扩展弹性域 ETS_OBJECT_ATTRIBUTE多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getDataMuxModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectAttributeDTO etsObjectAttribute = (EtsObjectAttributeDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " OBJECT_NO,"
			+ " ATTRIBUTE1,"
			+ " ATTRIBUTE2,"
			+ " ATTRIBUTE3,"
			+ " ATTRIBUTE4,"
			+ " ATTRIBUTE5,"
			+ " ATTRIBUTE6,"
			+ " ATTRIBUTE7,"
			+ " ATTRIBUTE8,"
			+ " ATTRIBUTE9,"
			+ " ATTRIBUTE10,"
			+ " ATTRIBUTE11,"
			+ " ATTRIBUTE12,"
			+ " ATTRIBUTE13,"
			+ " ATTRIBUTE14,"
			+ " ATTRIBUTE15,"
			+ " ATTRIBUTE16,"
			+ " ATTRIBUTE17,"
			+ " ATTRIBUTE18,"
			+ " ATTRIBUTE19,"
			+ " ATTRIBUTE20,"
			+ " ATTRIBUTE21,"
			+ " ATTRIBUTE22,"
			+ " ATTRIBUTE23,"
			+ " ATTRIBUTE24,"
			+ " ATTRIBUTE25,"
			+ " ATTRIBUTE26,"
			+ " ATTRIBUTE27,"
			+ " ATTRIBUTE28,"
			+ " ATTRIBUTE29,"
			+ " ATTRIBUTE30,"
			+ " ATTRIBUTE31,"
			+ " ATTRIBUTE32,"
			+ " ATTRIBUTE33,"
			+ " ATTRIBUTE34,"
			+ " ATTRIBUTE35,"
			+ " ATTRIBUTE36,"
			+ " ATTRIBUTE37,"
			+ " ATTRIBUTE38,"
			+ " ATTRIBUTE39,"
			+ " ATTRIBUTE40,"
			+ " ATTRIBUTE41,"
			+ " ATTRIBUTE42,"
			+ " ATTRIBUTE43,"
			+ " ATTRIBUTE44,"
			+ " ATTRIBUTE45,"
			+ " ATTRIBUTE46,"
			+ " ATTRIBUTE47,"
			+ " ATTRIBUTE48,"
			+ " ATTRIBUTE49,"
			+ " ATTRIBUTE50,"
			+ " ATTRIBUTE51,"
			+ " ATTRIBUTE52,"
			+ " ATTRIBUTE53,"
			+ " ATTRIBUTE54,"
			+ " ATTRIBUTE55,"
			+ " ATTRIBUTE56,"
			+ " ATTRIBUTE57,"
			+ " ATTRIBUTE58,"
			+ " ATTRIBUTE59,"
			+ " ATTRIBUTE60,"
			+ " ATTRIBUTE61,"
			+ " ATTRIBUTE62,"
			+ " ATTRIBUTE63,"
			+ " ATTRIBUTE64,"
			+ " ATTRIBUTE65"
			+ " FROM"
			+ " ETS_OBJECT_ATTRIBUTE"
			+ " WHERE"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR OBJECT_NO LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE1 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE2 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE3 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE4 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE5 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE6 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE7 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE8 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE9 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE10 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE11 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE12 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE13 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE14 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE15 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE16 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE17 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE18 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE19 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE20 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE21 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE22 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE23 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE24 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE25 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE26 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE27 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE28 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE29 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE30 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE31 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE32 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE33 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE34 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE35 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE36 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE37 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE38 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE39 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE40 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE41 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE42 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE43 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE44 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE45 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE46 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE47 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE48 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE49 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE50 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE51 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE52 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE53 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE54 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE55 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE56 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE57 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE58 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE59 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE60 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE61 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE62 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE63 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE64 LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE65 LIKE ?)";
		sqlArgs.add(etsObjectAttribute.getObjectNo());
		sqlArgs.add(etsObjectAttribute.getObjectNo());
		sqlArgs.add(etsObjectAttribute.getAttribute1());
		sqlArgs.add(etsObjectAttribute.getAttribute1());
		sqlArgs.add(etsObjectAttribute.getAttribute2());
		sqlArgs.add(etsObjectAttribute.getAttribute2());
		sqlArgs.add(etsObjectAttribute.getAttribute3());
		sqlArgs.add(etsObjectAttribute.getAttribute3());
		sqlArgs.add(etsObjectAttribute.getAttribute4());
		sqlArgs.add(etsObjectAttribute.getAttribute4());
		sqlArgs.add(etsObjectAttribute.getAttribute5());
		sqlArgs.add(etsObjectAttribute.getAttribute5());
		sqlArgs.add(etsObjectAttribute.getAttribute6());
		sqlArgs.add(etsObjectAttribute.getAttribute6());
		sqlArgs.add(etsObjectAttribute.getAttribute7());
		sqlArgs.add(etsObjectAttribute.getAttribute7());
		sqlArgs.add(etsObjectAttribute.getAttribute8());
		sqlArgs.add(etsObjectAttribute.getAttribute8());
		sqlArgs.add(etsObjectAttribute.getAttribute9());
		sqlArgs.add(etsObjectAttribute.getAttribute9());
		sqlArgs.add(etsObjectAttribute.getAttribute10());
		sqlArgs.add(etsObjectAttribute.getAttribute10());
		sqlArgs.add(etsObjectAttribute.getAttribute11());
		sqlArgs.add(etsObjectAttribute.getAttribute11());
		sqlArgs.add(etsObjectAttribute.getAttribute12());
		sqlArgs.add(etsObjectAttribute.getAttribute12());
		sqlArgs.add(etsObjectAttribute.getAttribute13());
		sqlArgs.add(etsObjectAttribute.getAttribute13());
		sqlArgs.add(etsObjectAttribute.getAttribute14());
		sqlArgs.add(etsObjectAttribute.getAttribute14());
		sqlArgs.add(etsObjectAttribute.getAttribute15());
		sqlArgs.add(etsObjectAttribute.getAttribute15());
		sqlArgs.add(etsObjectAttribute.getAttribute16());
		sqlArgs.add(etsObjectAttribute.getAttribute16());
		sqlArgs.add(etsObjectAttribute.getAttribute17());
		sqlArgs.add(etsObjectAttribute.getAttribute17());
		sqlArgs.add(etsObjectAttribute.getAttribute18());
		sqlArgs.add(etsObjectAttribute.getAttribute18());
		sqlArgs.add(etsObjectAttribute.getAttribute19());
		sqlArgs.add(etsObjectAttribute.getAttribute19());
		sqlArgs.add(etsObjectAttribute.getAttribute20());
		sqlArgs.add(etsObjectAttribute.getAttribute20());
		sqlArgs.add(etsObjectAttribute.getAttribute21());
		sqlArgs.add(etsObjectAttribute.getAttribute21());
		sqlArgs.add(etsObjectAttribute.getAttribute22());
		sqlArgs.add(etsObjectAttribute.getAttribute22());
		sqlArgs.add(etsObjectAttribute.getAttribute23());
		sqlArgs.add(etsObjectAttribute.getAttribute23());
		sqlArgs.add(etsObjectAttribute.getAttribute24());
		sqlArgs.add(etsObjectAttribute.getAttribute24());
		sqlArgs.add(etsObjectAttribute.getAttribute25());
		sqlArgs.add(etsObjectAttribute.getAttribute25());
		sqlArgs.add(etsObjectAttribute.getAttribute26());
		sqlArgs.add(etsObjectAttribute.getAttribute26());
		sqlArgs.add(etsObjectAttribute.getAttribute27());
		sqlArgs.add(etsObjectAttribute.getAttribute27());
		sqlArgs.add(etsObjectAttribute.getAttribute28());
		sqlArgs.add(etsObjectAttribute.getAttribute28());
		sqlArgs.add(etsObjectAttribute.getAttribute29());
		sqlArgs.add(etsObjectAttribute.getAttribute29());
		sqlArgs.add(etsObjectAttribute.getAttribute30());
		sqlArgs.add(etsObjectAttribute.getAttribute30());
		sqlArgs.add(etsObjectAttribute.getAttribute31());
		sqlArgs.add(etsObjectAttribute.getAttribute31());
		sqlArgs.add(etsObjectAttribute.getAttribute32());
		sqlArgs.add(etsObjectAttribute.getAttribute32());
		sqlArgs.add(etsObjectAttribute.getAttribute33());
		sqlArgs.add(etsObjectAttribute.getAttribute33());
		sqlArgs.add(etsObjectAttribute.getAttribute34());
		sqlArgs.add(etsObjectAttribute.getAttribute34());
		sqlArgs.add(etsObjectAttribute.getAttribute35());
		sqlArgs.add(etsObjectAttribute.getAttribute35());
		sqlArgs.add(etsObjectAttribute.getAttribute36());
		sqlArgs.add(etsObjectAttribute.getAttribute36());
		sqlArgs.add(etsObjectAttribute.getAttribute37());
		sqlArgs.add(etsObjectAttribute.getAttribute37());
		sqlArgs.add(etsObjectAttribute.getAttribute38());
		sqlArgs.add(etsObjectAttribute.getAttribute38());
		sqlArgs.add(etsObjectAttribute.getAttribute39());
		sqlArgs.add(etsObjectAttribute.getAttribute39());
		sqlArgs.add(etsObjectAttribute.getAttribute40());
		sqlArgs.add(etsObjectAttribute.getAttribute40());
		sqlArgs.add(etsObjectAttribute.getAttribute41());
		sqlArgs.add(etsObjectAttribute.getAttribute41());
		sqlArgs.add(etsObjectAttribute.getAttribute42());
		sqlArgs.add(etsObjectAttribute.getAttribute42());
		sqlArgs.add(etsObjectAttribute.getAttribute43());
		sqlArgs.add(etsObjectAttribute.getAttribute43());
		sqlArgs.add(etsObjectAttribute.getAttribute44());
		sqlArgs.add(etsObjectAttribute.getAttribute44());
		sqlArgs.add(etsObjectAttribute.getAttribute45());
		sqlArgs.add(etsObjectAttribute.getAttribute45());
		sqlArgs.add(etsObjectAttribute.getAttribute46());
		sqlArgs.add(etsObjectAttribute.getAttribute46());
		sqlArgs.add(etsObjectAttribute.getAttribute47());
		sqlArgs.add(etsObjectAttribute.getAttribute47());
		sqlArgs.add(etsObjectAttribute.getAttribute48());
		sqlArgs.add(etsObjectAttribute.getAttribute48());
		sqlArgs.add(etsObjectAttribute.getAttribute49());
		sqlArgs.add(etsObjectAttribute.getAttribute49());
		sqlArgs.add(etsObjectAttribute.getAttribute50());
		sqlArgs.add(etsObjectAttribute.getAttribute50());
		sqlArgs.add(etsObjectAttribute.getAttribute51());
		sqlArgs.add(etsObjectAttribute.getAttribute51());
		sqlArgs.add(etsObjectAttribute.getAttribute52());
		sqlArgs.add(etsObjectAttribute.getAttribute52());
		sqlArgs.add(etsObjectAttribute.getAttribute53());
		sqlArgs.add(etsObjectAttribute.getAttribute53());
		sqlArgs.add(etsObjectAttribute.getAttribute54());
		sqlArgs.add(etsObjectAttribute.getAttribute54());
		sqlArgs.add(etsObjectAttribute.getAttribute55());
		sqlArgs.add(etsObjectAttribute.getAttribute55());
		sqlArgs.add(etsObjectAttribute.getAttribute56());
		sqlArgs.add(etsObjectAttribute.getAttribute56());
		sqlArgs.add(etsObjectAttribute.getAttribute57());
		sqlArgs.add(etsObjectAttribute.getAttribute57());
		sqlArgs.add(etsObjectAttribute.getAttribute58());
		sqlArgs.add(etsObjectAttribute.getAttribute58());
		sqlArgs.add(etsObjectAttribute.getAttribute59());
		sqlArgs.add(etsObjectAttribute.getAttribute59());
		sqlArgs.add(etsObjectAttribute.getAttribute60());
		sqlArgs.add(etsObjectAttribute.getAttribute60());
		sqlArgs.add(etsObjectAttribute.getAttribute61());
		sqlArgs.add(etsObjectAttribute.getAttribute61());
		sqlArgs.add(etsObjectAttribute.getAttribute62());
		sqlArgs.add(etsObjectAttribute.getAttribute62());
		sqlArgs.add(etsObjectAttribute.getAttribute63());
		sqlArgs.add(etsObjectAttribute.getAttribute63());
		sqlArgs.add(etsObjectAttribute.getAttribute64());
		sqlArgs.add(etsObjectAttribute.getAttribute64());
		sqlArgs.add(etsObjectAttribute.getAttribute65());
		sqlArgs.add(etsObjectAttribute.getAttribute65());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产地点属性扩展弹性域 ETS_OBJECT_ATTRIBUTE页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectAttributeDTO etsObjectAttribute = (EtsObjectAttributeDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " OBJECT_NO,"
			+ " ATTRIBUTE1,"
			+ " ATTRIBUTE2,"
			+ " ATTRIBUTE3,"
			+ " ATTRIBUTE4,"
			+ " ATTRIBUTE5,"
			+ " ATTRIBUTE6,"
			+ " ATTRIBUTE7,"
			+ " ATTRIBUTE8,"
			+ " ATTRIBUTE9,"
			+ " ATTRIBUTE10,"
			+ " ATTRIBUTE11,"
			+ " ATTRIBUTE12,"
			+ " ATTRIBUTE13,"
			+ " ATTRIBUTE14,"
			+ " ATTRIBUTE15,"
			+ " ATTRIBUTE16,"
			+ " ATTRIBUTE17,"
			+ " ATTRIBUTE18,"
			+ " ATTRIBUTE19,"
			+ " ATTRIBUTE20,"
			+ " ATTRIBUTE21,"
			+ " ATTRIBUTE22,"
			+ " ATTRIBUTE23,"
			+ " ATTRIBUTE24,"
			+ " ATTRIBUTE25,"
			+ " ATTRIBUTE26,"
			+ " ATTRIBUTE27,"
			+ " ATTRIBUTE28,"
			+ " ATTRIBUTE29,"
			+ " ATTRIBUTE30,"
			+ " ATTRIBUTE31,"
			+ " ATTRIBUTE32,"
			+ " ATTRIBUTE33,"
			+ " ATTRIBUTE34,"
			+ " ATTRIBUTE35,"
			+ " ATTRIBUTE36,"
			+ " ATTRIBUTE37,"
			+ " ATTRIBUTE38,"
			+ " ATTRIBUTE39,"
			+ " ATTRIBUTE40,"
			+ " ATTRIBUTE41,"
			+ " ATTRIBUTE42,"
			+ " ATTRIBUTE43,"
			+ " ATTRIBUTE44,"
			+ " ATTRIBUTE45,"
			+ " ATTRIBUTE46,"
			+ " ATTRIBUTE47,"
			+ " ATTRIBUTE48,"
			+ " ATTRIBUTE49,"
			+ " ATTRIBUTE50,"
			+ " ATTRIBUTE51,"
			+ " ATTRIBUTE52,"
			+ " ATTRIBUTE53,"
			+ " ATTRIBUTE54,"
			+ " ATTRIBUTE55,"
			+ " ATTRIBUTE56,"
			+ " ATTRIBUTE57,"
			+ " ATTRIBUTE58,"
			+ " ATTRIBUTE59,"
			+ " ATTRIBUTE60,"
			+ " ATTRIBUTE61,"
			+ " ATTRIBUTE62,"
			+ " ATTRIBUTE63,"
			+ " ATTRIBUTE64,"
			+ " ATTRIBUTE65"
			+ " FROM"
			+ " ETS_OBJECT_ATTRIBUTE"
			+ " WHERE"
			+ " ( " + SyBaseSQLUtil.isNull() + "  OR OBJECT_NO LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE1 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE2 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE3 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE4 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE5 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE6 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE7 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE8 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE9 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE10 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE11 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE12 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE13 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE14 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE15 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE16 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE17 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE18 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE19 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE20 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE21 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE22 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE23 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE24 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE25 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE26 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE27 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE28 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE29 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE30 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE31 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE32 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE33 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE34 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE35 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE36 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE37 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE38 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE39 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE40 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE41 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE42 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE43 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE44 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE45 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE46 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE47 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE48 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE49 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE50 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE51 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE52 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE53 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE54 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE55 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE56 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE57 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE58 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE59 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE60 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE61 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE62 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE63 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE64 LIKE ?)"
			+ "AND ( " + SyBaseSQLUtil.isNull() + "  OR ATTRIBUTE65 LIKE ?)";
		sqlArgs.add(etsObjectAttribute.getObjectNo());
		sqlArgs.add(etsObjectAttribute.getObjectNo());
		sqlArgs.add(etsObjectAttribute.getAttribute1());
		sqlArgs.add(etsObjectAttribute.getAttribute1());
		sqlArgs.add(etsObjectAttribute.getAttribute2());
		sqlArgs.add(etsObjectAttribute.getAttribute2());
		sqlArgs.add(etsObjectAttribute.getAttribute3());
		sqlArgs.add(etsObjectAttribute.getAttribute3());
		sqlArgs.add(etsObjectAttribute.getAttribute4());
		sqlArgs.add(etsObjectAttribute.getAttribute4());
		sqlArgs.add(etsObjectAttribute.getAttribute5());
		sqlArgs.add(etsObjectAttribute.getAttribute5());
		sqlArgs.add(etsObjectAttribute.getAttribute6());
		sqlArgs.add(etsObjectAttribute.getAttribute6());
		sqlArgs.add(etsObjectAttribute.getAttribute7());
		sqlArgs.add(etsObjectAttribute.getAttribute7());
		sqlArgs.add(etsObjectAttribute.getAttribute8());
		sqlArgs.add(etsObjectAttribute.getAttribute8());
		sqlArgs.add(etsObjectAttribute.getAttribute9());
		sqlArgs.add(etsObjectAttribute.getAttribute9());
		sqlArgs.add(etsObjectAttribute.getAttribute10());
		sqlArgs.add(etsObjectAttribute.getAttribute10());
		sqlArgs.add(etsObjectAttribute.getAttribute11());
		sqlArgs.add(etsObjectAttribute.getAttribute11());
		sqlArgs.add(etsObjectAttribute.getAttribute12());
		sqlArgs.add(etsObjectAttribute.getAttribute12());
		sqlArgs.add(etsObjectAttribute.getAttribute13());
		sqlArgs.add(etsObjectAttribute.getAttribute13());
		sqlArgs.add(etsObjectAttribute.getAttribute14());
		sqlArgs.add(etsObjectAttribute.getAttribute14());
		sqlArgs.add(etsObjectAttribute.getAttribute15());
		sqlArgs.add(etsObjectAttribute.getAttribute15());
		sqlArgs.add(etsObjectAttribute.getAttribute16());
		sqlArgs.add(etsObjectAttribute.getAttribute16());
		sqlArgs.add(etsObjectAttribute.getAttribute17());
		sqlArgs.add(etsObjectAttribute.getAttribute17());
		sqlArgs.add(etsObjectAttribute.getAttribute18());
		sqlArgs.add(etsObjectAttribute.getAttribute18());
		sqlArgs.add(etsObjectAttribute.getAttribute19());
		sqlArgs.add(etsObjectAttribute.getAttribute19());
		sqlArgs.add(etsObjectAttribute.getAttribute20());
		sqlArgs.add(etsObjectAttribute.getAttribute20());
		sqlArgs.add(etsObjectAttribute.getAttribute21());
		sqlArgs.add(etsObjectAttribute.getAttribute21());
		sqlArgs.add(etsObjectAttribute.getAttribute22());
		sqlArgs.add(etsObjectAttribute.getAttribute22());
		sqlArgs.add(etsObjectAttribute.getAttribute23());
		sqlArgs.add(etsObjectAttribute.getAttribute23());
		sqlArgs.add(etsObjectAttribute.getAttribute24());
		sqlArgs.add(etsObjectAttribute.getAttribute24());
		sqlArgs.add(etsObjectAttribute.getAttribute25());
		sqlArgs.add(etsObjectAttribute.getAttribute25());
		sqlArgs.add(etsObjectAttribute.getAttribute26());
		sqlArgs.add(etsObjectAttribute.getAttribute26());
		sqlArgs.add(etsObjectAttribute.getAttribute27());
		sqlArgs.add(etsObjectAttribute.getAttribute27());
		sqlArgs.add(etsObjectAttribute.getAttribute28());
		sqlArgs.add(etsObjectAttribute.getAttribute28());
		sqlArgs.add(etsObjectAttribute.getAttribute29());
		sqlArgs.add(etsObjectAttribute.getAttribute29());
		sqlArgs.add(etsObjectAttribute.getAttribute30());
		sqlArgs.add(etsObjectAttribute.getAttribute30());
		sqlArgs.add(etsObjectAttribute.getAttribute31());
		sqlArgs.add(etsObjectAttribute.getAttribute31());
		sqlArgs.add(etsObjectAttribute.getAttribute32());
		sqlArgs.add(etsObjectAttribute.getAttribute32());
		sqlArgs.add(etsObjectAttribute.getAttribute33());
		sqlArgs.add(etsObjectAttribute.getAttribute33());
		sqlArgs.add(etsObjectAttribute.getAttribute34());
		sqlArgs.add(etsObjectAttribute.getAttribute34());
		sqlArgs.add(etsObjectAttribute.getAttribute35());
		sqlArgs.add(etsObjectAttribute.getAttribute35());
		sqlArgs.add(etsObjectAttribute.getAttribute36());
		sqlArgs.add(etsObjectAttribute.getAttribute36());
		sqlArgs.add(etsObjectAttribute.getAttribute37());
		sqlArgs.add(etsObjectAttribute.getAttribute37());
		sqlArgs.add(etsObjectAttribute.getAttribute38());
		sqlArgs.add(etsObjectAttribute.getAttribute38());
		sqlArgs.add(etsObjectAttribute.getAttribute39());
		sqlArgs.add(etsObjectAttribute.getAttribute39());
		sqlArgs.add(etsObjectAttribute.getAttribute40());
		sqlArgs.add(etsObjectAttribute.getAttribute40());
		sqlArgs.add(etsObjectAttribute.getAttribute41());
		sqlArgs.add(etsObjectAttribute.getAttribute41());
		sqlArgs.add(etsObjectAttribute.getAttribute42());
		sqlArgs.add(etsObjectAttribute.getAttribute42());
		sqlArgs.add(etsObjectAttribute.getAttribute43());
		sqlArgs.add(etsObjectAttribute.getAttribute43());
		sqlArgs.add(etsObjectAttribute.getAttribute44());
		sqlArgs.add(etsObjectAttribute.getAttribute44());
		sqlArgs.add(etsObjectAttribute.getAttribute45());
		sqlArgs.add(etsObjectAttribute.getAttribute45());
		sqlArgs.add(etsObjectAttribute.getAttribute46());
		sqlArgs.add(etsObjectAttribute.getAttribute46());
		sqlArgs.add(etsObjectAttribute.getAttribute47());
		sqlArgs.add(etsObjectAttribute.getAttribute47());
		sqlArgs.add(etsObjectAttribute.getAttribute48());
		sqlArgs.add(etsObjectAttribute.getAttribute48());
		sqlArgs.add(etsObjectAttribute.getAttribute49());
		sqlArgs.add(etsObjectAttribute.getAttribute49());
		sqlArgs.add(etsObjectAttribute.getAttribute50());
		sqlArgs.add(etsObjectAttribute.getAttribute50());
		sqlArgs.add(etsObjectAttribute.getAttribute51());
		sqlArgs.add(etsObjectAttribute.getAttribute51());
		sqlArgs.add(etsObjectAttribute.getAttribute52());
		sqlArgs.add(etsObjectAttribute.getAttribute52());
		sqlArgs.add(etsObjectAttribute.getAttribute53());
		sqlArgs.add(etsObjectAttribute.getAttribute53());
		sqlArgs.add(etsObjectAttribute.getAttribute54());
		sqlArgs.add(etsObjectAttribute.getAttribute54());
		sqlArgs.add(etsObjectAttribute.getAttribute55());
		sqlArgs.add(etsObjectAttribute.getAttribute55());
		sqlArgs.add(etsObjectAttribute.getAttribute56());
		sqlArgs.add(etsObjectAttribute.getAttribute56());
		sqlArgs.add(etsObjectAttribute.getAttribute57());
		sqlArgs.add(etsObjectAttribute.getAttribute57());
		sqlArgs.add(etsObjectAttribute.getAttribute58());
		sqlArgs.add(etsObjectAttribute.getAttribute58());
		sqlArgs.add(etsObjectAttribute.getAttribute59());
		sqlArgs.add(etsObjectAttribute.getAttribute59());
		sqlArgs.add(etsObjectAttribute.getAttribute60());
		sqlArgs.add(etsObjectAttribute.getAttribute60());
		sqlArgs.add(etsObjectAttribute.getAttribute61());
		sqlArgs.add(etsObjectAttribute.getAttribute61());
		sqlArgs.add(etsObjectAttribute.getAttribute62());
		sqlArgs.add(etsObjectAttribute.getAttribute62());
		sqlArgs.add(etsObjectAttribute.getAttribute63());
		sqlArgs.add(etsObjectAttribute.getAttribute63());
		sqlArgs.add(etsObjectAttribute.getAttribute64());
		sqlArgs.add(etsObjectAttribute.getAttribute64());
		sqlArgs.add(etsObjectAttribute.getAttribute65());
		sqlArgs.add(etsObjectAttribute.getAttribute65());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}