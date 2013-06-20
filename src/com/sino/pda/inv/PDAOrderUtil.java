package com.sino.pda.inv;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.FileException;
import com.sino.base.exception.QueryException;
import com.sino.base.file.FileProcessor;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.pda.inv.model.InvOperateModel;
import com.sino.pda.inv.model.ItemXmlModel;
import com.sino.pda.util.XmlUtil;
import org.jdom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-1-7
 * Time: 21:23:26
 * To change this template use File | Settings | File Templates.
 */
public class PDAOrderUtil {

    private String getInvOperateOrderNo(Connection conn, String opType) {
        if (opType.equalsIgnoreCase("INV_IN")) {

        }
        return null;
    }

    /**
     * PDA提交工单(库存类)
     *
     * @param conn
     * @param xml
     * @param sfUser
     * @param filePath
     * @param webPath
     * @return
     * @throws com.sino.base.exception.QueryException
     *
     * @throws com.sino.base.exception.ContainerException
     *
     * @throws com.sino.base.exception.DataHandleException
     *
     * @throws java.sql.SQLException
     * @throws com.sino.base.exception.FileException
     *
     */
    public boolean submitOrder(Connection conn, XmlUtil xml, SfUserDTO sfUser, String filePath, String webPath) throws QueryException, ContainerException, DataHandleException, SQLException, FileException {

        SQLModel sqlModel = new SQLModel();
        //OrderModel orderModel = new OrderModel();
        //InvOperateModel workorderModel = new InvOperateModel();
        //SystemItemUtil systemItemUtil = new SystemItemUtil();
        //boolean isSuccess = false;
        boolean submitResult = false;

        List workorders = xml.getAllRootChildren();

        int workorderCount = workorders.size();
        String workorderNo, workorderTypeDesc;//, loc, scanoverDate;


        for (int i = 0; i < workorderCount; i++) {//工单循环
            Element xmlWO = xml.getNthElement(workorders, i);
            workorderNo = "";

            workorderTypeDesc = xml.getElementAttrValue(xmlWO, "type");

            String fileHeader = "";
            InvOperateModel iom = new InvOperateModel();

            if (workorderTypeDesc.equals("库存出库")) {
                fileHeader = "INV_OUT_";
                workorderNo = iom.getAssetNumber(conn, sfUser.getCompanyCode(), "INV-O", 1);
            } else if (workorderTypeDesc.equals("库存入库")) {
                fileHeader = "INV_IN_";
                workorderNo = iom.getAssetNumber(conn, sfUser.getCompanyCode(), "INV-I", 1);
            } else {
                continue;
            }

            /******************************************
             *********** backup workorder file*********
             ******************************************/

            String bkFlName = fileHeader + workorderNo + ".xml";
            String bkFile = webPath + System.getProperty("file.separator") + bkFlName;
            File kk = new File(bkFile);
            FileProcessor fp = new FileProcessor();

            fp.copyFile(filePath, bkFile);

            /**
             * A: create Bill header
             */

            int bill_id = iom.createInvBillHeader(conn,
                    workorderNo,
                    xml.getElementAttrValue(xmlWO, "object_no"),
                    workorderTypeDesc,
                    xml.getElementAttrValue(xmlWO, "item_type"),
                    Integer.valueOf(sfUser.getUserId()).intValue()
            );

            /**
             * B: create Bill line
             *    and update item information,backup change log
            */

            int success=0;


            if(bill_id>0)
            {
                List xmlAllBarCodes = xml.getAllChild(xmlWO);

                int itemCount = xmlAllBarCodes.size();

                for (int j = 0; j < itemCount; j++) {
                    Element barcodeXml = xml.getNthElement(xmlAllBarCodes, j);
                    if (!barcodeXml.getName().equals("item"))
                        break;
                    ItemXmlModel itmModel=new ItemXmlModel();
                    itmModel.barcode= xml.getElementAttrValue(barcodeXml, "barcode");
                    itmModel.item_category2= xml.getElementAttrValue(barcodeXml, "item_category2");
                    itmModel.name= xml.getElementAttrValue(barcodeXml, "name");
                    itmModel.spec= xml.getElementAttrValue(barcodeXml, "spec");
                    itmModel.quantity= xml.getElementAttrValue(barcodeXml, "quantity");
                    itmModel.price= xml.getElementAttrValue(barcodeXml, "price");
                    itmModel.item_code= xml.getElementAttrValue(barcodeXml, "item_code");
                    itmModel.category= xml.getElementAttrValue(barcodeXml, "category");
                    itmModel.start_date= xml.getElementAttrValue(barcodeXml, "start_date");
                    itmModel.responsibility_dept= xml.getElementAttrValue(barcodeXml, "responsibility_dept");
                    itmModel.responsibility_user= xml.getElementAttrValue(barcodeXml, "responsibility_user");
                    itmModel.new_object_no= xml.getElementAttrValue(barcodeXml, "new_object_no");
                    itmModel.maintain_user= xml.getElementAttrValue(barcodeXml, "maintain_user");
                    itmModel.attribute3= xml.getElementAttrValue(barcodeXml, "attribute3");
                    itmModel.manual= xml.getElementAttrValue(barcodeXml, "manual");
                    itmModel.Status= xml.getElementAttrValue(barcodeXml, "status");
                    success =iom.createInvBillLine(conn,workorderNo,bill_id,sfUser,itmModel) ;
                 }
            }
            if(success>0)
            {
                  submitResult=true;
            }
        }
        return submitResult;
    }
}
