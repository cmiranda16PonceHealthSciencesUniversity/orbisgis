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

package org.orbisgis.core.renderer.se.parameter.real;

import javax.xml.bind.JAXBElement;
import org.gdms.data.SpatialDataSourceDecorator;
import org.gdms.data.feature.Feature;
import org.orbisgis.core.renderer.persistance.se.MapItemType;
import org.orbisgis.core.renderer.persistance.se.RecodeType;
import org.orbisgis.core.renderer.se.SeExceptions.InvalidStyle;
import org.orbisgis.core.renderer.se.parameter.ParameterException;
import org.orbisgis.core.renderer.se.parameter.Recode;
import org.orbisgis.core.renderer.se.parameter.SeParameterFactory;
import org.orbisgis.core.renderer.se.parameter.string.StringParameter;

public class Recode2Real extends Recode<RealParameter, RealLiteral> implements RealParameter {

	private Double min;
	private Double max;

	RealParameterContext ctx;

    public Recode2Real(RealLiteral fallback, StringParameter lookupValue){
        super(fallback, lookupValue);
		ctx = RealParameterContext.realContext;
    }

    public Recode2Real(JAXBElement<RecodeType> expr) throws InvalidStyle {
        RecodeType t = expr.getValue();
		ctx = RealParameterContext.realContext;

        this.fallbackValue = new RealLiteral(t.getFallbackValue());
        this.setLookupValue(SeParameterFactory.createStringParameter(t.getLookupValue()));

        for (MapItemType mi : t.getMapItem()){
            this.addMapItem(mi.getKey(), SeParameterFactory.createRealParameter(mi.getValue()));
        }
    }

    @Override
    public double getValue(SpatialDataSourceDecorator sds, long fid) throws ParameterException{
		if (sds == null){
			throw new ParameterException("No feature");
		}

        return getParameter(sds, fid).getValue(sds, fid);
    }

	@Override
	public int addMapItem(String key, RealParameter p){
		p.setContext(ctx);
		return super.addMapItem(key, p);
	}

	@Override
	public void setContext(RealParameterContext ctx) {
		this.ctx = ctx;

		if (getFallbackValue() != null){
			this.getFallbackValue().setContext(ctx);
		}
	}

	@Override
	public String toString(){
		return "NA";
	}


	@Override
	public RealParameterContext getContext() {
		return ctx;
	}

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
