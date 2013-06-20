package com.sino.ams.newasset.bean;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class FlexValueUtil {
    private Connection conn = null;
    private SfUserDTO userAccount = null;

    public FlexValueUtil(SfUserDTO userAccount, Connection conn) {
        this.userAccount = userAccount;
        this.conn = conn;
    }

    /**
     * 功能：获取指定字典分类下，指定字典代码的描述值
     * @param dictCode1 String 字典分类
     * @param dictCode2 String 字典代码
     * @return String 代码的描述值
     * @throws QueryException
     */
    public String getFlexValue(String dictCode1, String dictCode2) throws
            QueryException {
        String flexValue = "";
        try {
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr = "SELECT"
                            + " EFV.VALUE"
                            + " FROM"
                            + " ETS_FLEX_VALUES    EFV,"
                            + " ETS_FLEX_VALUE_SET EFVS"
                            + " WHERE"
                            + " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
                            + " AND EFVS.CODE = ?"
                            + " AND EFV.CODE = ?";
            sqlArgs.add(dictCode1);
            sqlArgs.add(dictCode2);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                Row row = simp.getFirstRow();
                flexValue = row.getStrValue("VALUE");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return flexValue;
    }
}
