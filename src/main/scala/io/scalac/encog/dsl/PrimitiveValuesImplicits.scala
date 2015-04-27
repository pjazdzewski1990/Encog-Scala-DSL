package io.scalac.encog.dsl

object PrimitiveValuesImplicits {

  case class Layers(num: Long)

  implicit class LongImplicits(l: Long) {
    def layers: Layers = {
      println(s"Creating $l layers")
      if(l <= 0) throw new IllegalArgumentException("There must be at least one layer")
      Layers(l)
    }
  }
}
