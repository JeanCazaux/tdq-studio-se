package net.sourceforge.sqlexplorer.history.actions;

import net.sourceforge.sqlexplorer.IConstants;
import net.sourceforge.sqlexplorer.Messages;
import net.sourceforge.sqlexplorer.connections.ConnectionsView;
import net.sourceforge.sqlexplorer.dbproduct.Alias;
import net.sourceforge.sqlexplorer.dbproduct.User;
import net.sourceforge.sqlexplorer.history.SQLHistoryElement;
import net.sourceforge.sqlexplorer.plugin.SQLExplorerPlugin;
import net.sourceforge.sqlexplorer.plugin.actions.OpenPasswordConnectDialogAction;
import net.sourceforge.sqlexplorer.plugin.editors.SQLEditor;
import net.sourceforge.sqlexplorer.plugin.editors.SQLEditorInput;
import net.sourceforge.sqlexplorer.util.ImageUtil;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPage;

public class OpenInEditorAction extends AbstractHistoryContextAction {

    private ImageDescriptor _imageOpenInEditor = ImageUtil.getDescriptor("Images.OpenSQLIcon"); //$NON-NLS-1$


    public ImageDescriptor getImageDescriptor() {

        return _imageOpenInEditor;
    }


    public String getText() {

        return Messages.getString("SQLHistoryView.OpenInEditor"); //$NON-NLS-1$
    }


    public boolean isEnabled() {

        TableItem[] ti = _table.getSelection();
        if (ti == null || ti.length == 0) {
            return false;
        }
        return true;
    }


    public void run() {

        try {
            TableItem[] ti = _table.getSelection();
            if (ti == null || ti.length == 0) {
                return;
            }

            String queryDelimiter = SQLExplorerPlugin.getDefault().getPluginPreferences().getString(
                    IConstants.SQL_QRY_DELIMITER);
            StringBuffer copiedText = new StringBuffer();

            for (int i = 0; i < ti.length; i++) {

                SQLHistoryElement el = (SQLHistoryElement) ti[i].getData();
                copiedText.append(el.getRawSQLString());

                if (ti.length > 0) {
                    copiedText.append(queryDelimiter);
                    copiedText.append("\n"); //$NON-NLS-1$
                }
            }

            SQLHistoryElement sqlHistoryElement = (SQLHistoryElement) ti[0].getData();
            User user = sqlHistoryElement.getUser();
            Alias alias;
            if (user != null)
            	alias = user.getAlias();
            else {
            	alias = sqlHistoryElement.getAlias();
            	if (alias != null)
            		user = alias.getDefaultUser();
            	if (user == null) {
        	        ConnectionsView view = SQLExplorerPlugin.getDefault().getConnectionsView();
        	        if (view != null)
                		user = view.getDefaultUser();
            	}
            }

            if (user != null && !user.hasAuthenticated()) {
                boolean okToOpen = MessageDialog.openConfirm(_table.getShell(),
                        Messages.getString("SQLHistoryView.OpenInEditor.Confirm.Title"), //$NON-NLS-1$
                        Messages.getString("SQLHistoryView.OpenInEditor.Confirm.Message.Prefix") + " " //$NON-NLS-1$ //$NON-NLS-2$
                                + user.getDescription()
                                + Messages.getString("SQLHistoryView.OpenInEditor.Confirm.Message.Postfix")); //$NON-NLS-1$

                if (okToOpen) {
                	OpenPasswordConnectDialogAction openDlgAction = new OpenPasswordConnectDialogAction(alias, user, false);
                	openDlgAction.run();
                }
            }

            SQLEditorInput input = new SQLEditorInput("SQL Editor (" + SQLExplorerPlugin.getDefault().getEditorSerialNo() + ").sql"); //$NON-NLS-1$ //$NON-NLS-2$
            input.setUser(user);
            IWorkbenchPage page = SQLExplorerPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
            if (page == null)
                return;
            SQLEditor editorPart = (SQLEditor) page.openEditor(input, SQLEditor.class.getName());
            editorPart.setText(copiedText.toString());

        } catch (Throwable e) {
            SQLExplorerPlugin.error(Messages.getString("OpenInEditorAction.ErrorCreatSqlEditor"), e); //$NON-NLS-1$
        }
    }
}
