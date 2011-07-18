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
package org.gdms.data.values;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import org.gdms.data.types.Type;
import org.gdms.data.types.TypeFactory;
import org.gdms.data.types.IncompatibleTypesException;

/**
 * Abstract class which gives a common basis to all the numeric values.
 * @author Fernando Gonzalez Cortes
 */
abstract class DefaultNumericValue extends AbstractValue implements Serializable, NumericValue {

        private static final String NOTNUMERIC = "The specified value is not a numeric:";

        /**
         * Returns the number of digits after the decimal point
         *
         * @return
         */
        @Override
        public abstract int getDecimalDigitsCount();

        /**
         * Compute the product between this and value
         *
         * @param value
         *            The value to compute the product with.
         *
         * @return The result as a numeric value.
         *
         * @throws IncompatibleTypesException
         *             If value is not a numeric value.
         */
        @Override
        public NumericValue multiply(Value value) {
                if (value.isNull()) {
                        return ValueFactory.createNullValue();
                } else {
                        if (!(value instanceof NumericValue)) {
                                throw new IncompatibleTypesException(
                                        NOTNUMERIC
                                        + TypeFactory.getTypeName(value.getType()));
                        }

                        return ValueFactory.product(this, (NumericValue) value);
                }
        }

        /**
         * Compute the sum between this and value
         *
         * @param value
         *            The value to compute the sum with.
         *
         * @return The result as a numeric value.
         *
         * @throws IncompatibleTypesException
         *             If value is not a numeric value.
         */
        @Override
        public NumericValue sum(Value value) {
                if (value.isNull()) {
                        return ValueFactory.createNullValue();
                } else {
                        if (!(value instanceof NumericValue)) {
                                throw new IncompatibleTypesException(
                                        NOTNUMERIC
                                        + TypeFactory.getTypeName(value.getType()));
                        }

                        return ValueFactory.sum(this, (NumericValue) value);
                }
        }

        @Override
        public NumericValue remainder(Value value) {
                if (value.isNull()) {
                        return ValueFactory.createNullValue();
                } else {
                        if (!(value instanceof NumericValue)) {
                                throw new IncompatibleTypesException(
                                        NOTNUMERIC
                                        + TypeFactory.getTypeName(value.getType()));
                        }

                        return ValueFactory.remainder(this, (NumericValue) value);
                }
        }

        @Override
        public DoubleValue pow(Value value) {
                if (value.isNull()) {
                        return ValueFactory.createNullValue();
                } else {
                        if (!(value instanceof NumericValue)) {
                                throw new IncompatibleTypesException(
                                        NOTNUMERIC
                                        + TypeFactory.getTypeName(value.getType()));
                        }

                        return ValueFactory.createValue(Math.pow(doubleValue(), value.getAsDouble()));
                }
        }



        /**
         * Compute the inverse of this.
         * @return
         *          The inverse as a numeric value.
         * @throws IncompatibleTypesException
         */
        @Override
        public Value inverse() {
                return ValueFactory.inverse(this);
        }

        @Override
        public BooleanValue equals(Value value) {
                if (value instanceof NullValue) {
                        return ValueFactory.createNullValue();
                }

                if (!(value instanceof NumericValue)) {
                        throw new IncompatibleTypesException("Type:" + value.getType());
                }

                return ValueFactory.createValue(this.doubleValue() == ((NumericValue) value).doubleValue());
        }

        @Override
        public BooleanValue greater(Value value) {
                if (value instanceof NullValue) {
                        return ValueFactory.createNullValue();
                }

                if (!(value instanceof NumericValue)) {
                        throw new IncompatibleTypesException(
                                NOTNUMERIC
                                + TypeFactory.getTypeName(value.getType()));
                }

                return ValueFactory.createValue(this.doubleValue() > ((NumericValue) value).doubleValue());
        }

        @Override
        public BooleanValue greaterEqual(Value value) {
                if (value instanceof NullValue) {
                        return ValueFactory.createNullValue();
                }

                if (!(value instanceof NumericValue)) {
                        throw new IncompatibleTypesException(
                                NOTNUMERIC
                                + TypeFactory.getTypeName(value.getType()));
                }

                return ValueFactory.createValue(this.doubleValue() >= ((NumericValue) value).doubleValue());
        }

        @Override
        public BooleanValue less(Value value) {
                if (value instanceof NullValue) {
                        return ValueFactory.createNullValue();
                }

                if (!(value instanceof NumericValue)) {
                        throw new IncompatibleTypesException(
                                NOTNUMERIC
                                + TypeFactory.getTypeName(value.getType()));
                }

                return ValueFactory.createValue(this.doubleValue() < ((NumericValue) value).doubleValue());
        }

        @Override
        public BooleanValue lessEqual(Value value) {
                if (value instanceof NullValue) {
                        return ValueFactory.createNullValue();
                }

                if (!(value instanceof NumericValue)) {
                        throw new IncompatibleTypesException(value.toString());
                }

                return ValueFactory.createValue(this.doubleValue() <= ((NumericValue) value).doubleValue());
        }

        @Override
        public BooleanValue notEquals(Value value) {
                if (value instanceof NullValue) {
                        return ValueFactory.createNullValue();
                }

                if (!(value instanceof NumericValue)) {
                        throw new IncompatibleTypesException(
                                NOTNUMERIC
                                + TypeFactory.getTypeName(value.getType()));
                }

                return ValueFactory.createValue(this.doubleValue() != ((NumericValue) value).doubleValue());
        }

        @Override
        public int hashCode() {
                return intValue();
        }

        @Override
        public byte getAsByte() {
                return byteValue();
        }

        @Override
        public double getAsDouble() {
                return doubleValue();
        }

        @Override
        public float getAsFloat() {
                return floatValue();
        }

        @Override
        public int getAsInt() {
                return intValue();
        }

        @Override
        public long getAsLong() {
                return longValue();
        }

        @Override
        public short getAsShort() {
                return shortValue();
        }

        private boolean isDecimal(int type) {
                return (type == Type.FLOAT) || (type == Type.DOUBLE);
        }

        @Override
        public Value toType(int typeCode) {
                switch (typeCode) {
                        case Type.NULL:
                        case Type.BYTE:
                        case Type.SHORT:
                        case Type.INT:
                        case Type.LONG:
                        case Type.FLOAT:
                        case Type.DOUBLE:
                                if (!isDecimal(typeCode) && isDecimal(getType())) {
                                        throw new IncompatibleTypesException(
                                                "Cannot cast decimal to whole :"
                                                + typeCode
                                                + ": "
                                                + getStringValue(ValueWriter.internalValueWriter));
                                }
                                return this;
                        case Type.DATE:
                                if (!isDecimal(getType())) {
                                        return ValueFactory.createValue(new Date(longValue()));
                                } else {
                                        break;
                                }
                        case Type.STRING:
                                return ValueFactory.createValue(toString());
                        case Type.TIME:
                                if (!isDecimal(getType())) {
                                        return ValueFactory.createValue(new Time(longValue()));
                                } else {
                                        break;
                                }
                        case Type.TIMESTAMP:
                                if (!isDecimal(getType())) {
                                        return ValueFactory.createValue(new Timestamp(longValue()));
                                } else {
                                        break;
                                }
                        default:
                                throw new IncompatibleTypesException("Cannot cast to type:" + typeCode
                                        + ": " + getStringValue(ValueWriter.internalValueWriter));
                }
                throw new IncompatibleTypesException("Cannot cast to type:" + typeCode
                        + ": " + getStringValue(ValueWriter.internalValueWriter));
        }

        @Override
        public int compareTo(Value o) {
                if (o.isNull()) {
                        // by default, NULL FIRST
                        return -1;
                } else if (o instanceof NumericValue) {
                        NumericValue nv = (NumericValue) o;
                        return Double.compare(getAsDouble(), nv.getAsDouble());
                } else {
                        return super.compareTo(o);
                }
        }
}
