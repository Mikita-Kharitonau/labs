package com.mzi.stb

import java.io.{BufferedOutputStream, FileOutputStream}
import java.nio.file.{Files, Paths}

import Common._

object Main {

  def fromArrayOfBytesToListOfBits(arr: Array[Byte]): Array[Int] = {
    arr.toList.flatMap(b => {
      val bs = ((b + 256) % 256).toBinaryString
      ("0" * (8 - bs.length) + bs).map(_.toByte - 48)
    }).toArray
  }

  def fromArrayOfBitsToArrayOfBytes(arr: Array[Int]) = {
    slicer(arr, 8).map(i => {
      val bs = i.mkString("")
      Integer.parseInt("0" * (8 - bs.length) + bs, 2).toByte
    })
  }

  def writeToFile(arr: Array[Int], filename: String) = {
    val bos = new BufferedOutputStream(new FileOutputStream(filename))
    Stream.continually(bos.write(fromArrayOfBitsToArrayOfBytes(arr)))
    bos.close()
  }

  def main(args: Array[String]): Unit = {
    val byteArray = fromArrayOfBytesToListOfBits(Files.readAllBytes(Paths.get("./plain")))

    val O = ("1110100111011110111001110010110010001111000011000000111110100110001011011101101101001001111101000110111" +
      "10111001110010110010001110000011000000111010100110001011011101101001001000111101000110111001110011100101110100" +
      "0111000001100000011101010011000101111110110").toList.map(ch => ch.toInt - 48).toArray

    val sse = new SimpleSwapEncryption()
    val encrypted = sse.encrypt(byteArray, O)
    writeToFile(encrypted, "./cipher")
    val byteArray2 = Files.readAllBytes(Paths.get("./cipher"))
    val decrypted = sse.decrypt(fromArrayOfBytesToListOfBits(byteArray2), O)
    writeToFile(decrypted, "./plain2")
  }
}
