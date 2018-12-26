package strategie;

import domain.MatrixMultiplier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DivideAndConquerMatrixMultipler implements MatrixMultiplier {
  @Override
  public List<List<Integer>> multiply(final List<List<Integer>> matrixA, final List<List<Integer>> matrixB) {
    final int n = matrixA.size();
    final int m = matrixB.size();
    final int p = matrixB.get(0).size();

    if (n <= 2 || m <=2) {
      List<List<Integer>> product = new ArrayList<>();
      for (int i = 0; i < n; ++i) {
        product.add(new ArrayList<>());
        for (int j = 0; j < p; ++j) {
          product.get(i).add(0);
        }
      }
      for(int i = 0; i < n; i++) {
        for (int j = 0; j < p; j++) {
          for (int k = 0; k < m; k++) {
            product.get(i).set(j, product.get(i).get(j) + matrixA.get(i).get(k)* matrixB.get(k).get(j)) ;
          }
        }
      }
      return product;
    }
    if (max3(n, m, p) == n) {
      List<List<Integer>> A1 = splitUp(matrixA);
      List<List<Integer>> A2 = splitDown(matrixA);
      List<List<Integer>> p1 = this.multiply(A1, matrixB);
      List<List<Integer>> p2 = this.multiply(A2, matrixB);
      p1.addAll(p2);
      return p1;
    } else if (max3(n, m, p) == p) {
      List<List<Integer>> B1 = splitLeft(matrixB);
      List<List<Integer>> B2 = splitRight(matrixB);
      List<List<Integer>> p1 = this.multiply(matrixA, B1);
      List<List<Integer>> p2 = this.multiply(matrixA, B2);
      for (int i = 0; i < p1.size(); ++i) {
        p1.get(i).addAll(p2.get(i));
      }
      return p1;
    }
    List<List<Integer>> A1 = splitLeft(matrixA);
    List<List<Integer>> A2 = splitRight(matrixA);
    List<List<Integer>> B1 = splitUp(matrixB);
    List<List<Integer>> B2 = splitDown(matrixB);
    List<List<Integer>> p1 = this.multiply(A1, B1);
    List<List<Integer>> p2 = this.multiply(A2, B2);
    for (int i = 0; i < p1.size(); i++) {
      for (int j = 0; j < p1.get(0).size(); j++) {
        Integer firstElement = p1.get(i).get(j);
        Integer secondElement = p2.get(i).get(j);
        p1.get(i).set(j, firstElement + secondElement);
      }
    }
    return p1;
  }

  private List<List<Integer>> splitRight(final List<List<Integer>> matrixB) {
    final int rowSize = matrixB.get(0).size();
    return matrixB.stream()
            .map(row -> row.subList((int) Math.ceil( (double) rowSize/ 2) - 1, rowSize))
            .collect(Collectors.toList());
  }

  private List<List<Integer>> splitLeft(final List<List<Integer>> matrixB) {
    final int rowSize = matrixB.get(0).size();
    return matrixB.stream()
            .map(row -> row.subList(0, (int) Math.ceil( (double) rowSize / 2) - 1))
            .collect(Collectors.toList());
  }

  private List<List<Integer>> splitDown(final List<List<Integer>> matrixA) {
    return matrixA.subList((int) Math.ceil((double)matrixA.size()/ 2 - 1), matrixA.size());
  }

  private List<List<Integer>> splitUp(final List<List<Integer>> matrixA) {
    return matrixA.subList(0, (int) Math.ceil((double)matrixA.size()/ 2) - 1);
  }

  private int max3(final int n, final int m, final int p) {
    return max2(p, max2(n, m));
  }

  private int max2(final int n, final int m) {
    return (n > m) ? n : m;
  }
}
