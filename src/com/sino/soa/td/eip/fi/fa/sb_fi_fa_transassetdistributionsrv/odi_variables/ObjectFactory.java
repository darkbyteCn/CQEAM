
package com.sino.soa.td.eip.fi.fa.sb_fi_fa_transassetdistributionsrv.odi_variables;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sino.soa.td.eip.fi.fa.sb_fi_fa_transassetdistributionsrv.odi_variables package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ODIVariables_QNAME = new QName("http://eip.zte.com/odi/ODI_Variables", "ODI_Variables");
    private final static QName _ODIVariablesType_QNAME = new QName("http://eip.zte.com/odi/ODI_Variables", "ODI_Variables_type");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sino.soa.td.eip.fi.fa.sb_fi_fa_transassetdistributionsrv.odi_variables
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ODIVariables }
     * 
     */
    public ODIVariables createODIVariables() {
        return new ODIVariables();
    }

    /**
     * Create an instance of {@link ODIVariablesType }
     * 
     */
    public ODIVariablesType createODIVariablesType() {
        return new ODIVariablesType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ODIVariables }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/odi/ODI_Variables", name = "ODI_Variables")
    public JAXBElement<ODIVariables> createODIVariables(ODIVariables value) {
        return new JAXBElement<ODIVariables>(_ODIVariables_QNAME, ODIVariables.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ODIVariablesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eip.zte.com/odi/ODI_Variables", name = "ODI_Variables_type")
    public JAXBElement<ODIVariablesType> createODIVariablesType(ODIVariablesType value) {
        return new JAXBElement<ODIVariablesType>(_ODIVariablesType_QNAME, ODIVariablesType.class, null, value);
    }

}
