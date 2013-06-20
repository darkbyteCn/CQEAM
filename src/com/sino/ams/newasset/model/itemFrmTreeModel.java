package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-3-14
 * Time: 12:05:24
 * To change this template use File | Settings | File Templates.
 */
public class itemFrmTreeModel extends AMSSQLProducer {
    public itemFrmTreeModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：构造任务树形菜单
     *
     * @return SQLModel
     */
    public SQLModel getDeptTreeModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

//==========================由于目前数据库资产属性数据不准确，导致查询结果与实物台账查询不一致，暂时注释掉，今后再开放，唐明胜备注，切勿删除该段代码===============================
//        String sqlStr = "SELECT EOCM.COMPANY,\n" +
//                "       AMD.DEPT_NAME || '(' ||\n" +
//                "       CONVERT(VARCHAR,\n" +
//                "               (SELECT COUNT(1)\n" +
//                "                FROM   ETS_ITEM_INFO EII\n" +
//                "                WHERE  EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
//                "                       AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE)) || ')' DEPT_NAME\n" +
//                "FROM   AMS_MIS_DEPT    AMD,\n" +
//                "       ETS_OU_CITY_MAP EOCM\n" +
//                "WHERE  AMD.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
//                "       AND EOCM.ORGANIZATION_ID = ?\n";
//==========================由于目前数据库资产属性数据不准确，导致查询结果与实物台账查询不一致，暂时注释掉，今后再开放，唐明胜备注，切勿删除该段代码===============================
        StringBuilder sql = new StringBuilder();
        sql.append( "SELECT EOCM.COMPANY,\n" );
        sql.append( "		AMD.DEPT_NAME\n" );
        sql.append( "  FROM   AMS_MIS_DEPT    AMD,\n" );
        sql.append( "  		  ETS_OU_CITY_MAP EOCM\n" );
        sql.append( "  WHERE  AMD.COMPANY_CODE = EOCM.COMPANY_CODE\n" );
        
//        String sqlStr = "SELECT EOCM.COMPANY,\n" +
//                "       AMD.DEPT_NAME\n" +
//                "FROM   AMS_MIS_DEPT    AMD,\n" +
//                "       ETS_OU_CITY_MAP EOCM\n" +
//                "WHERE  AMD.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
//                "       AND EOCM.ORGANIZATION_ID = ?\n";

        if( userAccount.isProvAssetsManager() ){
        	sql.append( "  AND EOCM.IS_TD = ? \n" );
        	sqlArgs.add(userAccount.getIsTd());
        }else{
        	sql.append( "  AND EOCM.ORGANIZATION_ID = ? \n" );
        	sqlArgs.add(userAccount.getOrganizationId());
        }
        sql.append(" AND AMD.ENABLED='Y' ");
        if (!userAccount.getProvinceCode().equals(AssetsDictConstant.PROVINCE_CODE_SX)) {
                    if (!userAccount.isComAssetsManager() && !userAccount.isMtlAssetsManager() && !userAccount.isProvAssetsManager() ) {
                        if (userAccount.isDptAssetsManager()) {
                            DTOSet depts = userAccount.getPriviDeptCodes();
                            String deptStr = "'";
                            if( null != depts){
                                int deptCount = depts.getSize();
                                for (int i = 0; i < deptCount; i++) {
                                    AmsMisDeptDTO dept = (AmsMisDeptDTO) depts.getDTO(i);
                                    deptStr += dept.getDeptCode() + "'";
                                    if (i < deptCount - 1) {
                                        deptStr += ", '";
                                    }
                                }
                            }
                            sql.append( "  AND AMD.DEPT_CODE IN(" + deptStr + ") \n" );
            //                sqlStr += "AND AMD.DEPT_CODE IN(" + deptStr + ")";
                        } else {
                            sql.append( "  AND AMD.DEPT_CODE = ? \n" );
            //                sqlStr += "AND AMD.DEPT_CODE = ?";
                            sqlArgs.add(userAccount.getDeptCode());
                        }
                    }
        }
        sql.append( "  ORDER BY EOCM.ORGANIZATION_ID,AMD.DEPT_NUMBER, AMD.DEPT_NAME" );
        sqlModel.setSqlStr( sql.toString() );
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}

