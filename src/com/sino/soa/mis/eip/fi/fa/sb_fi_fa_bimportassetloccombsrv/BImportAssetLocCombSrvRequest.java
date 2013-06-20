
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv}MsgHeader"/>
 *         &lt;element name="BImportAssetLocCombSrvInputCollection" type="{http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv}BImportAssetLocCombSrvInputCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "msgHeader",
    "bImportAssetLocCombSrvInputCollection"
})
@XmlRootElement(name = "BImportAssetLocCombSrvRequest")
public class BImportAssetLocCombSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "BImportAssetLocCombSrvInputCollection", required = true)
    protected BImportAssetLocCombSrvInputCollection bImportAssetLocCombSrvInputCollection;

    /**
     * Gets the value of the msgHeader property.
     * 
     * @return
     *     possible object is
     *     {@link MsgHeader }
     *     
     */
    public MsgHeader getMsgHeader() {
        return msgHeader;
    }

    /**
     * Sets the value of the msgHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link MsgHeader }
     *     
     */
    public void setMsgHeader(MsgHeader value) {
        this.msgHeader = value;
    }

    /**
     * Gets the value of the bImportAssetLocCombSrvInputCollection property.
     * 
     * @return
     *     possible object is
     *     {@link BImportAssetLocCombSrvInputCollection }
     *     
     */
    public BImportAssetLocCombSrvInputCollection getBImportAssetLocCombSrvInputCollection() {
        return bImportAssetLocCombSrvInputCollection;
    }

    /**
     * Sets the value of the bImportAssetLocCombSrvInputCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link BImportAssetLocCombSrvInputCollection }
     *     
     */
    public void setBImportAssetLocCombSrvInputCollection(BImportAssetLocCombSrvInputCollection value) {
        this.bImportAssetLocCombSrvInputCollection = value;
    }

}
