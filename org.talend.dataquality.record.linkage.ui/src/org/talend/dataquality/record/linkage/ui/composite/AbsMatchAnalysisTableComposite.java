// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.record.linkage.ui.composite;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer;
import org.talend.dataquality.rules.KeyDefinition;
import org.talend.dataquality.rules.MatchRuleDefinition;

/**
 * created by zhao on Aug 16, 2013 Abstract table compoiste. Blocking key, matching key survivorship key table are
 * intended to be extended.
 *
 */
public abstract class AbsMatchAnalysisTableComposite extends Composite {

    protected List<String> headers = new ArrayList<String>();

    protected AbstractMatchAnalysisTableViewer tableViewer = null;

    private boolean isAddColumn = true;

    /**
     * DOC zshen MatchRuleComposite constructor comment.
     *
     * @param parent
     * @param style
     */
    public AbsMatchAnalysisTableComposite(Composite parent, int style) {
        super(parent, style);
        createContent();
    }

    /**
     * DOC zshen Comment method "createContent".
     */
    private void createContent() {
        initHeaders();
        createTable();
    }

    /**
     * DOC zshen Comment method "initHeaders".
     */
    protected abstract void initHeaders();

    /**
     * DOC zshen Comment method "createTable".
     */
    protected abstract void createTable();

    protected int getTableStyle() {
        int style = SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.HIDE_SELECTION;
        return style;
    }

    public void setInput(List<KeyDefinition> inputs) {
        tableViewer.setInput(inputs);
    }

    public Object getInput() {
        return tableViewer.getInput();
    }

    public Boolean addKeyDefinition(String column, Analysis anlaysis) {
        return tableViewer.addElement(column, anlaysis);
    }

    public Boolean addKeyDefinition(String column, MatchRuleDefinition matchRuleDef) {
        return tableViewer.addElement(column, matchRuleDef);
    }

    public void removeKeyDefinition(String column, Analysis analysis) {
        tableViewer.removeElement(column, analysis);
    }

    public void removeKeyDefinition(KeyDefinition keyDef, Analysis analysis) {
        tableViewer.removeElement(keyDef, analysis);
    }

    public void removeKeyDefinition(KeyDefinition keyDef, MatchRuleDefinition matchRuleDef) {
        tableViewer.removeElement(keyDef, matchRuleDef);
    }

    public ISelection getSelectItems() {
        return tableViewer.getSelection();
    }

    /**
     * Getter for isAddColumn.
     *
     * @return the isAddColumn
     */
    public boolean isAddColumn() {
        return this.isAddColumn;
    }

    /**
     * Sets the isAddColumn.
     *
     * @param isAddColumn the isAddColumn to set
     */
    public void setAddColumn(boolean isAddColumn) {
        this.isAddColumn = isAddColumn;
    }


}
