package UI.adapters;

import domain.MatrixTableModel;
import strategie.DivideAndConquerMatrixMultipler;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MatrixMultiplierMouseAdapter extends MouseAdapter {

  private final MatrixTableModel matrixA;
  private final MatrixTableModel matrixB;
  private final DivideAndConquerMatrixMultipler matrixMultiplier;

  public MatrixMultiplierMouseAdapter(final TableModel matrixA, final TableModel matrixB) {
    this.matrixA = (MatrixTableModel) matrixA;
    this.matrixB = (MatrixTableModel) matrixB;
    this.matrixMultiplier = new DivideAndConquerMatrixMultipler();
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    if (this.matrixA.getRowCount() != this.matrixB.getColumnCount()) {
      JOptionPane.showMessageDialog(new JFrame(),
              "Matrix must be in the format n x m and m x p",
              "Input error",
              JOptionPane.ERROR_MESSAGE);
    } else {
      List<List<Integer>> result = this.matrixMultiplier.multiply(this.matrixA.getElements(), this.matrixB.getElements());
      JTable resultTable = new JTable();
      MatrixTableModel resultMatrixTableModel = new MatrixTableModel(result.size(), result.get(0).size());
      resultMatrixTableModel.setElements(result);
      resultTable.setModel(resultMatrixTableModel);
      JOptionPane.showMessageDialog(new JFrame("Result"), resultTable);
    }
  }
}
