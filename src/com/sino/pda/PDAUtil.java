package com.sino.pda;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.constant.AmsOrderConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dao.SfUserDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.util.UserUtil;
import com.sino.base.data.Row;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.ams.bean.SyBaseSQLUtil;

/**
 * User: zhoujs
 * Date: 2007-10-23
 * Time: 15:14:21
 * Function:
 */
public class PDAUtil {

    /**
     * 根据用户登录名称获取用户信息
     * @param conn      数据库连接
     * @param loginName 用户登录名
     * @return SfUserDTO
     * @throws QueryException
     */
    public static SfUserDTO getUserInfo(Connection conn, String loginName) throws QueryException {
        SfUserDTO userAccount = new SfUserDTO();
        SfUserDTO sfUser = new SfUserDTO();
        sfUser.setLoginName(loginName.toUpperCase());
        SfUserDAO sfUserDAO = new SfUserDAO(userAccount, sfUser, conn);

        sfUserDAO.setDTOClassName(SfUserDTO.class.getName());
        DTOSet dtoSet = (DTOSet) sfUserDAO.getMuxData();
        if (dtoSet.getSize() > 0) {
            sfUser = (SfUserDTO) dtoSet.getDTO(0);
            UserUtil.enrichUserAccount(sfUser, conn);
        }
        return sfUser;
    }

    public static String xmlFormat(String str) {
        String m = str;
        //& < > " '，需要分别转换成 &amp; &lt; &gt; &quot; &apos;
        m = StrUtil.replaceStr(m, "&", "&amp;");
        m = StrUtil.replaceStr(m, "<", "&lt;");
        m = StrUtil.replaceStr(m, ">", "&gt;");
        m = StrUtil.replaceStr(m, "\"", "&quot;");
        m = StrUtil.replaceStr(m, "'", "&apos;");
        return m;
    }

    public static String getCurUploadFilePath(Connection conn) {
        String uploadPath = AmsOrderConstant.orderUploadPath;

        //String sql = "SELECT NEWID('ETS_UPLOAD_PATH_S') CODE";
        //改为取时间毫秒序列
        //String sql = "SELECT STUFF(STUFF(STUFF(STUFF(STUFF(STUFF(CONVERT(VARCHAR(100),GETDATE(),9),4,1,NULL),6,1,NULL),10,2,NULL),11,1,NULL),13,1,NULL),15,1,NULL) CODE";
        //SQLModel sqlModel = new SQLModel();
        //sqlModel.setSqlStr(sql);

/*        boolean flag = false;
        try {
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();

            if (simpleQuery.hasResult()) {
                Row row = simpleQuery.getSearchResult().getRow(0);
                s1 = row.getStrValue("CODE");
            }

        } catch (QueryException e) {
            Logger.logError(e.toString());
        } catch (ContainerException e) {
            Logger.logError(e.toString());
        }*/
        SeqProducer seqProducer = new SeqProducer(conn);
//        return seqProducer.getGUID();
        String s1 = seqProducer.getGUID();
        return uploadPath + "/" + s1;
    }

    public static boolean copyFile(String srcFilePath, String destFilePath) {
        boolean flag = false;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(destFilePath);
            FileInputStream fileInputStream = new FileInputStream(srcFilePath);
            char c = '\u0400';
            byte abyte0[] = new byte[c];
            for (int i = fileInputStream.read(abyte0, 0, 1024); i != -1; i = fileInputStream.read(abyte0, 0, 1024))
                fileOutputStream.write(abyte0, 0, i);

            fileInputStream.close();
            fileOutputStream.close();
            flag = true;
        } catch (IOException _ex) {
            Logger.logError(_ex);
        }
        return flag;
    }

    public static boolean deleteFile(String s) {
        if (StrUtil.isEmpty(s))
            return false;

        boolean flag = false;
        java.io.File file = new java.io.File(s);
        if (file.exists()) {
            flag = file.delete();
        }
        return flag;
    }

    /**
     * 清除目录（文件），如果是文件则删除该文件目录（包括所有文件）
     * @param filePath 文件目录
     */
    public static void clearDir(String filePath) {
        if (filePath.equals(""))
            return;

        File file = new File(filePath);
        if (file.exists()) {
            File parentFile;
            if (file.isFile()) {
                parentFile = file.getParentFile();
            } else {
                parentFile = file;
            }
            if (parentFile.isDirectory()) {
                File[] fe = parentFile.listFiles();
                for (int i = 0; i < fe.length; i++) {
                    fe[i].delete();
                }
                parentFile.delete();
            }

        }

    }


    public static String getOrderType(Connection conn, String orderTypeDesc) throws QueryException, ContainerException {
        String sqlStr = "SELECT EFV.CODE\n" +
                "  FROM ETS_FLEX_VALUES EFV, ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = ?\n" +
                "   AND EFV.VALUE = ?";

        List sqlArgs = new ArrayList();
        sqlArgs.add(DictConstant.WORKORDER_TYPE);
        sqlArgs.add(orderTypeDesc);

        SQLModel sqlModel = new SQLModel();
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        String workorderType = "";
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            workorderType = simpleQuery.getFirstRow().getStrValue("CODE");
        }

        return workorderType;
    }

}
