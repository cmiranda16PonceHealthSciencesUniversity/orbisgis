/**
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information.
 *
 * OrbisGIS is distributed under GPL 3 license. It is produced by the "Atelier SIG"
 * team of the IRSTV Institute <http://www.irstv.fr/> CNRS FR 2488.
 *
 * Copyright (C) 2007-2014 IRSTV (FR CNRS 2488)
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
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.mapeditor;

import org.orbisgis.corejdbc.DataManager;
import org.orbisgis.coremap.renderer.ResultSetProviderFactory;
import org.orbisgis.mapeditor.map.MapEditor;
import org.orbisgis.sif.edition.EditorDockable;
import org.orbisgis.sif.edition.EditorFactory;
import org.orbisgis.sif.edition.EditorManager;
import org.orbisgis.sif.edition.SingleEditorFactory;
import org.orbisgis.viewapi.main.frames.ext.ToolBarAction;
import org.orbisgis.mapeditor.map.ext.MapEditorAction;
import org.orbisgis.mapeditor.map.ext.MapEditorExtension;
import org.orbisgis.viewapi.workspace.ViewWorkspace;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import java.util.ArrayList;
import java.util.List;

/**
 * MapEditor cannot be opened twice, the the factory is a SingleEditorFactory.
 */
@Component(service = EditorFactory.class, immediate = true)
public class MapEditorFactory implements SingleEditorFactory {
        public static final String FACTORY_ID = "MapFactory";
        private MapEditor mapPanel = null;
        //TODO reactive drawing
        //private DrawingToolBar drawingToolBar;
        private DataManager dataManager;
        private ViewWorkspace viewWorkspace;
        private EditorManager editorManager;
        private List<ResultSetProviderFactory> rsFactories = new ArrayList<>();
        private List<MapEditorAction> mapEditorActionFactory = new ArrayList<>();

        @Reference
        public void setEditorManager(EditorManager editorManager) {
            this.editorManager = editorManager;
        }

        public void unsetEditorManager(EditorManager editorManager) {
            dispose();
        }


        @Reference(cardinality = ReferenceCardinality.OPTIONAL, policy = ReferencePolicy.DYNAMIC)
        public void addMapEditorActionFactory(MapEditorAction mapEditorAction) {
            mapEditorActionFactory.add(mapEditorAction);
            if(mapPanel != null) {
                mapPanel.getActionCommands().addActionFactory(mapEditorAction, mapPanel);
            }
        }

        public void removeMapEditorActionFactory(MapEditorAction mapEditorAction) {
            mapEditorActionFactory.remove(mapEditorAction);
            if(mapPanel != null) {
                mapPanel.getActionCommands().removeActionFactory(mapEditorAction);
            }
        }


        @Reference(cardinality = ReferenceCardinality.OPTIONAL, policy = ReferencePolicy.DYNAMIC)
        public void addResultSetProviderFactory(ResultSetProviderFactory resultSetProviderFactory) {
            rsFactories.add(resultSetProviderFactory);
            if(mapPanel != null) {
                mapPanel.addResultSetProviderFactory(resultSetProviderFactory);
            }
        }

        public void removeResultSetProviderFactory(ResultSetProviderFactory resultSetProviderFactory) {
            rsFactories.remove(resultSetProviderFactory);
            if(mapPanel != null) {
                mapPanel.removeResultSetProviderFactory(resultSetProviderFactory);
            }
        }

        /**
         * @param dataManager DataManager
         */
        @Reference
        public void setDataManager(DataManager dataManager) {
            this.dataManager = dataManager;
        }

        /**
         * @param dataManager DataManager
         */
        public void unsetDataManager(DataManager dataManager) {
            dispose();
        }

        @Reference
        public void setViewWorkspace(ViewWorkspace viewWorkspace) {
            this.viewWorkspace = viewWorkspace;
        }

        public void unsetViewWorkspace(ViewWorkspace viewWorkspace) {
            dispose();
        }


        @Override
        public void dispose() {
            if(mapPanel!=null) {
                mapPanel.dispose();
            }
        }

        @Override
        public EditorDockable[] getSinglePanels() {
                if(mapPanel==null) {
                        mapPanel = new MapEditor(viewWorkspace,dataManager,editorManager);
                        for(ResultSetProviderFactory rsF  : rsFactories) {
                            mapPanel.addResultSetProviderFactory(rsF);
                        }
                        for(MapEditorAction mapEditorAction : mapEditorActionFactory) {
                            mapPanel.getActionCommands().addActionFactory(mapEditorAction, mapPanel);
                        }
                        // Create Drawing ToolBar
                        // TODO reactive drawing
                        // drawingToolBar = new DrawingToolBar(mapPanel);
                        // drawingToolbarService = hostBundle.registerService(ToolBarAction.class,drawingToolBar,null);
                }
                return new EditorDockable[] {mapPanel};
        }



        @Override
        public String getId() {
                return FACTORY_ID;
        }
}
