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
    val twoElements = 1.0 | 2.0 // 1x2 matrix
    twoElements shouldBe a [EncogArray2[_]]

    val fourElements = 1.0 | 2.0 | 3.0 | 4.0 // 1x4 matrix
    fourElements shouldBe a [EncogArray4[_]]

    val vertical = Tuple1(1.0) \\ 2.0 \\ 3.0 \\ 4.0 // 4x1 matrix
    vertical shouldBe a [EncogArray4[_]]
    vertical.elem1 shouldBe a [EncogArray1[_]]
    vertical.elem1.elem1 shouldBe 1.0
  }

  it should "allows creating matrices of well defined sizes" in {
    val matrix =
      (1.0 | 2.0) \\
      (3.0 | 4.0)
    matrix shouldBe a [EncogArray2[_]]
    matrix.elem1 shouldBe a [EncogArray2[_]]
    matrix.elem2 shouldBe a [EncogArray2[_]]
  }

  it should "allows creating 3d matrices of well defined sizes" in {
    val firstDimension: EncogArray3[EncogArray4[Double]] =
      ( (1.0 | 2.0 | 3.0 | 3.5) \\
        (4.0 | 5.0 | 6.0 | 6.5) \\
        (7.0 | 8.0 | 9.0 | 9.5)  )
    val secondDimension: EncogArray3[EncogArray4[Double]] =
      ( (101.0 | 102.0 | 103.0 | 103.5) \\
        (104.0 | 105.0 | 106.0 | 106.5) \\
        (107.0 | 108.0 | 109.0 | 109.5)  )

    val matrix =
      ( (1.0  |  2.0   | 3.0   | 3.5)   \\
        (4.0  |  5.0   | 6.0   | 6.5)   \\
        (7.0  |  8.0   | 9.0   | 9.5)
      ) \\\ ( /// don't put \\\ on a separate line or you will get a "no existing operator" error
        (101.0 | 102.0 | 103.0 | 103.5) \\
        (104.0 | 105.0 | 106.0 | 106.5) \\
        (107.0 | 108.0 | 109.0 | 109.5)
      )

    matrix shouldBe a [EncogArray2[_]]
    matrix.elem1 shouldBe a [EncogArray3[_]]
    matrix.elem1 === firstDimension
    matrix.elem2 shouldBe a [EncogArray3[_]]
    matrix.elem2 === secondDimension

    matrix.elem1.elem1 === firstDimension.elem1
    matrix.elem2.elem3 === secondDimension.elem3

    matrix.elem1.elem1.elem4 === firstDimension.elem1.elem4
    matrix.elem2.elem3.elem1 === secondDimension.elem3.elem1
  }
}
