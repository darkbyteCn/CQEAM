
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_importassetretirmentsrv;

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
 *         &lt;element name="MsgHeader" type="{http://eip.zte.com/SB_FI_FA_ImportAssetRetirmentSrv}MsgHeader"/>
 *         &lt;element name="ImportAssetRetirmentSrvInputCollection" type="{http://eip.zte.com/SB_FI_FA_ImportAssetRetirmentSrv}ImportAssetRetirmentSrvInputCollection"/>
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
    "importAssetRetirmentSrvInputCollection"
})
@XmlRootElement(name = "ImportAssetRetirmentSrvRequest")
public class ImportAssetRetirmentSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "ImportAssetRetirmentSrvInputCollection", required = true)
    protected ImportAssetRetirmentSrvInputCollection importAssetRetirmentSrvInputCollection;

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
     * Gets the value of the importAssetRetirmentSrvInputCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ImportAssetRetirmentSrvInputCollection }
     *     
     */
    public ImportAssetRetirmentSrvInputCollection getImportAssetRetirmentSrvInputCollection() {
        return importAssetRetirmentSrvInputCollection;
    }

    /**
     * Sets the value of the importAssetRetirmentSrvInputCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportAssetRetirmentSrvInputCollection }
     *     
     */
    public void setImportAssetRetirmentSrvInputCollection(ImportAssetRetirmentSrvInputCollection value) {
        this.importAssetRetirmentSrvInputCollection = value;
    }

}
