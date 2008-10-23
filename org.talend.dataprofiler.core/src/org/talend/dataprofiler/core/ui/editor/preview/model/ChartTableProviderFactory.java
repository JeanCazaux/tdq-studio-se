// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.editor.preview.model;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.talend.dataprofiler.core.ImageLib;
import org.talend.dataprofiler.core.ui.utils.TextFormatFactory;
import org.talend.dataquality.indicators.Indicator;
import org.talend.dq.indicators.preview.EIndicatorChartType;
import org.talend.dq.indicators.preview.table.ChartDataEntity;
import org.talend.dq.indicators.preview.table.PatternChartDataEntity;

/**
 * DOC zqin class global comment. Detailled comment
 */
public class ChartTableProviderFactory {

    static ITableLabelProvider createLabelProvider(EIndicatorChartType chartType) {

        switch (chartType) {
        case FREQUENCE_STATISTICS:
            return new FrequencyLabelProvider();
        case MODE_INDICATOR:
            return new ModeLabelProvider();
        case SQL_PATTERN_MATCHING:
        case PATTERN_MATCHING:
            return new PatternLabelProvider();
        case TEXT_STATISTICS:
        case SIMPLE_STATISTICS:
            return new SimpleLabelProvider();
        case SUMMARY_STATISTICS:
            return new SummaryLabelProvider();
        default:
            return new BaseChartTableLabelProvider();
        }
    }

    static IStructuredContentProvider createContentProvider(EIndicatorChartType chartType) {
        return new CommonContenteProvider();
    }

    /**
     * DOC zqin ChartTableFactory class global comment. Detailled comment
     */
    static class BaseChartTableLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider {

        public Image getColumnImage(Object element, int columnIndex) {

            if (isOutsideValueRange(element, columnIndex)) {
                return ImageLib.getImage(ImageLib.LEVEL_WARNING);
            }

            return null;
        }

        public String getColumnText(Object element, int columnIndex) {

            return "";
        }

        public Color getBackground(Object element, int columnIndex) {

            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ITableColorProvider#getForeground(java.lang.Object, int)
         */
        public Color getForeground(Object element, int columnIndex) {

            if (isOutsideValueRange(element, columnIndex)) {
                return Display.getDefault().getSystemColor(SWT.COLOR_RED);
            }

            return null;
        }

        private boolean isOutsideValueRange(Object element, int columnIndex) {
            ChartDataEntity entity = (ChartDataEntity) element;
            Indicator indicator = entity.getIndicator();
            String value = entity.getValue();

            String currentValue = getColumnText(element, columnIndex);
            return ChartTableFactory.getToolTipMsg(indicator, value) != null && value.equals(currentValue);
        }
    }

    /**
     * DOC zqin ChartTableFactory class global comment. Detailled comment
     */
    static class SimpleLabelProvider extends BaseChartTableLabelProvider {

        public String getColumnText(Object element, int columnIndex) {
            ChartDataEntity entity = (ChartDataEntity) element;

            switch (columnIndex) {
            case 0:
                return entity.getLabel();
            case 1:
                return entity.getValue();
            case 2:
                return TextFormatFactory.createStandardPercent(entity.getPersent());
            default:
                return "";
            }
        }

    }

    /**
     * DOC zqin ChartTableFactory class global comment. Detailled comment
     */
    static class FrequencyLabelProvider extends BaseChartTableLabelProvider {

        public String getColumnText(Object element, int columnIndex) {
            ChartDataEntity entity = (ChartDataEntity) element;

            switch (columnIndex) {
            case 0:
                return entity.getLabel();
            case 1:
                return entity.getValue();
            case 2:
                return TextFormatFactory.createStandardPercent(entity.getPersent());
            default:
                return "";
            }
        }

    }

    /**
     * DOC zqin ChartTableFactory class global comment. Detailled comment
     */
    static class SummaryLabelProvider extends BaseChartTableLabelProvider {

        public String getColumnText(Object element, int columnIndex) {
            ChartDataEntity entity = (ChartDataEntity) element;

            switch (columnIndex) {
            case 0:
                return entity.getLabel();
            case 1:
                return entity.getValue();

            default:
                return "";
            }
        }

    }

    /**
     * DOC zqin ChartTableFactory class global comment. Detailled comment
     */
    static class PatternLabelProvider extends BaseChartTableLabelProvider {

        public String getColumnText(Object element, int columnIndex) {
            PatternChartDataEntity entity = (PatternChartDataEntity) element;

            switch (columnIndex) {
            case 0:
                return entity.getLabel();
            case 1:
                return TextFormatFactory.createStandardPercent(entity.getPerMatch());
            case 2:
                return TextFormatFactory.createStandardPercent(entity.getPerNoMatch());
            case 3:
                return entity.getNumMatch();
            case 4:
                return entity.getNumNoMatch();

            default:
                return "";
            }
        }

    }

    /**
     * DOC zqin ChartTableFactory class global comment. Detailled comment
     */
    static class ModeLabelProvider extends BaseChartTableLabelProvider {

        @Override
        public String getColumnText(Object element, int columnIndex) {
            ChartDataEntity entity = (ChartDataEntity) element;

            return entity.getValue();
        }

    }

    /**
     * DOC zqin ChartTableFactory class global comment. Detailled comment
     */
    static class CommonContenteProvider implements IStructuredContentProvider {

        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof ChartWithData) {
                return ((ChartWithData) inputElement).getEnity();
            } else {
                return new Object[0];
            }
        }

        public void dispose() {
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

    }
}
