package com.sino.ams.house.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.PoolException;
import com.sino.ams.house.dao.AmshouseinfoDAO;
import com.sino.ams.house.dto.AmshouseinfoDTO;
import com.sino.framework.servlet.BaseServlet;
import org.apache.poi.hssf.usermodel.*;

/**
 * 房屋土地统计报表导出
 * 
 * @author kouzh
 * 
 */
public class ExportServlet extends BaseServlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sino.base.PubServlet#performTask(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		Connection conn = null;
		String nextPage = "";
		try {
			conn = DBManager.getDBConnection();
			this.export(req, res, conn);
			return;
		} catch (PoolException e) {
			req.setAttribute("ERROR_MSG", "取连接出错！");
			nextPage = "/flow/errorPage.jsp";
			req.getRequestDispatcher(nextPage).forward(req, res);
			e.printLog();
		} finally {
			DBManager.closeDBConnection(conn);
		}
	}

	/**
	 * 
	 * 导出房屋土地信息统计报表
	 * 
	 */
	public void export(HttpServletRequest req, HttpServletResponse res,
			Connection conn) {
		InputStream ins = null;// 输入流对象
		HSSFWorkbook mcBook = null;// 可写工作薄对象
		HSSFSheet mcSheet = null;// //可写工作表对象
		String exportFileName = "houseStat.xls";// 报表名称
		int size = 0;
		try {
			this.beforeExport(req, res, exportFileName);// 设置响应头信息
			ins = new FileInputStream(this.getFile(req, exportFileName));// 读取模版XLS文件以备数据写入
			mcBook = new HSSFWorkbook(ins); // 创建一个工作簿对象

			// 设置报表中数据区的单元格样式
			HSSFFont fontHead = mcBook.createFont();
			HSSFCellStyle cellStyle = mcBook.createCellStyle();
			cellStyle.setBorderBottom((short) 1);
			cellStyle.setBorderLeft((short) 1);
			cellStyle.setBorderRight((short) 1);
			cellStyle.setBorderTop((short) 1);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			fontHead.setFontHeightInPoints((short) 11);// 字号
			cellStyle.setFont(fontHead);

			// 设置报表中汇总区的单元格样式
			HSSFFont fontHead1 = mcBook.createFont();
			HSSFCellStyle cellStyle1 = mcBook.createCellStyle();
			cellStyle1.setBorderBottom((short) 1);
			cellStyle1.setBorderLeft((short) 1);
			cellStyle1.setBorderRight((short) 1);
			cellStyle1.setBorderTop((short) 1);
			cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			fontHead1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体
			fontHead1.setFontHeightInPoints((short) 11);// 字号
			cellStyle1.setFont(fontHead1);
			// 汇总信息部分
			long assetNum = 0;
			long houseCertificateNum = 0;
			double occupyArea = 0.0;
			double houseArea = 0.0;
			double cost = 0.0;
			double deprnReserve = 0.0;
			double netAssetValue = 0.0;
			Map map = new HashMap();
			AmshouseinfoDTO dto1 = new AmshouseinfoDTO();

			/** ***************** 获取并导出基站土地统计信息**************** */
			AmshouseinfoDAO dao = new AmshouseinfoDAO(conn, req);
			DTOSet set = dao.getAmslandInfo(conn);
			if (set != null && !set.isEmpty()) {// 如果基站土地统计信息不为空则写入到EXCEL模版中，以供导出
				size = set.getSize();
				mcSheet = mcBook.getSheetAt(4);// 得到第五个工作表即:基站土地
				// 组织数据并写入
				int rowIndex = 2;
				HSSFRow row = null;
				HSSFCell cell = null;
				AmshouseinfoDTO dto = null;
				for (int i = 0; i < size; i++) {
					dto = (AmshouseinfoDTO) set.getDTO(i);
					dto.setLandnum(dto.getAssetnum());// 基站土地数量
					map.put(dto.getCompany(), dto);
					row = mcSheet.createRow(rowIndex);
					cell = row.createCell((short) 0);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(i + 1);// 序号
					cell = row.createCell((short) 1);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getCompany());// 公司
					cell = row.createCell((short) 2);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getAssetnum());// 资产数量
					assetNum += dto.getAssetnum();
					cell = row.createCell((short) 3);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseCertificateNum());// 办理权证数量
					houseCertificateNum += dto.getHouseCertificateNum();
					cell = row.createCell((short) 4);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getOccupyArea());// 土地面积
					occupyArea += dto.getOccupyArea();
					cell = row.createCell((short) 5);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getCost());// 资产原值
					cost += dto.getCost();
					cell = row.createCell((short) 6);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getDeprnReserve());// 累计折旧
					deprnReserve += dto.getDeprnReserve();
					cell = row.createCell((short) 7);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getNetAssetValue());// 资产净值
					netAssetValue += dto.getNetAssetValue();
					rowIndex = rowIndex + 1;
				}
				row = mcSheet.createRow(rowIndex);
				cell = row.createCell((short) 1);
				cell.setCellStyle(cellStyle1);
				cell.setCellValue("合计");
				cell = row.createCell((short) 2);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(assetNum);// 资产数量合计
				cell = row.createCell((short) 3);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(houseCertificateNum);// 办理权证数量合计
				cell = row.createCell((short) 4);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(occupyArea);// 土地面积合计
				cell = row.createCell((short) 5);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(cost);// 资产原值合计
				cell = row.createCell((short) 6);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(deprnReserve);// 累计折旧合计
				cell = row.createCell((short) 7);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(netAssetValue);// 资产净值合计
				assetNum = 0;
				houseCertificateNum = 0;
				occupyArea = 0.0;
				houseArea = 0.0;
				cost = 0.0;
				deprnReserve = 0.0;
				netAssetValue = 0.0;
			}
			/** *********************** end **************************** */

            /** **************获取并导出基站房屋报表统计信息****************** */
			DTOSet set1 = dao.getAmshouseInfo(conn);
			if (set1 != null && !set1.isEmpty()) {// 如果基站房屋报表统计信息不为空则写入到EXCEL模版中，以供导出
				size = set1.getSize();
				mcSheet = mcBook.getSheetAt(3);// 得到第四个工作表即:基站房屋
				// 组织数据并写入
				int rowIndex = 2;
				HSSFRow row = null;
				HSSFCell cell = null;
				AmshouseinfoDTO dto = null;
				for (int i = 0; i < size; i++) {
					dto = (AmshouseinfoDTO) set1.getDTO(i);
					if (!map.containsKey(dto.getCompany())) {
						dto1.setCompany(dto.getCompany());// 公司
						dto1.setAssetnum(dto.getAssetnum());// 资产数量
						dto1.setLandnum(0);// 基站土地数量
						dto1.setHousenum(dto.getAssetnum());// 基站房屋数量
						dto1.setHouseCertificateNum(dto.getHouseCertificateNum());// 办理权证数量
						dto1.setHouseArea(dto.getHouseArea());// 房屋面积
						dto1.setOccupyArea(0);// 土地面积
						dto1.setCost(dto.getCost());// 资产原值
						dto1.setDeprnReserve(dto.getDeprnReserve());// 累计折旧
						dto1.setNetAssetValue(dto.getNetAssetValue());// 资产净值
						map.put(dto.getCompany(), dto1);
					} else {
						dto1 = (AmshouseinfoDTO) map.get(dto.getCompany());
						dto1.setAssetnum(dto1.getAssetnum() + dto.getAssetnum());// 资产数量
						dto1.setHousenum(dto.getAssetnum());// 基站房屋数量
						dto1.setHouseCertificateNum(dto1.getHouseCertificateNum() + dto.getHouseCertificateNum());// 办理权证数量
						dto1.setHouseArea(dto.getHouseArea());// 房屋面积
						dto1.setCost(dto1.getCost() + dto.getCost());// 资产原值
						dto1.setDeprnReserve(dto1.getDeprnReserve() + dto.getDeprnReserve());// 累计折旧
						dto1.setNetAssetValue(dto1.getNetAssetValue() + dto.getNetAssetValue());// 资产净值
						map.put(dto.getCompany(), dto1);
					}
					row = mcSheet.createRow(rowIndex);
					cell = row.createCell((short) 0);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(i + 1);// 序号
					cell = row.createCell((short) 1);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getCompany());// 公司
					cell = row.createCell((short) 2);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getAssetnum());// 资产数量
					assetNum += dto.getAssetnum();
					cell = row.createCell((short) 3);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseCertificateNum());// 办理权证数量
					houseCertificateNum += dto.getHouseCertificateNum();
					cell = row.createCell((short) 4);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseArea());// 房屋面积
					houseArea += dto.getHouseArea();
					cell = row.createCell((short) 5);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getCost());// 资产原值
					cost += dto.getCost();
					cell = row.createCell((short) 6);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getDeprnReserve());// 累计折旧
					deprnReserve += dto.getDeprnReserve();
					cell = row.createCell((short) 7);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getNetAssetValue());// 资产净值
					netAssetValue += dto.getNetAssetValue();
					rowIndex = rowIndex + 1;
				}
				row = mcSheet.createRow(rowIndex);
				cell = row.createCell((short) 1);
				cell.setCellStyle(cellStyle1);
				cell.setCellValue("合计");
				cell = row.createCell((short) 2);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(assetNum);// 资产数量合计
				cell = row.createCell((short) 3);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(houseCertificateNum);// 办理权证数量合计
				cell = row.createCell((short) 4);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(houseArea);// 房屋面积合计
				cell = row.createCell((short) 5);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(cost);// 资产原值合计
				cell = row.createCell((short) 6);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(deprnReserve);// 累计折旧合计
				cell = row.createCell((short) 7);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(netAssetValue);// 资产净值合计
				assetNum = 0;
				houseCertificateNum = 0;
				occupyArea = 0.0;
				houseArea = 0.0;
				cost = 0.0;
				deprnReserve = 0.0;
				netAssetValue = 0.0;
			}
			/** ********************** end ***************************** */

			/** ****************** 获取并导出办公土地报表统计信息************* */
			DTOSet set2 = dao.getAmsofficelandInfo(conn);
			if (set2 != null && !set2.isEmpty()) {// 如果办公土地报表统计信息不为空则写入到EXCEL模版中，以供导出
				size = set2.getSize();
				mcSheet = mcBook.getSheetAt(2);// 得到第三个工作表即:办公土地
				// 组织数据并写入
				int rowIndex = 2;
				HSSFRow row = null;
				HSSFCell cell = null;
				AmshouseinfoDTO dto = null;
				for (int i = 0; i < size; i++) {
					dto = (AmshouseinfoDTO) set2.getDTO(i);
					if (!map.containsKey(dto.getCompany())) {
						dto1.setCompany(dto.getCompany());// 公司
						dto1.setAssetnum(dto.getAssetnum());// 资产数量
						dto1.setLandnum(0);// 基站土地数量
						dto1.setHousenum(0);// 基站房屋数量
						dto1.setOfficeLandnum(dto.getAssetnum());// 办公营业土地数量
						dto1.setHouseCertificateNum(dto.getHouseCertificateNum());// 办理权证数量
						dto1.setHouseArea(0);// 房屋面积
						dto1.setOccupyArea(dto.getOccupyArea());// 土地面积
						dto1.setCost(dto.getCost());// 资产原值
						dto1.setDeprnReserve(dto.getDeprnReserve());// 累计折旧
						dto1.setNetAssetValue(dto.getNetAssetValue());// 资产净值
						map.put(dto.getCompany(), dto1);
					} else {
						dto1 = (AmshouseinfoDTO) map.get(dto.getCompany());
						dto1.setAssetnum(dto1.getAssetnum() + dto.getAssetnum());// 资产数量
						dto1.setOfficeLandnum(dto.getAssetnum());// 办公营业土地数量
						dto1.setHouseCertificateNum(dto1.getHouseCertificateNum() + dto.getHouseCertificateNum());// 办理权证数量
						dto1.setOccupyArea(dto1.getOccupyArea() + dto.getOccupyArea());// 土地面积
						dto1.setCost(dto1.getCost() + dto.getCost());// 资产原值
						dto1.setDeprnReserve(dto1.getDeprnReserve() + dto.getDeprnReserve());// 累计折旧
						dto1.setNetAssetValue(dto1.getNetAssetValue() + dto.getNetAssetValue());// 资产净值
						map.put(dto.getCompany(), dto1);
					}
					row = mcSheet.createRow(rowIndex);
					cell = row.createCell((short) 0);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(i + 1);// 序号
					cell = row.createCell((short) 1);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getCompany());// 公司
					cell = row.createCell((short) 2);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getAssetnum());// 资产数量
					assetNum += dto.getAssetnum();
					cell = row.createCell((short) 3);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseCertificateNum());// 办理权证数量
					houseCertificateNum += dto.getHouseCertificateNum();
					cell = row.createCell((short) 4);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getOccupyArea());// 土地面积
					occupyArea += dto.getOccupyArea();
					cell = row.createCell((short) 5);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getCost());// 资产原值
					cost += dto.getCost();
					cell = row.createCell((short) 6);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getDeprnReserve());// 累计折旧
					deprnReserve += dto.getDeprnReserve();
					cell = row.createCell((short) 7);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getNetAssetValue());// 资产净值
					netAssetValue += dto.getNetAssetValue();
					rowIndex = rowIndex + 1;
				}
				row = mcSheet.createRow(rowIndex);
				cell = row.createCell((short) 1);
				cell.setCellStyle(cellStyle1);
				cell.setCellValue("合计");
				cell = row.createCell((short) 2);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(assetNum);// 资产数量合计
				cell = row.createCell((short) 3);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(houseCertificateNum);// 办理权证数量合计
				cell = row.createCell((short) 4);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(occupyArea);// 土地面积合计
				cell = row.createCell((short) 5);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(cost);// 资产原值合计
				cell = row.createCell((short) 6);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(deprnReserve);// 累计折旧合计
				cell = row.createCell((short) 7);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(netAssetValue);// 资产净值合计
				assetNum = 0;
				houseCertificateNum = 0;
				occupyArea = 0.0;
				houseArea = 0.0;
				cost = 0.0;
				deprnReserve = 0.0;
				netAssetValue = 0.0;
			}
			/** ********************** end ************************ */

			/** ************ 获取并导出办公房屋报表统计信息 ************* */
			DTOSet set3 = dao.getAmsofficehouseInfo(conn);
			if (set3 != null && !set3.isEmpty()) {// 如果办公房屋报表统计信息不为空则写入到EXCEL模版中，以供导出
				size = set3.getSize();
				mcSheet = mcBook.getSheetAt(1);// 得到第二个工作表即:办公房屋
				// 组织数据并写入
				int rowIndex = 2;
				HSSFRow row = null;
				HSSFCell cell = null;
				AmshouseinfoDTO dto = null;
				for (int i = 0; i < size; i++) {
					dto = (AmshouseinfoDTO) set3.getDTO(i);
					if (!map.containsKey(dto.getCompany())) {
						dto1.setCompany(dto.getCompany());// 公司
						dto1.setAssetnum(dto.getAssetnum());// 资产数量
						dto1.setLandnum(0);// 基站土地数量
						dto1.setHousenum(0);// 基站房屋数量
						dto1.setOfficeLandnum(0);// 办公营业土地数量
						dto1.setOfficeHousenum(dto.getAssetnum());// 办公营业房屋数量
						dto1.setHouseCertificateNum(dto.getHouseCertificateNum());// 办理权证数量
						dto1.setHouseArea(dto.getHouseArea());// 房屋面积
						dto1.setOccupyArea(0);// 土地面积
						dto1.setCost(dto.getCost());// 资产原值
						dto1.setDeprnReserve(dto.getDeprnReserve());// 累计折旧
						dto1.setNetAssetValue(dto.getNetAssetValue());// 资产净值
						map.put(dto.getCompany(), dto1);
					} else {
						dto1 = (AmshouseinfoDTO) map.get(dto.getCompany());
						dto1.setAssetnum(dto1.getAssetnum() + dto.getAssetnum());// 资产数量
						dto1.setOfficeHousenum(dto.getAssetnum());// 办公营业房屋数量
						dto1.setHouseCertificateNum(dto1.getHouseCertificateNum() + dto.getHouseCertificateNum());// 办理权证数量
						dto1.setHouseArea(dto1.getHouseArea() + dto.getHouseArea());// 房屋面积
						dto1.setCost(dto1.getCost() + dto.getCost());// 资产原值
						dto1.setDeprnReserve(dto1.getDeprnReserve() + dto.getDeprnReserve());// 累计折旧
						dto1.setNetAssetValue(dto1.getNetAssetValue() + dto.getNetAssetValue());// 资产净值
						map.put(dto.getCompany(), dto1);
					}
					row = mcSheet.createRow(rowIndex);
					cell = row.createCell((short) 0);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(i + 1);// 序号
					cell = row.createCell((short) 1);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getCompany());// 公司
					cell = row.createCell((short) 2);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getAssetnum());// 资产数量
					assetNum += dto.getAssetnum();
					cell = row.createCell((short) 3);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseCertificateNum());// 办理权证数量
					houseCertificateNum += dto.getHouseCertificateNum();
					cell = row.createCell((short) 4);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseArea());// 房屋面积
					houseArea += dto.getHouseArea();
					cell = row.createCell((short) 5);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getCost());// 资产原值
					cost += dto.getCost();
					cell = row.createCell((short) 6);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getDeprnReserve());// 累计折旧
					deprnReserve += dto.getDeprnReserve();
					cell = row.createCell((short) 7);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getNetAssetValue());// 资产净值
					netAssetValue += dto.getNetAssetValue();
					rowIndex = rowIndex + 1;
				}
				row = mcSheet.createRow(rowIndex);
				cell = row.createCell((short) 1);
				cell.setCellStyle(cellStyle1);
				cell.setCellValue("合计");
				cell = row.createCell((short) 2);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(assetNum);// 资产数量合计
				cell = row.createCell((short) 3);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(houseCertificateNum);// 办理权证数量合计
				cell = row.createCell((short) 4);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(houseArea);// 房屋面积合计
				cell = row.createCell((short) 5);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(cost);// 资产原值合计
				cell = row.createCell((short) 6);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(deprnReserve);// 累计折旧合计
				cell = row.createCell((short) 7);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(netAssetValue);// 资产净值合计
				assetNum = 0;
				houseCertificateNum = 0;
				occupyArea = 0.0;
				houseArea = 0.0;
				cost = 0.0;
				deprnReserve = 0.0;
				netAssetValue = 0.0;
			}
			/** ********************** end ********************* */

            /** ************ 获取并导出办公房地合一报表统计信息 ************* 
            long houseLandCertificateNum = 0;
            long landCertificateNum = 0;
            DTOSet set4 = dao.getHouseLandInfo(conn);
			if (set4 != null && !set4.isEmpty()) {// 如果办公房屋报表统计信息不为空则写入到EXCEL模版中，以供导出
				size = set4.getSize();
				mcSheet = mcBook.getSheetAt(3);// 得到第四个工作表即:办公房地合一
				// 组织数据并写入
				int rowIndex = 2;
				HSSFRow row = null;
				HSSFCell cell = null;
				AmshouseinfoDTO dto = null;
				for (int i = 0; i < size; i++) {
					dto = (AmshouseinfoDTO) set4.getDTO(i);
					if (!map.containsKey(dto.getCompany())) {
						dto1.setCompany(dto.getCompany());// 公司
						dto1.setAssetnum(dto.getAssetnum());// 资产数量
						dto1.setLandnum(0);// 基站土地数量
						dto1.setHousenum(0);// 基站房屋数量
						dto1.setOfficeLandnum(0);// 办公营业土地数量
						dto1.setOfficeHousenum(0);// 办公营业房屋数量
                        dto1.setHouseLandNum(0);// 基站房地合一数量
                        dto1.setOfficeHouseLandNum(dto.getAssetnum());// 办公房地合一数量
                        dto1.setHouseCertificateNum(dto.getHouseLandCertificateNum());// 办理权证数量
						dto1.setHouseArea(dto.getHouseArea());// 房屋面积
						dto1.setOccupyArea(dto.getOccupyArea());// 土地面积
						dto1.setCost(dto.getCost());// 资产原值
						dto1.setDeprnReserve(dto.getDeprnReserve());// 累计折旧
						dto1.setNetAssetValue(dto.getNetAssetValue());// 资产净值
						map.put(dto.getCompany(), dto1);
					} else {
						dto1 = (AmshouseinfoDTO) map.get(dto.getCompany());//公司
						dto1.setAssetnum(dto1.getAssetnum() + dto.getAssetnum());// 资产数量
						dto1.setOfficeHouseLandNum(dto.getAssetnum());// 办公房地合一数量
						dto1.setHouseCertificateNum(dto1.getHouseCertificateNum() + dto.getHouseLandCertificateNum());// 办理权证数量
						dto1.setHouseArea(dto1.getHouseArea() + dto.getHouseArea());// 房屋面积
                        dto1.setOccupyArea(dto1.getOccupyArea() + dto.getOccupyArea());// 土地面积
                        dto1.setCost(dto1.getCost() + dto.getCost());// 资产原值
						dto1.setDeprnReserve(dto1.getDeprnReserve() + dto.getDeprnReserve());// 累计折旧
						dto1.setNetAssetValue(dto1.getNetAssetValue() + dto.getNetAssetValue());// 资产净值
						map.put(dto.getCompany(), dto1);
					}
					row = mcSheet.createRow(rowIndex);
					cell = row.createCell((short) 0);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(i + 1);// 序号

                    cell = row.createCell((short) 1);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getCompany());// 公司

                    cell = row.createCell((short) 2);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getAssetnum());// 资产数量
					assetNum += dto.getAssetnum();

                    cell = row.createCell((short) 3);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseLandCertificateNum());// 办理权证总量
					houseLandCertificateNum += dto.getHouseLandCertificateNum();

                    cell = row.createCell((short) 4);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseCertificateNum());// 房屋办理权证数量
					houseCertificateNum += dto.getHouseCertificateNum();

                    cell = row.createCell((short) 5);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseArea());// 房屋面积
					houseArea += dto.getHouseArea();

                    cell = row.createCell((short) 6);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getLandCertificateNum());// 土地办理权证数量
					landCertificateNum += dto.getLandCertificateNum();

                    cell = row.createCell((short) 7);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getOccupyArea());// 土地面积
					occupyArea += dto.getOccupyArea();

                    cell = row.createCell((short) 8);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getCost());// 资产原值
					cost += dto.getCost();

                    cell = row.createCell((short) 9);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getDeprnReserve());// 累计折旧
					deprnReserve += dto.getDeprnReserve();

                    cell = row.createCell((short) 10);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getNetAssetValue());// 资产净值
					netAssetValue += dto.getNetAssetValue();

                    rowIndex = rowIndex + 1;
				}
				row = mcSheet.createRow(rowIndex);
				cell = row.createCell((short) 1);
				cell.setCellStyle(cellStyle1);
				cell.setCellValue("合计");
				cell = row.createCell((short) 2);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(assetNum);// 资产数量合计
				cell = row.createCell((short) 3);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(houseLandCertificateNum);// 办理权证总量合计
                cell = row.createCell((short) 4);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(houseCertificateNum);// 房屋办理权证数量合计
                cell = row.createCell((short) 5);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(houseArea);// 房屋面积合计
                cell = row.createCell((short) 6);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(landCertificateNum);// 土地办理权证数量合计
                cell = row.createCell((short) 7);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(occupyArea);// 土地面积合计
                cell = row.createCell((short) 8);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(cost);// 资产原值合计
				cell = row.createCell((short) 9);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(deprnReserve);// 累计折旧合计
				cell = row.createCell((short) 10);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(netAssetValue);// 资产净值合计
                assetNum = 0;
                houseLandCertificateNum = 0;
                houseCertificateNum = 0;
                houseArea = 0.0;
                landCertificateNum = 0;
                occupyArea = 0.0;
				cost = 0.0;
				deprnReserve = 0.0;
				netAssetValue = 0.0;
            }
            */
			/** ********************** end ********************* */

            /** ************ 获取并导出基站房地合一报表统计信息 ************* 
//            long houseLandCertificateNum = 0;
//            long landCertificateNum = 0;
            DTOSet set5 = dao.getBtsHouseLandInfo(conn);
			if (set5 != null && !set5.isEmpty()) {
				size = set5.getSize();
				mcSheet = mcBook.getSheetAt(6);// 得到第七个工作表即:基站房地合一
				// 组织数据并写入
				int rowIndex = 2;
				HSSFRow row = null;
				HSSFCell cell = null;
				AmshouseinfoDTO dto = null;
				for (int i = 0; i < size; i++) {
					dto = (AmshouseinfoDTO) set5.getDTO(i);
					if (!map.containsKey(dto.getCompany())) {
						dto1.setCompany(dto.getCompany());// 公司
						dto1.setAssetnum(dto.getAssetnum());// 资产数量
						dto1.setLandnum(0);// 基站土地数量
						dto1.setHousenum(0);// 基站房屋数量
						dto1.setOfficeLandnum(0);// 办公营业土地数量
						dto1.setOfficeHousenum(0);// 办公营业房屋数量
                        dto1.setOfficeHouseLandNum(0);// 办公房地合一数量
                        dto1.setHouseLandNum(dto.getAssetnum());// 基站房地合一数量
                        dto1.setHouseCertificateNum(dto.getHouseLandCertificateNum());// 办理权证数量
						dto1.setHouseArea(dto.getHouseArea());// 房屋面积
						dto1.setOccupyArea(dto.getOccupyArea());// 土地面积
						dto1.setCost(dto.getCost());// 资产原值
						dto1.setDeprnReserve(dto.getDeprnReserve());// 累计折旧
						dto1.setNetAssetValue(dto.getNetAssetValue());// 资产净值
						map.put(dto.getCompany(), dto1);
					} else {
						dto1 = (AmshouseinfoDTO) map.get(dto.getCompany());//公司
						dto1.setAssetnum(dto1.getAssetnum() + dto.getAssetnum());// 资产数量
						dto1.setHouseLandNum(dto.getAssetnum());// 基站房地合一数量
						dto1.setHouseCertificateNum(dto1.getHouseCertificateNum() + dto.getHouseLandCertificateNum());// 办理权证数量
						dto1.setHouseArea(dto1.getHouseArea() + dto.getHouseArea());// 房屋面积
                        dto1.setOccupyArea(dto1.getOccupyArea() + dto.getOccupyArea());// 土地面积
                        dto1.setCost(dto1.getCost() + dto.getCost());// 资产原值
						dto1.setDeprnReserve(dto1.getDeprnReserve() + dto.getDeprnReserve());// 累计折旧
						dto1.setNetAssetValue(dto1.getNetAssetValue() + dto.getNetAssetValue());// 资产净值
						map.put(dto.getCompany(), dto1);
					}
					row = mcSheet.createRow(rowIndex);
					cell = row.createCell((short) 0);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(i + 1);// 序号

                    cell = row.createCell((short) 1);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getCompany());// 公司

                    cell = row.createCell((short) 2);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getAssetnum());// 资产数量
					assetNum += dto.getAssetnum();

                    cell = row.createCell((short) 3);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseLandCertificateNum());// 办理权证总量
					houseLandCertificateNum += dto.getHouseLandCertificateNum();

                    cell = row.createCell((short) 4);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseCertificateNum());// 房屋办理权证数量
					houseCertificateNum += dto.getHouseCertificateNum();

                    cell = row.createCell((short) 5);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseArea());// 房屋面积
					houseArea += dto.getHouseArea();

                    cell = row.createCell((short) 6);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getLandCertificateNum());// 土地办理权证数量
					landCertificateNum += dto.getLandCertificateNum();

                    cell = row.createCell((short) 7);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getOccupyArea());// 土地面积
					occupyArea += dto.getOccupyArea();

                    cell = row.createCell((short) 8);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getCost());// 资产原值
					cost += dto.getCost();

                    cell = row.createCell((short) 9);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getDeprnReserve());// 累计折旧
					deprnReserve += dto.getDeprnReserve();

                    cell = row.createCell((short) 10);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getNetAssetValue());// 资产净值
					netAssetValue += dto.getNetAssetValue();

                    rowIndex = rowIndex + 1;
				}
				row = mcSheet.createRow(rowIndex);
				cell = row.createCell((short) 1);
				cell.setCellStyle(cellStyle1);
				cell.setCellValue("合计");
				cell = row.createCell((short) 2);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(assetNum);// 资产数量合计
				cell = row.createCell((short) 3);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(houseLandCertificateNum);// 办理权证总量合计
                cell = row.createCell((short) 4);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(houseCertificateNum);// 房屋办理权证数量合计
                cell = row.createCell((short) 5);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(houseArea);// 房屋面积合计
                cell = row.createCell((short) 6);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(landCertificateNum);// 土地办理权证数量合计
                cell = row.createCell((short) 7);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(occupyArea);// 土地面积合计
                cell = row.createCell((short) 8);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(cost);// 资产原值合计
				cell = row.createCell((short) 9);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(deprnReserve);// 累计折旧合计
				cell = row.createCell((short) 10);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(netAssetValue);// 资产净值合计
                assetNum = 0;
                houseLandCertificateNum = 0;
                houseCertificateNum = 0;
                houseArea = 0.0;
                landCertificateNum = 0;
                occupyArea = 0.0;
				cost = 0.0;
				deprnReserve = 0.0;
				netAssetValue = 0.0;
            }*/
			/** ********************** end ********************* */

            /** *********** 获取并导出房屋土地汇总统计信息 ********** */
			Set entrySet = map.keySet();
			if (entrySet != null && !entrySet.isEmpty()) {
				mcSheet = mcBook.getSheetAt(0);// 得到第一个工作表即:汇总
				int rowIndex = 2;
				Iterator it = entrySet.iterator();
				int i = 0;
				HSSFRow row = null;
				HSSFCell cell = null;
				AmshouseinfoDTO dto = null;
				long officeHousenum = 0;// 办公营业房屋数量
				long officeLandnum = 0;// 办公营业土地数量
                long officeHouseLandnum = 0;//办公营业房地合一数量
                long landnum = 0;// 基站土地数量
				long housenum = 0;// 基站房屋数量
                long houseLandNum = 0;//基站房地合一数量

                while (it.hasNext()) {
					// 组织数据并写入
					dto = (AmshouseinfoDTO) map.get(it.next());
					row = mcSheet.createRow(rowIndex);
					cell = row.createCell((short) 0);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(++i);// 序号

                    cell = row.createCell((short) 1);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getCompany());// 公司

                    cell = row.createCell((short) 2);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getAssetnum());// 资产总量
					assetNum += dto.getAssetnum();

                    cell = row.createCell((short) 3);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getOfficeHousenum());// 办公营业房屋数量
					officeHousenum += dto.getOfficeHousenum();

                    cell = row.createCell((short) 4);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getOfficeLandnum());// 办公营业土地数量
					officeLandnum += dto.getOfficeLandnum();

                    cell = row.createCell((short) 5);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getOfficeHouseLandNum());// 办公营业房地合一数量
					officeHouseLandnum += dto.getOfficeHouseLandNum();

                    cell = row.createCell((short) 6);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHousenum());// 基站房屋数量
					housenum += dto.getHousenum();

                    cell = row.createCell((short) 7);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getLandnum());// 基站土地数量
					landnum += dto.getLandnum();

                    cell = row.createCell((short) 8);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseLandNum());// 基站房地合一数量
					houseLandNum += dto.getHouseLandNum();

                    cell = row.createCell((short) 9);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseArea());// 房屋面积
					houseArea += dto.getHouseArea();

                    cell = row.createCell((short) 10);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getOccupyArea());// 土地面积
					occupyArea += dto.getOccupyArea();

                    cell = row.createCell((short) 11);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getHouseCertificateNum());// 办理权证数量
					houseCertificateNum += dto.getHouseCertificateNum();

                    cell = row.createCell((short) 12);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getCost());// 资产原值
					cost += dto.getCost();

                    cell = row.createCell((short) 13);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getDeprnReserve());// 累计折旧
					deprnReserve += dto.getDeprnReserve();

                    cell = row.createCell((short) 14);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(dto.getNetAssetValue());// 资产净值
					netAssetValue += dto.getNetAssetValue();

                    rowIndex = rowIndex + 1;
				}
				row = mcSheet.createRow(rowIndex);
				cell = row.createCell((short) 1);
				cell.setCellStyle(cellStyle1);
				cell.setCellValue("合计");
				cell = row.createCell((short) 2);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(assetNum);// 资产总量合计
				cell = row.createCell((short) 3);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(officeHousenum);// 办公营业房屋数量
				cell = row.createCell((short) 4);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(officeLandnum);// 办公营业土地数量
				cell = row.createCell((short) 5);
				cell.setCellStyle(cellStyle);
                cell.setCellValue(officeHouseLandnum);// 办公营业房地合一数量
				cell = row.createCell((short) 6);
				cell.setCellStyle(cellStyle);
                cell.setCellValue(housenum);// 基站房屋数量
				cell = row.createCell((short) 7);
				cell.setCellStyle(cellStyle);
                cell.setCellValue(landnum);// 基站土地数量
                cell = row.createCell((short) 8);
				cell.setCellStyle(cellStyle);
                cell.setCellValue(houseLandNum);// 基站房地合一数量
                cell = row.createCell((short) 9);
				cell.setCellStyle(cellStyle);
                cell.setCellValue(houseArea);// 房屋面积合计
				cell = row.createCell((short) 10);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(occupyArea);// 土地面积
				cell = row.createCell((short) 11);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(houseCertificateNum);// 办理权证数量合计
				cell = row.createCell((short) 12);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(cost);// 资产原值合计
				cell = row.createCell((short) 13);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(deprnReserve);// 累计折旧合计
				cell = row.createCell((short) 14);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(netAssetValue);// 资产净值合计
			}
			mcBook.write(res.getOutputStream());// 写入数据
			// 资源释放
			this.afterExport(res, ins, mcBook, mcSheet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void beforeExport(HttpServletRequest request,
			HttpServletResponse response, String exportFileName) {
		if (exportFileName == null || "".equals(exportFileName)) {
			exportFileName = request.getParameter("exportTmpName") + ".xls";// 如果没有导出名称则取报表模版名称
		}
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ exportFileName + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
	}

	protected void afterExport(HttpServletResponse response, InputStream ins,
			HSSFWorkbook wbook, HSSFSheet wsheet) throws Exception {
		wbook = null;
		wsheet = null;
		if (ins != null)
			ins.close();
		if (response != null) {
			if (response.getOutputStream() != null)
				response.getOutputStream().flush();
			if (response.getOutputStream() != null)
				response.getOutputStream().close();
		}
	}

	protected String getFile(HttpServletRequest request, String filename)
			throws Exception {
		if (filename == null || "".equals(filename))
			return "";
		else
			return request.getRealPath("/") + "/document/" + filename;
	}
}