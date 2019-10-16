package com.mzi.stb

import scala.math.pow
import scala.annotation.tailrec

object Common {

  val H_TABLE = Array(
    Array(0xB1, 0x94, 0xBA, 0xC8, 0x0A, 0x08, 0xF5, 0x3B, 0x36, 0x6D, 0x00, 0x8E, 0x58, 0x4A, 0x5D, 0xE4),
    Array(0x85, 0x04, 0xFA, 0x9D, 0x1B, 0xB6, 0xC7, 0xAC, 0x25, 0x2E, 0x72, 0xC2, 0x02, 0xFD, 0xCE, 0x0D),
    Array(0x5B, 0xE3, 0xD6, 0x12, 0x17, 0xB9, 0x61, 0x81, 0xFE, 0x67, 0x86, 0xAD, 0x71, 0x6B, 0x89, 0x0B),
    Array(0x5C, 0xB0, 0xC0, 0xFF, 0x33, 0xC3, 0x56, 0xB8, 0x35, 0xC4, 0x05, 0xAE, 0xD8, 0xE0, 0x7F, 0x99),
    Array(0xE1, 0x2B, 0xDC, 0x1A, 0xE2, 0x82, 0x57, 0xEC, 0x70, 0x3F, 0xCC, 0xF0, 0x95, 0xEE, 0x8D, 0xF1),
    Array(0xC1, 0xAB, 0x76, 0x38, 0x9F, 0xE6, 0x78, 0xCA, 0xF7, 0xC6, 0xF8, 0x60, 0xD5, 0xBB, 0x9C, 0x4F),
    Array(0xF3, 0x3C, 0x65, 0x7B, 0x63, 0x7C, 0x30, 0x6A, 0xDD, 0x4E, 0xA7, 0x79, 0x9E, 0xB2, 0x3D, 0x31),
    Array(0x3E, 0x98, 0xB5, 0x6E, 0x27, 0xD3, 0xBC, 0xCF, 0x59, 0x1E, 0x18, 0x1F, 0x4C, 0x5A, 0xB7, 0x93),
    Array(0xE9, 0xDE, 0xE7, 0x2C, 0x8F, 0x0C, 0x0F, 0xA6, 0x2D, 0xDB, 0x49, 0xF4, 0x6F, 0x73, 0x96, 0x47),
    Array(0x06, 0x07, 0x53, 0x16, 0xED, 0x24, 0x7A, 0x37, 0x39, 0xCB, 0xA3, 0x83, 0x03, 0xA9, 0x8B, 0xF6),
    Array(0x92, 0xBD, 0x9B, 0x1C, 0xE5, 0xD1, 0x41, 0x01, 0x54, 0x45, 0xFB, 0xC9, 0x5E, 0x4D, 0x0E, 0xF2),
    Array(0x68, 0x20, 0x80, 0xAA, 0x22, 0x7D, 0x64, 0x2F, 0x26, 0x87, 0xF9, 0x34, 0x90, 0x40, 0x55, 0x11),
    Array(0xBE, 0x32, 0x97, 0x13, 0x43, 0xFC, 0x9A, 0x48, 0xA0, 0x2A, 0x88, 0x5F, 0x19, 0x4B, 0x09, 0xA1),
    Array(0x7E, 0xCD, 0xA4, 0xD0, 0x15, 0x44, 0xAF, 0x8C, 0xA5, 0x84, 0x50, 0xBF, 0x66, 0xD2, 0xE8, 0x8A),
    Array(0xA2, 0xD7, 0x46, 0x52, 0x42, 0xA8, 0xDF, 0xB3, 0x69, 0x74, 0xC5, 0x51, 0xEB, 0x23, 0x29, 0x21),
    Array(0xD4, 0xEF, 0xD9, 0xB4, 0x3A, 0x62, 0x28, 0x75, 0x91, 0x14, 0x10, 0xEA, 0x77, 0x6C, 0xDA, 0x1D)
  )

  def F(X: Array[Int], O: Array[Int]) = {
    var a = X.slice(0, 32)
    var b = X.slice(32, 64)
    var c = X.slice(64, 96)
    var d = X.slice(96, 128)

    (1 to 8).foreach(i => {
      b = xor(b, G(plus(a, Ki(O, 7 * i - 6)), 5))
      c = xor(c, G(plus(d, Ki(O, 7 * i - 5)), 21))
      a = minus(a, G(plus(b, Ki(O, 7 * i - 4)), 13))
      val e = xor(G(plus(plus(b, c), Ki(O, 7 * i - 3)), 21), corners(i, 32))
      b = plus(b, e)
      c = minus(c, e)
      d = plus(d, G(plus(c, Ki(O, 7 * i - 2)), 13))
      b = xor(b, G(plus(a, Ki(O, 7 * i - 1)), 21))
      c = xor(c, G(plus(d, Ki(O, 7 * i)), 5))

      var x = a; a = b; b = x
      x = c; c = d; d = x
      x = b; b = c; c = x
    })

    b ++ d ++ a ++ c
  }

  def F_(X: Array[Int], O: Array[Int]) = {
    var a = X.slice(0, 32)
    var b = X.slice(32, 64)
    var c = X.slice(64, 96)
    var d = X.slice(96, 128)

    (8 to 1 by -1).foreach(i => {
      b = xor(b, G(plus(a, Ki(O, 7 * i)), 5))
      c = xor(c, G(plus(d, Ki(O, 7 * i - 1)), 21))
      a = minus(a, G(plus(b, Ki(O, 7 * i - 2)), 13))
      val e = xor(G(plus(plus(b, c), Ki(O, 7 * i - 3)), 21), corners(i, 32))
      b = plus(b, e)
      c = minus(c, e)
      d = plus(d, G(plus(c, Ki(O, 7 * i - 4)), 13))
      b = xor(b, G(plus(a, Ki(O, 7 * i - 5)), 21))
      c = xor(c, G(plus(d, Ki(O, 7 * i - 6)), 5))

      var x = a; a = b; b = x
      x = c; c = d; d = x
      x = a; a = d; d = x
    })

    c ++ a ++ d ++ b
  }

  def H(u: Array[Int]): Array[Int] = {
    fromIntToBits(H_TABLE(fromBitsToInt(u.take(4)))(fromBitsToInt(u.drop(4))), 8)
  }

  def G(u: Array[Int], r: Int) = {
    val hs = slicer(u, 8).map(H).toList.flatten.toArray
    nTimes(hs, r, RotHi)
  }

  def ShLo(u: Array[Int]): Array[Int] = corners(cover(u) / 2, u.length)

  def ShHi(u: Array[Int]): Array[Int] = corners(cover(u) * 2, u.length)

  def RotHi(u: Array[Int]): Array[Int] = xor(ShHi(u), nTimes(u, u.length - 1, ShLo))

  def Ki(O: Array[Int], i: Int): Array[Int] = {
    val index = (i % 8) * 32
    O.slice(index, index + 32)
  }

  def plus(u: Array[Int], v: Array[Int]): Array[Int] = {
    corners(cover(u) + cover(v), u.length)
  }

  def minus(u: Array[Int], v: Array[Int]): Array[Int] = {
    val uCover = cover(u)
    val vCover = cover(v)
    val wCover =
      if (vCover < uCover) uCover - vCover
      else uCover + pow(2, u.length).toLong - vCover

    corners(wCover, u.length)
  }

  def corners(U: Long, dimension: Int): Array[Int] = {
    val num = fromIntToBits(U % pow(2, dimension).toLong, dimension)
    slicer(num, 8).toList.reverse.flatten.toArray
  }

  def cover(u: Array[Int]): Long = {
    (0 until u.length / 8)
      .map(i => {
        pow(2, 8 * i).toLong * Integer
          .parseInt(u.slice(8 * i, 8 * (i + 1)).mkString(""), 2)
      })
      .sum
  }

  // Utils
  def slicer(arr: Array[Int], delta: Int): Array[Array[Int]] = {
    (0 to arr.length / delta).map(i => arr.slice(delta * i, delta * (i + 1))).filter(_.length > 0).toArray
  }

  @tailrec
  def nTimes(u: Array[Int], n: Int, f: Array[Int] => Array[Int]): Array[Int] = {
    if (n == 0) u
    else nTimes(f(u), n - 1, f)
  }

  def fromBitsToInt(bits: Array[Int]) = Integer.parseInt(bits.mkString(""), 2)

  def fromIntToBits(num: Long, dim: Int): Array[Int] = {
    val bs = num.toBinaryString
    ("0" * (dim - bs.length) + bs).toList.map(ch => ch.toInt - 48).toArray
  }

  def xor(u: Array[Int], v: Array[Int]): Array[Int] = {
    val res = new Array[Int](u.length)
    u.indices.foreach(i => res(i) = (u(i) + v(i)) % 2)
    res
  }
}
