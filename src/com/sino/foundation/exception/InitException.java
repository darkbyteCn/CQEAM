package com.sino.foundation.exception;


/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>@todo：在此加入本组件具体的描述</p>
 * <p>Copyright: 北京思诺博版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public class InitException extends SinoRuntimeException {
	public InitException() {
		super();
	}

	public InitException(String msg) {
		super(msg);
	}


	public InitException(Throwable cause) {
		super(cause);
	}
}
