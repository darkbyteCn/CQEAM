package com.sino.rds.foundation.db.structure;

import com.sino.base.SinoBaseObject;
import com.sino.base.db.sql.model.SQLModel;

import java.util.ArrayList;
import java.util.List;

public class SQLObject extends SinoBaseObject {
	private SQLModel sqlModel = null;
	protected List<Field> fields = null;

	/**
	 * 功能：构造函数，包内公开，StructureParser使用。
	 */
	public SQLObject() {
		fields = new ArrayList<Field>();
	}

	/**
	 * 功能：设置查询SQL。包内StructureParser使用。
	 * @param sqlModel SQL查询。
	 */
	public void setSQL(SQLModel sqlModel) {
		this.sqlModel = sqlModel;
	}

	/**
	 * 功能：设置字段数组。
	 * @param fields List<Field>
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public void addField(Field field) {
		if(!fields.contains(field)){
			fields.add(field);
		}
	}

	/**
	 * 功能：获得SQL名
	 * @return SQLModel
	 */
	public SQLModel getSqlModel() {
		return sqlModel;
	}

	/**
	 * 功能：返回所有字段组成的列表
	 * @return List<Field>
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * 功能：判断该SQL是否具有指定类型的字段。
	 * @param fieldType String
	 * @return boolean
	 */
	public boolean hasGivenTypeField(String fieldType) {
		boolean hasField = false;
		for (int i = 0; i < fields.size(); i++) {
			if (fields.get(i).getType().equals(fieldType)) {
				hasField = true;
				break;
			}
		}
		return hasField;
	}

	/**
	 * 功能：获取所有字段名称列表。
	 * @return List
	 */
	public List<String> getFieldNames() {
		int size = fields.size();
		List<String>  fieldNames = new ArrayList<String> (size);
		for (int i = 0; i < size; i++) {
			fieldNames.add(fields.get(i).getName());
		}
		return fieldNames;
	}

	/**
	 * 功能：获取所有大字段名称构成的列表。
	 * @return List
	 */
	public List getLobFieldNames() {
		int size = fields.size();
		List fieldNames = new ArrayList();
		Field field = null;
		for (int i = 0; i < size; i++) {
			field = fields.get(i);
			if (field.isLobField()) {
				fieldNames.add(field.getName());
			}
		}
		return fieldNames;
	}

	/**
	 * 功能：判断是否含有该字段
	 * @param fieldName String
	 * @return boolean
	 */
	public boolean hasField(String fieldName) {
		int fieldCount = fields.size();
		for (int i = 0; i < fieldCount; i++) {
			String field = fields.get(i).getName();
			if (field.equalsIgnoreCase(fieldName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 通过字段名获取字段对象
	 * @param fieldName String
	 * @return Field
	 */
	public Field getField(String fieldName) {
		int fieldCount = fields.size();
		for (int i = 0; i < fieldCount; i++) {
			Field field = fields.get(i);
			if (field.getName().equalsIgnoreCase(fieldName)) {
				return field;
			}
		}
		return null;
	}
}
