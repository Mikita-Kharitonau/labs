package com.mzi.stb

object Common {

  // Пункт 6.1.3
  def crypt(X: Array[Int], O: Array[Int]) = {
    val a = X.slice(0, 32)
    val b = X.slice(32, 64)
    val c = X.slice(64, 96)
    val d = X.slice(96, 128)



  }


  def plus(u: Array[Int], v: Array[Int]) = {

  }

  def cover(u: Array[Int]): Long = {

  }

  def xor(u: Array[Int], v: Array[Int]): Array[Int] = {
    val res = new Array[Int](u.length)
    u.indices.foreach(i => res(i) = (u(i) + v(i)) % 2)
    res
  }

}
