package com.sino.base;

import java.util.ArrayList;

import com.sino.base.log.Logger;

/**
 * <p>Title: </p>
 * <p>Description: 主要是用于在获取元素时,如果元素是空(index超过当前list的size),则新增加一个元素,并将此元素初始化为一个类的实例</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2009</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2009-2-10
 */
public class ExtendedArrayList extends ArrayList {
    private Class itemClass;

    public ExtendedArrayList(Class itemClass) {
        this.itemClass = itemClass;
    }

    public Object get(int index) {
        while (index >= size()) {
            try {
                add(itemClass.newInstance());
            } catch (InstantiationException e) {
                Logger.logError(e);
            } catch (IllegalAccessException e) {
                Logger.logError(e);
            }
        }
        return super.get(index);
    }
}
