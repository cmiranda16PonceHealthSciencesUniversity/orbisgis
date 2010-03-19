package org.orbisgis.core.ui.pluginSystem.workbench;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.orbisgis.core.Services;
import org.orbisgis.core.images.IconLoader;
import org.orbisgis.core.ui.components.DropDownButton;
import org.orbisgis.core.ui.editor.IEditor;
import org.orbisgis.core.ui.editors.map.MapControl;
import org.orbisgis.core.ui.editors.map.tool.Automaton;
import org.orbisgis.core.ui.editors.map.tool.TransitionException;
import org.orbisgis.core.ui.pluginSystem.AbstractPlugIn;
import org.orbisgis.core.ui.pluginSystem.PlugIn;
import org.orbisgis.core.ui.pluginSystem.PlugInContext;
import org.orbisgis.core.ui.pluginSystem.menu.MenuSeparator;
import org.orbisgis.core.ui.plugins.views.MapEditorPlugIn;
import org.orbisgis.core.ui.plugins.views.editor.EditorManager;

public class WorkbenchToolBar extends EnableableToolBar implements Observer {

	private WorkbenchContext context;	
	private Map<String, WorkbenchToolBar> toolbars = new HashMap<String, WorkbenchToolBar>();
	private DropDownButton dropDownButton;
	private JPopupMenu popup = new JPopupMenu();
	private boolean dropDown;
	
	private List<Observer> toolsPlugInObservers = new ArrayList<Observer>();
	
	
	public DropDownButton getDropDownButton() {
		return dropDownButton;
	}

	public WorkbenchToolBar(WorkbenchContext workbenchContext) {
		this.context = workbenchContext;
		
	}

	public Map<String, WorkbenchToolBar> getToolbars() {
		return toolbars;
	}

	public boolean haveAnOtherToolBar() {
		return toolbars.size() > 0 ? true : false;
	}

	public WorkbenchToolBar(WorkbenchContext workbenchContext, String name) {
		super(name);
		this.context = workbenchContext;
		setOpaque(false);
	}
	
	
	public WorkbenchToolBar(WorkbenchContext workbenchContext, 
			String name, String iconFile, boolean dropDown) {
		super(name);
		this.context = workbenchContext;
		this.dropDown = dropDown;
		dropDownButton = new DropDownButton(iconFile) {		
			
			protected JPopupMenu getPopupMenu() {
				return popup;
			}
		};		
		dropDownButton.addToToolBar(this);
		dropDownButton.setVisible(false);		
		dropDownButton.setEnabled(false);
		setOpaque(false);
	}

	public void addPanelPlugIn(final PlugIn plugIn, Component c) {
		toolsPlugInObservers.add(plugIn);
		add(c, plugIn);
	}
	
	public void addPlugIn(final PlugIn plugIn, Component c, 
			PlugInContext plugInContext) {	
		plugIn.setPlugInContext(plugInContext);
		toolsPlugInObservers.add(plugIn);
		((JButton) c).addActionListener(AbstractPlugIn
				.toActionListener(plugIn, context));
		add(c, plugIn);
	}
/*	
	public void addDropPlugIn(final PlugIn plugIn, Component c) {
		context.addObserver((Observer) plugIn);
		c.setName(plugIn.getName());
		((JMenuItem) c).addActionListener(AbstractPlugIn
				.toActionListener(plugIn, context));
		popup.add(c);		
	}*/

	protected void addImpl(Component comp, final Object constraints, int index) {
		if (constraints instanceof Automaton) {

		} else {
			if (comp instanceof JComboBox) {
				((JComboBox) comp).addItemListener(AbstractPlugIn
						.toItemListener((PlugIn) constraints, context));
			} else if (comp instanceof JToolBar) {
				// TODO : For the moment tool bar is not floatable. This resolve
				// a problem, but not the solution
				// Maybe we'll extend JToolbar parent to modify toolbar
				// comportment.
				// We 'll working on at UI review moment.
				// PROBLEM :Consider toolbars : "Map Edition tools",
				// "Table Edition tools".
				// User is on map Editor and this toolbar is out the frame (Map
				// Edition toolbar).
				// when he switches to table editor : All MaJToggleButtonp tools are not
				// enabled in "Map Edition toolbar"
				// But This toolbar is always displayed.
				((WorkbenchToolBar) comp).setFloatable(false);
				toolbars.put(comp.getName(), (WorkbenchToolBar) comp);
				toolsPlugInObservers.add((WorkbenchToolBar) comp);
			} else if (comp instanceof JPanel) {
				Component actionComponent = (Component) ((AbstractPlugIn) constraints)
						.getActionComponent();
				String typeListener = (String) ((AbstractPlugIn) constraints)
						.getTypeListener();
				if (actionComponent != null) {
					if (typeListener.equals("item"))
						((JComboBox) actionComponent)
								.addItemListener(AbstractPlugIn.toItemListener(
										(AbstractPlugIn) constraints, context));
					else if (typeListener.equals("action"))
						((JButton) actionComponent)
								.addActionListener(AbstractPlugIn
										.toActionListener(
												(AbstractPlugIn) constraints,
												context));
				}
			}
		}
		super.addImpl(comp, constraints, index);
	}

	// TOOLBAR Automaton
	public AbstractButton addAutomaton(final Automaton automaton, String icon) {
		AbstractButton c = null;
		if(dropDown) {
			c = new JMenuItem() {
				public String getToolTipText(MouseEvent event) {
					return automaton.getName();
				}
			};
			popup.add(c);
		}
		else {
			c = new JToggleButton() {
				public String getToolTipText(MouseEvent event) {
					return automaton.getName();
				}
			};
		}
		c.setIcon(IconLoader.getIcon(icon));
		return addCursorAutomaton(automaton.getName(), automaton, c, icon);
	}

	private AbstractButton addCursorAutomaton(String tooltip,
			final Automaton automaton, final AbstractButton button, final String icon) {
		toolsPlugInObservers.add(automaton);
		add(button, dropDown, tooltip, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditorManager em = (EditorManager) Services
						.getService(EditorManager.class);
				IEditor editor = em.getActiveEditor();
				MapEditorPlugIn mapEditor = (MapEditorPlugIn) editor;
				if (mapEditor.getComponent() != null) {
					try {						
						((MapControl) mapEditor.getComponent())
								.setTool(automaton);						
						WorkbenchContext wbContext = Services.getService(WorkbenchContext.class);				
						wbContext.getWorkbench().getFrame().getWorkbenchToolBar().clearSelection();
						if(dropDown){
							dropDownButton.setSelected(true);
							dropDownButton.setIconFile(icon);
						}						
						wbContext.setLastAction("Set Tool");
					} catch (TransitionException e1) {
						Services.getErrorManager().error(
								"cannot add Automaton", e1);
					}
				}
			}
		}, automaton);
		automaton.setButton(button);
		return button;
	}

	protected void clearSelection() {
		for (int i = 0; i < getComponentCount(); i++) {
			if(((WorkbenchToolBar) getComponent(i)).getDropDownButton() != null) {
				((WorkbenchToolBar) getComponent(i)).getDropDownButton().setSelected(false);
			}
		}		
	}

	public void update(Observable arg0, Object arg1) {	
		this.setVisible(false);	
		for (int i = 0; i < getComponentCount(); i++) {
			if (getComponent(i).isEnabled() 
					&& getComponent(i).isVisible()
					&& !(getComponent(i) instanceof Separator) ) {
				this.setVisible(true);				
			}			
		}
		if(dropDownButton != null) {
			dropDownButton.setEnabled(false);
			for (int j=0 ; j<popup.getComponentCount(); j++) {
				if (popup.getComponent(j).isEnabled() 
						&& popup.getComponent(j).isVisible()
						&& !(popup.getComponent(j) instanceof Separator)) {
					this.setVisible(true);	
					dropDownButton.setVisible(true);
					dropDownButton.setEnabled(true);
				}
			}
		}
	}
	
	public List<Observer> getToolsPlugInObservers(){
		return toolsPlugInObservers;
	}	
}