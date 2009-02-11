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
package net.sourceforge.sqlexplorer.dbdetail.tab;

import java.sql.DatabaseMetaData;

import net.sourceforge.sqlexplorer.Messages;
import net.sourceforge.sqlexplorer.dataset.DataSet;
import net.sourceforge.sqlexplorer.dbstructure.nodes.INode;
import net.sourceforge.sqlexplorer.dbproduct.SQLConnection;
import net.sourceforge.squirrel_sql.fw.sql.SQLDatabaseMetaData;

/**
 * @author Davy Vanherbergen
 * 
 */
public class ConnectionInfoTab extends AbstractDataSetTab {

    public String getLabelText() {
        return Messages.getString("DatabaseDetailView.Tab.ConnectionInfo"); //$NON-NLS-1$
    }

    public String getStatusMessage() {
        return Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.status") + " " + getNode().getSession().getUser().getDescription();  //$NON-NLS-1$//$NON-NLS-2$
    }
    
    public DataSet getDataSet() throws Exception {

        INode node = getNode();

        if (node == null) {
            return null;
        }

        SQLDatabaseMetaData sqlMetaData = node.getSession().getMetaData();
        DatabaseMetaData jdbcMetaData = sqlMetaData.getJDBCMetaData();

        String[] header = new String[2];
        header[0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.Property"); //$NON-NLS-1$
        header[1] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.Value"); //$NON-NLS-1$

        String[][] data = new String[124][2];

    	SQLConnection connection = node.getSession().grabConnection();
    	boolean commitOnClose = false;
        try {
        	commitOnClose = connection.getCommitOnClose();
        } finally{
        	node.getSession().releaseConnection(connection);
        }
        
            data[0][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.DatabaseProductName"); //$NON-NLS-1$
            try {data[0][1] = sqlMetaData.getDatabaseProductName();} catch (Throwable e) {}                
            data[1][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.DriverMajor"); //$NON-NLS-1$
            try {data[1][1] = "" + jdbcMetaData.getDriverMajorVersion();} catch (Throwable e) {}         //$NON-NLS-1$
            data[2][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.DriverMinor"); //$NON-NLS-1$
            try {data[2][1] = "" + jdbcMetaData.getDriverMinorVersion();} catch (Throwable e) {}         //$NON-NLS-1$
            data[3][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.DriverName"); //$NON-NLS-1$
            try {data[3][1] = "" + sqlMetaData.getDriverName();} catch (Throwable e) {} //$NON-NLS-1$
            data[4][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.DriverVersion"); //$NON-NLS-1$
            try {data[4][1] = "" + jdbcMetaData.getDriverVersion();} catch (Throwable e) {} //$NON-NLS-1$
            data[5][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.UserName"); //$NON-NLS-1$
            try {data[5][1] = "" + sqlMetaData.getUserName();} catch (Throwable e) {} //$NON-NLS-1$
            data[6][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.URL"); //$NON-NLS-1$
            try {data[6][1] = "" + jdbcMetaData.getURL();} catch (Throwable e) {} //$NON-NLS-1$
            data[7][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.AutocommitMode"); //$NON-NLS-1$
            try {data[7][1] = "" + jdbcMetaData.getConnection().getAutoCommit();} catch (Throwable e) {} //$NON-NLS-1$
            data[8][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.CommitOnClose");             //$NON-NLS-1$
            data[8][1] = "" + commitOnClose;             //$NON-NLS-1$
            data[9][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.ProceduresCallable"); //$NON-NLS-1$
            try {data[9][1] = "" + jdbcMetaData.allProceduresAreCallable();} catch (Throwable e) {} //$NON-NLS-1$
            data[10][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.TablesSelectable"); //$NON-NLS-1$
            try {data[10][1] = "" + jdbcMetaData.allTablesAreSelectable();} catch (Throwable e) {} //$NON-NLS-1$
            data[11][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.NullsSortedHigh"); //$NON-NLS-1$
            try {data[11][1] = "" + jdbcMetaData.nullsAreSortedHigh();} catch (Throwable e) {} //$NON-NLS-1$
            data[12][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.NullsSortedLow"); //$NON-NLS-1$
            try {data[12][1] = "" + jdbcMetaData.nullsAreSortedLow();} catch (Throwable e) {} //$NON-NLS-1$
            data[13][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.NullsSortedStart"); //$NON-NLS-1$
            try {data[13][1] = "" + jdbcMetaData.nullsAreSortedAtStart();} catch (Throwable e) {} //$NON-NLS-1$
            data[14][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.NullsSortedEnd"); //$NON-NLS-1$
            try {data[14][1] = "" + jdbcMetaData.nullsAreSortedAtEnd();} catch (Throwable e) {} //$NON-NLS-1$
            data[15][0] = Messages.getString("ConnectionInfoTab.ResultSetHoldability"); //$NON-NLS-1$
            try {data[15][1] = "" + jdbcMetaData.getResultSetHoldability();} catch (Throwable e) {} //$NON-NLS-1$
            data[16][0] = Messages.getString("ConnectionInfoTab.UsesLocalFiles"); //$NON-NLS-1$
            try {data[16][1] = "" + jdbcMetaData.usesLocalFiles();} catch (Throwable e) {} //$NON-NLS-1$
            data[17][0] = Messages.getString("ConnectionInfoTab.UsesLocalFilePerTable"); //$NON-NLS-1$
            try { data[17][1] = "" + jdbcMetaData.usesLocalFilePerTable();} catch (Throwable e) {} //$NON-NLS-1$
            data[18][0] = Messages.getString("ConnectionInfoTab.SupportsMixedCaseIdentifiers"); //$NON-NLS-1$
            try {data[18][1] = "" + jdbcMetaData.supportsMixedCaseIdentifiers();} catch (Throwable e) {} //$NON-NLS-1$
            data[19][0] = Messages.getString("ConnectionInfoTab.StoresUpperCase"); //$NON-NLS-1$
            try {data[19][1] = "" + jdbcMetaData.storesUpperCaseIdentifiers();} catch (Throwable e) {} //$NON-NLS-1$
            data[20][0] = Messages.getString("ConnectionInfoTab.StoresLowerCase"); //$NON-NLS-1$
            try {data[20][1] = "" + jdbcMetaData.storesLowerCaseIdentifiers();} catch (Throwable e) {} //$NON-NLS-1$
            data[21][0] = Messages.getString("ConnectionInfoTab.StoresMixedCase"); //$NON-NLS-1$
            try {data[21][1] = "" + jdbcMetaData.storesMixedCaseIdentifiers();} catch (Throwable e) {} //$NON-NLS-1$
            data[22][0] = Messages.getString("ConnectionInfoTab.SupportsMixedCase"); //$NON-NLS-1$
            try {data[22][1] = "" + jdbcMetaData.supportsMixedCaseQuotedIdentifiers();} catch (Throwable e) {} //$NON-NLS-1$
            data[23][0] = Messages.getString("ConnectionInfoTab.StoresUpperCaseQuotedIdentifiers"); //$NON-NLS-1$
            try {data[23][1] = "" + jdbcMetaData.storesUpperCaseQuotedIdentifiers();} catch (Throwable e) {} //$NON-NLS-1$
            data[24][0] = Messages.getString("ConnectionInfoTab.StoresLowerCaseQuote"); //$NON-NLS-1$
            try {data[24][1] = "" + jdbcMetaData.storesLowerCaseQuotedIdentifiers();} catch (Throwable e) {} //$NON-NLS-1$
            data[25][0] = Messages.getString("ConnectionInfoTab.StoresMixedCaseQuote"); //$NON-NLS-1$
            try {data[25][1] = "" + jdbcMetaData.storesMixedCaseQuotedIdentifiers();} catch (Throwable e) {} //$NON-NLS-1$
            data[26][0] = Messages.getString("ConnectionInfoTab.IdentifierQuote"); //$NON-NLS-1$
            try { data[26][1] = "" + jdbcMetaData.getIdentifierQuoteString();} catch (Throwable e) {} //$NON-NLS-1$
            data[27][0] = Messages.getString("ConnectionInfoTab.SearchStringEscape"); //$NON-NLS-1$
            try {data[27][1] = "" + jdbcMetaData.getSearchStringEscape();} catch (Throwable e) {} //$NON-NLS-1$
            data[28][0] = Messages.getString("ConnectionInfoTab.ExtraNameCharacters"); //$NON-NLS-1$
            try {data[28][1] = "" + jdbcMetaData.getExtraNameCharacters();} catch (Throwable e) {} //$NON-NLS-1$
            data[29][0] = Messages.getString("ConnectionInfoTab.AlterTableAddColumn"); //$NON-NLS-1$
            try { data[29][1] = "" + jdbcMetaData.supportsAlterTableWithAddColumn();} catch (Throwable e) {} //$NON-NLS-1$
            data[30][0] = Messages.getString("ConnectionInfoTab.AlterTableDropColumn"); //$NON-NLS-1$
            try {data[30][1] = "" + jdbcMetaData.supportsAlterTableWithDropColumn();} catch (Throwable e) {} //$NON-NLS-1$
            data[31][0] = Messages.getString("ConnectionInfoTab.ColumnAlias"); //$NON-NLS-1$
            try {data[31][1] = "" + jdbcMetaData.supportsColumnAliasing();} catch (Throwable e) {} //$NON-NLS-1$
            data[32][0] = Messages.getString("ConnectionInfoTab.NullPlus"); //$NON-NLS-1$
            try {data[32][1] = "" + jdbcMetaData.nullPlusNonNullIsNull();} catch (Throwable e) {} //$NON-NLS-1$
            data[33][0] = Messages.getString("ConnectionInfoTab.SupportsConvert"); //$NON-NLS-1$
            try {data[33][1] = "" + jdbcMetaData.supportsConvert();} catch (Throwable e) {} //$NON-NLS-1$
            data[34][0] = Messages.getString("ConnectionInfoTab.SupportsTableCorrelationName"); //$NON-NLS-1$
            try {data[34][1] = "" + jdbcMetaData.supportsTableCorrelationNames();} catch (Throwable e) {} //$NON-NLS-1$
            data[35][0] = Messages.getString("ConnectionInfoTab.DifferentTableCorrelationName"); //$NON-NLS-1$
            try {data[35][1] = "" + jdbcMetaData.supportsDifferentTableCorrelationNames();} catch (Throwable e) {} //$NON-NLS-1$
            data[36][0] = Messages.getString("ConnectionInfoTab.ExpressionsinOrderBy"); //$NON-NLS-1$
            try {data[36][1] = "" + jdbcMetaData.supportsExpressionsInOrderBy();} catch (Throwable e) {} //$NON-NLS-1$
            data[37][0] = Messages.getString("ConnectionInfoTab.OrderByUnrelated"); //$NON-NLS-1$
            try {data[37][1] = "" + jdbcMetaData.supportsOrderByUnrelated();} catch (Throwable e) {} //$NON-NLS-1$
            data[38][0] = Messages.getString("ConnectionInfoTab.GroupBy"); //$NON-NLS-1$
            try { data[38][1] = "" + jdbcMetaData.supportsGroupBy();} catch (Throwable e) {} //$NON-NLS-1$
            data[39][0] = Messages.getString("ConnectionInfoTab.GroupByUnrelated"); //$NON-NLS-1$
            try {data[39][1] = "" + jdbcMetaData.supportsGroupByUnrelated();} catch (Throwable e) {} //$NON-NLS-1$
            data[40][0] = Messages.getString("ConnectionInfoTab.GroupByBeyondSelect"); //$NON-NLS-1$
            try {data[40][1] = "" + jdbcMetaData.supportsGroupByBeyondSelect();} catch (Throwable e) {} //$NON-NLS-1$
            data[41][0] = Messages.getString("ConnectionInfoTab.LikeEscapeClause"); //$NON-NLS-1$
            try {data[41][1] = "" + jdbcMetaData.supportsLikeEscapeClause();} catch (Throwable e) {} //$NON-NLS-1$
            data[42][0] = Messages.getString("ConnectionInfoTab.MultipleResultSets"); //$NON-NLS-1$
            try {data[42][1] = "" + jdbcMetaData.supportsMultipleResultSets();} catch (Throwable e) {} //$NON-NLS-1$
            data[43][0] = Messages.getString("ConnectionInfoTab.MultipleOpenResults"); //$NON-NLS-1$
            try {data[43][1] = "" + jdbcMetaData.supportsMultipleOpenResults();} catch (Throwable e) {} //$NON-NLS-1$
            data[44][0] = Messages.getString("ConnectionInfoTab.MultipleTransactions"); //$NON-NLS-1$
            try {data[44][1] = "" + jdbcMetaData.supportsMultipleTransactions();} catch (Throwable e) {} //$NON-NLS-1$
            data[45][0] = Messages.getString("ConnectionInfoTab.NonNullableColumns"); //$NON-NLS-1$
            try {data[45][1] = "" + jdbcMetaData.supportsNonNullableColumns();} catch (Throwable e) {} //$NON-NLS-1$
            data[46][0] = Messages.getString("ConnectionInfoTab.MinimumSQLGrammar"); //$NON-NLS-1$
            try {data[46][1] = "" + jdbcMetaData.supportsMinimumSQLGrammar();} catch (Throwable e) {} //$NON-NLS-1$
            data[47][0] = Messages.getString("ConnectionInfoTab.CoreSQLGrammar"); //$NON-NLS-1$
            try {data[47][1] = "" + jdbcMetaData.supportsCoreSQLGrammar();} catch (Throwable e) {} //$NON-NLS-1$
            data[48][0] = Messages.getString("ConnectionInfoTab.ExtendedSQLGrammar"); //$NON-NLS-1$
            try {data[48][1] = "" + jdbcMetaData.supportsExtendedSQLGrammar();} catch (Throwable e) {} //$NON-NLS-1$
            data[49][0] = Messages.getString("ConnectionInfoTab.ANSI92EntryLevelSQL"); //$NON-NLS-1$
            try {data[49][1] = "" + jdbcMetaData.supportsANSI92EntryLevelSQL();} catch (Throwable e) {} //$NON-NLS-1$
            data[50][0] = Messages.getString("ConnectionInfoTab.ANSI92IntermediateSQL"); //$NON-NLS-1$
            try {data[50][1] = "" + jdbcMetaData.supportsANSI92IntermediateSQL();} catch (Throwable e) {} //$NON-NLS-1$
            data[51][0] = Messages.getString("ConnectionInfoTab.ANSI92FullSQL"); //$NON-NLS-1$
            try {data[51][1] = "" + jdbcMetaData.supportsANSI92FullSQL();} catch (Throwable e) {} //$NON-NLS-1$
            data[52][0] = Messages.getString("ConnectionInfoTab.IntegrityEnhancementFacility"); //$NON-NLS-1$
            try {data[52][1] = "" + jdbcMetaData.supportsIntegrityEnhancementFacility();} catch (Throwable e) {} //$NON-NLS-1$
            data[53][0] = Messages.getString("ConnectionInfoTab.OuterJoins"); //$NON-NLS-1$
            try {data[53][1] = "" + jdbcMetaData.supportsOuterJoins();} catch (Throwable e) {} //$NON-NLS-1$
            data[54][0] = Messages.getString("ConnectionInfoTab.FullOuterJoins"); //$NON-NLS-1$
            try {data[54][1] = "" + jdbcMetaData.supportsFullOuterJoins();} catch (Throwable e) {} //$NON-NLS-1$
            data[55][0] = Messages.getString("ConnectionInfoTab.LimitedOuterJoins"); //$NON-NLS-1$
            try {data[55][1] = "" + jdbcMetaData.supportsLimitedOuterJoins();} catch (Throwable e) {} //$NON-NLS-1$
            data[56][0] = Messages.getString("ConnectionInfoTab.SchemaTerm"); //$NON-NLS-1$
            try {data[56][1] = "" + jdbcMetaData.getSchemaTerm();} catch (Throwable e) {} //$NON-NLS-1$
            data[57][0] = Messages.getString("ConnectionInfoTab.ProcedureTerm"); //$NON-NLS-1$
            try {data[57][1] = "" + jdbcMetaData.getProcedureTerm();} catch (Throwable e) {} //$NON-NLS-1$
            data[58][0] = Messages.getString("ConnectionInfoTab.CatalogTerm"); //$NON-NLS-1$
            try {data[58][1] = "" + jdbcMetaData.getCatalogTerm();} catch (Throwable e) {} //$NON-NLS-1$
            data[59][0] = Messages.getString("ConnectionInfoTab.IsCatalogStart"); //$NON-NLS-1$
            try {data[59][1] = "" + jdbcMetaData.isCatalogAtStart();} catch (Throwable e) {} //$NON-NLS-1$
            data[60][0] = Messages.getString("ConnectionInfoTab.CatalogSeparator"); //$NON-NLS-1$
            try {data[60][1] = "" + jdbcMetaData.getCatalogSeparator();} catch (Throwable e) {} //$NON-NLS-1$
            data[61][0] = Messages.getString("ConnectionInfoTab.SchemasInDataManipulation"); //$NON-NLS-1$
            try {data[61][1] = "" + jdbcMetaData.supportsSchemasInDataManipulation();} catch (Throwable e) {} //$NON-NLS-1$
            data[62][0] = Messages.getString("ConnectionInfoTab.SchemasInProcedureCalls"); //$NON-NLS-1$
            try {data[62][1] = "" + jdbcMetaData.supportsSchemasInProcedureCalls();} catch (Throwable e) {} //$NON-NLS-1$
            data[63][0] = Messages.getString("ConnectionInfoTab.SchemasInTableDefinitions"); //$NON-NLS-1$
            try {data[63][1] = "" + jdbcMetaData.supportsSchemasInTableDefinitions();} catch (Throwable e) {} //$NON-NLS-1$
            data[64][0] = Messages.getString("ConnectionInfoTab.SchemasInIndexDefinitions"); //$NON-NLS-1$
            try {data[64][1] = "" + jdbcMetaData.supportsSchemasInIndexDefinitions();} catch (Throwable e) {} //$NON-NLS-1$
            data[65][0] = Messages.getString("ConnectionInfoTab.SchemasInPrivilegeDefinitions"); //$NON-NLS-1$
            try {data[65][1] = "" + jdbcMetaData.supportsSchemasInPrivilegeDefinitions();} catch (Throwable e) {} //$NON-NLS-1$
            data[66][0] = Messages.getString("ConnectionInfoTab.CatalogsInDataManipulation"); //$NON-NLS-1$
            try {data[66][1] = "" + jdbcMetaData.supportsCatalogsInDataManipulation();} catch (Throwable e) {} //$NON-NLS-1$
            data[67][0] = Messages.getString("ConnectionInfoTab.CatalogsInProcedureCalls"); //$NON-NLS-1$
            try {data[67][1] = "" + jdbcMetaData.supportsCatalogsInProcedureCalls();} catch (Throwable e) {} //$NON-NLS-1$
            data[68][0] = Messages.getString("ConnectionInfoTab.CatalogsInTableDefinitions"); //$NON-NLS-1$
            try {data[68][1] = "" + jdbcMetaData.supportsCatalogsInTableDefinitions();} catch (Throwable e) {} //$NON-NLS-1$
            data[69][0] = Messages.getString("ConnectionInfoTab.CatalogsInIndexDefinitions"); //$NON-NLS-1$
            try {data[69][1] = "" + jdbcMetaData.supportsCatalogsInIndexDefinitions();} catch (Throwable e) {} //$NON-NLS-1$
            data[70][0] = Messages.getString("ConnectionInfoTab.CatalogsInPrivilegeDefinitions"); //$NON-NLS-1$
            try {data[70][1] = "" + jdbcMetaData.supportsCatalogsInPrivilegeDefinitions();} catch (Throwable e) {} //$NON-NLS-1$
            data[71][0] = Messages.getString("ConnectionInfoTab.PositionedDelete"); //$NON-NLS-1$
            try {data[71][1] = "" + jdbcMetaData.supportsPositionedDelete();} catch (Throwable e) {} //$NON-NLS-1$
            data[72][0] = Messages.getString("ConnectionInfoTab.PositionedUpdate"); //$NON-NLS-1$
            try {data[72][1] = "" + jdbcMetaData.supportsPositionedUpdate();} catch (Throwable e) {} //$NON-NLS-1$
            data[73][0] = Messages.getString("ConnectionInfoTab.StoreProcedure"); //$NON-NLS-1$
            try {data[73][1] = "" + jdbcMetaData.supportsStoredProcedures();} catch (Throwable e) {} //$NON-NLS-1$
            data[74][0] = Messages.getString("ConnectionInfoTab.SubqueriesInComparisons"); //$NON-NLS-1$
            try {data[74][1] = "" + jdbcMetaData.supportsSubqueriesInComparisons();} catch (Throwable e) {} //$NON-NLS-1$
            data[75][0] = Messages.getString("ConnectionInfoTab.SubqueriesInExists"); //$NON-NLS-1$
            try {data[75][1] = "" + jdbcMetaData.supportsSubqueriesInExists();} catch (Throwable e) {} //$NON-NLS-1$
            data[76][0] = Messages.getString("ConnectionInfoTab.SubqueriesINStatements"); //$NON-NLS-1$
            try {data[76][1] = "" + jdbcMetaData.supportsSubqueriesInIns();} catch (Throwable e) {} //$NON-NLS-1$
            data[77][0] = Messages.getString("ConnectionInfoTab.SubqueriesInQuantifiedExpressions"); //$NON-NLS-1$
            try {data[77][1] = "" + jdbcMetaData.supportsSubqueriesInQuantifieds();} catch (Throwable e) {} //$NON-NLS-1$
            data[78][0] = Messages.getString("ConnectionInfoTab.CorrelatedSubqueries"); //$NON-NLS-1$
            try {data[78][1] = "" + jdbcMetaData.supportsCorrelatedSubqueries();} catch (Throwable e) {} //$NON-NLS-1$
            data[79][0] = Messages.getString("ConnectionInfoTab.Union"); //$NON-NLS-1$
            try {data[79][1] = "" + jdbcMetaData.supportsUnion();} catch (Throwable e) {} //$NON-NLS-1$
            data[80][0] = Messages.getString("ConnectionInfoTab.UnionAll"); //$NON-NLS-1$
            try {data[80][1] = "" + jdbcMetaData.supportsUnionAll();} catch (Throwable e) {} //$NON-NLS-1$
            data[81][0] = Messages.getString("ConnectionInfoTab.OpenCursorsAcrossCommit"); //$NON-NLS-1$
            try {data[81][1] = "" + jdbcMetaData.supportsOpenCursorsAcrossCommit();} catch (Throwable e) {} //$NON-NLS-1$
            data[82][0] = Messages.getString("ConnectionInfoTab.OpenCursorsRollback"); //$NON-NLS-1$
            try {data[82][1] = "" + jdbcMetaData.supportsOpenCursorsAcrossRollback();} catch (Throwable e) {} //$NON-NLS-1$
            data[83][0] = Messages.getString("ConnectionInfoTab.OpenStatementsCommit"); //$NON-NLS-1$
            try {data[83][1] = "" + jdbcMetaData.supportsOpenStatementsAcrossCommit();} catch (Throwable e) {} //$NON-NLS-1$
            data[84][0] = Messages.getString("ConnectionInfoTab.OpenStatementsRollback"); //$NON-NLS-1$
            try {data[84][1] = "" + jdbcMetaData.supportsOpenStatementsAcrossRollback();} catch (Throwable e) {} //$NON-NLS-1$
            data[85][0] = Messages.getString("ConnectionInfoTab.MaxBinaryLength"); //$NON-NLS-1$
            try {data[85][1] = "" + jdbcMetaData.getMaxBinaryLiteralLength();} catch (Throwable e) {} //$NON-NLS-1$
            data[86][0] = Messages.getString("ConnectionInfoTab.MaxCharLength"); //$NON-NLS-1$
            try {data[86][1] = "" + jdbcMetaData.getMaxCharLiteralLength();} catch (Throwable e) {} //$NON-NLS-1$
            data[87][0] = Messages.getString("ConnectionInfoTab.MaxColumnNameLength"); //$NON-NLS-1$
            try {data[87][1] = "" + jdbcMetaData.getMaxColumnNameLength();} catch (Throwable e) {} //$NON-NLS-1$
            data[88][0] = Messages.getString("ConnectionInfoTab.MaxColumnsInGroupBy"); //$NON-NLS-1$
            try {data[88][1] = "" + jdbcMetaData.getMaxColumnsInGroupBy();} catch (Throwable e) {} //$NON-NLS-1$
            data[89][0] = Messages.getString("ConnectionInfoTab.MaxColumnsInIndex"); //$NON-NLS-1$
            try {data[89][1] = "" + jdbcMetaData.getMaxColumnsInIndex();} catch (Throwable e) {} //$NON-NLS-1$
            data[90][0] = Messages.getString("ConnectionInfoTab.MaxColumnsInOrderBy"); //$NON-NLS-1$
            try {data[90][1] = "" + jdbcMetaData.getMaxColumnsInOrderBy();} catch (Throwable e) {} //$NON-NLS-1$
            data[91][0] = Messages.getString("ConnectionInfoTab.MaxColumnsInSelect"); //$NON-NLS-1$
            try {data[91][1] = "" + jdbcMetaData.getMaxColumnsInSelect();} catch (Throwable e) {} //$NON-NLS-1$
            data[92][0] = Messages.getString("ConnectionInfoTab.MaxColumnsInTable"); //$NON-NLS-1$
            try {data[92][1] = "" + jdbcMetaData.getMaxColumnsInTable();} catch (Throwable e) {} //$NON-NLS-1$
            data[93][0] = Messages.getString("ConnectionInfoTab.MaxConnections"); //$NON-NLS-1$
            try {data[93][1] = "" + jdbcMetaData.getMaxConnections();} catch (Throwable e) {} //$NON-NLS-1$
            data[94][0] = Messages.getString("ConnectionInfoTab.MaxCursorNameLength"); //$NON-NLS-1$
            try {data[94][1] = "" + jdbcMetaData.getMaxCursorNameLength();} catch (Throwable e) {} //$NON-NLS-1$
            data[95][0] = Messages.getString("ConnectionInfoTab.MaxIndexLength"); //$NON-NLS-1$
            try {data[95][1] = "" + jdbcMetaData.getMaxIndexLength();} catch (Throwable e) {} //$NON-NLS-1$
            data[96][0] = Messages.getString("ConnectionInfoTab.MaxSchemaNameLength"); //$NON-NLS-1$
            try {data[96][1] = "" + jdbcMetaData.getMaxSchemaNameLength();} catch (Throwable e) {} //$NON-NLS-1$
            data[97][0] = Messages.getString("ConnectionInfoTab.MaxProcedureNameLength"); //$NON-NLS-1$
            try {data[97][1] = "" + jdbcMetaData.getMaxProcedureNameLength();} catch (Throwable e) {} //$NON-NLS-1$
            data[98][0] = Messages.getString("ConnectionInfoTab.MaxCatalogName"); //$NON-NLS-1$
            try {data[98][1] = "" + jdbcMetaData.getMaxCatalogNameLength();} catch (Throwable e) {} //$NON-NLS-1$
            data[99][0] = Messages.getString("ConnectionInfoTab.MaxRowSize"); //$NON-NLS-1$
            try {data[99][1] = "" + jdbcMetaData.getMaxRowSize();} catch (Throwable e) {} //$NON-NLS-1$
            data[100][0] = Messages.getString("ConnectionInfoTab.MaxRowSizeIncludeBlobs"); //$NON-NLS-1$
            try {data[100][1] = "" + jdbcMetaData.doesMaxRowSizeIncludeBlobs();} catch (Throwable e) {} //$NON-NLS-1$
            data[101][0] = Messages.getString("ConnectionInfoTab.MaxStatementLength"); //$NON-NLS-1$
            try {data[101][1] = "" + jdbcMetaData.getMaxStatementLength();} catch (Throwable e) {} //$NON-NLS-1$
            data[102][0] = Messages.getString("ConnectionInfoTab.MaxStatements"); //$NON-NLS-1$
            try {data[102][1] = "" + jdbcMetaData.getMaxStatements();} catch (Throwable e) {} //$NON-NLS-1$
            data[103][0] = Messages.getString("ConnectionInfoTab.MaxTableName"); //$NON-NLS-1$
            try {data[103][1] = "" + jdbcMetaData.getMaxTableNameLength();} catch (Throwable e) {} //$NON-NLS-1$
            data[104][0] = Messages.getString("ConnectionInfoTab.MaxTablesInSelect"); //$NON-NLS-1$
            try {data[104][1] = "" + jdbcMetaData.getMaxTablesInSelect();} catch (Throwable e) {} //$NON-NLS-1$
            data[105][0] = Messages.getString("ConnectionInfoTab.MaxUserNameLength"); //$NON-NLS-1$
            try {data[105][1] = "" + jdbcMetaData.getMaxUserNameLength();} catch (Throwable e) {} //$NON-NLS-1$

            data[106][0] = Messages.getString("ConnectionInfoTab.DefaultTransactionIsolation"); //$NON-NLS-1$
            try {
	            int isol = jdbcMetaData.getDefaultTransactionIsolation();
	            String is = null;
	            switch (isol) {
	                case java.sql.Connection.TRANSACTION_NONE:
	                    is = "TRANSACTION_NONE"; //$NON-NLS-1$
	                    break;
	
	                case java.sql.Connection.TRANSACTION_READ_COMMITTED:
	                    is = "TRANSACTION_READ_COMMITTED"; //$NON-NLS-1$
	                    break;
	
	                case java.sql.Connection.TRANSACTION_READ_UNCOMMITTED:
	                    is = "TRANSACTION_READ_UNCOMMITTED"; //$NON-NLS-1$
	                    break;
	
	                case java.sql.Connection.TRANSACTION_REPEATABLE_READ:
	                    is = "TRANSACTION_REPEATABLE_READ"; //$NON-NLS-1$
	                    break;
	
	                case java.sql.Connection.TRANSACTION_SERIALIZABLE:
	                    is = "TRANSACTION_SERIALIZABLE"; //$NON-NLS-1$
	                    break;
	
	                default:
	                    is = ""; //$NON-NLS-1$
	                    break;
	            }
	
	            data[106][1] = is;
            } catch (Throwable e) {}
           
            data[107][0] = Messages.getString("ConnectionInfoTab.Transactions"); //$NON-NLS-1$
            try {data[107][1] = "" + jdbcMetaData.supportsTransactions();} catch (Throwable e) {} //$NON-NLS-1$
            data[108][0] = Messages.getString("ConnectionInfoTab.DataTransactions"); //$NON-NLS-1$
            try {data[108][1] = "" + jdbcMetaData.supportsDataDefinitionAndDataManipulationTransactions();} catch (Throwable e) {} //$NON-NLS-1$
            data[109][0] = Messages.getString("ConnectionInfoTab.DataManipulationTransactions"); //$NON-NLS-1$
            try {data[109][1] = "" + jdbcMetaData.supportsDataManipulationTransactionsOnly();} catch (Throwable e) {} //$NON-NLS-1$
            data[110][0] = Messages.getString("ConnectionInfoTab.DataDefinitionCausesTransaction"); //$NON-NLS-1$
            try {data[110][1] = "" + jdbcMetaData.dataDefinitionCausesTransactionCommit();} catch (Throwable e) {} //$NON-NLS-1$
            data[111][0] = Messages.getString("ConnectionInfoTab.DataDefinitionIgnored"); //$NON-NLS-1$
            try {data[111][1] = "" + jdbcMetaData.dataDefinitionIgnoredInTransactions();} catch (Throwable e) {} //$NON-NLS-1$
            data[112][0] = Messages.getString("ConnectionInfoTab.SupportBatchUpdate"); //$NON-NLS-1$
            try {data[112][1] = "" + jdbcMetaData.supportsBatchUpdates();} catch (Throwable e) {} //$NON-NLS-1$
            data[113][0] = Messages.getString("ConnectionInfoTab.Savepoints"); //$NON-NLS-1$
            try {data[113][1] = "" + jdbcMetaData.supportsSavepoints();} catch (Throwable e) {} //$NON-NLS-1$
            data[114][0] = Messages.getString("ConnectionInfoTab.SupportsNamedParameters"); //$NON-NLS-1$
            try {data[114][1] = "" + jdbcMetaData.supportsNamedParameters();} catch (Throwable e) {} //$NON-NLS-1$
            data[115][0] = Messages.getString("ConnectionInfoTab.SupportsGetGeneratedKeys"); //$NON-NLS-1$
            try {data[115][1] = "" + jdbcMetaData.supportsGetGeneratedKeys();} catch (Throwable e) {} //$NON-NLS-1$
            data[116][0] = Messages.getString("ConnectionInfoTab.DatabaseMajorVersion"); //$NON-NLS-1$
            try {data[116][1] = "" + jdbcMetaData.getDatabaseMajorVersion();} catch (Throwable e) {} //$NON-NLS-1$
            data[117][0] = Messages.getString("ConnectionInfoTab.DatabaseMinorVersion"); //$NON-NLS-1$
            try {data[117][1] = "" + jdbcMetaData.getDatabaseMinorVersion();} catch (Throwable e) {} //$NON-NLS-1$
            data[118][0] = Messages.getString("ConnectionInfoTab.JDBCMinorVersion"); //$NON-NLS-1$
            try {data[118][1] = "" + jdbcMetaData.getJDBCMinorVersion();} catch (Throwable e) {} //$NON-NLS-1$
            data[119][0] = Messages.getString("ConnectionInfoTab.JDBCMajorVersion"); //$NON-NLS-1$
            try {data[119][1] = "" + jdbcMetaData.getJDBCMajorVersion();} catch (Throwable e) {} //$NON-NLS-1$
            data[120][0] = Messages.getString("ConnectionInfoTab.SQLStateType"); //$NON-NLS-1$
            try {data[120][1] = "" + jdbcMetaData.getSQLStateType();} catch (Throwable e) {} //$NON-NLS-1$
            data[121][0] = Messages.getString("ConnectionInfoTab.LocatorsUpdateCopy"); //$NON-NLS-1$
            try {data[121][1] = "" + jdbcMetaData.locatorsUpdateCopy();} catch (Throwable e) {} //$NON-NLS-1$
            data[122][0] = Messages.getString("ConnectionInfoTab.SupportStatementPool"); //$NON-NLS-1$
            try {data[122][1] = "" + jdbcMetaData.supportsStatementPooling();} catch (Throwable e) {} //$NON-NLS-1$
            
            data[123][0] = Messages.getString("DatabaseDetailView.Tab.ConnectionInfo.ReadOnly"); //$NON-NLS-1$
            try {data[123][1] = "" + jdbcMetaData.isReadOnly();} catch (Throwable e) {} //$NON-NLS-1$
        
        DataSet dataSet = new DataSet(header, data);

        return dataSet;

    }
}
