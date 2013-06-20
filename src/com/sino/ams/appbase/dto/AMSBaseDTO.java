package com.sino.ams.appbase.dto;

import com.sino.base.dto.CalendarUtililyDTO;
import com.sino.base.exception.ReflectException;
import com.sino.base.log.Logger;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;

/**
 * Created by IntelliJ IDEA.
 * User: mshtang
 * Date: 2011-7-6
 * Time: 16:05:21
 * To change this template use File | Settings | File Templates.
 */


public class AMSBaseDTO extends CalendarUtililyDTO {
    private String primaryKeyName = "";

    private int createdBy = 0;
    private int lastUpdateBy = 0;
    
    private String searchType = ""; //ADVANCE 高级查询

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }

    protected void setProperty(String propertyName, String propertyValue) {
        try {
            if (ReflectionUtil.hasProperty(getClass(), propertyName)) {
                ReflectionUtil.setProperty(this, propertyName, propertyValue);
            }
        } catch (ReflectException ex) {
            ex.printLog();
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
    }

    public String getProperty(String propertyName) {
        Object propertyValue = "";
        try {
            if (ReflectionUtil.hasProperty(getClass(), propertyName)) {
                propertyValue = ReflectionUtil.getProperty(this, propertyName);
                propertyValue = StrUtil.nullToString(propertyValue);
            }
        } catch (ReflectException ex) {
            ex.printLog();
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return propertyValue.toString();
    }



    public void setPrimaryKey(String primaryKeyValue) {
        setProperty(primaryKeyName, primaryKeyValue);
    }

    public void clearPrimaryKey() {
        setPrimaryKey("");
    }

    public String getPrimaryKey() {
        return getProperty(primaryKeyName);
    }

    /**
     * 功能：主键值是否为空
     *
     * @return
     */
    public boolean isPrimaryKeyEmpty() {
        return isEmpty(primaryKeyName);
    }

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
}