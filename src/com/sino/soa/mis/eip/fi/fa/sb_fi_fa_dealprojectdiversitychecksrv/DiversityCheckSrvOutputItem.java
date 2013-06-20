
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_dealprojectdiversitychecksrv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DiversityCheckSrvOutputItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiversityCheckSrvOutputItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BOOK_TYPE_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJRCT_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TASK_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TASK_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PROJECT_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TAG_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_DESCRIPTION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_CATEGORY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_CATEGORY_DESC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_LOCATION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_UNITS" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="UNIT_OF_MEASURE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CAPITALIZED_COST" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="CAPITALIZED_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ASSET_KEY_CCID_DESC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ASSET_EMPLOYEE" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="EMPLOYEE_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EMPLOYEE_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DEPRECIATION_EXPENSE_CCID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="MODEL_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MANUFACTORER_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DATE_PLACED_IN_SERVICE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FA_PERIOD_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LOCATION_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LOCATION_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TASK_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PROJECT_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PROJECT_ASSET_ID" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ATTRIBUTE4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE6" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE7" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiversityCheckSrvOutputItem", propOrder = {
    "booktypecode",
    "projrctnumber",
    "tasknumber",
    "taskname",
    "projecttype",
    "tagnumber",
    "assetname",
    "assetdescription",
    "assetnumber",
    "assetcategory",
    "assetcategorydesc",
    "assetlocation",
    "assetunits",
    "unitofmeasure",
    "capitalizedcost",
    "capitalizeddate",
    "assetkeycciddesc",
    "assetemployee",
    "employeename",
    "employeenumber",
    "depreciationexpenseccid",
    "modelnumber",
    "manufactorername",
    "dateplacedinservice",
    "faperiodname",
    "locationid",
    "locationcode",
    "taskid",
    "projectid",
    "projectassetid",
    "attribute4",
    "attribute5",
    "attribute6",
    "attribute7"
})
public class DiversityCheckSrvOutputItem {

    @XmlElement(name = "BOOK_TYPE_CODE", required = true, nillable = true)
    protected String booktypecode;
    @XmlElement(name = "PROJRCT_NUMBER", required = true, nillable = true)
    protected String projrctnumber;
    @XmlElement(name = "TASK_NUMBER", required = true, nillable = true)
    protected String tasknumber;
    @XmlElement(name = "TASK_NAME", required = true, nillable = true)
    protected String taskname;
    @XmlElement(name = "PROJECT_TYPE", required = true, nillable = true)
    protected String projecttype;
    @XmlElement(name = "TAG_NUMBER", required = true, nillable = true)
    protected String tagnumber;
    @XmlElement(name = "ASSET_NAME", required = true, nillable = true)
    protected String assetname;
    @XmlElement(name = "ASSET_DESCRIPTION", required = true, nillable = true)
    protected String assetdescription;
    @XmlElement(name = "ASSET_NUMBER", required = true, nillable = true)
    protected String assetnumber;
    @XmlElement(name = "ASSET_CATEGORY", required = true, nillable = true)
    protected String assetcategory;
    @XmlElement(name = "ASSET_CATEGORY_DESC", required = true, nillable = true)
    protected String assetcategorydesc;
    @XmlElement(name = "ASSET_LOCATION", required = true, nillable = true)
    protected String assetlocation;
    @XmlElement(name = "ASSET_UNITS", required = true, nillable = true)
    protected BigDecimal assetunits;
    @XmlElement(name = "UNIT_OF_MEASURE", required = true, nillable = true)
    protected String unitofmeasure;
    @XmlElement(name = "CAPITALIZED_COST", required = true, nillable = true)
    protected BigDecimal capitalizedcost;
    @XmlElement(name = "CAPITALIZED_DATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar capitalizeddate;
    @XmlElement(name = "ASSET_KEY_CCID_DESC", required = true, nillable = true)
    protected String assetkeycciddesc;
    @XmlElement(name = "ASSET_EMPLOYEE", required = true, nillable = true)
    protected BigDecimal assetemployee;
    @XmlElement(name = "EMPLOYEE_NAME", required = true, nillable = true)
    protected String employeename;
    @XmlElement(name = "EMPLOYEE_NUMBER", required = true, nillable = true)
    protected String employeenumber;
    @XmlElement(name = "DEPRECIATION_EXPENSE_CCID", required = true, nillable = true)
    protected BigDecimal depreciationexpenseccid;
    @XmlElement(name = "MODEL_NUMBER", required = true, nillable = true)
    protected String modelnumber;
    @XmlElement(name = "MANUFACTORER_NAME", required = true, nillable = true)
    protected String manufactorername;
    @XmlElement(name = "DATE_PLACED_IN_SERVICE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateplacedinservice;
    @XmlElement(name = "FA_PERIOD_NAME", required = true, nillable = true)
    protected String faperiodname;
    @XmlElement(name = "LOCATION_ID", required = true, nillable = true)
    protected BigDecimal locationid;
    @XmlElement(name = "LOCATION_CODE", required = true, nillable = true)
    protected String locationcode;
    @XmlElement(name = "TASK_ID", required = true, nillable = true)
    protected BigDecimal taskid;
    @XmlElement(name = "PROJECT_ID", required = true, nillable = true)
    protected BigDecimal projectid;
    @XmlElement(name = "PROJECT_ASSET_ID", required = true, nillable = true)
    protected BigDecimal projectassetid;
    @XmlElement(name = "ATTRIBUTE4", required = true, nillable = true)
    protected String attribute4;
    @XmlElement(name = "ATTRIBUTE5", required = true, nillable = true)
    protected String attribute5;
    @XmlElement(name = "ATTRIBUTE6", required = true, nillable = true)
    protected String attribute6;
    @XmlElement(name = "ATTRIBUTE7", required = true, nillable = true)
    protected String attribute7;

    /**
     * Gets the value of the booktypecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBOOKTYPECODE() {
        return booktypecode;
    }

    /**
     * Sets the value of the booktypecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBOOKTYPECODE(String value) {
        this.booktypecode = value;
    }

    /**
     * Gets the value of the projrctnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROJRCTNUMBER() {
        return projrctnumber;
    }

    /**
     * Sets the value of the projrctnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROJRCTNUMBER(String value) {
        this.projrctnumber = value;
    }

    /**
     * Gets the value of the tasknumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTASKNUMBER() {
        return tasknumber;
    }

    /**
     * Sets the value of the tasknumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTASKNUMBER(String value) {
        this.tasknumber = value;
    }

    /**
     * Gets the value of the taskname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTASKNAME() {
        return taskname;
    }

    /**
     * Sets the value of the taskname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTASKNAME(String value) {
        this.taskname = value;
    }

    /**
     * Gets the value of the projecttype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROJECTTYPE() {
        return projecttype;
    }

    /**
     * Sets the value of the projecttype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROJECTTYPE(String value) {
        this.projecttype = value;
    }

    /**
     * Gets the value of the tagnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTAGNUMBER() {
        return tagnumber;
    }

    /**
     * Sets the value of the tagnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTAGNUMBER(String value) {
        this.tagnumber = value;
    }

    /**
     * Gets the value of the assetname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSETNAME() {
        return assetname;
    }

    /**
     * Sets the value of the assetname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSETNAME(String value) {
        this.assetname = value;
    }

    /**
     * Gets the value of the assetdescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSETDESCRIPTION() {
        return assetdescription;
    }

    /**
     * Sets the value of the assetdescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSETDESCRIPTION(String value) {
        this.assetdescription = value;
    }

    /**
     * Gets the value of the assetnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSETNUMBER() {
        return assetnumber;
    }

    /**
     * Sets the value of the assetnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSETNUMBER(String value) {
        this.assetnumber = value;
    }

    /**
     * Gets the value of the assetcategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSETCATEGORY() {
        return assetcategory;
    }

    /**
     * Sets the value of the assetcategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSETCATEGORY(String value) {
        this.assetcategory = value;
    }

    /**
     * Gets the value of the assetcategorydesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSETCATEGORYDESC() {
        return assetcategorydesc;
    }

    /**
     * Sets the value of the assetcategorydesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSETCATEGORYDESC(String value) {
        this.assetcategorydesc = value;
    }

    /**
     * Gets the value of the assetlocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSETLOCATION() {
        return assetlocation;
    }

    /**
     * Sets the value of the assetlocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSETLOCATION(String value) {
        this.assetlocation = value;
    }

    /**
     * Gets the value of the assetunits property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getASSETUNITS() {
        return assetunits;
    }

    /**
     * Sets the value of the assetunits property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setASSETUNITS(BigDecimal value) {
        this.assetunits = value;
    }

    /**
     * Gets the value of the unitofmeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITOFMEASURE() {
        return unitofmeasure;
    }

    /**
     * Sets the value of the unitofmeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITOFMEASURE(String value) {
        this.unitofmeasure = value;
    }

    /**
     * Gets the value of the capitalizedcost property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCAPITALIZEDCOST() {
        return capitalizedcost;
    }

    /**
     * Sets the value of the capitalizedcost property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCAPITALIZEDCOST(BigDecimal value) {
        this.capitalizedcost = value;
    }

    /**
     * Gets the value of the capitalizeddate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCAPITALIZEDDATE() {
        return capitalizeddate;
    }

    /**
     * Sets the value of the capitalizeddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCAPITALIZEDDATE(XMLGregorianCalendar value) {
        this.capitalizeddate = value;
    }

    /**
     * Gets the value of the assetkeycciddesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASSETKEYCCIDDESC() {
        return assetkeycciddesc;
    }

    /**
     * Sets the value of the assetkeycciddesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASSETKEYCCIDDESC(String value) {
        this.assetkeycciddesc = value;
    }

    /**
     * Gets the value of the assetemployee property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getASSETEMPLOYEE() {
        return assetemployee;
    }

    /**
     * Sets the value of the assetemployee property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setASSETEMPLOYEE(BigDecimal value) {
        this.assetemployee = value;
    }

    /**
     * Gets the value of the employeename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPLOYEENAME() {
        return employeename;
    }

    /**
     * Sets the value of the employeename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPLOYEENAME(String value) {
        this.employeename = value;
    }

    /**
     * Gets the value of the employeenumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPLOYEENUMBER() {
        return employeenumber;
    }

    /**
     * Sets the value of the employeenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPLOYEENUMBER(String value) {
        this.employeenumber = value;
    }

    /**
     * Gets the value of the depreciationexpenseccid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEPRECIATIONEXPENSECCID() {
        return depreciationexpenseccid;
    }

    /**
     * Sets the value of the depreciationexpenseccid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEPRECIATIONEXPENSECCID(BigDecimal value) {
        this.depreciationexpenseccid = value;
    }

    /**
     * Gets the value of the modelnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODELNUMBER() {
        return modelnumber;
    }

    /**
     * Sets the value of the modelnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODELNUMBER(String value) {
        this.modelnumber = value;
    }

    /**
     * Gets the value of the manufactorername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMANUFACTORERNAME() {
        return manufactorername;
    }

    /**
     * Sets the value of the manufactorername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMANUFACTORERNAME(String value) {
        this.manufactorername = value;
    }

    /**
     * Gets the value of the dateplacedinservice property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATEPLACEDINSERVICE() {
        return dateplacedinservice;
    }

    /**
     * Sets the value of the dateplacedinservice property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATEPLACEDINSERVICE(XMLGregorianCalendar value) {
        this.dateplacedinservice = value;
    }

    /**
     * Gets the value of the faperiodname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAPERIODNAME() {
        return faperiodname;
    }

    /**
     * Sets the value of the faperiodname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAPERIODNAME(String value) {
        this.faperiodname = value;
    }

    /**
     * Gets the value of the locationid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLOCATIONID() {
        return locationid;
    }

    /**
     * Sets the value of the locationid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLOCATIONID(BigDecimal value) {
        this.locationid = value;
    }

    /**
     * Gets the value of the locationcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOCATIONCODE() {
        return locationcode;
    }

    /**
     * Sets the value of the locationcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOCATIONCODE(String value) {
        this.locationcode = value;
    }

    /**
     * Gets the value of the taskid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTASKID() {
        return taskid;
    }

    /**
     * Sets the value of the taskid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTASKID(BigDecimal value) {
        this.taskid = value;
    }

    /**
     * Gets the value of the projectid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPROJECTID() {
        return projectid;
    }

    /**
     * Sets the value of the projectid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPROJECTID(BigDecimal value) {
        this.projectid = value;
    }

    /**
     * Gets the value of the projectassetid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPROJECTASSETID() {
        return projectassetid;
    }

    /**
     * Sets the value of the projectassetid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPROJECTASSETID(BigDecimal value) {
        this.projectassetid = value;
    }

    /**
     * Gets the value of the attribute4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE4() {
        return attribute4;
    }

    /**
     * Sets the value of the attribute4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE4(String value) {
        this.attribute4 = value;
    }

    /**
     * Gets the value of the attribute5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE5() {
        return attribute5;
    }

    /**
     * Sets the value of the attribute5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE5(String value) {
        this.attribute5 = value;
    }

    /**
     * Gets the value of the attribute6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE6() {
        return attribute6;
    }

    /**
     * Sets the value of the attribute6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE6(String value) {
        this.attribute6 = value;
    }

    /**
     * Gets the value of the attribute7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE7() {
        return attribute7;
    }

    /**
     * Sets the value of the attribute7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE7(String value) {
        this.attribute7 = value;
    }

}
