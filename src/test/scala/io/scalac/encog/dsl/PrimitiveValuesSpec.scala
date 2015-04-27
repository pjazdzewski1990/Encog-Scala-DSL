package io.scalac.encog.dsl

import org.scalatest._

import io.scalac.encog.dsl.PrimitiveValuesImplicits._

class PrimitiveValuesSpec extends FlatSpec with Matchers {

  "Long" should "allow creating layers" in {
    3.layers === Layers(3)
    (5 layers) === Layers(5)
  }

  it should "throw IllegalArgumentException if 0 or less layers are constructed" in {
    a [IllegalArgumentException] should be thrownBy {
      0.layers
    }
    a [IllegalArgumentException] should be thrownBy {
      -1 layers
    }
  }
}
