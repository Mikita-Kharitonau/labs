import java.nio.file.{Files, Paths}

def fromArrayOfBytesToListOfBits(arr: Array[Byte]): List[Int] = {
  arr.toList.flatMap(b => {
    val bs = b.toBinaryString
    ("0" * (8 - bs.length) + bs).map(_.toByte - 48)
  })
}

fromArrayOfBytesToListOfBits(" ".getBytes())

fromArrayOfBytesToListOfBits(Files.readAllBytes(Paths.get("/home/kharivitalij/scrum")))

val IP: Array[Array[Int]] = Array(
  Array(58, 50, 42, 34, 26, 18, 10, 2),
  Array(60, 52, 44, 36, 28, 20, 12, 4),
  Array(62, 54, 46, 38, 30, 22, 14, 6),
  Array(64, 56, 48, 40, 32, 24, 16, 8),
  Array(57, 49, 41, 33, 25, 17, 9, 1),
  Array(59, 51, 43, 35, 27, 19, 11, 3),
  Array(61, 53, 45, 37, 29, 21, 13, 5),
  Array(63, 55, 47, 39, 31, 23, 15, 7)
)

val IP1: Array[Array[Int]] = Array(
  Array(40, 8, 48, 16, 56, 24, 64, 32),
  Array(39, 7, 47, 15, 55, 23, 63, 31),
  Array(38, 6, 46, 14, 54, 22, 62, 30),
  Array(37, 5, 45, 13, 53, 21, 61, 29),
  Array(36, 4, 44, 12, 52, 20, 60, 28),
  Array(35, 3, 43, 11, 51, 19, 59, 27),
  Array(34, 2, 42, 10, 50, 18, 58, 26),
  Array(33, 1, 41, 9, 49, 17, 57, 25)
)

import breeze.linalg.DenseVector

def round()