package io.scalac.encog

object HelloWorld {

  def main(args: Array[String]) {
    ClassicRunner.run()
    println("---===---")
    DSLRunner.run()
  }
}
