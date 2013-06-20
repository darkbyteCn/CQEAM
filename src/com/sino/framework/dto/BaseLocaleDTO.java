package com.sino.framework.dto;

import java.io.Serializable;
import java.util.Locale;

import com.sino.base.calen.CalPatternBase;
import com.sino.base.dto.DTO;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public class BaseLocaleDTO extends CalPatternBase implements DTO, Serializable {
    private Locale locale = null;

    public BaseLocaleDTO() {
        super();
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
