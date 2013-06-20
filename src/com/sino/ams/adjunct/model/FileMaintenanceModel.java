package com.sino.ams.adjunct.model;

import java.util.ArrayList;

import com.sino.ams.adjunct.dto.FileDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-7-29
 * Time: 10:09:01
 * To change this template use File | Settings | File Templates.
 */
public class FileMaintenanceModel {
    private FileDTO dtoParameter = null;

    public FileMaintenanceModel(FileDTO dtoParameter) {
        this.dtoParameter = dtoParameter;
    }

    public FileDTO getDtoParameter() {
        return dtoParameter;
    }

    public void setDtoParameter(FileDTO dtoParameter) {
        this.dtoParameter = dtoParameter;
    }

    @SuppressWarnings("unchecked")
	public SQLModel getAttachesModel() {
        String sql = "SELECT IA.ORDER_PK_VALUE,\n" +
                "       IA.ORDER_PK_NAME,\n" +
                "       IA.FILE_NAME,\n" +
                "       IA.FILE_PATH,\n" +
                "       IA.CREATION_DATE,\n" +
                "       IA.CREATED_BY,\n" +
                "       IA.DESCRIPTION,\n" +
                "       SU.USERNAME USER_NAME\n" +
                "  FROM AMS_ASSETS_ATTACH IA, SF_USER SU\n" +
                " WHERE IA.CREATED_BY = SU.USER_ID\n" +
                "   AND IA.ORDER_PK_NAME = ?";
        ArrayList al = new ArrayList();
        al.add(dtoParameter.getOrderPkName());
        SQLModel sqlModel = new SQLModel();
        sqlModel.setSqlStr(sql);
        sqlModel.setArgs(al);
        return sqlModel;
    }

    @SuppressWarnings("unchecked")
	public SQLModel getAttachCountModel() {
        String sql = "SELECT COUNT(1) ATTACH_COUNT\n" +
                "  FROM AMS_ASSETS_ATTACH IA\n" +
                " WHERE IA.ORDER_PK_NAME = ?";
        ArrayList al = new ArrayList();
        al.add(dtoParameter.getOrderPkName());
        SQLModel sqlModel = new SQLModel();
        sqlModel.setSqlStr(sql);
        sqlModel.setArgs(al);
        return sqlModel;
    }

    @SuppressWarnings("unchecked")
	public static SQLModel getAttachesModel(String orderPkName) {
        String sql = "SELECT IA.ORDER_PK_VALUE,\n" +
                "       IA.ORDER_PK_NAME,\n" +
                "       IA.FILE_NAME,\n" +
                "       IA.FILE_PATH,\n" +
                "       IA.CREATION_DATE,\n" +
                "       IA.CREATED_BY,\n" +
                "       IA.DESCRIPTION,\n" +
                "       SU.USERNAME USER_NAME\n" +
                "  FROM AMS_ASSETS_ATTACH IA, SF_USER SU\n" +
                " WHERE IA.CREATED_BY = SU.USER_ID\n" +
                "   AND IA.ORDER_PK_NAME = ?";
        ArrayList al = new ArrayList();
        al.add(orderPkName);
        SQLModel sqlModel = new SQLModel();
        sqlModel.setSqlStr(sql);
        sqlModel.setArgs(al);
        return sqlModel;
    }
    @SuppressWarnings("unchecked")
	public SQLModel getAttachByIdModel() {
        String strSql =
                "SELECT AAA.* FROM AMS_ASSETS_ATTACH AAA WHERE AAA.ORDER_PK_VALUE = ? ";
        ArrayList strArg = new ArrayList();
        strArg.add(dtoParameter.getOrderPkValue());
        SQLModel sqlModel = new SQLModel();
        sqlModel.setSqlStr(strSql);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }

    @SuppressWarnings("unchecked")
	public SQLModel getInsertNewAttachModel() {
        String strSql =
        		" INSERT INTO AMS_ASSETS_ATTACH\n" +
                "     ( ORDER_PK_VALUE,\n" +
                "      ORDER_TYPE,\n" +
                "      ORDER_TABLE,\n" +
                "      ORDER_PK_NAME,\n" +
                "      FILE_TYPE,\n" +
                "      FILE_NAME,\n" +
                "      FILE_PATH,\n" +
                "      CREATION_DATE,\n" +
                "      DESCRIPTION,\n" +
                "      CREATED_BY)\n" +
                //"  VALUES( " + SyBaseSQLUtil.getNewID( "AMS_ASSETS_ATTACH_S" ) + " , ? ,?, ?, ?, ?, ?, GETDATE(),?, ?)";
        		"  VALUES( NEWID(), ? ,?, ?, ?, ?, ?, GETDATE(), ?, ?)";
        ArrayList strArg = new ArrayList();
        strArg.add(dtoParameter.getOrderType());
        strArg.add(dtoParameter.getOrderTable());
        strArg.add(dtoParameter.getOrderPkName());
        strArg.add(dtoParameter.getFileType());
        strArg.add(dtoParameter.getFileName());
        strArg.add(dtoParameter.getFilePath());
        strArg.add(dtoParameter.getDescription());
        strArg.add(dtoParameter.getCreatedBy());
        SQLModel sqlModel = new SQLModel();
        sqlModel.setSqlStr(strSql);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }

    /**
     * 得到获取附件存储路径SQLModel
     * @return
     */
    @SuppressWarnings("unchecked")
	public SQLModel getPathModel() {
        String strSql =
                "SELECT AAA.FILE_PATH FROM AMS_ASSETS_ATTACH AAA WHERE AAA.ORDER_PK_VALUE = ?";
        ArrayList strArg = new ArrayList();
        strArg.add(dtoParameter.getOrderPkValue());
        SQLModel sqlModel = new SQLModel();
        sqlModel.setSqlStr(strSql);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }

    /**
     * 得到删除附件SQLModel
     * @return
     */
    @SuppressWarnings("unchecked")
	public SQLModel getDeleteAttachModel() {
        String strSql =
                "DELETE FROM AMS_ASSETS_ATTACH WHERE ORDER_PK_VALUE = ? ";
        ArrayList strArg = new ArrayList();
        strArg.add(dtoParameter.getOrderPkValue());
        SQLModel sqlModel = new SQLModel();
        sqlModel.setSqlStr(strSql);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }


}
