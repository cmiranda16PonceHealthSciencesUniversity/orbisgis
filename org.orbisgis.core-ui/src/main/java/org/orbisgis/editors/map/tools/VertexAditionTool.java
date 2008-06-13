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
package org.orbisgis.editors.map.tools;

import java.awt.Graphics;
import java.net.URL;

import org.orbisgis.editors.map.tool.DrawingException;
import org.orbisgis.editors.map.tool.FinishedAutomatonException;
import org.orbisgis.editors.map.tool.ToolManager;
import org.orbisgis.editors.map.tool.TransitionException;
import org.orbisgis.editors.map.tools.generated.VertexAdition;
import org.orbisgis.layerModel.MapContext;

public class VertexAditionTool extends VertexAdition {

	@Override
	public void transitionTo_Standby(MapContext vc, ToolManager tm)
			throws FinishedAutomatonException, TransitionException {

	}

	@Override
	public void transitionTo_Done(MapContext vc, ToolManager tm)
			throws FinishedAutomatonException, TransitionException {
		// Point2D p = new Point2D.Double(tm.getValues()[0], tm.getValues()[1]);
		// try {
		// Geometry[] selection = vc.getSelectedGeometries();
		// for (int i = 0; i < selection.length; i++) {
		// Primitive prim = new Primitive(selection[i]);
		// Geometry g = prim.insertVertex(p, tm.getTolerance());
		// if (g != null) {
		// vc.updateGeometry(g);
		// break;
		// }
		// }
		// } catch (EditionContextException e) {
		// throw new TransitionException(e);
		// } catch (CannotChangeGeometryException e) {
		// throw new TransitionException(e);
		// }
		//
		// transition("init"); //$NON-NLS-1$
	}

	@Override
	public void transitionTo_Cancel(MapContext vc, ToolManager tm)
			throws FinishedAutomatonException, TransitionException {

	}

	@Override
	public void drawIn_Standby(Graphics g, MapContext vc, ToolManager tm)
			throws DrawingException {
		// Point2D p = tm.getLastRealMousePosition();
		// try {
		// Geometry[] selection = vc.getSelectedGeometries();
		// for (int i = 0; i < selection.length; i++) {
		// Primitive prim = new Primitive(selection[i]);
		// Geometry geom = prim.insertVertex(p, tm.getTolerance());
		// tm.addGeomToDraw(geom);
		// }
		// } catch (CannotChangeGeometryException e) {
		// throw new DrawingException(e);
		// } catch (EditionContextException e) {
		// throw new DrawingException(e);
		// }
	}

	@Override
	public void drawIn_Done(Graphics g, MapContext vc, ToolManager tm)
			throws DrawingException {

	}

	@Override
	public void drawIn_Cancel(Graphics g, MapContext vc, ToolManager tm)
			throws DrawingException {

	}

	public boolean isEnabled(MapContext vc, ToolManager tm) {
		return ToolValidationUtilities.activeSelectionGreaterThan(vc, 0)
				&& ToolValidationUtilities.isActiveLayerEditable(vc);
	}

	public boolean isVisible(MapContext vc, ToolManager tm) {
		return true;
	}

	public URL getMouseCursor() {
		return null;
	}
}
