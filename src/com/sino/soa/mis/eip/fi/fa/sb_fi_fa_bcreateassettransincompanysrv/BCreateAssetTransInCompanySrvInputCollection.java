
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bcreateassettransincompanysrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BCreateAssetTransInCompanySrvInputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BCreateAssetTransInCompanySrvInputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BCreateAssetTransInCompanySrvInputItem" type="{http://eip.zte.com/SB_FI_FA_BCreateAssetTransInCompanySrv}BCreateAssetTransInCompanySrvInputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BCreateAssetTransInCompanySrvInputCollection", propOrder = {
    "bCreateAssetTransInCompanySrvInputItem"
})
public class BCreateAssetTransInCompanySrvInputCollection {

    @XmlElement(name = "BCreateAssetTransInCompanySrvInputItem")
    protected List<BCreateAssetTransInCompanySrvInputItem> bCreateAssetTransInCompanySrvInputItem;

    /**
     * Gets the value of the bCreateAssetTransInCompanySrvInputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bCreateAssetTransInCompanySrvInputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBCreateAssetTransInCompanySrvInputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BCreateAssetTransInCompanySrvInputItem }
     * 
     * 
     */
    public List<BCreateAssetTransInCompanySrvInputItem> getBCreateAssetTransInCompanySrvInputItem() {
        if (bCreateAssetTransInCompanySrvInputItem == null) {
            bCreateAssetTransInCompanySrvInputItem = new ArrayList<BCreateAssetTransInCompanySrvInputItem>();
        }
        return this.bCreateAssetTransInCompanySrvInputItem;
    }

}
