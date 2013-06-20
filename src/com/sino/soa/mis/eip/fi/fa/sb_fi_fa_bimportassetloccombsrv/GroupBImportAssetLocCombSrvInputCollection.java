
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GroupBImportAssetLocCombSrvInputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroupBImportAssetLocCombSrvInputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BImportAssetLocCombSrvInputCollection" type="{http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv}BImportAssetLocCombSrvInputCollection" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupBImportAssetLocCombSrvInputCollection", propOrder = {
    "bImportAssetLocCombSrvInputCollection"
})
public class GroupBImportAssetLocCombSrvInputCollection {

    @XmlElement(name = "BImportAssetLocCombSrvInputCollection")
    protected List<BImportAssetLocCombSrvInputCollection> bImportAssetLocCombSrvInputCollection;

    /**
     * Gets the value of the bImportAssetLocCombSrvInputCollection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bImportAssetLocCombSrvInputCollection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBImportAssetLocCombSrvInputCollection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BImportAssetLocCombSrvInputCollection }
     * 
     * 
     */
    public List<BImportAssetLocCombSrvInputCollection> getBImportAssetLocCombSrvInputCollection() {
        if (bImportAssetLocCombSrvInputCollection == null) {
            bImportAssetLocCombSrvInputCollection = new ArrayList<BImportAssetLocCombSrvInputCollection>();
        }
        return this.bImportAssetLocCombSrvInputCollection;
    }

}
