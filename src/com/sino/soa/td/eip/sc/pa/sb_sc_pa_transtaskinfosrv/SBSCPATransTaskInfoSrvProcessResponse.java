
package com.sino.soa.td.eip.sc.pa.sb_sc_pa_transtaskinfosrv;

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
 *         &lt;element name="ERROR_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="INSTANCE_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SESSION_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ERROR_MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "errorflag",
    "instanceid",
    "sessionnum",
    "errormessage"
})
@XmlRootElement(name = "SB_SC_PA_TransTaskInfoSrvProcessResponse")
public class SBSCPATransTaskInfoSrvProcessResponse {

    @XmlElement(name = "ERROR_FLAG", required = true)
    protected String errorflag;
    @XmlElement(name = "INSTANCE_ID", required = true)
    protected String instanceid;
    @XmlElement(name = "SESSION_NUM", required = true)
    protected String sessionnum;
    @XmlElement(name = "ERROR_MESSAGE", required = true)
    protected String errormessage;

    /**
     * Gets the value of the errorflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERRORFLAG() {
        return errorflag;
    }

    /**
     * Sets the value of the errorflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERRORFLAG(String value) {
        this.errorflag = value;
    }

    /**
     * Gets the value of the instanceid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINSTANCEID() {
        return instanceid;
    }

    /**
     * Sets the value of the instanceid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINSTANCEID(String value) {
        this.instanceid = value;
    }

    /**
     * Gets the value of the sessionnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSESSIONNUM() {
        return sessionnum;
    }

    /**
     * Sets the value of the sessionnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSESSIONNUM(String value) {
        this.sessionnum = value;
    }

    /**
     * Gets the value of the errormessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERRORMESSAGE() {
        return errormessage;
    }

    /**
     * Sets the value of the errormessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERRORMESSAGE(String value) {
        this.errormessage = value;
    }

}
