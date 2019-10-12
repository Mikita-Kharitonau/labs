package com.mzi.des

object Main {

  def fromArrayOfBytesToListOfBits(arr: Array[Byte]): Array[Int] = {
    arr.toList.flatMap(b => {
      val bs = b.toBinaryString
      ("0" * (8 - bs.length) + bs).map(_.toByte - 48)
    }).toArray
  }

  def main(args: Array[String]): Unit = {
    val d = new Des(fromArrayOfBytesToListOfBits("aaaaaaaa".getBytes()))
    d.decrypt(d.encrypt(
      fromArrayOfBytesToListOfBits("aaaaaaaa".getBytes())
    )).foreach(i => print(i))
  }
}

// key 01100001 10110000 01011000 00101100 00010110 00001011 10000101 11000010
// data 01100001 01100001 01100001 01100001 01100001 01100001 01100001 01100001

// Пример из методы
// key 10101010  10111011  00001001  00011000  00100111  00110110  11001100  11011101
// data 00010010  00110100  01010110  010101011  11001101  00010011  00100101  00110110

// Array(1, 0,1,0, 1,0,1, 0,1,0, 1,1,1, 0,1,1, 0,0,0, 0,1,0, 0,1,0, 0,0,1, 1,0,0, 0,0,0, 1,0,0, 1,1,1, 0,0,1, 1,0,1, 1,0,1, 1,0,0, 1,1,0, 0,1,1, 0,1,1, 1,0,1)
// Array(0,0,0,1, 0,0,1, 0,0,0, 1,1,0, 1,0,0, 0,1,0, 1,0,1, 1,0,1, 0,1,0, 1,0,1, 1,1,1, 0,0,1, 1,0,1, 0,0,0, 1,0,0, 1,1,0, 0,1,0, 0,1,0, 1,0,0, 1,1,0, 1,1,0)