package io.scalac.encog.dsl

import org.scalatest.{Matchers, FlatSpec}

import io.scalac.encog.dsl.EncogImplicits._
import io.scalac.encog.dsl.PrimitiveValuesImplicits._

class EncogImplicitsSpec extends FlatSpec with Matchers {
  "Activation Function Implicits" should "start with Input" in {
    InputLayer === new EnrichedBasicLayer(null)
  }

  it should "allow updating using having keyword" in {

    InputLayer having bias
    InputLayer having 1.layers
    InputLayer having bias having 1.layers
  }

  it should "work with other activation functions" in {
    ActivationSigmoid having bias having 1.layers
  }

  "LayersHolder" should "allow combining Activation Functions together" in {
    val holder = (InputLayer having bias) + (ActivationSigmoid having bias)
    holder.length == 2
  }
  "LayersHolder" should "allow combining multiple Activation Functions together" in {
    val holder =
      (InputLayer having bias) +
      (ActivationSigmoid having bias) +
      (ActivationSigmoid having 2.layers) +
      (ActivationSigmoid having bias having 1.layers)
    holder.length == 4
  }
}
