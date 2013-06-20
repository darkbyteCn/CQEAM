
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_bimportassetloccombsrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BImportAssetLocCombSrvInputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BImportAssetLocCombSrvInputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BImportAssetLocCombSrvInputItem" type="{http://eip.zte.com/SB_FI_FA_BImportAssetLocCombSrv}BImportAssetLocCombSrvInputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BImportAssetLocCombSrvInputCollection", propOrder = {
    "bImportAssetLocCombSrvInputItem"
})
public class BImportAssetLocCombSrvInputCollection {

    @XmlElement(name = "BImportAssetLocCombSrvInputItem")
    protected List<BImportAssetLocCombSrvInputItem> bImportAssetLocCombSrvInputItem;

    /**
     * Gets the value of the bImportAssetLocCombSrvInputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bImportAssetLocCombSrvInputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBImportAssetLocCombSrvInputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BImportAssetLocCombSrvInputItem }
     * 
     * 
     */
    public List<BImportAssetLocCombSrvInputItem> getBImportAssetLocCombSrvInputItem() {
        if (bImportAssetLocCombSrvInputItem == null) {
            bImportAssetLocCombSrvInputItem = new ArrayList<BImportAssetLocCombSrvInputItem>();
        }
        return this.bImportAssetLocCombSrvInputItem;
    }

}
