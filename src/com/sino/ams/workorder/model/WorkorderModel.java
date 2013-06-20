package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.util.ArrUtil;
import com.sino.ams.constant.AmsOrderConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * User: zhoujs
 * Date: 2007-11-18
 * Time: 19:38:49
 * Function:
 */
public class WorkorderModel {
    /**
     * 更新工单状态
     * @param workorderNo
     * @param sfUser
     * @param orderFlag
     * @return
     */
    public SQLModel getUpdateOrderStatusModel(String workorderNo, SfUserDTO sfUser, String orderFlag) {

        SQLModel sqlModel = new SQLModel();
        if (orderFlag.equals(DictConstant.WOR_STATUS_DEPLOY)) {//下发工单

        } else if (orderFlag.equals(DictConstant.WOR_STATUS_DOWNLOAD)) {//下载工单
            sqlModel = getDownloadStatusModel(workorderNo, sfUser);
        }

        return sqlModel;
    }

    /**
     * 更新下载工单状态
     * @param workorderNo
     * @param sfUser
     * @return
     */
    private SQLModel getDownloadStatusModel(String workorderNo, SfUserDTO sfUser) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_WORKORDER \n" +
                "   SET WORKORDER_FLAG = ?,\n" +
                "       DOWNLOAD_DATE = GETDATE(),\n" +
                "       DOWNLOAD_BY   = ?\n" +
                " WHERE WORKORDER_NO = ?";
        sqlArgs.add(DictConstant.WOR_STATUS_DOWNLOAD);
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(workorderNo);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
    
    /**
     * 工单退回:将工单状态改为已下发状态
     * @param workorderNo
     * @param sfUser
     * @return
     */
    public SQLModel getOrderDeployModel(String workorderNo) {
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_WORKORDER \n" +
                "   SET WORKORDER_FLAG = ?\n" +
                " WHERE WORKORDER_NO = ?";
        sqlArgs.add(DictConstant.WOR_STATUS_DEPLOY);
        sqlArgs.add(workorderNo);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
	}

    /**
     * 删除工单行信息
     * @param workorderNo
     * @return
     */
    public SQLModel getDeleteWorkorderDtlModel(String workorderNo) {
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM ETS_WORKORDER_DTL \n" +
                " WHERE WORKORDER_NO = ?";
        sqlArgs.add(workorderNo);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
	}
    public SQLModel getDeleteWorkorderInterModel(String workorderNo) {
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM ETS_WORKORDER_INTERFACE \n" +
                " WHERE WORKORDER_NO = ?";
        sqlArgs.add(workorderNo);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
	}

    public SQLModel getOrderAchieveModel(String workorderNo, SfUserDTO sfUser) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_WORKORDER \n" +
                "   SET WORKORDER_FLAG = ?,\n" +
                "       CHECKOVER_DATE = GETDATE(),\n" +
                "       CHECKOVER_BY   = ?,\n" +
                "       ARCHFLAG       = 1\n" +
                " WHERE WORKORDER_NO = ?";
        sqlArgs.add(DictConstant.WOR_STATUS_ARCHIVED);
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(workorderNo);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 更新工单差异原因及责任人
     * @param workorderNo
     * @param responsibilityUser
     * @param diffReason
     * @return
     */
       public SQLModel getUpdateOrderDiffModel(String workorderNo, String responsibilityUser,String diffReason) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_WORKORDER \n" +
                "   SET RESPONSIBILITY_USER = ?,\n" +
                " DIFFERENCE_REASON=?\n" +
                " WHERE WORKORDER_NO = ?";

        sqlArgs.add(responsibilityUser);
        sqlArgs.add(diffReason);
        sqlArgs.add(workorderNo);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 更新工单进度条
     * @param workorderNo
     * @param process
     * @param isComplete
     * @return
     */
    public SQLModel getUpdateOrderProcessModel(String workorderNo, String process, boolean isComplete) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_PROCESSBAR \n" +
                " SET " + process + " = ?\n" +
                " WHERE WORKORDER_NO = ?";

        if (isComplete) {
            sqlArgs.add("100");
        } else {
            sqlArgs.add("50");
        }
        sqlArgs.add(workorderNo);

        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
     /**
     * 更新工单进度条
     * @param workorderNos
     * @param process
     * @param isComplete
     * @return
     */
     public SQLModel getUpdateOrderProcessModel(String[] workorderNos, String process, boolean isComplete) {
         SQLModel sqlModel = new SQLModel();

         String workorderNoStr = ArrUtil.arrToSqlStr(workorderNos);
         List sqlArgs = new ArrayList();
         String sqlStr = "UPDATE ETS_PROCESSBAR \n" +
                 " SET " + process + " = ?\n" +
                 " WHERE WORKORDER_NO IN ("+workorderNoStr+")";

         if (isComplete) {
             sqlArgs.add("100");
         } else {
             sqlArgs.add("50");
         }
//         sqlArgs.add(workorderNoStr);

         sqlModel.setArgs(sqlArgs);
         sqlModel.setSqlStr(sqlStr);

         return sqlModel;
     }

    /**
     * 备份设备信息
     * @param barcodesList 标签号
     * @param workorderNo 工单号
     * @param sfUser  用户信息
     * @return SQLModel
     */
    public SQLModel getBackupItemModel(List barcodesList, String workorderNo, SfUserDTO sfUser) {
        SQLModel sqlModel = new SQLModel();
       String ORDER_DTL_URL="/public/wait.jsp?title=工单详细信息&src=/servlet/com.sino.ams.workorder.servlet.OrderDetailServlet&act=DETAIL_ACTION&WORKORDER_NO="+workorderNo;

        String barcodeStr = "";
        String linkStr = ",";
        if (barcodesList != null && barcodesList.size() > 0) {
            boolean hasProcessed = false;
            for (int i = 0; i <= barcodesList.size() - 1; i++) {
                String barcode=barcodesList.get(i).toString();
                barcodeStr += "'" + barcode + "'" + linkStr;
                hasProcessed = true;
            }
            if (hasProcessed) {
                barcodeStr = barcodeStr.substring(0, barcodeStr.length() - linkStr.length());
            }
        }else{
             barcodeStr="''";
        }
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_ITEM_INFO_HISTORY\n" +
                "  (HISTORY_ID,\n" +
                "   BARCODE,\n" +
                "   ADDRESS_ID,\n" +
                "   ITEM_CODE,\n" +
                "   RESPONSIBILITY_USER,\n" +
                "   RESPONSIBILITY_DEPT,\n" +
                "   ORDER_NO,\n" +
                "   ORDER_CATEGORY,\n" +
                "   ORDER_DTL_URL,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY,\n" +
                "   REMARK)\n" +
                "  (SELECT " +
                "    NEWID() ," +
                "   BARCODE," +
                "   ADDRESS_ID," +
                "   ITEM_CODE," +
                "   RESPONSIBILITY_USER," +
                "   RESPONSIBILITY_DEPT," +
                "   ?, ?, ?, GETDATE(), ?,\n" +
                "           REMARK\n" +
                "     FROM ETS_ITEM_INFO EII\n" +
                "    WHERE EII.BARCODE IN("+barcodeStr+"))";
        sqlArgs.add(workorderNo);
        sqlArgs.add(AmsOrderConstant.ORDER);
        sqlArgs.add(ORDER_DTL_URL);
        sqlArgs.add(sfUser.getUserId());
//        sqlArgs.add(barcodeStr);


        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }
}
