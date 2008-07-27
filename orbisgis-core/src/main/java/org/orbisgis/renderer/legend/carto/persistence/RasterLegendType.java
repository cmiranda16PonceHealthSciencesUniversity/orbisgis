//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.07.26 at 07:26:49 PM CEST 
//


package org.orbisgis.renderer.legend.carto.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for raster-legend-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="raster-legend-type">
 *   &lt;complexContent>
 *     &lt;extension base="{org.orbisgis.legend}legend-type">
 *       &lt;sequence>
 *         &lt;element name="color-model-component" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="opacity" use="required" type="{http://www.w3.org/2001/XMLSchema}float" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "raster-legend-type", propOrder = {
    "colorModelComponent"
})
public class RasterLegendType
    extends LegendType
{

    @XmlElement(name = "color-model-component", type = Integer.class)
    protected List<Integer> colorModelComponent;
    @XmlAttribute(required = true)
    protected float opacity;

    /**
     * Gets the value of the colorModelComponent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the colorModelComponent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getColorModelComponent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getColorModelComponent() {
        if (colorModelComponent == null) {
            colorModelComponent = new ArrayList<Integer>();
        }
        return this.colorModelComponent;
    }

    /**
     * Gets the value of the opacity property.
     * 
     */
    public float getOpacity() {
        return opacity;
    }

    /**
     * Sets the value of the opacity property.
     * 
     */
    public void setOpacity(float value) {
        this.opacity = value;
    }

}
