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
package org.gdms.data;

import java.util.List;
import org.gdms.data.schema.Schema;

import org.gdms.driver.DriverException;
import org.gdms.driver.Driver;
import org.gdms.driver.ReadAccess;
import org.gdms.source.directory.DefinitionType;
import org.orbisgis.progress.ProgressMonitor;

/**
 * Class to be implemented to add new types of sources to the system.
 */
public interface DataSourceDefinition {

        /**
         * Creates a DataSource with the information of this object
         *
         * @param tableName
         *            name of the DataSource
         * @param pm
         *            To indicate progress or being canceled
         * @return DataSource
         * @throws DataSourceCreationException
         */
        DataSource createDataSource(String tableName, ProgressMonitor pm)
                throws DataSourceCreationException;

        /**
         * Creates this source with the content specified in the parameter
         *
         * @param contents
         * @param pm
         * @throws DriverException 
         */
        void createDataSource(ReadAccess contents, ProgressMonitor pm)
                throws DriverException;

        /**
         * if any, frees the resources taken when the DataSource was created
         *
         * @param name
         *            DataSource registration name
         *
         * @throws DataSourceFinalizationException
         *             If the operation fails
         */
        void freeResources(String name)
                throws DataSourceFinalizationException;

        /**
         * Gives to the DataSourceDefinition a reference of the DataSourceFactory
         * where the DataSourceDefinition is registered
         *
         * @param dsf
         */
        void setDataSourceFactory(DataSourceFactory dsf);

        /**
         * Returns a xml object to save the definition at disk
         *
         * @return
         */
        DefinitionType getDefinition();

        /**
         * Calculates the checksum of the source
         *
         * @param openDS
         *            An instance to an open DataSource that accesses the source
         *            this object defines. Null if there is no open DataSource
         *
         * @return
         * @throws DriverException
         */
        String calculateChecksum(DataSource openDS) throws DriverException;

        /**
         * Gets the names of the sources this source depends on. Usually it will be
         * an empty array but definitions that consist in an sql instruction may
         * return several values
         *
         * @return
         * @throws DriverException
         */
        List<String> getSourceDependencies() throws DriverException;

        /**
         * Gets the type of the source accessed by this definition
         *
         * @return
         */
        int getType();

        /**
         * Get the source type description of the source accessed by this definition
         *
         * @return
         */
        String getTypeName();

        /**
         * Method that lets the DataSourceDefinitions perform any kind of
         * initialization
         *
         * @throws DriverException
         *             If the source is not valid and cannot be initializated
         */
        void initialize() throws DriverException;

        /**
         * Get the id of the driver used to access this source definition
         *
         * @return the id of the driver
         */
        String getDriverId();

        /**
         * Gets the driver associated with this source.
         * @return the driver
         */
        Driver getDriver();
        
        /**
         * Refreshes all stored data of the definition (e.g. the type).
         */
        void refresh();

        /**
         * Gets the name of the table of the driver this Definition is
         * describing
         * @return the name of the driver
         */
        String getDriverTableName();

        /**
         * Deletes all physical storage associated with this source.
         */
        void delete();
        
        /**
         * Gets the schema of underlying source.
         * @return a never-empty schema
         * @throws DriverException  
         */
        Schema getSchema() throws DriverException;
}
