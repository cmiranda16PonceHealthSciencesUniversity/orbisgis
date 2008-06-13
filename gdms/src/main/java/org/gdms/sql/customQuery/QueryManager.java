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

import java.util.HashMap;

import org.gdms.sql.customQuery.showAttributes.ShowCall;
import org.gdms.sql.customQuery.spatial.convert.Explode;
import org.gdms.sql.function.FunctionManager;

/**
 * Manages the custom queries
 *
 * @author Fernando Gonzalez Cortes
 */
public class QueryManager {
	private static HashMap<String, CustomQuery> queries = new HashMap<String, CustomQuery>();

	static {
		registerQuery(new RegisterCall());
		registerQuery(new BuildSpatialIndex());
		registerQuery(new BuildAlphanumericIndex());
		registerQuery(new DeleteIndex());
		registerQuery(new Extrude());
		registerQuery(new ShowCall());

		registerQuery(new Explode());
	}

	/**
	 * Registers a query
	 *
	 * @param query
	 *            Query to add to the manager.
	 *
	 * @throws RuntimeException
	 *             If a query with the name already exists
	 */
	public static void registerQuery(CustomQuery query) {
		String queryName = query.getName().toLowerCase();

		if (FunctionManager.getFunction(queryName) != null) {
			throw new RuntimeException(
					"There is already a function with that name");
		} else if (queries.get(queryName) != null) {
			throw new IllegalArgumentException("Query already registered: "
					+ queryName);
		} else {
			queries.put(queryName, query);
		}
	}

	/**
	 * Gets the query by name
	 *
	 * @param queryName
	 *            Name of the query
	 *
	 * @return An instance of the query
	 */
	public static CustomQuery getQuery(String queryName) {
		queryName = queryName.toLowerCase();

		return (CustomQuery) queries.get(queryName);
	}
}
