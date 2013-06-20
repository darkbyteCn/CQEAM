package com.sino.ams.expand.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * Created by IntelliJ IDEA. User: srf Date: 2008-3-12 Time: 16:42:34 To change
 * this template use File | Settings | File Templates.
 * USER:’≈–«
 */
public class UploadFileDTO extends CommonRecordDTO {

	private String barcode = "";

	private String fileDesc = "";

	private String filePath = "";

	private String systemId = "";

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getFileDesc() {
		return fileDesc;
	}

	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
}
