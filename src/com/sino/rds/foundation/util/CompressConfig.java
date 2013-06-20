package com.sino.rds.foundation.util;

import com.sino.base.SinoBaseObject;
import com.sino.base.constant.CompressMethodConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CompressConfig extends SinoBaseObject {
	private List<File> srcFiles = null;
	private String targetPath = "";
	private String compressMethod = CompressMethodConstant.ZIP_METHOD;
	private String fileName = "";
	private String encode = "GBK";
	private boolean deleteSrc = false;

	public CompressConfig() {
		srcFiles = new ArrayList<File>();
	}

	/**
	 * 功能：加入待压缩文件。
	 * @param file File 待压缩文件，可以是文件，也可以是包含文件或子文件夹的文件夹。
	 */
	public void addFile(File file) {
		if(file == null){
			return;
		}
		if(!file.exists()){
			return;
		}
		if (!srcFiles.contains(file)) {
			srcFiles.add(file);
		}
	}

	/**
	 * 功能：加入待压缩文件。
	 * @param file String 待压缩文件，可以是文件，也可以是包含文件或子文件夹的文件夹。
	 */
	public void addFile(String file) {
		File srcFile = new File(file);
		addFile(srcFile);
	}

	/**
	 * 功能：设置文件列表
	 * @param files List
	 */
	public void setFiles(List files) {
		if(files == null || files.isEmpty()){
			return;
		}
		int fileCount = files.size();
		srcFiles.clear();
		Object file = null;
		for(int i = 0; i < fileCount; i++){
			file = files.get(i);
			if(file == null){
				continue;
			}
			if(file instanceof String){
				addFile((String)file);
			} else if(file instanceof File){
				addFile((File)file);
			}
		}
	}

	/**
	 * 功能：设定压缩方法。目前仅支持ZIP和GZIP格式。
	 * @param compressMethod String
	 */
	public void setCompressMethod(String compressMethod) {
		this.compressMethod = compressMethod;
	}

	/**
	 * 功能：设置目标路径。
	 * @param targetPath String
	 */
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	/**
	 * 功能：设置压缩后的文件名。
	 * @param fileName String
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public void setDeleteSrc(boolean deleteSrc) {
		this.deleteSrc = deleteSrc;
	}

	/**
	 * 功能：获取压缩方法
	 * @return String
	 */
	public String getCompressMethod() {
		return compressMethod;
	}

	/**
	 * 功能：获取文件名。
	 * @return String
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 功能：获取待压缩文件列表。
	 * @return List
	 */
	public List<File> getSrcFiles() {
		return srcFiles;
	}

	/**
	 * 功能：设置目标路径。
	 * @return String
	 */
	public String getTargetPath() {
		return targetPath;
	}

	public String getEncode() {
		return encode;
	}

	public boolean isDeleteSrc() {
		return deleteSrc;
	}
}
