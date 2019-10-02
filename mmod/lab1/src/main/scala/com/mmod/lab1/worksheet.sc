import breeze.linalg._
import breeze.plot._

import scala.math.BigInt

class LinearCongruentGenerator(m: BigInt = BigInt("34359738368"),
                               k: BigInt = BigInt("152587890625"),
                               A0: BigInt = 999) extends Serializable {

  var prevValue: BigInt = A0

  def next: Double = {
    val result: BigInt = (k * prevValue) % m
    prevValue = result
    result.toDouble / m.toDouble
  }
}

val f = Figure()

val h = f.subplot(0)

val random = new LinearCongruentGenerator

import scala.util.Random

val random2 = new Random

h += hist(DenseVector.fill(1000000){random.next}, 10)

h.ylim(50000, 150000)

f.refresh