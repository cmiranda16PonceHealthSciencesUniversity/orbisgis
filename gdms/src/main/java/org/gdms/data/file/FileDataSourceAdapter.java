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
package org.gdms.data.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.gdms.data.AlreadyClosedException;
import org.gdms.data.DataSource;
import org.gdms.data.DriverDataSource;
import org.gdms.data.FreeingResourcesException;
import org.gdms.data.RightValueDecorator;
import org.gdms.data.edition.Commiter;
import org.gdms.data.edition.DeleteEditionInfo;
import org.gdms.data.edition.EditionInfo;
import org.gdms.data.edition.PhysicalDirection;
import org.gdms.driver.DriverException;
import org.gdms.driver.FileDriver;
import org.gdms.driver.FileReadWriteDriver;
import org.gdms.source.Source;
import org.orbisgis.progress.NullProgressMonitor;

/**
 * Adapter to the DataSource interface for file drivers
 *
 * @author Fernando Gonzalez Cortes
 */
public class FileDataSourceAdapter extends DriverDataSource implements Commiter {

	private FileDriver driver;

	private File file;

	private boolean realSource;

	/**
	 * @param src
	 * @param file
	 * @param driver
	 * @param commitable
	 *            If the file is the source itself or it's the result of a SQL
	 *            query for example
	 */
	public FileDataSourceAdapter(Source src, File file, FileDriver driver,
			boolean commitable) {
		super(src);
		this.driver = driver;
		this.file = file;
		this.realSource = commitable;
	}

	public FileDriver getDriver() {
		return driver;
	}

	public void commit() throws DriverException, FreeingResourcesException {
	}

	/**
	 * @see org.gdms.data.DataSource#saveData(org.gdms.data.DataSource)
	 */
	public void saveData(DataSource ds) throws DriverException {
		ds.open();
		((FileReadWriteDriver) driver).writeFile(file, ds,
				new NullProgressMonitor());
		ds.cancel();
	}

	public void open() throws DriverException {
		driver.open(file);
		listenerSupport.fireOpen(this);
	}

	public void cancel() throws DriverException, AlreadyClosedException {
		driver.close();
		listenerSupport.fireCancel(this);
	}

	public long[] getWhereFilter() throws IOException {
		return null;
	}

	public void commit(List<PhysicalDirection> rowsDirections,
			String[] fieldNames, ArrayList<EditionInfo> schemaActions,
			ArrayList<EditionInfo> editionActions,
			ArrayList<DeleteEditionInfo> deletedPKs, DataSource modifiedSource)
			throws DriverException, FreeingResourcesException {
		File temp = new File(driver.completeFileName(getDataSourceFactory()
				.getTempFile()));
		((FileReadWriteDriver) driver).writeFile(temp, new RightValueDecorator(
				modifiedSource), new NullProgressMonitor());
		try {
			driver.close();
		} catch (DriverException e) {
			throw new FreeingResourcesException(
					"Cannot free resources: data writen in "
							+ temp.getAbsolutePath(), e, temp);
		}
		try {
			((FileReadWriteDriver) driver).copy(temp, file);
		} catch (IOException e) {
			throw new FreeingResourcesException(
					"Cannot copy file: data writen in "
							+ temp.getAbsolutePath(), e, temp);
		}

		listenerSupport.fireCommit(this);
	}

	@Override
	public boolean isEditable() {
		return super.isEditable() && realSource;
	}
}