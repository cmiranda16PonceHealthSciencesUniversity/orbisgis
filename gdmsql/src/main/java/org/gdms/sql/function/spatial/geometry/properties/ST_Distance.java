/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information. OrbisGIS is
 * distributed under GPL 3 license. It is produced by the "Atelier SIG" team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/> CNRS FR 2488.
 *
 *
 * Team leader : Erwan BOCHER, scientific researcher,
 *
 * User support leader : Gwendall Petit, geomatic engineer.
 *
 * Previous computer developer : Pierre-Yves FADET, computer engineer, Thomas LEDUC, 
 * scientific researcher, Fernando GONZALEZ CORTES, computer engineer.
 *
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 *
 * Copyright (C) 2010 Erwan BOCHER, Alexis GUEGANNO, Maxence LAURENT, Antoine GOURLAY
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
 * info@orbisgis.org
 */
package org.gdms.sql.function.spatial.geometry.properties;

import org.gdms.data.SQLDataSourceFactory;
import org.gdms.data.types.Type;
import org.gdms.data.types.TypeFactory;
import org.gdms.data.values.Value;
import org.gdms.data.values.ValueFactory;
import org.gdms.sql.function.ScalarArgument;
import org.gdms.sql.function.FunctionException;

import com.vividsolutions.jts.geom.Geometry;
import org.gdms.sql.function.BasicFunctionSignature;
import org.gdms.sql.function.FunctionSignature;

/**
 * For geometry type returns the 2-dimensional minimum Cartesian
 * distance between two geometries in projected units (spatial ref units)
 */
public final class ST_Distance extends AbstractSpatialPropertyFunction {

        @Override
        public Value evaluateResult(SQLDataSourceFactory dsf, Value[] args) throws FunctionException {
                final Geometry g1 = args[0].getAsGeometry();
                final Geometry g2 = args[1].getAsGeometry();
                return ValueFactory.createValue(g1.distance(g2));
        }

        @Override
        public String getName() {
                return "ST_Distance";
        }

        @Override
        public Type getType(Type[] types) {
                return TypeFactory.createType(Type.DOUBLE);
        }

        @Override
        public String getDescription() {
                return "For geometry type returns the 2-dimensional minimum Cartesian distance between two geometries in projected units (spatial ref units)";
        }

        @Override
        public String getSqlOrder() {
                return "select ST_Distance((geometry g1, geometry g2)) from myTable;";
        }

        @Override
        public FunctionSignature[] getFunctionSignatures() {
                return new FunctionSignature[]{
                                new BasicFunctionSignature(getType(null),
                                ScalarArgument.GEOMETRY, ScalarArgument.GEOMETRY)
                        };
        }
}
