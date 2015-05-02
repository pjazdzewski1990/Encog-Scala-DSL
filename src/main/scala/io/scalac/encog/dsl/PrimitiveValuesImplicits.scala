package io.scalac.encog.dsl

object PrimitiveValuesImplicits {

  case class Layers(num: Int)

  implicit class IntImplicits(l: Int) {
    def layers: Layers = {
      if(l <= 0) throw new IllegalArgumentException("There must be at least one layer")
      Layers(l)
    }
  }

//  trait EncogCompossible[T] { self =>
//    type JA = self.type
//    def |(v: T): EncogCompossible[T]
//    def \\(v: JA): EncogCompossible[JA] = new EncogArray2(this, v) // |(v)
//  }
//  implicit class EncogArray1[T](elem1: T) extends EncogCompossible[T] {
//    override def |(v: T): EncogCompossible[T] = new EncogArray2[T](elem1, v)
//  }
//  case class EncogArray2[T](elem1: T, elem2: T) extends EncogCompossible[T] {
//    override def |(v: T): EncogCompossible[T] = new EncogArray3[T](elem1, elem2, v)
//  }
//  case class EncogArray3[T](elem1: T, elem2: T, elem3: T) extends EncogCompossible[T] {
//    override def |(v: T): EncogCompossible[T] = new EncogArray4[T](elem1, elem2, elem3, v)
//  }
//  case class EncogArray4[T](elem1: T, elem2: T, elem3: T, elem4: T) extends EncogCompossible[T] {
//    override def |(v: T): EncogCompossible[T] = new EncogArray5[T](elem1, elem2, elem3, elem4, v)
//  }
//  case class EncogArray5[T](elem1: T, elem2: T, elem3: T, elem4: T, elem5: T) extends EncogCompossible[T] {
//    override def |(v: T): EncogCompossible[T] = throw new UnsupportedOperationException("DSL (for now) handles Arrays up to 6 elements!")
//  }

//  trait EncogCompossible[T] { self =>
//    type JA = self.type
//    def |(v: T): EncogCompossible[T]
//  }
  implicit class EncogArray1[T](elem1: T) {
    def |(v: T) = new EncogArray2[T](elem1, v)
    def \\(v: EncogArray1[T]) = EncogArray2(this, v)
  }
  case class EncogArray2[T](elem1: T, elem2: T) {
    def |(v: T) = new EncogArray3[T](elem1, elem2, v)
    def \\(v: EncogArray2[T]) = EncogArray2(this, v)
  }
  case class EncogArray3[T](elem1: T, elem2: T, elem3: T) {
    def |(v: T) = new EncogArray4[T](elem1, elem2, elem3, v)
    def \\(v: EncogArray3[T]) = EncogArray2(this, v)
  }
  case class EncogArray4[T](elem1: T, elem2: T, elem3: T, elem4: T)  {
    def |(v: T) = new EncogArray5[T](elem1, elem2, elem3, elem4, v)
    def \\(v: EncogArray4[T]) = EncogArray2(this, v)
  }
  case class EncogArray5[T](elem1: T, elem2: T, elem3: T, elem4: T, elem5: T) {
    def |(v: T): EncogArray5[T] = throw new UnsupportedOperationException("DSL (for now) handles Arrays up to 6 elements!")
    def \\(v: EncogArray2[T]): EncogArray2[T] = throw new UnsupportedOperationException("DSL (for now) handles Arrays up to 6 elements!")
  }
}
