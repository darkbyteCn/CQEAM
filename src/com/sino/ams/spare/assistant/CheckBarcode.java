package com.sino.ams.spare.assistant;

import java.sql.Connection;
import java.util.ArrayList;

import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 贾龙川
 * @version 0.1
 *          Date: 2008-3-17
 */
public class CheckBarcode {

    public static boolean isBarcodeRepeated(String barcodeNo, Connection conn) throws QueryException {
        boolean isRepeated = false;
        String sqlStr = "SELECT 1\n" +
                "  FROM ETS_ITEM_INFO EII, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                "   AND ESI.ITEM_CATEGORY NOT IN\n" +
                "       ('BSC', 'BTS', 'DATA', 'ELEC', 'EXCHG', 'NETMGR', 'NETOPT', 'TRANS')\n" +
                "   AND EII.BARCODE = ?";
        SQLModel sqlModel = new SQLModel();
        ArrayList list = new ArrayList();
        list.add(barcodeNo);
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if(sq.getSearchResult().getSize()>0){
            isRepeated = true;
        }
        return isRepeated;

    }

    public static boolean isBarcodeInObject(String barcodeNo,String objectNo, Connection conn) throws QueryException {
        boolean isIn = false;
        String sqlStr = "SELECT 1\n" +
                "  FROM ETS_ITEM_INFO EII, AMS_OBJECT_ADDRESS AOA\n" +
                " WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND EII.BARCODE = ?\n" +
                "   AND AOA.OBJECT_NO = ?";
        SQLModel sqlModel = new SQLModel();
        ArrayList list = new ArrayList();
        list.add(barcodeNo);
        list.add(objectNo);
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if(sq.getSearchResult().getSize()>0){
            isIn = true;
        }
        return isIn;
    }

    public static boolean isQuantityEnough(String barcodeNo, String quantity,String objectNo, Connection conn) throws QueryException {
        boolean isEnough = false;
        String sqlStr = "SELECT QUANTITY\n" +
                "  FROM AMS_SPARE_INFO ASI\n" +
                " WHERE ASI.BARCODE = ?\n" +
                "   AND ASI.OBJECT_NO = ?";
        SQLModel sqlModel = new SQLModel();
        ArrayList list = new ArrayList();
        list.add(barcodeNo);
        list.add(objectNo);
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        try {
            if(Integer.parseInt(sq.getFirstRow().getValue("QUANTITY").toString())>=Integer.parseInt(quantity)){
            isEnough = true;
            }
        } catch (ContainerException e) {
            throw new QueryException(e);
        }
        return isEnough;
    }
}
