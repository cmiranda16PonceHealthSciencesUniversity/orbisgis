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
package org.gdms.sql.function.spatial.geometry.operators;

import java.util.ArrayList;

import org.gdms.data.SQLDataSourceFactory;
import org.gdms.data.values.Value;
import org.gdms.data.values.ValueFactory;
import org.gdms.sql.function.ScalarArgument;
import org.gdms.sql.function.FunctionException;
import org.gdms.sql.function.spatial.geometry.AbstractScalarSpatialFunction;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.operation.buffer.BufferOp;
import com.vividsolutions.jts.operation.buffer.BufferParameters;
import org.gdms.sql.function.BasicFunctionSignature;
import org.gdms.sql.function.FunctionSignature;

/**
 * Compute a ring buffer around a geometry.
 */
public final class ST_RingBuffer extends AbstractScalarSpatialFunction {

        private static final String CAP_STYLE_SQUARE = "square";
        private static final String CAP_STYLE_BUTT = "butt";
        private static final String CAP_STYLE_ROUND = "round";
        private static final GeometryFactory gf = new GeometryFactory();

        @Override
        public Value evaluate(SQLDataSourceFactory dsf, Value[] args) throws FunctionException {
                if ((args[0].isNull()) || (args[1].isNull())) {
                        return ValueFactory.createNullValue();
                } else {
                        final Geometry geom = args[0].getAsGeometry();
                        final double bufferSize = args[1].getAsDouble();
                        Geometry buffer;
                        int numBuffer = args[2].getAsInt();
                        if (args.length == 4) {
                                final String bufferStyle = args[3].toString();
                                buffer = getBuffers(geom, bufferSize, numBuffer, bufferStyle);
                        } else {
                                buffer = getBuffers(geom, bufferSize, numBuffer,
                                        CAP_STYLE_ROUND);
                        }
                        return ValueFactory.createValue(buffer);
                }
        }

        @Override
        public String getName() {
                return "ST_RingBuffer";
        }

        public Geometry getBuffers(Geometry geom, double bufferDistance,
                int numBuffer, String endCapStyle) {

                ArrayList<Geometry> buffers = new ArrayList<Geometry>();

                Geometry previous = geom;
                double distance = 0;
                for (int i = 0; i < numBuffer; i++) {
                        distance += bufferDistance;
                        Geometry newBuffer = runBuffer(geom, distance, endCapStyle);
                        Geometry geomBufferExternal = newBuffer.difference(previous);
                        buffers.add(geomBufferExternal);
                        previous = newBuffer;
                }
                return gf.createGeometryCollection(buffers.toArray(new Geometry[buffers.size()]));

        }

        private Geometry runBuffer(final Geometry geom, final double bufferSize,
                final String endCapStyle) {
                BufferOp bufOp = null;

                if (endCapStyle.equalsIgnoreCase(CAP_STYLE_SQUARE)) {
                        bufOp = new BufferOp(geom, new BufferParameters(
                                BufferParameters.DEFAULT_QUADRANT_SEGMENTS,
                                BufferParameters.CAP_SQUARE));
                } else if (endCapStyle.equalsIgnoreCase(CAP_STYLE_BUTT)) {
                        bufOp = new BufferOp(geom, new BufferParameters(
                                BufferParameters.DEFAULT_QUADRANT_SEGMENTS,
                                BufferParameters.CAP_FLAT));
                } else {
                        bufOp = new BufferOp(geom, new BufferParameters(
                                BufferParameters.DEFAULT_QUADRANT_SEGMENTS,
                                BufferParameters.CAP_ROUND));
                }

                return bufOp.getResultGeometry(bufferSize);
        }

        @Override
        public String getDescription() {
                return "Compute a ring buffer around a geometry.";
        }

        @Override
        public String getSqlOrder() {
                return "select ST_RingBuffer(the_geom, bufferSize, ringNumbers[, 'butt'|'square'|'round']) from myTable;";
        }

        @Override
        public FunctionSignature[] getFunctionSignatures() {
                return new FunctionSignature[]{
                                new BasicFunctionSignature(getType(null),
                                ScalarArgument.GEOMETRY, ScalarArgument.DOUBLE, ScalarArgument.INT),
                                new BasicFunctionSignature(getType(null),
                                ScalarArgument.GEOMETRY, ScalarArgument.DOUBLE, ScalarArgument.INT,
                                ScalarArgument.STRING)
                        };
        }
}
