package com.saio.lab4.tasks

import breeze.linalg._

class ResourceAllocationTask(F: DenseMatrix[Int]) {
  val (c, n) = (F.cols, F.rows)

  def solve: (Int, List[Int]) = BellmanFunction(n, c - 1)

  def BellmanFunction(i: Int, y: Int): (Int, List[Int]) = {
    if (i == 1) return (F(0, y), List(y))
    val res = (0 to y).map(z => {
      val bf = BellmanFunction(i - 1, y - z)
      (F(i - 1, z) + bf._1, z, bf._2)
    }).maxBy(_._1)
    (res._1, res._2 :: res._3)
  }
}