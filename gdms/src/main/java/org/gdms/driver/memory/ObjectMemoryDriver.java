package org.gdms.driver.memory;

import java.io.File;
import java.util.ArrayList;

import org.gdms.data.DataSource;
import org.gdms.data.DataSourceFactory;
import org.gdms.data.metadata.DefaultMetadata;
import org.gdms.data.metadata.Metadata;
import org.gdms.data.object.ObjectSourceDefinition;
import org.gdms.data.types.DefaultType;
import org.gdms.data.types.DefaultTypeDefinition;
import org.gdms.data.types.InvalidTypeException;
import org.gdms.data.types.Type;
import org.gdms.data.types.TypeDefinition;
import org.gdms.data.values.Value;
import org.gdms.data.values.ValueFactory;
import org.gdms.driver.DriverException;
import org.gdms.driver.ObjectReadWriteDriver;
import org.gdms.spatial.FID;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

public class ObjectMemoryDriver implements ObjectReadWriteDriver {

	private static final String GEOMETRY = "GEOMETRY";

	public static String[] typeNames = new String[] { "BINARY", "BOOLEAN",
			"BYTE", "DATE", "DOUBLE", "FLOAT", "INT", "LONG", "SHORT",
			"STRING", "TIMESTAMP", "TIME" };

	private ArrayList<ArrayList<Value>> contents = new ArrayList<ArrayList<Value>>();

	private String[] columnsNames;

	private Type[] columnsTypes;

	/**
	 * Create a new empty source of data in memory. The source will have as many
	 * columns as specified in the 'columnsNames' parameter. The values in this
	 * array are the names of the columns and the values in the 'columnsTypes'
	 * array are constants in the org.gdms.data.values.Value interface and
	 * specify the type of each column.
	 * 
	 * @param types
	 */
	public ObjectMemoryDriver(String[] columnsNames, Type[] columnsTypes) {
		this.columnsNames = columnsNames;
		this.columnsTypes = columnsTypes;
	}

	public ObjectMemoryDriver(DataSource dataSource) throws DriverException {
		dataSource.open();
		this.write(dataSource);
		Metadata m = dataSource.getMetadata();
		this.columnsNames = new String[m.getFieldCount()];
		this.columnsTypes = new Type[m.getFieldCount()];
		for (int i = 0; i < columnsNames.length; i++) {
			columnsNames[i] = m.getFieldName(i);
			columnsTypes[i] = m.getFieldType(i);
		}
		dataSource.cancel();
	}

	public void write(DataSource dataSource) throws DriverException {
		ArrayList<ArrayList<Value>> newContents = new ArrayList<ArrayList<Value>>();
		for (int i = 0; i < dataSource.getRowCount(); i++) {
			Value[] row = dataSource.getRow(i);
			ArrayList<Value> rowArray = new ArrayList<Value>();
			for (int j = 0; j < row.length; j++) {
				rowArray.add(row[j]);
			}
			newContents.add(rowArray);
		}

		contents = newContents;
	}

	public void start() throws DriverException {

	}

	public void stop() throws DriverException {

	}

	public Metadata getMetadata() throws DriverException {
		return new DefaultMetadata(columnsTypes, columnsNames);
		// Metadata ret = new DefaultMetadata();
		// for (int i = 0; i < columnsNames.length; i++) {
		// String typeName;
		// if (columnsTypes[i] == PTTypes.GEOMETRY) {
		// typeName = GEOMETRY;
		// } else {
		// typeName = typeNames[columnsTypes[i]];
		// }
		// ret.addField(columnsNames[i], typeName);
		// }
		// return ret;
	}

	public FID getFid(long row) {
		return null;
	}

	public int getType(String driverType) {
		for (int i = 0; i < typeNames.length; i++) {
			if (typeNames[i].equals(driverType)) {
				return i;
			}
		}

		if (driverType.equals(GEOMETRY)) {
			return Type.GEOMETRY;
		}

		throw new RuntimeException();
	}

	public boolean hasFid() {
		return false;
	}

	public void setDataSourceFactory(DataSourceFactory dsf) {
	}

	public String getName() {
		return "Object memory driver";
	}

	public Value getFieldValue(long rowIndex, int fieldId)
			throws DriverException {
		return contents.get((int) rowIndex).get(fieldId);
	}

	public long getRowCount() throws DriverException {
		return contents.size();
	}

	public Number[] getScope(int dimension, String fieldName)
			throws DriverException {
		return null;
	}

	public boolean isCommitable() {
		return true;
	}

	public static void main(String[] args) throws Exception {
		DataSourceFactory dsf = new DataSourceFactory();

		ObjectMemoryDriver omd = new ObjectMemoryDriver(new String[] { "geom",
				"ageofthecaptain" }, new Type[] {
				new DefaultType(null, "GEOMETRY", Type.GEOMETRY),
				new DefaultType(null, "INTEGER", Type.INT) });

		dsf.registerDataSource("ds1", new ObjectSourceDefinition(omd));
		DataSource ds = dsf.getDataSource("ds1");
		ds.open();
		Geometry g = new GeometryFactory().createPoint(new Coordinate(0, 0));
		ds.insertEmptyRow();
		ds.setFieldValue(0, 0, ValueFactory.createValue(g));
		ds.setFieldValue(0, 1, ValueFactory.createValue(4));
		System.out.println(ds.getAsString());
		ds.commit();

		omd = new ObjectMemoryDriver(ds);
		DataSource ds2 = dsf.getDataSource(omd);
		ds2.open();
		System.out.println(ds2.getAsString());
		ds2.cancel();

		ds.open();
		ds.deleteRow(0);
		ds.commit();

		ds2.open();
		System.out.println(ds2.getAsString());
		ds2.cancel();

		// An exemple with SQL queries

		File src1 = new File(
				"../../datas2tests/shp/mediumshape2D/bzh5_communes.shp");
		DataSource ds1 = dsf.getDataSource(src1);

		String dsName = ds1.getName();

		String sqlQuery = "select Buffer(" + dsName + ".the_geom,20) from "
				+ dsName + ";";

		// SpatialDataSource spatialds = new SpatialDataSourceDecorator(dsf
		// .executeSQL(sqlQuery));

		DataSource result = dsf.executeSQL(sqlQuery);

		ObjectMemoryDriver omdResult = new ObjectMemoryDriver(result);

		// Object memory driver register

		dsf.registerDataSource("myResult",
				new ObjectSourceDefinition(omdResult));

		DataSource newds = dsf.executeSQL("select * from myResult;");
		newds.open();
		System.out.println(newds.getAsString());
		newds.cancel();
	}

	public TypeDefinition[] getTypesDefinitions() throws DriverException {
		try {
			return new TypeDefinition[] {
					new DefaultTypeDefinition("BINARY", Type.BINARY),
					new DefaultTypeDefinition("BOOLEAN", Type.BOOLEAN),
					new DefaultTypeDefinition("BYTE", Type.BYTE),
					new DefaultTypeDefinition("DATE", Type.DATE),
					new DefaultTypeDefinition("DOUBLE", Type.DOUBLE),
					new DefaultTypeDefinition("FLOAT", Type.FLOAT),
					new DefaultTypeDefinition("INT", Type.INT),
					new DefaultTypeDefinition("LONG", Type.LONG),
					new DefaultTypeDefinition("SHORT", Type.SHORT),
					new DefaultTypeDefinition("STRING", Type.STRING),
					new DefaultTypeDefinition("TIMESTAMP", Type.TIMESTAMP),
					new DefaultTypeDefinition("TIME", Type.TIME),
					new DefaultTypeDefinition("GEOMETRY", Type.GEOMETRY) };
		} catch (InvalidTypeException e) {
			throw new DriverException("Invalid type");
		}
	}
}