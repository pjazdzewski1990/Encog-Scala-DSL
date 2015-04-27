package io.scalac.encog

object HelloWorld {

  val XOR_INPUT = Array(
    Array( 0.0, 0.0 ),
    Array( 1.0, 0.0 ),
    Array( 0.0, 1.0 ),
    Array( 1.0, 1.0 )
  )

  val XOR_IDEAL = Array(
    Array(0.0),
    Array(1.0),
    Array(1.0),
    Array(0.0)
  )

  def main(args: Array[String]) {
    ClassicRunner.run(XOR_INPUT, XOR_IDEAL)
    println("---===---")
    DSLRunner.run(XOR_INPUT, XOR_IDEAL)
  }
}
