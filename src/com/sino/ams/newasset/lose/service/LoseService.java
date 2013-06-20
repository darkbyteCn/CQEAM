package com.sino.ams.newasset.lose.service;

import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsItemInfoHistoryDTO;
import com.sino.ams.newasset.lose.constant.LoseAppConstant;
import com.sino.ams.newasset.lose.dao.LoseDAO;
import com.sino.ams.newasset.lose.dto.LoseDTO;
import com.sino.ams.newasset.lose.dto.LoseHeaderDTO;
import com.sino.ams.newasset.lose.dto.LoseLineDTO;
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

/**
 * @系统名称: 挂失
 * @功能描述:
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Jul 14, 2011
 */
public class LoseService extends AssetsBaseService {
    static String ORDER_TITLE = "挂失单";
    private LoseDTO loseDTO = null;
    private LoseHeaderDTO headerDTO = null;

    private DTOSet lines = null;

    private LoseDAO headerDAO = null;

    private String msg = null;

    public LoseService(SfUserDTO user, LoseDTO dto, Connection conn) {
        super(user, dto, conn);
        this.init(user, dto, conn);
    }

    /**
	 * 退回
	 * 
	 * @param
	 * @return
	 */
	public boolean doReturn() {
		boolean operateResult = false;
		boolean autoCommit = true;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			headerDTO.setTransStatus(AssetsDictConstant.REJECTED);
			headerDAO.updateHeaderStatus(headerDTO);
			headerDTO.setApp_dataID( headerDTO.getTransId() ); 
			headerDAO = new LoseDAO( userAccount , headerDTO, conn);
			operateResult = super.rejectProcedure();

		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (DataHandleException ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					this.msg = ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" +  "退回失败";
					conn.rollback();
				} else {
					this.msg = ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" +  "退回成功";
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
			headerDAO.updateHeaderStatus(headerDTO);
			super.deleteReserved(headerDTO.getTransId());
			operateResult = super.cancelProcedure();
			
		} catch (SQLException ex) {
			Logger.logError(ex);
		} catch (DataHandleException ex) {
			Logger.logError(ex);
		} finally {
			try {
				if (!operateResult) {
					this.msg = ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" + "撤销失败";
					conn.rollback();
				} else {
					this.msg = ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" + "撤销成功";
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
                    this.msg = ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" + "保存失败";
                    conn.rollback();
                } else {
                    this.msg = ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" + "保存成功";
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
            this.msg = ORDER_TITLE + "提交";

            if (sfAtt3.equals(LoseAppConstant.ATT3_FILL_DATA)) {
                headerDTO.setTransStatus(AssetsDictConstant.IN_PROCESS);
                this.saveHeader();
                this.saveLines();
            }   else if (sfAtt3.equals(LoseAppConstant.ATT3_APPROVING)) {
                headerDTO.setTransStatus(AssetsDictConstant.COMPLETED);
                headerDAO.updateHeaderStatus(headerDTO);
                super.deleteReserved(headerDTO.getTransId());
                //记录资产变更情况
                saveItemInfoHistory();
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
                    this.msg += ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" + "提交失败";
                    conn.rollback();
                } else {
                    this.msg = ORDER_TITLE + "(" + headerDTO.getTransNo() +  ")" + "提交成功";
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
     * 记录变更历史
     *
     * @throws DataHandleException
     */
    private void saveItemInfoHistory() throws DataHandleException {
        try {
            prodLines();
            if (lines != null && !lines.isEmpty()) {
                String orderURL = "/servlet/com.sino.ams.newasset.lose.servlet.LoseServlet";
                orderURL += "?act=DETAIL_ACTION";
                orderURL += "&transId=" + headerDTO.getTransId();
                for (int i = 0; i < lines.getSize(); i++) {
                    LoseLineDTO line = (LoseLineDTO) lines.getDTO(i);
                    AmsItemInfoHistoryDTO historyDTO = new AmsItemInfoHistoryDTO();
                    historyDTO.setBarcode( line.getBarcode() );
                    historyDTO.setOrderCategory(LoseAppConstant.ORDER_CATEGORY);
                    historyDTO.setOrderNo(headerDTO.getTransNo());
                    historyDTO.setOrderDtlUrl(orderURL);
                    historyDTO.setCreatedBy(userAccount.getUserId());
                    historyDTO.setRemark(LoseAppConstant.TRANS_TYPE_NAME);
                    super.saveItemInfoHistory(historyDTO);
                }
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        }
    }

    /**
     * 保存头
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
            headerDAO.createHeader(headerDTO);
        } else {
            headerDAO.updateHeader(headerDTO);
        }
    }

    /**
     * 保存行
     * @throws DataHandleException
     * @throws CalendarException
     */
    public void saveLines() throws DataHandleException, CalendarException {
        String transId = headerDTO.getTransId();
        if (!StrUtil.isEmpty(transId)) {
            super.deleteReserved(transId);
            headerDAO.deleteLine(transId);
        }
        LoseLineDTO line = null;
        String lineId = null;
        SeqProducer seqProducer = new SeqProducer(conn);
        for (int i = 0; i < lines.getSize(); i++) {
            line = (LoseLineDTO) lines.getDTO(i);
            lineId = seqProducer.getGUID();
            line.setLineId(lineId);
            line.setTransId(headerDTO.getTransId());
            headerDAO.createLine(line);
            super.createReserved(transId, line.getBarcode());
        }
    }

    /**
     * 初始化头信息
     * @param dto
     * @return
     */
    private LoseHeaderDTO initHeaderData(LoseHeaderDTO dto) {
        dto.setTransNo(AssetsWebAttributes.ORDER_AUTO_PROD); // 设置单据号
        dto.setCreatedBy(userAccount.getUserId()); // 设置创建人
        dto.setCreated(userAccount.getUsername()); // 设置创建人
        dto.setFromOrganizationId(userAccount.getOrganizationId());
        dto.setFromCompanyName(userAccount.getCompany());
        dto.setTransTypeValue(LoseAppConstant.TRANS_TYPE_NAME);
        dto.setTransType(LoseAppConstant.TRANS_TYPE);
         
        dto.setCurrCreationDate();
        dto.setEmail(userAccount.getEmail());
        dto.setPhoneNumber(userAccount.getMobilePhone());
        return dto;
    }

    /**
     * 取详细数据
     * @throws QueryException
     */
    public void prodData() throws QueryException {
        headerDTO = this.setFlowIdToDTO(headerDTO);
        prodHeader();
        prodLines();
    }

    /**
     * 取头信息
     * @throws QueryException
     */
    private void prodHeader() throws QueryException {
        headerDAO.setDTOClassName(LoseHeaderDTO.class.getName());
        headerDAO.setCalPattern(CalendarConstant.LINE_PATTERN);

        LoseHeaderDTO tmpDTO = (LoseHeaderDTO) headerDTO.clone();

        headerDTO = (LoseHeaderDTO) headerDAO.getDataByPrimaryKey();
        // 当新建时候
        if (null == headerDTO || StrUtil.isEmpty(headerDTO.getTransId())) {
            headerDTO = new LoseHeaderDTO();
            headerDTO = initHeaderData(headerDTO);
        }
        headerDTO.setSf_task_attribute3(tmpDTO.getSf_task_attribute3());

        loseDTO.setHeaderDTO(headerDTO);
    }

    /**
     * 取行信息
     * @throws QueryException
     */
    private void prodLines() throws QueryException {
        lines = headerDAO.getLinesData(headerDTO.getTransId());
        loseDTO.setLines(lines);
    }

    public LoseDTO getForm() throws QueryException {
    	AssetsOptProducer optProducer = new AssetsOptProducer(userAccount, conn);
        if("".equals(loseDTO.getHeaderDTO().getEmergentLevel())) {
        	loseDTO.getHeaderDTO().setEmergentLevel("0");
        }            
        String emergentLevelOption = optProducer.getAmsEmergentLevel(headerDTO.getEmergentLevel());
        headerDTO.setEmergentLevelOption(emergentLevelOption);

        String opt = optProducer.getSpecialAsssetsDeptOption(headerDTO.getSpecialityDept());
        headerDTO.setSpecialityDeptOption(opt);
        loseDTO.setHeaderDTO(headerDTO);
        loseDTO.setLines(lines);
        return loseDTO;
    }

    public void setForm(LoseDTO loseDTO) {
        this.loseDTO = loseDTO;
        this.lines = loseDTO.getLines();
        this.headerDTO = loseDTO.getHeaderDTO();
        this.headerDAO = new LoseDAO(userAccount, headerDTO, conn);
    }


    /**
     * 功能：准备流程数据,由应用实现
     */
    protected void prepareProcedureData(){
        flowDTO.setApp_dataID(headerDTO.getTransId()); //应用ID
        flowDTO.setPrimaryKey(headerDTO.getTransId());
        flowDTO.setOrderNo(headerDTO.getTransNo());
        flowDTO.setOrderName(LoseAppConstant.PROC_NAME);
    }


    /**
     * 初始化
     * @param user
     * @param dto
     * @param conn
     */
    private void init(SfUserDTO user, LoseDTO dto, Connection conn) {
        this.loseDTO = dto;
        this.lines = loseDTO.getLines();
        this.headerDTO = loseDTO.getHeaderDTO();
        this.headerDAO = new LoseDAO(user, headerDTO, conn);
    }

    /**
     * 将流程中保存的单据ID设置进DTO
     * @param dtoParameter
     * @return
     */
    private LoseHeaderDTO setFlowIdToDTO(LoseHeaderDTO dtoParameter) {
        if (StrUtil.isEmpty(dtoParameter.getTransId())) {
            dtoParameter.setTransId(StrUtil.nullToString(dtoParameter.getApp_dataID()));
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

}
