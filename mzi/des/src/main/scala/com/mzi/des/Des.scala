package com.mzi.des

import scala.annotation.tailrec


class Des(key: Array[Int]) {

  val IP: Array[Int] = Array(
    58, 50, 42, 34, 26, 18, 10, 2,
    60, 52, 44, 36, 28, 20, 12, 4,
    62, 54, 46, 38, 30, 22, 14, 6,
    64, 56, 48, 40, 32, 24, 16, 8,
    57, 49, 41, 33, 25, 17, 9, 1,
    59, 51, 43, 35, 27, 19, 11, 3,
    61, 53, 45, 37, 29, 21, 13, 5,
    63, 55, 47, 39, 31, 23, 15, 7
  )

  val IP1: Array[Int] = Array(
    40, 8, 48, 16, 56, 24, 64, 32,
    39, 7, 47, 15, 55, 23, 63, 31,
    38, 6, 46, 14, 54, 22, 62, 30,
    37, 5, 45, 13, 53, 21, 61, 29,
    36, 4, 44, 12, 52, 20, 60, 28,
    35, 3, 43, 11, 51, 19, 59, 27,
    34, 2, 42, 10, 50, 18, 58, 26,
    33, 1, 41, 9, 49, 17, 57, 25
  )

  val P: Array[Int] = Array(
    32, 1, 2, 3, 4, 5,
    4, 5, 6, 7, 8, 9,
    8, 9, 10, 11, 12, 13,
    12, 13, 14, 15, 16, 17,
    16, 17, 18, 19, 20, 21,
    20, 21, 22, 23, 24, 25,
    24, 25, 26, 27, 28, 29,
    28, 29, 30, 31, 32, 1
  )

  val S: Array[Array[Array[Int]]] = Array(
    Array(
      Array(14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7),
      Array(0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8),
      Array(4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0),
      Array(15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13)
    ),
    Array(
      Array(15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10),
      Array(3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5),
      Array(0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15),
      Array(13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9)
    ),
    Array(
      Array(10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8),
      Array(13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1),
      Array(13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7),
      Array(1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12)
    ),
    Array(
      Array(7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15),
      Array(13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9),
      Array(10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4),
      Array(3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14)
    ),
    Array(
      Array(2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9),
      Array(14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6),
      Array(4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14),
      Array(11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3)
    ),
    Array(
      Array(12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11),
      Array(10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8),
      Array(9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6),
      Array(4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13)
    ),
    Array(
      Array(4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1),
      Array(13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6),
      Array(1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2),
      Array(6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12)
    ),
    Array(
      Array(13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7),
      Array(1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2),
      Array(7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8),
      Array(2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11)
    )
  )

  val PI: Array[Int] = Array(
    16, 7, 20, 21,
    29, 12, 28, 17,
    1, 15, 23, 26,
    5, 18, 31, 10,
    2, 8, 24, 14,
    32, 27, 3, 9,
    19, 13, 30, 6,
    22, 11, 4, 25
  )

  val PK1: Array[Int] = Array(
    57, 49, 41, 33, 25, 17, 9,
    1, 58, 50, 42, 34, 26, 18,
    10, 2, 59, 51, 43, 35, 27,
    19, 11, 3, 60, 52, 44, 36,
    63, 55, 47, 39, 31, 23, 15,
    7, 62, 54, 46, 38, 30, 22,
    14, 6, 61, 53, 45, 37, 29,
    21, 13, 5, 28, 20, 12, 4
  )

  val PK2: Array[Int] = Array(
    14, 17, 11, 24, 1, 5,
    3, 28, 15, 6, 21, 10,
    23, 19, 12, 4, 26, 8,
    16, 7, 27, 20, 13, 2,
    41, 52, 31, 37, 47, 55,
    30, 40, 51, 45, 33, 48,
    44, 49, 39, 56, 34, 53,
    46, 42, 50, 36, 29, 32
  )

  val SHIFTS: Array[Int] = Array(
    1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
  )

  var C0: Array[Int] = _
  var D0: Array[Int] = _

  // Just to calculate C0 and D0
  prepareK(key)

  def crypt(source: Array[Int], rounds: (Array[Int], Array[Int], Int) => Array[Int]): Array[Int] = {
    val ip = ext(source, IP)
    val L = ip.take(32)
    val R = ip.drop(32)
    val afterRounds = rounds(L, R, 0)
    ext(afterRounds, IP1)
  }

  def encrypt(plain: Array[Int]): Array[Int] = crypt(plain, roundsEncrypt)

  def decrypt(encrypted: Array[Int]): Array[Int] = crypt(encrypted, roundsDecrypt)

  @tailrec
  private def roundsEncrypt(L: Array[Int], R: Array[Int], i: Int): Array[Int] = {
    if (i == 16) return L ++ R
    val ki = getKi(i)
    roundsEncrypt(R, xor(L, f(R, ki)), i + 1)
  }

  @tailrec
  private def roundsDecrypt(L: Array[Int], R: Array[Int], i: Int): Array[Int] = {
    if (i == 16) return L ++ R
    val ki = getKi(15 - i)
    roundsDecrypt(xor(R, f(L, ki)), L, i + 1)
  }

  def f(R: Array[Int], ki: Array[Int]) = {
    val extended = ext(R, P)
    val afterXor = xor(extended, ki.toArray)
    val b = (0 until 48 by 6).map(i => afterXor.slice(i, i + 6))
    val b_ = (0 until 8).flatMap(i => {
      val bi = b(i)
      val m = Integer.parseInt(bi(0).toString + bi(5), 2)
      val n = Integer.parseInt(bi(1).toString + bi(2) + bi(3) + bi(4), 2)
      val s = S(i)(m)(n)
      val bs = s.toBinaryString
      ("0" * (4 - bs.length) + bs).map(_.toByte - 48)
    }).toArray

    ext(b_, PI)
  }

  // Common functions
  def prepareK(k: Array[Int]) = {
    val arrBuffer = new scala.collection.mutable.ArrayBuffer[Int]
    (0 until 56).foreach(i => arrBuffer.addOne(k(i)))
    (7 to 63 by 8).foreach(arrBuffer.insert(_, 0))
    val CD = ext(arrBuffer.toArray, PK1)

    C0 = CD.take(28)
    D0 = CD.drop(28)
  }

  def ext(source: Array[Int], rule: Array[Int]): Array[Int] = {
    val res = new Array[Int](rule.length)
    rule.indices.foreach(i => res(i) = source(rule(i) - 1))
    res
  }

  def xor(arr1: Array[Int], arr2: Array[Int]) = {
    val res = new Array[Int](arr1.length)
    arr1.indices.foreach(i => res(i) = (arr1(i) + arr2(i)) % 2)
    res
  }

  def getKi(i: Int): Array[Int] = {
    val CD = shiftLeft(C0, SHIFTS.take(i + 1).sum) ++ shiftLeft(D0, SHIFTS.take(i + 1).sum)
    ext(CD, PK2)
  }

  def shiftLeft(arr: Array[Int], i: Int): Array[Int] = arr.drop(i) ++ arr.take(i)

  def shiftRight(arr: Array[Int], i: Int): Array[Int] = arr.drop(arr.length - i) ++ arr.take(arr.length - i)
}
