package com.sino.ams.newasset.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.bean.CustomQryFields;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsAssetsCommQueryDTO;
import com.sino.ams.newasset.model.CustomQuerySetModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.WorldConstant;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsAssetsCommQueryDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsCommQueryDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class CustomQuerySetDAO extends AMSBaseDAO {


    /**
     * 功能：资产综合查询设置 AMS_ASSETS_COMM_QUERY 数据访问层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsCommQueryDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public CustomQuerySetDAO(SfUserDTO userAccount,
                             AmsAssetsCommQueryDTO dtoParameter,
                             Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsCommQueryDTO dtoPara = (AmsAssetsCommQueryDTO) dtoParameter;
        super.sqlProducer = new CustomQuerySetModel((SfUserDTO) userAccount,
                dtoPara);
    }

    /**
     * 功能：获取用户自定义的字段
     * @param fieldUsage String 字段用途:用于查询还是显示
     * @return DTOSet
     * @throws QueryException
     */
    public DTOSet getCheckedFields(String fieldUsage) throws QueryException {
        DTOSet checkedFields = new DTOSet();
        CustomQuerySetModel modelProducer = (CustomQuerySetModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getFieldsByUserIdModel(fieldUsage);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(AmsAssetsCommQueryDTO.class.getName());
        simp.executeQuery();
        checkedFields = simp.getDTOSet();
        return checkedFields;
    }

    /**
     * 功能：获取通用查询所有待选字段列表
     * @return String
     * @throws QueryException
     */
    public String getAllQueryFields() throws QueryException {
        StringBuffer fieldsOption = new StringBuffer();
        DTOSet fields = getCheckedFields(AssetsDictConstant.FIELD_FOR_QUERY);
        Object fieldName = "";
        Object fieldComment = "";
        Map fieldMap = CustomQryFields.getFieldsMap();
        Iterator fieldNames = fieldMap.keySet().iterator();
        while (fieldNames.hasNext()) {
            fieldName = fieldNames.next();
            if (fields != null && fields.contains("fieldName", fieldName)) {
                continue;
            }
            fieldComment = fieldMap.get(fieldName);
            fieldsOption.append("<option value=\"");
            fieldsOption.append(fieldName);
            fieldsOption.append("\">");
            fieldsOption.append(fieldComment);
            fieldsOption.append("</option>");
            fieldsOption.append(WorldConstant.ENTER_CHAR);
        }
        return fieldsOption.toString();
    }


    /**
     * 功能：获取通用查询中用户选择保存的字段
     * @return String
     * @throws QueryException
     */
    public String getChkQueryFields() throws QueryException {
        StringBuffer fieldsOption = new StringBuffer();
        DTOSet fields = getCheckedFields(AssetsDictConstant.FIELD_FOR_QUERY);
        Object fieldName = "";
        Object fieldComment = "";
        Map fieldMap = CustomQryFields.getFieldsMap();
        if (fields != null) {
            AmsAssetsCommQueryDTO field = null;
            for (int i = 0; i < fields.getSize(); i++) {
                field = (AmsAssetsCommQueryDTO) fields.getDTO(i);
                fieldName = field.getFieldName();
                fieldComment = fieldMap.get(fieldName);
                fieldsOption.append("<option value=\"");
                fieldsOption.append(fieldName);
                fieldsOption.append("\">");
                fieldsOption.append(fieldComment);
                fieldsOption.append("</option>");
                fieldsOption.append(WorldConstant.ENTER_CHAR);
            }
        }
        return fieldsOption.toString();
    }

    /**
     * 功能：获取通用查询所有待选字段列表
     * @return String
     * @throws QueryException
     */
    public String getAllDisplayFields() throws QueryException {
        StringBuffer fieldsOption = new StringBuffer();
        DTOSet fields = getCheckedFields(AssetsDictConstant.FIELD_FOR_DISPL);
        Object fieldName = "";
        Object fieldComment = "";
        Map fieldMap = CustomQryFields.getFieldsMap();
        Iterator fieldNames = fieldMap.keySet().iterator();
        while (fieldNames.hasNext()) {
            fieldName = fieldNames.next();
            if (fields != null && fields.contains("fieldName", fieldName)) {
                continue;
            }
            fieldComment = fieldMap.get(fieldName);
            fieldsOption.append("<option value=\"");
            fieldsOption.append(fieldName);
            fieldsOption.append("\">");
            fieldsOption.append(fieldComment);
            fieldsOption.append("</option>");
            fieldsOption.append(WorldConstant.ENTER_CHAR);
        }
        return fieldsOption.toString();
    }


    /**
     * 功能：获取通用查询中用户选择保存的字段
     * @return String
     * @throws QueryException
     */
    public String getChkDisplayFields() throws QueryException {
        StringBuffer fieldsOption = new StringBuffer();
        DTOSet fields = getCheckedFields(AssetsDictConstant.FIELD_FOR_DISPL);
        Object fieldName = "";
        Object fieldComment = "";
        Map fieldMap = CustomQryFields.getFieldsMap();
        if (fields != null) {
            AmsAssetsCommQueryDTO field = null;
            for (int i = 0; i < fields.getSize(); i++) {
                field = (AmsAssetsCommQueryDTO) fields.getDTO(i);
                fieldName = field.getFieldName();
                fieldComment = fieldMap.get(fieldName);
                fieldsOption.append("<option value=\"");
                fieldsOption.append(fieldName);
                fieldsOption.append("\">");
                fieldsOption.append(fieldComment);
                fieldsOption.append("</option>");
                fieldsOption.append(WorldConstant.ENTER_CHAR);
            }
        }
        return fieldsOption.toString();
    }

    /**
     * 功能：保存自定义字段
     * @param chkQueryFields String[] 选中的查询条件字段
     * @param chkDisplayFields String[] 选中的显示字段
     * @return boolean
     */
    public boolean saveCustomizeFields(String[] chkQueryFields,
                                       String[] chkDisplayFields) {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            CustomQuerySetModel modelProducer = (CustomQuerySetModel)
                                                sqlProducer;
            SQLModel sqlModel = modelProducer.getDeleteFieldsByUserIdModel();
            DBOperator.updateRecord(sqlModel, conn);
            saveCustomizedFields(chkQueryFields,
                                 AssetsDictConstant.FIELD_FOR_QUERY);
            saveCustomizedFields(chkDisplayFields,
                                 AssetsDictConstant.FIELD_FOR_DISPL);
            operateResult = true;
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (SQLException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                    prodMessage(AssetsMessageKeys.COMM_QRY_CUST_SUCCESS);
                } else {
                    conn.rollback();
                    prodMessage(AssetsMessageKeys.COMM_QRY_CUST_SUCCESS);
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                prodMessage(AssetsMessageKeys.SQL_ERROR);
            }
        }
        return operateResult;
    }

    /**
     * 功能：保存自定义的字段
     * @param fields String[]
     * @param fieldUsage String
     * @throws DataHandleException
     */
    private void saveCustomizedFields(String[] fields, String fieldUsage) throws
            DataHandleException {
        String field = "";
        int index = -1;
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            index = field.indexOf(";");
            AmsAssetsCommQueryDTO dto = new AmsAssetsCommQueryDTO();
            dto.setFieldName(field.substring(0, index));
            dto.setFieldDesc(field.substring(index + 1));
            dto.setUserId(StrUtil.nullToString(userAccount.getUserId()));
            dto.setFieldUsage(fieldUsage);
            dto.setSortNo(i);
            setDTOParameter(dto);
            createData();
        }
    }
}
