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
package org.orbisgis.legend.thematic;

import org.orbisgis.coremap.renderer.se.fill.SolidFill;
import org.orbisgis.coremap.renderer.se.parameter.ParameterUtil;
import org.orbisgis.coremap.renderer.se.stroke.PenStroke;

import java.awt.*;

/**
 * A simple java "tuple" that embeds the fours parameters needed to draw a line defined with a {@link
 * org.orbisgis.coremap.renderer.se.stroke.PenStroke}.
 * @author alexis
 */
public class LineParameters implements SymbolParameters {

    private Color color;
    private Double opacity;
    private Double width;
    private String dash;

    /**
     * Build a new {@code LineParameters} with the given arguments.
     * @param c The color, set to {@link Color#BLACK} if null
     * @param op The opacity, set to 1.0 if null. If it's out of [0,1], will be set to the closest extremum of this
     *           interval.
     * @param w The width, set to 0.25 if null.
     * @param d The dash pattern, set to the empty string if null or not valid.
     */
    public  LineParameters(Color c, Double op, Double w, String d){
        color = c == null ? Color.BLACK : c;
        opacity = op == null ? SolidFill.DEFAULT_OPACITY : Math.min(1.0, Math.max(0,op));
        width = w == null ? PenStroke.DEFAULT_WIDTH : w;
        dash = d == null || !ParameterUtil.validateDashArray(d)? "" : d;
    }

    /**
     * The color of the line
     * @return The color of the line
     */
    public Color getLineColor() {
        return color;
    }

    /**
     * The opacity of the line
     * @return The opacity of the line
     */
    public Double getLineOpacity() {
        return opacity;
    }

    /**
     * The width of the line
     * @return The width of the line
     */
    public Double getLineWidth() {
        return width;
    }

    /**
     * The dash pattern of the line
     * @return The dash pattern of the line
     */
    public String getLineDash() {
        return dash;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof LineParameters){
            LineParameters bis = (LineParameters) o;
            return bis.getLineColor().equals(color) &&
                        bis.getLineWidth().equals(width) &&
                        bis.getLineDash().equals(dash) &&
                        bis.getLineOpacity().equals(opacity);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode(){
        return color.hashCode() + dash.hashCode()*7 + width.hashCode()*29 +  opacity.hashCode()*17;
    }
}
