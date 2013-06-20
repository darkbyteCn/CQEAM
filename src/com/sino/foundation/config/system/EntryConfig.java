package com.sino.foundation.config.system;


import com.sino.base.constant.WorldConstant;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>@todo：暂时位于该包下，经过实际项目的验证之后，将其加入基础库，并取代目前不灵活的配置管理</p>
 * <p>Copyright: 北京思诺博版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public class EntryConfig{
	private String name = "";
	private String value = "";
	private String loaderClassName = "";
	private boolean loadConfig = false;
	private String entryPath = "";//配置文件所在路径，不含文件本身
	private String absolutePath = "";
	private boolean supportReload = false;//是否支持热加载
	private String description = "";//配置描述
	private String postProcessor = "";//配置后处理器
	private String fileExtention = "";//文件名后缀
    private long delayTime = 0L;//如果本配置有后置处理器，此选项有效，表示后置处理器延迟启动的毫秒数

	public EntryConfig() {
		super();
	}

	public String getValue() {
		return value;
	}

	public String getLoaderClassName() {
		return loaderClassName;
	}

	public String getName() {
		return name;
	}

	public boolean isLoadConfig() {
		return loadConfig;
	}

	public String getEntryPath() {
		return entryPath;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setLoaderClassName(String loaderClassName) {
		this.loaderClassName = loaderClassName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLoadConfig(boolean loadConfig) {
		this.loadConfig = loadConfig;
	}

	public void setEntryPath(String entryPath) {
		this.entryPath = entryPath;
        String localPath = "";
		if(!entryPath.endsWith(WorldConstant.FILE_SEPARATOR)){
			localPath = entryPath + WorldConstant.FILE_SEPARATOR + value;
		} else {
			localPath = entryPath + value;
		}
        setAbsolutePath(localPath);
	}

	public void setSupportReload(boolean supportReload) {
		this.supportReload = supportReload;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAbsolutePath(){
		return this.absolutePath;
	}

	public boolean isSupportReload() {
		return supportReload;
	}

	public String getDescription() {
		return description;
	}

    public String getPostProcessor() {
        return postProcessor;
    }

    public void setPostProcessor(String postProcessor) {
        this.postProcessor = postProcessor;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public boolean equals(EntryConfig anotherEntry){
        boolean equals = false;
        if(anotherEntry != null){
            return name.equals(anotherEntry.getName());
        }
        return equals;
    }

    public String getFileExtention() {
        return fileExtention;
    }

    public void setFileExtention(String fileExtention) {
        if(fileExtention != null){
            this.fileExtention = fileExtention;
        }
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }
}
