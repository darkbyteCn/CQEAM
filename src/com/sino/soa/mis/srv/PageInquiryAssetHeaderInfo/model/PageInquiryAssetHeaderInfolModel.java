package com.sino.soa.mis.srv.PageInquiryAssetHeaderInfo.model;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.config.SinoConfig;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.mis.srv.PageInquiryAssetHeaderInfo.dto.InquiryAssetHeaderInfoSrvDTO; 
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: SrvAssetBookModel</p>
 * <p>Description:程序自动生成SQL构造器“SrvAssetBookModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * user:wangzp
 * function:查询资产头基本信息（分页）
 */
 
public class PageInquiryAssetHeaderInfolModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：资产账簿服务 SRV_ASSET_BOOK 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SrvAssetBookDTO 本次操作的数据
     */
    public PageInquiryAssetHeaderInfolModel(SfUserDTO userAccount, InquiryAssetHeaderInfoSrvDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：增加资产头信息
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        InquiryAssetHeaderInfoSrvDTO pageRetiredAssetDTO = (InquiryAssetHeaderInfoSrvDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " ZTE_FA_ASSET_HEADER("
                + " BOOK_TYPE_CODE,"
                + " ASSET_ID,"
                + " ASSET_NUMBER,"
                + " TAG_NUMBER,"
                + " DATE_PLACED_IN_SERVICE,"
                + " LIFE_IN_MONTH,"
                + " DEPRN_METHOD,"
                + " DEPRECIATE_FLAG , "
                + " FIXED_ASSETS_COST, "
                + " DEPRN_RESERVE, "   //  
                + " SALVAGE_VALUE, "
                + " IMPAIRMENT_RESERVE, "
                + " DESCRIPTION, "
                + " MANUFACTURER_NAME, "
                + " MODEL_NUMBER, "
                + " SERIAL_NUMBER, "
                + " IN_USE_FLAG,"
                + " INVENTORIAL ,"
                + " ASSET_SOURCE_ID,"
                + " PROJECT_NUMBER, "        //   
                + " CONSTRUCTION_STATUS, "
                + " CATEGORY_CON_SEG,"
                + " CATEGORY_DESCRIPTION,"
                + " ASSET_KEY,"
                + " ASSET_KEY_DESC,"
                + " CREATION_DATE,"
                + " LAST_UPDATE_DATE,"
                + " FIXED_ASSETS_UNIT ," 
                + " RETIREMENT_FLAG "
                + ") VALUES ("
                + " ?, CONVERT(FLOAT, ?), ?, ?, ?, CONVERT(FLOAT, ?) , ?, ?, CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), "
                + " CONVERT(FLOAT, ?), CONVERT(FLOAT, ?), ?, ?, ?, ?, ?, ?, ?, ?, "  
                + " ?, ?, ?, ?, ?, ?, ?, CONVERT(FLOAT, ?), ? )";

        sqlArgs.add(pageRetiredAssetDTO.getBookTypeCode());
        sqlArgs.add(pageRetiredAssetDTO.getAssetId());
        sqlArgs.add(pageRetiredAssetDTO.getAssetNumber());
        sqlArgs.add(pageRetiredAssetDTO.getTagNumber());
        sqlArgs.add(pageRetiredAssetDTO.getDatePlacedInService().subSequence(0, 10));
        sqlArgs.add(pageRetiredAssetDTO.getLifeInMonth());
        sqlArgs.add(pageRetiredAssetDTO.getDeprnMethod());
        sqlArgs.add(pageRetiredAssetDTO.getDepreciateFlag());
        sqlArgs.add(pageRetiredAssetDTO.getFixedAssetsCost());
        sqlArgs.add(pageRetiredAssetDTO.getDeprnReserve());

        sqlArgs.add(pageRetiredAssetDTO.getSalvageValue() );
        sqlArgs.add(pageRetiredAssetDTO.getImpairmentReserve() );
        sqlArgs.add(pageRetiredAssetDTO.getDescription() );
        sqlArgs.add(pageRetiredAssetDTO.getManufacturerName() );
        sqlArgs.add(pageRetiredAssetDTO.getModelNumber() );
        sqlArgs.add(pageRetiredAssetDTO.getSerialNumber() );
        sqlArgs.add(pageRetiredAssetDTO.getInUseFlag());
        sqlArgs.add(pageRetiredAssetDTO.getInventorial() );
        sqlArgs.add(pageRetiredAssetDTO.getAssetSourceId() );
        sqlArgs.add(pageRetiredAssetDTO.getProjectNumber() );
        
        sqlArgs.add(pageRetiredAssetDTO.getConstructionStatus() );
        sqlArgs.add(pageRetiredAssetDTO.getCategoryConSeg() );
        sqlArgs.add(pageRetiredAssetDTO.getCategoryDescription() );
        sqlArgs.add(pageRetiredAssetDTO.getAssetKey() );
        sqlArgs.add(pageRetiredAssetDTO.getAssetKeyDesc() );
        sqlArgs.add(pageRetiredAssetDTO.getCreationDate().subSequence(0, 10) );
        sqlArgs.add(pageRetiredAssetDTO.getLastUpdateDate().subSequence(0, 10));
        sqlArgs.add(pageRetiredAssetDTO.getFixedAssetsUnit() );
        sqlArgs.add(pageRetiredAssetDTO.getRetirementFlag() );
        
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：更新资产头信息表
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        InquiryAssetHeaderInfoSrvDTO pageRetiredAssetDTO = (InquiryAssetHeaderInfoSrvDTO) dtoParameter;
        String sqlStr = "UPDATE "
                + " ZTE_FA_ASSET_HEADER SET"
                + " BOOK_TYPE_CODE = ?,"
//                + " ASSET_ID,"
                + " ASSET_NUMBER = ?,"
                + " TAG_NUMBER = ?,"
                + " DATE_PLACED_IN_SERVICE = ?,"
                + " LIFE_IN_MONTH = CONVERT(FLOAT, ?) ,"
                + " DEPRN_METHOD = ?,"
                + " DEPRECIATE_FLAG = ? ,"
                + " FIXED_ASSETS_COST = CONVERT(FLOAT, ?) ,"
                + " DEPRN_RESERVE = CONVERT(FLOAT, ?) ,"    //       
                + " SALVAGE_VALUE = CONVERT(FLOAT, ?) ,"
                + " IMPAIRMENT_RESERVE = CONVERT(FLOAT, ?) ,"
                + " DESCRIPTION = ?,"
                + " MANUFACTURER_NAME = ?,"
                + " MODEL_NUMBER = ?,"
                + " SERIAL_NUMBER = ?,"
                + " IN_USE_FLAG = ?,"
                + " INVENTORIAL = ?,"
                + " ASSET_SOURCE_ID = ?,"
                + " PROJECT_NUMBER = ?,"        //  
                + " CONSTRUCTION_STATUS = ?,"
                + " CATEGORY_CON_SEG = ?,"
                + " CATEGORY_DESCRIPTION = ?,"
                + " ASSET_KEY  = ?,"
                + " ASSET_KEY_DESC  = ?,"
                + " CREATION_DATE = ?,"
                + " LAST_UPDATE_DATE = ?,"
                + " FIXED_ASSETS_UNIT = CONVERT(FLOAT, ?) ,"
                + " RETIREMENT_FLAG = ?"
                + " WHERE ASSET_ID= CONVERT(FLOAT, ?) ";

        sqlArgs.add(pageRetiredAssetDTO.getBookTypeCode());
        sqlArgs.add(pageRetiredAssetDTO.getAssetNumber());
        sqlArgs.add(pageRetiredAssetDTO.getTagNumber());
        sqlArgs.add(pageRetiredAssetDTO.getDatePlacedInService().subSequence(0, 10));
        sqlArgs.add(pageRetiredAssetDTO.getLifeInMonth());
        sqlArgs.add(pageRetiredAssetDTO.getDeprnMethod());
        sqlArgs.add(pageRetiredAssetDTO.getDepreciateFlag());
        sqlArgs.add(pageRetiredAssetDTO.getFixedAssetsCost());
        sqlArgs.add(pageRetiredAssetDTO.getDeprnReserve());

        sqlArgs.add(pageRetiredAssetDTO.getSalvageValue() );
        sqlArgs.add(pageRetiredAssetDTO.getImpairmentReserve() );
        sqlArgs.add(pageRetiredAssetDTO.getDescription() );
        sqlArgs.add(pageRetiredAssetDTO.getManufacturerName() );
        sqlArgs.add(pageRetiredAssetDTO.getModelNumber() );
        sqlArgs.add(pageRetiredAssetDTO.getSerialNumber() );
        sqlArgs.add(pageRetiredAssetDTO.getInUseFlag());
        sqlArgs.add(pageRetiredAssetDTO.getInventorial() );
        sqlArgs.add(pageRetiredAssetDTO.getAssetSourceId() );
        sqlArgs.add(pageRetiredAssetDTO.getProjectNumber() );
        
        sqlArgs.add(pageRetiredAssetDTO.getConstructionStatus() );
        sqlArgs.add(pageRetiredAssetDTO.getCategoryConSeg() );
        sqlArgs.add(pageRetiredAssetDTO.getCategoryDescription() );
        sqlArgs.add(pageRetiredAssetDTO.getAssetKey() );
        sqlArgs.add(pageRetiredAssetDTO.getAssetKeyDesc() );
        sqlArgs.add(pageRetiredAssetDTO.getCreationDate().subSequence(0, 10) );
        sqlArgs.add(pageRetiredAssetDTO.getLastUpdateDate().subSequence(0, 10));
        sqlArgs.add(pageRetiredAssetDTO.getFixedAssetsUnit() );
        sqlArgs.add(pageRetiredAssetDTO.getRetirementFlag() );
        sqlArgs.add(pageRetiredAssetDTO.getAssetId());
        
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 判断是否存在此记录
     * 条件: 资产ID(ASSET_ID)
     * @return 
     */
    public SQLModel getAssetExistsModel() {
        List sqlArgs = new ArrayList();
        InquiryAssetHeaderInfoSrvDTO retiredAssetDTO = (InquiryAssetHeaderInfoSrvDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT"
                + "	ECOM.ASSET_ID"
                + "	FROM ZTE_FA_ASSET_HEADER ECOM WHERE ECOM.ASSET_ID=CONVERT(FLOAT, ?)";
        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(retiredAssetDTO.getAssetId());
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel geUpdateItemInfoModel() {
        List sqlArgs = new ArrayList();
        InquiryAssetHeaderInfoSrvDTO srvVendorInfoDTO = (InquiryAssetHeaderInfoSrvDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE ETS_ITEM_INFO EII"
                + "   SET EII.ITEM_STATUS = 'DISCARDED',"
                + "	     EII.DISABLE_DATE= SYSDATE"
                + "	 WHERE EII.BARCODE = ?";
        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(srvVendorInfoDTO.getTagNumber());
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}