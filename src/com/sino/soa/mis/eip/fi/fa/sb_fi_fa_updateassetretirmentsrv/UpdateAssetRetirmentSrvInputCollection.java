
package com.sino.soa.mis.eip.fi.fa.sb_fi_fa_updateassetretirmentsrv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateAssetRetirmentSrvInputCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateAssetRetirmentSrvInputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UpdateAssetRetirmentSrvInputItem" type="{http://eip.zte.com/SB_FI_FA_UpdateAssetRetirmentSrv}UpdateAssetRetirmentSrvInputItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateAssetRetirmentSrvInputCollection", propOrder = {
    "updateAssetRetirmentSrvInputItem"
})
public class UpdateAssetRetirmentSrvInputCollection {

    @XmlElement(name = "UpdateAssetRetirmentSrvInputItem")
    protected List<UpdateAssetRetirmentSrvInputItem> updateAssetRetirmentSrvInputItem;

    /**
     * Gets the value of the updateAssetRetirmentSrvInputItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updateAssetRetirmentSrvInputItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdateAssetRetirmentSrvInputItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UpdateAssetRetirmentSrvInputItem }
     * 
     * 
     */
    public List<UpdateAssetRetirmentSrvInputItem> getUpdateAssetRetirmentSrvInputItem() {
        if (updateAssetRetirmentSrvInputItem == null) {
            updateAssetRetirmentSrvInputItem = new ArrayList<UpdateAssetRetirmentSrvInputItem>();
        }
        return this.updateAssetRetirmentSrvInputItem;
    }

}
