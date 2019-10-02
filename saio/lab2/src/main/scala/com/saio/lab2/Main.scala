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
//      DenseMatrix(
//        (1.0, 0.0, 0.0, 12.0, 1.0, -3.0, 4.0, -1.0),
//        (0.0, 1.0, 0.0, 11.0, 12.0, 3.0, 5.0, 3.0),
//        (0.0, 0.0, 1.0, 1.0, 0.0, 22.0, -2.0, 1.0)
//      ),
//      DenseVector(40.0, 107.0, 61.0),
//      DenseVector(2.0, 1.0, -2.0, -1.0, 4.0, -5.0, 5.0, 5.0),
//      DenseVector(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
//      DenseVector(3.0, 5.0, 5.0, 3.0, 4.0, 5.0, 6.0, 3.0)
//      DenseMatrix(
//        (-3.0, 6.0, 7.0),
//        (6.0, -3.0, 7.0)
//      ),
//      DenseVector(8.0, 8.0),
//      DenseVector(3.0, 3.0, 13.0),
//      DenseVector(0.0, 0.0, 0.0),
//      DenseVector(5.0, 5.0, 5.0)

//      DenseMatrix(
//        (1.0, 0.0, 3.0, 1.0, 0.0, 0.0),
//        (0.0, -1.0, 1.0, 1.0, 1.0, 2.0),
//        (-2.0, 4.0, 2.0, 0.0, 0.0, 1.0)
//      ),
//      DenseVector(10.0, 8.0, 10.0),
//      DenseVector(7.0, -2.0, 6.0, 0.0, 5.0, 2.0),
//      DenseVector(0.0, 1.0, -1.0, 0.0, -2.0, 1.0),
//      DenseVector(3.0, 3.0, 6.0, 2.0, 4.0, 6.0)
    )

    println(bnb.solve)
  }

}
