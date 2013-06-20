package com.sino.ams.system.resource.dao;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.resource.dto.SfResDefineDTO;
import com.sino.ams.system.resource.model.ResouceAdjustModel;
import com.sino.ams.system.resource.model.SfResDefineModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SfResDefineDAO</p>
 * <p>Description:程序自动生成服务程序“SfResDefineDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) {year}</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class ResourceAdjustDAO extends AMSBaseDAO {


    /**
     * 功能：SF_RES_DEFINE 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SfResDefineDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public ResourceAdjustDAO(SfUserDTO userAccount, SfResDefineDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SfResDefineDTO dtoPara = (SfResDefineDTO) dtoParameter;
        super.sqlProducer = new SfResDefineModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：获取URL资源菜单栏目的下拉列表
     * @param sfResource SfResDefineDTO 选中的资源
     * @return String
     * @throws com.sino.base.exception.QueryException
     *
     */
    public String getResourceOption(SfResDefineDTO sfResource) throws QueryException {
        StringBuffer resourceOption = new StringBuffer();
        String selectedResource = sfResource.getResParId();
        ResouceAdjustModel adjustModel=new ResouceAdjustModel(userAccount,(SfResDefineDTO)dtoParameter);
        SQLModel sqlModel = adjustModel.getResourceOptionModel(sfResource.getResId());
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(SfResDefineDTO.class.getName());
        simp.executeQuery();
        DTOSet resources = simp.getDTOSet();
        if (resources != null && !resources.isEmpty()) {
            SfResDefineDTO resource = null;
            int dotCount = 0;
            String resourceId = "";
            String resourceName = "";
            resourceOption.append("<option value=\"\">选择上级栏目</option>");
            resourceOption.append("<option value=\"\">根目录</option>");
            for (int i = 0; i < resources.getSize(); i++) {
                resource = (SfResDefineDTO) resources.getDTO(i);
                resourceId = resource.getResId();
                resourceName = resource.getResName();
                dotCount = StrUtil.containNum(resourceId, ".");
                resourceName = StrUtil.getStrByCount("&nbsp;&nbsp;", dotCount)
                        + resourceId
                        + " "
                        + resourceName;
                resourceOption.append("<option value=\"");
                resourceOption.append(resourceId);
                resourceOption.append("\"");
                if (resourceId.equals(selectedResource)) {
                    resourceOption.append(" selected");
                }
                resourceOption.append(">");
                resourceOption.append(resourceName);
                resourceOption.append("</option>");
            }
        }
        return resourceOption.toString();
    }


    /**
     * 功能：创建URL资源
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("URL资源");
    }

    /**
     * 功能：更新URL资源
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    public void updateData() throws DataHandleException {
        boolean autoCommit = true;
        boolean hasError = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            setDTOClassName(SfResDefineDTO.class.getName());
            SfResDefineDTO oldResource = (SfResDefineDTO) getDataByPrimaryKey();
            SfResDefineDTO newResource = (SfResDefineDTO) dtoParameter;
            if (newResource.getEnabled().equals(WebAttrConstant.FALSE_VALUE)) {//如果要失效，则先失效
                passviateResource();
            } else if (newResource.getEnabled().equals(WebAttrConstant.TRUE_VALUE)) {//如果要生效，则先生效
                activateResource();
            }
            if (oldResource.getResParId().equals(newResource.getResParId())) {
                super.updateData();
            } else {
                updateResourceRecursive(oldResource);
            }
            conn.commit();
            prodMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
            hasError = false;
        } catch (QueryException ex) {
            ex.printLog();
            prodMessage(MsgKeyConstant.UPDATE_DATA_FAILURE);
        } catch (DataHandleException ex) {
            ex.printLog();
            prodMessage(MsgKeyConstant.UPDATE_DATA_FAILURE);
        } catch (SQLException ex) {
            Logger.logError(ex);
            prodMessage(MsgKeyConstant.UPDATE_DATA_FAILURE);
        } finally {
            try {
                if (!hasError) {
                    conn.rollback();
                }
                getMessage().addParameterValue("URL资源");
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
            }
        }
    }


    /**
     * 功能：递归更新资源信息。
     * @param oldResource 页面更新的URL对象
     * @return boolean
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private boolean updateResourceRecursive(SfResDefineDTO oldResource) throws DataHandleException {
        boolean operateResult = false;
        try {
            SfResDefineDTO newResource = (SfResDefineDTO) dtoParameter;
            String newResParId = newResource.getResParId();
            newResource.setResParId(oldResource.getResParId());
            newResource.setNewResParId(newResParId);
            DTOSet resources = new DTOSet();
            resources.addDTO(newResource);
            DTOSet tmpResources = getAllChildResources(oldResource.getResId());
            if (tmpResources != null && !tmpResources.isEmpty()) {
                resources.addDTOs(tmpResources);
            }
            String newResourceId = "";
            SfResDefineDTO parResource = null;
            for (int i = 0; i < resources.getSize(); i++) {//生成新的结构数据
                SfResDefineDTO currResource = (SfResDefineDTO) resources.getDTO(i);
                if (StrUtil.isEmpty(currResource.getNewResParId())) {
                    parResource = currResource.getParent(resources);
                    newResParId = parResource.getNewResId();
                }
                newResourceId = getNewChildId(newResParId);
                currResource.setNewResParId(newResParId);
                currResource.setNewResId(newResourceId);

                newResourceId = currResource.getResId();//备份好数据
                newResParId = currResource.getResParId();

                currResource.setResId(currResource.getNewResId());//准备更新数据
                currResource.setResParId(currResource.getNewResParId());
                SfResDefineModel tmpModel = new SfResDefineModel(userAccount, currResource);
                DBOperator.updateRecord(tmpModel.getDataUpdateModel(), conn);

                currResource.setResId(newResourceId);//恢复数据，以便下一个资源查找父资源使用。
                currResource.setResParId(newResParId);
                resources.set(i, currResource);
            }
            prodMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
            getMessage().addParameterValue("URL资源");
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        } catch (DTOException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        }
        return operateResult;
    }

    /**
     * 功能：获取指定资源下所有子资源，不含自身。
     * 修改URL资源时使用，由于自身在界面可能已经修改，故不应当含自身。
     * @param resourcePid String
     * @return DTOSet
     * @throws com.sino.base.exception.QueryException
     *
     */
    private DTOSet getAllChildResources(String resourcePid) throws QueryException {
        SfResDefineModel modelProducer = (SfResDefineModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getAllChildModel(resourcePid);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(SfResDefineDTO.class.getName());
        simp.executeQuery();
        return simp.getDTOSet();
    }

    /**
     * 功能：获取指定父资源下的新一个资源编号
     * @param resourcePid String
     * @return String
     * @throws com.sino.base.exception.QueryException
     *
     */
    private String getNewChildId(String resourcePid) throws QueryException {
        String nextResourceId = "";
        SfResDefineModel modelProducer = (SfResDefineModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getNewChildModel(resourcePid);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(SfResDefineDTO.class.getName());
        simp.executeQuery();
        if (simp.hasResult()) {
            SfResDefineDTO nextDTO = (SfResDefineDTO) simp.getFirstDTO();
            nextResourceId = nextDTO.getResId();
        }
        return nextResourceId;
    }

    /**
     * 功能：失效URL资源。含本URL资源和子资源。
     * @return 返回true表示成功，否则表示失败
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private boolean passviateResource() throws DataHandleException {
        SfResDefineModel modelProducer = (SfResDefineModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getPassviateResourceModel();
        return DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：生URL资源。含本URL资源和直系上级资源。
     * @return 返回true表示成功，否则表示失败
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private boolean activateResource() throws DataHandleException {
        SfResDefineModel modelProducer = (SfResDefineModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getActivateResourceModel();
        return DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：根据资源ID获取资源
     * @return SfResDefineDTO
     * @throws com.sino.base.exception.QueryException
     *
     */
    public SfResDefineDTO getResourceById() throws QueryException {
        SfResDefineDTO resource = new SfResDefineDTO();
        SfResDefineModel modelProducer = (SfResDefineModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getResourceByIdModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(SfResDefineDTO.class.getName());
        simp.executeQuery();
        if (simp.hasResult()) {
            resource = (SfResDefineDTO) simp.getFirstDTO();
        }
        return resource;
    }

    public RowSet getChildren(String resId) throws QueryException {
        RowSet rs = null;
        ResouceAdjustModel adjustModel = new ResouceAdjustModel(userAccount, (SfResDefineDTO) dtoParameter);
        SQLModel sqlModel = adjustModel.getChildrenModel(resId);
        SimpleQuery sq=new SimpleQuery(sqlModel,conn);
        sq.executeQuery();
        if (sq.hasResult()) {
            rs=sq.getSearchResult();
        }
        return rs;
    }
    public void updateChildrenOrder(String resIds[]) throws DataHandleException {
        ResouceAdjustModel adjustModel = new ResouceAdjustModel(userAccount, (SfResDefineDTO) dtoParameter);
        List sqlModelList =new ArrayList();
        for (int i = 0; i < resIds.length; i++) {
            String resId = resIds[i];
            SQLModel sqlModel=adjustModel.getUpdateResOrderModel(resId, i);
            sqlModelList.add(sqlModel);
        }
        DBOperator.updateBatchRecords(sqlModelList,conn);

    }
}