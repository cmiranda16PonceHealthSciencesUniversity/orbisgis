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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for label-legend-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="label-legend-type">
 *   &lt;complexContent>
 *     &lt;extension base="{org.orbisgis.legend}legend-type">
 *       &lt;attribute name="font-size" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="field-font-size" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="field-name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "label-legend-type")
public class LabelLegendType
    extends LegendType
{

    @XmlAttribute(name = "font-size", required = true)
    protected int fontSize;
    @XmlAttribute(name = "field-font-size")
    protected String fieldFontSize;
    @XmlAttribute(name = "field-name", required = true)
    protected String fieldName;

    /**
     * Gets the value of the fontSize property.
     * 
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * Sets the value of the fontSize property.
     * 
     */
    public void setFontSize(int value) {
        this.fontSize = value;
    }

    /**
     * Gets the value of the fieldFontSize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldFontSize() {
        return fieldFontSize;
    }

    /**
     * Sets the value of the fieldFontSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldFontSize(String value) {
        this.fieldFontSize = value;
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

}
