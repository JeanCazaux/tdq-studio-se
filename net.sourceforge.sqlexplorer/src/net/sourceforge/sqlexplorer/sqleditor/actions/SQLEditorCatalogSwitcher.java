package net.sourceforge.sqlexplorer.sqleditor.actions;

import java.sql.SQLException;

import net.sourceforge.sqlexplorer.Messages;
import net.sourceforge.sqlexplorer.dbproduct.MetaDataSession;
import net.sourceforge.sqlexplorer.dbproduct.SQLConnection;
import net.sourceforge.sqlexplorer.dbproduct.User;
import net.sourceforge.sqlexplorer.plugin.SQLExplorerPlugin;
import net.sourceforge.sqlexplorer.plugin.editors.SwitchableSessionEditor;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


public class SQLEditorCatalogSwitcher extends ControlContribution {

    
    private SwitchableSessionEditor _editor;
    
    private Combo _catalogCombo;

    
    /**
     * @param editor SQLEditor to which this catalog switcher belongs
     */
    public SQLEditorCatalogSwitcher(SwitchableSessionEditor editor) {
        
        super("net.sourceforge.sqlexplorer.catalogswitcher"); //$NON-NLS-1$
        
        _editor = editor;
        
    }
    
    protected Control createControl(Composite parent) {

        _catalogCombo = new Combo(parent, SWT.READ_ONLY);
        _catalogCombo.setToolTipText(Messages.getString("SQLEditor.Actions.ChooseCatalog.ToolTip")); //$NON-NLS-1$
        _catalogCombo.setSize(200, _catalogCombo.getSize().y);
        
        _catalogCombo.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent arg0) {
                
                int selIndex = _catalogCombo.getSelectionIndex();
                String newCat = _catalogCombo.getItem(selIndex);
                if (_editor.getSession() != null) {
                    try {
                        _editor.getSession().setCatalog(newCat);
                    } catch (Exception e1) {
                        SQLExplorerPlugin.error(Messages.getString("SQLEditorCatalogSwitcher.ErrorChangeCatalog"), e1); //$NON-NLS-1$
                    }
                }
            }
        });
        
        _catalogCombo.add(""); //$NON-NLS-1$
        
        if (_editor.getSession() != null) {
                       
            try {
                String[] catalogs = getMetaDataSession().getCatalogs();
            	User user = _editor.getSession().getUser();
            	
            	// Get the connection directly from the user because the session may be busy with its one one
	            SQLConnection connection = user.getConnection();
	            try {
		            String currentCatalog = connection.getCatalog();
		            
		            for (int i = 0; i < catalogs.length; i++) {
		                _catalogCombo.add(catalogs[i]);
		                if (currentCatalog.equals(catalogs[i])) {
		                    _catalogCombo.select(_catalogCombo.getItemCount() - 1);
		                }
		            }
	            }finally {
	            	if(connection != null)
	            		user.releaseConnection(connection);
	            }
            } catch(SQLException e) {
            	SQLExplorerPlugin.error(e);
            }
        }
        
        return _catalogCombo;
    }

    private MetaDataSession getMetaDataSession() {
    	return _editor.getSession().getUser().getMetaDataSession();
    }
    
}
