package com.sino.ams.system.important.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.important.dto.PublishInfoDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * User: T_zhoujinsong
 * Date: 2011-3-14 17:29:20
 * Function:
 */

public class PublishInfoModel {
    private PublishInfoDTO dto = null;

    public PublishInfoModel() {
        this.dto = new PublishInfoDTO();
    }

    public PublishInfoDTO getDtoParameter() {
        return dto;
    }

    public void setDtoParameter(PublishInfoDTO dto) {
        this.dto = dto;
    }

    public SQLModel getLocationModel() {
        SQLModel sqlModel = new SQLModel();
        ArrayList list = new ArrayList();
        String sqlStr = "SELECT \n " +
                " SU.USERNAME PUBLISH_USER_NAME,\n" +
                "IIP.PUBLISH_ID,\n" +
                "IIP.TITLE,\n" +
                "IIP.CONTENTS,\n" +
                "IIP.PUBLISH_DATE,\n" +
                "IIP.TITLE_ONLY_FLAG," +
                " IIP.ENABLED  \n" +
                "FROM PMS_INFO_PUBLISH IIP , SF_USER SU  \n" +
                "WHERE  IIP.PUBLISH_USER_ID = SU.USER_ID \n" +
                "AND  " + SyBaseSQLUtil.isNotNull("IIP.TITLE") + "  " +
                "AND IIP.ENABLED = 'Y' " +
//                "   AND INFO_TYPE = '1' \n" +
                "AND  ( " + SyBaseSQLUtil.isNull() + "  OR SU.USERNAME LIKE ?) \n" +
                "AND IIP.TITLE LIKE dbo.NVL(?,IIP.TITLE)\n" +
                "AND ( " + SyBaseSQLUtil.isNull() + "  OR IIP.PUBLISH_DATE>=to_date(?,'YYYY-MM-DD')) \n" +
                "AND ( " + SyBaseSQLUtil.isNull() + "  OR IIP.PUBLISH_DATE<=to_date(?,'YYYY-MM-DD')) \n" +
                "ORDER BY IIP.PUBLISH_DATE DESC";
        list.add(dto.getPublishUserName());
        list.add(dto.getPublishUserName());
        /* list.add(dto.getTitle());*/
        list.add(dto.getTitle());
        list.add(dto.getPublishStartDate());
        list.add(dto.getPublishStartDate());
        list.add(dto.getPublishEndDate());
        list.add(dto.getPublishEndDate());
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getLocationTitle() {
        SQLModel sqlModel = new SQLModel();
        ArrayList list = new ArrayList();
        String sqlStr = "SELECT PUBLISH_ID, DOC_TYPE, TITLE, PUBLISH_DATE\n" +
                "  FROM PMS_INFO_PUBLISH\n" +
                " WHERE TITLE LIKE dbo.NVL(?, TITLE)\n" +
                "   AND INFO_TYPE='1' \n" +
                "    " + SyBaseSQLUtil.isNotNull("AND TITLE") + " ";
        list.add(dto.getTitle());
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }


    public SQLModel updateInfo() {
        List list = new ArrayList();
        SQLModel model = new SQLModel();
        String str = "UPDATE PMS_INFO_PUBLISH YT\n" +
                "   SET YT.TITLE           = ?,\n" +
                "       YT.ENABLED         = ?,\n" +
                "       YT.CONTENTS        = ?,\n" +
                "       YT.SEE_USER_TYPE   = ?,\n" +
                "       YT.TITLE_ONLY_FLAG = ?,\n" +
                "       YT.INFO_TYPE       = ?\n" +
                " WHERE YT.PUBLISH_ID = ?";
        list.add(dto.getTitle());
        list.add(dto.getEnabled());
        list.add(dto.getContents());
        list.add(dto.getSeeUserType());
        list.add(dto.getTitleOnlyFlag());
        list.add(dto.getInfoType());
        list.add(dto.getPublishId());
        model.setArgs(list);
        model.setSqlStr(str);
        return model;
    }

    public SQLModel getInsertNewAcceptanceModel(HttpServletRequest request) {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql =
                "INSERT INTO \n" +
                        "PMS_INFO_PUBLISH\n" +
                        "  (PUBLISH_USER_ID,INFO_TYPE,PUBLISH_DATE,TITLE,CONTENTS,TITLE_ONLY_FLAG,PUBLISH_ID,SEE_USER_TYPE)\n" +
                        "VALUES\n" +
                        "  (?,?, GETDATE(), ?, ?, ?, ?, ?)";
        strArg.add(dto.getPublishUserId());
        strArg.add(dto.getInfoType());
        strArg.add(dto.getTitle());
        strArg.add(dto.getContents());
        strArg.add(dto.getTitleOnlyFlag());
        strArg.add(dto.getPublishId());
        strArg.add(dto.getSeeUserType());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }

    //按更新时间取前5个标题

    public SQLModel getTitle() {
        List list = new ArrayList();
        SQLModel sqlModel = new SQLModel();
        String strSql = "SELECT IIP.TITLE FROM PMS_INFO_PUBLISH IIP \n" +
                "WHERE \n" +
                " ROWNUM<6\n" +
                "   AND INFO_TYPE = '1' \n" +
                "   ORDER BY IIP.PUBLISH_DATE DESC";
        return sqlModel;
    }

    public SQLModel showDetailModel() {
        List strArg = new ArrayList();
        SQLModel sqlModel = new SQLModel();
        String strSql = "";
        strArg.add(dto.getPublishId());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }

    public SQLModel getInfoDetailModel() {
        List strArg = new ArrayList();
        SQLModel sqlModel = new SQLModel();
        String strSql =
                "SELECT PUBLISH_USER_ID,\n" +
                        " PUBLISH_DATE,\n" +
                        " PUBLISH_ID,\n" +
                        " ENABLED,\n" +
                        " DOC_TYPE,\n" +
                        " TITLE,\n" +
                        " CONTENTS,\n" +
                        " SEE_USER_TYPE,\n" +
                        " TITLE_ONLY_FLAG, \n " +
                        " INFO_TYPE \n " +
                        " FROM PMS_INFO_PUBLISH IIP \n" +
                        "WHERE PUBLISH_ID=? \n";
//                        "   AND INFO_TYPE = '1' \n";
        strArg.add(dto.getPublishId());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }

    public PublishInfoDTO getUserPriviDTO() {
        return dto;
    }


    public void setParaDTO(PublishInfoDTO dto) {
        this.dto = dto;
    }

    public SQLModel inserNoticeVendorModel(String publishId, String vendorId, String vendorName, String userId) {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "INSERT INTO PMS_PUBLISH_FOR_VENDOR\n" +
                "  (PUBLISH_ID,\n" +
                "   VENDOR_ID,\n" +
                "   VENDOR_NAME,\n" +
                "   USER_ID)\n" +
                "VALUES(?, ?, ?, ?)";
        strArg.add(publishId);
        strArg.add(vendorId);
        strArg.add(vendorName);
        strArg.add(userId);
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }

    public SQLModel getNoticeVendorModel(String publishId) {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "SELECT PPFV.VENDOR_ID,\n" +
                "       PPFV.VENDOR_NAME,\n" +
                "       SU.USER_ID,\n" +
                "       SU.USERNAME,\n" +
                "       SU.MOBILE_PHONE\n" +
                "  FROM PMS_PUBLISH_FOR_VENDOR PPFV,\n" +
                "       SF_USER                SU\n" +
                " WHERE PPFV.PUBLISH_ID = ?\n" +
                "       AND PPFV.USER_ID = SU.USER_ID";
        strArg.add(publishId);
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }

    public SQLModel deleteNoticeVendorModel(String publishId) {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "DELETE FROM PMS_PUBLISH_FOR_VENDOR WHERE PUBLISH_ID = ?";
        strArg.add(publishId);
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }
}
