package com.mmod.lab1

import methods.LinearCongruentGenerator


object Main {

  def main(args: Array[String]): Unit = {
    val random = new LinearCongruentGenerator

    (0 to 100).foreach(i => println(random.next))
  }

}