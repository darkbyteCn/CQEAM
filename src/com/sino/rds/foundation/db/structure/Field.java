package com.sino.rds.foundation.db.structure;

import com.sino.base.SinoBaseObject;
import com.sino.base.constant.db.DataTypeConstant;
import com.sino.base.util.ArrUtil;
import com.sino.base.util.StrUtil;

public class Field extends SinoBaseObject {
	private String name = "";
	private String type = "";
	private boolean nullAble = true;
	private int length = -1;
	private int precision = -1;
	private boolean foreignKey = false;
	private String referField = "";
	private String referTable = "";
	private String comment = "";
	private String catalogName = "";
	private String className = "";

	/**
	 * 功能：返回字段名。
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * 功能：设置字段名。
	 * @param name 字段名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 功能：返回字段类型。
	 * @return 字段类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 功能：设置字段类型。
	 * @param type 字段类型
	 */
	public void setType(String type){
		this.type = type;
	}

	/**
	 * 功能：判断该字段是否允许为空。
	 * @return true表示允许空，false表示不允许。
	 */
	public boolean isNullAble() {
		return nullAble;
	}

	/**
	 * 功能：设置是否允许为空。
	 * @param nullAble boolean
	 */
	public void setNullAble(boolean nullAble) {
		this.nullAble = nullAble;
	}

	/**
	 * 功能：获取字段允许的最大字符数。
	 * @return int
	 */
	public int getLength() {
		return length;
	}

	public boolean isForeignKey() {
		return foreignKey;
	}

	public String getReferField() {
		return referField;
	}

	public String getReferTable() {
		return referTable;
	}

	public String getComment() {
		return comment;
	}

	public int getPrecision() {
		return precision;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public String getClassName() {
		return className;
	}

	/**
	 * 功能：设置字段允许的最大字符数。
	 * @param length int
	 */
	public void setLength(int length) {
		this.length = length;
	}

	public void setForeignKey(boolean foreignKey) {
		this.foreignKey = foreignKey;
	}

	public void setReferField(String referField) {
		this.referField = referField;
	}

	public void setReferTable(String referTable) {
		this.referTable = referTable;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * 判断该字段是否大字段
	 * @return boolean
	 */
	public boolean isLobField() {
		boolean isLobField = false;
		if (!StrUtil.isEmpty(name)) {
			isLobField = ArrUtil.isInArr(DataTypeConstant.LIMIT_LOB_TYPE, type);
		}
		return isLobField;
	}

	/**
	 * 功能：判断字段是否数字字段
	 * @return boolean
	 */
	public boolean isNumberField() {
		return ArrUtil.isInArr(DataTypeConstant.LIMIT_NUMBER_TYPE, type);
	}
}

