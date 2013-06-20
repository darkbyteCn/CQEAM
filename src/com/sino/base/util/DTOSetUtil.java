package com.sino.base.util;

import java.util.List;

import com.sino.base.data.RowSet;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ReflectException;

/**
 * <p>Title: SinoCMS</p>
 * <p>Description: 河南移动合同管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public abstract class DTOSetUtil {

	/**
	 * 判断DTOSet中指定字段值是否相等
	 * @param dtos DTOSet
	 * @param fieldName String
	 * @return boolean true表示相等，false表示不等
	 */
	public static boolean equals(DTOSet dtos, String fieldName) {
		boolean equals = false;
		try {
			if (dtos != null && !dtos.isEmpty() && dtos.contains(fieldName)) {
				equals = true;
				int dtoCount = dtos.getSize();
				DTO dto = null;
				Object firstValue = null;
				boolean valueSeted = false;
				Object loopValue = null;
				for (int i = 0; i < dtoCount; i++) {
					dto = (DTO) dtos.getDTO(i);
					if (!valueSeted) {
						firstValue = ReflectionUtil.getProperty(dto, fieldName);
						valueSeted = true;
						continue;
					}
					loopValue = ReflectionUtil.getProperty(dto, fieldName);
					if (firstValue == null) {
						equals = (loopValue == null);
					} else {
						equals = (firstValue.equals(loopValue));
					}
					if (!equals) {
						break;
					}
				}
			}
		} catch (ReflectException ex) {
			ex.printLog();
		}
		return equals;
	}

	/**
	 * 判断DTOSet中指定字段值是否相等
	 * @param dtos DTOSet
	 * @param fieldNameArr String[]
	 * @return boolean true表示相等，false表示不等
	 */
	public static boolean equals(DTOSet dtos, String[] fieldNameArr) {
		boolean equals = false;
		if (fieldNameArr != null) {
			equals = true;
			int fieldCount = fieldNameArr.length;
			String fieldName = "";
			for (int i = 0; i < fieldCount; i++) {
				fieldName = fieldNameArr[i];
				equals = equals && equals(dtos, fieldName);
				if (!equals) {
					break;
				}
			}
		}
		return equals;
	}

	public static List getListFromRowSet(RowSet rows, String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
