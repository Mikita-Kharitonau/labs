package com.saio.lab1

import com.saio.lab1.methods.DualSimplexMethod
import breeze.linalg.{
  DenseMatrix,
  DenseVector
}

object Main {

  def main(args: Array[String]): Unit = {
    val dsm: DualSimplexMethod = new DualSimplexMethod(
      DenseMatrix(
        (1.0, -5.0, 3.0, 1.0, 0.0, 0.0),
        (4.0, -1.0, 1.0, 0.0, 1.0, 0.0),
        (2.0, 4.0, 2.0, 0.0, 0.0, 1.0)
      ),
      DenseVector(-7.0, 22.0, 30.0),
      DenseVector(7.0, -2.0, 6.0, 0.0, 5.0, 2.0),
      DenseVector(2.0, 1.0, 0.0, 0.0, 1.0, 1.0),
      DenseVector(6.0, 6.0, 5.0, 2.0, 4.0, 6.0)
    )

    println(dsm.solve)
  }
}
