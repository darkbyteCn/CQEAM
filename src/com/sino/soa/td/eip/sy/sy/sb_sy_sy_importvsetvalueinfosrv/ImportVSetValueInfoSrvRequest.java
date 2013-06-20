
package com.sino.soa.td.eip.sy.sy.sb_sy_sy_importvsetvalueinfosrv;

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
 *         &lt;element name="MsgHeader" type="{http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv}MsgHeader"/>
 *         &lt;element name="ImportVSetValueInfoSrvInputCollection" type="{http://xmlns.oracle.com/SB_SY_SY_ImportVSetValueInfoSrv}ImportVSetValueInfoSrvInputCollection"/>
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
    "importVSetValueInfoSrvInputCollection"
})
@XmlRootElement(name = "ImportVSetValueInfoSrvRequest")
public class ImportVSetValueInfoSrvRequest {

    @XmlElement(name = "MsgHeader", required = true, nillable = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "ImportVSetValueInfoSrvInputCollection", required = true, nillable = true)
    protected ImportVSetValueInfoSrvInputCollection importVSetValueInfoSrvInputCollection;

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
     * Gets the value of the importVSetValueInfoSrvInputCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ImportVSetValueInfoSrvInputCollection }
     *     
     */
    public ImportVSetValueInfoSrvInputCollection getImportVSetValueInfoSrvInputCollection() {
        return importVSetValueInfoSrvInputCollection;
    }

    /**
     * Sets the value of the importVSetValueInfoSrvInputCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportVSetValueInfoSrvInputCollection }
     *     
     */
    public void setImportVSetValueInfoSrvInputCollection(ImportVSetValueInfoSrvInputCollection value) {
        this.importVSetValueInfoSrvInputCollection = value;
    }

}
