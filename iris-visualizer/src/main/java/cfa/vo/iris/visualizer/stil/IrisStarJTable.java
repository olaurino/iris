/**
 * Copyright (C) 2016 Smithsonian Astrophysical Observatory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cfa.vo.iris.visualizer.stil;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import java.awt.Rectangle;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import cfa.vo.iris.sed.stil.SegmentColumn.Column;
import cfa.vo.iris.utils.UTYPE;
import cfa.vo.iris.visualizer.stil.tables.ColumnInfoMatcher;
import cfa.vo.iris.visualizer.stil.tables.IrisStarTable;
import cfa.vo.iris.visualizer.stil.tables.SegmentColumnInfoMatcher;
import cfa.vo.iris.visualizer.stil.tables.StackedStarTable;
import uk.ac.starlink.table.ColumnInfo;
import uk.ac.starlink.table.StarTable;
import uk.ac.starlink.table.gui.StarJTable;
import uk.ac.starlink.table.gui.StarTableColumn;
import uk.ac.starlink.ttools.jel.ColumnIdentifier;

/**
 * Simple bean wrapper for the StarJTable class.
 *
 */
public class IrisStarJTable extends StarJTable {
    
    private static final long serialVersionUID = -6504087912203707631L;
    private static final Logger logger = Logger.getLogger(IrisStarJTable.class.getName());
    
    // Use ColumnNames or Utypes in the column header fields
    private boolean utypeAsNames = false;
    
    // Use the plotter data star tables or the metadata star tables
    private boolean usePlotterDataTables;
    
    // Default sorting behavior. If true columns will sort by spectral data value by default.
    private boolean sortBySpecValues;

    private ColumnInfoMatcher columnInfoMatcher;
    private List<IrisStarTable> selectedStarTables;

    public IrisStarJTable() {
        super(false);
        
        // By default we use use the plotter data, as it is generally available first
        columnInfoMatcher = new SegmentColumnInfoMatcher();
        usePlotterDataTables = true;
        
        // Tables support sorting
        setAutoCreateRowSorter(true);
    }
    
    /**
     * 
     * GETTERS AND SETTERS
     * 
     */
    
    public List<IrisStarTable> getSelectedStarTables() {
        return selectedStarTables;
    }

    /**
     * Updates the selected list of star tables and creates a new stacked star table to represent
     * the concatenated tables in a single JTable.
     * 
     * @param selectedStarTables
     */
    public void setSelectedStarTables(List<IrisStarTable> selectedStarTables) {
        if (selectedStarTables == null) return;
        
        // Include the index column for non-null/non-empty star tables.
        boolean showIndex = (selectedStarTables.size() > 0);
        
        this.selectedStarTables = selectedStarTables;
        List<StarTable> dataTables = new LinkedList<>();
        
        for (IrisStarTable table : selectedStarTables) {
            if (usePlotterDataTables) {
                dataTables.add(table.getPlotterDataTable());
            } else {
                dataTables.add(table.getSegmentMetadataTable());
            }
        }
        
        this.setStarTable(new StackedStarTable(dataTables, columnInfoMatcher), showIndex);
        IrisStarJTable.configureColumnWidths(this, 200, 20);
        
        // If specified we re-sort by spectral values
        if (sortBySpecValues) {
            sortBySpectralValue(showIndex);
        }
    }
    
    public boolean isUtypeAsNames() {
        return utypeAsNames;
    }

    public void setUtypeAsNames(boolean utypeAsNames) {
        this.utypeAsNames = utypeAsNames;
    }
    
    public boolean isUsePlotterDataTables() {
        return usePlotterDataTables;
    }

    public void setUsePlotterDataTables(boolean usePlotterDataTables) {
        this.usePlotterDataTables = usePlotterDataTables;
    }

    public ColumnInfoMatcher getColumnInfoMatcher() {
        return columnInfoMatcher;
    }

    public void setColumnInfoMatcher(ColumnInfoMatcher columnInfoMatcher) {
        this.columnInfoMatcher = columnInfoMatcher;
    }

    public boolean isSortBySpecValues() {
        return sortBySpecValues;
    }

    public void setSortBySpecValues(boolean sortBySpectralDataValues) {
        this.sortBySpecValues = sortBySpectralDataValues;
    }
    
    /**
     * Overridden to customize header tooltips and appearances
     */
    @Override 
    protected JTableHeader createDefaultTableHeader() {
        return new StarJTableHeader(columnModel);
    }
    
    @Override
    public void setStarTable(StarTable table, boolean showIndex) {
        super.setStarTable(table, showIndex);
        if (utypeAsNames) {
            setUtypeColumnNames();
        }
    }
    
    /**
     * Selects the specified row from the specified star table. If the table isn't currently 
     * selected, we add it - which resets the view.
     * 
     * @param starTableIndex
     * @param irow
     */
    public void selectRowIndex(int starTableIndex, int irow) {
        // irow corresponds to the row in the (possibly masked) IrisStarTable, we need to 
        // map it back to the correct row in the dataTable.
        IrisStarTable selectedTable = this.selectedStarTables.get(starTableIndex);
        int trueRow = selectedTable.getBaseTableRow(irow);
        
        // Actual row is the trueRow plus the length of all the other tables (based on the
        // base table! Not the masked table!)
        for (int i=0; i<starTableIndex; i++) {
            trueRow += this.selectedStarTables.get(i).getBaseTable().getRowCount();
        }
        
        // Map true index to sorted view index
        trueRow = convertRowIndexToView(trueRow);
        
        this.selectionModel.addSelectionInterval(trueRow, trueRow);
        this.scrollRectToVisible(new Rectangle(this.getCellRect(trueRow, 0, true)));
    }
    
    /**
     * Returns selected rows according either to the model index, or to the view's index - as
     * specified by modelView.
     */
    public int[] getSelectedRows(boolean modelView) {
        int[] rows = super.getSelectedRows();
        if (!modelView) {
            return rows;
        }
        
        for (int i = 0; i < rows.length; i++) {
            rows[i] = convertRowIndexToModel(rows[i]);
        }
        Arrays.sort(rows);
        return rows;
    }
    
    public RowSelection getRowSelection() {
        return new RowSelection(this.selectedStarTables, this.getSelectedRows(true));
    }
    
    private void setUtypeColumnNames() {
        
        // If we're using utypes as column names, override existing settings here.
        for (int i=0;i < columnModel.getColumnCount(); i++) {
            StarTableColumn c = (StarTableColumn) columnModel.getColumn(i);
            
            if (c.getColumnInfo() != null &&
                StringUtils.isNotBlank(c.getColumnInfo().getUtype()))
            {
                String utype = UTYPE.trimPrefix(c.getColumnInfo().getUtype());
                c.setHeaderValue(utype);
            }
        }
    }
    
    private void sortBySpectralValue(boolean showIndex) {
        // Do nothing is we have no data
        if (getStarTable().getRowCount() <= 0) return;
        
        try {
            ColumnIdentifier id = new ColumnIdentifier(getStarTable());
            int col = id.getColumnIndex(Column.Spectral_Value.name());
            
            // Nothing to be done if there is no spectral value
            if (col == -1) return;
            
            // Add one if there's an index column
            if (showIndex) col++;
            
            // Sort based on spectral value column
            TableRowSorter<?> sorter = (TableRowSorter<?>) getRowSorter();
            sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(col, SortOrder.ASCENDING)));
            sorter.sort();
        } catch (IOException ex) {
            // Ignore these
            ex.printStackTrace();
            logger.log(Level.WARNING, "Could not read spectral value column", ex);;
        }
    }
    
    protected class StarJTableHeader extends JTableHeader {
        
        private static final long serialVersionUID = -3882589620263074781L;
        
        public StarJTableHeader(TableColumnModel model) {
            super(model);
        }
        
        @Override
        public String getToolTipText(MouseEvent evt) {
            Point p = evt.getPoint();
            int index = columnModel.getColumnIndexAtX(p.x);
            String tt = printColumnInfo((StarTableColumn) columnModel.getColumn(index));
            return tt;
        }
        
        private String printColumnInfo(StarTableColumn column) {
            ColumnInfo info = column.getColumnInfo();
            
            StringBuilder bb = new StringBuilder();
            bb.append("<html>");
            if (!StringUtils.isEmpty(info.getName())) {
                bb.append(String.format("name: %s<br>", info.getName()));
            }
            if (!StringUtils.isEmpty(info.getUnitString())) {
                bb.append(String.format("unit: %s<br>", info.getUnitString()));
            }
            if (!StringUtils.isEmpty(info.getUtype())) {
                bb.append(String.format("utype: %s<br>", info.getUtype()));
            }
            bb.append("</html>");
            
            return bb.toString();
        }
    }
    
    /**
     * Represents a selection of rows in each star table by mapping the current row selection
     * into a list of rows for each StarTable currently in the segment plotter.
     * 
     * e.g.
     * 
     * Given a list of two star tables with 3 rows each
     * [[r11, r12, r13], [r21, r22, r23]]
     * 
     * and a list of selected rows
     * S = [0,1,4,5]
     * 
     * We can map the single array S into a list of arrays corresponding to rows in the
     * list of star tables since 0,1 applies to the first star table. Any row index beyond 2 
     * will apply to the second star table (as the first table has 3 rows).
     * 
     * The resulting row selection would be:
     * [[0,1],[1,2]]
     * 
     * Corresponding to elements:
     * [[r11, r12], [r22, r23]]
     *
     */
    public static class RowSelection {
        
        public final IrisStarTable[] selectedTables;
        public final int[][] selectedRows;
        public final int[] originalRows;
        
        private RowSelection(List<IrisStarTable> tables, int[] rows) {
            
            this.selectedRows = new int[tables.size()][];
            Arrays.fill(selectedRows, new int[0]);
            
            this.selectedTables = tables.toArray(new IrisStarTable[tables.size()]);
            
            this.originalRows = rows;
            
            int startIndex = 0; // start index of current star table
            int t = 0; // current table
            int i = 0; // current selected row
            
            // In the ordered list of selected rows, each star table will have start index equal
            // to the sum of the lengths of all previous star tables, and end index equal to the 
            // start index plus the length of the table.
            while (t < selectedTables.length) {
                int end = (int) (startIndex + selectedTables[t].getBaseTable().getRowCount());
                
                // Get subarray that applies to this star table
                int start = i;
                while (i < rows.length && rows[i] < end) ++i;
                selectedRows[t] = ArrayUtils.subarray(rows, start, i);
                
                // Adjust indexes to match star table start index
                for (int j=0; j<selectedRows[t].length; j++) {
                    selectedRows[t][j] -= startIndex;
                }
                
                startIndex += selectedTables[t].getRowCount();
                t++;
            }
        }
    }
}
