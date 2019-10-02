package com.saio.lab2

import com.saio.lab2.methods.BnBMethod
import breeze.linalg.{
  DenseMatrix,
  DenseVector
}

object Main {

  def main(args: Array[String]): Unit = {

    val bnb: BnBMethod = new BnBMethod(
      DenseMatrix(
        (1.0, -5.0, 3.0, 1.0, 0.0, 0.0),
        (4.0, -1.0, 1.0, 0.0, 1.0, 0.0),
        (2.0, 4.0, 2.0, 0.0, 0.0, 1.0)
      ),
      DenseVector(-8.0, 22.0, 30.0),
      DenseVector(7.0, -2.0, 6.0, 0.0, 5.0, 2.0),
      DenseVector(2.0, 1.0, 0.0, 0.0, 1.0, 1.0),
      DenseVector(6.0, 6.0, 5.0, 2.0, 4.0, 6.0)
    )

    println(bnb.solve)
  }
}
