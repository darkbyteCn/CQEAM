
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.createassettransferintercompanyssrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.sino.soa.mis.eip.fi.fa.sb_fi_fa_createassettransferintercompanyssrv.msgheader.MsgHeader;


/**
 * <p>Java class for CreateAssetTransferIntercompanysSrvRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateAssetTransferIntercompanysSrvRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgHeader" type="{http://eip.zte.com/common/MsgHeader}MsgHeader"/>
 *         &lt;element name="CreateAssetTransferIntercompanysSrvInputCollection" type="{http://eip.zte.com/common/fa/CreateAssetTransferIntercompanysSrv}CreateAssetTransferIntercompanysSrvInputCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateAssetTransferIntercompanysSrvRequest", propOrder = {
    "msgHeader",
    "createAssetTransferIntercompanysSrvInputCollection"
})
public class CreateAssetTransferIntercompanysSrvRequest {

    @XmlElement(name = "MsgHeader", required = true)
    protected MsgHeader msgHeader;
    @XmlElement(name = "CreateAssetTransferIntercompanysSrvInputCollection", required = true)
    protected CreateAssetTransferIntercompanysSrvInputCollection createAssetTransferIntercompanysSrvInputCollection;

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
     * Gets the value of the createAssetTransferIntercompanysSrvInputCollection property.
     * 
     * @return
     *     possible object is
     *     {@link CreateAssetTransferIntercompanysSrvInputCollection }
     *     
     */
    public CreateAssetTransferIntercompanysSrvInputCollection getCreateAssetTransferIntercompanysSrvInputCollection() {
        return createAssetTransferIntercompanysSrvInputCollection;
    }

    /**
     * Sets the value of the createAssetTransferIntercompanysSrvInputCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateAssetTransferIntercompanysSrvInputCollection }
     *     
     */
    public void setCreateAssetTransferIntercompanysSrvInputCollection(CreateAssetTransferIntercompanysSrvInputCollection value) {
        this.createAssetTransferIntercompanysSrvInputCollection = value;
    }

}
