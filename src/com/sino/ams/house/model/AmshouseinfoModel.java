package com.sino.ams.house.model;

import com.sino.base.db.sql.model.SQLModel;

/**
 * 房屋土地报表信息统计
 * @author kouzh
 * 
 */
public class AmshouseinfoModel {

	/**
	 * 获取基站土地报表统计信息
	 */
	public SQLModel getAmslandInfo() {
		SQLModel sqlModel = new SQLModel();
//        String sqlStr = "SELECT EOCM.COMPANY,\n" +
//                        "       COUNT(1) ASSETNUM,\n" +
//                        "       A.HOUSE_CERTIFICATE_NUM,\n" +
//                        "       SUM(AHI.OCCUPY_AREA) OCCUPY_AREA,\n" +
//                        "       SUM(EFA.COST) COST,\n" +
//                        "       SUM(EFA.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
//                        "       SUM(EFA.NET_ASSET_VALUE) NET_ASSET_VALUE\n" +
//                        "  FROM AMS_HOUSE_INFO AHI,\n" +
//                        "       ETS_OU_CITY_MAP EOCM,\n" +
//                        "       ETS_FA_ASSETS EFA,\n" +
//                        "       ETS_ITEM_INFO EII,\n" +
//                        "       ETS_ITEM_MATCH EIM,\n" +
//                        "       (SELECT EII.ORGANIZATION_ID ORGANIZATION_ID,\n" +
//                        "               COUNT(AHI.LAND_CERTFICATE_NO) HOUSE_CERTIFICATE_NUM\n" +
//                        "          FROM AMS_HOUSE_INFO AHI, ETS_ITEM_INFO EII, ETS_ITEM_MATCH EIM\n" +
//                        "         WHERE AHI.OFFICE_TYPE = '土地'\n" +
//                        "           AND AHI.OFFICE_USAGE = '基站'\n" +
//                        "           AND EII.BARCODE = AHI.BARCODE\n" +
//                        "           AND EII.SYSTEMID = EIM.SYSTEMID\n" +
//                        "           AND AHI.LAND_CERTFICATE_NO IS NOT NULL\n" +
//                        "         GROUP BY EII.ORGANIZATION_ID) A\n" +
//                        " WHERE AHI.OFFICE_TYPE = '土地'\n" +
//                        "   AND AHI.OFFICE_USAGE = '基站'\n" +
//                        "   AND EFA.TAG_NUMBER = AHI.BARCODE\n" +
//                        "   AND EII.SYSTEMID = EIM.SYSTEMID\n" +
//                        "   AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
//                        "   AND EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
//                        "   AND A.ORGANIZATION_ID = EOCM.ORGANIZATION_ID(+)\n" +
//                        " GROUP BY EOCM.COMPANY, A.HOUSE_CERTIFICATE_NUM";
        
        //2009-9-4(SUHP修改)
        String sqlStr = "SELECT EOCM.COMPANY,\n" +
                "       CASE WHEN AA.ASSETNUM IS NULL THEN 0 ELSE AA.ASSETNUM END AS AASETNUM,\n" +
                "       CASE WHEN BB.LAND_CERTIFICATE_NUM IS NULL THEN 0 ELSE BB.LAND_CERTIFICATE_NUM END AS HOUSE_CERTIFICATE_NUM, \n" +
                "        CASE WHEN AA.OCCUPY_AREA IS NULL THEN 0 ELSE AA.OCCUPY_AREA END AS OCCUPY_AREA,\n" +
                "        CASE WHEN AA.COST IS NULL THEN 0 ELSE AA.COST END AS COST,\n" +
                "       CASE WHEN AA.DEPRN_RESERVE IS NULL THEN 0 ELSE AA.DEPRN_RESERVE END AS DEPRN_RESERVE,\n" +
                "       CASE WHEN AA.NET_ASSET_VALUE IS NULL THEN 0 ELSE AA.NET_ASSET_VALUE END AS NET_ASSET_VALUE \n" +
                "  FROM (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               EOCM.COMPANY,\n" +
                "               COUNT(AHI.BARCODE) ASSETNUM,\n" +
                "               SUM(AHI.OCCUPY_AREA) OCCUPY_AREA,\n" +
                "               SUM(EFA.ORIGINAL_COST) COST,\n" +
                "               SUM(EFA.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
                "               SUM(EFA.NET_ASSET_VALUE) NET_ASSET_VALUE\n" +
                "          FROM ETS_OU_CITY_MAP EOCM,\n" +
                "               AMS_HOUSE_INFO  AHI,\n" +
                "               ETS_FA_ASSETS   EFA,\n" +
                "               ETS_ITEM_INFO   EII\n" +
                "         WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND AHI.BARCODE = EII.BARCODE\n" +
                "               AND AHI.BARCODE = EFA.TAG_NUMBER\n" +
                "               AND AHI.OFFICE_TYPE = '土地'\n" +
                "               AND AHI.OFFICE_USAGE = '基站'\n" +
                "               AND EII.ATTRIBUTE2 = '已处理'\n" +
                "         GROUP BY EOCM.COMPANY,\n" +
                "                  EOCM.ORGANIZATION_ID) AA,\n" +
                "       (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               COUNT(DISTINCT(AHI.LAND_CERTFICATE_NO)) LAND_CERTIFICATE_NUM\n" +
                "          FROM ETS_OU_CITY_MAP EOCM,\n" +
                "               AMS_HOUSE_INFO  AHI,\n" +
                "               ETS_FA_ASSETS   EFA,\n" +
                "               ETS_ITEM_INFO   EII\n" +
                "         WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND AHI.BARCODE = EII.BARCODE\n" +
                "               AND AHI.BARCODE = EFA.TAG_NUMBER\n" +
                "               AND AHI.OFFICE_TYPE = '土地'\n" +
                "               AND AHI.OFFICE_USAGE = '基站'\n" +
                "               AND EII.ATTRIBUTE2 = '已处理'\n" +
                "               AND AHI.LAND_CERTFICATE_NO IS NOT NULL\n" +
                "         GROUP BY EOCM.ORGANIZATION_ID) BB,\n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EOCM.ORGANIZATION_ID *= AA.ORGANIZATION_ID\n" +
                "       AND EOCM.ORGANIZATION_ID *= BB.ORGANIZATION_ID\n" +
                " ORDER BY EOCM.ORGANIZATION_ID";
        sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 获取基站房屋报表统计信息
	 */
	public SQLModel getAmshouseInfo() {
		SQLModel sqlModel = new SQLModel();
//        String sqlStr = "SELECT EOCM.COMPANY,\n" +
//                        "       COUNT(1) ASSETNUM,\n" +
//                        "       A.HOUSE_CERTIFICATE_NUM,\n" +
//                        "       SUM(AHI.HOUSE_AREA) HOUSE_AREA,\n" +
//                        "       SUM(EFA.COST) COST,\n" +
//                        "       SUM(EFA.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
//                        "       SUM(EFA.NET_ASSET_VALUE) NET_ASSET_VALUE\n" +
//                        "  FROM AMS_HOUSE_INFO AHI,\n" +
//                        "       ETS_OU_CITY_MAP EOCM,\n" +
//                        "       ETS_FA_ASSETS EFA,\n" +
//                        "       ETS_ITEM_INFO EII,\n" +
//                        "       ETS_ITEM_MATCH EIM,\n" +
//                        "       (SELECT EII.ORGANIZATION_ID ORGANIZATION_ID,\n" +
//                        "               COUNT(AHI.LAND_CERTFICATE_NO) HOUSE_CERTIFICATE_NUM\n" +
//                        "          FROM AMS_HOUSE_INFO AHI, ETS_ITEM_INFO EII, ETS_ITEM_MATCH EIM\n" +
//                        "         WHERE AHI.OFFICE_TYPE = '房屋'\n" +
//                        "           AND AHI.OFFICE_USAGE = '基站'\n" +
//                        "           AND EII.BARCODE = AHI.BARCODE\n" +
//                        "           AND EII.SYSTEMID = EIM.SYSTEMID\n" +
//                        "           AND AHI.LAND_CERTFICATE_NO IS NOT NULL\n" +
//                        "         GROUP BY EII.ORGANIZATION_ID) A\n" +
//                        " WHERE AHI.OFFICE_TYPE = '房屋'\n" +
//                        "   AND AHI.OFFICE_USAGE = '基站'\n" +
//                        "   AND EFA.TAG_NUMBER = AHI.BARCODE \n" +
//                        "   AND EII.SYSTEMID = EIM.SYSTEMID\n" +
//                        "   AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
//                        "   AND EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
//                        "   AND A.ORGANIZATION_ID = EOCM.ORGANIZATION_ID(+)\n" +
//                        " GROUP BY EOCM.COMPANY, A.HOUSE_CERTIFICATE_NUM";

        //2009-9-4(SUHP修改)
        String sqlStr = "SELECT EOCM.COMPANY,\n" +
                "       CASE WHEN AA.ASSETNUM IS NULL THEN 0 ELSE AA.ASSETNUM END AS AASETNUM,\n" +
                "		CASE WHEN BB.HOUSE_CERTIFICATE_NO IS NULL THEN 0 ELSE BB.HOUSE_CERTIFICATE_NO END AS HOUSE_CERTIFICATE_NUM, "+
                "		CASE WHEN AA.HOUSE_AREA IS NULL THEN 0 ELSE AA.HOUSE_AREA END AS HOUSE_AREA, "+
                "        CASE WHEN AA.COST IS NULL THEN 0 ELSE AA.COST END AS COST,\n" +
                "       CASE WHEN AA.DEPRN_RESERVE IS NULL THEN 0 ELSE AA.DEPRN_RESERVE END AS DEPRN_RESERVE,\n" +
                "       CASE WHEN AA.NET_ASSET_VALUE IS NULL THEN 0 ELSE AA.NET_ASSET_VALUE END AS NET_ASSET_VALUE \n" +
                "  FROM (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               EOCM.COMPANY,\n" +
                "               COUNT(AHI.BARCODE) ASSETNUM,\n" +
                "               SUM(AHI.HOUSE_AREA) HOUSE_AREA,\n" +
                "               SUM(EFA.ORIGINAL_COST) COST,\n" +
                "               SUM(EFA.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
                "               SUM(EFA.NET_ASSET_VALUE) NET_ASSET_VALUE\n" +
                "          FROM ETS_OU_CITY_MAP EOCM,\n" +
                "               AMS_HOUSE_INFO  AHI,\n" +
                "               ETS_FA_ASSETS   EFA,\n" +
                "               ETS_ITEM_INFO   EII\n" +
                "         WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND AHI.BARCODE = EII.BARCODE\n" +
                "               AND AHI.BARCODE = EFA.TAG_NUMBER\n" +
                "               AND AHI.OFFICE_TYPE = '房屋'\n" +
                "               AND AHI.OFFICE_USAGE = '基站'\n" +
                "               AND EII.ATTRIBUTE2 = '已处理'\n" +
                "         GROUP BY EOCM.COMPANY,\n" +
                "                  EOCM.ORGANIZATION_ID) AA,\n" +
                "       (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               COUNT(DISTINCT(AHI.HOUSE_CERTIFICATE_NO)) HOUSE_CERTIFICATE_NO\n" +
                "          FROM ETS_OU_CITY_MAP EOCM,\n" +
                "               AMS_HOUSE_INFO  AHI,\n" +
                "               ETS_FA_ASSETS   EFA,\n" +
                "               ETS_ITEM_INFO   EII\n" +
                "         WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND AHI.BARCODE = EII.BARCODE\n" +
                "               AND AHI.BARCODE = EFA.TAG_NUMBER\n" +
                "               AND AHI.OFFICE_TYPE = '房屋'\n" +
                "               AND AHI.OFFICE_USAGE = '基站'\n" +
                "               AND EII.ATTRIBUTE2 = '已处理'\n" +
                "               AND AHI.HOUSE_CERTIFICATE_NO IS NOT NULL\n" +
                "         GROUP BY EOCM.ORGANIZATION_ID) BB,\n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EOCM.ORGANIZATION_ID *= AA.ORGANIZATION_ID\n" +
                "       AND EOCM.ORGANIZATION_ID *= BB.ORGANIZATION_ID\n" +
                "       ORDER BY EOCM.ORGANIZATION_ID";
        sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 获取办公土地报表统计信息
	 */
	public SQLModel getAmsofficelandInfo() {
		SQLModel sqlModel = new SQLModel();
//        String sqlStr = "SELECT EOCM.COMPANY,\n" +
//                        "       COUNT(1) ASSETNUM,\n" +
//                        "       A.HOUSE_CERTIFICATE_NUM,\n" +
//                        "       SUM(AHI.OCCUPY_AREA) OCCUPY_AREA,\n" +
//                        "       SUM(EFA.COST) COST,\n" +
//                        "       SUM(EFA.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
//                        "       SUM(EFA.NET_ASSET_VALUE) NET_ASSET_VALUE\n" +
//                        "  FROM AMS_HOUSE_INFO AHI,\n" +
//                        "       ETS_OU_CITY_MAP EOCM,\n" +
//                        "       ETS_FA_ASSETS EFA,\n" +
//                        "       ETS_ITEM_INFO EII,\n" +
//                        "       ETS_ITEM_MATCH EIM,\n" +
//                        "       (SELECT EII.ORGANIZATION_ID ORGANIZATION_ID,\n" +
//                        "               COUNT(AHI.LAND_CERTFICATE_NO) HOUSE_CERTIFICATE_NUM\n" +
//                        "          FROM AMS_HOUSE_INFO AHI, ETS_ITEM_INFO EII, ETS_ITEM_MATCH EIM\n" +
//                        "         WHERE AHI.OFFICE_TYPE = '土地'\n" +
//                        "           AND AHI.OFFICE_USAGE = '非基站'\n" +
//                        "           AND EII.BARCODE = AHI.BARCODE\n" +
//                        "           AND EII.SYSTEMID = EIM.SYSTEMID\n" +
//                        "           AND AHI.LAND_CERTFICATE_NO IS NOT NULL\n" +
//                        "         GROUP BY EII.ORGANIZATION_ID) A\n" +
//                        " WHERE AHI.OFFICE_TYPE = '土地'\n" +
//                        "   AND AHI.OFFICE_USAGE = '非基站'\n" +
//                        "   AND EFA.TAG_NUMBER = AHI.BARCODE\n" +
//                        "   AND EII.SYSTEMID = EIM.SYSTEMID\n" +
//                        "   AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
//                        "   AND EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
//                        "   AND A.ORGANIZATION_ID = EOCM.ORGANIZATION_ID(+)\n" +
//                        " GROUP BY EOCM.COMPANY, A.HOUSE_CERTIFICATE_NUM";

        //2009-9-4(SUHP修改)
        String sqlStr = "SELECT EOCM.COMPANY,\n" +
                "       CASE WHEN AA.ASSETNUM IS NULL THEN 0 ELSE AA.ASSETNUM END AS AASETNUM,\n" +
                "       CASE WHEN BB.LAND_CERTIFICATE_NUM IS NULL THEN 0 ELSE BB.LAND_CERTIFICATE_NUM END AS HOUSE_CERTIFICATE_NUM,\n" +
                "       CASE WHEN AA.OCCUPY_AREA IS NULL THEN 0 ELSE AA.OCCUPY_AREA END AS OCCUPY_AREA,\n" +
                "        CASE WHEN AA.COST IS NULL THEN 0 ELSE AA.COST END AS COST,\n" +
                "       CASE WHEN AA.DEPRN_RESERVE IS NULL THEN 0 ELSE AA.DEPRN_RESERVE END AS DEPRN_RESERVE,\n" +
                  "       CASE WHEN AA.NET_ASSET_VALUE IS NULL THEN 0 ELSE AA.NET_ASSET_VALUE END AS NET_ASSET_VALUE \n" +
                "  FROM (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               EOCM.COMPANY,\n" +
                "               COUNT(AHI.BARCODE) ASSETNUM,\n" +
                "               SUM(AHI.OCCUPY_AREA) OCCUPY_AREA,\n" +
                "               SUM(EFA.ORIGINAL_COST) COST,\n" +
                "               SUM(EFA.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
                "               SUM(EFA.NET_ASSET_VALUE) NET_ASSET_VALUE\n" +
                "          FROM ETS_OU_CITY_MAP EOCM,\n" +
                "               AMS_HOUSE_INFO  AHI,\n" +
                "               ETS_FA_ASSETS   EFA,\n" +
                "               ETS_ITEM_INFO   EII\n" +
                "         WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND AHI.BARCODE = EII.BARCODE\n" +
                "               AND AHI.BARCODE = EFA.TAG_NUMBER\n" +
                "               AND AHI.OFFICE_TYPE = '土地'\n" +
                "               AND AHI.OFFICE_USAGE = '非基站'\n" +
                "               AND EII.ATTRIBUTE2 = '已处理'\n" +
                "         GROUP BY EOCM.COMPANY,\n" +
                "                  EOCM.ORGANIZATION_ID) AA,\n" +
                "       (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               COUNT(DISTINCT(AHI.LAND_CERTFICATE_NO)) LAND_CERTIFICATE_NUM\n" +
                "          FROM ETS_OU_CITY_MAP EOCM,\n" +
                "               AMS_HOUSE_INFO  AHI,\n" +
                "               ETS_FA_ASSETS   EFA,\n" +
                "               ETS_ITEM_INFO   EII\n" +
                "         WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND AHI.BARCODE = EII.BARCODE\n" +
                "               AND AHI.BARCODE = EFA.TAG_NUMBER\n" +
                "               AND AHI.OFFICE_TYPE = '土地'\n" +
                "               AND AHI.OFFICE_USAGE = '非基站'\n" +
                "               AND EII.ATTRIBUTE2 = '已处理'\n" +
                "               AND AHI.LAND_CERTFICATE_NO IS NOT NULL\n" +
                "         GROUP BY EOCM.ORGANIZATION_ID) BB,\n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EOCM.ORGANIZATION_ID *= AA.ORGANIZATION_ID\n" +
                "       AND EOCM.ORGANIZATION_ID *= BB.ORGANIZATION_ID\n" +
                " ORDER BY EOCM.ORGANIZATION_ID";
        sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 获取办公房屋报表统计信息
	 */
	public SQLModel getAmsofficehouseInfo() {
		SQLModel sqlModel = new SQLModel();
//        String sqlStr = "SELECT EOCM.COMPANY,\n" +
//                        "       COUNT(1) ASSETNUM,\n" +
//                        "       A.HOUSE_CERTIFICATE_NUM,\n" +
//                        "       SUM(AHI.HOUSE_AREA) HOUSE_AREA,\n" +
//                        "       SUM(EFA.COST) COST,\n" +
//                        "       SUM(EFA.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
//                        "       SUM(EFA.NET_ASSET_VALUE) NET_ASSET_VALUE\n" +
//                        "  FROM AMS_HOUSE_INFO AHI,\n" +
//                        "       ETS_OU_CITY_MAP EOCM,\n" +
//                        "       ETS_FA_ASSETS EFA,\n" +
//                        "       ETS_ITEM_INFO EII,\n" +
//                        "       ETS_ITEM_MATCH EIM,\n" +
//                        "       (SELECT EII.ORGANIZATION_ID ORGANIZATION_ID,\n" +
//                        "               COUNT(AHI.LAND_CERTFICATE_NO) HOUSE_CERTIFICATE_NUM\n" +
//                        "          FROM AMS_HOUSE_INFO AHI, ETS_ITEM_INFO EII, ETS_ITEM_MATCH EIM\n" +
//                        "         WHERE AHI.OFFICE_TYPE = '房屋'\n" +
//                        "           AND AHI.OFFICE_USAGE = '非基站'\n" +
//                        "           AND EII.BARCODE = AHI.BARCODE\n" +
//                        "           AND EII.SYSTEMID = EIM.SYSTEMID\n" +
//                        "           AND AHI.LAND_CERTFICATE_NO IS NOT NULL\n" +
//                        "         GROUP BY EII.ORGANIZATION_ID) A\n" +
//                        " WHERE AHI.OFFICE_TYPE = '房屋'\n" +
//                        "   AND AHI.OFFICE_USAGE = '非基站'\n" +
//                        "   AND EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
//                        "   AND EFA.TAG_NUMBER = AHI.BARCODE\n" +
//                        "   AND EII.SYSTEMID = EIM.SYSTEMID\n" +
//                        "   AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
//                        "   AND A.ORGANIZATION_ID = EOCM.ORGANIZATION_ID(+)\n" +
//                        " GROUP BY EOCM.COMPANY, A.HOUSE_CERTIFICATE_NUM";

        //2009-9-4(SUHP修改)
        String sqlStr = "SELECT EOCM.COMPANY,\n" +
		        "       CASE WHEN AA.ASSETNUM IS NULL THEN 0 ELSE AA.ASSETNUM END AS AASETNUM,\n" +
		        "       CASE WHEN BB.HOUSE_CERTIFICATE_NUM IS NULL THEN 0 ELSE BB.HOUSE_CERTIFICATE_NUM END AS HOUSE_CERTIFICATE_NUM, \n" +
		        "        CASE WHEN AA.HOUSE_AREA IS NULL THEN 0 ELSE AA.HOUSE_AREA END AS HOUSE_AREA,\n" +
		        "        CASE WHEN AA.COST IS NULL THEN 0 ELSE AA.COST END AS COST,\n" +
		        "       CASE WHEN AA.DEPRN_RESERVE IS NULL THEN 0 ELSE AA.DEPRN_RESERVE END AS DEPRN_RESERVE,\n" +
		        "       CASE WHEN AA.NET_ASSET_VALUE IS NULL THEN 0 ELSE AA.NET_ASSET_VALUE END AS NET_ASSET_VALUE \n" +
		        
                "  FROM (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               EOCM.COMPANY,\n" +
                "               COUNT(AHI.BARCODE) ASSETNUM,\n" +
                "               SUM(AHI.HOUSE_AREA) HOUSE_AREA,\n" +
                "               SUM(EFA.ORIGINAL_COST) COST,\n" +
                "               SUM(EFA.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
                "               SUM(EFA.NET_ASSET_VALUE) NET_ASSET_VALUE\n" +
                "          FROM ETS_OU_CITY_MAP EOCM,\n" +
                "               AMS_HOUSE_INFO  AHI,\n" +
                "               ETS_FA_ASSETS   EFA,\n" +
                "               ETS_ITEM_INFO   EII\n" +
                "         WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND AHI.BARCODE = EII.BARCODE\n" +
                "               AND AHI.BARCODE = EFA.TAG_NUMBER\n" +
                "               AND AHI.OFFICE_TYPE = '房屋'\n" +
                "               AND AHI.OFFICE_USAGE = '非基站'\n" +
                "               AND EII.ATTRIBUTE2 = '已处理'\n" +
                "         GROUP BY EOCM.COMPANY,\n" +
                "                  EOCM.ORGANIZATION_ID) AA,\n" +
                "       (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               COUNT(DISTINCT(AHI.HOUSE_CERTIFICATE_NO)) HOUSE_CERTIFICATE_NUM\n" +
                "          FROM ETS_OU_CITY_MAP EOCM,\n" +
                "               AMS_HOUSE_INFO  AHI,\n" +
                "               ETS_FA_ASSETS   EFA,\n" +
                "               ETS_ITEM_INFO   EII\n" +
                "         WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND AHI.BARCODE = EII.BARCODE\n" +
                "               AND AHI.BARCODE = EFA.TAG_NUMBER\n" +
                "               AND AHI.OFFICE_TYPE = '房屋'\n" +
                "               AND AHI.OFFICE_USAGE = '非基站'\n" +
                "               AND EII.ATTRIBUTE2 = '已处理'\n" +
                "               AND AHI.HOUSE_CERTIFICATE_NO IS NOT NULL\n" +
                "         GROUP BY EOCM.ORGANIZATION_ID) BB,\n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EOCM.ORGANIZATION_ID *= AA.ORGANIZATION_ID\n" +
                "       AND EOCM.ORGANIZATION_ID *= BB.ORGANIZATION_ID\n" +
                " ORDER BY EOCM.ORGANIZATION_ID";
        sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

    /**
	 * 获取办公房地合一报表统计信息
	 */
	public SQLModel getHouseLandInfo() {
		SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT EOCM.COMPANY,\n" +
		        "       CASE WHEN AA.ASSETNUM IS NULL THEN 0 ELSE AA.ASSETNUM END AS AASETNUM,\n" +
		        
		        "       CASE WHEN BB.HOUSE_CERTIFICATE_NO+CC.LAND_CERTFICATE_NO IS NULL THEN 0 ELSE BB.HOUSE_CERTIFICATE_NO+CC.LAND_CERTFICATE_NO END AS HOUSE_LAND_CERTIFICATE_NUM,\n" +
		        
		        "        CASE WHEN BB.HOUSE_CERTIFICATE_NO IS NULL THEN 0 ELSE BB.HOUSE_CERTIFICATE_NO END  AS HOUSE_CERTIFICATE_NUM,\n" +
		        "        CASE WHEN AA.HOUSE_AREA IS NULL THEN 0 ELSE AA.HOUSE_AREA END AS HOUSE_AREA,\n" +
		        "        CASE WHEN CC.LAND_CERTFICATE_NO IS NULL THEN 0 ELSE CC.LAND_CERTFICATE_NO END AS LAND_CERTIFICATE_NUM,\n" +
		        "        CASE WHEN AA.OCCUPY_AREA IS NULL THEN 0 ELSE AA.OCCUPY_AREA END AS OCCUPY_AREA,\n" +
		        "        CASE WHEN AA.COST IS NULL THEN 0 ELSE AA.COST END AS COST,\n" +
		        "       CASE WHEN AA.DEPRN_RESERVE IS NULL THEN 0 ELSE AA.DEPRN_RESERVE END AS DEPRN_RESERVE,\n" +
		        "       CASE WHEN AA.NET_ASSET_VALUE IS NULL THEN 0 ELSE AA.NET_ASSET_VALUE END AS NET_ASSET_VALUE \n" +
                "  FROM (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               EOCM.COMPANY,\n" +
                "               COUNT(AHI.BARCODE) ASSETNUM,\n" +
                "               SUM(AHI.HOUSE_AREA) HOUSE_AREA,\n" +
                "               SUM(AHI.OCCUPY_AREA) OCCUPY_AREA,\n" +
                "               SUM(EFA.ORIGINAL_COST) COST,\n" +
                "               SUM(EFA.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
                "               SUM(EFA.NET_ASSET_VALUE) NET_ASSET_VALUE\n" +
                "          FROM ETS_OU_CITY_MAP EOCM,\n" +
                "               AMS_HOUSE_INFO  AHI,\n" +
                "               ETS_FA_ASSETS   EFA,\n" +
                "               ETS_ITEM_INFO   EII\n" +
                "         WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND AHI.BARCODE = EII.BARCODE\n" +
                "               AND AHI.BARCODE = EFA.TAG_NUMBER\n" +
                "               AND AHI.OFFICE_TYPE = '房地合一'\n" +
                "               AND AHI.OFFICE_USAGE = '非基站'\n" +
                "               AND EII.ATTRIBUTE2 = '已处理'\n" +
                "         GROUP BY EOCM.COMPANY,\n" +
                "                  EOCM.ORGANIZATION_ID) AA,\n" +
                "       (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               COUNT(DISTINCT(AHI.HOUSE_CERTIFICATE_NO)) HOUSE_CERTIFICATE_NO\n" +
                "          FROM ETS_OU_CITY_MAP EOCM,\n" +
                "               AMS_HOUSE_INFO  AHI,\n" +
                "               ETS_FA_ASSETS   EFA,\n" +
                "               ETS_ITEM_INFO   EII\n" +
                "         WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND AHI.BARCODE = EII.BARCODE\n" +
                "               AND AHI.BARCODE = EFA.TAG_NUMBER\n" +
                "               AND AHI.OFFICE_TYPE = '房地合一'\n" +
                "               AND AHI.OFFICE_USAGE = '非基站'\n" +
                "               AND EII.ATTRIBUTE2 = '已处理'\n" +
                "               AND AHI.HOUSE_CERTIFICATE_NO IS NOT NULL\n" +
                "         GROUP BY EOCM.ORGANIZATION_ID) BB,\n" +
                "       (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               COUNT(DISTINCT(AHI.LAND_CERTFICATE_NO)) LAND_CERTFICATE_NO\n" +
                "          FROM ETS_OU_CITY_MAP EOCM,\n" +
                "               AMS_HOUSE_INFO  AHI,\n" +
                "               ETS_FA_ASSETS   EFA,\n" +
                "               ETS_ITEM_INFO   EII\n" +
                "         WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND AHI.BARCODE = EII.BARCODE\n" +
                "               AND AHI.BARCODE = EFA.TAG_NUMBER\n" +
                "               AND AHI.OFFICE_TYPE = '房地合一'\n" +
                "               AND AHI.OFFICE_USAGE = '非基站'\n" +
                "               AND EII.ATTRIBUTE2 = '已处理'\n" +
                "               AND AHI.LAND_CERTFICATE_NO IS NOT NULL\n" +
                "         GROUP BY EOCM.ORGANIZATION_ID) CC,\n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EOCM.ORGANIZATION_ID *= AA.ORGANIZATION_ID\n" +
                "       AND EOCM.ORGANIZATION_ID *= BB.ORGANIZATION_ID\n" +
                "       AND EOCM.ORGANIZATION_ID *= CC.ORGANIZATION_ID\n" +
                " ORDER BY EOCM.ORGANIZATION_ID";
        sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

    /**
	 * 获取基站房地合一报表统计信息
	 */
	public SQLModel getBtsHouseLandInfo() {
		SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT EOCM.COMPANY,\n" +
                "       CASE WHEN AA.ASSETNUM IS NULL THEN 0 ELSE AA.ASSETNUM END AS AASETNUM,\n" +
                "       CASE WHEN BB.HOUSE_CERTIFICATE_NO+CC.LAND_CERTFICATE_NO IS NULL THEN 0 ELSE BB.HOUSE_CERTIFICATE_NO+CC.LAND_CERTFICATE_NO END AS HOUSE_LAND_CERTIFICATE_NUM,\n" +  
                "        CASE WHEN BB.HOUSE_CERTIFICATE_NO IS NULL THEN 0 ELSE BB.HOUSE_CERTIFICATE_NO END AS HOUSE_CERTIFICATE_NUM,\n" +
		        "        CASE WHEN AA.HOUSE_AREA IS NULL THEN 0 ELSE AA.HOUSE_AREA END AS HOUSE_AREA,\n" +
		        "        CASE WHEN CC.LAND_CERTFICATE_NO IS NULL THEN 0 ELSE CC.LAND_CERTFICATE_NO END AS LAND_CERTIFICATE_NUM,\n" +
		        
		        "        CASE WHEN AA.OCCUPY_AREA IS NULL THEN 0 ELSE AA.OCCUPY_AREA END AS OCCUPY_AREA,\n" +
		        "        CASE WHEN AA.COST IS NULL THEN 0 ELSE AA.COST END AS COST,\n" +
		        "       CASE WHEN AA.DEPRN_RESERVE IS NULL THEN 0 ELSE AA.DEPRN_RESERVE END AS DEPRN_RESERVE,\n" +
		        "       CASE WHEN AA.NET_ASSET_VALUE IS NULL THEN 0 ELSE AA.NET_ASSET_VALUE END AS NET_ASSET_VALUE \n" +
		        
		        
                "  FROM (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               EOCM.COMPANY,\n" +
                "               COUNT(AHI.BARCODE) ASSETNUM,\n" +
                "               SUM(AHI.HOUSE_AREA) HOUSE_AREA,\n" +
                "               SUM(AHI.OCCUPY_AREA) OCCUPY_AREA,\n" +
                "               SUM(EFA.ORIGINAL_COST) COST,\n" +
                "               SUM(EFA.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
                "               SUM(EFA.NET_ASSET_VALUE) NET_ASSET_VALUE\n" +
                "          FROM ETS_OU_CITY_MAP EOCM,\n" +
                "               AMS_HOUSE_INFO  AHI,\n" +
                "               ETS_FA_ASSETS   EFA,\n" +
                "               ETS_ITEM_INFO   EII\n" +
                "         WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND AHI.BARCODE = EII.BARCODE\n" +
                "               AND AHI.BARCODE = EFA.TAG_NUMBER\n" +
                "               AND AHI.OFFICE_TYPE = '房地合一'\n" +
                "               AND AHI.OFFICE_USAGE = '基站'\n" +
                "               AND EII.ATTRIBUTE2 = '已处理'\n" +
                "         GROUP BY EOCM.COMPANY,\n" +
                "                  EOCM.ORGANIZATION_ID) AA,\n" +
                "       (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               COUNT(DISTINCT(AHI.HOUSE_CERTIFICATE_NO)) HOUSE_CERTIFICATE_NO\n" +
                "          FROM ETS_OU_CITY_MAP EOCM,\n" +
                "               AMS_HOUSE_INFO  AHI,\n" +
                "               ETS_FA_ASSETS   EFA,\n" +
                "               ETS_ITEM_INFO   EII\n" +
                "         WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND AHI.BARCODE = EII.BARCODE\n" +
                "               AND AHI.BARCODE = EFA.TAG_NUMBER\n" +
                "               AND AHI.OFFICE_TYPE = '房地合一'\n" +
                "               AND AHI.OFFICE_USAGE = '基站'\n" +
                "               AND EII.ATTRIBUTE2 = '已处理'\n" +
                "               AND AHI.HOUSE_CERTIFICATE_NO IS NOT NULL\n" +
                "         GROUP BY EOCM.ORGANIZATION_ID) BB,\n" +
                "       (SELECT EOCM.ORGANIZATION_ID,\n" +
                "               COUNT(DISTINCT(AHI.LAND_CERTFICATE_NO)) LAND_CERTFICATE_NO\n" +
                "          FROM ETS_OU_CITY_MAP EOCM,\n" +
                "               AMS_HOUSE_INFO  AHI,\n" +
                "               ETS_FA_ASSETS   EFA,\n" +
                "               ETS_ITEM_INFO   EII\n" +
                "         WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "               AND AHI.BARCODE = EII.BARCODE\n" +
                "               AND AHI.BARCODE = EFA.TAG_NUMBER\n" +
                "               AND AHI.OFFICE_TYPE = '房地合一'\n" +
                "               AND AHI.OFFICE_USAGE = '基站'\n" +
                "               AND EII.ATTRIBUTE2 = '已处理'\n" +
                "               AND AHI.LAND_CERTFICATE_NO IS NOT NULL\n" +
                "         GROUP BY EOCM.ORGANIZATION_ID) CC,\n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EOCM.ORGANIZATION_ID *= AA.ORGANIZATION_ID\n" +
                "       AND EOCM.ORGANIZATION_ID *= BB.ORGANIZATION_ID\n" +
                "       AND EOCM.ORGANIZATION_ID *= CC.ORGANIZATION_ID\n" +
                " ORDER BY EOCM.ORGANIZATION_ID";
        sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
}
