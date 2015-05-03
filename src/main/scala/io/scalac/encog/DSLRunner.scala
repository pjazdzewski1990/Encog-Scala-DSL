package io.scalac.encog

import io.scalac.encog.dsl.EncogImplicits._
import io.scalac.encog.dsl.PrimitiveValuesImplicits._
import io.scalac.encog.dsl.StructureImplicits._
import org.encog.Encog
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation

import scala.collection.JavaConversions._

object DSLRunner {
  def run() = {

    val input =
      ( 0.0 | 0.0 ) \\
      ( 1.0 | 0.0 ) \\
      ( 0.0 | 1.0 ) \\
      ( 1.0 | 1.0 )

    val ideal =
      Tuple1(0.0) \\
            (1.0) \\
            (1.0) \\
            (0.0)

    val network =
      (InputLayer having bias having 2.layers) +
      (ActivationSigmoid having bias having 3.layers) +
      (ActivationSigmoid having 1.layers)

    val procedure = input into network using (d => new ResilientPropagation(d.network, d.data)) until (_ < 0.01) giving ideal
    val trainResult = procedure.get()

    System.out.println("Neural Network Results for DSL:")

    for { pair <- trainResult.trainingSet.getData() } {
      val output = trainResult.trainedNetwork.compute(pair.getInput())
      println(pair.getInput().getData(0) + "," + pair.getInput().getData(1)
        + ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
    }

    Encog.getInstance().shutdown()
  }
}
