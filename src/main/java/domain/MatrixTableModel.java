package domain;

import domain.exceptions.MatrixBoundBelowOneException;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixTableModel extends AbstractTableModel {
  private List<List<Integer>> elements = new ArrayList<>();
  private int rowCount;
  private int columnCount;

  public List<List<Integer>> getElements() {
    return elements;
  }

  public void setElements(final List<List<Integer>> elements) {
    this.elements = elements;
  }

  private MatrixTableModel(final List<List<Integer>> elements, final int rowCount, final int columnCount) {
    this.elements = elements;
    this.rowCount = rowCount;
    this.columnCount = columnCount;
  }

  public MatrixTableModel clone() {
    return new MatrixTableModel(this.elements, this.rowCount, this.columnCount);
  }

  public MatrixTableModel(final int xSize, final int ySize) {
    this.rowCount = xSize;
    this.columnCount = ySize;
    if (xSize < 1 || ySize < 1) {
      throw new MatrixBoundBelowOneException(xSize, ySize);
    }
    for (int i = 0; i < xSize; ++i) {
      this.elements.add(new ArrayList<>());
      for (int j = 0; j < ySize; ++j) {
        this.elements.get(i).add(0);
      }
    }
  }

  public int getRowCount() {
    return this.rowCount;
  }

  public int getColumnCount() {
    return this.columnCount;
  }

  public String getColumnName(final int columnIndex) {
    return "C" + columnIndex;
  }

  public Class<?> getColumnClass(final int columnIndex) {
    return Integer.class;
  }

  public boolean isCellEditable(final int rowIndex, final int columnIndex) {
    return true;
  }

  public Object getValueAt(final int rowIndex, final int columnIndex) {
    return elements.get(rowIndex).get(columnIndex);
  }

  public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
    elements.get(rowIndex).set(columnIndex, (Integer) aValue);
  }

  public boolean setRowsSize(final int newXSize) {
    if (this.rowCount > newXSize) {
      this.elements = this.elements.subList(0, newXSize);
      this.rowCount = newXSize;
      return true;
    } else if (this.rowCount < newXSize) {
      do {
        List<Integer> row = new ArrayList<>(Collections.nCopies(this.getColumnCount(), 0));
        this.elements.add(row);
        this.rowCount = newXSize;
      } while (this.elements.size() < newXSize);
      return true;
    }
    return false;
  }

  public boolean setColumnSize(final int newYSize) {
    if (this.columnCount < newYSize) {
      do {
        this.elements.forEach(column -> column.add(0));
        ++this.columnCount;
      } while (this.columnCount < newYSize);
      fireTableStructureChanged();

      return true;
    } else if (this.columnCount > newYSize) {
      this.elements = this.elements.stream().map(row -> row.subList(0, newYSize)).collect(Collectors.toList());
      this.columnCount = newYSize;
      fireTableStructureChanged();
      return true;
    }
    return false;
  }

  public void addTableModelListener(final TableModelListener l) {
  }

  public void removeTableModelListener(final TableModelListener l) {
  }
}
