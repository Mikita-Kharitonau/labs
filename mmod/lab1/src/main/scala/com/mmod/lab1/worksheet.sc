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

val random = new LinearCongruentGenerator

val p = List(0.00032, 0.0064, 0.0512, 0.2048, 0.4096, 0.32768)
var sum = 0.0
val dfv = 0.0 :: p.map(pi => {sum += pi; sum})
val res: List[Double] = (0 until 100000).map(i => dfv.count(_ <= random.next) - 1.0).toList


val f = Figure()

val h = f.subplot(0)

h += hist(res, 10)

f.refresh