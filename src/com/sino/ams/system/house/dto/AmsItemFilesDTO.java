package com.sino.ams.system.house.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: 设备相关附件(EAM) AmsItemFiles</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsItemFilesDTO extends CheckBoxDTO{

//	private String barcodeNo = "";
    private String barcode ="";

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    private String fileDesc = "";
	private String filePath = "";
	private String systemId = "";
	private SimpleCalendar creationDate = null;
	private String createdBy = "";
	private SimpleCalendar lastUpdateDate = null;
	private String lastUpdateBy = "";


	/**
	 * 功能：设置设备相关附件(EAM)属性 标签号
	 * @param barcodeNo String
	 */
//	public void setBarcodeNo(String barcodeNo){
//		this.barcodeNo = barcodeNo;
//	}

	/**
	 * 功能：设置设备相关附件(EAM)属性 附件描述
	 * @param fileDesc String
	 */
	public void setFileDesc(String fileDesc){
		this.fileDesc = fileDesc;
	}

	/**
	 * 功能：设置设备相关附件(EAM)属性 存储路径
	 * @param filePath String
	 */
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}

	/**
	 * 功能：设置设备相关附件(EAM)属性 序列号
	 * @param systemId String
	 */
	public void setSystemId(String systemId){
		this.systemId = systemId;
	}

	/**
	 * 功能：设置设备相关附件(EAM)属性 创建日期
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		if(!StrUtil.isEmpty(creationDate)){
			this.creationDate = new SimpleCalendar(creationDate);
		}
	}

	/**
	 * 功能：设置设备相关附件(EAM)属性 创建人
	 * @param createdBy String
	 */
	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置设备相关附件(EAM)属性 上次修改日期
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		if(!StrUtil.isEmpty(lastUpdateDate)){
			this.lastUpdateDate = new SimpleCalendar(lastUpdateDate);
		}
	}

	/**
	 * 功能：设置设备相关附件(EAM)属性 上次修改人
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(String lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}


	/**
	 * 功能：获取设备相关附件(EAM)属性 标签号
	 * @return String
	 */
//	public String getBarcodeNo(){
//		return this.barcodeNo;
//	}

	/**
	 * 功能：获取设备相关附件(EAM)属性 附件描述
	 * @return String
	 */
	public String getFileDesc(){
		return this.fileDesc;
	}

	/**
	 * 功能：获取设备相关附件(EAM)属性 存储路径
	 * @return String
	 */
	public String getFilePath(){
		return this.filePath;
	}

	/**
	 * 功能：获取设备相关附件(EAM)属性 序列号
	 * @return String
	 */
	public String getSystemId(){
		return this.systemId;
	}

	/**
	 * 功能：获取设备相关附件(EAM)属性 创建日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getCreationDate(){
		return this.creationDate;
	}

	/**
	 * 功能：获取设备相关附件(EAM)属性 创建人
	 * @return String
	 */
	public String getCreatedBy(){
		return this.createdBy;
	}

	/**
	 * 功能：获取设备相关附件(EAM)属性 上次修改日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取设备相关附件(EAM)属性 上次修改人
	 * @return String
	 */
	public String getLastUpdateBy(){
		return this.lastUpdateBy;
	}

}