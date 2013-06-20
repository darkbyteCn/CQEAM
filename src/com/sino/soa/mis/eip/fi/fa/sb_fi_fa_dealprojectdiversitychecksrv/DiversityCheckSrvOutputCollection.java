
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_dealprojectdiversitychecksrv;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiversityCheckSrvOutputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiversityCheckSrvOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DiversityCheckSrvOutputItem" type="{http://eip.zte.com/fi/SB_FI_FA_PageInquiryAssetCustDetailSrv}InquiryAssetCustDetailSrvOutputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiversityCheckSrvOutputCollection", propOrder = {
    "diversityCheckSrvOutputItem"
})
public class DiversityCheckSrvOutputCollection {

    @XmlElement(name = "DiversityCheckSrvOutputItem", nillable = true)
    protected List<DiversityCheckSrvOutputItem> diversityCheckSrvOutputItem;

    /**
     * Gets the value of the diversityCheckSrvOutputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diversityCheckSrvOutputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiversityCheckSrvOutputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DiversityCheckSrvOutputItem }
     * 
     * 
     */
    public List<DiversityCheckSrvOutputItem> getDiversityCheckSrvOutputItem() {
        if (diversityCheckSrvOutputItem == null) {
        	diversityCheckSrvOutputItem = new ArrayList<DiversityCheckSrvOutputItem>();
        }
        return this.diversityCheckSrvOutputItem;
    }

}
