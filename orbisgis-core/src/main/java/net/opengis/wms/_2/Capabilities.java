//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.24 at 12:13:09 PM CEST 
//


package net.opengis.wms._2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.opengis.ows._2.CapabilitiesBaseType;
import net.opengis.ows._2.ContentsBaseType;
import net.opengis.ows._2.OnlineResourceType;
import net.opengis.sld._2.UserDefinedSymbolization;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/ows/2.0}CapabilitiesBaseType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/wms/2.0}ServiceConstraints" minOccurs="0"/>
 *         &lt;element name="Contents" type="{http://www.opengis.net/ows/2.0}ContentsBaseType"/>
 *         &lt;element name="WSDL" type="{http://www.opengis.net/ows/2.0}OnlineResourceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ServiceMetadataURL" type="{http://www.opengis.net/ows/2.0}OnlineResourceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/wms/2.0}_ExtendedCapabilities" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "serviceConstraints",
    "contents",
    "wsdl",
    "serviceMetadataURL",
    "extendedCapabilities"
})
@XmlRootElement(name = "Capabilities")
public class Capabilities
    extends CapabilitiesBaseType
{

    @XmlElement(name = "ServiceConstraints")
    protected ServiceConstraints serviceConstraints;
    @XmlElement(name = "Contents", required = true)
    protected ContentsBaseType contents;
    @XmlElement(name = "WSDL")
    protected List<OnlineResourceType> wsdl;
    @XmlElement(name = "ServiceMetadataURL")
    protected List<OnlineResourceType> serviceMetadataURL;
    @XmlElementRef(name = "_ExtendedCapabilities", namespace = "http://www.opengis.net/wms/2.0", type = JAXBElement.class)
    protected List<JAXBElement<? extends ExtendedCapabilitiesType>> extendedCapabilities;

    /**
     * Metadata about the limitations of this service.
     * 
     * @return
     *     possible object is
     *     {@link ServiceConstraints }
     *     
     */
    public ServiceConstraints getServiceConstraints() {
        return serviceConstraints;
    }

    /**
     * Sets the value of the serviceConstraints property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceConstraints }
     *     
     */
    public void setServiceConstraints(ServiceConstraints value) {
        this.serviceConstraints = value;
    }

    /**
     * Gets the value of the contents property.
     * 
     * @return
     *     possible object is
     *     {@link ContentsBaseType }
     *     
     */
    public ContentsBaseType getContents() {
        return contents;
    }

    /**
     * Sets the value of the contents property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentsBaseType }
     *     
     */
    public void setContents(ContentsBaseType value) {
        this.contents = value;
    }

    /**
     * Gets the value of the wsdl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wsdl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWSDL().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OnlineResourceType }
     * 
     * 
     */
    public List<OnlineResourceType> getWSDL() {
        if (wsdl == null) {
            wsdl = new ArrayList<OnlineResourceType>();
        }
        return this.wsdl;
    }

    /**
     * Gets the value of the serviceMetadataURL property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceMetadataURL property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceMetadataURL().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OnlineResourceType }
     * 
     * 
     */
    public List<OnlineResourceType> getServiceMetadataURL() {
        if (serviceMetadataURL == null) {
            serviceMetadataURL = new ArrayList<OnlineResourceType>();
        }
        return this.serviceMetadataURL;
    }

    /**
     * An element substitutionGroup of _ExtendedCapabilities can be used here Gets the value of the extendedCapabilities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extendedCapabilities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtendedCapabilities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link UserDefinedSymbolization }{@code >}
     * {@link JAXBElement }{@code <}{@link ExtendedCapabilitiesType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends ExtendedCapabilitiesType>> getExtendedCapabilities() {
        if (extendedCapabilities == null) {
            extendedCapabilities = new ArrayList<JAXBElement<? extends ExtendedCapabilitiesType>>();
        }
        return this.extendedCapabilities;
    }

}