/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information. 
 * 
 * OrbisGIS is distributed under GPL 3 license. It is produced by the "Atelier SIG"
 * team of the IRSTV Institute <http://www.irstv.fr/> CNRS FR 2488.
 * 
 * Copyright (C) 2007-2012 IRSTV (FR CNRS 2488)
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
package org.orbisgis.view.components.fstree;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * This is the base class for all items in the tree.
 * The tree model is used to fire events related to node modifications.
 * @author Nicolas Fortin
 */
public abstract class AbstractTreeNode implements MutableTreeNode {
        private static final long serialVersionUID = 1L;
        // Tree Model
        protected DefaultTreeModel model;
        // Properties        
        private String label = "none";
        protected MutableTreeNode parent = null;

        @Override
        public String toString() {
                return label;
        }
        /**
         * Set the tree model of this item.
         * This method must be called by the parent item on insertion.
         * @param model 
         */
        public void setModel(DefaultTreeModel model) {
                this.model = model;
        }
        
        @Override
        public void removeFromParent() {
                if(parent!=null) {
                        parent.remove(this);
                }
        }

        @Override
        public void setParent(MutableTreeNode mtn) {
                parent = mtn;
        }
        
        @Override
        public TreeNode getParent() {
                return parent;
        }
        /**
         * Get the value of label of the TreeNode
         *
         * @return the value of label
         */
        public String getLabel() {
                return label;
        }

        /**
         * Set the value of label of the TreeNode
         *
         * @param label new value of label
         */
        public void setLabel(String label) {
                this.label = label;
                if(model!=null) {
                        model.nodeChanged(this);
                }
        }        
}
