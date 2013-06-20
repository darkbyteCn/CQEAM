package com.sino.ams.newasset.service;

import com.sino.ams.appbase.service.AMSBaseService;
import com.sino.ams.newasset.dao.ItemFinanceReportDAO;
import com.sino.ams.newasset.dto.ItemFinanceReportDTO;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;


public class ItemFinanceReportService extends AMSBaseService {

    public ItemFinanceReportService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public DTOSet getItemFinanceReport() throws QueryException {
//==========================由于目前数据库资产属性数据不准确，导致查询结果与实物台账查询不一致，暂时注释掉，今后再开放，唐明胜备注，切勿删除该段代码===============================
//        ItemFinanceReportDAO reportDAO = new ItemFinanceReportDAO(userAccount, dtoParameter, conn);
//        DTOSet report = reportDAO.getFinanceDictionaryList();
//        DTOSet itemReport = reportDAO.getItemFinanceReport();
//        if (itemReport != null && !itemReport.isEmpty()) {
//            int dictCount = report.getSize();
//            int reportCount = itemReport.getSize();
//            for (int i = 0; i < reportCount; i++) {
//                ItemFinanceReportDTO dto = (ItemFinanceReportDTO) itemReport.getDTO(i);
//                for (int j = 0; j < dictCount; j++) {
//                    ItemFinanceReportDTO reportDTO = (ItemFinanceReportDTO) report.getDTO(j);
//                    if(reportDTO.getFinanceProp().equals(dto.getFinanceProp())){
//                        reportDTO.setItemCount(dto.getItemCount());
//                        break;
//                    }
//                }
//            }
//        }
//        return report;
//==========================由于目前数据库资产属性数据不准确，导致查询结果与实物台账查询不一致，暂时注释掉，今后再开放，唐明胜备注，切勿删除该段代码===============================
        return new DTOSet();
    }

}
