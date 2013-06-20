package com.sino.ams.system.user.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.dto.SfUserRightDTO;
import com.sino.ams.system.user.model.SfUserModel;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 唐明胜版权所有Copyright (c) 2003~2007。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Copyright: 作者授权北京思诺博信息技术有限公司在一定范围内使用</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 0.1
 */


public class SfUserDAO extends BaseDAO {
    private SfUserDTO userAccount = null;

    public SfUserDAO(BaseUserDTO userAccount, SfUserDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器baseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO
     * @param dtoParameter DTO
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SfUserDTO sfUser = (SfUserDTO) dtoParameter;
        super.sqlProducer = new SfUserModel(userAccount, sfUser);
    }

    private void prodNextUserId() throws SQLException {

    }

    /**
     * 检查用户登录名是否存在
     *
     * @param sfUser
     * @return
     * @throws QueryException
     */
    public boolean checkSfUser(SfUserDTO sfUser) throws QueryException {
        boolean hasRecord = false;
        SQLModel sqlModel = getCheckUserModel(sfUser);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            hasRecord = true;
        }

        return hasRecord;
    }

    public boolean saveData(SfUserDTO sfUser, DTOSet dtoSet) throws DataHandleException {
        boolean operateResult = true;
        try {
            boolean autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            boolean isNew = StrUtil.isEmpty(sfUser.getUserId());
            if (isNew) {
                SeqProducer seqProducer = new SeqProducer(conn);
                sfUser.setUserId(seqProducer.getStrNextSeq("SF_USER_S"));
                createData();
            } else {
                updateData();
            }
            SfUserRightDTO userRightDTO = new SfUserRightDTO();
            userRightDTO.setUserId(sfUser.getUserId());

            SfUserRightDAO sfUserRightDAO = new SfUserRightDAO(userAccount, userRightDTO, conn);
            sfUserRightDAO.deleteData();
            if (dtoSet != null && dtoSet.getSize() > 0) {
                for (int i = 0; i < dtoSet.getSize(); i++) {
                    userRightDTO = (SfUserRightDTO) dtoSet.getDTO(i);
                    if (isNew) {
                        userRightDTO.setUserId(sfUser.getUserId());
                    }
                    sfUserRightDAO.setDTOParameter(userRightDTO);
                    sfUserRightDAO.createData();
                }
            }
            conn.commit();
            conn.setAutoCommit(autoCommit);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return operateResult;
    }

    public RowSet getGroupOfOu(String organizationId) throws QueryException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT SG.GROUP_ID, SG.GROUP_NAME\n" +
                "  FROM SF_GROUP SG\n" +
                " WHERE SG.ORGANIZATION_ID = ?\n" +
                "   AND SG.ENABLED = 'Y'";
        sqlArgs.add(organizationId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        return simpleQuery.getSearchResult();
    }

    /**
     * 检查用户登录名是否重复
     *
     * @param sfUser SfUserDTO
     * @return SQLModel
     */

    private SQLModel getCheckUserModel(SfUserDTO sfUser) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT * FROM SF_USER SU WHERE UPPER(SU.LOGIN_NAME) = UPPER(?)";
        sqlArgs.add(sfUser.getLoginName().toUpperCase());

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }


    public Object getMuxData() throws QueryException {
        return super.getMuxData();
    }
}
