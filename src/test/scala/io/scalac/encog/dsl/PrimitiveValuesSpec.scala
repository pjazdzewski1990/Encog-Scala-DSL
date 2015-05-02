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

  "Double" should "allow creating flat Arrays" in {
    val twoElements = 1.0 | 2.0
    twoElements shouldBe a [EncogArray2[_]]

    val fourElements = 1.0 | 2.0 | 3.0 | 4.0
    fourElements shouldBe a [EncogArray4[_]]
  }

  it should "allows creating matrixes of well defined sizes" in {
    val matrix =
      (1.0 | 2.0) \\
      (3.0 | 4.0)
    matrix shouldBe a [EncogArray2[_]]
    matrix.asInstanceOf[EncogArray2[Double]].elem1 shouldBe a [EncogArray2[_]]
    matrix.asInstanceOf[EncogArray2[Double]].elem2 shouldBe a [EncogArray2[_]]
  }
}
