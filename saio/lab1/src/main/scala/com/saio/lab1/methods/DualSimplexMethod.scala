package com.saio.lab1.methods

import breeze.linalg._
import scala.annotation.tailrec

class DualSimplexMethod(val A: DenseMatrix[Double],
                        val b: DenseVector[Double],
                        val c: DenseVector[Double],
                        val dl: DenseVector[Double],
                        val dh: DenseVector[Double]) {

  private val INF = Double.MaxValue

  private val J = 0 until A.cols

  def solve: Either[String, DenseVector[Double]] = {
    getJb(J, A) match {
      case Some(s) => iteration(s.toSeq)
      case None => Left("Can't find set of basis indexes")
    }

  }

  @tailrec
  private def iteration(
    Jb: Seq[Int],
    coPlanPrev: Option[DenseVector[Double]] = None,
    JnPPrev: Option[Set[Int]] = None,
    JnMPrev: Option[Set[Int]] = None
  ): Either[String, DenseVector[Double]] = {
    val Ab = A(::, Jb).toDenseMatrix
    val B = inv(Ab)

    val currentValues: (DenseVector[Double], Set[Int], Set[Int]) =
      coPlanPrev match {
        case Some(c) => (c, JnPPrev.get, JnMPrev.get)
        case None =>
          val y = c(Jb).toDenseVector.t * B
          val coPlan = DenseVector.zeros[Double](J.size)
          J.foreach(j => coPlan(j) = y * A(::, j) - c(j))
          val Jn = J.toSet -- Jb.toSet
          val JnP = Jn.filter(jn => coPlan(jn) >= 0)
          val JnM = Jn -- JnP
          (coPlan, JnP, JnM)
      }

    val coPlan = currentValues._1
    val JnP = currentValues._2
    val JnM = currentValues._3

    val N = DenseVector.zeros[Double](J.size)
    JnP.foreach(j => N(j) = dl(j))
    JnM.foreach(j => N(j) = dh(j))

    val sum = (JnP ++ JnM).map(j => A(::, j) * N(j)).reduce(_ + _)
    val Nb = B * (b - sum)
    (0 until Nb.size).foreach(i => N(Jb(i)) = Nb(i))

    if ((dl(Jb) <:= Nb).forall(b => b) && (Nb <:= dh(Jb)).forall(b => b))
      return Right(N)

    val (k, jk) = getK(N, Jb)

    val mujk: Int =
      if (N(jk) < dl(jk)) 1
      else if (N(jk) > dh(jk)) -1
      else return Left("Arithmetic error")

    val ek = DenseVector.zeros[Double](Jb.size); ek(k) = 1
    val deltaY: Transpose[DenseVector[Double]] = (mujk.toDouble * ek).t * B

    val mus = J.map(j => deltaY * A(::, j))

    val sigma =
      (JnP.map(j => (if (mus(j) < 0) -coPlan(j) / mus(j) else INF, j)) ++
        JnM.map(j => (if (mus(j) > 0) -coPlan(j) / mus(j) else INF, j)))
        .reduceLeft((a, b) => if (b._1 < a._1) b else a)

    val sigma0 = sigma._1
    val jStar = sigma._2
    if (sigma0 == INF) return Left("Hasn't any permissible plans")

    val JnJk = JnP ++ JnM + jk
    val newCoPlan = DenseVector.zeros[Double](A.cols)
    JnJk.foreach(j => newCoPlan(j) = coPlan(j) + sigma0 * mus(j))

    val newJb = Jb.patch(k, Seq(jStar), 1)
    val newJn = J.toSet -- newJb
    val newJnP = (mujk, JnP(jStar)) match {
      case (1, true)   => JnP - jStar + jk
      case (-1, true)  => JnP - jStar
      case (1, false)  => JnP + jk
      case (-1, false) => JnP
    }

    iteration(newJb, Option(newCoPlan), Option(newJnP), Option(newJn -- newJnP))
  }

  @tailrec
  private def getK(N: DenseVector[Double],
                   Jb: Seq[Int],
                   index: Int = 0): (Int, Int) = {
    if (index > Jb.length - 1) throw new IndexOutOfBoundsException

    val JbI = Jb(index)
    (N(JbI) < dl(JbI) || N(JbI) > dh(JbI)) match {
      case true  => (index, JbI)
      case false => getK(N, Jb, index + 1)
    }
  }

  private def getJb(J: Seq[Int], A: DenseMatrix[Double]): Option[Set[Int]] = {
    selection(J.toSet, A.rows)
      .find(s => det(A(::, s.toSeq).toDenseMatrix) != 0)
  }

  // Just to generate selections
  def selection(set: Set[Int], count: Int): Seq[Set[Int]] = {
    if (count == 1) set.map(Set(_)).toSeq
    else
      set
        .map(elem => selection(set - elem, count - 1).map(_ + elem))
        .reduce(_ ++ _)
  }
}
