
package com.sino.soa.td.eip.sc.pa.sb_sc_pa_transtaskinfosrv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="ENV_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJECT_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TASK_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="START_LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="END_LAST_UPDATE_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
    "envcode",
    "projectnum",
    "tasknum",
    "startlastupdatedate",
    "endlastupdatedate"
})
@XmlRootElement(name = "SB_SC_PA_TransTaskInfoSrvProcessRequest")
public class SBSCPATransTaskInfoSrvProcessRequest {

    @XmlElement(name = "ENV_CODE", required = true, nillable = true)
    protected String envcode;
    @XmlElement(name = "PROJECT_NUM", required = true, nillable = true)
    protected String projectnum;
    @XmlElement(name = "TASK_NUM", required = true, nillable = true)
    protected String tasknum;
    @XmlElement(name = "START_LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startlastupdatedate;
    @XmlElement(name = "END_LAST_UPDATE_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endlastupdatedate;

    /**
     * Gets the value of the envcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getENVCODE() {
        return envcode;
    }

    /**
     * Sets the value of the envcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setENVCODE(String value) {
        this.envcode = value;
    }

    /**
     * Gets the value of the projectnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROJECTNUM() {
        return projectnum;
    }

    /**
     * Sets the value of the projectnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROJECTNUM(String value) {
        this.projectnum = value;
    }

    /**
     * Gets the value of the tasknum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTASKNUM() {
        return tasknum;
    }

    /**
     * Sets the value of the tasknum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTASKNUM(String value) {
        this.tasknum = value;
    }

    /**
     * Gets the value of the startlastupdatedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSTARTLASTUPDATEDATE() {
        return startlastupdatedate;
    }

    /**
     * Sets the value of the startlastupdatedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSTARTLASTUPDATEDATE(XMLGregorianCalendar value) {
        this.startlastupdatedate = value;
    }

    /**
     * Gets the value of the endlastupdatedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getENDLASTUPDATEDATE() {
        return endlastupdatedate;
    }

    /**
     * Sets the value of the endlastupdatedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setENDLASTUPDATEDATE(XMLGregorianCalendar value) {
        this.endlastupdatedate = value;
    }

}
