//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.07.26 at 07:26:49 PM CEST 
//


package org.orbisgis.renderer.legend.carto.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.orbisgis.renderer.symbol.collection.persistence.SymbolType;


/**
 * <p>Java class for classified-legend-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="classified-legend-type">
 *   &lt;complexContent>
 *     &lt;extension base="{org.orbisgis.legend}legend-type">
 *       &lt;sequence>
 *         &lt;element name="default-symbol" type="{org.orbisgis.symbol}symbol-type"/>
 *       &lt;/sequence>
 *       &lt;attribute name="default-label" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="field-name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="field-type" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "classified-legend-type", propOrder = {
    "defaultSymbol"
})
public class ClassifiedLegendType
    extends LegendType
{

    @XmlElement(name = "default-symbol", required = true)
    protected SymbolType defaultSymbol;
    @XmlAttribute(name = "default-label", required = true)
    protected String defaultLabel;
    @XmlAttribute(name = "field-name", required = true)
    protected String fieldName;
    @XmlAttribute(name = "field-type", required = true)
    protected int fieldType;

    /**
     * Gets the value of the defaultSymbol property.
     * 
     * @return
     *     possible object is
     *     {@link SymbolType }
     *     
     */
    public SymbolType getDefaultSymbol() {
        return defaultSymbol;
    }

    /**
     * Sets the value of the defaultSymbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link SymbolType }
     *     
     */
    public void setDefaultSymbol(SymbolType value) {
        this.defaultSymbol = value;
    }

    /**
     * Gets the value of the defaultLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultLabel() {
        return defaultLabel;
    }

    /**
     * Sets the value of the defaultLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultLabel(String value) {
        this.defaultLabel = value;
    }

    /**
     * Gets the value of the fieldName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the value of the fieldName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldName(String value) {
        this.fieldName = value;
    }

    /**
     * Gets the value of the fieldType property.
     * 
     */
    public int getFieldType() {
        return fieldType;
    }

    /**
     * Sets the value of the fieldType property.
     * 
     */
    public void setFieldType(int value) {
        this.fieldType = value;
    }

}
