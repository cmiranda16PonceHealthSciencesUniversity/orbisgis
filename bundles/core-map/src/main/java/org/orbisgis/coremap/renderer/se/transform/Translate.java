/**
 * OrbisGIS is a java GIS application dedicated to research in GIScience.
 * OrbisGIS is developed by the GIS group of the DECIDE team of the 
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 * 
 * OrbisGIS is distributed under GPL 3 license.
 *
 * Copyright (C) 2007-2014 CNRS (IRSTV FR CNRS 2488)
 * Copyright (C) 2015-2017 CNRS (Lab-STICC UMR CNRS 6285)
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
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.coremap.renderer.se.transform;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import net.opengis.se._2_0.core.ObjectFactory;
import net.opengis.se._2_0.core.TranslateType;

import org.orbisgis.coremap.map.MapTransform;
import org.orbisgis.coremap.renderer.se.AbstractSymbolizerNode;
import org.orbisgis.coremap.renderer.se.SeExceptions.InvalidStyle;
import org.orbisgis.coremap.renderer.se.SymbolizerNode;
import org.orbisgis.coremap.renderer.se.common.Uom;
import org.orbisgis.coremap.renderer.se.parameter.ParameterException;
import org.orbisgis.coremap.renderer.se.parameter.SeParameterFactory;
import org.orbisgis.coremap.renderer.se.parameter.real.RealParameter;
import org.orbisgis.coremap.renderer.se.parameter.real.RealParameterContext;

/**
 * Represents a translation in an euclidean plane. As it can be represented with
 * a 2D vector, it is defined by two <code>RealParameter</code>s.
 * @author Maxence Laurent
 */
public class Translate extends AbstractSymbolizerNode implements Transformation {

        private RealParameter x;
        private RealParameter y;

        /**
         * Create a new <code>Translate</code>
         * @param x The translation about X-axis
         * @param y The translation about Y-axis
         */
        public Translate(RealParameter x, RealParameter y) {
                setX(x);
                setY(y);
        }

        /**
         * Create an new empty <code>Translate</code>
         */
        public Translate(){
        }

        /**
         * Create a new <code>Translate</code>, using the informations contained in 
         * <code>t</code>
         * @param t
         * @throws org.orbisgis.coremap.renderer.se.SeExceptions.InvalidStyle
         */
        public Translate(TranslateType t) throws InvalidStyle {
                if (t.getX() != null) {
                        setX(SeParameterFactory.createRealParameter(t.getX()));
                }
                if (t.getY() != null) {
                        setY(SeParameterFactory.createRealParameter(t.getY()));
                }
        }

        @Override
        public boolean allowedForGeometries() {
                return true;
        }

        @Override
        public AffineTransform getAffineTransform(Map<String,Object> map, Uom uom,
            MapTransform mt, Double width100p, Double height100p) throws ParameterException {
                double tx = 0.0;
                if (x != null) {
                        tx = Uom.toPixel(x.getValue(map), uom, mt.getDpi(), mt.getScaleDenominator(), width100p);
                }

                double ty = 0.0;
                if (y != null) {
                        ty = Uom.toPixel(y.getValue(map), uom, mt.getDpi(), mt.getScaleDenominator(), height100p);
                }

                return AffineTransform.getTranslateInstance(tx, ty);
        }

        @Override
        public List<SymbolizerNode> getChildren() {
                List<SymbolizerNode> ls = new ArrayList<SymbolizerNode>();
                if (x != null) {
                        ls.add(x);
                }
                if (y != null) {
                        ls.add(y);
                }
                return ls;
        }

        @Override
        public JAXBElement<?> getJAXBElement() {
                TranslateType t = this.getJAXBType();
                ObjectFactory of = new ObjectFactory();
                return of.createTranslate(t);
        }

        @Override
        public TranslateType getJAXBType() {
                TranslateType t = new TranslateType();

                if (x != null) {
                        t.setX(x.getJAXBParameterValueType());
                }

                if (y != null) {
                        t.setY(y.getJAXBParameterValueType());
                }

                return t;
        }

        /**
         * Get the translation about the X-axis
         * @return The translation about the X-axis
         */
        public RealParameter getX() {
                return x;
        }

        /**
         * Get the translation about the Y-axis
         * @return The translation about the Y-axis
         */
        public RealParameter getY() {
                return y;
        }

        /**
         * Set the translation about the Y-axis
         * @param y 
         */
        public final void setY(RealParameter y) {
                this.y = y;
                if (y != null) {
                        y.setContext(RealParameterContext.REAL_CONTEXT);
                        this.y.setParent(this);
                }
        }

        /**
         * Set the translation about the X-axis
         * @param y 
         */
        public final void setX(RealParameter x) {
                this.x = x;
                if (x != null) {
                        x.setContext(RealParameterContext.REAL_CONTEXT);
                        this.x.setParent(this);
                }
        }

        @Override
        public String toString() {
                return "Translate";
        }
}
