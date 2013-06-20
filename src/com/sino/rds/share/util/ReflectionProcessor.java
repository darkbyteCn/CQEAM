package com.sino.rds.share.util;

import com.sino.base.exception.ReflectException;
import com.sino.base.log.Logger;
import com.sino.base.util.ReflectionUtil;

import java.lang.reflect.Field;

public abstract class ReflectionProcessor extends ReflectionUtil {

    public static Object getProperty(Class cls, String property) throws ReflectException {
        Object obj = null;
        try {
            Field[] fields = cls.getFields();
            if (fields != null) {
                for (Field field : fields) {
                    if (field.getName().equals(property)) {
                        field.setAccessible(true);
                        obj = field.get(null);
                        break;
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            Logger.logError(ex);
            throw new ReflectException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReflectException(ex.getMessage());
        }
        return obj;
    }
}
