package io.scalac.encog

import io.scalac.encog.dsl.EncogImplicits._
import io.scalac.encog.dsl.PrimitiveValuesImplicits._
import io.scalac.encog.dsl.StructureImplicits._
import org.encog.Encog
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation

object DSLRunner {
  def run(input: Array[Array[Double]], ideal: Array[Array[Double]]) = {

//    1.0 | 1.0 | 1.0 | 0.0 \\
//    2.0 | 1.0 | 1.0 | 1.0
//    \\\
//    1.0 | 1.0 | 1.0 | 0.0 \\
//    2.0 | 1.0 | 1.0 | 1.0

    val network =
      (InputLayer having bias having 2.layers) +
      (ActivationSigmoid having bias having 3.layers) +
      (ActivationSigmoid having 1.layers)

    val procedure = input into network using (d => new ResilientPropagation(d.network, d.data) ) until (_ < 0.1) giving ideal
    procedure.get()

    Encog.getInstance().shutdown()
  }
}
