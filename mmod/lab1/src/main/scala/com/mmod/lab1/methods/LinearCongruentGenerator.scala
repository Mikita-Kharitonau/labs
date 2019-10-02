package com.mmod.lab1.methods

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
