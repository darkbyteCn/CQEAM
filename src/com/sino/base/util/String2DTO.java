package com.sino.base.util;

import com.sino.base.exception.ReflectException;
import com.sino.base.log.Logger;

/**
 * <p>Description: </p>
 * @author 何睿
 * @version 1.0
 *          Time: 9:05:05
 */
public class String2DTO {


    /**
     * 将DTO生成的字符串转化为一个DTO对象 ,暂时不能复制该DTO的父类中的属性,有待改进 
     * @param dtoStr DTO生成的字符
     * @return DTO
     */
    public static Object getDTO(String dtoStr) {
        String[] tempArr = StrUtil.splitStr(dtoStr, "[");
        String classPath = tempArr[0];
        String[] fieldsArr = StrUtil.splitStr(tempArr[1], ",");
        String fieldName = "";
        String fieldValue = "";
        int fieldCount = fieldsArr.length;
        String[] fieldArr;
        Object obj = null;
        try {
            obj = Class.forName(classPath).newInstance();
            for (int i = 0; i < fieldCount; i++) {
                fieldArr = StrUtil.splitStr(fieldsArr[i], "=");
                fieldName = fieldArr[0].trim();
                fieldValue = fieldArr[1].trim();
                if (i == fieldCount-1) {
                    fieldValue = fieldValue.substring(0, fieldValue.length() - 1);
                }
                ReflectionUtil.setProperty(obj, fieldName, fieldValue);

            }
        } catch (InstantiationException e) {
            Logger.logError(e);
        } catch (IllegalAccessException e) {
            Logger.logError(e);
        } catch (ClassNotFoundException e) {
            Logger.logError(e);
        } catch (ReflectException e) {
            Logger.logError(e);
        }
        return obj;
    }
}
