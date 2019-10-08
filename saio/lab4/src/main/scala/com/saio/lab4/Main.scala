package com.saio.lab4

import breeze.linalg.DenseMatrix
import com.saio.lab4.tasks.ResourceAllocationTask

object Main {
  def main(args: Array[String]): Unit = {
    val rat = new ResourceAllocationTask(
      DenseMatrix(
        (0, 3, 4, 5, 8, 9, 10),
        (0, 2, 3, 7, 9, 12, 13),
        (0, 1, 2, 6, 11, 11, 13)
      )
    )

    println(rat.solve)
  }
}
