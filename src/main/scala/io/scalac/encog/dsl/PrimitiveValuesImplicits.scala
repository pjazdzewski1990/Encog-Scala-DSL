package io.scalac.encog.dsl

import scala.reflect.ClassTag

object PrimitiveValuesImplicits {

  case class Layers(num: Int)

  implicit class IntImplicits(l: Int) {
    def layers: Layers = {
      if(l <= 0) throw new IllegalArgumentException("There must be at least one layer")
      Layers(l)
    }
  }

  trait EncogCollection[+T] {
    def collect(): List[T]
  }

  type EncogMatrix = EncogCollection[EncogCollection[Double]]

  implicit class EncogArray1[T : ClassTag](val elem1: T) extends EncogCollection[T] {
    def |(v: T) = new EncogArray2[T](elem1, v)
    def \\(v: T) = this.|(v)
    def \\\(v: T) = this.|(v)

    override def collect(): List[T] = List[T](elem1)
  }
  case class EncogArray2[T : ClassTag](elem1: T, elem2: T) extends EncogCollection[T] {
    def |(v: T) = new EncogArray3[T](elem1, elem2, v)
    def \\(v: T) = this.|(v)
    def \\\(v: T) = this.|(v)

    override def collect(): List[T] = List(elem1, elem2)
  }
  case class EncogArray3[T : ClassTag](elem1: T, elem2: T, elem3: T) extends EncogCollection[T] {
    def |(v: T) = new EncogArray4[T](elem1, elem2, elem3, v)
    def \\(v: T) = this.|(v)
    def \\\(v: T) = this.|(v)

    override def collect(): List[T] = List(elem1, elem2, elem3)
  }
  case class EncogArray4[T : ClassTag](elem1: T, elem2: T, elem3: T, elem4: T) extends EncogCollection[T] {
    def |(v: T) = new EncogArray5[T](elem1, elem2, elem3, elem4, v)
    def \\(v: T) = this.|(v)
    def \\\(v: T) = this.|(v)

    override def collect(): List[T] = List(elem1, elem2, elem3, elem4)
  }
  case class EncogArray5[T : ClassTag](elem1: T, elem2: T, elem3: T, elem4: T, elem5: T) extends EncogCollection[T] {
    def |(v: T): EncogArray5[T] = throw new UnsupportedOperationException("DSL (for now) handles Arrays up to 4 elements!")
    def \\(v: T): EncogArray5[T] = throw new UnsupportedOperationException("DSL (for now) handles Arrays up to 4 elements!")
    def \\\(v: T): EncogArray5[T] = throw new UnsupportedOperationException("DSL (for now) handles Arrays up to 4 elements!")

    override def collect(): List[T] = throw new UnsupportedOperationException("DSL (for now) handles Arrays up to 4 elements!")
  }
  
  implicit def tuple2Encog[T : ClassTag](t: Tuple1[T]) = EncogArray1(EncogArray1(t._1))
}
