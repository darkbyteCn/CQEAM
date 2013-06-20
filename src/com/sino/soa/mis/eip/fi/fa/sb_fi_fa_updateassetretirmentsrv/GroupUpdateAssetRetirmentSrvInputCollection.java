
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_updateassetretirmentsrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GroupUpdateAssetRetirmentSrvInputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GroupUpdateAssetRetirmentSrvInputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UpdateAssetRetirmentSrvInputCollection" type="{http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv}UpdateAssetRetirmentSrvInputCollection" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupUpdateAssetRetirmentSrvInputCollection", propOrder = {
    "updateAssetRetirmentSrvInputCollection"
})
public class GroupUpdateAssetRetirmentSrvInputCollection {

    @XmlElement(name = "UpdateAssetRetirmentSrvInputCollection")
    protected List<UpdateAssetRetirmentSrvInputCollection> updateAssetRetirmentSrvInputCollection;

    /**
     * Gets the value of the updateAssetRetirmentSrvInputCollection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updateAssetRetirmentSrvInputCollection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdateAssetRetirmentSrvInputCollection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UpdateAssetRetirmentSrvInputCollection }
     * 
     * 
     */
    public List<UpdateAssetRetirmentSrvInputCollection> getUpdateAssetRetirmentSrvInputCollection() {
        if (updateAssetRetirmentSrvInputCollection == null) {
            updateAssetRetirmentSrvInputCollection = new ArrayList<UpdateAssetRetirmentSrvInputCollection>();
        }
        return this.updateAssetRetirmentSrvInputCollection;
    }

}
