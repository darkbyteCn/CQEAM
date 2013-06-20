package com.sino.config;

import com.sino.base.constant.WorldConstant;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

/**
 * User: baileigy
 * Date: 11-10-18 下午5:03
 * Function:
 */
public abstract class SinoConfig {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("config.app");

    public static String getProvinceCode() {
        return getProperty("PROVINCECODE");
    }

    public static String getTDProvinceCode() {
        return getProperty("TDPROVINCECODE");
    }

    public static String getOrgStructureName() throws UnsupportedEncodingException {
        return new String(getProperty("ORG_STRUCTURE_NAME").getBytes("ISO-8859-1"), "GBK");
    }

    public static String getTDOrgStructureName() throws UnsupportedEncodingException {
        return new String(getProperty("TD_ORG_STRUCTURE_NAME").getBytes("ISO-8859-1"), "GBK");
    }

    public static String getODIUser() {
        return getProperty("ODI_USER");
    }

    public static String getFaCat1Mis() {
        return getProperty("MIS_FA_CAT_1");
    }

    public static String getFaCat2Mis() {
        return getProperty("MIS_FA_CAT_2");
    }

    public static String getFaCat3Mis() {
        return getProperty("MIS_FA_CAT_3");
    }

    public static String getFlexValueSetNameMis() {
        return getProperty("MIS_FLEX_VALUE_SET_NAME");
    }

    public static String getLoc1SetNameMis() {
        return getProperty("MIS_LOC1_SET_NAME");
    }

    public static String getLoc1SetNameTD() {
        return getProperty("TD_LOC1_SET_NAME");
    }

    public static String getFlexValueSetNameTD() {
        return getProperty("TD_FLEX_VALUE_SET_NAME");
    }
    
    public static String getEamLoc3Set_Name() throws UnsupportedEncodingException {
    	return new String(getProperty("EAM_LOC3_000_SET_NAME").getBytes("ISO-8859-1"), "GBK");
    }
    
    public static String getEamLoc3ASet_Name() throws UnsupportedEncodingException {
    	return new String(getProperty("EAM_LOC3_A01_SET_NAME").getBytes("ISO-8859-1"), "GBK");
    }
    
    public static String getEamLoc3TdSet_Name() throws UnsupportedEncodingException {
    	return new String(getProperty("EAM_LOC3_000_TD_SET_NAME").getBytes("ISO-8859-1"), "GBK");
    }
    public static boolean getFlowHnOpen() {
        String flowHnOpen = getProperty("FLOW_HN_OPEN");
        return flowHnOpen.equalsIgnoreCase("TRUE");
    }
    //河南流程个性化开关，小心使用
//    public static boolean getFlowHnOpen() {
//        String flowHnOpen = "";
//        try {
//            flowHnOpen = StrUtil.nullToString(getProperty("FLOW_HN_OPEN"));
//        } catch (Throwable ex) {
//            Logger.logError(ex.getMessage());
//        }
//
//        if (flowHnOpen.equals("true")) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public static String getSystemAdminRole() throws UnsupportedEncodingException{
        return new String(getProperty("SYS_ADMIN_ROLE").getBytes("ISO-8859-1"), "GBK");
    }

    public static String getProperty(String propertyName) {
        String value = "";
        if (bundle.containsKey(propertyName)) {
            value = bundle.getString(propertyName);
        }
        return value;
    }

    public static String getConnectionTimeout() {
        return getProperty("CONNECTION_TIMEOUT");
    }

    public static String getReceiveTimeout() {
        return getProperty("RECEIVE_TIMEOUT");
    }
    public static String getExportHOME() {
        String exportDIR = "";
        exportDIR = getProperty("EXPORT_HOME");

        if (StrUtil.isEmpty(exportDIR)) {
            exportDIR = WorldConstant.USER_HOME;
        }

        return exportDIR;
    }

    public static  int getMaxItemCount(){
        int maxCount=0;
        String strCount=getProperty("MAX_COUNT");
        if(StrUtil.isNotEmpty(strCount)){
           maxCount=Integer.parseInt(strCount);
        }
        return maxCount;
    }
    ///调拨、报废等工单上允许选择的资产条目数量
    
    public static int getMaxAssetsCount()
    {
    	 int maxCount=0;
         String strCount=getProperty("ASS_CHK_MAX_COUNT");
         if(StrUtil.isNotEmpty(strCount)){
            maxCount=Integer.parseInt(strCount);
         }
         return maxCount;
    	
    }
   
    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = SinoConfig.getProperty("2");
        //System.out.println( ResourceBundle.getBundle("config.app").getString("TD_FLEX_VALUE_SET_NAME") );
        System.out.println(str);
    }
}
