
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_importassetlocationcombinationsrv;

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
 *         &lt;element name="MsgHeader" type="{http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv}MsgHeader"/>
 *         &lt;element name="ImportAssetLocationCombinationSrvInputCollection" type="{http://eip.zte.com/SB_FI_FA_ImportAssetLocationCombinationSrv}ImportAssetLocationCombinationSrvInputCollection"/>
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
    "importAssetLocationCombinationSrvInputCollection"
})
@XmlRootElement(name = "ImportAssetLocationCombinationSrvRequest")
public class ImportAssetLocationCombinationSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "ImportAssetLocationCombinationSrvInputCollection", required = true)
    protected ImportAssetLocationCombinationSrvInputCollection importAssetLocationCombinationSrvInputCollection;

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
     * Gets the value of the importAssetLocationCombinationSrvInputCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ImportAssetLocationCombinationSrvInputCollection }
     *     
     */
    public ImportAssetLocationCombinationSrvInputCollection getImportAssetLocationCombinationSrvInputCollection() {
        return importAssetLocationCombinationSrvInputCollection;
    }

    /**
     * Sets the value of the importAssetLocationCombinationSrvInputCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportAssetLocationCombinationSrvInputCollection }
     *     
     */
    public void setImportAssetLocationCombinationSrvInputCollection(ImportAssetLocationCombinationSrvInputCollection value) {
        this.importAssetLocationCombinationSrvInputCollection = value;
    }

}
