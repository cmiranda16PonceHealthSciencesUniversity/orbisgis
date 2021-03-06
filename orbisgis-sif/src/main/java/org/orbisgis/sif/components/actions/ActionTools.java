/**
 * OrbisGIS is a java GIS application dedicated to research in GIScience.
 * OrbisGIS is developed by the GIS group of the DECIDE team of the 
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 * 
 * OrbisGIS is distributed under GPL 3 license.
 *
 * Copyright (C) 2007-2014 CNRS (IRSTV FR CNRS 2488)
 * Copyright (C) 2015-2017 CNRS (Lab-STICC UMR CNRS 6285)
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
package org.orbisgis.sif.components.actions;

import java.util.List;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

/**
 * Additional properties of Actions related to ActionCommands features.
 * @author Nicolas Fortin
 */
public class ActionTools {
        // Additional properties
        /** A collection of KeyStroke*/
        public static final String ADDITIONAL_ACCELERATOR_KEY = "additionalAcceleratorKeys";
        /** Optional Parent action ID (parent menu), String value */
        public static final String PARENT_ID = "parentMenuId";
        /** Unique menu identifier, String value */
        public static final String MENU_ID = "menuId";
        /** Define if this action is a Menu container, String value */
        public static final String MENU_GROUP = "menuGroup";
        /** If set, the action will try to be inserted just before the provided menu id. */
        public static final String INSERT_BEFORE_MENUID = "insertBeforeMenuId";
        /** If set, the action will try to be inserted just after the provided menu id. */
        public static final String INSERT_AFTER_MENUID = "insertAfterMenuId";
        /** if set, the action will be inserted first instead of last (default) */
        public static final String INSERT_FIRST = "insertFirst";
        /** Using logical group on actions will automatically create JSeparator between such groups
         *  Actions with the same logical group are created on the same toolbar also */
        public static final String LOGICAL_GROUP = "logicalGroup";
        /**  To hide a menu / toolbar item without removing the Action */
        public static final String VISIBLE = "visible";
        /** If set, other actions with the same actionGroup will be unSet if this action is set active.
         * ButtonGroup will be created by ActionCommands.
         * Setting a value will create a JRadioButton or a JRadioButtonMenu instead of JButton and JMenuItem.
         * @see Action#SELECTED_KEY
         */
        public static final String TOGGLE_GROUP = "toggleGroupName";

        /**
         * Utility class
         */
        private ActionTools() {
        }

        /**
         * Return the icon.
         * @param action Action to use
         * @return Icon instance or null
         */
        public static Icon getIcon(Action action) {
                Object val = action.getValue(Action.SMALL_ICON);
                if(val==null) {
                        return null;
                }
                return (Icon)val;
        }

        /**
         * Visible property of control linked with this action.
         * A visible at True does not mean that the user see the control, it can be hidden by other components.
         * @param action Action to read
         * @return True if visible
         */
        public static boolean isVisible(Action action) {
            Object val = action.getValue(VISIBLE);
            if(!(val instanceof Boolean)) {
                return true;
            }
            return (Boolean)val;
        }
        /**
         * Return the list of additional KeyStrokes.
         * @param action Action to use
         * @return KeyStroke list instance or null
         */
        public static List<KeyStroke> getAdditionalKeyStroke(Action action) {
                Object val = action.getValue(ADDITIONAL_ACCELERATOR_KEY);
                if(val==null) {
                        return null;
                }
                return (List<KeyStroke>)val;
        }
        /**
         * Return the KeyStroke.
         * @param action Action to use
         * @return KeyStroke instance or null
         */
        public static KeyStroke getKeyStroke(Action action) {
                Object val = action.getValue(Action.ACCELERATOR_KEY);
                if(val==null) {
                        return null;
                }
                return (KeyStroke)val;
        }
        /**
         * @param action
         * @return True if this action will create a menu group.
         */
        public static boolean isMenu(Action action) {
                return action.getValue(MENU_GROUP)!=null;
        }
        /**
         * Return the parent id.
         * @param action Action to use
         * @return parent Name or empty if placed on root.
         */
        public static String getParentMenuId(Action action) {
                Object val = action.getValue(PARENT_ID);
                if(val==null) {
                        return "";
                }
                return (String)val;
        }
        /**
         * Return the menu id.
         * @param action Action to use
         * @return menu Name or empty if not set.
         */
        public static String getMenuId(Action action) {
                Object val = action.getValue(MENU_ID);
                if(val==null) {
                        return "";
                }
                return (String)val;
        }

        /**
         * @return The other Menu id that should be after this action,
         * empty if none.
         */
        public static String getInsertBeforeMenuId(Action action) {
                Object val = action.getValue(INSERT_BEFORE_MENUID);
                if(val==null) {
                        return "";
                }
                return (String)val;
        }
        /**
         * @return The other Menu id that should be before this action,
         * empty if none.
         */
        public static String getInsertAfterMenuId(Action action) {
                Object val = action.getValue(INSERT_AFTER_MENUID);
                if(val==null) {
                        return "";
                }
                return (String)val;
        }
        /**
         * @return The ButtonGroup name to create
         */
        public static String getToggleGroup(Action action) {
                Object val = action.getValue(TOGGLE_GROUP);
                if(val==null) {
                        return "";
                }
                return (String)val;
        }
        /**
         * Using logical group on actions will automatically create JSeparator between such groups
         * @return logical group name
         */
        public static String getLogicalGroup(Action action) {
                Object val = action.getValue(LOGICAL_GROUP);
                if(val==null) {
                    return "";
                }
                return (String)val;
        }
        /**
         * @param action
         * @return True if this action should be inserted at 0 index in the menu container.
         */
        public static boolean isFirstInsertion(Action action) {
            return action.getValue(INSERT_FIRST)!=null;
        }
}
