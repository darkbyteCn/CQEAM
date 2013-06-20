
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_transmetacustdetailsrv;

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
 *         &lt;element name="PROJECT_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TASK_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CAPITALIZED_DATE_FROM" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CAPITALIZED_DATE_TO" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
    "projectnumber",
    "tasknum",
    "capitalizeddatefrom",
    "capitalizeddateto"
})
@XmlRootElement(name = "SB_FI_FA_TransMetaCustDetailSrvProcessRequest")
public class SBFIFATransMetaCustDetailSrvProcessRequest {

    @XmlElement(name = "ENV_CODE", required = true, nillable = true)
    protected String envcode;
    @XmlElement(name = "PROJECT_NUMBER", required = true, nillable = true)
    protected String projectnumber;
    @XmlElement(name = "TASK_NUM", required = true, nillable = true)
    protected String tasknum;
    @XmlElement(name = "CAPITALIZED_DATE_FROM", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar capitalizeddatefrom;
    @XmlElement(name = "CAPITALIZED_DATE_TO", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar capitalizeddateto;

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
     * Gets the value of the projectnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROJECTNUMBER() {
        return projectnumber;
    }

    /**
     * Sets the value of the projectnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROJECTNUMBER(String value) {
        this.projectnumber = value;
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
     * Gets the value of the capitalizeddatefrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCAPITALIZEDDATEFROM() {
        return capitalizeddatefrom;
    }

    /**
     * Sets the value of the capitalizeddatefrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCAPITALIZEDDATEFROM(XMLGregorianCalendar value) {
        this.capitalizeddatefrom = value;
    }

    /**
     * Gets the value of the capitalizeddateto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCAPITALIZEDDATETO() {
        return capitalizeddateto;
    }

    /**
     * Sets the value of the capitalizeddateto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCAPITALIZEDDATETO(XMLGregorianCalendar value) {
        this.capitalizeddateto = value;
    }

}
