package com.sino.ams.workorder.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;

//解析零购导入
public class ReadZeroTurnExcel {
	private HSSFWorkbook book = null;

	private int startRowNum = 0;
	private int numberOfColumn = 0;

	public void setFileName(String fileName) throws IOException {
		FileInputStream fileIn = new FileInputStream(fileName);
		POIFSFileSystem fs = new POIFSFileSystem(fileIn);
		book = new HSSFWorkbook(fs);
	}

	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}

	public void setNumberOfColumn(int numberOfColumn) {
		this.numberOfColumn = numberOfColumn;
	}

	// 读取指定sheet
	public DTOSet readXls(int indexOfSheet) throws DTOException {
		DTOSet orderDTOSet = new DTOSet();
		if (book != null) {
			int numberOfSheet = book.getNumberOfSheets();
			if (indexOfSheet < numberOfSheet) {
				HSSFSheet hssfSheet = book.getSheetAt(indexOfSheet);
				HSSFRow hssfRow = null;
				HSSFCell hssfCell = null;
				int row = hssfSheet.getLastRowNum();
				for (int i = startRowNum; i <= row; i++) {
					hssfRow = hssfSheet.getRow(i);
					if (hssfRow != null) {
						numberOfColumn = numberOfColumn == 0 ? hssfRow
								.getLastCellNum() : numberOfColumn;
						ZeroTurnLineDTO lineDTO = new ZeroTurnLineDTO();
						String excelLineId = String.valueOf(i + 1);
						lineDTO.setExcelLineId(excelLineId);
						for (int k = 0; k <= numberOfColumn; k++) {
							hssfCell = hssfRow.getCell((short) k);
							String strValue = getStringValue(hssfCell);
							lineDTO = fillLineDTO(k, strValue, lineDTO);
						}
						boolean flag = true;
						if (isExist(lineDTO)) {
							orderDTOSet.addDTO(lineDTO);
						}
					}
				}
			}

		}
		return orderDTOSet;
	}

	private ZeroTurnLineDTO fillLineDTO(int indexk, String strValue,
			ZeroTurnLineDTO lineDTO) {
		if (strValue != null) {
			strValue = strValue.trim();
		}
		switch (indexk) {
		case 0:// 发货单编号
			strValue = noIntType(strValue);
			lineDTO.setMisProcureCode(strValue);
			break;
		case 1:// 采购单号（必输）
			lineDTO.setProcureCode(strValue);
			break;
		case 2:
			// 公司代码(必输)
			strValue = noIntType(strValue);
			lineDTO.setCompanyCode(strValue);
			break;
		case 3:// 资产名称
			lineDTO.setAssetsDescription(strValue);
			break;
		case 4:
			// 规格型号（必输）
			lineDTO.setItemSpec(strValue);
			break;
		case 5:
			// 厂商（必输）
			lineDTO.setManufacturerName(strValue);
			break;
		case 6:// 资产目录（必输）
			lineDTO.setContentCode(strValue);
			break;
		case 7:// 数量（必输）
			strValue = noIntType(strValue);
			lineDTO.setItemQty(strValue);
			break;
		case 8:// 使用年限（必输）
			strValue = noIntType(strValue);
			lineDTO.setYears(strValue);
			break;
		case 9:
			// 金额（必输）
			//strValue = noIntType(strValue);
			lineDTO.setPrice(strValue);
			break;
		case 10:
			// 启用日期（可选）
			lineDTO.setStartDate(strValue);
			break;
		case 11:
			// 业务平台（可选）
			lineDTO.setOpeId(strValue);
			break;
		case 12:
			// 网各层次（可选）
			lineDTO.setNleId(strValue);
			break;
		case 13:
			// 是否共建设备（可选）
			lineDTO.setIsBulid(strValue);
			break;
		case 14:
			// 成本中心（必输）
			strValue = noIntType(strValue);
			lineDTO.setCostCenterCode(strValue);
			break;
		case 15:
			// 地点名称（可选）
			lineDTO.setWorkorderObjectName(strValue);
			break;
		case 16:
			// 地点编号（必输）
			lineDTO.setObjectNo(strValue);
			break;
		case 17:
			// 责任人编号（必输）
			strValue = noIntType(strValue);
			lineDTO.setResponsibilityUser(strValue);
			break;
		case 18:
			// 责任人姓名（必输）
			lineDTO.setResponsibilityName(strValue);
			break;
		case 19:// 请购类别（必输）
			lineDTO.setProcureType(strValue);
			break;
		case 20:// 收货人（必输）
			lineDTO.setReceiver(strValue);
			break;
		case 21:// 收货人联系方式
			strValue = noIntType(strValue);
			lineDTO.setReceiverContact(strValue);
			break;
		case 22:// 业务分类
			lineDTO.setAssetKey1(strValue);
			break;
		case 23:// 总公司分类
			lineDTO.setAssetKey2(strValue);
			break;
		case 24:// 总公司备用
			lineDTO.setAssetKey3(strValue);
			break;
		case 25:// 资产类型
			lineDTO.setAssetType(strValue);
			break;
		case 26:// 是否折旧
			lineDTO.setIsDeprn(strValue);
			break;
		case 27:// 是否摊销调整
			lineDTO.setIsAdjust(strValue);
			break;
		case 28:// 建设状态
			lineDTO.setAttribute4(strValue);
			break;
		case 29:// 资产来源
			strValue = noIntType(strValue);
			lineDTO.setAttribute5(strValue);
			break;
		case 30:// 预计到货日期
			lineDTO.setExpectedDate(strValue);
			break;

		default:
		}
		return lineDTO;
	}

	private String getStringValue(HSSFCell cell) {
		String strValue = "";
		if (cell != null) {
			if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
				strValue = "";
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				strValue = String.valueOf(cell.getNumericCellValue());
			} else {
				strValue = cell.getRichStringCellValue().toString();
			}
		}
		return strValue;
	}

	// 去掉导入EXCEL中的空白行
	public boolean isExist(ZeroTurnLineDTO lineDTO) {
		boolean flag = true;
		if (lineDTO.getAssetsDescription().equals("")
				&& lineDTO.getBarcode().equals("")
				&& lineDTO.getCexId().equals("")
				&& lineDTO.getCompanyCode().equals("")
				&& lineDTO.getComputeDays().equals("")
				&& lineDTO.getContentName().equals("")
				&& lineDTO.getCostCenterCode().equals("")
				&& lineDTO.getIsBulid().equals("")
				&& lineDTO.getIsShare().equals("")
				&& lineDTO.getItemQty().equals("")
				&& lineDTO.getItemSpec().equals("")
				&& lineDTO.getLneId().equals("")
				&& lineDTO.getManufacturerName().equals("")
				&& lineDTO.getNleId().equals("")
				&& lineDTO.getObjectNo().equals("")
				&& lineDTO.getOpeId().equals("")
				&& lineDTO.getPrice().equals("")
				&& lineDTO.getProcureCode().equals("")
				&& lineDTO.getRemark().equals("")
				&& lineDTO.getResponsibilityDept().equals("")
				&& lineDTO.getResponsibilityUser().equals("")
				&& lineDTO.getSpecialityDept().equals("")
				&& lineDTO.getStartDate().equals("")
				&& lineDTO.getUnitOfMeasure().equals("")
				&& lineDTO.getWorkorderObjectName().equals("")
				&& lineDTO.getYears().equals("")) {
			flag = false;
		}
		return flag;
	}

	// 去掉数据性字符串中的.0样式
	public String noIntType(String strValue) {
		String newStr = strValue;
		if (!strValue.equals("")) {
			if (strValue.length() > 0) {
				int index = strValue.indexOf(".0");
				if (index >= 0) {
					newStr = strValue.substring(0, index);
				}
			}
		}
		return newStr;
	}

//	 public static void main(String[] args) {
//	 String value="3910-70000029,3910-70000030,3910-70000031,";
//	 String os="";
//	 String [] arg=value.split(",");
//	 for (int i = 0; i < arg.length; i++) {
//		 String a=arg[i];
//		if (i!=arg.length-1) {
//			os+="'"+a+"',";
//		}else {
//			os+="'"+a+"'";
//		}
//	}
//	 System.out.println(os);
//	 }
}
