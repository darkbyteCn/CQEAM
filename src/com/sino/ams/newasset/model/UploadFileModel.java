package com.sino.ams.newasset.model;

import java.util.ArrayList;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.UploadFileDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-12
 * Time: 16:40:21
 * To change this template use File | Settings | File Templates.
 */
public class UploadFileModel extends AMSSQLProducer {
    private UploadFileDTO dto;

    public UploadFileModel(SfUserDTO userAccount, UploadFileDTO dto) {
        super(userAccount, dto);
        this.dto = dto;
    }

    public SQLModel insert(int userId) {
        String sql = " INSERT INTO AMS_ASSETS_ATTACH\n" +
                     "     ( ORDER_PK_VALUE,\n" +
                     "      ORDER_TYPE,\n" +
                     "      ORDER_TABLE,\n" +
                     "      ORDER_PK_NAME,\n" +
                     "      FILE_TYPE,\n" +
                     "      FILE_NAME,\n" +
                     "      FILE_PATH,\n" +
                     "      CREATION_DATE,\n" +
                     "      CREATED_BY)\n" +
                     "  values( NEWID() ,?,'AMS_ASSETS_TRANS_HEADER',?,?,?,?,GETDATE(),?)";
        ArrayList al = new ArrayList();
        al.add(dto.getOrderType());
        al.add(dto.getOrderPkName());
        al.add(dto.getFileType());
        al.add(dto.getFileName());
        al.add(dto.getFilePath());
        al.add(userId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    public SQLModel getFileModel(String transId, String fileType) {
        String sql = "SELECT AAA.*,\n" +
                     "       dbo.APP_GET_FLEX_VALUE(AAA.FILE_TYPE, 'FILE_TYPE') FILE_TYPE_DESC\n" +
                     "  FROM AMS_ASSETS_ATTACH AAA\n" +
                     " WHERE AAA.ORDER_PK_NAME = ?\n" +
                     "   AND AAA.FILE_TYPE = ?";
        ArrayList al = new ArrayList();
        al.add(transId);
        al.add(fileType);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //取路径
    public SQLModel getPathModel(String fileId) {
        String sql =
                "SELECT AAA.FILE_PATH FROM AMS_ASSETS_ATTACH WHERE ORDER_PK_VALUE = ?";
        ArrayList al = new ArrayList();
        al.add(fileId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //从数据库里删除记录
    public SQLModel deleteFromDB(String fileId) {
        String sql =
                "DELETE FROM AMS_ASSETS_ATTACH WHERE ORDER_PK_VALUE = ?";
        ArrayList al = new ArrayList();
        al.add(fileId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //从数据库中取附件信息
    public SQLModel getFileById(String fileId) {
        String sql =
                "SELECT AAA.* FROM AMS_ASSETS_ATTACH AAA WHERE AAA.ORDER_PK_VALUE = ?";
        ArrayList al = new ArrayList();
        al.add(fileId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //修改附件描述
    public SQLModel updateDesc(String fileId, String desc) {
        String sql =
                "UPDATE AMS_ASSETS_ATTACH SET DESCRIPTION=? WHERE ORDER_PK_VALUE=? ";
        ArrayList al = new ArrayList();
        al.add(desc);
        al.add(fileId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }
}
