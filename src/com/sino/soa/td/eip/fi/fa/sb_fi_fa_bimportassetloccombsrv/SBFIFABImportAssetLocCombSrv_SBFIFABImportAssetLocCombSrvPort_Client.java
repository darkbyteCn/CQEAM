
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 2.1.4
 * Mon Sep 26 14:59:06 CST 2011
 * Generated source version: 2.1.4
 * 
 */

public final class SBFIFABImportAssetLocCombSrv_SBFIFABImportAssetLocCombSrvPort_Client {

    private static final QName SERVICE_NAME = new QName("http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv", "SB_FI_FA_TDBImportAssetLocCombSrv");

    private SBFIFABImportAssetLocCombSrv_SBFIFABImportAssetLocCombSrvPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = SBFIFATDBImportAssetLocCombSrv.WSDL_LOCATION;
        if (args.length > 0) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        SBFIFATDBImportAssetLocCombSrv ss = new SBFIFATDBImportAssetLocCombSrv(wsdlURL, SERVICE_NAME);
        SBFIFABImportAssetLocCombSrv port = ss.getSBFIFABImportAssetLocCombSrvPort();  
        
        {
        System.out.println("Invoking process...");
        com.sino.soa.td.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv.BImportAssetLocCombSrvRequest _process_payload = null;
        com.sino.soa.td.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv.BImportAssetLocCombSrvResponse _process__return = port.process(_process_payload);
        System.out.println("process.result=" + _process__return);


        }

        System.exit(0);
    }

}
