package com.sino.ams.newasset.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.ams.appbase.service.AMSFlowService;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AssetsDiscardHeaderDAO;
import com.sino.ams.newasset.dao.AssetsDiscardLineDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.framework.dto.BaseUserDTO;


public class AssetsDiscardService extends AMSFlowService {
    private AssetsDiscardHeaderDAO headerDAO = null;
    private AssetsDiscardLineDAO lineDAO = null;


    public AssetsDiscardService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        headerDAO = new AssetsDiscardHeaderDAO(userAccount, dtoParameter, conn);
        lineDAO = new AssetsDiscardLineDAO(userAccount, null, conn);
    }

    public AmsAssetsTransHeaderDTO searchDataByPrimaryKey() throws QueryException {
        AmsAssetsTransHeaderDTO data = null;
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        if (dto.isPrimaryKeyEmpty()) {
            data = constructDefaultData();
        } else {
            data = headerDAO.searchDTOByPrimaryKey();
            if (data != null) {
                AmsAssetsTransLineDTO lineParameter = new AmsAssetsTransLineDTO();
                lineParameter.setTransId(data.getTransId());
                lineDAO.setDTOParameter(lineParameter);
                List<AmsAssetsTransLineDTO> lines = lineDAO.searchListByForeignKey("transId");
                data.setLines(lines);
            }
        }
        if(data != null){
            processWebComponent(data);            
        }
        return data;
    }

    private AmsAssetsTransHeaderDTO constructDefaultData(){
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        dto.setTransNo(AssetsWebAttributes.ORDER_AUTO_PROD); //设置单据号
        dto.setTransType(AssetsDictConstant.ASS_DIS);
        dto.setCreatedBy(userAccount.getUserId());
        dto.setCreated(userAccount.getUsername());
        dto.setCurrCreationDate();
        dto.setFromCompanyName(userAccount.getCompany());
        dto.setFromOrganizationId(userAccount.getOrganizationId());
        dto.setBookTypeName(userAccount.getBookTypeCode() + "--" + userAccount.getBookTypeName());
        dto.setEmail(userAccount.getEmail());
        dto.setPhoneNumber(userAccount.getMobilePhone());
        dto.setUserDeptName(userAccount.getDeptName());
        DTOSet assetsGroups = userAccount.getUserGroups();
        if(assetsGroups != null && !assetsGroups.isEmpty()){
            if (assetsGroups.getSize() == 1) {
                SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
                dto.setFromGroup(sfGRoup.getGroupId());
                dto.setFromGroupName(sfGRoup.getGroupname());
                dto.setGroupProp(sfGRoup.getGroupProp());
            }
        }
        return dto;
    }

    private void processWebComponent(AmsAssetsTransHeaderDTO data) throws QueryException{
        AssetsOptProducer optProducer = new AssetsOptProducer(userAccount, conn);
        String option = optProducer.getFAContentOption(data.getFaContentCode());
        data.setFaContentOption(option);
        String deptOptions = optProducer.getUserAsssetsDeptOption("");
        data.setFromDeptOption(deptOptions);
    }

    public void processSave() throws DataHandleException {
        boolean operateResult = true;
        boolean autoCommit = false;
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        boolean isNew = dto.isPrimaryKeyEmpty();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            processBizData();
            processProcedure(false);
            operateResult = true;
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            endTransaction(operateResult, autoCommit);
            processPrimaryKey(operateResult, isNew);
        }
    }

    public void processSubmit() throws DataHandleException {
        boolean operateResult = true;
        boolean autoCommit = false;
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        boolean isNew = dto.isPrimaryKeyEmpty();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            processBizData();
            processProcedure(true);
            operateResult = true;
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            endTransaction(operateResult, autoCommit);
            processPrimaryKey(operateResult, isNew);
        }
    }

    /**
     * 功能：处理业务数据
     *
     * @throws DataHandleException
     */
    private void processBizData() throws DataHandleException {
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        String sf_task_attribute1 = dto.getSf_task_attribute1();
        saveBizData();
    }

    private void saveBizData() throws DataHandleException {
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        if (dto.isPrimaryKeyEmpty()) {
            String transId = headerDAO.getNextGUID();
            dto.setPrimaryKey(transId);
            headerDAO.setDTOParameter(dto);
            headerDAO.createData();
        } else {
            headerDAO.updateData();
        }
        List<AmsAssetsTransLineDTO> lines = dto.getLines();
        if (lines != null && !lines.isEmpty()) {
            for (AmsAssetsTransLineDTO line : lines) {
                if (line.isPrimaryKeyEmpty()) {
                    line.setTransId(dto.getTransId());
                    lineDAO.setDTOParameter(line);
                    lineDAO.createData();
                } else {
                    lineDAO.setDTOParameter(line);
                    lineDAO.updateData();
                }
            }
        }
    }


    /**
     * 功能：处理流程数据
     *
     * @throws DataHandleException
     */
    private void processProcedureData() throws DataHandleException {

    }

}
