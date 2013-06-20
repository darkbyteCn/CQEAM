package com.sino.sinoflow.framework.resource.dao;


import java.sql.Connection;

import com.sino.base.constant.TreeConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.treeview.*;
import com.sino.base.util.StrUtil;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.constant.CustMessageKey;
import com.sino.sinoflow.framework.resource.dto.SfResDefineDTO;
import com.sino.sinoflow.framework.resource.dto.SfResPrivsDTO;
import com.sino.sinoflow.framework.resource.model.SfResPrivsModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfResPrivsDAO</p>
 * <p>Description:程序自动生成服务程序“SfResPrivsDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) {year}</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 * 
 * 修改人：白嘉
 * 修改日期：2008.8.20
 */


public class SfResPrivsDAO extends BaseDAO {
    private SfResPrivsModel sfResPrivsModel = null;
    /**
     * 功能：SF_RES_PRIVS 数据访问层构造函数
     * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SfResPrivsDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public SfResPrivsDAO(SfUserBaseDTO userAccount, SfResPrivsDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        initSQLProducer(userAccount, dtoParameter);
    }
    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        SfResPrivsDTO dtoPara = (SfResPrivsDTO)dtoParameter;
        super.sqlProducer = new SfResPrivsModel((SfUserBaseDTO)userAccount, dtoPara);
        sfResPrivsModel = (SfResPrivsModel)sqlProducer;
    }

    /**
     * 功能：获取URL资源菜单栏目的下拉列表
     * @param selectedResource String 选中的资源编号
     * @return String
     * @throws QueryException
     */
    public String getResourceOption(String selectedResource) throws QueryException {
        StringBuffer resourceOption = new StringBuffer();
        SQLModel sqlModel = sfResPrivsModel.getResourceOptionModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(SfResDefineDTO.class.getName());
        simp.executeQuery();
        DTOSet resources = simp.getDTOSet();
        if(resources != null && !resources.isEmpty()){
            SfResDefineDTO resource = null;
            int dotCount = 0;
            String resourceId = "";
            String resourceName = "";
            resourceOption.append("<option value=\"\">请选择</option>");
            for(int i = 0; i < resources.getSize(); i++){
                resource = (SfResDefineDTO)resources.getDTO(i);
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
                if(resourceId.equals(selectedResource)){
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
     * 功能：获取所有定义的资源。栏目定义用。
     * @param systemName 系统名称
     * @return String
     * @throws QueryException
     * @throws NodeException 
     */
    public String getTreeHtml(String systemName) throws QueryException, NodeException {
//        StringBuffer treeHtml = new StringBuffer();
//        try {
//            SQLModel sqlModel = sfResPrivsModel.getResourceTreeModel();
//            RelateSQLProperty sqlProp = new RelateSQLProperty();
//            sqlProp.setSqlModel(sqlModel);
//            sqlProp.setIdField("RES_ID");
//            sqlProp.setPidField("RES_PAR_ID");
//            sqlProp.setDescField("RES_NAME");
//            sqlProp.setUrlField("RES_URL");
//            sqlProp.setPopupField("IS_POPUP");
//            sqlProp.setPopscriptField("POPSCRIPT");
//            DataBaseTree dbTree = new DataBaseTree(conn, sqlProp);
//            Tree tree = dbTree.getTree();
//
//            tree.setRootDesc(systemName);
//            WebPageTree webTree = new WebPageTree(tree, TreeConstant.SIMPLE_DIV_TREE);
//            StyleProperty styleProperty = new StyleProperty();
//            styleProperty.setShowParURL(true);
//            webTree.setStyleProperty(styleProperty);
//            webTree.setTarFrame("right");
//            //原代码 webTree.setUrlPrefix("/servlet/com.sino.ams.system.resource.servlet.SfResPrivsServlet?act=" + WebActionConstant.QUERY_ACTION + "&resId=");
//            //修改后
//            webTree.setUrlPrefix("/servlet/com.sino.sinoflow.framework.resource.servlet.SfResPrivsServlet?act=" + WebActionConstant.QUERY_ACTION + "&resId=");
//            treeHtml.append(webTree.getTreeDataHtml());
//            treeHtml.append(webTree.getPageJs());
//        } catch (NodeException ex) {
//            Logger.logError(ex);
//            throw new QueryException(ex.getMessage());
//        }
//        return treeHtml.toString();
    	
    	
//    	MzTree2 mzTree = new MzTree2(conn);
//    	SfResPrivsModel modelProducer = (SfResPrivsModel) sqlProducer;
//		mzTree.setSqModel(modelProducer.getResourceTreeModel() );
//		mzTree.setTarget("right");
//		mzTree.setUrl("/servlet/com.sino.sinoflow.framework.resource.servlet.SfResPrivsServlet" );
//		mzTree.setLevels(3);
//		mzTree.setRootText( "思诺博工作流系统" );
//		mzTree.setTransParaName("resId");
//		mzTree.produceTree3();
//		return mzTree.getTreeStr();
		
    	StringBuffer treeHtml = new StringBuffer();
    	SfResPrivsModel modelProducer = (SfResPrivsModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getResourceTreeModel();
        RelateSQLProperty sqlProp = new RelateSQLProperty();
        sqlProp.setSqlModel(sqlModel);
        sqlProp.setIdField("RES_ID");
        sqlProp.setPidField("RES_PAR_ID");
        sqlProp.setDescField("RES_NAME");
        sqlProp.setUrlField("RES_URL");
        //sqlProp.setPopupField("IS_POPUP");
        //sqlProp.setPopscriptField("POPSCRIPT");
        DataBaseTree dbTree = new DataBaseTree(conn, sqlProp);
        Tree tree = dbTree.getTree();
        tree.setRootDesc(systemName);
        WebPageTree webTree = new WebPageTree(tree, TreeConstant.DIV_TREE);
        StyleProperty styleProperty = new StyleProperty();
        styleProperty.setInitExpand(false);  
        styleProperty.setShowParURL(true);
        webTree.setStyleProperty(styleProperty); 
        webTree.setTarFrame("right");
        webTree.setUrlPrefix("/servlet/com.sino.sinoflow.framework.resource.servlet.SfResPrivsServlet?act=" + WebActionConstant.QUERY_ACTION + "&resId=" );
        treeHtml.append(webTree.getTreeDataHtml());
        treeHtml.append(webTree.getPageJs());
        return treeHtml.toString();
    }

    /**
     * 功能：获取页面显示权限数据
     * @throws QueryException
     * @return DTOSet
     */
    public DTOSet getPageQueryDTOs() throws QueryException {
        DTOSet dtos = new DTOSet();
        try {
            SQLModel sqlModel = sfResPrivsModel.getPageQueryModel();
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.setDTOClassName(SfResPrivsDTO.class.getName());
            simp.executeQuery();
            dtos = simp.getDTOSet();
        } catch (SQLModelException ex) {
            Logger.logError(ex);
            throw new QueryException(ex);
        }
        return dtos;
    }

    /**
     * 功能：获取第一个菜单栏目。
     * @return SfResDefineDTO
     * @throws QueryException
     */
    public SfResDefineDTO getFirstResource() throws QueryException {
        SfResDefineDTO firstResource = new SfResDefineDTO();
        SQLModel sqlModel = sfResPrivsModel.getFirstResourceModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(SfResDefineDTO.class.getName());
        simp.executeQuery();
        firstResource = (SfResDefineDTO)simp.getFirstDTO();
        return firstResource;
    }

    /**
     * 功能：根据资源编号获取资源。
     * @return SfResDefineDTO
     * @throws QueryException
     */
    public SfResDefineDTO getResourceById() throws QueryException {
        SfResDefineDTO firstResource = new SfResDefineDTO();
        SQLModel sqlModel = sfResPrivsModel.getResourceByIdModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(SfResDefineDTO.class.getName());
        simp.executeQuery();
        firstResource = (SfResDefineDTO)simp.getFirstDTO();
        return firstResource;
    }

    /**
      * 功能：保存栏目访问权限。
      * @param resId systemId
      * @return boolean
      */
     public boolean saveResPrivis(DTOSet dtoSet,int resId){
         boolean operateResult = false;
         try {
             SfResPrivsDTO resPrivi = null;

             //删除以前的SfResPrivis栏目权限.
             deleteExistPrivi(resId);

             //获取父菜单的DTOSet SfResPrivsDTO
              DTOSet parentRes = getParentRes(resId);

             if(parentRes != null){//给父节点也赋相同的权限
                 int parentCount = parentRes.getSize();
                 SfResDefineDTO resource = null;
                 DTOSet appendRole = new DTOSet();
                 for(int i = 0; i < parentCount; i++){
                     resource = (SfResDefineDTO)parentRes.getDTO(i);
                     resId = resource.getSystemId();
                     SfResPrivsDTO tmpPrivi = null;
                     for(int j = 0; j < dtoSet.getSize(); j++){//给每个父节点加上子节点的角色权限
                         tmpPrivi = new SfResPrivsDTO();
                         resPrivi = (SfResPrivsDTO)dtoSet.getDTO(j);
                         tmpPrivi.setResId(resId);
                         tmpPrivi.setRoleName(resPrivi.getRoleName());//设置RoleName
                         tmpPrivi.setGroupName(resPrivi.getGroupName());
                         tmpPrivi.setCreatedBy(resPrivi.getCreatedBy());
                         appendRole.addDTO(tmpPrivi);
                     }
                 }
                 dtoSet.addDTOs(appendRole);
             }

             for(int i = 0; i < dtoSet.getSize(); i++){
                 resPrivi = (SfResPrivsDTO)dtoSet.getDTO(i);
                 saveResPrivis(resPrivi);
             }

             for (int i = 0; i < parentRes.getSize(); i++) {
                 SfResDefineDTO resDefineDTO = (SfResDefineDTO) parentRes.getDTO(i);
                 deleteInvalidatePrivi(resDefineDTO);
             }
             operateResult = true;
             prodMessage(CustMessageKey.PRIVI_SAVE_SUCCESS);
         } catch (DataHandleException ex) {
             Logger.logError(ex);
             prodMessage(CustMessageKey.RRIVI_SAVE_FAILURE);
         } catch (QueryException ex) {
             Logger.logError(ex);
             prodMessage(CustMessageKey.RRIVI_SAVE_FAILURE);
         } catch (DTOException ex) {
             Logger.logError(ex);
             prodMessage(CustMessageKey.RRIVI_SAVE_FAILURE);
         }
         return operateResult;
     }


    /**
     * 功能：删除权限。
     * @param resId systemId
     * @throws DataHandleException
     */
    public void deleteExistPrivi(int resId) throws DataHandleException {
        SQLModel sqlModel = sfResPrivsModel.getPriviDeleteModel(resId);
        DBOperator.updateRecord(sqlModel, conn);
    }

    public void deleteInvalidatePrivi(SfResDefineDTO resDefineDTO) throws DataHandleException {
        SQLModel sqlModel = sfResPrivsModel.getDeleteInValidatePriviModel(resDefineDTO);
        DBOperator.updateRecord(sqlModel, conn);
    }


    /**
     * 功能：保存权限
     * @param resPrivi SfResPrivsDTO
     * @throws DataHandleException
     */
    private void saveResPrivis(SfResPrivsDTO resPrivi) throws DataHandleException{
        try {
            if (!hasPriviData(resPrivi)) {
                SQLModel sqlModel = sfResPrivsModel.getPriviCreateModel(resPrivi);
                DBOperator.updateRecord(sqlModel, conn);
            }
        } catch (QueryException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
    }


    /**
     *
     * @param resPrivi SfResPrivsDTO
     * @return boolean
     * @throws QueryException
     */
    private boolean hasPriviData(SfResPrivsDTO resPrivi) throws QueryException {
        boolean hasData = false;
        SQLModel sqlModel = sfResPrivsModel.getPriviExistModel(resPrivi);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        hasData = simp.hasResult();
        return hasData;
    }
    /**
     *
     * @param systemId String
     * @return DTOSet
     * @throws QueryException
     */
    private DTOSet getParentRes(int systemId) throws QueryException {
        DTOSet parentRes = new DTOSet();
        SQLModel sqlModel = sfResPrivsModel.getParentResModel(systemId);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(SfResDefineDTO.class.getName());
        simp.executeQuery();
        parentRes = simp.getDTOSet();
        return parentRes;
    }

    public String getInitDate() throws QueryException{
    	DTOSet dtoSet = null;
        SQLModel sqlModel = sfResPrivsModel.getPriviModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(SfResPrivsDTO.class.getName());
        simp.executeQuery();
        if(simp.hasResult()){
        	StringBuffer arr = new StringBuffer();
        	dtoSet = simp.getDTOSet();
        	for(int i=0;i<dtoSet.getSize();i++){
        		SfResPrivsDTO dto = (SfResPrivsDTO)dtoSet.getDTO(i);
        		String group = dto.getGroupName();
        		arr.append("new Array('"+group+"','"+dto.getRoleName()+"'),");
        	}
        	String tp = arr.toString();
        	return tp.substring(0,tp.length()-1);
        }

    	return "";
    }    
}
