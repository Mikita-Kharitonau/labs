import breeze.linalg._
import breeze.plot._
import scala.util.Random

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

val random = new LinearCongruentGenerator

import scala.math._

val n = 1000
val k = 200.0

def nd(x: Double) = {
  ((1 / pow(2 * Pi, 0.5)) * exp(-pow(x, 2)/2))
}
val p = (-n to n).map(i => nd(i / k)).toList
var sum = 0.0
val dfv = p.map(pi => {sum += (pi / k); sum})

p.sum / k

val (a, b) = (0.0, 200.0)

val r = new Random()

val res: List[Double] = (0 until 10000).map(i => dfv.count(_ <= r.nextFloat).toDouble * (b - a) / (2 * n) + a).toList

val f = Figure()

val h = f.subplot(0)

h += hist(res, 1000)

f.refresh