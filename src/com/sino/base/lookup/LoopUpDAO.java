package com.sino.base.lookup;

import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.structure.SQLObject;
import com.sino.base.db.structure.StructureParser;
import com.sino.base.exception.HandlerException;
import com.sino.base.exception.LookUpException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.log.Logger;
import com.sino.base.validate.LookUpValidator;
import com.sino.base.web.*;
import com.sino.framework.security.dto.ServletConfigDTO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public class LoopUpDAO extends WebPageView {
	private LookUpModel lookUpModel = null;
	private Connection conn = null;
	private LookUpProp lookProp = null;
	private SQLModel sqlModel = null;
	protected ServletConfigDTO servletConfig = null;

	public LoopUpDAO(LookUpModel lookUpModel, Connection conn, HttpServletRequest req){
		super(req, conn);
		this.lookUpModel = lookUpModel;
		this.conn = conn;
	}

	public void setServletConfig(ServletConfigDTO servletConfig) {
		this.servletConfig = servletConfig;
		lookUpModel.setServletConfig(servletConfig);
	}

	/**
	 * 功能：生成页面所需数据
	 * @throws LookUpException
	 */
	public void produceWebData() throws LookUpException{
		try {
			initData();
			setWebCheckProp(prodWebCheckProp());
			if(lookProp.isResultAsDTO()){
				setDTOClassName(lookProp.getDtoClass().getName());
			}
			setCalPattern(lookProp.getCalPattern());
            setCountPages(lookProp.isCountPages());
            produceWebData(sqlModel);
		} catch (QueryException ex) {
			ex.printLog();
			throw new LookUpException(ex);
		} catch (HandlerException ex) {
			ex.printLog();
			throw new LookUpException(ex);
		}
	}

	/**
	 * 功能：初始化数据。
	 * @throws LookUpException
	 */
	private void initData() throws LookUpException {
		try {
			LookUpValidator.validateEmptyLookProp(lookUpModel);
			StructureParser strParser = new StructureParser();
			lookProp = lookUpModel.getLookProp();
			sqlModel = lookUpModel.getSQLModel();
//            SQLObject sqlObject = strParser.getSQLObject(sqlModel, conn);

            SQLModel tempModel = getTopModel(sqlModel);
            SQLObject sqlObject = strParser.getSQLObject(tempModel, conn);

            List fields = sqlObject.getFieldNames();
			String[] fieldNames = new String[fields.size()];
			fields.toArray(fieldNames);
			lookProp.setFieldNames(fieldNames);
			LookUpValidator.validateLookProp(lookUpModel);
			String[] retFields = lookProp.getRetFields();
			String[] disFieldNames = lookProp.getDisFieldNames();
			String[] disFieldLabels = lookProp.getDisFieldLabels();
			String[] viewPercent = lookProp.getViewPercent();
			String[] qryFieldNames = lookProp.getQryFieldNames();
			String[] qryFieldLabels = lookProp.getQryFieldLabels();
			String[] primaryKeys = lookProp.getPrimaryKeys();

			int dispFieldCount = 0;
			if(retFields == null || retFields.length == 0){
				lookProp.setRetFields(fieldNames);
			} else {
				lookProp.setRetFields(getUpperCaseFields(retFields));
			}
			if(disFieldNames == null || disFieldNames.length == 0){
				disFieldNames = new String[fieldNames.length];
				System.arraycopy(fieldNames, 0, disFieldNames, 0, disFieldNames.length);
				lookProp.setDisFieldNames(disFieldNames);
			} else {
				lookProp.setDisFieldNames(getUpperCaseFields(disFieldNames));
			}

			if(disFieldLabels == null || disFieldLabels.length == 0){
				disFieldLabels = new String[fieldNames.length];
				System.arraycopy(fieldNames, 0, disFieldLabels, 0, disFieldLabels.length);
				lookProp.setDisFieldLabels(disFieldLabels);
			}
			dispFieldCount = disFieldNames.length;
			if(viewPercent == null || viewPercent.length == 0){
				viewPercent = new String[dispFieldCount];
				String columnWidth = String.valueOf(100 / dispFieldCount) + "%";
				for(int i = 0; i < dispFieldCount; i++){
					viewPercent[i] = columnWidth;
				}
				lookProp.setViewPercent(viewPercent);
			}
			if(qryFieldNames == null || qryFieldNames.length == 0){
				qryFieldNames = new String[disFieldNames.length];
				qryFieldLabels = new String[disFieldNames.length];
				System.arraycopy(disFieldNames, 0, qryFieldNames, 0, qryFieldNames.length);
				System.arraycopy(disFieldLabels, 0, qryFieldLabels, 0, disFieldLabels.length);
				lookProp.setQryFieldNames(qryFieldNames);
				lookProp.setQryFieldLabels(qryFieldLabels);
			} else {
				lookProp.setQryFieldNames(getUpperCaseFields(qryFieldNames));
			}
			if(primaryKeys == null || primaryKeys.length == 0){
				primaryKeys = new String[fieldNames.length];
				System.arraycopy(fieldNames, 0, primaryKeys, 0, primaryKeys.length);
				lookProp.setPrimaryKeys(getUpperCaseFields(primaryKeys));
			} else {
				lookProp.setPrimaryKeys(getUpperCaseFields(primaryKeys));
			}
		} catch (SQLException ex) {
			Logger.logError(ex);
			throw new LookUpException(ex);
		}
	}

	/**
	 * 功能：将字段名转换为大写后返回
	 * @param fieldNames List
	 * @return List
	 */
	private static String[] getUpperCaseFields(String[] fieldNames){
		String fieldName = "";
		for(int i = 0; i < fieldNames.length; i++){
			fieldName = fieldNames[i];
			fieldNames[i] = fieldName.toUpperCase();
		}
		return fieldNames;
	}
	/**
	 * 功能：产生翻页选择支持的WebCheckProp对象
	 * @return CheckBoxProp
	 * @throws HandlerException
	 */
	private WebCheckProp prodWebCheckProp() throws HandlerException {
		WebCheckProp checkProp = null;
		try {
			if (lookProp.isMultipleChose()) {
				checkProp = new CheckBoxProp("subCheck");
				( (CheckBoxProp) checkProp).setMemorySpanQuery(lookProp.isMemorySpan());
			} else {
				checkProp = new CheckRadioProp("checkRadio");
				EventHandler handler = new EventHandler();
				handler.setFunName("do_TransData");
				handler.setEventName("onClick");
				EventHandlers handlers = new EventHandlers();
				handlers.addHandler(handler);
				checkProp.setHandlers(handlers);
			}
			String fieldName = "";
			String[] primaryKeys = lookProp.getPrimaryKeys();
			for (int i = 0; i < primaryKeys.length; i++) {
				fieldName = primaryKeys[i];
				checkProp.addDbField(fieldName);
			}
			String[] retFields = lookProp.getRetFields();
			for (int i = 0; i < retFields.length; i++) {
				fieldName = retFields[i];
				checkProp.addTransField(fieldName);
			}
		} catch (StrException ex) {
			ex.printLog();
			throw new HandlerException(ex);
		}
		return checkProp;
	}

    private SQLModel getTopModel(SQLModel model) {
        SQLModel topModule = new SQLModel();
        String sqlStr = model.getSqlString().replaceFirst("\\b(?i)select\\b", "SELECT").replaceFirst("\\b(?i)distinct\\b", "DISTINCT");
        String topSql;
        int index = sqlStr.indexOf("SELECT");
        int index2 = sqlStr.indexOf("DISTINCT");
        if(index >= 0) {
            if(index > 0)
                topSql = sqlStr.substring(0, index - 1);
            else
                topSql = "";
            if(index2 > 0) {
                if(index2 > index && (index2 - index) < 12) {
                    topSql += "SELECT DISTINCT TOP 10 " + sqlStr.substring(index2+8);
                } else {
                    topSql += "SELECT TOP 10 " + sqlStr.substring(index + 6);
                }
            } else {
                topSql += "SELECT TOP 10 " + sqlStr.substring(index + 6);
            }
        } else {
            return model;
        }
//        topSql = topSql.replaceAll("DBO.", "dbo.");
        topModule.setSqlStr(topSql);
        return topModule;
    }
}

