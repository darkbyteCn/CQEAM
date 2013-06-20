package com.sino.ams.newasset.scrap.service;

import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.scrap.constant.ScrapAppConstant;
import com.sino.ams.newasset.scrap.dao.AmsAssetsTransHeaderDAO;
import com.sino.ams.newasset.scrap.dto.TransDTO;
import com.sino.ams.newasset.service.AssetsBaseService;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class TransService extends AssetsBaseService {
    static String ORDER_TITLE = "报废单";
    private TransDTO transDTO = null;
    private AmsAssetsTransHeaderDTO headerDTO = null;

    private DTOSet lines = null;

    protected AmsAssetsTransHeaderDAO headerDAO = null;

    protected String msg = null;

    public TransService(SfUserDTO user, TransDTO dto, Connection conn) {
        super(user, dto, conn);
        this.init(user, dto, conn);
    }


    /**
     * 撤销
     *
     * @param
     * @return
     */
    public boolean doCancel() {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            headerDTO.setTransStatus(AssetsDictConstant.CANCELED);
            super.deleteReserved(headerDTO.getTransId());
            operateResult = super.cancelProcedure();
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    this.msg = "撤销失败";
                    conn.rollback();
                } else {
                    this.msg = "撤销成功";
                    conn.commit();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
        return operateResult;
    }

    /**
     * 保存
     *
     * @param
     * @return
     */
    public boolean doSave() {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            headerDTO.setTransStatus(AssetsDictConstant.SAVE_TEMP);
            this.saveHeader();
            this.saveLines();
            operateResult = super.processProcedure(false);
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } catch (CalendarException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    this.msg = "保存失败";
                    conn.rollback();
                } else {
                    this.msg = "保存成功";
                    conn.commit();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
        return operateResult;
    }

    /**
     * 保存
     *
     * @param
     * @return
     */
    public boolean doSubmit() {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String sfAtt3 = headerDTO.getSf_task_attribute3();
            String flowCode = headerDTO.getFlowCode();
            this.msg = ORDER_TITLE + "提交";

            if (sfAtt3.equals(ScrapAppConstant.ATT3_FILL_DATA)) {
                headerDTO.setTransStatus(AssetsDictConstant.IN_PROCESS);
                this.saveHeader();
                this.saveLines();
            } else if (sfAtt3.equals(ScrapAppConstant.ATT3_APPROVING)) {
                if (flowCode.equals("A1")) {
                    headerDTO.setTransStatus(AssetsDictConstant.APPROVED);
                } else { // 撤销
                    super.deleteReserved(headerDTO.getTransId());
                    headerDTO.setTransStatus(AssetsDictConstant.CANCELED);
                }
                headerDTO.setTransStatus(AssetsDictConstant.COMPLETED);
            }
            operateResult = super.processProcedure(true);
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } catch (CalendarException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    this.msg += "失败";
                    conn.rollback();
                } else {
                    this.msg = ORDER_TITLE + "“" + headerDTO.getTransNo() + "”"
                            + "提交成功";
                    conn.commit();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
        return operateResult;
    }

    /**
     * 保存头
     *
     * @throws DataHandleException
     * @throws SQLException
     */
    public void saveHeader() throws DataHandleException, SQLException {
        if (headerDTO.getTransNo().equals(AssetsWebAttributes.ORDER_AUTO_PROD)) {
            if (StrUtil.isEmpty(headerDTO.getTransId())) {
                SeqProducer seqProducer = new SeqProducer(conn);
                String transId = seqProducer.getGUID();
                headerDTO.setTransId(transId);
            }
            String companyCode = userAccount.getCompanyCode(); // 还是采用该方法，以下考虑周子君认为没必要
            String transType = headerDTO.getTransType();
            OrderNumGenerator numberProducer = new OrderNumGenerator(conn,
                    companyCode, transType);
            headerDTO.setTransNo(numberProducer.getOrderNum());
//			headerDAO.createHeader(headerDTO);
        } else {
//			headerDAO.updateHeader(headerDTO);
        }
    }

    /**
     * 保存行
     *
     * @throws DataHandleException
     * @throws CalendarException
     */
    public void saveLines() throws DataHandleException, CalendarException {
        String transId = headerDTO.getTransId();
        if (!StrUtil.isEmpty(transId)) {
            super.deleteReserved(transId);
//			headerDAO.deleteLine(transId);
        }
        AmsAssetsTransLineDTO line = null;
        String lineId = null;
        SeqProducer seqProducer = new SeqProducer(conn);
        for (int i = 0; i < lines.getSize(); i++) {
            line = (AmsAssetsTransLineDTO) lines.getDTO(i);
            lineId = seqProducer.getGUID();
            line.setLineId(lineId);
            line.setTransId(headerDTO.getTransId());
//			headerDAO.createLine(line);
            super.createReserved(transId, line.getBarcode());
        }
    }

    /**
     * 初始化头信息
     *
     * @param dto
     * @return
     */
    private AmsAssetsTransHeaderDTO initHeaderData(AmsAssetsTransHeaderDTO dto) {
        dto.setTransNo(AssetsWebAttributes.ORDER_AUTO_PROD); // 设置单据号
        dto.setCreatedBy(userAccount.getUserId()); // 设置创建人
        dto.setCreated(userAccount.getUsername()); // 设置创建人
        dto.setFromOrganizationId(userAccount.getOrganizationId());
        dto.setFromCompanyName(userAccount.getCompany());
        dto.setTransTypeValue(ScrapAppConstant.TRANS_TYPE_NAME);
        dto.setTransType(ScrapAppConstant.TRANS_TYPE);

        dto.setCurrCreationDate();
        dto.setEmail(userAccount.getEmail());
        dto.setPhoneNumber(userAccount.getMobilePhone());
        return dto;
    }

    /**
     * 取详细数据
     *
     * @throws QueryException
     */
    public void prodData() throws QueryException {
        headerDTO = this.setFlowIdToDTO(headerDTO);
        prodHeader();
        prodLines();
    }

    /**
     * 取头信息
     *
     * @throws QueryException
     */
    private void prodHeader() throws QueryException {
        headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
        headerDAO.setCalPattern(CalendarConstant.LINE_PATTERN);

        AmsAssetsTransHeaderDTO tmpDTO = (AmsAssetsTransHeaderDTO) headerDTO.clone();

        headerDTO = (AmsAssetsTransHeaderDTO) headerDAO.getDataByPrimaryKey();
        // 当新建时候
        if (null == headerDTO || StrUtil.isEmpty(headerDTO.getTransId())) {
            headerDTO = new AmsAssetsTransHeaderDTO();
            headerDTO = initHeaderData(headerDTO);
        }
        headerDTO.setSf_task_attribute3(tmpDTO.getSf_task_attribute3());

        transDTO.setHeaderDTO(headerDTO);
    }

    /**
     * 取行信息
     *
     * @throws QueryException
     */
    private void prodLines() throws QueryException {
//		lines = headerDAO.getLinesData(headerDTO.getTransId());
        transDTO.setLines(lines);
    }

    public TransDTO getForm() throws QueryException {
        AssetsOptProducer optProducer = new AssetsOptProducer(userAccount, conn);
        if ("".equals(transDTO.getHeaderDTO().getEmergentLevel())) {
            transDTO.getHeaderDTO().setEmergentLevel("0");
        }
        String emergentLevelOption = optProducer.getAmsEmergentLevel(headerDTO
                .getEmergentLevel());
        headerDTO.setEmergentLevelOption(emergentLevelOption);
        transDTO.setHeaderDTO(headerDTO);
        transDTO.setLines(lines);
        return transDTO;
    }

    public void setForm(TransDTO transDTO) {
        this.transDTO = transDTO;
        this.lines = transDTO.getLines();
        this.headerDTO = transDTO.getHeaderDTO();
        this.headerDAO = new AmsAssetsTransHeaderDAO(userAccount, headerDTO, conn);
    }


    /**
     * 功能：准备流程数据,由应用实现
     */
    protected void prepareProcedureData(){
        flowDTO.setApp_dataID(headerDTO.getTransId()); // 应用ID
        flowDTO.setPrimaryKey(headerDTO.getTransId()); // 应用ID
        flowDTO.setOrderNo(headerDTO.getTransNo()); // 应用的单据编号
        flowDTO.setOrderName(ScrapAppConstant.PROC_NAME); // 应用的单据编号
    }


    /**
     * 将流程中保存的单据ID设置进DTO
     *
     * @param dtoParameter
     * @return
     */
    private AmsAssetsTransHeaderDTO setFlowIdToDTO(AmsAssetsTransHeaderDTO dtoParameter) {
        if (StrUtil.isEmpty(dtoParameter.getTransId())) {
            dtoParameter.setTransId(StrUtil.nullToString(dtoParameter
                    .getApp_dataID()));
        }
        return dtoParameter;
    }

    public File exportFile() throws DataTransException {
        return headerDAO.exportFile();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    /**
     * 初始化
     *
     * @param user
     * @param dto
     * @param conn
     */
    private void init(SfUserDTO user, TransDTO dto, Connection conn) {
        this.transDTO = dto;

        this.lines = transDTO.getLines();
        this.headerDTO = transDTO.getHeaderDTO();
        this.headerDAO = new AmsAssetsTransHeaderDAO(user, headerDTO, conn);
    }
}
