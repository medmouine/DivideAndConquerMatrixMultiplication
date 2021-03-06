package UI.listeners;

import domain.MatrixTableModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MatrixRowsSizeChangeListener implements ChangeListener {
  private final MatrixTableModel matrixTableModel;
  private final JSpinner matrixSizeSpinner;
  private final JTable matrixTable;

  public MatrixRowsSizeChangeListener(final JTable matrixTable, final JSpinner matrixSizeSpinner) {
    this.matrixTableModel = (MatrixTableModel) matrixTable.getModel();
    this.matrixSizeSpinner = matrixSizeSpinner;
    this.matrixTable = matrixTable;
  }

  @Override
  public void stateChanged(final ChangeEvent e) {
    if (this.matrixTableModel.setRowsSize((int) this.matrixSizeSpinner.getValue())) {
      this.matrixTable.setModel(this.matrixTableModel.clone());
      this.matrixTable.repaint();
    }
  }
}
