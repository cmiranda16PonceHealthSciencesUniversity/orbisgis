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
package org.orbisgis.views.sqlConsole.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import org.orbisgis.Services;
import org.orbisgis.editorViews.toc.TransferableLayer;
import org.orbisgis.views.geocatalog.TransferableResource;
import org.orbisgis.views.sqlConsole.actions.ActionsListener;

import com.Ostermiller.Syntax.HighlightedDocument;

public class ScriptPanel extends JScrollPane implements DropTargetListener {

	private ActionsListener actionAndKeyListener;

	/** The document holding the text being edited. */
	private StyledDocument document;

	private JTextPane jTextPane;

	public ScriptPanel(final ActionsListener actionAndKeyListener, boolean sql) {
		this.actionAndKeyListener = actionAndKeyListener;
		setViewportView(getJTextPane(sql));
	}

	public JTextPane getJTextPane(boolean sql) {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			if (sql) {
				document = new SHDocument(jTextPane);
			} else {
				HighlightedDocument highlightedDocument = new HighlightedDocument();
				highlightedDocument
						.setHighlightStyle(HighlightedDocument.JAVA_STYLE);
				document = highlightedDocument;
			}
			jTextPane.setDocument(document);
			jTextPane.setDropTarget(new DropTarget(this, this));
			jTextPane.addKeyListener(actionAndKeyListener);
		}

		return jTextPane;

	}

	public void dragEnter(DropTargetDragEvent dtde) {
	}

	public void dragExit(DropTargetEvent dte) {
	}

	public void dragOver(DropTargetDragEvent dtde) {
	}

	public void drop(DropTargetDropEvent dtde) {
		final Transferable t = dtde.getTransferable();
		String query = null;

		try {
			if ((t.isDataFlavorSupported(TransferableResource
					.getResourceFlavor()))
					|| (t.isDataFlavorSupported(TransferableLayer
							.getLayerFlavor()))) {
				dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				String s = (String) t.getTransferData(DataFlavor.stringFlavor);
				dtde.getDropTargetContext().dropComplete(true);
				query = s;
			} else if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				String s = (String) t.getTransferData(DataFlavor.stringFlavor);
				dtde.getDropTargetContext().dropComplete(true);
				query = s;
			}
		} catch (IOException e) {
			dtde.rejectDrop();
		} catch (UnsupportedFlavorException e) {
			dtde.rejectDrop();
		}

		if (query != null) {
			// Cursor position
			int position = jTextPane.viewToModel(dtde.getLocation());
			try {
				jTextPane.getDocument().insertString(position, query, null);
			} catch (BadLocationException e) {
				Services.getErrorManager().error("Cannot place the text there", e);
			}
		}
		dtde.rejectDrop();

		actionAndKeyListener.setButtonsStatus();
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
	}

	public void setText(String text) {
		jTextPane.setText(text);
	}

	public String getText() {
		return jTextPane.getText();
	}

	public void insertString(String string) throws BadLocationException {
		document.insertString(document.getLength(), string, null);
	}
}