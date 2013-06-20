package com.sino.ams.important.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.important.dto.ImpInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.security.bean.SessionUtil;

/**
 * Created by IntelliJ IDEA.
 * User: yu
 * Date: 2007-5-28
 * Time: 14:39:04
 * To change this template use File | Settings | File Templates.
 */
public class ImpInfoModel {
    private ImpInfoDTO dto = null;

    public ImpInfoModel() {
        this.dto = new ImpInfoDTO();
    }

    public ImpInfoDTO getDtoParameter() {
        return dto;
    }

    public void setDtoParameter(ImpInfoDTO dto) {
        this.dto = dto;
    }

    public SQLModel getLocationModel(HttpServletRequest req) {
        SQLModel sqlModel = new SQLModel();
        ArrayList list = new ArrayList();
        String sqlStr = "SELECT \n " +
                " SU.USERNAME PUBLISH_USER_NAME,\n" +
                "IIP.PUBLISH_ID,\n" +
                "IIP.TITLE,\n" +
                "IIP.CONTENTS,\n" +
                "IIP.PUBLISH_DATE,\n" +
                "IIP.TITLE_ONLY_FLAG," +
                " IIP.DISABLED  \n" +
                "FROM AMS_INFO_PUBLISH IIP , SF_USER SU  \n" +
                "WHERE  IIP.PUBLISH_USER_ID = SU.USER_ID \n" +
                "AND  " + SyBaseSQLUtil.isNotNull("IIP.TITLE") + "  " +
                "AND IIP.DISABLED = 'N' " +
                "AND INFO_TYPE = '1' \n" +
                "AND ( " + SyBaseSQLUtil.nullStringParam() + " OR SU.USERNAME LIKE ?) \n" +
                "AND (  " + SyBaseSQLUtil.nullStringParam() + " OR IIP.TITLE LIKE ? )\n" +
                "AND (  " + SyBaseSQLUtil.nullStringParam() + " OR IIP.PUBLISH_DATE>=CONVERT(DATE, ?) ) \n" +
                "AND (  " + SyBaseSQLUtil.nullStringParam() + " OR IIP.PUBLISH_DATE<=CONVERT(DATE, ?) ) \n" +
                "AND SU.ORGANIZATION_ID = ? \n" +
                "ORDER BY IIP.PUBLISH_DATE DESC";
        
        SyBaseSQLUtil.nullStringParamArgs(list, dto.getPublishUserName() );
        
        SyBaseSQLUtil.nullStringParamArgs(list, dto.getTitle() );
        SyBaseSQLUtil.nullStringParamArgs(list, dto.getPublishStartDate() );
        SyBaseSQLUtil.nullStringParamArgs(list, dto.getPublishEndDate() );
        
        SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(req);
        list.add(userAccount.getOrganizationId());

        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getLocationTitle() {
        SQLModel sqlModel = new SQLModel();
        ArrayList list = new ArrayList();
        String sqlStr = "SELECT PUBLISH_ID, " +
                "DOC_TYPE, " +
                "TITLE, " +
                "PUBLISH_DATE\n" +
                "  FROM AMS_INFO_PUBLISH\n" +
                " WHERE TITLE LIKE dbo.NVL(?, TITLE)\n" +
                "   AND INFO_TYPE='1' \n" +
                "   AND  " + SyBaseSQLUtil.isNotNull("TITLE") +
                " ORDER BY PUBLISH_DATE DESC \n";
        list.add(dto.getTitle());
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }


    public SQLModel updateInfo() {
        List list = new ArrayList();
        SQLModel model = new SQLModel();
        String str = "UPDATE AMS_INFO_PUBLISH \n" +
                "   SET TITLE           = ?,\n" +
                "       DISABLED        = ?,\n" +
                "       CONTENTS        = ?,\n" +
                "       SEE_USER_TYPE   = ?,\n" +
                "       TITLE_ONLY_FLAG = ?\n" +
                " WHERE PUBLISH_ID = ?";
        list.add(dto.getTitle());
        list.add(dto.getDisabled());
        list.add(dto.getContents());
        list.add(dto.getSeeUserType());        
        list.add(dto.getTitleOnlyFlag());
        list.add(dto.getPublishId());
        model.setArgs(list);
        model.setSqlStr(str);
        return model;
    }

    public SQLModel getInsertNewAcceptanceModel() {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql =
                "INSERT INTO \n" +
                        "AMS_INFO_PUBLISH\n" +
                        "  (PUBLISH_USER_ID,INFO_TYPE,PUBLISH_DATE,TITLE,CONTENTS,TITLE_ONLY_FLAG,PUBLISH_ID,SEE_USER_TYPE)\n" +
                        "VALUES\n" +
						"  (?, '1', GETDATE(), ?, ?, ?, ?, ?)";
        strArg.add(dto.getPublishUserId());
        strArg.add(dto.getTitle());
        strArg.add(dto.getContents());
        strArg.add(dto.getTitleOnlyFlag());
        strArg.add(dto.getPublishId());
        strArg.add(dto.getSeeUserType());
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
                        " DISABLED,\n" +
                        " DOC_TYPE,\n" +
                        " TITLE,\n" +
                        " CONTENTS,\n" +
                        " SEE_USER_TYPE,\n" +
                        " TITLE_ONLY_FLAG \n " +
                        " FROM AMS_INFO_PUBLISH IIP \n" +
                        "WHERE PUBLISH_ID=? \n"+
                        "   AND INFO_TYPE = '1' \n";
        strArg.add(dto.getPublishId());
        sqlModel.setArgs(strArg);
        sqlModel.setSqlStr(strSql);
        return sqlModel;
    }

    public ImpInfoDTO getUserPriviDTO() {
        return dto;
    }


    public void setParaDTO(ImpInfoDTO dto) {
        this.dto = dto;
    }
}
