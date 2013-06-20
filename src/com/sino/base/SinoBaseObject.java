package com.sino.base;

import java.io.IOException;
import java.io.Writer;

import com.sino.base.exception.ReflectException;
import com.sino.base.log.Logger;
import com.sino.base.util.*;
/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public class SinoBaseObject extends Object {

    public SinoBaseObject() {
        super();
    }

    /**
     *
     * @return String
     */
    public String toString() {
        return SystemUtil.toString(this);
    }


    public Object clone(){
        SinoBaseObject tarObj = null;
        String className = "";
        try {
            className = this.getClass().getName();
            tarObj = (SinoBaseObject) Class.forName(className).newInstance();
            ReflectionUtil.copyData(this, tarObj);
        } catch (ClassNotFoundException ex) {
            Logger.logError(ex);
        } catch (IllegalAccessException ex) {
            Logger.logError(ex);
        } catch (InstantiationException ex) {
            Logger.logError(ex);
        } catch (ReflectException ex) {
            ex.printLog();
        }
        return tarObj;
    }


	/**
	 * 功能：将DTO对象打印到控制台
	 */
	public void println(){
		System.out.println(this);
	}


	/**
	 * 功能：将DTO对象打印到指定的输出流
	 * @param writer Writer
	 */
	public void print(Writer writer){
		try {
			writer.write(toString());
		} catch (IOException ex) {
			Logger.logError(ex);
		}
	}

	/**
	 * 功能：将某个属性打印到控制台上
	 * @param fieldName String
	 */
	public void println(String fieldName){
		if(StrUtil.isEmpty(fieldName)){
			System.out.println("you printed fieldName is empty, and there's no value binding it");
		} else {
			try{
				boolean hasProp = ReflectionUtil.hasProperty(this.getClass(), fieldName);
				if (hasProp) {
					Object value = ReflectionUtil.getProperty(this, fieldName);
					System.out.println(this.getClass().getName() + ": " + fieldName + " = " + value);
				} else {
					System.out.println(this.getClass().getName() + " does not contain a field named " + fieldName); ;
				}
			}catch(ReflectException ex){
				ex.printLog();
			}
		}
	}

	/**
	 * 功能：将本队象记录到日志文件
	 */
	public void log(){
		Logger.logInfo(this);
	}
}
