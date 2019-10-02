package com.saio.lab2.methods

import scala.math.{abs, round}

import com.saio.lab1.methods.DualSimplexMethod

import breeze.linalg._

import scala.annotation.tailrec

class BnBMethod(val A: DenseMatrix[Double],
                val b: DenseVector[Double],
                val c: DenseVector[Double],
                val dl: DenseVector[Double],
                val dh: DenseVector[Double]) {

  private val (m, n) = (A.rows, A.cols)
  private val J = 0 until n
  private val r0 =
    if (c.forall(_ >= 0) && dl.forall(_ >= 0)) 0 else Double.MinValue
  private val (mu0, mu) = (0, DenseVector.zeros[Int](n))

  def solve: Either[String, DenseVector[Int]] = {
    val dsm: DualSimplexMethod =
      new DualSimplexMethod(A.copy, b.copy, c.copy, dl.copy, dh.copy)
    iteration(Seq(dsm), r0, mu0, mu)
  }

  @tailrec
  private def iteration(
    tasks: Seq[DualSimplexMethod],
    r0: Double,
    mu0: Int,
    mu: DenseVector[Int]
  ): Either[String, DenseVector[Int]] = {
    if (tasks.isEmpty) {
      mu0 match {
        case 1 => return Right(mu)
        case 0 => return Left("Hasn't any permissible plans")
      }
    }

    val currentTask = tasks.head
    val solution = currentTask.solve
    solution match {
      case Left(msg) => iteration(tasks.tail, r0, mu0, mu)
      case Right(x) =>
        val currentAim = c.t * x
        currentAim <= r0 match {
          case true => iteration(tasks.tail, r0, mu0, mu)
          case false =>
            val notInt = firstNotInt(x)
            notInt match {
              case Some(value) =>
                val currentDl = currentTask.dl.copy
                val currentDh = currentTask.dh.copy
                currentDh(value._1) = math.floor(value._2)
                val subtask1 = new DualSimplexMethod(
                  currentTask.A.copy,
                  currentTask.b.copy,
                  currentTask.c.copy,
                  currentTask.dl.copy,
                  currentDh
                )
                currentDl(value._1) = math.ceil(value._2)
                val subtask2 = new DualSimplexMethod(
                  currentTask.A.copy,
                  currentTask.b.copy,
                  currentTask.c.copy,
                  currentDl,
                  currentTask.dh.copy
                )
                iteration(subtask1 :: subtask2 :: tasks.tail.toList, r0, mu0, mu)
              case None => iteration(tasks.tail, currentAim, 1, toInt(x))
            }
        }
    }
  }

  private def firstNotInt(dv: DenseVector[Double]): Option[(Int, Double)] = {
    (0 until dv.length).foreach(i => if (!isInt(dv(i))) return Some((i, dv(i))))
    None
  }

  private def isInt(double: Double) = abs(round(double) - double) < 0.0001

  private def toInt(d: DenseVector[Double]) = d.map(round(_).toInt)
}
