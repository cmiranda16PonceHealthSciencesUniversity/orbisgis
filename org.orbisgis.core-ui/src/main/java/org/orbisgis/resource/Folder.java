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
package org.orbisgis.resource;

import javax.swing.Icon;

import org.orbisgis.images.IconLoader;

public class Folder implements IResourceType {

	private final Icon emptyIcon = IconLoader.getIcon("folder.png");

	private final Icon openIcon = IconLoader.getIcon("folder_magnify.png");

	public Icon getIcon(INode node, boolean isExpanded) {
		Icon icon = emptyIcon;
		if (node.getChildCount() != 0) {
			if (!isExpanded) {
				icon = openIcon;
			}

		}

		return icon;
	}

	public void addToTree(INode parent, INode toAdd)
			throws ResourceTypeException {
		if (parent.getResourceType() instanceof Folder) {
			parent.addNode(toAdd);
		} else {
			throw new ResourceTypeException(
					"The folder cannot be added to a node of type "
							+ parent.getResourceType().getClass()
									.getCanonicalName());
		}
	}

	public void moveResource(INode src, INode dst) throws ResourceTypeException {
		if (dst.getResourceType() instanceof Folder) {
			src.getParent().removeNode(src);
			dst.addNode(src);
		} else {
			throw new ResourceTypeException(
					"The folder cannot be moved to a node of type "
							+ dst.getResourceType().getClass()
									.getCanonicalName());
		}
	}

	public void removeFromTree(INode toRemove) throws ResourceTypeException {
		IResource folderResource = ((IResource)toRemove);
		IResource[] children = folderResource.getResources();
		for (IResource node : children) {
			folderResource.removeResource( node);
		}
		toRemove.getParent().removeNode(toRemove);
	}

	public void setName(INode node, String newName)
			throws ResourceTypeException {
		node.setName(newName);
	}

}
