package com.sino.ams.net.equip.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.net.equip.dto.PlantMessageDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: Owner
 * Date: 2008-2-21
 * Time: 16:02:04
 * To change this template use File | Settings | File Templates.
 */
public class PlantMessageModel extends AMSSQLProducer {

  private SfUserDTO sfUser = null;

    /**
     * 功能：标签号信息(EAM) ETS_ITEM_INFO 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemInfoDTO 本次操作的数据
     */
    public PlantMessageModel(SfUserDTO userAccount, PlantMessageDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

   public SQLModel getPrimaryKeyDataModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		PlantMessageDTO dto = (PlantMessageDTO) dtoParameter;
		List sqlArgs = new ArrayList();
       String sqlStr ="SELECT EII.BARCODE,\n" +
               "    EFV.VALUE ITEM_CATEGORY,\n" +
               "    ESI.ITEM_CODE,\n" +
               "    ESI.ITEM_NAME,\n" +
               "    ESI.ITEM_SPEC,\n" +
               "    ESI.YEARS,\n" +
               "    ESI.VENDOR_ID,\n" +
               "    AMS_PUB_PKG.GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME,\n" +
               "    EPPA.NAME,\n" +
               "    EPPA.SEGMENT1,\n" +
               "    EO.WORKORDER_OBJECT_NAME,\n" +
               "    EO.WORKORDER_OBJECT_LOCATION,\n" +
               "    AME.USER_NAME,\n" +
               "    AMD.DEPT_NAME\n" +
               " FROM ETS_ITEM_INFO EII,\n" +
               "    ETS_SYSTEM_ITEM ESI,\n" +
               "    ETS_PA_PROJECTS_ALL EPPA,\n" +
               "    ETS_MIS_PO_VENDORS EMPV,\n" +
               "    AMS_OBJECT_ADDRESS AOA,\n" +
               "    ETS_OBJECT EO,\n" +
               "    AMS_MIS_EMPLOYEE AME,\n" +
               "    AMS_MIS_DEPT AMD,\n" +
               "    ETS_FLEX_VALUES EFV\n" +
               " WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
               "  AND ESI.ITEM_CATEGORY = EFV.CODE\n" +
               "  AND EFV.FLEX_VALUE_SET_ID='1'\n" +
               "  AND EII.PROJECTID *= EPPA.PROJECT_ID\n" +
               "  AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n" +
               "  AND AOA.OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
               "  AND EII.ADDRESS_ID *= AOA.ADDRESS_ID\n" +
               "  AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
               "  AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
               "  AND EII.BARCODE = ?";
		
		sqlArgs.add(dto.getBarcode());
        
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }

 }
