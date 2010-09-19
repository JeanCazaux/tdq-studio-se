// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.cwm.compare.ui.actions.provider;

import java.util.Iterator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.TreeSelection;
import org.talend.cwm.compare.i18n.Messages;
import org.talend.cwm.compare.ui.actions.SelectedComparisonAction;
import org.talend.cwm.db.connection.ConnectionUtils;
import org.talend.dataprofiler.core.ui.action.provider.AbstractCommonActionProvider;

/**
 * 
 * DOC mzhao class global comment. Compare selected model elements.
 */
public class SelectedCompareUIProvider extends AbstractCommonActionProvider {

    private static final String COMPARISON_MENUTEXT = Messages.getString("SelectedCompareUIProvider.Comparison"); //$NON-NLS-1$

    private SelectedComparisonAction selectionCompareAction = null;

    public SelectedCompareUIProvider() {
        selectionCompareAction = new SelectedComparisonAction(COMPARISON_MENUTEXT);

    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        Iterator iter = ((TreeSelection) this.getContext().getSelection()).iterator();
        // remove the "Database Compare" menu when the object is a mdm connection
        while (iter.hasNext()) {
            Object obj = iter.next();
            if (ConnectionUtils.isMdmConnection(obj)) {
                return;
            }
        }
        TreeSelection treeSelection = (TreeSelection) getContext().getSelection();
        if (treeSelection == null) {
            return;
        }
        Object[] selectedObj = treeSelection.toArray();
        if (selectedObj.length < 2) {
            return;
        }
        selectionCompareAction.refreshSelectedObj(selectedObj[0], selectedObj[1]);
        menu.add(selectionCompareAction);

    }

}
