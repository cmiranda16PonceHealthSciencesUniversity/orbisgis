/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information. OrbisGIS is
 * distributed under GPL 3 license. It is produced by the "Atelier SIG" team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/> CNRS FR 2488.
 *
 *
 *  Team leader Erwan BOCHER, scientific researcher,
 *
 *  User support leader : Gwendall Petit, geomatic engineer.
 *
 *
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 *
 * Copyright (C) 2010 Erwan BOCHER, Pierre-Yves FADET, Alexis GUEGANNO, Maxence LAURENT
 *
 * This file is part of OrbisGIS.
 *
 * OrbisGIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisGIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 *
 * or contact directly:
 * erwan.bocher _at_ ec-nantes.fr
 * gwendall.petit _at_ ec-nantes.fr
 */


package org.orbisgis.core.renderer.se.fill;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;

import javax.xml.bind.JAXBElement;
import org.gdms.data.SpatialDataSourceDecorator;

import net.opengis.se._2_0.core.ObjectFactory;
import net.opengis.se._2_0.core.SolidFillType;

import org.orbisgis.core.map.MapTransform;
import org.orbisgis.core.renderer.se.SeExceptions.InvalidStyle;

import org.orbisgis.core.renderer.se.parameter.ParameterException;
import org.orbisgis.core.renderer.se.parameter.SeParameterFactory;
import org.orbisgis.core.renderer.se.parameter.color.ColorHelper;
import org.orbisgis.core.renderer.se.parameter.color.ColorLiteral;
import org.orbisgis.core.renderer.se.parameter.color.ColorParameter;
import org.orbisgis.core.renderer.se.parameter.real.RealLiteral;
import org.orbisgis.core.renderer.se.parameter.real.RealParameter;
import org.orbisgis.core.renderer.se.parameter.real.RealParameterContext;

/**
 * A solid fill fills a shape with a solid color (+opacity)
 *
 * @author maxence
 */
public final class SolidFill extends Fill {

	private ColorParameter color;
	private RealParameter opacity;

        /**
         * Default value for opacity : 0.65
         */
        public static final double DEFAULT_OPACITY = 0.65;
        /**
        * Default colour value.
        */
        public static final float GRAY50 = 128.0f;


	/**
	 * fill with random color 65% opaque
	 */
	public SolidFill() {
		this(new ColorLiteral(), new RealLiteral(DEFAULT_OPACITY));
	}

	/**
	 * fill with specified color 60% opaque
	 * @param c
	 */
	public SolidFill(Color c) {
		this(new ColorLiteral(c), new RealLiteral(DEFAULT_OPACITY));
	}

	/**
	 * fill with specified color and opacity
	 * @param c
	 * @param opacity
	 */
	public SolidFill(Color c, double opacity) {
		this(new ColorLiteral(c), new RealLiteral(opacity));
	}

	/**
	 * fill with specified color and opacity
	 * @param c
	 * @param opacity
	 */
	public SolidFill(ColorParameter c, RealParameter opacity) {
		this.setColor(c);
		this.setOpacity(opacity);
	}

	public SolidFill(JAXBElement<SolidFillType> sf) throws InvalidStyle {
		if (sf.getValue().getColor() != null) {
			setColor(SeParameterFactory.createColorParameter(sf.getValue().getColor()));
		}

		if (sf.getValue().getOpacity() != null) {
			setOpacity(SeParameterFactory.createRealParameter(sf.getValue().getOpacity()));
		}
	}

        /**
         * Set the colour value for this SolidFill.
         * @param color 
         */
	public void setColor(ColorParameter color) {
		this.color = color;
	}

        /**
         * Get the current colour value for this SolidFill.
         */
	public ColorParameter getColor() {
		return color;
	}

        /**
         * Set the opacity value for this SolidFill.
         * @param opacity 
         */
	public void setOpacity(RealParameter opacity) {
		this.opacity = opacity;

		if (opacity != null) {
			this.opacity.setContext(RealParameterContext.percentageContext);
		}
	}

        /**
         * Get the current opacity associated to this SolidFill.
         * @return 
         */
	public RealParameter getOpacity() {
		return opacity;
	}

    /**
     * Return a Java Color according to this SE Solid Fill
     * @param fid
     * @param sds
     * @param selected
     * @param mt
     * @return A java.awt.Color
     * @throws ParameterException
     */
	@Override
	public Paint getPaint(long fid, SpatialDataSourceDecorator sds, boolean selected, MapTransform mt) throws ParameterException {

        Color c, ac; // ac stands 4 colour + alpha channel

		if (color != null){
			c = color.getColor(sds, fid);
		} else {
                        //We must cast the colours to int values, because we want to use 
                        //GRAY50 to build RGB value - As it equals 128.0f, we need a cast
                        //because Color(float, float, float) needs values between 0 and 1.
		    c = new Color((int) GRAY50, (int) GRAY50, (int) GRAY50);
        }

		if (this.opacity != null) {
		    ac = ColorHelper.getColorWithAlpha(c, this.opacity.getValue(sds, fid));
		} else {
            ac = c;
        }

		// Add opacity to the color


		if (selected) {
			ac = ColorHelper.invert(ac);
		}

		return ac;
	}

	@Override
	public void draw(Graphics2D g2, SpatialDataSourceDecorator sds, long fid, Shape shp, boolean selected, MapTransform mt) throws ParameterException {
		g2.setPaint(getPaint(fid, sds, selected, mt));
		g2.fill(shp);
	}

	@Override
	public String toString() {
		return "Color: " + color + " alpha: " + opacity;
	}

	@Override
	public String dependsOnFeature() {
                String c = "";
                if (color != null) {
                    c = color.dependsOnFeature();
                }

                String o = "";
                if (opacity != null) {
                    o = opacity.dependsOnFeature();
                }
                return (c + " " + o).trim();
	}

	@Override
	public SolidFillType getJAXBType() {
		SolidFillType f = new SolidFillType();

		if (color != null) {
			f.setColor(color.getJAXBParameterValueType());
		}
		if (opacity != null) {
			f.setOpacity(opacity.getJAXBParameterValueType());
		}

		return f;
	}

	@Override
	public JAXBElement<SolidFillType> getJAXBElement() {
		ObjectFactory of = new ObjectFactory();
		return of.createSolidFill(this.getJAXBType());
	}
}