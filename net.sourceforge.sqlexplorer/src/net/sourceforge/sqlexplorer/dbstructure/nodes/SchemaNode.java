/*
 * Copyright (C) 2006 Davy Vanherbergen
 * dvanherbergen@users.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.sourceforge.sqlexplorer.dbstructure.nodes;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.sqlexplorer.Messages;
import net.sourceforge.sqlexplorer.dbproduct.Alias;
import net.sourceforge.sqlexplorer.dbproduct.MetaDataSession;
import net.sourceforge.sqlexplorer.plugin.SQLExplorerPlugin;
import net.sourceforge.sqlexplorer.util.ImageUtil;
import net.sourceforge.sqlexplorer.util.TextUtil;
import net.sourceforge.squirrel_sql.fw.sql.ITableInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

public class SchemaNode extends AbstractNode {

    private List _childNames = new ArrayList();

    private String[] _filteredNames;

    private static final Log _logger = LogFactory.getLog(SchemaNode.class);


    /**
     * Create new database Schema node.
     * 
     * @param parent node
     * @param name of this node
     * @param sessionNode session for this node
     */
    public SchemaNode(INode parent, String name, MetaDataSession sessionNode) {
    	super(parent, name, sessionNode, "schema"); //$NON-NLS-1$
        setImageKey("Images.SchemaNodeIcon"); //$NON-NLS-1$
    }


    private void addExtensionNodes() {

        String databaseProductName = getSession().getRoot().getDatabaseProductName().toLowerCase().trim();

        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint("net.sourceforge.sqlexplorer", "node"); //$NON-NLS-1$ //$NON-NLS-2$
        IExtension[] extensions = point.getExtensions();

        for (int i = 0; i < extensions.length; i++) {

            IExtension e = extensions[i];

            IConfigurationElement[] ces = e.getConfigurationElements();

            for (int j = 0; j < ces.length; j++) {
                try {

                    // include only nodes that are attachted to the schema
                    // node..
                    String parent = ces[j].getAttribute("parent-node"); //$NON-NLS-1$
                    if (parent.indexOf("schema") == -1) { //$NON-NLS-1$
                        continue;
                    }

                    boolean isValidProduct = false;
                    String[] validProducts = ces[j].getAttribute("database-product-name").split(",");  //$NON-NLS-1$//$NON-NLS-2$

                    // include only nodes valid for this database
                    for (int k = 0; k < validProducts.length; k++) {

                        String product = validProducts[k].toLowerCase().trim();

                        if (product.length() == 0) {
                            continue;
                        }

                        if (product.equals("*")) { //$NON-NLS-1$
                            isValidProduct = true;
                            break;
                        }

                        String regex = TextUtil.replaceChar(product, '*', ".*"); //$NON-NLS-1$
                        if (databaseProductName.matches(regex)) {
                            isValidProduct = true;
                            break;
                        }

                    }

                    if (!isValidProduct) {
                        continue;
                    }

                    String imagePath = ces[j].getAttribute("icon"); //$NON-NLS-1$
                    String id = ces[j].getAttribute("id"); //$NON-NLS-1$
                    String type = ces[j].getAttribute("table-type").trim(); //$NON-NLS-1$

                    AbstractNode childNode = (AbstractNode) ces[j].createExecutableExtension("class"); //$NON-NLS-1$
                    childNode.setParent(this);
                    childNode.setSession(_session);
                    childNode.setType(type);

                    String fragmentId = id.substring(0, id.indexOf('.', 28));
                    if (imagePath != null && imagePath.trim().length() != 0) {
                        childNode.setImage(ImageUtil.getFragmentImage(fragmentId, imagePath));
                    }

                    _childNames.add(childNode.getLabelText());
                    if (!isExcludedByFilter(childNode.getLabelText())) {
                        addChildNode(childNode);
                    }

                } catch (Throwable ex) {
                    SQLExplorerPlugin.error(Messages.getString("SchemaNode.NotCreateChildNode"), ex); //$NON-NLS-1$
                }
            }
        }

    }


    /**
     * Location extenstion nodes for a given tableType
     * 
     * @param tableType for which to find extension node
     * @return INode or null if no extensions found
     */
    private INode findExtensionNode(String tableType) {

        String databaseProductName = getSession().getRoot().getDatabaseProductName().toLowerCase().trim();

        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint("net.sourceforge.sqlexplorer", "node"); //$NON-NLS-1$ //$NON-NLS-2$
        IExtension[] extensions = point.getExtensions();

        for (int i = 0; i < extensions.length; i++) {

            IExtension e = extensions[i];

            IConfigurationElement[] ces = e.getConfigurationElements();

            for (int j = 0; j < ces.length; j++) {
                try {

                    // include only nodes that are attachted to the schema
                    // node..
                    String parent = ces[j].getAttribute("parent-node"); //$NON-NLS-1$
                    if (parent.indexOf("schema") == -1) { //$NON-NLS-1$
                        continue;
                    }

                    boolean isValidProduct = false;
                    String[] validProducts = ces[j].getAttribute("database-product-name").split(",");  //$NON-NLS-1$//$NON-NLS-2$

                    // include only nodes valid for this database
                    for (int k = 0; k < validProducts.length; k++) {

                        String product = validProducts[k].toLowerCase().trim();

                        if (product.length() == 0) {
                            continue;
                        }

                        if (product.equals("*")) { //$NON-NLS-1$
                            isValidProduct = true;
                            break;
                        }

                        String regex = TextUtil.replaceChar(product, '*', ".*"); //$NON-NLS-1$
                        if (databaseProductName.matches(regex)) {
                            isValidProduct = true;
                            break;
                        }

                    }

                    if (!isValidProduct) {
                        continue;
                    }

                    // check if it is the correct type
                    String type = ces[j].getAttribute("table-type").trim(); //$NON-NLS-1$
                    if (!type.equalsIgnoreCase(tableType)) {
                        continue;
                    }

                    AbstractNode childNode = (AbstractNode) ces[j].createExecutableExtension("class"); //$NON-NLS-1$
                    childNode.setParent(this);
                    childNode.setSession(_session);

                    return childNode;

                } catch (Throwable ex) {
                    SQLExplorerPlugin.error(Messages.getString("SchemaNode.NotCreateChildNode"), ex); //$NON-NLS-1$
                }
            }
        }

        return null;
    }


    public String[] getChildNames() {

        if (_childNames.size() == 0) {
            getChildNodes();
        }
        return (String[]) _childNames.toArray(new String[] {});
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.sqlexplorer.dbstructure.nodes.INode#getUniqueIdentifier()
     */
    public String getUniqueIdentifier() {

        return getQualifiedName();
    }


    /**
     * Checks if a node name should be filtered.
     * 
     * @param name to check for filtering
     * @return true if the name should be filtered
     */
    protected boolean isExcludedByFilter(String name) {

        if (_filteredNames == null) {
            String filter = ((Alias) getSession().getUser().getAlias()).getFolderFilterExpression();
            if (filter != null) {
                _filteredNames = filter.split(","); //$NON-NLS-1$
            }
        }
        if (_filteredNames == null || _filteredNames.length == 0) {
            // no active filter
            return false;
        }

        for (int i = 0; i < _filteredNames.length; i++) {

            if (_filteredNames[i].equalsIgnoreCase(name)) {
                // we have a match, exclude node..
                return true;
            }
        }

        // no match found
        return false;

    }


    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.sqlexplorer.dbstructure.nodes.AbstractNode#loadChildren()
     */
    public void loadChildren() {

        _childNames = new ArrayList();

        try {

            ITableInfo[] tables = null;
            String[] tableTypes = _session.getMetaData().getTableTypes();

            try {
                tables = _session.getMetaData().getTables(_name, _name, "%", tableTypes, null); //$NON-NLS-1$
            } catch (Throwable e) {
                _logger.debug("Loading all tables at once is not supported");
            }

            for (int i = 0; i < tableTypes.length; ++i) {

                INode childNode = findExtensionNode(tableTypes[i]);

                if (childNode != null) {
                    _childNames.add(childNode.getLabelText());
                    if (!isExcludedByFilter(childNode.getLabelText())) {
                        addChildNode(childNode);
                    }
                } else {
                    TableFolderNode node = new TableFolderNode(this, tableTypes[i], _session, tables);
                    _childNames.add(node.getLabelText());
                    if (!isExcludedByFilter(node.getLabelText())) {
                        addChildNode(node);
                    }
                }
            }

            // load extension nodes
            addExtensionNodes();

        } catch (Throwable e) {

            SQLExplorerPlugin.error("Could not load child nodes for " + _name, e); //$NON-NLS-1$
        }

    }
}
