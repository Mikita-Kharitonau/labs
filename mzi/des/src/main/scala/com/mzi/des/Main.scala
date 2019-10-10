package com.mzi.des

object Main {

  def fromArrayOfBytesToListOfBits(arr: Array[Byte]): Array[Int] = {
    arr.toList.flatMap(b => {
      val bs = b.toBinaryString
      ("0" * (8 - bs.length) + bs).map(_.toByte - 48)
    }).toArray
  }

  def main(args: Array[String]): Unit = {
    val d = new Des(fromArrayOfBytesToListOfBits("lolloll".getBytes()))
    d.decrypt(d.encrypt(
      fromArrayOfBytesToListOfBits("lollollo".getBytes()),
    )).foreach(i => print(i))
  }
}
