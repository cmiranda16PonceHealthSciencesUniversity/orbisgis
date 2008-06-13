/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able
 * to manipulate and create vector and raster spatial information. OrbisGIS
 * is distributed under GPL 3 license. It is produced  by the geo-informatic team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/>, CNRS FR 2488:
 *    Erwan BOCHER, scientific researcher,
 *    Thomas LEDUC, scientific researcher,
 *    Fernando GONZALEZ CORTES, computer engineer.
 *
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 *
 * This file is part of OrbisGIS.
 *
 * OrbisGIS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OrbisGIS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult:
 *    <http://orbisgis.cerma.archi.fr/>
 *    <http://sourcesup.cru.fr/projects/orbisgis/>
 *
 * or contact directly:
 *    erwan.bocher _at_ ec-nantes.fr
 *    fergonco _at_ gmail.com
 *    thomas.leduc _at_ cerma.archi.fr
 */
package org.gdms.sql.customQuery;

import org.gdms.data.DataSource;
import org.gdms.data.DataSourceFactory;
import org.gdms.data.ExecutionException;
import org.gdms.data.metadata.Metadata;
import org.gdms.data.types.Type;
import org.gdms.data.values.Value;
import org.gdms.driver.DriverException;
import org.gdms.driver.ObjectDriver;
import org.gdms.sql.strategies.IncompatibleTypesException;
import org.gdms.sql.strategies.SemanticException;
import org.orbisgis.progress.IProgressMonitor;

/**
 * Interface to implement by the custom queries
 *
 * @author Fernando Gonzalez Cortes
 */
public interface CustomQuery {
	/**
	 * Executes the custom query
	 *
	 * @param dsf
	 *            data source factory
	 * @param tables
	 *            tables involved in the query
	 * @param values
	 *            values passed to the query
	 *
	 * @return DataSource result of the query
	 *
	 * @throws ExecutionException
	 *             if the custom query execution fails
	 */
	public ObjectDriver evaluate(DataSourceFactory dsf, DataSource[] tables,
			Value[] values, IProgressMonitor pm) throws ExecutionException;

	/**
	 * Gets the query name. Must be a valid SQL identifier (i.e.: '.' is not
	 * allowed)
	 *
	 * @return query name
	 */
	public String getName();

	/**
	 * Description to use the method.
	 *
	 * @return
	 */
	public String getDescription();

	/**
	 * Example of use.
	 *
	 * @return
	 */
	public String getSqlOrder();

	/**
	 * Gets the metadata of the result without executing the query
	 * @param tables TODO
	 *
	 * @return
	 * @throws DriverException
	 */
	public Metadata getMetadata(Metadata[] tables) throws DriverException;

	/**
	 * Validates the number and types of the arguments
	 *
	 * @param types
	 * @throws IncompatibleTypesException
	 *             If the number or types of the arguments are not valid for
	 *             this custom query
	 */
	public void validateTypes(Type[] types) throws IncompatibleTypesException;

	/**
	 * Validates the number and structure of the input tables
	 *
	 * @param tables
	 * @throws SemanticException
	 *             If the number or schemas of the input tables are not valid
	 *             for this custom query
	 * @throws DriverException
	 */
	public void validateTables(Metadata[] tables) throws SemanticException, DriverException;
}