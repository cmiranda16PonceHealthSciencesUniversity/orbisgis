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
package org.orbisgis.core.ui.views.geocatalog.newSourceWizards.db;

import java.awt.Component;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.gdms.data.db.DBSource;
import org.gdms.driver.DBDriver;
import org.gdms.driver.DriverException;
import org.gdms.driver.TableDescription;
import org.orbisgis.sif.UIPanel;

public class TableSelectionPanel implements UIPanel {
	
	private ConnectionPanel firstPanel;
	private JTree tableTree;
	private JScrollPane jScrollPane;
	
	public TableSelectionPanel(final ConnectionPanel firstPanel) {
		super();
		this.firstPanel = firstPanel;
	}

	@Override
	public Component getComponent() {
		if(null == jScrollPane)
			jScrollPane = new JScrollPane();
		return jScrollPane;
	}

	@Override
	public URL getIconURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		return "Select table(s) name(s)...";
	}

	@Override
	public String initialize() {
		try {
			tableTree = getTableTree();
		} catch (SQLException e) {
			return e.getMessage();
		} catch (DriverException e) {
			return e.getMessage();
		}
		if(jScrollPane == null)
			jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(tableTree);
	
		return null;
	}

	private JTree getTableTree() throws SQLException, DriverException {
		  if (tableTree == null) {

			DBDriver dbDriver = firstPanel.getDBDriver();
			final Connection connection = firstPanel.getConnection();
			final String[] schemas = dbDriver.getSchemas(connection);
			
			DefaultMutableTreeNode rootNode =                    
//		        new DefaultMutableTreeNode ("Schemas");
	        	new DefaultMutableTreeNode (connection.getCatalog());

			// Add Data to the tree
			for (String schema : schemas){
				
				//list schemas
				DefaultMutableTreeNode schemaNode = new DefaultMutableTreeNode(new SchemaNode(schema));
				rootNode.add(schemaNode);
				
				//list Tables
				DefaultMutableTreeNode tableNode = 
					new DefaultMutableTreeNode("Tables");
				final TableDescription[] tableDescriptions = dbDriver
				.getTables(connection,null,schema,null,new String[]{"TABLE"});
				if(tableDescriptions.length > 0){
					schemaNode.add(tableNode);
					for (TableDescription tableDescription : tableDescriptions){
						tableNode.add(new DefaultMutableTreeNode(new TableNode(tableDescription)));
					}
				}
				
				//list View
				DefaultMutableTreeNode viewNode = 
					new DefaultMutableTreeNode("Views");
				final TableDescription[] viewDescriptions = dbDriver
				.getTables(connection,null,schema,null,new String[]{"VIEW"});
				if(viewDescriptions.length > 0){
					schemaNode.add(viewNode);
					for (TableDescription viewDescription : viewDescriptions){
						viewNode.add(new DefaultMutableTreeNode(new ViewNode(viewDescription)));
					}
				}
			}

			connection.close();
		                                             
		    tableTree = new JTree (rootNode);
		    tableTree.setRootVisible(true);                        
		    tableTree.setShowsRootHandles(true);      
		    tableTree.setCellRenderer(new TableTreeCellRenderer());
		  }                                                           
		  return tableTree;
		}

	@Override
	public String validateInput() {
		String validateInput = null;
			validateInput = (getSelectedDBSources().length == 0) ? "Please select at least a table" : null;
		return validateInput;
	}

	@Override
	public String getInfoText() {
		return null;
	}

	@Override
	public String postProcess() {
		return null;
	}

	public DBSource[] getSelectedDBSources() {
		List<TableNode> tables = new ArrayList<TableNode>();
		TreePath[] treePath;
		try {
			treePath = getTableTree().getSelectionPaths();
			if (treePath == null)
				return new DBSource[0];
			for(int i=0;i<treePath.length;i++){
				Object selectedObject  = ((DefaultMutableTreeNode)treePath[i].getLastPathComponent()).getUserObject();
				if(selectedObject instanceof TableNode)
					tables.add(((TableNode)selectedObject));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DriverException e) {
			e.printStackTrace();
		}
			
		final DBSource[] dbSources = new DBSource[tables.size()];
		int i=0;
		for (TableNode table : tables) {
			dbSources[i] = firstPanel.getDBSource();
			dbSources[i].setTableName(table.getName());
			dbSources[i].setSchemaName(table.getSchema());
			i++;
		}
		return dbSources;
	}

}
