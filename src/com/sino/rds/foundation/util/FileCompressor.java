package com.sino.rds.foundation.util;


import com.sino.base.SinoBaseObject;
import com.sino.base.constant.CompressMethodConstant;
import com.sino.base.constant.WorldConstant;
import com.sino.base.exception.CompressException;
import com.sino.base.exception.FileException;
import com.sino.base.file.FileProcessor;
import com.sino.base.log.Logger;
import com.sino.base.util.PlatUtil;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>@todo：在此加入本组件具体的描述</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public class FileCompressor extends SinoBaseObject {
	private CompressConfig config = null;
	private List<File> files = null;
	private String method = "";
	private String targetPath = "";
	private String fileName = "";
	private String encode = "";
	private GZIPOutputStream gZipOut = null;
	private ZipOutputStream zipOut = null;

	/**
	 * 功能：设置压缩配置。
	 * @param config CompressConfig
	 * @throws CompressException
	 */
	public void setConfig(CompressConfig config) throws CompressException {
		clearConfigData();
		this.config = config;
		files = config.getSrcFiles();
		method = config.getCompressMethod();
		targetPath = config.getTargetPath();
		fileName = config.getFileName();
		int index = fileName.lastIndexOf(".");
		if(index == -1){
			fileName += ".zip";
		} else {
			fileName = fileName.substring(0, index + 1) + "zip";
		}
		encode = config.getEncode();
		if (!targetPath.endsWith(WorldConstant.FILE_SEPARATOR)) {
			targetPath += WorldConstant.FILE_SEPARATOR;
		}
		targetPath += fileName;
	}

	/**
	 * 功能：压缩文件
	 * @throws CompressException
	 */
	public void compress() throws CompressException {
		try {
			startCompress();
			compressFile();
			endCompress();
			if (config.isDeleteSrc()) {
				deleteSrcFiles();
			}
		} catch (FileException ex) {
			ex.printLog();
			throw new CompressException(ex);
		}
	}

	private void startCompress() throws CompressException {
		try {
			OutputStream fileOut = new FileOutputStream(targetPath);
			if (method.equals(CompressMethodConstant.GZIP_METHOD)) {
				gZipOut = new GZIPOutputStream(fileOut);
			} else if (method.endsWith(CompressMethodConstant.ZIP_METHOD)) {
				CheckedOutputStream chOut = new CheckedOutputStream(fileOut, new CRC32());
				zipOut = new ZipOutputStream(chOut);
				zipOut.setComment(CompressMethodConstant.ZIP_COMMENT);
			}
		} catch (FileNotFoundException ex) {
			Logger.logError(ex);
			throw new CompressException(ex);
		} catch (IOException ex) {
			Logger.logError(ex);
			throw new CompressException(ex);
		}
	}

	/**
	 * 功能：递归调用自身，压缩文件及文件夹。
	 * @throws CompressException
	 */
	private void compressFile() throws CompressException {
		int fileCount = files.size();
		File file = null;
		for (int i = 0; i < fileCount; i++) {
			file = files.get(i);
			if (file.isFile()) {
				fileToZip(file);
			} else {
				dirToZip(file);
			}
		}
	}

	/**
	 * 功能：将文件添加到压缩档。
	 * @param file File 文件
	 * @throws CompressException
	 */
	private void fileToZip(File file) throws CompressException {
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis, 1024);
			byte b[] = new byte[1024];
			int len = 0;
			if (method.equals(CompressMethodConstant.GZIP_METHOD)) {
				while ((len = bis.read(b)) != -1) {
					gZipOut.write(b, 0, len);
				}
			} else if (method.equals(CompressMethodConstant.ZIP_METHOD)) {
				String enteryName = file.getAbsolutePath();
				if(PlatUtil.isWindowsPlat()){
					enteryName = enteryName.substring(2);
				}
				ZipEntry nextEntry = new ZipEntry(enteryName);
				zipOut.putNextEntry(nextEntry);
				while ((len = bis.read(b)) != -1) {
					zipOut.write(b, 0, len);
				}
			}
			bis.close();
			fis.close();
		} catch (IOException ex) {
			Logger.logError(ex);
			throw new CompressException(ex);
		}
	}

	/**
	 * 功能：压缩文件夹
	 * @param file File
	 * @throws CompressException
	 */
	private void dirToZip(File file) throws CompressException {
		File listfile[] = file.listFiles();
		for (File tmpFile:listfile) {
			if (tmpFile.isFile()) {
				fileToZip(tmpFile);
			} else if (tmpFile.isDirectory()) {
				dirToZip(tmpFile);
			}
		}
	}

	private void endCompress() throws CompressException {
		try {
			if (method.equals(CompressMethodConstant.ZIP_METHOD)) {
				zipOut.flush();
				zipOut.close();
			} else if (method.equals(CompressMethodConstant.GZIP_METHOD)) {
				gZipOut.flush();
				gZipOut.close();
			}
		} catch (IOException ex) {
			Logger.logError(ex);
			throw new CompressException(ex);
		}
	}

	/**
	 * 功能：删除源文件
	 * @throws FileException
	 */
	private void deleteSrcFiles() throws FileException {
		int fileCount = files.size();
		File file = null;
		FileProcessor fileProcessor = new FileProcessor();
		for (int i = 0; i < fileCount; i++) {
			file = files.get(i);
			fileProcessor.delete(file);
		}
	}

	/**
	 * 功能：清除压缩配置信息
	 */
	private void clearConfigData() {
		this.files = null;
		this.config = null;
		this.method = "";
		this.targetPath = "";
		this.fileName = "";
	}

	/**
	 * 功能：获取压缩后的文件。
	 * @return File
	 */
	public File getCompressedFile(){
		return new File(targetPath);
	}
}

