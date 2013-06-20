package com.sino.ams.expand.model;

import java.util.ArrayList;

import com.sino.base.db.sql.model.SQLModel;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.expand.dto.UploadFileDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

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

    public SQLModel insert(String userId) {
        String sql = " INSERT INTO AMS_ITEM_FILES\n" +
                     "     ( BARCODE,\n" +
                     "      FILE_DESC,\n" +
                     "      FILE_PATH,\n" +
                     "      SYSTEM_ID,\n" +
                     "      CREATION_DATE,\n" +
                     "      CREATED_BY)\n" +
                     "  values(?,?,?,NEWID(),GETDATE(),CONVERT(INT, ?))";
        ArrayList al = new ArrayList();
        al.add(dto.getBarcode());
        al.add(dto.getFileDesc());
        al.add(dto.getFilePath());
        al.add(userId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    public SQLModel getFileModel(String transId) {
        String sql = "SELECT AIF.*,\n" +
                     "       AIF.FILE_DESC\n" +
                     "  FROM AMS_ITEM_FILES AIF\n" +
                     " WHERE AIF.BARCODE = ?\n";
        ArrayList al = new ArrayList();
        al.add(transId);
//        al.add(fileType);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }

    //取路径
    public SQLModel getPathModel(String fileId) {
        String sql =
                "SELECT AIF.FILE_PATH FROM AMS_ITEM_FILES AIF WHERE AIF.SYSTEM_ID = ?";
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
                "DELETE FROM AMS_ITEM_FILES WHERE SYSTEM_ID = ?";
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
                "SELECT AIF.* FROM AMS_ITEM_FILES AIF WHERE AIF.SYSTEM_ID = ?";
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
                "UPDATE AMS_ITEM_FILES SET FILE_DESC=? WHERE SYSTEM_ID=? ";
        ArrayList al = new ArrayList();
        al.add(desc);
        al.add(fileId);
        SQLModel sm = new SQLModel();
        sm.setSqlStr(sql);
        sm.setArgs(al);
        return sm;
    }
}
