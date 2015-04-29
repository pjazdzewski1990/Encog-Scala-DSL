package io.scalac.encog.dsl

object PrimitiveValuesImplicits {

  case class Layers(num: Int)

  implicit class IntImplicits(l: Int) {
    def layers: Layers = {
      if(l <= 0) throw new IllegalArgumentException("There must be at least one layer")
      Layers(l)
    }
  }
}
